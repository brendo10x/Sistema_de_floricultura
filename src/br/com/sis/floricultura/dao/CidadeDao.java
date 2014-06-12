//pacote
package br.com.sis.floricultura.dao;

//import's
import java.util.List;

import javax.persistence.EntityManager;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sis.floricultura.connection.FabricaDeConexao;
import br.com.sis.floricultura.model.Cidade;
import br.com.sis.floricultura.model.Estado;
import br.com.sis.floricultura.model.Usuario;

/*
 Esta classe (CidadeDao) anotada com @Component notifica para o Vraptor que o 
 framework se encarregará de gerenciar suas instâncias,isto é, ele criará uma instância
 e utilizará como argumento para o construtor controller.
 */

@Component
public class CidadeDao {

	// variável gerenciador de entidades
	private EntityManager gerenciadorEntidades;

	// construtor padrão
	public CidadeDao() {

	}// fim do construtor

	// obj: lista todas as cidades de acordo com o id passado
	public List<Cidade> listaDeCidades(Integer idEstado) {

		// entrega uma instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// carrega objeto do tipo Estado
		Estado estado = gerenciadorEntidades.find(Estado.class, idEstado);
		
		// carrega uma lista de cidades de acordo com estado
		List<Cidade> listaCidade = estado.getCidadeList();

		// retorna uma lista de cidades
		return listaCidade;

	}// fim listaDeCidades()

	// obj: carrega objeto do tipo Cidade de acordo com o id passado
	public Cidade carrega(Integer id) {
		
		// entrega uma instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();
		
		// carrega objeto do tipo Cidade
		 Cidade cidade = gerenciadorEntidades.find(Cidade.class, id);
		 
		// fecha conexão
		gerenciadorEntidades.close();
		
		//retorna objeto
		return cidade;

	}// fim load()

}// fim class CidadeDao()
