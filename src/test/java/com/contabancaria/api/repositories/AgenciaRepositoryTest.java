package com.contabancaria.api.repositories;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.contabancaria.api.entitys.Agencia;
import com.contabancaria.api.repositorys.AgenciaRepository;

@SpringBootTest
@ActiveProfiles("test")
public class AgenciaRepositoryTest {
	
	@Autowired
	private AgenciaRepository agenciaRepository;
	
	private static final String CNPJ = "22984380000189";
	
	@Test
	public void agenciaInsert() {
		Agencia agencia = new Agencia();
		
		agencia.setCnpj(CNPJ);
		agencia.setDescricao("Teste");
		agencia.setEndereco("Endereco de teste.");
		
		this.agenciaRepository.save(agencia);
	}
	
	@Test
	public void buscarCnpj() {
		Agencia agencia = this.agenciaRepository.findByCnpj(CNPJ);
		
		assertEquals(CNPJ, agencia.getCnpj());
	}

	@Test
	public void buscarCnpjnotNull() {
		Agencia agencia = this.agenciaRepository.findByCnpj(CNPJ);
		
		assertNotNull(agencia);
	}
	
}
