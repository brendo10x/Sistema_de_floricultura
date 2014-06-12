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

//interceptar requisições executando

@Intercepts
public class AutorizacaoVendedor implements Interceptor {

	private final UsuarioWeb usuario; // usuário da Sessão
	private final Result result; // mostra resultados

	// inicializando
	public AutorizacaoVendedor(UsuarioWeb usuario, Result result) {
		this.usuario = usuario;
		this.result = result;
	}// fim construtor

	// decide se vai ou não interceptar a requisição atual
	public boolean accepts(ResourceMethod method) {

		// condição (true) para poder ir para o método intercept abaixo
		// esteja anotado como @BloqueiaProprietario
		return method.containsAnnotation(BloqueiaProprietario.class);

	}// fim accepts()

	// executa de fato a lógica de negócios
	public void intercept(InterceptorStack stack, ResourceMethod method,
			Object resourceInstance) throws InterceptionException {

		// 1º lógica
		// para usuário não logado
		if (!usuario.isLogado()) {

			// define atributo para a jsp
			result.include("atencao",
					"SEM PERMISSÃO! FAÇA O LOGIN 1º PARA TER ACESSO AO SISTEMA!");

			// redireciona para a página de login
			result.redirectTo(UsuarioController.class).loginForm();

		}// fim if

		// 2º lógica
		// para usuário logado
		if (usuario.isLogado()) {

			// verifica se o tipo de usuário é um 'PROPRIETARIO'
			if (usuario.getTipo().name().equalsIgnoreCase("PROPRIETARIO")) {

				// define atributo para a jsp
				result.include("atencao",
						"SEM PERMISSÃO! ACESSO RESTRITO PARA O TIPO 'PROPRIETARIO'");

				// redireciona para a página principal
				result.redirectTo(IndexController.class).index();

			} else {

				// continuar a execução normal da requisição
				stack.next(method, resourceInstance);

			}// fim if

		}// fim if

	}// fim intercept()

}// fim class AutorizacaoProprietario