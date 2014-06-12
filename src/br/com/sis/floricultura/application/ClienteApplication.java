package br.com.sis.floricultura.application;

import java.util.List;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.sis.floricultura.dao.ClienteDao;
import br.com.sis.floricultura.dao.EnderecoDao;
import br.com.sis.floricultura.exception.EmailInvalidoException;
import br.com.sis.floricultura.exception.ExcluirSelecInvalidoException;
import br.com.sis.floricultura.exception.RgInvalidoException;
import br.com.sis.floricultura.model.Cliente;
import br.com.sis.floricultura.model.Endereco;
import br.com.sis.floricultura.service.ClienteService;

@Component
public class ClienteApplication {

	private final Integer REGISTROS_POR_PAGINA = 4;
	private ClienteDao clienteDao;
	private EnderecoDao enderecoDao;
	private ClienteService clienteService;

	public ClienteApplication() {
		clienteDao = new ClienteDao();
		clienteService = new ClienteService(clienteDao);
		enderecoDao = new EnderecoDao();

	}

	public List<Cliente> listarClientes(Integer pagAtual, String nomePaginacao,
			String nomeBusca) {

		return clienteDao.listarClientes(REGISTROS_POR_PAGINA, pagAtual,
				nomePaginacao, nomeBusca);
	}

	public Integer numeroDePaginacao(String nomePaginacao, String nomeBusca) {

		return clienteDao.numeroDePaginacao(REGISTROS_POR_PAGINA,
				nomePaginacao, nomeBusca);

	}

	public void excluirCliente(Integer idCliente) {
		// carrega objeto do tipo cliente
		Cliente cliente = clienteDao.carregar(idCliente);

		// exclui cliente
		clienteDao.deletar(idCliente);

		// carrega objeto do tipo endere�o
		Endereco endereco = enderecoDao.carregar(cliente.getEnderecoEndId()
				.getEndId());

		// exclui endere�o
		enderecoDao.deletar(endereco.getEndId());

	}

	public void excluirClienteSelecionados(List<Cliente> clientes)
			throws ExcluirSelecInvalidoException {

		// valida��o
		clienteService.validaSeExisteSelecValido(clientes);

		for (Cliente cliente : clientes) {

			// carrega objeto do tipo cliente
			cliente = clienteDao.carregar(cliente.getCliId());

			// exclui cliente
			clienteDao.deletar(cliente.getCliId());

			// exclui endere�o
			enderecoDao.deletar(cliente.getEnderecoEndId().getEndId());
		}
	}

	public void salvarCliente(Cliente cliente, Endereco endereco)
			throws EmailInvalidoException, RgInvalidoException {

		// valida��o se email j� existe
		clienteService.clienteEmailExisteAdicionar(cliente);

		// valida��o se rg j� existe tbm
		clienteService.clienteRgExisteAdicionar(cliente);

		// adiciona endere�o
		enderecoDao.adicionar(endereco);

		/*
		 * define atributo do cliente vinculando o �ltimo endere�o adicionado a
		 * ele
		 */

		// define atributo
		cliente.setEnderecoEndId(enderecoDao.buscarUltimoEndereco());

		// adiciona cliente
		clienteDao.adicionar(cliente);

	}

	public void atualizarCliente(Cliente cliente, Endereco endereco)
			throws EmailInvalidoException, RgInvalidoException {

		// valida��o se email j� existe
		clienteService.clienteEmailExisteEditar(cliente);

		// valida��o se rg j� existe tbm
		clienteService.clienteRgExisteEditar(cliente);

		// atualiza endere�o
		enderecoDao.atualizar(endereco);

		// carrega objeto do tipo endere�o
		Endereco objEndereco = enderecoDao.carregar(endereco.getEndId());

		// define atributo
		cliente.setEnderecoEndId(objEndereco);

		// atualiza cliente
		clienteDao.atualizar(cliente);
	}

	public List<Cliente> buscarClientes(String term) {

		return clienteDao.buscarClientes(term);
	}

	public Cliente carregaCliente(Integer idCliente) {

		return clienteDao.carregar(idCliente);
	}

	public String numeroTotalClientes() {

		return clienteDao.numeroClientes();
	}

	public String numeroRegistroFornecedorBuscar(String busca) {

		return clienteDao.numeroDeRegistrosBusca(busca);
	}
}
