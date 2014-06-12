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

	// objeto do tipo Usuário
	private Usuario logado;

	// que pode ser o id do vendedor ou id do proprietário
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

	// Guarda o objeto Usuário
	public void fazerLogin(Usuario usuario) {
		this.logado = usuario;

		if (usuario != null) {

			if (usuario.getUsuTipo().name().equalsIgnoreCase("PROPRIETARIO")) {
				// atualiza nome de usuário
				setNome(ProprietarioApplication
						.obterNomeProprietarioUsuarioId(getUsuarioId()));

				// define id da entidade que é proprietário
				setIdEntidadeUsuario(ProprietarioApplication
						.obterIdProprietarioUsuarioId(getUsuarioId()));
			}// fim if

			// se usuário é um vendedor
			// define nome
			if (usuario.getUsuTipo().name().equalsIgnoreCase("VENDEDOR")) {
				setNome(vendedorApplication
						.obterNomeVendedorUsuarioId(getUsuarioId()));
			}// fim if

		}// fim if
	}

	// recupera informações do objeto
	// como id
	public Integer getUsuarioId() {
		return logado.getUsuId();
	}

	// recupera informações do objeto
	// como nome
	public String getNome() {
		return logado.getUsuNome();
	}

	public void atualizaNomeUsuario() {
		// se usuário é um proprietário
		// define nome

	}

	public void setNome(String nome) {
		logado.setUsuNome(nome);
	}

	// recupera informações do objeto
	// tipo de usuário
	public TipoUsuario getTipo() {

		return logado.getUsuTipo();
	}

	// verifica se está logado
	public boolean isLogado() {
		return logado != null;
	}

	// sair
	public void logout() {

		this.logado = null;

	}
}
