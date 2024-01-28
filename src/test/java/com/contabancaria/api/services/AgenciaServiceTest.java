package com.contabancaria.api.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;

import com.contabancaria.api.entitys.Agencia;
import com.contabancaria.api.repositorys.AgenciaRepository;

@SpringBootTest
@Profile("test")
public class AgenciaServiceTest {
	
	@MockBean
	private AgenciaRepository agenciaRepository;
	
	@Autowired
	private AgenciaService agenciaService;

	private static final String CNPJ = "36885247000180";
	
	@BeforeEach
	public void setUp() throws Exception{
		BDDMockito.given(this.agenciaRepository.findByCnpj(Mockito.anyString())).willReturn(new Agencia());
		BDDMockito.given(this.agenciaRepository.save(Mockito.any(Agencia.class))).willReturn(new Agencia());
	}
	
	@Test
	public void testBuscarAgenciaCnpj() {
		Optional<Agencia> agOpt = this.agenciaService.buscarPorCnpj(CNPJ);
		
		assertTrue(agOpt.isPresent());
	}
	
	@Test
	public void testPersistAgencia() {
		Agencia ag = this.agenciaService.persistir(new Agencia());
		
		assertNotNull(ag);
	}
}
