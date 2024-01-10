package com.contabancaria.api.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;

import jakarta.validation.constraints.NotEmpty;

public class AgenciaDTO {

	private Long id;
	private String cnpj;
	private String endereco;
	private String descricao;
	
	public AgenciaDTO() {
	}

	public Long getId() {
		return id;
	}

	@NotEmpty(message = "O campo CNPJ não pode estar vazio.")
	@CNPJ(message = "CNPJ Invalido.")
	@Length(min = 5, max = 200, message = "O campo CNPJ deve conter entre 5 e 200 caracterer.")
	public String getCnpj() {
		return cnpj;
	}

	@NotEmpty(message = "O campo ENDEREÇO não pode estar vazio.")
	@Length(min = 5, max = 200, message = "O campo ENDEREÇO deve conter entre 5 e 200 caracterer.")
	public String getEndereco() {
		return endereco;
	}

	@NotEmpty(message = "O campo DESCRIÇÃO não pode estar vazio.")
	@Length(min = 5, max = 200, message = "O campo DESCRIÇÃO deve conter entre 5 e 200 caracterer.")
	public String getDescricao() {
		return descricao;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return "AgenciaDTO [id=" + id + ", cnpj=" + cnpj + ", endereco=" + endereco + ", descricao=" + descricao + "]";
	}
}
