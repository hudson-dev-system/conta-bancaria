package com.contabancaria.api.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.contabancaria.api.entitys.Agencia;

@Transactional(readOnly = true)
public interface AgenciaRepository extends JpaRepository<Agencia, Long>{
	Agencia findByCnpj(String cnpj);
}
