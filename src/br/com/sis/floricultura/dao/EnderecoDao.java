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
 framework se encarregar� de gerenciar suas inst�ncias,isto �, ele criar� uma inst�ncia
 e utilizar� como argumento para o construtor controller.
 */

@Component
public class EnderecoDao extends Dao<Endereco> {

	// vari�vel gerenciador de entidades
	private EntityManager gerenciadorEntidades;

	public EnderecoDao() {

	}// contrutor padr�o EnderecoDao

	// obj: adicionar endere�o
	@Override
	public void adicionar(Endereco endereco) {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();
		try {

			// inicia a transa��o
			gerenciadorEntidades.getTransaction().begin();

			// adiciona propriet�rio
			gerenciadorEntidades.persist(endereco);

			// transa��o com sucesso
			gerenciadorEntidades.getTransaction().commit();

		} catch (Exception e) {

			// transa��o ocorreu erro
			gerenciadorEntidades.getTransaction().rollback();
 
			// mostra erro
			e.printStackTrace();

		}// fim catch e try

		// fecha conex�o
		gerenciadorEntidades.close();

	}// fim add()

	// obj: recuperar o �ltimo id endere�o adicionado
	public Endereco buscarUltimoEndereco() {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// cosulta
		Query query = gerenciadorEntidades
				.createNamedQuery("Endereco.findUltimoEndereco");

		// define id endere�o
		Integer enderecoId = (Integer) query.getResultList().get(0);

		// carrega objeto do tipo Endere�o
		Endereco endereco = gerenciadorEntidades.find(Endereco.class,
				enderecoId);

		// o �ltimo id endere�o adicionado
		return endereco;

	}// fim buscarUltimoUsuario()

	// obj: carregar objeto do tipo Endere�o
	@Override
	public Endereco carregar(Integer id) {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// carrega objeto do tipo endere�o
		Endereco endereco = gerenciadorEntidades.find(Endereco.class, id);

		// retorna objeto
		return endereco;

	}// fim loadEndereco()

	// obj: excluir endere�o
	@Override
	public void deletar(Integer id) {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();
		
		try {

			// inicia a transa��o
			gerenciadorEntidades.getTransaction().begin();

			// carrega objeto do tipo Endereco
			Endereco endereco = gerenciadorEntidades.find(Endereco.class, id);

			// excluir propriet�rio
			gerenciadorEntidades.remove(endereco);

			// transa��o com sucesso
			gerenciadorEntidades.getTransaction().commit();

		} catch (Exception e) {

			// transa��o ocorreu erro
			gerenciadorEntidades.getTransaction().rollback();
 

			// mostra erro
			e.printStackTrace();

		}// fim catch e try

		// fecha conex�o
		gerenciadorEntidades.close();

	}// fim delete()

	// obj: atuliza endereco
	@Override
	public void atualizar(Endereco endereco) {
		
		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();
		
		try {

			// inicia a transa��o
			gerenciadorEntidades.getTransaction().begin();

			// atuliza propritario
			gerenciadorEntidades.merge(endereco);

			// transa��o com sucesso
			gerenciadorEntidades.getTransaction().commit();

		} catch (Exception e) {

			// transa��o ocorreu erro
			gerenciadorEntidades.getTransaction().rollback();
 

			// mostra erro
			e.printStackTrace();

		}// fim catch e try

		// fecha
		gerenciadorEntidades.close();

	}// fim update()

	//obj: listar endere�o
	@Override
	public List<Endereco> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}// fim  listarTodos()

}// fim class EnderecoDao
