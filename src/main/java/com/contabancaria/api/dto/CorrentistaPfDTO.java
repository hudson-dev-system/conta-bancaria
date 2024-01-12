package com.contabancaria.api.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class CorrentistaPfDTO {
	
	private Long id;
	private String nome;
	private String cpf;
	private String email;
	private String saldo;
	private Long agenciaId;
	
	public CorrentistaPfDTO() {
	}

	public Long getId() {
		return id;
	}

	@NotEmpty(message = "O campo nome não pode ser vazio.")
	@Length(min = 2, max = 200, message = "O campo nome deve ter entre 2 e 200 caracter.")
	public String getNome() {
		return nome;
	}

	@CPF(message = "CPF invalido.")
	@NotEmpty(message = "O campo cpf não pode ser vazio.")
	@Length(min = 11, max = 11, message = "O campo cpf deve ter 11 caracter.")
	public String getCpf() {
		return cpf;
	}

	@Email(message = "Email invalido.")
	@NotEmpty(message = "O campo email não pode ser vazio.")
	@Length(min = 5, max = 200, message = "O campo email deve ter entre 5 e 200 caracter.")
	public String getEmail() {
		return email;
	}

	@NotEmpty(message = "O campo saldo deve ser pelo menos 0.")
	public String getSaldo() {
		return saldo;
	}

	public Long getAgenciaId() {
		return agenciaId;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}

	public void setAgenciaId(Long agenciaId) {
		this.agenciaId = agenciaId;
	}

	@Override
	public String toString() {
		return "CorrentistaDTO [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", email=" + email + ", saldo=" + saldo
				+ ", agenciaId=" + agenciaId + "]";
	}

}
