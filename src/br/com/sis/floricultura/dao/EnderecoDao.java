//pacote
package br.com.sis.floricultura.dao;

//import's
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.JOptionPane;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sis.floricultura.connection.FabricaDeConexao;
import br.com.sis.floricultura.model.Cliente;
import br.com.sis.floricultura.model.Endereco;
import br.com.sis.floricultura.model.Proprietario;

/*
 Esta classe (EnderecoDao) anotada com @Component notifica para o Vraptor que o 
 framework se encarregará de gerenciar suas instâncias,isto é, ele criará uma instância
 e utilizará como argumento para o construtor controller.
 */

@Component
public class EnderecoDao extends Dao<Endereco> {

	// variável gerenciador de entidades
	private EntityManager gerenciadorEntidades;

	public EnderecoDao() {

	}// contrutor padrão EnderecoDao

	// obj: adicionar endereço
	@Override
	public void adicionar(Endereco endereco) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();
		try {

			// inicia a transação
			gerenciadorEntidades.getTransaction().begin();

			// adiciona proprietário
			gerenciadorEntidades.persist(endereco);

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

	// obj: recuperar o último id endereço adicionado
	public Endereco buscarUltimoEndereco() {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// cosulta
		Query query = gerenciadorEntidades
				.createNamedQuery("Endereco.findUltimoEndereco");

		// define id endereço
		Integer enderecoId = (Integer) query.getResultList().get(0);

		// carrega objeto do tipo Endereço
		Endereco endereco = gerenciadorEntidades.find(Endereco.class,
				enderecoId);

		// o último id endereço adicionado
		return endereco;

	}// fim buscarUltimoUsuario()

	// obj: carregar objeto do tipo Endereço
	@Override
	public Endereco carregar(Integer id) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// carrega objeto do tipo endereço
		Endereco endereco = gerenciadorEntidades.find(Endereco.class, id);

		// retorna objeto
		return endereco;

	}// fim loadEndereco()

	// obj: excluir endereço
	@Override
	public void deletar(Integer id) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();
		
		try {

			// inicia a transação
			gerenciadorEntidades.getTransaction().begin();

			// carrega objeto do tipo Endereco
			Endereco endereco = gerenciadorEntidades.find(Endereco.class, id);

			// excluir proprietário
			gerenciadorEntidades.remove(endereco);

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

	}// fim delete()

	// obj: atuliza endereco
	@Override
	public void atualizar(Endereco endereco) {
		
		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();
		
		try {

			// inicia a transação
			gerenciadorEntidades.getTransaction().begin();

			// atuliza propritario
			gerenciadorEntidades.merge(endereco);

			// transação com sucesso
			gerenciadorEntidades.getTransaction().commit();

		} catch (Exception e) {

			// transação ocorreu erro
			gerenciadorEntidades.getTransaction().rollback();
 

			// mostra erro
			e.printStackTrace();

		}// fim catch e try

		// fecha
		gerenciadorEntidades.close();

	}// fim update()

	//obj: listar endereço
	@Override
	public List<Endereco> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}// fim  listarTodos()

}// fim class EnderecoDao
