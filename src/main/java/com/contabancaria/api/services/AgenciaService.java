package com.contabancaria.api.services;

import java.util.Optional;

import com.contabancaria.api.entitys.Agencia;

public interface AgenciaService {

	Agencia persistir(Agencia agencia);
	
	Optional<Agencia> buscarPorCnpj(String cnpj);
	
	Boolean buscarBooleanPorId(Long id);
	
	Optional<Agencia> byId(Long id);
	
	void delete(Long id);
}
