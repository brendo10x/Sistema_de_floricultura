//pacote
package br.com.sis.floricultura.controller;

//import's
import java.io.IOException;
import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.com.caelum.vraptor.view.Results;
import br.com.sis.floricultura.application.ProdutosApplication;
import br.com.sis.floricultura.dao.EstadoDao;
import br.com.sis.floricultura.dao.ProdutoDao;
import br.com.sis.floricultura.exception.ExcluirSelecInvalidoException;
import br.com.sis.floricultura.intercepts.Liberado;
import br.com.sis.floricultura.intercepts.BloqueiaVendedor;
import br.com.sis.floricultura.model.Cliente;
import br.com.sis.floricultura.model.Endereco;
import br.com.sis.floricultura.model.Fornecedor;
import br.com.sis.floricultura.model.Produtos;

/*Classe controladora ProdutoController cont�m 
 as regras de neg�cio, com anota��o @Resource para o
 Vraptor saber que essa classe � um controller*/

@Resource
public class ProdutoController {

	private Result result; // mostra resultados
	private Validator validator;
	private ProdutosApplication produtosApplication;

	// contrutor padr�o inicializa as depend�ncias que seram usadas
	public ProdutoController(Result result,
			ProdutosApplication produtosApplication, Validator validator) {
		this.result = result; // mostra resultados
		this.produtosApplication = produtosApplication;
		this.validator = validator;
	}// fim do contrutor

	// Acesso
	@BloqueiaVendedor
	// redireciona para a p�gina novo
	@Get
	// m�todo get
	@Path("produto/novo")
	// url de caminho
	// l�gica de neg�cio
	public void novo() {

		// define t�tulo da p�gina
		result.include("titulo", "Adicionar produto");

	}// fim novo()

	// Acesso
	@BloqueiaVendedor
	// a��o de adiciona efitivamente
	@Post
	// m�todo post
	@Path("produto/adicionar")
	// url de caminho
	public void adicionar(Produtos produto) {

		// processo salvar
		produtosApplication.salvarProduto(produto);

		// define atributo para a jsp
		// adicionado com sucesso
		result.include("sucesso_adicionado", "Produto " + produto.getProNome()
				+ " adicionado com sucesso!");

		// redirecionando para a p�gina novo()
		result.redirectTo(ProdutoController.class).novo();

	}// fim adicionar()

	// Acesso
	@BloqueiaVendedor
	// p�gina de listar produtos
	@Get
	@Path("produto/listar")
	public List<Produtos> listar(Integer pagina) {

		// define t�tulo da p�gina
		result.include("titulo", "Listar produtos");

		// define atributo para a jsp
		// N�mero total de produtos
		result.include("totalProdutos",
				produtosApplication.numeroTotalProdutos());

		// define atributo para a jsp
		// N�mero de pagina��es
		result.include("numeroDePaginacao",
				produtosApplication.numeroDePaginacao("NORMAL", null));

		// define atributo para a jsp
		// P�gina atual
		result.include("paginaAtual", pagina == null ? pagina = 1 : pagina);

		// retorna uma lista de produtos
		return produtosApplication.listarProdutos(pagina, "NORMAL", null);

	}// fim listar()

	// Acesso
	@BloqueiaVendedor
	// p�gina de listar produtos pesquisado
	@Post
	@Get
	@Path({ "produto/lista-buscar", "produto/lista-buscar/{busca}" })
	public List<Produtos> buscar(Integer pagina, String busca) {

		// define t�tulo da p�gina
		result.include("titulo", "Listar produtos");

		// define atributo para a jsp
		// N�mero de pagina��es
		result.include("numeroDePaginacao",
				produtosApplication.numeroDePaginacao("BUSCA", busca));

		// define atributo para a jsp
		// N�mero total de produtos
		result.include("totalProdutos",
				produtosApplication.numeroRegistroClienteBuscar(busca));

		// define atributo para a jsp
		// P�gina atual
		result.include("paginaAtual", pagina == null ? pagina = 1 : pagina);

		// define atributo para a jsp
		// Pesquisa
		result.include("pesquisa", busca);

		// retorna uma lista de produtos
		return produtosApplication.listarProdutos(pagina, "BUSCA", busca);

	}// fim buscar()

	// Acesso
	@BloqueiaVendedor
	// excluir produto efetivamente
	@Get
	@Path("produto/excluir")
	public void excluir(Integer idProduto) {

		// processo exclir
		produtosApplication.excluirProduto(idProduto);

		// define atributo para a jsp
		// remo��o com sucesso
		result.include("sucesso_remocao", "Produto removido com sucesso!");

		// redirecionando para a p�gina listar
		result.redirectTo(ProdutoController.class).listar(null);

	}// fim excluir()

	// Acesso
	@BloqueiaVendedor
	// excluir produto efetivamente
	@Post
	@Path("produto/excluir-selecionados")
	public void excluirSelecionados(List<Produtos> produtos) {

		// processo exclir selecionados
		try {
			produtosApplication.excluirProdutoSelecionados(produtos);
		} catch (ExcluirSelecInvalidoException e) {
			validator.add(new ValidationMessage(e.getMessage(), null));
		}
		// redireciona para a p�gina edita() caso houver erro na valida��o
		validator.onErrorUsePageOf(ProdutoController.class).listar(1);

		// define atributo para a jsp
		// remo��o com sucesso
		result.include("sucesso_remocao", "Produto removido com sucesso!");

		// redirecionando para a p�gina listar
		result.redirectTo(ProdutoController.class).listar(null);

	}// fim excluir()

	// Acesso
	@BloqueiaVendedor
	// formul�rio de edita produto
	// m�todo get
	@Get
	// url de caminho
	@Path("produto/edita")
	public void edita(Integer idProduto) {

		// define t�tulo da p�gina
		result.include("titulo", "Editar produto");

		// carrega objeto do tipo produto
		Produtos produto = produtosApplication.carregaProduto(idProduto);

		// define atributo para a jsp
		result.include("produto", produto);

	}// fim edita()

	// Acesso
	@BloqueiaVendedor
	// a��o de editar efetivamente
	@Post
	// m�todo post
	@Path("produto/editar")
	// url de caminho
	public void editando(Produtos produto) {

		// processo atualizar
		produtosApplication.atualizaProduto(produto);

		// define um atributo de atualizado com sucesso para a jsp
		result.include("sucesso_editado", "Produto " + produto.getProNome()
				+ " editado com sucesso!");

		// redirecionando para a edita
		result.redirectTo(ProdutoController.class).edita(produto.getProId());

	}// fim editando()

	// Acesso
	@BloqueiaVendedor
	// formul�rio de edita produto
	// m�todo get
	@Get
	// url de caminho
	@Path("produto/visualizar")
	public void visualizar(Integer idProduto) {

		// define t�tulo da p�gina
		result.include("titulo", "Visualizar produto");

		// carrega objeto do tipo produto
		Produtos produto = produtosApplication.carregaProduto(idProduto);

		// define atributo para a jsp
		result.include("produto", produto);

	}// fim edita()

	// Acesso
	@Liberado
	@Get
	// m�todo get
	@Path("/produto/busca.json")
	// url de caminho
	// l�gica de neg�cio
	public void buscarProdutos(String term) throws IOException {

		// recebe uma lista de objetos e retorna uma lista no formato json
		result.use(Results.json()).withoutRoot()
				.from(produtosApplication.buscarProdutos(term))
				.exclude("proTipo", "proQuantidade").serialize();

	}// fim buscarProdutos()

}// fim class ProdutoController
