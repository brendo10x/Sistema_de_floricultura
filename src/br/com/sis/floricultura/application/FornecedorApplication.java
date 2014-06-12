package br.com.sis.floricultura.application;

import java.util.List;

import javax.swing.JOptionPane;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sis.floricultura.dao.CidadeDao;
import br.com.sis.floricultura.dao.EnderecoDao;
import br.com.sis.floricultura.dao.FornecedorDao;
import br.com.sis.floricultura.exception.ExcluirSelecInvalidoException;
import br.com.sis.floricultura.model.Cidade;
import br.com.sis.floricultura.model.Endereco;
import br.com.sis.floricultura.model.Fornecedor;
import br.com.sis.floricultura.service.FornecedorService;

@Component
public class FornecedorApplication {

	private FornecedorDao fornecedorDao;
	private EnderecoDao enderecoDao;
	private CidadeDao cidadeDao;
	private final Integer REGISTROS_POR_PAGINA = 4;
	private FornecedorService fornecedorService;

	public FornecedorApplication() {
		super();
		fornecedorDao = new FornecedorDao();
		enderecoDao = new EnderecoDao();
		cidadeDao = new CidadeDao();
		fornecedorService = new FornecedorService();
	}// fim construtor

	public void adicionarFornecedor(Fornecedor fornecedor, Endereco endereco) {

		// carrega objeto do tipo cidade
		Cidade cidade = cidadeDao.carrega(endereco.getCidadeCidId().getCidId());

		// define atributo
		endereco.setCidadeCidId(cidade);

		// adiciona endereço
		enderecoDao.adicionar(endereco);

		// define atributo
		fornecedor.setEnderecoEndId(enderecoDao.buscarUltimoEndereco());

		// adiciona fornecedor
		fornecedorDao.adicionar(fornecedor);

	}// fim salvarFornecedor()

	public List<Fornecedor> listarFornecedores(Integer pagAtual,
			String nomePaginacao, String nomeBusca) {

		return fornecedorDao.listarFornecedores(REGISTROS_POR_PAGINA, pagAtual,
				nomePaginacao, nomeBusca);
	}

	public Integer numeroDePaginacao(String nomePaginacao, String nomeBusca) {

		return fornecedorDao.numeroDePaginacao(REGISTROS_POR_PAGINA,
				nomePaginacao, nomeBusca);

	}

	public void excluirFornecedor(Integer idFornecedor) {
		// carrega objeto do tipo fornecedor
		Fornecedor fornecedor = fornecedorDao.carregar(idFornecedor);

		// exclui fornecedor
		fornecedorDao.deletar(idFornecedor);

		// exclui endereço
		enderecoDao.deletar(fornecedor.getEnderecoEndId().getEndId());

	}

	public void excluirFornecedorSelecionados(List<Fornecedor> listaFornecedor)
			throws ExcluirSelecInvalidoException {

		
		// valida se existe selecionado
		fornecedorService.validaSeExisteSelecValido(listaFornecedor);

		for (Fornecedor fornecedor : listaFornecedor) {

			// carrega objeto do tipo fornecedor
			Fornecedor fornecedorEncontrado = fornecedorDao.carregar(fornecedor
					.getForId());

			// exclui fornecedor
			fornecedorDao.deletar(fornecedor.getForId());

			// exclui endereço
			enderecoDao.deletar(fornecedorEncontrado.getEnderecoEndId()
					.getEndId());
		}

	}

	public void atualizaFornecedor(Fornecedor fornecedor, Endereco endereco) {
		// atualiza endereço
		enderecoDao.atualizar(endereco);

		// carrega objeto do tipo endereço
		endereco = enderecoDao.carregar(endereco.getEndId());

		// define atributo
		fornecedor.setEnderecoEndId(endereco);

		// atualiza fornecedor
		fornecedorDao.atualizar(fornecedor);

	}

	public Fornecedor carregaFornecedor(Integer idFornecedor) {

		return fornecedorDao.carregar(idFornecedor);

	}

	public List<Fornecedor> buscarFornecedores(String term) {

		return fornecedorDao.buscarFornecedores(term);
	}

	public String numeroTotalFornecedores() {

		return fornecedorDao.numeroFornecedores();

	}
	
	public String numeroRegistroFornecedorBuscar(String busca){
		
		return fornecedorDao.numeroDeRegistrosBusca(busca);
	}

}// fim class FornecedorApplication
