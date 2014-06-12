// pacote
package br.com.sis.floricultura.dao;

// import
import java.util.List;

import br.com.sis.floricultura.model.Usuario;

/*
 Classe abstract Dao
 */

public abstract class Dao<Tipo> {

	// método adiciona
	public abstract void adicionar(Tipo t);

	// método exluir
	public abstract void deletar(Integer i);

	// método atualiza
	public abstract void atualizar(Tipo t);

	// método listar todos
	public abstract List<Tipo> listarTodos();

	// método carrega objeto
	public abstract Tipo carregar(Integer i);

}// fim class Dao
