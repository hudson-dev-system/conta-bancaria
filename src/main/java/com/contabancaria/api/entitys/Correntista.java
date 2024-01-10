package com.contabancaria.api.entitys;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.contabancaria.api.enums.TipoConta;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Table(name = "correntista")
@Entity
public class Correntista implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3583318513099306577L;
	
	private Long id;
	private String nome;
	private String cpf;
	private String cnpj;
	private String email;
	private TipoConta tipoConta;
	private BigDecimal saldo;
	private Date data_cadastro;
	private Date data_atualizacao;
	private Agencia agencia;
	private List<Transacao> transacoes;
	
	public Correntista(){}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	@Column(name = "nome", nullable = false)
	public String getNome() {
		return nome;
	}

	@Column(name = "cpf", nullable = false)
	public String getCpf() {
		return cpf;
	}

	@Column(name = "cnpj")
	public String getCnpj() {
		return cnpj;
	}
	
	@Transient
	public Optional<String> getCnpjOpt(){
		return Optional.ofNullable(cnpj);
	}

	@Column(name = "email", nullable = false)
	public String getEmail() {
		return email;
	}

	@Column(name = "tipo", nullable = false)
	@Enumerated(EnumType.STRING)
	public TipoConta getTipoEnum() {
		return tipoConta;
	}

	@Column(name = "saldo", nullable = false)
	public BigDecimal getSaldo() {
		return saldo;
	}

	@Column(name = "data_cadastro", nullable = false)
	public Date getData_cadastro() {
		return data_cadastro;
	}

	@Column(name = "data_atualizacao", nullable = false)
	public Date getData_atualizacao() {
		return data_atualizacao;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	public Agencia getAgencia() {
		return agencia;
	}

	@OneToMany(mappedBy = "correntista", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Transacao> getTransacoes() {
		return transacoes;
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

	public void setTipoEnum(TipoConta tipoConta) {
		this.tipoConta = tipoConta;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public void setData_cadastro(Date data_cadastro) {
		this.data_cadastro = data_cadastro;
	}

	public void setData_atualizacao(Date data_atualizacao) {
		this.data_atualizacao = data_atualizacao;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public void setTransacoes(List<Transacao> transacoes) {
		this.transacoes = transacoes;
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
		return "Correntista [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", cnpj=" + cnpj + ", email=" + email
				+ ", tipoConta=" + tipoConta + ", saldo=" + saldo + ", data_cadastro=" + data_cadastro
				+ ", data_atualizacao=" + data_atualizacao + ", agencia=" + agencia + ", transacoes=" + transacoes
				+ "]";
	}
}
