//pacote
package br.com.sis.floricultura.controller;

//import's
import java.text.ParseException;
import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.com.sis.floricultura.application.CidadeApplication;
import br.com.sis.floricultura.application.EstadoApplication;
import br.com.sis.floricultura.application.RelatorioApplication;
import br.com.sis.floricultura.application.VendaApplication;
import br.com.sis.floricultura.application.VendedorApplication;
import br.com.sis.floricultura.dao.CidadeDao;
import br.com.sis.floricultura.dao.ClienteDao;
import br.com.sis.floricultura.dao.EstadoDao;
import br.com.sis.floricultura.dao.ProdutoDao;
import br.com.sis.floricultura.dao.VendaDao;
import br.com.sis.floricultura.dao.VendedorDao;
import br.com.sis.floricultura.intercepts.BloqueiaVendedor;
import br.com.sis.floricultura.model.Cidade;
import br.com.sis.floricultura.model.Cliente;
import br.com.sis.floricultura.model.Estado;
import br.com.sis.floricultura.model.Produtos;
import br.com.sis.floricultura.model.Venda;
import br.com.sis.floricultura.model.Vendedor;

/*Classe controladora RelatorioController contém 
 as regras de negócio, com anotação @Resource para o
 Vraptor saber que essa classe é um controller*/

@Resource
public class RelatorioController {
	private Result result;// mostra resultados
	private Validator validator;
	private RelatorioApplication relatorioApplication;
	private VendedorApplication vendedorApplication;
	private EstadoApplication estadoApplication;
	private CidadeApplication cidadeApplication;

	// contrutor padrão inicializa as dependências que seram usadas
	public RelatorioController(Result result, Validator validator,
			RelatorioApplication relatorioApplication,
			VendedorApplication vendedorApplication,
			EstadoApplication estadoApplication,
			CidadeApplication cidadeApplication) {

		this.result = result;
		this.validator = validator;
		this.relatorioApplication = relatorioApplication;
		this.vendedorApplication = vendedorApplication;
		this.estadoApplication = estadoApplication;
		this.cidadeApplication = cidadeApplication;

	}// fim do contrutor

	// Acesso
	@BloqueiaVendedor
	// Obs: Relatório de vendas realizadas em um dia ou no mês
	@Get
	@Path("relatorio/vendas-por-um-dia-ou-no-mes")
	public List<Venda> vendas_1(List<Venda> venda) {

		// define título da página
		result.include("titulo", "Vendas realizadas em um dia ou no mês");

		// retorna uma lista de vendas
		return venda;
	}// fim vendas_1()

	// Acesso
	@BloqueiaVendedor
	// Obs: relatório vendas por um dia
	@Post
	@Path("relatorio/vendas-por-um-dia")
	public void vendas_por_um_dia(String dia) {

		// define título da página
		result.include("titulo", "Vendas realizadas em um dia ou no mês");

		// define atributo para a jsp
		result.include("pesquisa", "Todas as vendas realizadas na data " + dia);

		List<Venda> listaVendas = null;
		try {
			listaVendas = relatorioApplication.listarVendasPorDia(dia);
		} catch (ParseException e) {
			validator.add(new ValidationMessage("Data inválida", null));
		}

		// redireciona para a página edita() caso houver erro na validação
		validator.onErrorUsePageOf(RelatorioController.class).vendas_1(null);

		// redireciona para a página vendas_1() com lista de vendas
		result.redirectTo(this).vendas_1(listaVendas);

	}// fim vendas_por_um_dia()

	// Acesso
	@BloqueiaVendedor
	// Obs: relatório vendas em um determinado mês
	@Post
	@Path("relatorio/vendas-por-um-mes")
	public void vendas_por_um_mes(String mes, String ano) {

		// define título da página
		result.include("titulo", "Vendas realizadas em um dia ou no mês");

		// define atributo para a jsp
		result.include("pesquisa", "Todas as vendas realizadas nas datas "
				+ ano + "-" + mes + "-dias");

		// redireciona para a página vendas_1() com lista de vendas
		result.redirectTo(this).vendas_1(
				relatorioApplication.listarVendasPorMes(mes, ano));

	}// fim vendas_por_um_dia()

