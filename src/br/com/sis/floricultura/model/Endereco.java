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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Brendo
 */
@Entity
@Table(name = "endereco")
@NamedQueries({
    @NamedQuery(name = "Endereco.findAll", query = "SELECT e FROM Endereco e"),
    @NamedQuery(name = "Endereco.findUltimoEndereco", query = "SELECT MAX(e.endId) FROM Endereco e"),
    @NamedQuery(name = "Endereco.findByEndId", query = "SELECT e FROM Endereco e WHERE e.endId = :endId"),
    @NamedQuery(name = "Endereco.findByEndRua", query = "SELECT e FROM Endereco e WHERE e.endRua = :endRua"),
    @NamedQuery(name = "Endereco.findByEndNumero", query = "SELECT e FROM Endereco e WHERE e.endNumero = :endNumero"),
    @NamedQuery(name = "Endereco.findByEndBairro", query = "SELECT e FROM Endereco e WHERE e.endBairro = :endBairro")})
public class Endereco implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "end_id")
    private Integer endId;
    @Column(name = "end_rua")
    private String endRua;
    @Column(name = "end_numero")
    private String endNumero;
    @Column(name = "end_bairro")
    private String endBairro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "enderecoEndId")
    private List<Fornecedor> fornecedorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "enderecoEndId")
    private List<Vendedor> vendedorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "enderecoEndId")
    private List<Cliente> clienteList;
    @JoinColumn(name = "cidade_cid_id", referencedColumnName = "cid_id")
    @OneToOne(optional = false)
    private Cidade cidadeCidId;

    public Endereco() {
    }

    public Endereco(Integer endId) {
        this.endId = endId;
    }

    public Integer getEndId() {
        return endId;
    }

    public void setEndId(Integer endId) {
        this.endId = endId;
    }

    public String getEndRua() {
        return endRua;
    }

    public void setEndRua(String endRua) {
        this.endRua = endRua;
    }

    public String getEndNumero() {
        return endNumero;
    }

    public void setEndNumero(String endNumero) {
        this.endNumero = endNumero;
    }

    public String getEndBairro() {
        return endBairro;
    }

    public void setEndBairro(String endBairro) {
        this.endBairro = endBairro;
    }

    public List<Fornecedor> getFornecedorList() {
        return fornecedorList;
    }

    public void setFornecedorList(List<Fornecedor> fornecedorList) {
        this.fornecedorList = fornecedorList;
    }

    public List<Vendedor> getVendedorList() {
        return vendedorList;
    }

    public void setVendedorList(List<Vendedor> vendedorList) {
        this.vendedorList = vendedorList;
    }

    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    public Cidade getCidadeCidId() {
        return cidadeCidId;
    }

    public void setCidadeCidId(Cidade cidadeCidId) {
        this.cidadeCidId = cidadeCidId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (endId != null ? endId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Endereco)) {
            return false;
        }
        Endereco other = (Endereco) object;
        if ((this.endId == null && other.endId != null) || (this.endId != null && !this.endId.equals(other.endId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "projeto.Endereco[ endId=" + endId + " ]";
    }
    
}
