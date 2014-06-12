//pacote
package br.com.sis.floricultura.dao;

//import's
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.JOptionPane;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sis.floricultura.connection.FabricaDeConexao;
import br.com.sis.floricultura.model.Cliente;
import br.com.sis.floricultura.model.Estado;
import br.com.sis.floricultura.model.Fornecedor;
import br.com.sis.floricultura.model.Proprietario;

/*
 Esta classe (FornecedorDao) anotada com @Component notifica para o Vraptor que o 
 framework se encarregar� de gerenciar suas inst�ncias,isto �, ele criar� uma inst�ncia
 e utilizar� como argumento para o construtor controller.
 */

@Component
public class FornecedorDao extends Dao<Fornecedor> {

	// vari�vel gerenciador de entidades
	private EntityManager gerenciadorEntidades;

	public FornecedorDao() {

	}// contrutor padr�o FornecedorDao

	// obj: adiciona Fornecedor
	@Override
	public void adicionar(Fornecedor fornecedor) {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		try {

			// inicia a transa��o
			gerenciadorEntidades.getTransaction().begin();

			// adiciona propriet�rio
			gerenciadorEntidades.persist(fornecedor);

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
	}// fim add()

	// obj: carrega objeto do tipo Fornecedor
	@Override
	public Fornecedor carregar(Integer id) {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// carrega objeto do tipo fornecedor
		Fornecedor fornecedor = gerenciadorEntidades.find(Fornecedor.class, id);

		// fecha conex�o
		gerenciadorEntidades.close();

		// retorna fornecedor
		return fornecedor;

	}// fim loadFornecedor()

	// obj: excluir fornecedor
	@Override
	public void deletar(Integer id) {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		try {

			// inicia a transa��o
			gerenciadorEntidades.getTransaction().begin();

			// carrega objeto do tipo Fornecedor
			Fornecedor fornecedor = gerenciadorEntidades.find(Fornecedor.class,
					id);

			// excluir fornecedor
			gerenciadorEntidades.remove(fornecedor);

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

	// obj: atuliza fornecedor
	@Override
	public void atualizar(Fornecedor fornecedor) {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		try {

			// inicia a transa��o
			gerenciadorEntidades.getTransaction().begin();

			// atuliza fornecedor
			gerenciadorEntidades.merge(fornecedor);

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

	}// fim update()

	// obj: retorna uma lista de fornecedores
	@Override
	public List<Fornecedor> listarTodos() {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		TypedQuery<Fornecedor> query = FabricaDeConexao
				.getInstanciaConexaoMysql().createNamedQuery(
						"Fornecedor.findAllOrderByForIdDesc", Fornecedor.class);

		// carrega lista de fornecedores
		List<Fornecedor> listaFornecedor = query.getResultList();

		// fecha conex�o
		gerenciadorEntidades.close();

		// retorna uma lista de objetos do tipo Fornecedor
		return listaFornecedor;

	}// fim listarTodos()

	// obj: retornar uma lista de fornecedores de acordo com consulta
	public List<Fornecedor> buscarFornecedores(String consulta) {

		// define lista de fornecedores achados
		List<Fornecedor> fornecedoresAchados = new ArrayList<>();

		// define lista de todos os fornecedores do banco
		List<Fornecedor> fornecedores = listarTodos();

		// converte consulta para min�scula
		consulta = consulta.toLowerCase();

		// loop condi��o
		for (int i = 0; i < fornecedores.size(); i++) {

			// se for
			if (fornecedores.get(i).getForNome().toLowerCase()
					.startsWith(consulta)) {

				// adiciona fornecedor a lista de fornecedores
				fornecedoresAchados.add(fornecedores.get(i));
			}// fim if

		}// fim for

		// retorna lista de fornecedores
		return fornecedoresAchados;

	}// fim buscarFornecedores()

	// obj: retorna uma lista de fornecedores de acordo com os par�metros
	@SuppressWarnings("unchecked")
	public List<Fornecedor> listarFornecedores(Integer porPagina,
			Integer pagAtual, String nomePaginacao, String nomeBusca) {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// define offset
		Integer offset = PaginacaoAuxiliarDao.getOffset(porPagina, pagAtual);

		// inicializa
		String sqlNomeClausula = "";

		// verifica��o se a op��o � busca
		if (nomePaginacao.equalsIgnoreCase("BUSCA")) {

			sqlNomeClausula = "WHERE for_nome like '%" + nomeBusca + "%'";
		}// fim if

		// verifica��o se a op��o � normal
		if (nomePaginacao.equalsIgnoreCase("NORMAL")) {
			sqlNomeClausula = "";
		}// fim if

		// define estrutura jpql
		String jpql = "SELECT * FROM fornecedor " + sqlNomeClausula + " LIMIT "
				+ porPagina + " OFFSET " + offset;

		// consulta
		Query query = gerenciadorEntidades.createNativeQuery(jpql,
				Fornecedor.class);

		// define lista de Fornecedores
		List<Fornecedor> listaFornecedor = query.getResultList();

		// retorna uma lista de objetos do tipo Fornecedor
		return listaFornecedor;

	}// fim listarFornecedores()

	// obj: retorna o n�mero de pagina��es
	public Integer numeroDePaginacao(int porPagina, String nomePaginacao,
			String nomeBusca) {

		// inicializa
		String sqlNomeClausula = "";

		// verifica��o se a op��o � busca
		if (nomePaginacao.equalsIgnoreCase("BUSCA")) {
			sqlNomeClausula = "WHERE for_nome like '%" + nomeBusca + "%'";
		}// fim if

		// verifica��o se a op��o � normal
		if (nomePaginacao.equalsIgnoreCase("NORMAL")) {
			sqlNomeClausula = "";
		}// fim if

		// retorna o n�mero de pagina��es
		return PaginacaoAuxiliarDao.numeroDePaginacao("fornecedor", porPagina,
				sqlNomeClausula);

	}// fim numeroDePaginacao()

	// obj: retorna o n�mero de registros
	public String numeroDeRegistrosBusca(String nomeBusca) {
		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// define estrutura jpql
		String jpql = "SELECT COUNT(f.for_nome) FROM fornecedor f WHERE f.for_nome like '%" + nomeBusca + "%'";
		
		Query consulta = gerenciadorEntidades.createNativeQuery(jpql);
		
		String numeroTotalRegBusca = consulta.getResultList().get(0).toString();

		// retorna o n�mero de pagina��es
		return numeroTotalRegBusca;

	}// fim numeroDeRegistrosBusca()

	// obj: retorna o total de fornecedores do sistema
	public String numeroFornecedores() {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		Query query = gerenciadorEntidades
				.createNamedQuery("Fornecedor.numeroFornecedores");

		// retorna n�mero de fornecedores
		return query.getResultList().get(0).toString();

	}// fim numeroFornecedores()

}// fim class FornecedorDao
