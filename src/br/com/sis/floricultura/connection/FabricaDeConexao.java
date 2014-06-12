// pacote
package br.com.sis.floricultura.connection;

// import's
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/* A classe estática ConnectionFactory responsável por invocar as métodos com as 
 conexões com os bancos de dados, tanto com o mysql quanto o do postgres
 */

public class FabricaDeConexao {

	// variáveis de fábricas de gerenciador de entidades
	private static EntityManagerFactory fabricaMysql = null,
			fabricaPostgres = null;

	private FabricaDeConexao() {

	}// fim construtor padrão

	/*
	 * Recupera gerenciador de entidades com conexão com o banco de dado mysql
	 */

	public static EntityManager getInstanciaConexaoMysql() {

		// verifica se está null
		if (fabricaMysql == null) {
			// gera gerenciador de entidades
			fabricaMysql = Persistence
					.createEntityManagerFactory("ConexaoMysql");
		}

		// retorna gerenciador de entidades
		// retornando sempre um única instância
		return fabricaMysql.createEntityManager();

	}// fim getInstanciaConexaoMysql()

	 

}// fim class ConnectionFactory
