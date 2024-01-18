package com.contabancaria.api.dto;


public class CorrentistaDTO {

	private String nome;
	private String cpf;
	private String cnpj;
	private String email;
	private String saldo;
	private String cnpjBanco;
	private AgenciaDTO agencia;
	
	public CorrentistaDTO() {
	}

	public String getNome() {
		return nome;
	}

	public String getCpf() {
		return cpf;
	}

	public String getCnpj() {
		return cnpj;
	}

	public String getEmail() {
		return email;
	}

	public String getSaldo() {
		return saldo;
	}

	public String getCnpjBanco() {
		return cnpjBanco;
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

	public AgenciaDTO getAgenciaDTO() {
		return agencia;
	}

	public void setAgenciaDTO(AgenciaDTO agenciaDTO) {
		this.agencia = agenciaDTO;
	}

	@Override
	public String toString() {
		return "CorrentistaDTO [nome=" + nome + ", cpf=" + cpf + ", cnpj=" + cnpj + ", email=" + email + ", saldo="
				+ saldo + ", cnpjBanco=" + cnpjBanco + "]";
	}
}
