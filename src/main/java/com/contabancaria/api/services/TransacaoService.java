package com.contabancaria.api.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.contabancaria.api.entitys.Transacao;

public interface TransacaoService {

	Transacao persistir(Transacao transacao);
	
	Page<Transacao> pageTransacao(Long transacaoId, Pageable pageable);
	
	Optional<Transacao> buscaPorId(Long id);
	
	void deletePorId(Long id);
}
