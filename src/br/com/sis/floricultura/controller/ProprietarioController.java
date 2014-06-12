//pacote
package br.com.sis.floricultura.controller;

//import's
import java.util.List;

import javax.persistence.Version;
import javax.swing.JOptionPane;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.com.caelum.vraptor.validator.Validations;
import br.com.sis.floricultura.application.ProprietarioApplication;
import br.com.sis.floricultura.application.UsuarioApplication;
import br.com.sis.floricultura.dao.ProprietarioDao;
import br.com.sis.floricultura.dao.UsuarioDao;
import br.com.sis.floricultura.exception.UsuarioInvalidoException;
import br.com.sis.floricultura.intercepts.BloqueiaVendedor;
import br.com.sis.floricultura.intercepts.Liberado;
import br.com.sis.floricultura.model.Cliente;
import br.com.sis.floricultura.model.Proprietario;
import br.com.sis.floricultura.model.Usuario;

/*Classe controladora ProprietarioController contém 
 as regras de negócio, com anotação @Resource para o
 Vraptor saber que essa classe é um controller*/

@Resource
public class ProprietarioController {

	private Result result;// mostra resultados
	private Validator validator; // validação
	private ProprietarioApplication proprietarioApplication;// proprietário app
	private UsuarioApplication usuarioApplication; // usuário app

	// contrutor padrão inicializa as dependências que seram usadas
	public ProprietarioController(Validator validator, Result result,
			ProprietarioApplication proprietarioApplication,
			UsuarioApplication usuarioApplication) {

		this.result = result;
		this.validator = validator;
		this.usuarioApplication = usuarioApplication;
		this.proprietarioApplication = proprietarioApplication;

	}// fim do contrutor

	// Acesso
	@BloqueiaVendedor
	// página de edita
	@Get
	// método get
	@Path("proprietario/edita")
	// url de caminho
	public void edita(Integer idProprietario, Integer idUsuario) {

		// define título da página
		result.include("titulo", "Editar proprietário");

		// define atributo para a jsp
		result.include("proprietario",
				proprietarioApplication.carregaProprietario(idProprietario));

		// define atributo para a jsp
		result.include("usuario", usuarioApplication.carregaUsuario(idUsuario));

	}// fim edita()

	// Acesso
	@BloqueiaVendedor
	// ação de editar efetivamente
	@Post
	// método post
	@Path("proprietario/editar")
	// url de caminho
	public void editando(Proprietario proprietario, Usuario usuario) {

		try {

			// processo editar
			proprietarioApplication.editar(proprietario, usuario);

		} catch (UsuarioInvalidoException e) {
			validator.add(new ValidationMessage(e.getMessage(), null));
		}

		//redireciona se tiver erro
		validator.onErrorUsePageOf(ProprietarioController.class).edita(
				proprietario.getProId(), usuario.getUsuId());

		// define um atributo de atualizado com sucesso para a jsp
		result.include("sucesso_editado", " Editado com sucesso!");

		result.redirectTo(ProprietarioController.class).edita(
				proprietario.getProId(), usuario.getUsuId());

	}// fim editando()

}// fim class ProprietarioController
