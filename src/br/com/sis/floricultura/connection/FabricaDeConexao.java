// pacote
package br.com.sis.floricultura.connection;

// import's
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/* A classe est�tica ConnectionFactory respons�vel por invocar as m�todos com as 
 conex�es com os bancos de dados, tanto com o mysql quanto o do postgres
 */

public class FabricaDeConexao {

	// vari�veis de f�bricas de gerenciador de entidades
	private static EntityManagerFactory fabricaMysql = null,
			fabricaPostgres = null;

	private FabricaDeConexao() {

	}// fim construtor padr�o

	/*
	 * Recupera gerenciador de entidades com conex�o com o banco de dado mysql
	 */

	public static EntityManager getInstanciaConexaoMysql() {

		// verifica se est� null
		if (fabricaMysql == null) {
			// gera gerenciador de entidades
			fabricaMysql = Persistence
					.createEntityManagerFactory("ConexaoMysql");
		}

		// retorna gerenciador de entidades
		// retornando sempre um �nica inst�ncia
		return fabricaMysql.createEntityManager();

	}// fim getInstanciaConexaoMysql()

	 

}// fim class ConnectionFactory
