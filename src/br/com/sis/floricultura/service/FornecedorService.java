package br.com.sis.floricultura.service;

import java.util.List;

import javax.swing.JOptionPane;

import br.com.sis.floricultura.exception.ExcluirSelecInvalidoException;
import br.com.sis.floricultura.model.Fornecedor;

public class FornecedorService {

	public FornecedorService() {

	}

	// verifica se a lista recebida pelo formulária para se está vazia
	// se estiver joga uma Exception
	public void validaSeExisteSelecValido(List<Fornecedor> listaFornecedores)
			throws ExcluirSelecInvalidoException {

		
		if (listaFornecedores == null) {
		
			throw new ExcluirSelecInvalidoException(
					"Para excluir fornecedor(es) você deve selecionar pelo o menos um!");
		}
	}
}
