//pacote
package br.com.sis.floricultura.dao;

//import's
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sis.floricultura.connection.FabricaDeConexao;
import br.com.sis.floricultura.model.Vendedor;

/*
 Esta classe (VendedorDao) anotada com @Component notifica para o Vraptor que o 
 framework se encarregar� de gerenciar suas inst�ncias,isto �, ele criar� uma inst�ncia
 e utilizar� como argumento para o construtor controller.
 */

@Component
public class VendedorDao extends Dao<Vendedor> {

	// vari�vel gerenciador de entidades
	private EntityManager gerenciadorEntidades;

	public VendedorDao() {

	}// contrutor padr�o ClienteDao

	// obj: adiciona vendedor
	@Override
	public void adicionar(Vendedor vendedor) {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		try {

			// inicia a transa��o
			gerenciadorEntidades.getTransaction().begin();

			// adiciona propriet�rio
			gerenciadorEntidades.persist(vendedor);

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

	// obj: verificar se existe rg do vendedor
	// Tem diferencia��o de (A) para (a) ao validar
	// valida��o de rg, se rg do Vendedor j� existe ou n�o
	public boolean validaRgAdicionar(Vendedor vendedor) {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		Query query = gerenciadorEntidades
				.createNamedQuery("Vendedor.findAllVenRg");

		// define par�metro
		query.setParameter("venRg", vendedor.getVenRg());

		// verifica
		if (query.getResultList().size() != 0) {
			return true;// rg j� registrado
		}

		// fecha
		gerenciadorEntidades.close();

		return false;// rg n�o registrado

	}// fim validaRgAdicionar()

	// obj: verificar se existe rg do vendedor
	// Tem diferencia��o de (A) para (a) ao validar
	// valida��o de rg, se rg do vendedor j� existe ou n�o
	@SuppressWarnings("unchecked")
	public boolean validaRgEditar(Vendedor vendedor) {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		TypedQuery<Vendedor> query = gerenciadorEntidades.createNamedQuery(
				"Vendedor.findAllNotInVenId", Vendedor.class);

		// define par�metro
		query.setParameter("venId", vendedor.getVenId());

		// define lista de vendedores
		List<Vendedor> listaVendedores = query.getResultList();

		// verifica
		boolean existeRg = false;// Rg n�o registrado
		for (Vendedor vendedorLista : listaVendedores) {

			if (vendedorLista.getVenRg().equals(vendedor.getVenRg())) {

				existeRg = true;// Rg j� registrado
				break;// encerra loop

			}// fim if
		}// fim for

		// fecha conex�o
		gerenciadorEntidades.close();

		// retorna um boolean
		return existeRg;

	}// fim validaRgEditar()

	// obj: verifica se existe cpf do vendedor
	// valida��o de CPF, se CPF do cliente j� existe ou n�o
	// Tem diferencia��o de (A) para (a) ao validar
	public boolean validaCpfAdicionar(Vendedor vendedor) {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		Query query = gerenciadorEntidades
				.createNamedQuery("Vendedor.findAllVenCpf");

		// define par�metro
		query.setParameter("venCpf", vendedor.getVenCpf());

		// verifica
		if (query.getResultList().size() != 0) {
			return true;// CPF j� registrado
		}// fim if

		// fecha conex�o
		gerenciadorEntidades.close();

		return false;// CPF n�o registrado

	}// fim validaCpfAdicionar()

	// obj: verifica se existe cpf do vendedor
	// Tem diferencia��o de (A) para (a) ao validar
	// valida��o de Cpf, se Cpf do vendedor j� existe ou n�o
	public boolean validaCpfEditar(Vendedor vendedor) {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		TypedQuery<Vendedor> query = gerenciadorEntidades.createNamedQuery(
				"Vendedor.findAllNotInVenId", Vendedor.class);

		// define par�metro
		query.setParameter("venId", vendedor.getVenId());

		// define lista de vendedores
		List<Vendedor> listaVendedores = query.getResultList();

		// verifica
		boolean existeCpf = false;// Cpf n�o registrado
		for (Vendedor vendedorLista : listaVendedores) {

			if (vendedorLista.getVenCpf().equals(vendedor.getVenCpf())) {
				existeCpf = true;// Cpf j� registrado

			}// fim if
		}// fim for

		// fecha conex�o
		gerenciadorEntidades.close();

		// retorna boolean
		return existeCpf;

	}// fim validaEmailEditar()

	// obj: retorna uma lista de vendedores
	@Override
	public List<Vendedor> listarTodos() {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		TypedQuery<Vendedor> query = gerenciadorEntidades.createNamedQuery(
				"Vendedor.findAllOrderByVenIdDesc", Vendedor.class);

		// carrega lista de vendedores
		List<Vendedor> listaVendedor = query.getResultList();

		// retorna uma lista de vendedores
		return listaVendedor;

	}// fim listarTodos()

	// obj: carrega um objeto do tipo Vendedor
	public Vendedor carregar(Integer id) {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// retorna um objeto do tipo Vendedor
		Vendedor vendedor = gerenciadorEntidades.find(Vendedor.class, id);

		// fecha conex�o
		gerenciadorEntidades.close();

		// retorna um vendedor
		return vendedor;

	}// fim carregar()

	// obj: atuliza cliente
	@Override
	public void atualizar(Vendedor vendedor) {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		try {

			// inicia a transa��o
			gerenciadorEntidades.getTransaction().begin();

			// atuliza fornecedor
			gerenciadorEntidades.merge(vendedor);

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

	// excluir vendedor
	@Override
	public void deletar(Integer id) {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		try {

			// inicia a transa��o
			gerenciadorEntidades.getTransaction().begin();

			// carrega objeto do tipo Vendedor
			Vendedor vendedor = gerenciadorEntidades.find(Vendedor.class, id);

			// excluir vendedor
			gerenciadorEntidades.remove(vendedor);

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

	// obj: retorna uma lista de vendedores de acordo com a consulta
	public List<Vendedor> buscarVendedores(String consulta) {

		// define lista de vendedores achados
		List<Vendedor> vendedoresAchados = new ArrayList<>();

		// define lista de todos os vendedores do banco
		List<Vendedor> vendedores = listarTodos();

		// transforma todas as letras para min�scula
		consulta = consulta.toLowerCase();

		// loop verificando se a consulta � igual a do banco
		for (int i = 0; i < vendedores.size(); i++) {

			// se for
			if (vendedores.get(i).getVenNome().toLowerCase()
					.startsWith(consulta)) {

				// adiciona vendedor a lista de vendedores
				vendedoresAchados.add(vendedores.get(i));

			}// fim if

		}// fim for

		// retorna lista de vendedores achados
		return vendedoresAchados;

	}// fim buscarVendedores()

	// obj: Lista todos os vendedores de acordo com os par�metros
	@SuppressWarnings("unchecked")
	public List<Vendedor> listarVendedores(Integer porPagina, Integer pagAtual,
			String nomePaginacao, String nomeBusca) {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// define offset
		Integer offset = PaginacaoAuxiliarDao.getOffset(porPagina, pagAtual);

		// inicializa
		String sqlNomeClausula = "";

		// verifica��o se a op��o � busca
		if (nomePaginacao.equalsIgnoreCase("BUSCA")) {
			sqlNomeClausula = "WHERE ven_nome like '%" + nomeBusca + "%'";
		}// fim if

		// verifica��o se a op��o � normal
		if (nomePaginacao.equalsIgnoreCase("NORMAL")) {
			sqlNomeClausula = "";
		}// fim if

		// define estrutura jpql
		String jpql = "SELECT * FROM vendedor " + sqlNomeClausula + " LIMIT "
				+ porPagina + " OFFSET " + offset;

		// consulta
		Query query = gerenciadorEntidades.createNativeQuery(jpql,
				Vendedor.class);

		// define lista de vendedores
		List<Vendedor> listaVendedor = query.getResultList();

		// retorna uma lista de fornecedores
		return listaVendedor;

	}// fim listarVendedores()

	// obj: retorna o n�mero de pagina��es
	public Integer numeroDePaginacao(int porPagina, String nomePaginacao,
			String nomeBusca) {

		// inicializa
		String sqlNomeClausula = "";

		// verifica��o se a op��o � busca
		if (nomePaginacao.equalsIgnoreCase("BUSCA")) {
			sqlNomeClausula = "WHERE ven_nome like '%" + nomeBusca + "%'";
		}// fim if

		// verifica��o se a op��o � normal
		if (nomePaginacao.equalsIgnoreCase("NORMAL")) {
			sqlNomeClausula = "";
		}// fim if

		// retorna o n�mero de pagina��es
		return PaginacaoAuxiliarDao.numeroDePaginacao("vendedor", porPagina,
				sqlNomeClausula);

	} // fim numeroDePaginacao()

	// obj: retorna o n�mero total de vendedores
	public String numeroVendedores() {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		Query query = gerenciadorEntidades
				.createNamedQuery("Vendedor.numeroVendedores");

		// retorna o n�mero total
		return query.getResultList().get(0).toString();

	}// fim numeroVendedores()

	// obj: carregar vendedor com id usu�rio, para recuperar o nome
	public String obterNomeVendedorUsuarioId(Integer idUsuario) {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		TypedQuery<Vendedor> query = gerenciadorEntidades.createNamedQuery(
				"Vendedor.usuarioUsuId", Vendedor.class);

		// define par�metro
		query.setParameter("IdUsuario", idUsuario);

		// carrega vendedor
		Vendedor vendedor = query.getResultList().get(0);

		// define nome vendedor
		String nomeVendedor = vendedor.getVenNome();

		// fecha conex�o
		gerenciadorEntidades.close();

		// retorna o nome do vendedor
		return nomeVendedor;

	}// fim obterNomeVendedorUsuarioId()

	// obj: retorna o n�mero de registros
	public String numeroDeRegistrosBusca(String nomeBusca) {
		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// define estrutura jpql
		String jpql = "SELECT COUNT(v.ven_nome) FROM vendedor v WHERE v.ven_nome like '%"
				+ nomeBusca + "%'";

		Query consulta = gerenciadorEntidades.createNativeQuery(jpql);

		String numeroTotalRegBusca = consulta.getResultList().get(0).toString();

		// retorna o n�mero de pagina��es
		return numeroTotalRegBusca;

	}// fim numeroDeRegistrosBusca()

}// fim class ClienteDao
