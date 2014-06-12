/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.sis.floricultura.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.sis.floricultura.enums.TipoUsuario;

/**
 * 
 * @author Brendo
 * 
 */
@Entity
@Table(name = "usuario")
@NamedQueries({
		@NamedQuery(name = "Usuario.numeroUsuarios", query = "SELECT COUNT(u.usuId) FROM Usuario u "),
		@NamedQuery(name = "Usuario.findAllNotInUsuId", query = "SELECT u FROM Usuario u WHERE u.usuId  NOT IN (:usuId)"),
		@NamedQuery(name = "Usuario.findUsuNome", query = "SELECT u FROM Usuario u WHERE u.usuNome = :usuNome "),
		@NamedQuery(name = "Usuario.findUsuNomeSenha", query = "SELECT u FROM Usuario u WHERE u.usuNome = :usuNome AND u.usuSenha = :usuSenha"),
		@NamedQuery(name = "Usuario.findMaxUsuId", query = "SELECT MAX(u.usuId) FROM Usuario u")
})
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "usu_id")
	private Integer usuId;
	@Column(name = "usu_nome")
	private String usuNome;
	@Column(name = "usu_senha")
	private String usuSenha;
	@Column(name = "usu_tipo")
	@Enumerated(EnumType.ORDINAL)
	private TipoUsuario usuTipo;

	public Usuario() {
	}

	public Usuario(Integer usuId) {
		this.usuId = usuId;
	}

	public Integer getUsuId() {
		return usuId;
	}

	public void setUsuId(Integer usuId) {
		this.usuId = usuId;
	}

	public String getUsuNome() {
		return usuNome;
	}

	public void setUsuNome(String usuNome) {
		this.usuNome = usuNome;
	}

	public String getUsuSenha() {
		return usuSenha;
	}

	public void setUsuSenha(String usuSenha) {
		this.usuSenha = usuSenha;
	}

	public TipoUsuario getUsuTipo() {
		return usuTipo;
	}

	public void setUsuTipo(TipoUsuario vendedor) {
		this.usuTipo = vendedor;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (usuId != null ? usuId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {

		// not set
		if (!(object instanceof Usuario)) {
			return false;
		}
		Usuario other = (Usuario) object;
		if ((this.usuId == null && other.usuId != null)
				|| (this.usuId != null && !this.usuId.equals(other.usuId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "br.com.sis.floricultura.modelo.Usuario[ usuId=" + usuId + " ]";
	}

}
