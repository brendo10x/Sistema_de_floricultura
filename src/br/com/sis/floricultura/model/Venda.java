/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.sis.floricultura.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import br.com.sis.floricultura.enums.FormaPagamento;

/**
 * 
 * @author Brendo
 */
@Entity
@Table(name = "venda")
@NamedQueries({
		@NamedQuery(name = "Venda.findAll", query = "SELECT v FROM Venda v"),
		@NamedQuery(name = "Venda.numeroVendas", query = "SELECT COUNT(v.venId) AS numeroVendas FROM Venda v"),
		@NamedQuery(name = "Venda.findAllOrderByVenIdDesc", query = "SELECT v FROM Venda v ORDER BY v.venId DESC"),
		@NamedQuery(name = "Venda.findUltimoVenda", query = "SELECT MAX(v.venId) FROM Venda v"),
		@NamedQuery(name = "Venda.findByVenId", query = "SELECT v FROM Venda v WHERE v.venId = :venId"),
		@NamedQuery(name = "Venda.findByVenDataVenda", query = "SELECT v FROM Venda v WHERE v.venDataVenda = :venDataVenda"),
		@NamedQuery(name = "Venda.findByVenDataVendaPorVendedor", query = "SELECT v FROM Venda v WHERE v.venDataVenda = :venDataVenda AND v.vendedorVenId = :vendedorId "),
		@NamedQuery(name = "Venda.findByVenTotal", query = "SELECT v FROM Venda v WHERE v.venTotal = :venTotal"),
		@NamedQuery(name = "Venda.findByVenFormaPagamento", query = "SELECT v FROM Venda v WHERE v.venFormaPagamento = :venFormaPagamento") })
public class Venda implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ven_id")
	private Integer venId;
	@Column(name = "ven_data_venda")
	@Temporal(TemporalType.DATE)
	private Date venDataVenda;
	@Column(name = "ven_total")
	private Double venTotal;
	@Column(name = "ven_forma_pagamento")
	@Enumerated(EnumType.ORDINAL)
	private FormaPagamento venFormaPagamento;
	@JoinColumn(name = "cliente_cli_id", referencedColumnName = "cli_id")
	@OneToOne(optional = false)
	private Cliente clienteCliId;
	@JoinColumn(name = "vendedor_ven_id", referencedColumnName = "ven_id")
	@OneToOne(optional = false)
	private Vendedor vendedorVenId;

	//produtos vendidos
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "vendaVenId")
	private List<ProdutosVendidos> produtosVendidosList;

	public Venda() {
	}

	public Venda(Integer venId) {
		this.venId = venId;
	}

	public Integer getVenId() {
		return venId;
	}

	public void setVenId(Integer venId) {
		this.venId = venId;
	}

	public Date getVenDataVenda() {
		return venDataVenda;
	}

	public void setVenDataVenda(Date venDataVenda) {
		this.venDataVenda = venDataVenda;
	}

	public Double getVenTotal() {
		return venTotal;
	}

	public void setVenTotal(Double venTotal) {
		this.venTotal = venTotal;
	}

	public FormaPagamento getVenFormaPagamento() {
		return venFormaPagamento;
	}

	public void setVenFormaPagamento(FormaPagamento venFormaPagamento) {
		this.venFormaPagamento = venFormaPagamento;
	}

	public Cliente getClienteCliId() {
		return clienteCliId;
	}

	public void setClienteCliId(Cliente clienteCliId) {
		this.clienteCliId = clienteCliId;
	}

	public Vendedor getVendedorVenId() {
		return vendedorVenId;
	}

	public void setVendedorVenId(Vendedor vendedorVenId) {
		this.vendedorVenId = vendedorVenId;
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
		hash += (venId != null ? venId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Venda)) {
			return false;
		}
		Venda other = (Venda) object;
		if ((this.venId == null && other.venId != null)
				|| (this.venId != null && !this.venId.equals(other.venId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "projeto.Venda[ venId=" + venId + ",venDataVenda="
				+ venDataVenda + " ]";
	}

}
