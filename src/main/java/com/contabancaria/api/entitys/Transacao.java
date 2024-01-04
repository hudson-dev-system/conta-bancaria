package com.contabancaria.api.entitys;

import java.io.Serializable;
import java.util.Date;

import com.contabancaria.api.enums.TipoTransacao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Table(name = "transacao")
@Entity
public class Transacao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5445746018452070375L;

	private Long id;
	private Correntista correntista;
	private TipoTransacao tipoTransacao;
	private Date data_cadastro;
	private Date data_atualizacao;
	
	public Transacao() {}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	@Column(name = "correntista_id", nullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	public Correntista getCorrentista() {
		return correntista;
	}

	@Column(name = "tipo", nullable = false)
	@Enumerated(EnumType.STRING)
	public TipoTransacao getTipoTransacao() {
		return tipoTransacao;
	}

	@Column(name = "data_cadastro", nullable = false)
	public Date getData_cadastro() {
		return data_cadastro;
	}

	@Column(name = "data_atualizacao", nullable = false)
	public Date getData_atualizacao() {
		return data_atualizacao;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCorrentista(Correntista correntista) {
		this.correntista = correntista;
	}

	public void setTipoTransacao(TipoTransacao tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}

	public void setData_cadastro(Date data_cadastro) {
		this.data_cadastro = data_cadastro;
	}

	public void setData_atualizacao(Date data_atualizacao) {
		this.data_atualizacao = data_atualizacao;
	}
	
	@PreUpdate
	public void preUpdate() {
		this.data_atualizacao = new Date();
	}
	
	@PrePersist
	public void prePersist() {
		final Date agora = new Date();
		this.data_cadastro = agora;
		this.data_atualizacao = agora;
	}

	@Override
	public String toString() {
		return "Transacao [id=" + id + ", correntista=" + correntista + ", tipoTransacao=" + tipoTransacao
				+ ", data_cadastro=" + data_cadastro + ", data_atualizacao=" + data_atualizacao + "]";
	}
}
