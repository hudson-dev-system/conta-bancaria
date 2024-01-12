package com.contabancaria.api.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class CorrentistaPjDTO {

	private Long id;
	private String nome;
	private String cpf;
	private String cnpj;
	private String email;
	private String saldo;
	private String cnpjBanco;
	
	public CorrentistaPjDTO() {
	}

	public Long getId() {
		return id;
	}

	@NotEmpty(message = "O campo NOME deve ser preenchido.")
	@Length(min = 5, max = 200, message = "O campo nome deve ter de 5 a 200 caracteres.")
	public String getNome() {
		return nome;
	}

	@CPF(message = "CPF invalido.")
	@NotEmpty(message = "O campo CPF deve ser preenhido.")
	public String getCpf() {
		return cpf;
	}

	@CNPJ(message = "CNPJ invalido.")
	@NotEmpty(message = "O campo CNPJ deve ser preenchido.")
	public String getCnpj() {
		return cnpj;
	}

	@Email(message = "EMAIL invalido.")
	@NotEmpty(message = "O campo EMAIL deve ser preenchido.")
	@Length(min = 5, max = 200, message = "O campos EMAIL deve ter entre 5 e 200 caracteres.")
	public String getEmail() {
		return email;
	}

	@NotEmpty(message = "O campo SALDO deve ser preenchido.")
	@Length(min = 1, max = 19, message = "O campo SALDO deve ter de 1 a 19 caracteres.")
	public String getSaldo() {
		return saldo;
	}

	@CNPJ(message = "CNPJ invalido.")
	public String getCnpjBanco() {
		return cnpjBanco;
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

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}

	public void setCnpjBanco(String cnpjBanco) {
		this.cnpjBanco = cnpjBanco;
	}

	@Override
	public String toString() {
		return "CorrentistaPjDTO [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", cnpj=" + cnpj + ", email=" + email
				+ ", saldo=" + saldo + ", cnpjBanco=" + cnpjBanco + "]";
	}
}
