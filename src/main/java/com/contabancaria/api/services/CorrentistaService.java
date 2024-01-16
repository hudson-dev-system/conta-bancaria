package com.contabancaria.api.services;

import java.util.Optional;

import com.contabancaria.api.entitys.Correntista;

public interface CorrentistaService {

	Correntista persistir(Correntista correntista);
	
	Optional<Correntista> buscarPorCpf(String cpf);
	
	Optional<Correntista> buscarPorCnpj(String cnpj);
	
	Optional<Correntista> buscarPorEmail(String email);
	
	Optional<Correntista> buscarPorCpfOuEmail(String cpf, String email);
	
	Correntista buscarPorId(Long id);

	Boolean buscarPorIdOpt(Long idCorrentista);
}
