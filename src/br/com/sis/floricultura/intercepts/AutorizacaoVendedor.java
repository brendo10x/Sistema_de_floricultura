package br.com.sis.floricultura.intercepts;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.sis.floricultura.controller.IndexController;
import br.com.sis.floricultura.controller.UsuarioController;
import br.com.sis.floricultura.model.UsuarioWeb;

//interceptar requisi��es executando

@Intercepts
public class AutorizacaoVendedor implements Interceptor {

	private final UsuarioWeb usuario; // usu�rio da Sess�o
	private final Result result; // mostra resultados

	// inicializando
	public AutorizacaoVendedor(UsuarioWeb usuario, Result result) {
		this.usuario = usuario;
		this.result = result;
	}// fim construtor

	// decide se vai ou n�o interceptar a requisi��o atual
	public boolean accepts(ResourceMethod method) {

		// condi��o (true) para poder ir para o m�todo intercept abaixo
		// esteja anotado como @BloqueiaProprietario
		return method.containsAnnotation(BloqueiaProprietario.class);

	}// fim accepts()

	// executa de fato a l�gica de neg�cios
	public void intercept(InterceptorStack stack, ResourceMethod method,
			Object resourceInstance) throws InterceptionException {

		// 1� l�gica
		// para usu�rio n�o logado
		if (!usuario.isLogado()) {

			// define atributo para a jsp
			result.include("atencao",
					"SEM PERMISS�O! FA�A O LOGIN 1� PARA TER ACESSO AO SISTEMA!");

			// redireciona para a p�gina de login
			result.redirectTo(UsuarioController.class).loginForm();

		}// fim if

		// 2� l�gica
		// para usu�rio logado
		if (usuario.isLogado()) {

			// verifica se o tipo de usu�rio � um 'PROPRIETARIO'
			if (usuario.getTipo().name().equalsIgnoreCase("PROPRIETARIO")) {

				// define atributo para a jsp
				result.include("atencao",
						"SEM PERMISS�O! ACESSO RESTRITO PARA O TIPO 'PROPRIETARIO'");

				// redireciona para a p�gina principal
				result.redirectTo(IndexController.class).index();

			} else {

				// continuar a execu��o normal da requisi��o
				stack.next(method, resourceInstance);

			}// fim if

		}// fim if

	}// fim intercept()

}// fim class AutorizacaoProprietario