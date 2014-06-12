//pacote
package br.com.sis.floricultura.dao;

//import's
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.JOptionPane;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sis.floricultura.connection.FabricaDeConexao;
import br.com.sis.floricultura.model.ProdutosVendidos;
import br.com.sis.floricultura.model.Venda;

/*
 Esta classe (VendaDao) anotada com @Component notifica para o Vraptor que o 
 framework se encarregará de gerenciar suas instâncias,isto é, ele criará uma instância
 e utilizará como argumento para o construtor controller.
 */

@Component
public class ProdutosVendidosDao extends Dao<ProdutosVendidos> {

	// variável gerenciador de entidades
	private EntityManager gerenciadorEntidades;

	public ProdutosVendidosDao() {

	}// contrutor padrão ProprietarioDao

	// obj: adiciona ProdutosVendidos
	@Override
	public void adicionar(ProdutosVendidos produtosVendidos) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		try {

			// inicia a transação
			gerenciadorEntidades.getTransaction().begin();

			// adiciona proprietário
			gerenciadorEntidades.persist(produtosVendidos);

			// transação com sucesso
			gerenciadorEntidades.getTransaction().commit();

		} catch (Exception e) {

			// transação ocorreu erro
			gerenciadorEntidades.getTransaction().rollback();
 
			// mostra erro
			e.printStackTrace();

		}// fim catch e try

		// fecha conexão
		gerenciadorEntidades.close();

	}// fim add()

	// obj: carregar objeto do tipo ProdutosVendidos
	@Override
	public ProdutosVendidos carregar(Integer i) {

		return null;
	}// fim ProdutosVendidos()

	// obj: deletar ProdutosVendidos
	@Override
	public void deletar(Integer id) {

	}// fim deletar()

	// obj: atualziar ProdutosVendidos
	@Override
	public void atualizar(ProdutosVendidos t) {

	}// fim atualizar()

	// obj: retornar uma lista de ProdutosVendidos
	@Override
	public List<ProdutosVendidos> listarTodos() {

		return null;
	}// fim listarTodos()

}// fim class ProdutosVendidosDao
