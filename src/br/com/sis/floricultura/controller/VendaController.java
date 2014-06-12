//pacote
package br.com.sis.floricultura.controller;

//import's
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.com.sis.floricultura.application.ClienteApplication;
import br.com.sis.floricultura.application.VendaApplication;
import br.com.sis.floricultura.dao.ClienteDao;
import br.com.sis.floricultura.dao.ProdutoDao;
import br.com.sis.floricultura.dao.ProdutosVendidosDao;
import br.com.sis.floricultura.dao.VendaDao;
import br.com.sis.floricultura.dao.VendedorDao;
import br.com.sis.floricultura.exception.ExcluirSelecInvalidoException;
import br.com.sis.floricultura.exception.VendaInvalidaException;
import br.com.sis.floricultura.intercepts.Liberado;
import br.com.sis.floricultura.intercepts.BloqueiaProprietario;
import br.com.sis.floricultura.intercepts.BloqueiaVendedor;
import br.com.sis.floricultura.model.Cliente;
import br.com.sis.floricultura.model.Produtos;
import br.com.sis.floricultura.model.ProdutosVendidos;
import br.com.sis.floricultura.model.Proprietario;
import br.com.sis.floricultura.model.Venda;
import br.com.sis.floricultura.model.Vendedor;

/*Classe controladora ProdutoController cont�m 
 as regras de neg�cio, com anota��o @Resource para o
 Vraptor saber que essa classe � um controller*/

@Resource
public class VendaController {

	private Result result;// mostra resultados
	private Validator validator; // valida��o
	private VendaApplication vendaApplication;

	// contrutor padr�o inicializa as depend�ncias que seram usadas
	public VendaController(Result result, Validator validator,
			VendaApplication vendaApplication) {

		this.result = result;
		this.validator = validator;
		this.vendaApplication = vendaApplication;

	}// fim contrutor padr�o

	// Acesso
	@BloqueiaProprietario
	// redireciona para a p�gina de formul�rio
	@Get
	// m�todo get
	@Path("venda/novo")
	// url de caminho
	// l�gica de neg�cio
	public void novo() {

		// define t�tulo da p�gina
		result.include("titulo", "Adicionar venda");

	}// fim novo()

	// Acesso
	@BloqueiaProprietario
	// a��o de adicionar efitivamente
	@Post
	// m�todo post
	@Path("venda/adicionar")
	// url de caminho
	public void adicionar(Cliente cliente, Venda venda, String vendaData,
			Vendedor vendedor, List<ProdutosVendidos> produtosVendidos) {

		// define t�tulo da p�gina
		result.include("titulo", "Adicionar venda");

		try {

			// processo salvar venda
			vendaApplication.salvarVenda(cliente, venda, vendaData, vendedor,
					produtosVendidos);

		} catch (ParseException e) {

			validator.add(new ValidationMessage("Data inv�lida!", null));

		} catch (VendaInvalidaException e) {

			validator.add(new ValidationMessage(e.getMessage(), null));

		}

		// redireciona para a p�gina edita() caso houver erro na valida��o
		validator.onErrorUsePageOf(VendaController.class).novo();

		// define atributo para a jsp
		// adicionado com sucesso
		result.include("sucesso_adicionado", "Venda adicionada com sucesso!");

		// redirecionando para a p�gina novo()
		result.redirectTo(this).novo();

	}// fim adicionar()

	// Acesso
	@Liberado
	// p�gina de listar
	@Get
	@Path("venda/listar")
	public List<Venda> listar(Integer pagina) {

		// define t�tulo da p�gina
		result.include("titulo", "Listar vendas");

		// define atributo para a jsp
		// P�gina atual
		result.include("paginaAtual", pagina == null ? pagina = 1 : pagina);

		// total de registros
		result.include("totalVendas", vendaApplication.numeroTotalVendas());

		// define atributo para a jsp
		// N�mero de pagina��es
		result.include("numeroDePaginacao",
				vendaApplication.numeroDePaginacao("NORMAL", ""));

		// retorna uma lista de vendas
		return vendaApplication.listarVendas(pagina, "NORMAL", "");

	}// fim listar()

	// Acesso
	@Liberado
	// p�gina de listar vendas pesquisadas
	@Post
	@Get
	@Path({ "venda/lista-buscar", "venda/lista-buscar/{busca}" })
	public List<Venda> buscar(Integer pagina, String busca) {

		// define t�tulo da p�gina
		result.include("titulo", "Listar vendas");

		// define atributo para a jsp
		// N�mero de pagina��es
		result.include("numeroDePaginacao",
				vendaApplication.numeroDePaginacao("BUSCA", busca));

		// total de registros
		result.include("totalVendas", vendaApplication.numeroRegistroVendaBuscar(busca));

		// define atributo para a jsp
		// P�gina atual
		result.include("paginaAtual", pagina == null ? pagina = 1 : pagina);

		// define atributo para a jsp
		// Pesquisa
		result.include("pesquisa", busca);

		// retorna uma lista de vendas
		return vendaApplication.listarVendas(pagina, "BUSCA", busca);

	}// fim buscar()

	// Acesso
	@BloqueiaVendedor
	// excluir venda efetivamente
	@Get
	@Path("venda/excluir")
	public void excluir(Integer idVenda) {

		// processo excluir venda
		vendaApplication.excluirVenda(idVenda);

		// define atributo para a jsp
		// remo��o com sucesso
		result.include("sucesso_remocao", "Venda removida com sucesso!");

		// redirecionando para a p�gina listar
		result.redirectTo(this).listar(null);

	}// fim excluir()

	// Acesso
	@BloqueiaVendedor
	// excluir venda efetivamente
	@Post
	@Path("venda/excluir-selecionados")
	public void excluirSelecionados(List<Venda> vendas) {

		// processo excluir venda
		try {
			vendaApplication.excluirVendaSelecionadas(vendas);
		} catch (ExcluirSelecInvalidoException e) {
			validator.add(new ValidationMessage(e.getMessage(), null));
		}
		// redireciona para a p�gina listar caso houver erro na valida��o
		validator.onErrorUsePageOf(this).listar(null);

		// define atributo para a jsp
		// remo��o com sucesso
		result.include("sucesso_remocao", "Venda(s) removida(s) com sucesso!");

		// redirecionando para a p�gina listar
		result.redirectTo(this).listar(null);

	}// fim excluir()

}// fim class VendaController