	// Acesso
	@BloqueiaVendedor
	// Obs: Relatório de vendas realizadas por um vendedor em um dia ou no mês
	@Get
	@Path("relatorio/vendas-por-um-vendedor-em-um-dia-ou-no-mes")
	public List<Venda> vendas_2(List<Venda> venda) {

		// define título da página
		result.include("titulo",
				"Vendas realizadas por um vendedor em um dia ou no mês");

		// retorna uma lista de vendas
		return venda;

	}// fim vendas_2()

	// Acesso
	@BloqueiaVendedor
	// Obs: relatório vendas por um vendedor em um dia
	@Post
	@Path("relatorio/vendas-por-um-vendedor-em-um-dia")
	public void vendas_por_vendedor_um_dia(String dia, Vendedor vendedor) {

		// define título da página
		result.include("titulo",
				"Vendas realizadas por um vendedor em um dia ou no mês");

		List<Venda> listaVendas = null;
		try {
			listaVendas = relatorioApplication.listarVendasPorVendedorDia(dia,
					vendedor);
		} catch (ParseException e) {
			validator.add(new ValidationMessage("Data inválida", null));
		}

		// redireciona para a página vendas_2() caso houver erro na validação
		validator.onErrorUsePageOf(this).vendas_2(null);

		// carrega objeto do tipo vendedor
		Vendedor objVendedor = vendedorApplication.carregaVendedor(vendedor
				.getVenId());

		// define atributo para a jsp
		result.include("pesquisa", "Todas as vendas realizadas no dia " + dia
				+ " com vendedor '" + objVendedor.getVenNome() + "'");

		// redireciona para a página vendas_2() com lista de vendas
		result.redirectTo(this).vendas_2(listaVendas);

	}// fim vendas_por_vendedor_um_dia()

	// Acesso
	@BloqueiaVendedor
	// Obs: relatório vendas de uma determinado vendedor e mês
	@Post
	@Path("relatorio/vendas-por-vendedor-em-um-mes")
	public void vendas_por_vendedor_em_um_mes(String mes, String ano,
			Vendedor vendedor) {

		// define título da página
		result.include("titulo",
				"Vendas realizadas por um vendedor em um dia ou no mês");

		List<Venda> listaVendas = null;
		try {
			listaVendas = relatorioApplication.listarVendasPorVendedorMes(mes,
					ano, vendedor);
		} catch (ParseException e) {
			validator.add(new ValidationMessage("Data inválida!", null));
		}

		// redireciona para a página vendas_2() caso houver erro na validação
		validator.onErrorUsePageOf(this).vendas_2(null);

		// carrega objeto do tipo vendedor
		Vendedor objVendedor = vendedorApplication.carregaVendedor(vendedor
				.getVenId());

		// define atributo para a jsp
		result.include("pesquisa",
				"Todas as vendas realizadas nas datas " + ano + "-" + mes
						+ "-dias com vendedor '" + objVendedor.getVenNome()
						+ "'");

		// redireciona para a página vendas_2() com lista de vendas
		result.redirectTo(this).vendas_2(listaVendas);

	}// fim vendas_por_vendedor_em_um_mes()

	// Acesso
	@BloqueiaVendedor
	// Obs: Relatório de todos os clientes de uma determinada cidade ou estado
	@Get
	@Path("relatorio/clientes-por-estado-ou-cidade")
	public List<Cliente> clientes(List<Cliente> listaClientes) {

		// define título da página
		result.include("titulo",
				"Todos os clientes de uma determinada cidade ou estado");

		// define atributo para a jsp
		// retornando uma lista de estados
		result.include("estados", estadoApplication.listaTodosEstados());

		// retorna uma lista de clientes
		return listaClientes;

	}// fim clientes()

