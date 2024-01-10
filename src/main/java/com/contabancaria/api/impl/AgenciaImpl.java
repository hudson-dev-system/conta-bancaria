package com.contabancaria.api.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contabancaria.api.entitys.Agencia;
import com.contabancaria.api.repositorys.AgenciaRepository;
import com.contabancaria.api.services.AgenciaService;

@Service
public class AgenciaImpl implements AgenciaService{

	private final Logger log = LoggerFactory.getLogger(AgenciaImpl.class);
	
	@Autowired
	private AgenciaRepository agenciaRepository;
	
	@Override
	public Optional<Agencia> buscarPorCnpj(String cnpj) {
		log.info("BUSCANDO AGENCIA PELO CNPJ {}:", cnpj);
		return Optional.ofNullable(this.agenciaRepository.findByCnpj(cnpj));
	}

	@Override
	public Agencia persistir(Agencia agencia) {
		log.info("SALVANDO AGENCIA {} ", agencia.toString());
		return this.agenciaRepository.save(agencia);
	}

	@Override
	public Boolean buscarBooleanPorId(Long id) {
		log.info("BUSCANDO AGENCIA BOOLEAN PELO ID {} ", id);
		return this.agenciaRepository.existsById(id);
	}

	@Override
	public Optional<Agencia> byId(Long id) {
		log.info("BUSCANDO AGENCIA PELO ID {} ", id);
		return Optional.ofNullable(this.agenciaRepository.findById(id).get());
	}
}
