package com.contabancaria.api.dto;

import java.util.Optional;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotEmpty;

public class TransacaoDTO {
	
	private Optional<Long> id = Optional.empty();
	private String tipo;
	private String valor;
	private Long idCorrentista;
	
	public TransacaoDTO() {
	}

	public Optional<Long> getId() {
		return id;
	}

	@NotEmpty(message = "O campo TIPO não pode ser vazio.")
	public String getTipo() {
		return tipo;
	}

	@NotEmpty(message = "O campo VALOR não pode ser vazio.")
	@Length(min = 1, max = 19, message = "O campo valor deve conter entre 1 e 19 caracter.")
	@JsonFormat(pattern = "###.00")
	public String getValor() {
		return valor;
	}

	public Long getIdCorrentista() {
		return idCorrentista;
	}

	public void setId(Optional<Long> id) {
		this.id = id;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public void setIdCorrentista(Long idCorrentista) {
		this.idCorrentista = idCorrentista;
	}

	@Override
	public String toString() {
		return "TransacaoDTO [id=" + id + ", tipo=" + tipo + ", valor=" + valor + ", idCorrentista=" + idCorrentista
				+ "]";
	}
	
}
