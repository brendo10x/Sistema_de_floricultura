package br.com.sis.floricultura.application;

import java.util.List;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sis.floricultura.dao.ProdutoDao;
import br.com.sis.floricultura.exception.ExcluirSelecInvalidoException;
import br.com.sis.floricultura.model.Produtos;
import br.com.sis.floricultura.service.ProdutosService;

@Component
public class ProdutosApplication {

	private ProdutoDao produtoDao;
	private final Integer REGISTROS_POR_PAGINA = 4;
	private ProdutosService produtosService;

	public ProdutosApplication() {
		super();
		produtoDao = new ProdutoDao();
		produtosService = new ProdutosService();
	}

	public List<Produtos> listarProdutos(Integer pagAtual,
			String nomePaginacao, String nomeBusca) {

		return produtoDao.listarProdutos(REGISTROS_POR_PAGINA, pagAtual,
				nomePaginacao, nomeBusca);
	}

	public Integer numeroDePaginacao(String nomePaginacao, String nomeBusca) {

		return produtoDao.numeroDePaginacao(REGISTROS_POR_PAGINA,
				nomePaginacao, nomeBusca);

	}

	public void excluirProduto(Integer idProduto) {
		// exclui produto
		produtoDao.deletar(idProduto);

	}

	public void excluirProdutoSelecionados(List<Produtos> listaProdutos)
			throws ExcluirSelecInvalidoException {

		// verificação validação
		produtosService.validaSeExisteSelecValido(listaProdutos);

		for (Produtos produtos : listaProdutos) {
			// exclui produto
			produtoDao.deletar(produtos.getProId());
		}

	}

	public Produtos carregaProduto(Integer idProduto) {

		return produtoDao.carregar(idProduto);
	}

	public void atualizaProduto(Produtos produto) {

		// atualiza produto
		produtoDao.atualizar(produto);

	}

	public List<Produtos> buscarProdutos(String term) {

		return produtoDao.buscarProdutos(term);
	}

	public void salvarProduto(Produtos produto) {
		
		// adiciona produto
		produtoDao.adicionar(produto);

	}

	public String numeroTotalProdutos() {

		return produtoDao.numeroProdutos();
	}
	
public String numeroRegistroClienteBuscar(String busca){
		
		return produtoDao.numeroDeRegistrosBusca(busca);
	}

}
