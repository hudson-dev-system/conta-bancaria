package com.contabancaria.api.controllers;

import java.security.NoSuchAlgorithmException;

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

import com.contabancaria.api.dto.AgenciaDTO;
import com.contabancaria.api.entitys.Agencia;
import com.contabancaria.api.services.AgenciaService;
import com.contabancaria.api.utils.Response;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/agencia")
public class AgenciaController {

	private final Logger log = LoggerFactory.getLogger(AgenciaController.class);
	
	@Autowired
	private AgenciaService agenciaService;
	
	@PostMapping
	public ResponseEntity<Response<AgenciaDTO>> cadastrar(@Valid @RequestBody AgenciaDTO agenciaDTO,
			BindingResult bindingResult) throws NoSuchAlgorithmException{
		
		log.info("CADASTRANDO AGENCIA: {} ", agenciaDTO.toString());
		Response<AgenciaDTO> response = new Response<AgenciaDTO>();
		
		this.validarDados(agenciaDTO, bindingResult);
		
		Agencia agencia = this.convertDTOagencia(agenciaDTO, bindingResult);
		
		if(bindingResult.hasErrors()) {
			log.info("ERRO AO CADASTRAR AGENCIA.");
			bindingResult.getAllErrors().forEach(erro -> response.getErros().add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		Agencia agenciaRet = this.agenciaService.persistir(agencia);
		response.setData(this.convertAgenciaDTO(agenciaRet));
		
		return ResponseEntity.ok(response);
	}

	AgenciaDTO convertAgenciaDTO(Agencia agenciaRet) {
		AgenciaDTO agenciaDTO = new AgenciaDTO();
		
		agenciaDTO.setId(agenciaRet.getId());
		agenciaDTO.setCnpj(agenciaRet.getCnpj());
		agenciaDTO.setDescricao(agenciaRet.getDescricao());
		agenciaDTO.setEndereco(agenciaRet.getEndereco());
		
		return agenciaDTO;
	}

	Agencia convertDTOagencia(@Valid AgenciaDTO agenciaDTO, BindingResult bindingResult) {
		Agencia agencia = new Agencia();
		
		agencia.setCnpj(agenciaDTO.getCnpj());
		agencia.setDescricao(agenciaDTO.getDescricao());
		agencia.setEndereco(agenciaDTO.getEndereco());
		
		return agencia;
	}

	void validarDados(@Valid AgenciaDTO agenciaDTO, BindingResult bindingResult) {

		this.agenciaService.buscarPorCnpj(agenciaDTO.getCnpj()).ifPresent(
				result -> bindingResult.addError(new ObjectError("agencia", "Agencia já existe pare este CNPJ.")));
	}
}
