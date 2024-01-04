package com.contabancaria.api.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.contabancaria.api.entitys.Correntista;

@Transactional(readOnly = true)
public interface CorrentistaRepository extends JpaRepository<Correntista, Long>{

	Correntista findByCpf(String cpf);
	Correntista findByCnpj(String cnpj);
	Correntista findByEmail(String email);
	Correntista fundByCpfOrEmail(String cpf, String email);
}
