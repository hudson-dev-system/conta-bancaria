package com.contabancaria.api.controllers;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contabancaria.api.dto.CorrentistaPjDTO;
import com.contabancaria.api.entitys.Agencia;
import com.contabancaria.api.entitys.Correntista;
import com.contabancaria.api.enums.TipoConta;
import com.contabancaria.api.services.AgenciaService;
import com.contabancaria.api.services.CorrentistaService;
import com.contabancaria.api.utils.Response;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/correntista/pj")
public class CorrentistaPjController {
	
	private final Logger log = LoggerFactory.getLogger(CorrentistaPjController.class);
	
	@Autowired
	private CorrentistaService correntistaService;
	
	@Autowired
	private AgenciaService agenciaService;
	
	@PostMapping
	public ResponseEntity<Response<CorrentistaPjDTO>> cadastrar(@Valid @RequestBody CorrentistaPjDTO correntistaPjDTO,
			BindingResult bindingResult) throws NoSuchAlgorithmException {
		log.info("CADASTRANDO CORRENTISTA DO TIPO PJ: {}", correntistaPjDTO.toString());
		
		Response<CorrentistaPjDTO> response = new Response<CorrentistaPjDTO>();
		
		validarDados(correntistaPjDTO, bindingResult);
		
		Correntista correntista = this.convertDTOcerrentista(correntistaPjDTO, bindingResult);
		
		if(bindingResult.hasErrors()) {
			log.info("NÃO FOI POSSIVEL SALVAR CORRENTISTA: {} ", correntistaPjDTO.toString());
			bindingResult.getAllErrors().forEach(erro -> response.getErros().add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		Optional<Agencia> ag = this.agenciaService.buscarPorCnpj(correntistaPjDTO.getCnpjBanco());
		ag.ifPresent(age -> correntista.setAgencia(age));
		
		this.correntistaService.persistir(correntista);
		
		response.setData(this.convertCorrentistaDTO(correntista));
		
		return ResponseEntity.ok(response);
	}

	CorrentistaPjDTO convertCorrentistaDTO(Correntista correntista) {
		CorrentistaPjDTO correntistaPjDTO = new CorrentistaPjDTO();
		
		correntistaPjDTO.setId(correntista.getId());
		correntistaPjDTO.setNome(correntista.getNome());
		correntistaPjDTO.setCpf(correntista.getCpf());
		correntistaPjDTO.setCnpj(correntista.getCnpj());
		correntistaPjDTO.setCnpjBanco(correntista.getAgencia().getCnpj());
		correntistaPjDTO.setSaldo(correntista.getSaldo().toString());
		correntistaPjDTO.setEmail(correntista.getEmail());
		
		return correntistaPjDTO;
	}

	Correntista convertDTOcerrentista(@Valid CorrentistaPjDTO correntistaPjDTO, BindingResult bindingResult) {
		Correntista correntista = new Correntista();
		
		correntista.setNome(correntistaPjDTO.getNome());
		correntista.setCpf(correntistaPjDTO.getCpf());
		correntista.setCnpj(correntistaPjDTO.getCnpj());
		correntista.setEmail(correntistaPjDTO.getEmail());
		correntista.setTipoEnum(TipoConta.CONTA_JURIDICA);
		
		if(correntistaPjDTO.getSaldo() != null && correntistaPjDTO.getSaldo() != "") {
			correntista.setSaldo(new BigDecimal(correntistaPjDTO.getSaldo()));
		}
		
		
		return correntista;
	}

	void validarDados(@Valid CorrentistaPjDTO correntistaPjDTO, BindingResult bindingResult) {
		Optional<Agencia> agencia = this.agenciaService.buscarPorCnpj(correntistaPjDTO.getCnpjBanco());
		if(!agencia.isPresent()) {
			bindingResult.addError(new ObjectError("agencia", "Agencia não localizada ou inexistente para esse CNPJ."));
		}
		
		this.correntistaService.buscarPorCpf(correntistaPjDTO.getCpf()).ifPresent(
				erro -> bindingResult.addError(new ObjectError("correntista", "Correntista ja existe com esse CPF.")));
		
		this.correntistaService.buscarPorEmail(correntistaPjDTO.getEmail()).ifPresent(
				erro -> bindingResult.addError(new ObjectError("email", "Corretista ja existe com esse EMAIL.")));
		
		this.correntistaService.buscarPorCnpj(correntistaPjDTO.getCnpj()).ifPresent(
				err -> bindingResult.addError(new ObjectError("cnpj", "Correntista ja existe com esse CNPJ.")));
	}

}
