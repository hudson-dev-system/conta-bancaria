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

import com.contabancaria.api.dto.CorrentistaPfDTO;
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
	public ResponseEntity<Response<CorrentistaPfDTO>> cadastro(@Valid @RequestBody CorrentistaPfDTO correntistaPfDTO,
			BindingResult bindingResult) throws NoSuchElementException{
		
		log.info("CADASTRANDO CORRENTISTA: {} ", correntistaPfDTO.toString());
		Response<CorrentistaPfDTO> response = new Response<CorrentistaPfDTO>();
		
		this.validationDados(correntistaPfDTO, bindingResult);
		
		Correntista correntista = this.converteDTOcorrentista(correntistaPfDTO, bindingResult);
		
		if(bindingResult.hasErrors()) {
			log.info("ERRO AO CADASTRAR CORRENTISTA: {} ", correntista.toString());
			bindingResult.getAllErrors().forEach(erro -> response.getErros().add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		Optional<Agencia> age = this.agenciaService.byId(correntistaPfDTO.getAgenciaId());
		age.ifPresent(agen -> correntista.setAgencia(agen));
		
		Correntista corr = this.correntistaService.persistir(correntista);
		response.setData(this.convertCorrentistaDTO(corr));
		
		return ResponseEntity.ok(response);
	}

	CorrentistaPfDTO convertCorrentistaDTO(Correntista correntista) {
		CorrentistaPfDTO correntistaPfDTO = new CorrentistaPfDTO();
		
		correntistaPfDTO.setId(correntista.getId());
		correntistaPfDTO.setNome(correntista.getNome());
		correntistaPfDTO.setCpf(correntista.getCpf());
		correntistaPfDTO.setEmail(correntista.getEmail());
		
		correntistaPfDTO.setSaldo(correntista.getSaldo().toString());
		
		correntistaPfDTO.setAgenciaId(correntista.getAgencia().getId());
		
		return correntistaPfDTO;
	}

	Correntista converteDTOcorrentista(CorrentistaPfDTO correntistaPfDTO, BindingResult bindingResult) {
		Correntista correntista = new Correntista();
		
		correntista.setTipoEnum(TipoConta.CONTA_FISICA);
		correntista.setNome(correntistaPfDTO.getNome());
		correntista.setCpf(correntistaPfDTO.getCpf());
		correntista.setEmail(correntistaPfDTO.getEmail());
		
		if(correntistaPfDTO.getSaldo() != null && correntistaPfDTO.getSaldo() != "") {
			correntista.setSaldo(new BigDecimal(correntistaPfDTO.getSaldo()));
		}
		
		return correntista;
	}

	void validationDados(CorrentistaPfDTO correntistaPfDTO, BindingResult bindingResult) throws NoSuchElementException{
		
		this.correntistaService.buscarPorCpf(correntistaPfDTO.getCpf()).ifPresent(
				erroCpf -> bindingResult.addError(new ObjectError("correntista", "Jã existe correntista com o cpf informado.")));
		
		this.correntistaService.buscarPorEmail(correntistaPfDTO.getEmail()).ifPresent(
				erroEmail -> bindingResult.addError(new ObjectError("correntista", "Jã existe correntista com o email informado.")));
		
		if(correntistaPfDTO.getAgenciaId() == null || correntistaPfDTO.getSaldo() == null) {
			bindingResult.addError(new ObjectError("agencia", "Os campos agenciaId e/ou saldo não foram localizados."));
			return;
		}
		
		Boolean agencia = this.agenciaService.buscarBooleanPorId(correntistaPfDTO.getAgenciaId());
		if(!agencia) {
			bindingResult.addError(new ObjectError("agenciaErro", "Agencia não localizada com id informado."));
		}
	}
}
