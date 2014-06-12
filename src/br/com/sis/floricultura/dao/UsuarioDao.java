//pacote
package br.com.sis.floricultura.dao;

//import's
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sis.floricultura.connection.FabricaDeConexao;
import br.com.sis.floricultura.model.Usuario;

/*
 Esta classe (UsuarioDao) anotada com @Component notifica para o Vraptor que o 
 framework se encarregar� de gerenciar suas inst�ncias,isto �, ele criar� uma inst�ncia
 e utilizar� como argumento para o construtor controller.
 */

@Component
public class UsuarioDao extends Dao<Usuario> {

	// vari�vel gerenciador de entidades
	EntityManager gerenciadorEntidades;

	public UsuarioDao() {

	}// contrutor padr�o UsuarioDao

	// obj: adiciona Usu�rio
	@Override
	public void adicionar(Usuario usuario) {

		// entrega inst�ncia do tipo EntityManager com conex�o postgres
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		try {

			// inicia a transa��o
			gerenciadorEntidades.getTransaction().begin();

			// adiciona usu�rio
			gerenciadorEntidades.persist(usuario);

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

	// exlui usu�rio
	@Override
	public void deletar(Integer id) {

		// entrega inst�ncia do tipo EntityManager com conex�o postgres
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();
		try {

			// inicia a transa��o
			gerenciadorEntidades.getTransaction().begin();

			// carrega objeto do tipo usuario
			Usuario usuario = gerenciadorEntidades.find(Usuario.class, id);

			// excluir usu�rio
			gerenciadorEntidades.remove(usuario);

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

	// obj: Carrega objeto do tipo Usuario
	public Usuario carregar(Integer id) {

		// entrega inst�ncia do tipo EntityManager com conex�o postgres
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// carrega objeto do tipo Usuario
		Usuario usuario = gerenciadorEntidades.find(Usuario.class, id);

		// fecha conex�o
		gerenciadorEntidades.close();

		// retorna cliente
		return usuario;
	}

	// obj: atualiza usu�rio
	@Override
	public void atualizar(Usuario usuario) {

		// entrega inst�ncia do tipo EntityManager com conex�o postgres
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		try {

			// inicia a transa��o
			gerenciadorEntidades.getTransaction().begin();

			// atualiza usu�rio
			gerenciadorEntidades.merge(usuario);

			// transa��o com sucesso
			gerenciadorEntidades.getTransaction().commit();

		} catch (Exception e) {

			// transa��o ocorreu erro
			gerenciadorEntidades.getTransaction().rollback(); 

			// mostra erro
			e.printStackTrace();

		}// fim catch e try

		// fecha conexao
		gerenciadorEntidades.close();

	}// fim update()

	// obj: listar usu�rios
	@Override
	public List<Usuario> listarTodos() {
		return null;

	}// fim listarTodos()

	// obj: verificar se j� existe nome de usu�rio
	// Tem diferencia��o de (A) para (a) ao validar
	// valida��o de usu�rio, se nome de usu�rio j� existe ou n�o
	public boolean validaUsuarioAdicionar(Usuario usuario) {

		// entrega inst�ncia do tipo EntityManager com conex�o postgres
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		Query query = gerenciadorEntidades
				.createNamedQuery("Usuario.findUsuNome");

		// define p�rametro
		query.setParameter("usuNome", usuario.getUsuNome());

		// verifica
		if (query.getResultList().size() != 0) {
			return true;// usu�rio j� registrado
		}// fim if

		// fecha conex�o
		gerenciadorEntidades.close();

		return false;// usu�rio n�o registrado

	}// fim validaUsuario()

	// obj: verificar se j� existe nome de usu�rio
	// Tem diferencia��o de (A) para (a) ao validar
	// valida��o de usu�rio, se nome de usu�rio j� existe ou n�o
	public boolean validaUsuarioEditar(Usuario usuario) {

		// entrega inst�ncia do tipo EntityManager com conex�o postgres
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		TypedQuery<Usuario> query = gerenciadorEntidades.createNamedQuery(
				"Usuario.findAllNotInUsuId", Usuario.class);

		// define par�metro
		query.setParameter("usuId", usuario.getUsuId());

		// / define lista de usu�rios
		List<Usuario> listaUsuarios = query.getResultList();

		// verifica
		boolean existeUsuario = false;// usu�rio n�o registrado

		for (Usuario usuarioLista : listaUsuarios) {

			if (usuarioLista.getUsuNome().equals(usuario.getUsuNome())) {

				existeUsuario = true;// usu�rio j� registrado

			} // fim if

		}// fim for

		// fecha conex�o
		gerenciadorEntidades.close();

		// retorna boolean
		return existeUsuario;

	}// fim validaUsuario()

	// obj: recuperar o �ltimo id de usu�rio adicionado
	public Integer buscarUltimoUsuario() {

		// entrega inst�ncia do tipo EntityManager com conex�o postgres
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		Query query = gerenciadorEntidades
				.createNamedQuery("Usuario.findMaxUsuId");

		// recupera n�mero
		Integer numero = (Integer) query.getResultList().get(0);

		// fecha conex�o
		gerenciadorEntidades.close();

		// retorna id
		return numero;

	}// fim buscarUltimoUsuario()

	// obj: carrega usu�rio, usando no login do sistema
	public Usuario carregaUsuario(Usuario usuario) {

		// entrega inst�ncia do tipo EntityManager com conex�o postgres
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		Query query = gerenciadorEntidades
				.createNamedQuery("Usuario.findUsuNomeSenha",Usuario.class);
 
		// define par�metro
		query.setParameter("usuNome", usuario.getUsuNome());

		// define par�metro
		query.setParameter("usuSenha", usuario.getUsuSenha());

		// verifica se a lista n�o est� vazia
		if (!query.getResultList().isEmpty()) {

			// existe
			// carrega usu�rio
			usuario = (Usuario) query.getResultList().get(0);

		} else {

			// n�o existe
			// carrega com null
			usuario = null;
		}
		
		// retorna usu�rio
		return usuario;

	}// fim carregaUsuario()

	
	// obj: retorna o n�mero total de usu�rios no sistema
	public String numeroUsuarios() {

		// entrega inst�ncia do tipo EntityManager com conex�o postgres
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		//consulta
		Query query = gerenciadorEntidades
				.createNamedQuery("Usuario.numeroUsuarios");

		//retorna n�mero total
		return query.getResultList().get(0).toString();

	}// fim numeroUsuarios()

}// fim class UsuarioDao
