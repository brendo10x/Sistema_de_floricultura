package br.com.sis.floricultura.application;

import java.text.ParseException;
import java.util.List;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sis.floricultura.dao.ClienteDao;
import br.com.sis.floricultura.dao.ProdutoDao;
import br.com.sis.floricultura.dao.VendaDao;
import br.com.sis.floricultura.model.Cidade;
import br.com.sis.floricultura.model.Cliente;
import br.com.sis.floricultura.model.Estado;
import br.com.sis.floricultura.model.Produtos;
import br.com.sis.floricultura.model.Venda;
import br.com.sis.floricultura.model.Vendedor;

@Component
public class RelatorioApplication {

	private ClienteDao clienteDao;
	private ProdutoDao produtoDao;
	private VendaDao vendaDao;

	public RelatorioApplication() {
		clienteDao = new ClienteDao();
		vendaDao = new VendaDao();
		produtoDao = new ProdutoDao();
	}

	public List<Venda> listarVendasPorDia(String dia) throws ParseException {

		return vendaDao.listarVendasPorDia(dia);

	}

	public List<Venda> listarVendasPorMes(String mes, String ano) {

		return vendaDao.listarVendasPorMes(mes, ano);

	}

	public List<Venda> listarVendasPorVendedorDia(String dia, Vendedor vendedor)
			throws ParseException {

		return vendaDao.listarVendasPorVendedorDia(dia, vendedor);

	}

	public List<Venda> listarVendasPorVendedorMes(String mes, String ano,
			Vendedor vendedor) throws ParseException {

		return vendaDao.listarVendasPorVendedorMes(mes, ano, vendedor);

	}

	public List<Cliente> clientesPorCidade(Cidade cidade, Estado estado) {

		return clienteDao.clientesPorCidade(cidade, estado);

	}

	public List<Produtos> produtosMaisVendidos(String ano, String mes) {

		return produtoDao.produtosMaisVendidos(ano, mes);
	}
	
	public List<Cliente> clientesPorEstado(Estado estado) {
		return clienteDao.clientesPorEstado(estado);
	}

	public List<Produtos> listarTodos() {

		return produtoDao.listarTodos();
	}
}
