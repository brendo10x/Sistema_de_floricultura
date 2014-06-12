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

/*Classe controladora ProdutoController contém 
 as regras de negócio, com anotação @Resource para o
 Vraptor saber que essa classe é um controller*/

@Resource
public class VendaController {

	private Result result;// mostra resultados
	private Validator validator; // validação
	private VendaApplication vendaApplication;

	// contrutor padrão inicializa as dependências que seram usadas
	public VendaController(Result result, Validator validator,
			VendaApplication vendaApplication) {

		this.result = result;
		this.validator = validator;
		this.vendaApplication = vendaApplication;

	}// fim contrutor padrão

	// Acesso
	@BloqueiaProprietario
	// redireciona para a página de formulário
	@Get
	// método get
	@Path("venda/novo")
	// url de caminho
	// lógica de negócio
	public void novo() {

		// define título da página
		result.include("titulo", "Adicionar venda");

	}// fim novo()

	// Acesso
	@BloqueiaProprietario
	// ação de adicionar efitivamente
	@Post
	// método post
	@Path("venda/adicionar")
	// url de caminho
	public void adicionar(Cliente cliente, Venda venda, String vendaData,
			Vendedor vendedor, List<ProdutosVendidos> produtosVendidos) {

		// define título da página
		result.include("titulo", "Adicionar venda");

		try {

			// processo salvar venda
			vendaApplication.salvarVenda(cliente, venda, vendaData, vendedor,
					produtosVendidos);

		} catch (ParseException e) {

			validator.add(new ValidationMessage("Data inválida!", null));

		} catch (VendaInvalidaException e) {

			validator.add(new ValidationMessage(e.getMessage(), null));

		}

		// redireciona para a página edita() caso houver erro na validação
		validator.onErrorUsePageOf(VendaController.class).novo();

		// define atributo para a jsp
		// adicionado com sucesso
		result.include("sucesso_adicionado", "Venda adicionada com sucesso!");

		// redirecionando para a página novo()
		result.redirectTo(this).novo();

	}// fim adicionar()

	// Acesso
	@Liberado
	// página de listar
	@Get
	@Path("venda/listar")
	public List<Venda> listar(Integer pagina) {

		// define título da página
		result.include("titulo", "Listar vendas");

		// define atributo para a jsp
		// Página atual
		result.include("paginaAtual", pagina == null ? pagina = 1 : pagina);

		// total de registros
		result.include("totalVendas", vendaApplication.numeroTotalVendas());

		// define atributo para a jsp
		// Número de paginações
		result.include("numeroDePaginacao",
				vendaApplication.numeroDePaginacao("NORMAL", ""));

		// retorna uma lista de vendas
		return vendaApplication.listarVendas(pagina, "NORMAL", "");

	}// fim listar()

	// Acesso
	@Liberado
	// página de listar vendas pesquisadas
	@Post
	@Get
	@Path({ "venda/lista-buscar", "venda/lista-buscar/{busca}" })
	public List<Venda> buscar(Integer pagina, String busca) {

		// define título da página
		result.include("titulo", "Listar vendas");

		// define atributo para a jsp
		// Número de paginações
		result.include("numeroDePaginacao",
				vendaApplication.numeroDePaginacao("BUSCA", busca));

		// total de registros
		result.include("totalVendas", vendaApplication.numeroRegistroVendaBuscar(busca));

		// define atributo para a jsp
		// Página atual
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
		// remoção com sucesso
		result.include("sucesso_remocao", "Venda removida com sucesso!");

		// redirecionando para a página listar
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
		// redireciona para a página listar caso houver erro na validação
		validator.onErrorUsePageOf(this).listar(null);

		// define atributo para a jsp
		// remoção com sucesso
		result.include("sucesso_remocao", "Venda(s) removida(s) com sucesso!");

		// redirecionando para a página listar
		result.redirectTo(this).listar(null);

	}// fim excluir()

}// fim class VendaController
