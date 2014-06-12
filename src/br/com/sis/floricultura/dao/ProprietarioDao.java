//pacote
package br.com.sis.floricultura.dao;

//import's
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.JOptionPane;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sis.floricultura.connection.FabricaDeConexao;
import br.com.sis.floricultura.model.Proprietario;

/*
 Esta classe (ProprietarioDao) anotada com @Component notifica para o Vraptor que o 
 framework se encarregará de gerenciar suas instâncias,isto é, ele criará uma instância
 e utilizará como argumento para o construtor controller.
 */

@Component
public class ProprietarioDao extends Dao<Proprietario> {

	// variável gerenciador de entidades
	private EntityManager gerenciadorEntidades;

	public ProprietarioDao() {

	}// contrutor padrão ProprietarioDao

	// obj: adiciona proprietário
	@Override
	public void adicionar(Proprietario proprietario) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		try {

			// inicia a transação
			gerenciadorEntidades.getTransaction().begin();

			// adiciona proprietário
			gerenciadorEntidades.persist(proprietario);

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

	// obj: excluir proprietário
	@Override
	public void deletar(Integer id) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		try {

			// inicia a transação
			gerenciadorEntidades.getTransaction().begin();

			// carrega objeto do tipo Proprietario
			Proprietario proprietario = gerenciadorEntidades.find(
					Proprietario.class, id);

			// excluir proprietário
			gerenciadorEntidades.remove(proprietario);

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

	// obj: Carrega objeto do tipo Proprietario
	public Proprietario carregar(Integer id) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// carrega objeto do tipo Proprietario
		Proprietario proprietario = gerenciadorEntidades.find(
				Proprietario.class, id);

		// retorna proprietario
		return proprietario;

	}// fim loadProprietario()

	// obj: atuliza propritario
	@Override
	public void atualizar(Proprietario proprietario) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		try {

			// inicia a transação
			gerenciadorEntidades.getTransaction().begin();

			// atuliza propritario
			gerenciadorEntidades.merge(proprietario);

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

	}// fim update()

	// obj: listar proprietários
	@Override
	public List<Proprietario> listarTodos() {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		TypedQuery<Proprietario> query = gerenciadorEntidades.createQuery(
				"FROM Proprietario ORDER BY proId DESC", Proprietario.class);

		// define lista de Proprietarios
		List<Proprietario> listaProprietarios = query.getResultList();

		// fecha conexão
		gerenciadorEntidades.close();

		// retorna uma lista de Proprietarios
		return listaProprietarios;

	}// fim listAll()

	// obj: Lista todos os Proprietarios de acordo com os parâmetros
	@SuppressWarnings("unchecked")
	public List<Proprietario> listarProprietarios(Integer porPagina,
			Integer pagAtual, String nomePaginacao, String nomeBusca) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// define offset
		Integer offset = PaginacaoAuxiliarDao.getOffset(porPagina, pagAtual);

		// inicializa
		String sqlNomeClausula = "";

		// verificação se a opção é busca
		if (nomePaginacao.equalsIgnoreCase("BUSCA")) {
			sqlNomeClausula = "WHERE pro_nome like '%" + nomeBusca + "%'";
		}// fim if

		// verificação se a opção é normal
		if (nomePaginacao.equalsIgnoreCase("NORMAL")) {
			sqlNomeClausula = "";
		}// fim if

		// define estrutura jpql
		String jpql = "SELECT * FROM proprietario " + sqlNomeClausula
				+ " LIMIT " + porPagina + " OFFSET " + offset;

		// consulta
		Query query = gerenciadorEntidades.createNativeQuery(jpql,
				Proprietario.class);

		// define lista de proprietarios
		List<Proprietario> listaProprietarios = query.getResultList();

		// retorna uma lista de proprietarios
		return listaProprietarios;

	}// fim listarProprietarios()

	// retorna o número de paginações
	public Integer numeroDePaginacao(int porPagina, String nomePaginacao,
			String nomeBusca) {

		// inicializa
		String sqlNomeClausula = "";

		// verificação se a opção é busca
		if (nomePaginacao.equalsIgnoreCase("BUSCA")) {
			sqlNomeClausula = "WHERE pro_nome like '%" + nomeBusca + "%'";
		}// fim if

		// verificação se a opção é normal
		if (nomePaginacao.equalsIgnoreCase("NORMAL")) {
			sqlNomeClausula = "";
		}// fim if

		// retorna o número de paginações
		return PaginacaoAuxiliarDao.numeroDePaginacao("Proprietario", porPagina,
				sqlNomeClausula);

	}// fim numeroDePaginacao()

	// obj: carregar proprietário com id usuário, para recuperar o nome
	public Proprietario obterProprietarioUsuarioId(Integer idUsuario) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		TypedQuery<Proprietario> query = gerenciadorEntidades.createNamedQuery(
				"Proprietario.usuarioId", Proprietario.class);

		// define parâmetro
		query.setParameter("idUsuario", idUsuario);

		// carrega proprietário
		Proprietario Proprietario = query.getResultList().get(0);

		// fecha conexão
		gerenciadorEntidades.close();

		// retorna o nome do proprietário
		return Proprietario;

	}// fim obterNomeProprietarioUsuarioId()

}// fim class ProprietarioDao