	// Acesso
	@BloqueiaVendedor
	// Obs: Relatório de todos os clientes de uma determinada cidade
	@Post
	@Path("relatorio/clientes-por-uma-cidade")
	public void clientes_por_uma_cidade(Cidade cidade, Estado estado) {

		// define título da página
		result.include("titulo",
				"Todos os clientes de uma determinada cidade ou estado");

		// define atributo para a jsp
		// retornando uma lista de estados
		result.include("estados", estadoApplication.listaTodosEstados());

		// carrega objeto do tipo cidade
		Cidade objCidade = cidadeApplication.carregaCidade(cidade.getCidId());

		// define atributo para a jsp
		result.include("pesquisa",
				"Todos os clientes da cidade '" + objCidade.getCidNome()
						+ "' do estado '"
						+ objCidade.getEstadoEstId().getEstDescricao() + "'");

		// redireciona para a página clientes() com lista de clientes
		result.redirectTo(this).clientes(
				relatorioApplication.clientesPorCidade(cidade, estado));

	}// fim clientes_por_uma_cidade()

	// Acesso
	@BloqueiaVendedor
	// Obs: Relatório de todos os clientes de uma determinado estado
	@Post
	@Path("relatorio/clientes-por-um-estado")
	public void clientes_por_um_estado(Estado estado) {

		// define título da página
		result.include("titulo",
				"Todos os clientes de uma determinada cidade ou estado");

		// validação
		if (estado.getEstId() == null) {
			// adiciona mensagem na validação

			validator.add(new ValidationMessage(
					"Estado é obrigatório para esta pesquisa!", null));
		}// fim if

		// define atributo para a jsp
		// retornando uma lista de estados
		result.include("estados", estadoApplication.listaTodosEstados());

		// redireciona para a página clientes() caso houver erro na validação
		validator.onErrorUsePageOf(this).clientes(null);

		// carrega objeto do tipo estado
		Estado objEstado = estadoApplication.carregaEstado(estado.getEstId());

		// define atributo para a jsp
		result.include("pesquisa",
				"Todos os clientes do estado '" + objEstado.getEstDescricao()
						+ "'");

		// redireciona para a página clientes() com lista de clientes
		result.redirectTo(this).clientes(
				relatorioApplication.clientesPorEstado(estado));

	}// fim clientes_por_um_estado()

	// Acesso
	@BloqueiaVendedor
	// Obs: Relatório de produtos mais vendidos no mês
	@Get
	@Path("relatorio/produtos-mais-vendidos-no-mes")
	public List<Produtos> produtos_1(List<Produtos> listaProdutos) {

		// define título da página
		result.include("titulo", "Produtos mais vendidos no mês");

		// retorna uma lista de produtos
		return listaProdutos;

	}// fim produtos_1()

	// Acesso
	@BloqueiaVendedor
	// lista de produtos mais vendidos mês
	@Post
	@Path("relatorio/produtos-mais-vendidos-no-mes")
	public void produtos_mais_no_mes_vendidos(String mes, String ano) {

		// define título da página
		result.include("titulo", "Produtos mais vendidos no mês");

		// define atributo para a jsp
		result.include("pesquisa",
				"Todos os produtos mais vendidos nas datas '" + ano + "-" + mes
						+ "-dias'");

		// redireciona para a página produtos_1() com lista de clientes
		result.redirectTo(this).produtos_1(
				relatorioApplication.produtosMaisVendidos(ano, mes));

	}// fim produtos_mais_no_mes_vendidos()

	// Acesso
	@BloqueiaVendedor
	// Obs: Relatório de quantidade de cada produto em estoque
	@Get
	@Path("relatorio/produtos-em-estoque")
	public List<Produtos> produtos_2() {

		// define título da página
		result.include("titulo", "Quantidade de cada produto em estoque");

		// retorna uma lista de produtos
		return relatorioApplication.listarTodos();

	}// fim listar()

}// fim class RelatorioController
