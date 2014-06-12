package br.com.sis.floricultura.service;

import java.util.List;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.sis.floricultura.dao.VendedorDao;
import br.com.sis.floricultura.exception.CpfInvalidoException;
import br.com.sis.floricultura.exception.ExcluirSelecInvalidoException;
import br.com.sis.floricultura.exception.UsuarioInvalidoException;
import br.com.sis.floricultura.model.Fornecedor;
import br.com.sis.floricultura.model.Vendedor;

public class VendedorService {

	private VendedorDao vendedorDao;

	public VendedorService(VendedorDao vendedorDao) {
		this.vendedorDao = vendedorDao;
	}

	public void validaCPFVendedor(String cpf) throws CpfInvalidoException {

		CPFValidator validarCPF = new CPFValidator();

		try {
			validarCPF.assertValid(cpf);
		} catch (Exception e) {
			throw new CpfInvalidoException("CPF '" + cpf + "' inválido!");
		}

	}

	public void validaCPFVendedorEditar(Vendedor vendedor)
			throws CpfInvalidoException {

		if (vendedorDao.validaCpfEditar(vendedor)) {

			throw new CpfInvalidoException("CPF '" + vendedor.getVenCpf()
					+ "' já existe no editar!");
		}
	}

	public void validaCPFVendedorAdicionar(Vendedor vendedor)
			throws CpfInvalidoException {

		if (vendedorDao.validaCpfAdicionar(vendedor)) {

			throw new CpfInvalidoException("CPF '" + vendedor.getVenCpf()
					+ "' já existe no adicionar!");
		}

	}

	// verifica se a lista recebida pelo formulária para se está vazia
	// se estiver joga uma Exception
	public void validaSeExisteSelecValido(List<Vendedor> vendedores)
			throws ExcluirSelecInvalidoException {

		if (vendedores == null) {

			throw new ExcluirSelecInvalidoException(
					"Para excluir vendedor(es) você deve selecionar pelo o menos um!");
		}
	}

}
