// pacote
package br.com.sis.floricultura.dao;

// import
import java.util.List;

import br.com.sis.floricultura.model.Usuario;

/*
 Classe abstract Dao
 */

public abstract class Dao<Tipo> {

	// m�todo adiciona
	public abstract void adicionar(Tipo t);

	// m�todo exluir
	public abstract void deletar(Integer i);

	// m�todo atualiza
	public abstract void atualizar(Tipo t);

	// m�todo listar todos
	public abstract List<Tipo> listarTodos();

	// m�todo carrega objeto
	public abstract Tipo carregar(Integer i);

}// fim class Dao
