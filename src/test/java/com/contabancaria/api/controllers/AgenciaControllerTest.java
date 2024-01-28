package com.contabancaria.api.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.contabancaria.api.entitys.Agencia;
import com.contabancaria.api.services.AgenciaService;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AgenciaControllerTest {
	
	@MockBean
	private AgenciaService agenciaService;
	
	@Autowired
	MockMvc mockMvc;
	
	private static final String BUSCAR_AGENCIA_CNPJ_URL = "/api/agencia/cnpj/";
	private static final Long ID = Long.valueOf(1);
	private static final String CNPJ = "36885247000180";
	private static final String ENDERECO = "Test endereco.";
	private static final String DESCRICAO = "Test descricao.";
	
	@Test
	public void testBuscarAgenciaCnpjInvalido() throws Exception {
		BDDMockito.given(this.agenciaService.buscarPorCnpj(Mockito.anyString())).willReturn(Optional.empty());
		mockMvc.perform(MockMvcRequestBuilders.get(BUSCAR_AGENCIA_CNPJ_URL + CNPJ)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.erros").value("Agencia não existe com esse cnpj."));
	}
	
	@Test
	public void testBuscarAgenciaCnpjValido() throws Exception {
		BDDMockito.given(this.agenciaService.buscarPorCnpj(Mockito.anyString())).willReturn(Optional.of(this.obterDadosAgencia()));
		mockMvc.perform(MockMvcRequestBuilders.get(BUSCAR_AGENCIA_CNPJ_URL + CNPJ)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.id").value(ID.longValue()))
				.andExpect(jsonPath("$.data.cnpj", equalTo(CNPJ)))
				.andExpect(jsonPath("$.data.endereco", equalTo(ENDERECO)))
				.andExpect(jsonPath("$.data.descricao", equalTo(DESCRICAO)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.erros").isEmpty());
	}

	Agencia obterDadosAgencia() {
		Agencia agencia = new Agencia();
		agencia.setId(ID);
		agencia.setCnpj(CNPJ);
		agencia.setEndereco(ENDERECO);
		agencia.setDescricao(DESCRICAO);
		
		return agencia;
	}

}
