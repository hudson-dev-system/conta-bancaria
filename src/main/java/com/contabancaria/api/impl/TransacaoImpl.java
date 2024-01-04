package com.contabancaria.api.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.contabancaria.api.entitys.Transacao;
import com.contabancaria.api.repositorys.TransacaoRepository;
import com.contabancaria.api.services.TransacaoService;

@Service
public class TransacaoImpl implements TransacaoService{

	private final Logger log = LoggerFactory.getLogger(TransacaoImpl.class);
	
	@Autowired
	private TransacaoRepository transacaoRepository;
	
	@Override
	public Transacao persistir(Transacao transacao) {
		log.info("SALVANDO TRANSACAO {}:", transacao.toString());
		return this.transacaoRepository.save(transacao);
	}

	@Override
	public Page<Transacao> pageTransacao(Long transacaoId, Pageable pageable) {
		log.info("BUSCA PAGINADA DE TRANSACAO:");
		return this.transacaoRepository.findByCorrentistaId(transacaoId, pageable);
	}

	@Override
	public Optional<Transacao> buscaPorId(Long id) {
		log.info("BUSCA DE TRANSACAO POR ID {}: ", id);
		return Optional.ofNullable(this.transacaoRepository.findById(id).get());
	}

	@Override
	public void deletePorId(Long id) {
		log.info("DELETANDO A TRANSACAO DE ID {}:", id);
		this.transacaoRepository.deleteById(id);
	}
}
