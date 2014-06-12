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

		// carrega objeto do tipo endereço
		Endereco endereco = enderecoDao.carregar(cliente.getEnderecoEndId()
				.getEndId());

		// exclui endereço
		enderecoDao.deletar(endereco.getEndId());

	}

	public void excluirClienteSelecionados(List<Cliente> clientes)
			throws ExcluirSelecInvalidoException {

		// validação
		clienteService.validaSeExisteSelecValido(clientes);

		for (Cliente cliente : clientes) {

			// carrega objeto do tipo cliente
			cliente = clienteDao.carregar(cliente.getCliId());

			// exclui cliente
			clienteDao.deletar(cliente.getCliId());

			// exclui endereço
			enderecoDao.deletar(cliente.getEnderecoEndId().getEndId());
		}
	}

	public void salvarCliente(Cliente cliente, Endereco endereco)
			throws EmailInvalidoException, RgInvalidoException {

		// validação se email já existe
		clienteService.clienteEmailExisteAdicionar(cliente);

		// validação se rg já existe tbm
		clienteService.clienteRgExisteAdicionar(cliente);

		// adiciona endereço
		enderecoDao.adicionar(endereco);

		/*
		 * define atributo do cliente vinculando o último endereço adicionado a
		 * ele
		 */

		// define atributo
		cliente.setEnderecoEndId(enderecoDao.buscarUltimoEndereco());

		// adiciona cliente
		clienteDao.adicionar(cliente);

	}

	public void atualizarCliente(Cliente cliente, Endereco endereco)
			throws EmailInvalidoException, RgInvalidoException {

		// validação se email já existe
		clienteService.clienteEmailExisteEditar(cliente);

		// validação se rg já existe tbm
		clienteService.clienteRgExisteEditar(cliente);

		// atualiza endereço
		enderecoDao.atualizar(endereco);

		// carrega objeto do tipo endereço
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
