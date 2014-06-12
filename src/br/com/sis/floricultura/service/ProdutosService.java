package br.com.sis.floricultura.service;

import java.util.List;

import br.com.sis.floricultura.exception.ExcluirSelecInvalidoException;
import br.com.sis.floricultura.model.Fornecedor;
import br.com.sis.floricultura.model.Produtos;

public class ProdutosService {

	public ProdutosService() {

	}

	// verifica se a lista recebida pelo formul�ria para se est� vazia
	// se estiver joga uma Exception
	public void validaSeExisteSelecValido(List<Produtos> listaProdutos)
			throws ExcluirSelecInvalidoException {

		if (listaProdutos == null) {
			throw new ExcluirSelecInvalidoException(
					"Para excluir produto(s) voc� deve selecionar pelo o menos um!");
		}
	}

}
