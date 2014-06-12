/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.sis.floricultura.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author Brendo
 */
@Entity
@Table(name = "produtos_vendidos")
@NamedQueries({
		@NamedQuery(name = "ProdutosVendidos.findAll", query = "SELECT p FROM ProdutosVendidos p"),
		@NamedQuery(name = "ProdutosVendidos.findByProVendid", query = "SELECT p FROM ProdutosVendidos p WHERE p.proVendid = :proVendid"),
		@NamedQuery(name = "ProdutosVendidos.findProVendidVendaVenId", query = "SELECT p FROM ProdutosVendidos p WHERE p.vendaVenId = :idvenda"),
		@NamedQuery(name = "ProdutosVendidos.findByProVendquantidades", query = "SELECT p FROM ProdutosVendidos p WHERE p.proVendquantidades = :proVendquantidades") })
public class ProdutosVendidos implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "proVend_id")
	private Integer proVendid;
	@Column(name = "proVend_quantidades")
	private Integer proVendquantidades;
	//venda
	@JoinColumn(name = "venda_ven_id", referencedColumnName = "ven_id")
	@OneToOne
	private Venda vendaVenId;
	@JoinColumn(name = "produtos_pro_id", referencedColumnName = "pro_id")
	@OneToOne
	private Produtos produtosProId;

	public ProdutosVendidos() {
	}

	public ProdutosVendidos(Integer proVendid) {
		this.proVendid = proVendid;
	}

	public Integer getProVendid() {
		return proVendid;
	}

	public void setProVendid(Integer proVendid) {
		this.proVendid = proVendid;
	}

	public Integer getProVendquantidades() {
		return proVendquantidades;
	}

	public void setProVendquantidades(Integer proVendquantidades) {
		this.proVendquantidades = proVendquantidades;
	}

	public Venda getVendaVenId() {
		return vendaVenId;
	}

	public void setVendaVenId(Venda vendaVenId) {
		this.vendaVenId = vendaVenId;
	}

	public Produtos getProdutosProId() {
		return produtosProId;
	}

	public void setProdutosProId(Produtos produtosProId) {
		this.produtosProId = produtosProId;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (proVendid != null ? proVendid.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof ProdutosVendidos)) {
			return false;
		}
		ProdutosVendidos other = (ProdutosVendidos) object;
		if ((this.proVendid == null && other.proVendid != null)
				|| (this.proVendid != null && !this.proVendid
						.equals(other.proVendid))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "projeto.ProdutosVendidos[ proVendid=" + proVendid + " ]";
	}

}
