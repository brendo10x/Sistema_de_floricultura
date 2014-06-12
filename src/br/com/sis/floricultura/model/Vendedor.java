/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.sis.floricultura.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.caelum.stella.bean.validation.CPF;

//http://stella.caelum.com.br/ valida cpf
//donwload https://github.com/caelum/caelum-stella/wiki/Downloads-do-caelum-stella
//http://docs.jboss.org/hibernate/validator/4.0.1/reference/en/html_single/

@Entity
@Table(name = "vendedor")
@NamedQueries({
		@NamedQuery(name = "Vendedor.usuarioUsuId", query = "SELECT v FROM Vendedor v WHERE v.usuarioUsuId IN(:IdUsuario)"),
		@NamedQuery(name = "Vendedor.numeroVendedores", query = "SELECT COUNT(v.venId) FROM Vendedor v "),
		@NamedQuery(name = "Vendedor.findAllOrderByVenIdDesc", query = "SELECT v FROM Vendedor v ORDER BY v.venId DESC"),
		@NamedQuery(name = "Vendedor.findAllNotInVenId", query = "SELECT v FROM Vendedor v WHERE v.venId NOT IN (:venId)"),
		@NamedQuery(name = "Vendedor.findAllVenCpf", query = "SELECT v FROM Vendedor v WHERE v.venCpf = :venCpf"),
		@NamedQuery(name = "Vendedor.findAllVenRg", query = "SELECT v FROM Vendedor v WHERE v.venRg = :venRg") })
public class Vendedor implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ven_id")
	private Integer venId;
	@Basic(optional = false)
	@Column(name = "ven_nome")
	private String venNome;
	@Basic(optional = false)
	@Column(name = "ven_cpf")
	private String venCpf;
	@Column(name = "ven_rg")
	private String venRg;
	@Basic(optional = false)
	@Column(name = "usuario_usu_id")
	private int usuarioUsuId;
	@JoinColumn(name = "endereco_end_id", referencedColumnName = "end_id")
	@ManyToOne(optional = false)
	private Endereco enderecoEndId;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "vendedorVenId")
	private List<Venda> vendaList;

	public Vendedor() {
	}

	public Vendedor(Integer venId) {
		this.venId = venId;
	}

	public Vendedor(Integer venId, String venNome, String venCpf,
			int usuarioUsuId) {
		this.venId = venId;
		this.venNome = venNome;
		this.venCpf = venCpf;
		this.usuarioUsuId = usuarioUsuId;
	}

	public Integer getVenId() {
		return venId;
	}

	public void setVenId(Integer venId) {
		this.venId = venId;
	}

	public String getVenNome() {
		return venNome;
	}

	public void setVenNome(String venNome) {
		this.venNome = venNome;
	}

	public String getVenCpf() {
		return venCpf;
	}

	public void setVenCpf(String venCpf) {
		this.venCpf = venCpf;
	}

	public String getVenRg() {
		return venRg;
	}

	public void setVenRg(String venRg) {
		this.venRg = venRg;
	}

	public int getUsuarioUsuId() {
		return usuarioUsuId;
	}

	public void setUsuarioUsuId(int usuarioUsuId) {
		this.usuarioUsuId = usuarioUsuId;
	}

	public Endereco getEnderecoEndId() {
		return enderecoEndId;
	}

	public void setEnderecoEndId(Endereco enderecoEndId) {
		this.enderecoEndId = enderecoEndId;
	}

	public List<Venda> getVendaList() {
		return vendaList;
	}

	public void setVendaList(List<Venda> vendaList) {
		this.vendaList = vendaList;
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
		if (!(object instanceof Vendedor)) {
			return false;
		}
		Vendedor other = (Vendedor) object;
		if ((this.venId == null && other.venId != null)
				|| (this.venId != null && !this.venId.equals(other.venId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "projeto.Vendedor[ venId=" + venId + " ]";
	}

}
