/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.sis.floricultura.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.sis.floricultura.enums.TipoProduto;

/**
 * 
 * @author Brendo
 */
@Entity
@Table(name = "produtos")
@NamedQueries({
		@NamedQuery(name = "Produtos.numeroProdutos", query = "SELECT COUNT(p.proId) FROM Produtos p"),
		@NamedQuery(name = "Produtos.findAll", query = "SELECT p FROM Produtos p"),
		@NamedQuery(name = "Produtos.findAllOrderByProIdDesc", query = "SELECT p FROM Produtos p ORDER BY proId DESC"),
		@NamedQuery(name = "Produtos.findByProId", query = "SELECT p FROM Produtos p WHERE p.proId = :proId"),
		@NamedQuery(name = "Produtos.findByProNome", query = "SELECT p FROM Produtos p WHERE p.proNome = :proNome"),
		@NamedQuery(name = "Produtos.findByProPreco", query = "SELECT p FROM Produtos p WHERE p.proPreco = :proPreco"),
		@NamedQuery(name = "Produtos.findByProQuantidade", query = "SELECT p FROM Produtos p WHERE p.proQuantidade = :proQuantidade"),
		@NamedQuery(name = "Produtos.findByProTipo", query = "SELECT p FROM Produtos p WHERE p.proTipo = :proTipo") })
public class Produtos implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "pro_id")
	private Integer proId;
	@Column(name = "pro_nome")
	private String proNome;
	@Column(name = "pro_preco")
	private Double proPreco;
	@Column(name = "pro_quantidade")
	private Integer proQuantidade;
	@Column(name = "pro_tipo")
	@Enumerated(EnumType.ORDINAL)
	private TipoProduto proTipo;
	@JoinColumn(name = "fornecedor_for_id", referencedColumnName = "for_id")
	@OneToOne(optional = false)
	private Fornecedor fornecedorForId;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "produtosProId")
	private List<ProdutosVendidos> produtosVendidosList;

	public Produtos() {
	}

	public Produtos(Integer proId) {
		this.proId = proId;
	}

	public Integer getProId() {
		return proId;
	}

	public void setProId(Integer proId) {
		this.proId = proId;
	}

	public String getProNome() {
		return proNome;
	}

	public void setProNome(String proNome) {
		this.proNome = proNome;
	}

	public Double getProPreco() {
		return proPreco;
	}

	public void setProPreco(Double proPreco) {
		this.proPreco = proPreco;
	}

	public Integer getProQuantidade() {
		return proQuantidade;
	}

	public void setProQuantidade(Integer proQuantidade) {
		this.proQuantidade = proQuantidade;
	}

	public TipoProduto getProTipo() {
		return proTipo;
	}

	public void setProTipo(TipoProduto proTipo) {
		this.proTipo = proTipo;
	}

	public Fornecedor getFornecedorForId() {
		return fornecedorForId;
	}

	public void setFornecedorForId(Fornecedor fornecedorForId) {
		this.fornecedorForId = fornecedorForId;
	}

	public List<ProdutosVendidos> getProdutosVendidosList() {
		return produtosVendidosList;
	}

	public void setProdutosVendidosList(
			List<ProdutosVendidos> produtosVendidosList) {
		this.produtosVendidosList = produtosVendidosList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (proId != null ? proId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Produtos)) {
			return false;
		}
		Produtos other = (Produtos) object;
		if ((this.proId == null && other.proId != null)
				|| (this.proId != null && !this.proId.equals(other.proId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "projeto.Produtos[ proId=" + proId + " ]";
	}

}
