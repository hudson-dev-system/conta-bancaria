package com.contabancaria.api.controllers;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.apache.commons.lang3.EnumUtils;
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
import com.contabancaria.api.dto.CorrentistaDTO;
import com.contabancaria.api.dto.TransacaoDTO;
import com.contabancaria.api.entitys.Correntista;
import com.contabancaria.api.entitys.Transacao;
import com.contabancaria.api.enums.TipoTransacao;
import com.contabancaria.api.services.CorrentistaService;
import com.contabancaria.api.services.TransacaoService;
import com.contabancaria.api.utils.Response;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/transacao")
public class TransacaoController {
	
	private final Logger log = LoggerFactory.getLogger(TransacaoController.class);
	
	@Autowired
	private TransacaoService transacaoService;
	
	@Autowired
	private CorrentistaService correntistaService;
	
	@PostMapping
	public ResponseEntity<Response<TransacaoDTO>> cadastrar(@Valid @RequestBody TransacaoDTO transacaoDTO,
			BindingResult bindingResult) throws NoSuchAlgorithmException{
		
		log.info("CADASTRANDO TRANSAÇÃO: {} ",transacaoDTO.toString());
		
		Response<TransacaoDTO> response = new Response<TransacaoDTO>();
		
		this.validarCorrentista(transacaoDTO, bindingResult);
		
		Correntista corr = this.atualizarCorrentista(transacaoDTO, bindingResult);
		
		Transacao transacao = this.converteDTOtransacao(corr, transacaoDTO, bindingResult);
		
		CorrentistaDTO correntistaDTO = this.convertCorrDTO(corr, bindingResult);
		
		AgenciaDTO agenciaDTO = this.convertAgenDTO(corr, bindingResult);
		
		if(bindingResult.hasErrors()) {
			log.info("ERROS AO CADASTRAR TRANSACAO.");
			bindingResult.getAllErrors().forEach(erro -> response.getErros().add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		transacao.setCorrentista(corr);
		
		this.transacaoService.persistir(transacao);
		TransacaoDTO Tdto = this.convertTransacDTO(transacao);
		
		correntistaDTO.setAgenciaDTO(agenciaDTO);
		
		Tdto.setCorrentista(correntistaDTO);
		
		response.setData(Tdto);
		
		
		return ResponseEntity.ok(response);
	}

	AgenciaDTO convertAgenDTO(Correntista corr, BindingResult bindingResult) {
		AgenciaDTO agenciaDTO = new AgenciaDTO();
		
		agenciaDTO.setId(corr.getAgencia().getId());
		agenciaDTO.setDescricao(corr.getAgencia().getDescricao());
		agenciaDTO.setEndereco(corr.getAgencia().getEndereco());
		agenciaDTO.setCnpj(corr.getAgencia().getCnpj());
		
		return agenciaDTO;
	}

	CorrentistaDTO convertCorrDTO(Correntista corr, BindingResult bindingResult) {
		CorrentistaDTO correntistaDTO = new CorrentistaDTO();
		
		correntistaDTO.setNome(corr.getNome());
		correntistaDTO.setCpf(corr.getCpf());
		corr.getCnpjOpt().ifPresent(
				cnpj -> correntistaDTO.setCnpj(cnpj));
		correntistaDTO.setEmail(corr.getEmail());
		correntistaDTO.setSaldo(corr.getSaldo().toString());
		correntistaDTO.setCnpjBanco(corr.getAgencia().getCnpj());
		
		return correntistaDTO;
	}

	TransacaoDTO convertTransacDTO(Transacao transacao) {
		
		TransacaoDTO dto = new TransacaoDTO();
		
		dto.setId(Optional.of(transacao.getId()));
		dto.setTipo(transacao.getTipoTransacao().toString());
		dto.setValor(transacao.getValor().toString());
		dto.setIdCorrentista(transacao.getCorrentista().getId());
		
		return dto;
	}

	void validarCorrentista(@Valid TransacaoDTO transacaoDTO, BindingResult bindingResult) {
		if(transacaoDTO.getIdCorrentista() == null) {
			bindingResult.addError(new ObjectError("transacao", "O Id correntista não pode ser nulo."));
			return;
		}
		
		log.info("BUSCANDO CORRENTISTA PELO ID: {} ", transacaoDTO.getIdCorrentista());
		Boolean correntista = this.correntistaService.buscarPorIdOpt(transacaoDTO.getIdCorrentista());
		if(!correntista) {
			bindingResult.addError(new ObjectError("correntista", "Correntista nã localizado."));
		}
	}

	Transacao converteDTOtransacao(Correntista correntista, TransacaoDTO transacaoDTO, BindingResult bindingResult) {

		Transacao transacao = new Transacao();
				
		if(transacaoDTO.getId().isPresent()) {
			Optional<Transacao> tr = this.transacaoService.buscaPorId(transacaoDTO.getId().get());
			if(tr.isPresent()) {
				transacao = tr.get();
			} else {
				bindingResult.addError(new ObjectError("trans", "Transacao não localizada com esse id."));
			}
		} else {
			transacao.setCorrentista(new Correntista());
			transacao.getCorrentista().setId(correntista.getId());
		}
		
		if(EnumUtils.isValidEnum(TipoTransacao.class, transacaoDTO.getTipo())) {
			transacao.setTipoTransacao(TipoTransacao.valueOf(transacaoDTO.getTipo()));
		} else {
			bindingResult.addError(new ObjectError("tipo", "Tipo transacao invalido."));
		}
		
		if(!transacaoDTO.getValor().isEmpty() && transacaoDTO.getValor() != null) {
			transacao.setValor(new BigDecimal(transacaoDTO.getValor()));
		}
		
		return transacao;
	}

	@SuppressWarnings("deprecation")
	Correntista atualizarCorrentista(@Valid TransacaoDTO transacaoDTO, BindingResult bindingResult) {
		
		Correntista correntista = this.correntistaService.buscarPorId(transacaoDTO.getIdCorrentista());
		
		BigDecimal valorDigitado = new BigDecimal(transacaoDTO.getValor());
		BigDecimal valorSaldo = new BigDecimal(correntista.getSaldo().floatValue());
		
		valorDigitado = valorDigitado.setScale(2, 1);

		if(transacaoDTO.getTipo().equals(TipoTransacao.SAQUE.toString())) {
			if(valorSaldo.floatValue() < valorDigitado.floatValue()) {
				bindingResult.addError(new ObjectError("valor", "Saque maior que saldo."));
			}
			
			correntista.setSaldo(new BigDecimal(valorSaldo.floatValue() - valorDigitado.floatValue()));
		}
		
		if(transacaoDTO.getTipo().equals(TipoTransacao.DEPOSITO.toString())) {
			correntista.setSaldo(new BigDecimal(valorSaldo.floatValue() + valorDigitado.floatValue()));
		}
		
		return correntista;
	}
}
