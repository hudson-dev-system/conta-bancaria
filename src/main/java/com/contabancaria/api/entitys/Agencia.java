package com.contabancaria.api.entitys;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "agencia")
public class Agencia implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6201968064394804573L;
	
	private Long id;
	private String cnpj;
	private String endereco;
	private String descricao;
	private Date data_cadastro;
	private Date data_atualizacao;
	private List<Correntista> correntistas;
	
	public Agencia() {}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	@Column(name = "cnpj", nullable = false)
	public String getCnpj() {
		return cnpj;
	}

	@Column(name = "endereco", nullable = false)
	public String getEndereco() {
		return endereco;
	}

	@Column(name = "descricao", nullable = false)
	public String getDescricao() {
		return descricao;
	}

	@Column(name = "data_cadastro", nullable = false)
	public Date getData_cadastro() {
		return data_cadastro;
	}

	@Column(name = "data_atualizacao", nullable = false)
	public Date getData_atualizacao() {
		return data_atualizacao;
	}

	@OneToMany(mappedBy = "agencia", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Correntista> getCorrentistas() {
		return correntistas;
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

	public void setData_cadastro(Date data_cadastro) {
		this.data_cadastro = data_cadastro;
	}

	public void setData_atualizacao(Date data_atualizacao) {
		this.data_atualizacao = data_atualizacao;
	}

	public void setCorrentistas(List<Correntista> correntistas) {
		this.correntistas = correntistas;
	}

	@Override
	public String toString() {
		return "Agencia [id=" + id + ", cnpj=" + cnpj + ", endereco=" + endereco + ", descricao=" + descricao
				+ ", data_cadastro=" + data_cadastro + ", data_atualizacao=" + data_atualizacao + ", correntistas="
				+ correntistas + "]";
	}

	@PreUpdate
	public void preUpdate() {
		this.data_atualizacao = new Date();
	}
	
	@PrePersist
	public void prePersist() {
		final Date now = new Date();
		this.data_atualizacao = now;
		this.data_cadastro = now;
	}
}
