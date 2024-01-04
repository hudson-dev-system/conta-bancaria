package com.contabancaria.api.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contabancaria.api.entitys.Correntista;
import com.contabancaria.api.repositorys.CorrentistaRepository;
import com.contabancaria.api.services.CorrentistaService;

@Service
public class CorrentistaImpl implements CorrentistaService{

	private final Logger log = LoggerFactory.getLogger(CorrentistaImpl.class);
	
	@Autowired
	private CorrentistaRepository correntistaRepository;
	
	@Override
	public Correntista persistir(Correntista correntista) {
		log.info("SALVANDO O CORRENTISTA {}:", correntista.toString());
		return this.correntistaRepository.save(correntista);
	}

	@Override
	public Optional<Correntista> buscarPorCpf(String cpf) {
		log.info("BUSCANDO CORRENTISTA PELO CPF {}:", cpf);
		return Optional.ofNullable(this.correntistaRepository.findByCpf(cpf));
	}

	@Override
	public Optional<Correntista> buscarPorCnpj(String cnpj) {
		log.info("BUSCANDO CORRENTISTA PELO CNPJ {}:", cnpj);
		return Optional.ofNullable(this.correntistaRepository.findByCnpj(cnpj));
	}

	@Override
	public Optional<Correntista> buscarPorEmail(String email) {
		log.info("BUSCANDO CORRENTISTA PELO EMAIL {}:", email);
		return Optional.ofNullable(this.correntistaRepository.findByEmail(email));
	}

	@Override
	public Optional<Correntista> buscarPorCpfOuEmail(String cpf, String email) {
		log.info("BUSCANDO CORRENTISTA PELO CPF OU EMAIL {} {}:", cpf, email);
		return Optional.ofNullable(this.correntistaRepository.fundByCpfOrEmail(cpf, email));
	}
}
