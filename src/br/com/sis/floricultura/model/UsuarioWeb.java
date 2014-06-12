package br.com.sis.floricultura.model;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import br.com.sis.floricultura.application.ProprietarioApplication;
import br.com.sis.floricultura.application.VendedorApplication;
import br.com.sis.floricultura.dao.ProprietarioDao;
import br.com.sis.floricultura.dao.VendedorDao;
import br.com.sis.floricultura.enums.TipoUsuario;

@Component
@SessionScoped
public class UsuarioWeb {

	// objeto do tipo Usu�rio
	private Usuario logado;

	// que pode ser o id do vendedor ou id do propriet�rio
	private Integer idEntidadeUsuario;

	private ProprietarioApplication ProprietarioApplication;
	private VendedorApplication vendedorApplication;

	public UsuarioWeb() {

		ProprietarioApplication = new ProprietarioApplication();
		vendedorApplication = new VendedorApplication();
	}

	public Integer getIdEntidadeUsuario() {
		return idEntidadeUsuario;
	}

	public void setIdEntidadeUsuario(Integer idEntidadeUsuario) {
		this.idEntidadeUsuario = idEntidadeUsuario;
	}

	// Guarda o objeto Usu�rio
	public void fazerLogin(Usuario usuario) {
		this.logado = usuario;

		if (usuario != null) {

			if (usuario.getUsuTipo().name().equalsIgnoreCase("PROPRIETARIO")) {
				// atualiza nome de usu�rio
				setNome(ProprietarioApplication
						.obterNomeProprietarioUsuarioId(getUsuarioId()));

				// define id da entidade que � propriet�rio
				setIdEntidadeUsuario(ProprietarioApplication
						.obterIdProprietarioUsuarioId(getUsuarioId()));
			}// fim if

			// se usu�rio � um vendedor
			// define nome
			if (usuario.getUsuTipo().name().equalsIgnoreCase("VENDEDOR")) {
				setNome(vendedorApplication
						.obterNomeVendedorUsuarioId(getUsuarioId()));
			}// fim if

		}// fim if
	}

	// recupera informa��es do objeto
	// como id
	public Integer getUsuarioId() {
		return logado.getUsuId();
	}

	// recupera informa��es do objeto
	// como nome
	public String getNome() {
		return logado.getUsuNome();
	}

	public void atualizaNomeUsuario() {
		// se usu�rio � um propriet�rio
		// define nome

	}

	public void setNome(String nome) {
		logado.setUsuNome(nome);
	}

	// recupera informa��es do objeto
	// tipo de usu�rio
	public TipoUsuario getTipo() {

		return logado.getUsuTipo();
	}

	// verifica se est� logado
	public boolean isLogado() {
		return logado != null;
	}

	// sair
	public void logout() {

		this.logado = null;

	}
}
