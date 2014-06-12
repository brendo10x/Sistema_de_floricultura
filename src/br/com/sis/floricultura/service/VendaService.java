package br.com.sis.floricultura.service;

import java.util.List;

import br.com.sis.floricultura.dao.VendaDao;
import br.com.sis.floricultura.exception.ExcluirSelecInvalidoException;
import br.com.sis.floricultura.exception.VendaInvalidaException;
import br.com.sis.floricultura.model.Produtos;
import br.com.sis.floricultura.model.ProdutosVendidos;
import br.com.sis.floricultura.model.Venda;
import br.com.sis.floricultura.model.Vendedor;

public class VendaService {

	private VendaDao vendaDao;

	public VendaService(VendaDao venda) {
		vendaDao = venda;
	}

	// verifica se o venda est� v�zio
	public void validaTotalVenda(Venda venda) throws VendaInvalidaException {

		if (venda.getVenTotal() <= 0.0) {

			// joga a exception
			throw new VendaInvalidaException("Total venda tem que ser maior que 0!");
		}

	}

	// verifica se o produtos vendidos est� v�zio
	public void validaQuantProdutosVendidos(ProdutosVendidos produtosVendidos,
			Produtos produto, Venda UltimaVenda) throws VendaInvalidaException {

		if (produtosVendidos.getProVendquantidades() <= 0) {
			// deleta venda
			vendaDao.deletar(UltimaVenda.getVenId());

			// joga a exception
			throw new VendaInvalidaException("Para o produto " + produto.getProNome()
					+ " (id = " + produto.getProId() + ")"
					+ " quantidade tem que ser maior igual 0!");

		}

	}

	// verifica se o estoque est� v�zio
	public void validaQuantdProduto(Produtos produto, Venda UltimaVenda)
			throws VendaInvalidaException {

		if (produto.getProQuantidade() <= 0) {
			// deleta venda
			vendaDao.deletar(UltimaVenda.getVenId());

			// joga a exception
			throw new VendaInvalidaException("Para o produto '" + produto.getProNome()
					+ "' (id = " + produto.getProId() + ")"
					+ " estoque est� em 0!");
		}
	}

	// se produto quantidade em estoque for maior que produto quantidade
	public void validaQuantdEstoqueComProdutosVendidosEscolhido(
			Produtos produto, ProdutosVendidos produtosVendidos,
			Venda UltimaVenda) throws VendaInvalidaException {

		if (produto.getProQuantidade() < produtosVendidos
				.getProVendquantidades()) {

			// deleta venda
			vendaDao.deletar(UltimaVenda.getVenId());

			// joga a exception
			throw new VendaInvalidaException("Para o produto '" + produto.getProNome()
					+ "' (id = " + produto.getProId() + ")"
					+ " tem quantidade indispon�vel, em estoque ("
					+ produto.getProQuantidade() + ") escolhido ("
					+ produtosVendidos.getProVendquantidades()
					+ "), pedido tem que ser <= quantidade dispon�vel!");
		}

	}
	
	// verifica se a lista recebida pelo formul�ria para se est� vazia
		// se estiver joga uma Exception
		public void validaSeExisteSelecValido(List<Venda> vendas)
				throws ExcluirSelecInvalidoException {

			if (vendas == null) {

				throw new ExcluirSelecInvalidoException(
						"Para excluir venda(s) voc� deve selecionar pelo o menos um!");
			}
		}

}
