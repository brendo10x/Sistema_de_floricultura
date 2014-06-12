//pacote
package br.com.sis.floricultura.controller;

//import's
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sis.floricultura.application.ClienteApplication;
import br.com.sis.floricultura.application.FornecedorApplication;
import br.com.sis.floricultura.application.ProdutosApplication;
import br.com.sis.floricultura.application.UsuarioApplication;
import br.com.sis.floricultura.application.VendaApplication;
import br.com.sis.floricultura.application.VendedorApplication;
import br.com.sis.floricultura.dao.FornecedorDao;
import br.com.sis.floricultura.dao.VendedorDao;
import br.com.sis.floricultura.intercepts.Liberado;

/* Classe controladora IndexController contém 
 as regras de negócio, com anotação @Resource para o
 Vraptor saber que essa classe é um controller
 */

@Resource
public class IndexController {

	Result result; // mostra resultados
	VendaApplication vendaApplication;
	UsuarioApplication usuarioApplication;
	ClienteApplication clienteApplication;
	ProdutosApplication produtosApplication;
	FornecedorApplication fornecedorApplication;
	VendedorApplication vendedorApplication;

	/*
	 * Injetando dependências no controller IndexController inicializando, o
	 * controller se encarregara de instânciar para nós o que precisamos
	 * simplesmente declarando no parâmetro do construtor
	 */

	public IndexController(Result result, VendaApplication vendaApplication,
			UsuarioApplication usuarioApplication,
			ClienteApplication clienteApplication,
			ProdutosApplication produtosApplication,
			FornecedorApplication fornecedorApplication,
			VendedorApplication vendedorApplication) {

		this.result = result;
		this.vendaApplication = vendaApplication;
		this.usuarioApplication = usuarioApplication;
		this.clienteApplication = clienteApplication;
		this.produtosApplication = produtosApplication;
		this.fornecedorApplication = fornecedorApplication;
		this.vendedorApplication = vendedorApplication;

	}// fim do contrutor

	// Acesso
	@Liberado
	// tipo de requisição
	@Get
	// caminho da requição
	@Path("/")
	public void index() {

		// define título da página
		result.include("titulo", "Sistema Floricultura");

		// define atributo para a jsp
		// Numero total de vendas
		result.include("numeroVendas", vendaApplication.numeroTotalVendas());

		// define atributo para a jsp
		// Numero total de usuários
		result.include("numeroUsuarios",
				usuarioApplication.numeroTotalUsuarios());

		// define atributo para a jsp
		// Numero total de clientes
		result.include("numeroClientes",
				clienteApplication.numeroTotalClientes());

		// define atributo para a jsp
		// Numero total de produtos
		result.include("numeroProdutos",
				produtosApplication.numeroTotalProdutos());

		// define atributo para a jsp
		// Numero total de fornecedores
		result.include("numeroFornecedores",
				fornecedorApplication.numeroTotalFornecedores());

		// define atributo para a jsp
		// Numero total de vendedores
		result.include("numeroVendedores",
				vendedorApplication.numeroTotalVendedores());

	}// fim index()

}// fim class IndexController
