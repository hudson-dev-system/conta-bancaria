package com.contabancaria.api.controllers;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
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

import com.contabancaria.api.dto.CorrentistaDTO;
import com.contabancaria.api.entitys.Agencia;
import com.contabancaria.api.entitys.Correntista;
import com.contabancaria.api.enums.TipoConta;
import com.contabancaria.api.services.AgenciaService;
import com.contabancaria.api.services.CorrentistaService;
import com.contabancaria.api.utils.Response;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/correntista")
public class CorrentistaPfController {

	private final Logger log = LoggerFactory.getLogger(CorrentistaPfController.class);
	
	@Autowired
	private CorrentistaService correntistaService;
	
	@Autowired
	private AgenciaService agenciaService;
	
	@PostMapping(path = "/pf")
	public ResponseEntity<Response<CorrentistaDTO>> cadastro(@Valid @RequestBody CorrentistaDTO correntistaDTO,
			BindingResult bindingResult) throws NoSuchElementException{
		
		log.info("CADASTRANDO CORRENTISTA: {} ", correntistaDTO.toString());
		Response<CorrentistaDTO> response = new Response<CorrentistaDTO>();
		
		this.validationDados(correntistaDTO, bindingResult);
		
		Correntista correntista = this.converteDTOcorrentista(correntistaDTO, bindingResult);
		
		if(bindingResult.hasErrors()) {
			log.info("ERRO AO CADASTRAR CORRENTISTA: {} ", correntista.toString());
			bindingResult.getAllErrors().forEach(erro -> response.getErros().add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		Optional<Agencia> age = this.agenciaService.byId(correntistaDTO.getAgenciaId());
		age.ifPresent(agen -> correntista.setAgencia(agen));
		
		Correntista corr = this.correntistaService.persistir(correntista);
		response.setData(this.convertCorrentistaDTO(corr));
		
		return ResponseEntity.ok(response);
	}

	CorrentistaDTO convertCorrentistaDTO(Correntista correntista) {
		CorrentistaDTO correntistaDTO = new CorrentistaDTO();
		
		correntistaDTO.setId(correntista.getId());
		correntistaDTO.setNome(correntista.getNome());
		correntistaDTO.setCpf(correntista.getCpf());
		correntistaDTO.setEmail(correntista.getEmail());
		
		correntistaDTO.setSaldo(correntista.getSaldo().toString());
		
		correntistaDTO.setAgenciaId(correntista.getAgencia().getId());
		
		return correntistaDTO;
	}

	Correntista converteDTOcorrentista(CorrentistaDTO correntistaDTO, BindingResult bindingResult) {
		Correntista correntista = new Correntista();
		
		correntista.setTipoEnum(TipoConta.CONTA_FISICA);
		correntista.setNome(correntistaDTO.getNome());
		correntista.setCpf(correntistaDTO.getCpf());
		correntista.setEmail(correntistaDTO.getEmail());
		
		if(correntistaDTO.getSaldo() != null && correntistaDTO.getSaldo() != "") {
			correntista.setSaldo(new BigDecimal(correntistaDTO.getSaldo()));
		}
		
		return correntista;
	}

	void validationDados(CorrentistaDTO correntistaDTO, BindingResult bindingResult) throws NoSuchElementException{
		
		this.correntistaService.buscarPorCpf(correntistaDTO.getCpf()).ifPresent(
				erroCpf -> bindingResult.addError(new ObjectError("correntista", "Jã existe correntista com o cpf informado.")));
		
		this.correntistaService.buscarPorEmail(correntistaDTO.getEmail()).ifPresent(
				erroEmail -> bindingResult.addError(new ObjectError("correntista", "Jã existe correntista com o email informado.")));
		
		if(correntistaDTO.getAgenciaId() == null || correntistaDTO.getSaldo() == null) {
			bindingResult.addError(new ObjectError("agencia", "Os campos agenciaId e/ou saldo não foram localizados."));
			return;
		}
		
		Boolean agencia = this.agenciaService.buscarBooleanPorId(correntistaDTO.getAgenciaId());
		if(!agencia) {
			bindingResult.addError(new ObjectError("agenciaErro", "Agencia não localizada com id informado."));
		}
	}
}
