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
 framework se encarregará de gerenciar suas instâncias,isto é, ele criará uma instância
 e utilizará como argumento para o construtor controller.
 */

@Component
public class UsuarioDao extends Dao<Usuario> {

	// variável gerenciador de entidades
	EntityManager gerenciadorEntidades;

	public UsuarioDao() {

	}// contrutor padrão UsuarioDao

	// obj: adiciona Usuário
	@Override
	public void adicionar(Usuario usuario) {

		// entrega instância do tipo EntityManager com conexão postgres
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		try {

			// inicia a transação
			gerenciadorEntidades.getTransaction().begin();

			// adiciona usuário
			gerenciadorEntidades.persist(usuario);

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

	// exlui usuário
	@Override
	public void deletar(Integer id) {

		// entrega instância do tipo EntityManager com conexão postgres
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();
		try {

			// inicia a transação
			gerenciadorEntidades.getTransaction().begin();

			// carrega objeto do tipo usuario
			Usuario usuario = gerenciadorEntidades.find(Usuario.class, id);

			// excluir usuário
			gerenciadorEntidades.remove(usuario);

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

	// obj: Carrega objeto do tipo Usuario
	public Usuario carregar(Integer id) {

		// entrega instância do tipo EntityManager com conexão postgres
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// carrega objeto do tipo Usuario
		Usuario usuario = gerenciadorEntidades.find(Usuario.class, id);

		// fecha conexão
		gerenciadorEntidades.close();

		// retorna cliente
		return usuario;
	}

	// obj: atualiza usuário
	@Override
	public void atualizar(Usuario usuario) {

		// entrega instância do tipo EntityManager com conexão postgres
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		try {

			// inicia a transação
			gerenciadorEntidades.getTransaction().begin();

			// atualiza usuário
			gerenciadorEntidades.merge(usuario);

			// transação com sucesso
			gerenciadorEntidades.getTransaction().commit();

		} catch (Exception e) {

			// transação ocorreu erro
			gerenciadorEntidades.getTransaction().rollback(); 

			// mostra erro
			e.printStackTrace();

		}// fim catch e try

		// fecha conexao
		gerenciadorEntidades.close();

	}// fim update()

	// obj: listar usuários
	@Override
	public List<Usuario> listarTodos() {
		return null;

	}// fim listarTodos()

	// obj: verificar se já existe nome de usuário
	// Tem diferenciação de (A) para (a) ao validar
	// validação de usuário, se nome de usuário já existe ou não
	public boolean validaUsuarioAdicionar(Usuario usuario) {

		// entrega instância do tipo EntityManager com conexão postgres
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		Query query = gerenciadorEntidades
				.createNamedQuery("Usuario.findUsuNome");

		// define pârametro
		query.setParameter("usuNome", usuario.getUsuNome());

		// verifica
		if (query.getResultList().size() != 0) {
			return true;// usuário já registrado
		}// fim if

		// fecha conexão
		gerenciadorEntidades.close();

		return false;// usuário não registrado

	}// fim validaUsuario()

	// obj: verificar se já existe nome de usuário
	// Tem diferenciação de (A) para (a) ao validar
	// validação de usuário, se nome de usuário já existe ou não
	public boolean validaUsuarioEditar(Usuario usuario) {

		// entrega instância do tipo EntityManager com conexão postgres
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		TypedQuery<Usuario> query = gerenciadorEntidades.createNamedQuery(
				"Usuario.findAllNotInUsuId", Usuario.class);

		// define parâmetro
		query.setParameter("usuId", usuario.getUsuId());

		// / define lista de usuários
		List<Usuario> listaUsuarios = query.getResultList();

		// verifica
		boolean existeUsuario = false;// usuário não registrado

		for (Usuario usuarioLista : listaUsuarios) {

			if (usuarioLista.getUsuNome().equals(usuario.getUsuNome())) {

				existeUsuario = true;// usuário já registrado

			} // fim if

		}// fim for

		// fecha conexão
		gerenciadorEntidades.close();

		// retorna boolean
		return existeUsuario;

	}// fim validaUsuario()

	// obj: recuperar o último id de usuário adicionado
	public Integer buscarUltimoUsuario() {

		// entrega instância do tipo EntityManager com conexão postgres
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		Query query = gerenciadorEntidades
				.createNamedQuery("Usuario.findMaxUsuId");

		// recupera número
		Integer numero = (Integer) query.getResultList().get(0);

		// fecha conexão
		gerenciadorEntidades.close();

		// retorna id
		return numero;

	}// fim buscarUltimoUsuario()

	// obj: carrega usuário, usando no login do sistema
	public Usuario carregaUsuario(Usuario usuario) {

		// entrega instância do tipo EntityManager com conexão postgres
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		Query query = gerenciadorEntidades
				.createNamedQuery("Usuario.findUsuNomeSenha",Usuario.class);
 
		// define parâmetro
		query.setParameter("usuNome", usuario.getUsuNome());

		// define parâmetro
		query.setParameter("usuSenha", usuario.getUsuSenha());

		// verifica se a lista não está vazia
		if (!query.getResultList().isEmpty()) {

			// existe
			// carrega usuário
			usuario = (Usuario) query.getResultList().get(0);

		} else {

			// não existe
			// carrega com null
			usuario = null;
		}
		
		// retorna usuário
		return usuario;

	}// fim carregaUsuario()

	
	// obj: retorna o número total de usuários no sistema
	public String numeroUsuarios() {

		// entrega instância do tipo EntityManager com conexão postgres
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		//consulta
		Query query = gerenciadorEntidades
				.createNamedQuery("Usuario.numeroUsuarios");

		//retorna número total
		return query.getResultList().get(0).toString();

	}// fim numeroUsuarios()

}// fim class UsuarioDao
