package com.contabancaria.api.repositories;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import com.contabancaria.api.entitys.Agencia;
import com.contabancaria.api.entitys.Correntista;
import com.contabancaria.api.enums.TipoConta;
import com.contabancaria.api.repositorys.AgenciaRepository;
import com.contabancaria.api.repositorys.CorrentistaRepository;

@SpringBootTest
@Profile("test")
public class CorrentistaRepositoryTest {

	private final String CPF = "09851817619";
	private final String EMAIL = "hudsonsdi@gmail.com";
		
	@Autowired
	private CorrentistaRepository correntistaRepository;
	
	@Autowired
	private AgenciaRepository agenciaRepository;
	
	@BeforeEach
	public void setUp() throws Exception{
		Agencia agencia = this.agenciaRepository.save(obterDadosAgencia());
		this.correntistaRepository.save(obterDadosCorrentista(agencia));
	}
	
	@AfterEach
	public final void tearDown() {
		this.correntistaRepository.deleteAll();
	}
	
	@Test
	public void testBuscarCpfCorrentista() {
		Correntista correntista = this.correntistaRepository.findByCpf(CPF);
		
		assertEquals(CPF, correntista.getCpf());
	}
	
	@Test
	public void testBuscarEmailCorretista() {
		Correntista correntista = this.correntistaRepository.findByEmail(EMAIL);
		
		assertEquals(EMAIL, correntista.getEmail());
	}
	
	private Correntista obterDadosCorrentista(Agencia agencia) throws NoSuchAlgorithmException{
		Correntista correntista = new Correntista();
		
		correntista.setNome("Teste Nome");
		correntista.setCpf(CPF);
		correntista.setEmail(EMAIL);
		correntista.setCnpj("22984380000189");
		correntista.setSaldo(new BigDecimal(0.00));
		correntista.setTipoEnum(TipoConta.CONTA_FISICA);
		correntista.setAgencia(agencia);
		
		return correntista;
	}
	
	private Agencia obterDadosAgencia() {
		Agencia agencia = new Agencia();
		
		agencia.setCnpj("22984380000189");
		agencia.setDescricao("Agencia para correntista.");
		agencia.setEndereco("Endereco para correntista.");
		
		return agencia;		
	}
	
}
