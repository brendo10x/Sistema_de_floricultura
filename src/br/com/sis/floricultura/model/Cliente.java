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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.sun.istack.internal.NotNull;

/**
 *
 * @author Brendo
 */
@Entity
@Table(name = "cliente")
@NamedQueries({

	@NamedQuery(name = "Cliente.numeroClientes", query = "SELECT COUNT(c.cliId) FROM Cliente c "),
    @NamedQuery(name = "Cliente.findAllCliRg", query = "SELECT c FROM Cliente c WHERE c.cliRg = :cliRg"),
    @NamedQuery(name = "Cliente.findAllNotInCliId", query = "SELECT c FROM Cliente c WHERE c.cliId NOT IN ( :cliId)"),
    @NamedQuery(name = "Cliente.findAllCliEmail", query = "SELECT c FROM Cliente c WHERE c.cliEmail = :cliEmail"), 
    @NamedQuery(name = "Cliente.findAllOrderByCliIdDesc", query = "SELECT c FROM Cliente c ORDER BY c.cliId DESC")
	
  })
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cli_id")
    private Integer cliId;
    @Basic(optional = false)
    @Column(name = "cli_nome")
    @NotEmpty(message="Nome do cliente é obrigatório!")
    private String cliNome;
    @Basic(optional = false)
    @Column(name = "cli_rg")
    @NotEmpty(message="RG é obrigatório!")
    private String cliRg;
    @Column(name = "cli_email")
    @Email(message="E-mail é inválido!")
    @NotEmpty(message="E-mail do cliente é obrigatório!")
    private String cliEmail;
    @Column(name = "cli_telefone_fixo")
    @NotEmpty(message="Telefone fixo é obrigatório!")
    private String cliTelefoneFixo;
    @Column(name = "cli_telefone_movel")
    @NotEmpty(message="Telefone móvel é obrigatório!")
    private String cliTelefoneMovel;
    //                    cliente fk                            endereço pk
    @JoinColumn(name = "endereco_end_id", referencedColumnName = "end_id")
    @OneToOne(optional = false)
    private Endereco enderecoEndId;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clienteCliId")
    private List<Venda> vendaList;

    public Cliente() {
    }

    public Cliente(Integer cliId) {
        this.cliId = cliId;
    }

    public Cliente(Integer cliId, String cliNome, String cliRg) {
        this.cliId = cliId;
        this.cliNome = cliNome;
        this.cliRg = cliRg;
    }

    public Integer getCliId() {
        return cliId;
    }

    public void setCliId(Integer cliId) {
        this.cliId = cliId;
    }

    public String getCliNome() {
        return cliNome;
    }

    public void setCliNome(String cliNome) {
        this.cliNome = cliNome;
    }

    public String getCliRg() {
        return cliRg;
    }

    public void setCliRg(String cliRg) {
        this.cliRg = cliRg;
    }

    public String getCliEmail() {
        return cliEmail;
    }

    public void setCliEmail(String cliEmail) {
        this.cliEmail = cliEmail;
    }

    public String getCliTelefoneFixo() {
        return cliTelefoneFixo;
    }

    public void setCliTelefoneFixo(String cliTelefoneFixo) {
        this.cliTelefoneFixo = cliTelefoneFixo;
    }

    public String getCliTelefoneMovel() {
        return cliTelefoneMovel;
    }

    public void setCliTelefoneMovel(String cliTelefoneMovel) {
        this.cliTelefoneMovel = cliTelefoneMovel;
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
        hash += (cliId != null ? cliId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.cliId == null && other.cliId != null) || (this.cliId != null && !this.cliId.equals(other.cliId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "projeto.Cliente[ cliId=" + cliId + " ]";
    }
    
}
