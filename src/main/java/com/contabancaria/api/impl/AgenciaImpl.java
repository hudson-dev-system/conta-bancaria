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
}
