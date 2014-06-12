package br.com.sis.floricultura.service;

import java.util.List;

import br.com.sis.floricultura.dao.ClienteDao;
import br.com.sis.floricultura.exception.EmailInvalidoException;
import br.com.sis.floricultura.exception.ExcluirSelecInvalidoException;
import br.com.sis.floricultura.exception.RgInvalidoException;
import br.com.sis.floricultura.model.Cliente;
import br.com.sis.floricultura.model.Fornecedor;

public class ClienteService {

	private ClienteDao clienteDao;

	public ClienteService(ClienteDao clienteDao) {
		this.clienteDao = clienteDao;
	}

	public void clienteEmailExisteAdicionar(Cliente cliente)
			throws EmailInvalidoException {

		if (clienteDao.validaEmailAdicionar(cliente)) {

			throw new EmailInvalidoException("Email '" + cliente.getCliEmail()
					+ "' já existe no adicionar!");

		}

	}

	public void clienteEmailExisteEditar(Cliente cliente)
			throws EmailInvalidoException {

		if (clienteDao.validaEmailEditar(cliente))

			throw new EmailInvalidoException("Email '" + cliente.getCliEmail()
					+ "' já existe no editar!");

	}

	public void clienteRgExisteAdicionar(Cliente cliente)
			throws RgInvalidoException {

		if (clienteDao.validaRgAdicionar(cliente)) {
			throw new RgInvalidoException("Rg '" + cliente.getCliRg()
					+ "' já existe no adicionar!");
		}
	}

	public void clienteRgExisteEditar(Cliente cliente)
			throws RgInvalidoException {

		if (clienteDao.validaRgEditar(cliente)) {
			throw new RgInvalidoException("Rg '" + cliente.getCliRg()
					+ "' já existe no editar!");
		}
	}

	// verifica se a lista recebida pelo formulária para se está vazia
	// se estiver joga uma Exception
	public void validaSeExisteSelecValido(List<Cliente> listaClientes)
			throws ExcluirSelecInvalidoException {

		if (listaClientes == null)

			throw new ExcluirSelecInvalidoException(
					"Para excluir cliente(s) você deve selecionar pelo o menos um!");

	}

}
