package br.com.sis.floricultura.application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.com.sis.floricultura.dao.ClienteDao;
import br.com.sis.floricultura.dao.ProdutoDao;
import br.com.sis.floricultura.dao.ProdutosVendidosDao;
import br.com.sis.floricultura.dao.VendaDao;
import br.com.sis.floricultura.dao.VendedorDao;
import br.com.sis.floricultura.exception.ExcluirSelecInvalidoException;
import br.com.sis.floricultura.exception.VendaInvalidaException;
import br.com.sis.floricultura.model.Cliente;
import br.com.sis.floricultura.model.Produtos;
import br.com.sis.floricultura.model.ProdutosVendidos;
import br.com.sis.floricultura.model.Venda;
import br.com.sis.floricultura.model.Vendedor;
import br.com.sis.floricultura.service.VendaService;

@Component
public class VendaApplication {

	private final Integer REGISTROS_POR_PAGINA = 4;
	private VendaDao vendaDao;
	private ClienteDao clienteDao;
	private VendedorDao vendedorDao;
	private ProdutoDao produtoDao;
	private ProdutosVendidosDao produtosVendidosDao;
	private VendaService vendaService;

	public VendaApplication() {
		vendaDao = new VendaDao();
		vendaService = new VendaService(vendaDao);
		clienteDao = new ClienteDao();
		vendedorDao = new VendedorDao();
		produtoDao = new ProdutoDao();
		produtosVendidosDao = new ProdutosVendidosDao();
	}

	public List<Venda> listarVendas(Integer pagAtual, String nomePaginacao,
			String nomeBusca) {

		return vendaDao.listarVendas(REGISTROS_POR_PAGINA, pagAtual,
				nomePaginacao, nomeBusca);
	}

	public Integer numeroDePaginacao(String nomePaginacao, String nomeBusca) {

		return vendaDao.numeroDePaginacao(REGISTROS_POR_PAGINA, nomePaginacao,
				nomeBusca);

	}

	public void excluirVenda(Integer idVenda) {

		// exclui venda
		vendaDao.deletar(idVenda);

	}

	public void excluirVendaSelecionadas(List<Venda> vendas)
			throws ExcluirSelecInvalidoException {

		// validação
		vendaService.validaSeExisteSelecValido(vendas);

		for (Venda venda : vendas) {
			// exclui venda
			vendaDao.deletar(venda.getVenId());

		}

	}

	public void salvarVenda(Cliente cliente, Venda venda, String vendaData,
			Vendedor vendedor, List<ProdutosVendidos> produtosVendidos)
			throws ParseException, VendaInvalidaException {

		// 1 º validação o total da venda
		vendaService.validaTotalVenda(venda);

		// atribuir SimpleDateFormat recebendo data string
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dataUtil = new Date();

		// convertendo em data

		dataUtil = df.parse(vendaData);

		// define atributo
		venda.setVenDataVenda(dataUtil);

		// carrega objeto do tipo cliente
		cliente = clienteDao.carregar(cliente.getCliId());

		// define atributo
		venda.setClienteCliId(cliente);

		// carrega objeto do tipo vendedor
		Vendedor objVendedor = vendedorDao.carregar(vendedor.getVenId());

		// define atributo
		venda.setVendedorVenId(objVendedor);

		// adiciona venda
		vendaDao.adicionar(venda);

		// carrega o última venda
		Venda ultimaVenda = vendaDao.buscarUltimaVenda();

		// 2º validação

		// adicionar produtos vendidos defina última venda para todos

		// validação antes só vai cadastrar os produtos vendidos se todos os
		// campos estiver validado, caso contrário pode vir a afetar o estoque
		for (ProdutosVendidos objProdutosVendidos : produtosVendidos) {

			// carrega objeto do tipo produto
			Produtos produto = produtoDao.carregar(objProdutosVendidos
					.getProdutosProId().getProId());

			// validação
			vendaService.validaQuantProdutosVendidos(objProdutosVendidos,
					produto, ultimaVenda);

			// verifica se o estoque está vázio

			// validação
			vendaService.validaQuantdProduto(produto, ultimaVenda);

			// se produto quantidade em estoque for maior que produto quantidade
			// escolhido, ok salva

			// validação
			vendaService.validaQuantdEstoqueComProdutosVendidosEscolhido(
					produto, objProdutosVendidos, ultimaVenda);

		}// fim for

		// pode cadastrar sem erros
		for (ProdutosVendidos objProdutosVendidos : produtosVendidos) {

			// carrega objeto do tipo produto
			Produtos produto = produtoDao.carregar(objProdutosVendidos
					.getProdutosProId().getProId());

			// atualiza estoque ou quantidade do produto
			produto.setProQuantidade(produto.getProQuantidade()
					- objProdutosVendidos.getProVendquantidades());

			// atualzia produto
			produtoDao.atualizar(produto);

			// defino venda a produtos vendidos
			objProdutosVendidos.setVendaVenId(ultimaVenda);

			// defino produto a produtos vendidos
			objProdutosVendidos.setProdutosProId(produto);

			// adicionar produtos vendidos
			produtosVendidosDao.adicionar(objProdutosVendidos);

		}// fim for

	}

	public String numeroTotalVendas() {

		return vendaDao.numeroVendas();
	}

	public String numeroRegistroVendaBuscar(String busca) {

		return vendaDao.numeroDeRegistrosBusca(busca);
	}
}
