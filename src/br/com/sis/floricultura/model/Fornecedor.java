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
@Table(name = "fornecedor")
@NamedQueries({
	 @NamedQuery(name = "Fornecedor.numeroFornecedores", query = "SELECT COUNT(f.forId) FROM Fornecedor f"),
    @NamedQuery(name = "Fornecedor.findAll", query = "SELECT f FROM Fornecedor f"),
    @NamedQuery(name = "Fornecedor.buscarTotalRegistros", query = "SELECT COUNT(f.forId) AS TOTAL FROM Fornecedor f WHERE f.forNome like :consulta "),
    @NamedQuery(name = "Fornecedor.findAllOrderByForIdDesc", query = "SELECT f FROM Fornecedor f ORDER BY forId DESC"),
    @NamedQuery(name = "Fornecedor.findByForId", query = "SELECT f FROM Fornecedor f WHERE f.forId = :forId"),
    @NamedQuery(name = "Fornecedor.findByForNome", query = "SELECT f FROM Fornecedor f WHERE f.forNome = :forNome")})
public class Fornecedor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "for_id")
    private Integer forId;
    @Column(name = "for_nome")
    
    private String forNome;
    @JoinColumn(name = "endereco_end_id", referencedColumnName = "end_id")
    @OneToOne(optional = false)
    private Endereco enderecoEndId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fornecedorForId")
    private List<Produtos> produtosList;

    public Fornecedor() {
    }

    public Fornecedor(Integer forId) {
        this.forId = forId;
    }

    public Integer getForId() {
        return forId;
    }

    public void setForId(Integer forId) {
        this.forId = forId;
    }

    public String getForNome() {
        return forNome;
    }

    public void setForNome(String forNome) {
        this.forNome = forNome;
    }

    public Endereco getEnderecoEndId() {
        return enderecoEndId;
    }

    public void setEnderecoEndId(Endereco enderecoEndId) {
        this.enderecoEndId = enderecoEndId;
    }

    public List<Produtos> getProdutosList() {
        return produtosList;
    }

    public void setProdutosList(List<Produtos> produtosList) {
        this.produtosList = produtosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (forId != null ? forId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fornecedor)) {
            return false;
        }
        Fornecedor other = (Fornecedor) object;
        if ((this.forId == null && other.forId != null) || (this.forId != null && !this.forId.equals(other.forId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "projeto.Fornecedor[ forId=" + forId + ", forNome="+ forNome +" ]";
    }
    
}
