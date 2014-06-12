package br.com.sis.floricultura.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "proprietario")
@NamedQueries({ @NamedQuery(name = "Proprietario.usuarioId", query = "SELECT p FROM Proprietario p WHERE p.usuarioId IN(:idUsuario) "),

})
public class Proprietario implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pro_id")
	private Integer proId;
	@Column(name = "pro_nome")
	private String proNome;
	@Column(name = "usuario_usu_id")
	private Integer usuarioId;

	public Proprietario() {
	}

	public Proprietario(Integer proId) {
		this.proId = proId;

	}

	public Proprietario(Integer proId, Integer usuarioId) {
		this.proId = proId;
		this.usuarioId = usuarioId;
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

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
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
		if (!(object instanceof Proprietario)) {
			return false;
		}
		Proprietario other = (Proprietario) object;
		if ((this.proId == null && other.proId != null)
				|| (this.proId != null && !this.proId.equals(other.proId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "br.com.sis.floricultura.modelo.Proprietario[ proId=" + proId
				+ " ]";
	}

}
