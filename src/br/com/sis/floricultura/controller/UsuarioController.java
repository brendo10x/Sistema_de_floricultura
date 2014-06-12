package br.com.sis.floricultura.controller;

import javax.swing.JOptionPane;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.com.sis.floricultura.application.UsuarioApplication;
import br.com.sis.floricultura.dao.ProprietarioDao;
import br.com.sis.floricultura.dao.UsuarioDao;
import br.com.sis.floricultura.dao.VendedorDao;
import br.com.sis.floricultura.exception.UsuarioInvalidoException;
import br.com.sis.floricultura.model.Usuario;
import br.com.sis.floricultura.model.UsuarioWeb;
import br.com.sis.floricultura.service.UsuarioService;

/*Classe controladora UsuarioController cont�m 
 as regras de neg�cio, com anota��o @Resource para o
 Vraptor saber que essa classe � um controller*/

@Resource
public class UsuarioController {

	private UsuarioWeb usuarioWeb;// mantem o estado do usu�rio
	private Result result; // mostra resultados
	private Validator validator; // valida��o
	private UsuarioApplication usuarioApplication; // usu�rio app

	// ...
	public UsuarioController(Validator validator, UsuarioWeb usuarioWeb,
			UsuarioApplication usuarioApplication, Result result) {

		this.result = result;
		this.validator = validator;
		this.usuarioWeb = usuarioWeb;
		this.usuarioApplication = usuarioApplication;

	}// fim do construtor

	// p�gina de login
	@Get("/login")
	public void loginForm() {

		// define t�tulo da p�gina
		result.include("titulo", "Login - Sistema Floricultura");

	}// fim login()

	// a��o de login
	@Post("/login")
	public void login(Usuario usuario) {

		Usuario usuarioValido = null;

		try {

			// condi��o que permite fazer login
			usuarioValido = usuarioApplication.autenticaoLogin(usuario);

		} catch (UsuarioInvalidoException e) {

			validator.add(new ValidationMessage(e.getMessage(), null));

		} finally {

			// redireciona para a p�gina loginForm() caso houver erro na
			// valida��o
			validator.onErrorUsePageOf(UsuarioController.class).loginForm();
			
			// permite fazer login sem erros em sess�o
			usuarioWeb.fazerLogin(usuarioValido);

		}

		// redirecionando para a index
		result.redirectTo(IndexController.class).index();

	}// fim login()

	// a��o de sair do sistema
	@Path("/logout")
	public void logout() {

		// permite sair
		usuarioWeb.logout();

		// redirecionando para a p�gina de login
		result.redirectTo(UsuarioController.class).loginForm();

	}// fim logout()

}// fim class UsuarioController
