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
import br.com.sis.floricultura.model.Cidade;
import br.com.sis.floricultura.model.Cliente;
import br.com.sis.floricultura.model.Estado;

/*
  Esta classe (ClienteDao) anotada com @Component notifica para o Vraptor que o 
  framework se encarregar� de gerenciar suas inst�ncias,isto �, ele criar� uma inst�ncia
  e utilizar� como argumento para o construtor controller.
 */

@Component
public class ClienteDao extends Dao<Cliente> {

	// vari�vel gerenciador de entidades
	private EntityManager gerenciadorEntidades;

	public ClienteDao() {

	}// contrutor padr�o ClienteDao

	// obj: adiciona cliente
	@Override
	public void adicionar(Cliente cliente) {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();
		
		try {

			// inicia a transa��o
			gerenciadorEntidades.getTransaction().begin();

			// adiciona propriet�rio
			gerenciadorEntidades.persist(cliente);

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

	// Tem diferencia��o de (A) para (a) ao validar
	// valida��o de rg, se rg do cliente se existe ou n�o
	public boolean validaRgAdicionar(Cliente cliente) {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		Query query = gerenciadorEntidades
				.createNamedQuery("Cliente.findAllCliRg");

		// define par�metro
		query.setParameter("cliRg", cliente.getCliRg());

		// verifica
		if (query.getResultList().size() != 0) {
			return true;// rg j� registrado
		}

		// fecha conex�o
		gerenciadorEntidades.close();

		return false;// rg n�o registrado

	}// fim validaRgAdicionar()

	// Tem diferencia��o de (A) para (a) ao validar
	// valida��o de rg, se rg do cliente se existe ou n�o
	public boolean validaRgEditar(Cliente cliente) {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		TypedQuery<Cliente> query = gerenciadorEntidades.createNamedQuery(
				"Cliente.findAllNotInCliId", Cliente.class);

		// define par�metro
		query.setParameter("cliId", cliente.getCliId());

		// define lista de clientes
		List<Cliente> listaClientes = query.getResultList();

		// verifica
		boolean existeRg = false;// Rg n�o registrado
		for (Cliente clienteLista : listaClientes) {
			if (clienteLista.getCliRg().equals(cliente.getCliRg())) {

				existeRg = true;// Rg j� registrado

			}// fim if

		}// fim for

		// fecha conex�o
		gerenciadorEntidades.close();

		// retorna boolean
		return existeRg;

	}// fim validaUsuario()

	// valida��o de email, se emial do cliente j� existe ou n�o
	// Tem diferencia��o de (A) para (a) ao validar
	// valida��o de Email, se Email do cliente j� existe ou n�o
	public boolean validaEmailAdicionar(Cliente cliente) {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		Query query = gerenciadorEntidades
				.createNamedQuery("Cliente.findAllCliEmail");

		// define par�metro
		query.setParameter("cliEmail", cliente.getCliEmail());

		// verifica
		if (query.getResultList().size() != 0) {
			return true;// email j� registrado
		}

		// fecha
		gerenciadorEntidades.close();

		return false;// email n�o registrado

	}// fim validaEmailAdicionar()

	// Tem diferencia��o de (A) para (a) ao validar
	// valida��o de Email, se Email do cliente j� existe ou n�o

	public boolean validaEmailEditar(Cliente cliente) {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		TypedQuery<Cliente> query = gerenciadorEntidades.createNamedQuery(
				"Cliente.findAllNotInCliId", Cliente.class);

		// define par�metro
		query.setParameter("cliId", cliente.getCliId());

		// define lista de clientes
		List<Cliente> listaClientes = query.getResultList();

		// verifica
		boolean existeEmail = false;// Email n�o registrado
		for (Cliente clienteLista : listaClientes) {
			if (clienteLista.getCliEmail().equals(cliente.getCliEmail())) {
				existeEmail = true;// Email j� registrado
			}
		}

		// fecha
		gerenciadorEntidades.close();

		// retorna boolean
		return existeEmail;

	}// fim validaEmailEditar()

	// obj: Lista dos os clientes
	@Override
	public List<Cliente> listarTodos() {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		TypedQuery<Cliente> query = gerenciadorEntidades.createNamedQuery(
				"Cliente.findAllOrderByCliIdDesc", Cliente.class);

		// define lista de clientes
		List<Cliente> listaCliente = query.getResultList();

		// retorna uma lista de clientes
		return listaCliente;

	}// fim listAll()

	// obj: Carrega objeto do tipo Cliente
	@Override
	public Cliente carregar(Integer id) {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// carrega objeto do tipo Cliente
		Cliente cliente = gerenciadorEntidades.find(Cliente.class, id);

		// fecha conex�o
		gerenciadorEntidades.close();

		// retorna cliente
		return cliente;

	}// fim loadCliente()

	// obj: atuliza cliente
	@Override
	public void atualizar(Cliente cliente) {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		try {

			// inicia a transa��o
			gerenciadorEntidades.getTransaction().begin();

			// atuliza fornecedor
			gerenciadorEntidades.merge(cliente);

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

	// obj: buscar lista de clientes de acordo com consulta
	public List<Cliente> buscarClientes(String consulta) {

		// define lista de clientes achados
		List<Cliente> clientesAchados = new ArrayList<>();

		// define lista de todos os clientes do banco
		List<Cliente> clientes = listarTodos();

		// transforma todas as letras para min�scula
		consulta = consulta.toLowerCase();

		// loop verificando se a consulta � igual a do banco
		for (int i = 0; i < clientes.size(); i++) {

			// se for
			if (clientes.get(i).getCliNome().toLowerCase().startsWith(consulta)) {

				// adiciona cliente a lista de clientes
				clientesAchados.add(clientes.get(i));

			}// fim if

		}// fim for

		// retorna lista de clientes achados
		return clientesAchados;

	}// fim buscarClientes()

	// obj: relat�rio: Listar todos os clientes de uma determinada cidade
	public List<Cliente> clientesPorCidade(Cidade cidade, Estado estado) {

		// define lista de todos os clientes do banco
		List<Cliente> listaClientesAchados = new ArrayList<>();

		// define lista de clientes achados
		List<Cliente> listaClientes = listarTodos();

		// compara se cidade id e estado id s�o iguais a do banco
		for (Cliente cliente : listaClientes) {

			// carrega id
			int cidadeCliente = cliente.getEnderecoEndId().getCidadeCidId()
					.getCidId();

			// carrega id
			int cidadeEscolhida = cidade.getCidId();

			// verifica se s�o iguais
			if (cidadeCliente == cidadeEscolhida) {

				// se for
				// adiciona cliente a lista de clientes achados
				listaClientesAchados.add(cliente);

			}
		}

		// retorna lista de clientes achados
		return listaClientesAchados;

	}// fim clientesPorCidade()

	// obj: relat�rio: Listar todos os clientes de um determinado estado
	public List<Cliente> clientesPorEstado(Estado estado) {

		// define lista de clientes achados
		List<Cliente> listaClientesAchados = new ArrayList<>();

		// define lista de todos os clientes do banco
		List<Cliente> listaClientes = listarTodos();

		// compara se cidade id e estado id s�o iguais a do banco
		for (Cliente cliente : listaClientes) {

			// carrega id
			int estadoCliente = cliente.getEnderecoEndId().getCidadeCidId()
					.getEstadoEstId().getEstId();

			// carrega id
			int estadoEscolhido = estado.getEstId();

			// verifica se s�o iguais
			if (estadoCliente == estadoEscolhido) {

				// se for
				// adiciona cliente a lista de clientes achados
				listaClientesAchados.add(cliente);

			}
		}

		// retorna lista de clientes achados
		return listaClientesAchados;

	}// fim clientesPorEstado()

	// obj: Lista todos os clientes de acordo com os par�metros
	@SuppressWarnings("unchecked")
	public List<Cliente> listarClientes(Integer porPagina, Integer pagAtual,
			String nomePaginacao, String nomeBusca) {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// define offset
		Integer offset = PaginacaoAuxiliarDao.getOffset(porPagina, pagAtual);

		// inicializa
		String sqlNomeClausula = "";

		// verifica��o se a op��o � busca
		if (nomePaginacao.equalsIgnoreCase("BUSCA")) {
			sqlNomeClausula = "WHERE cli_nome like '%" + nomeBusca + "%'";
		}// fim if

		// verifica��o se a op��o � normal
		if (nomePaginacao.equalsIgnoreCase("NORMAL")) {
			sqlNomeClausula = "";
		}// fim if

		// define estrutura jpql
		String jpql = "SELECT * FROM cliente " + sqlNomeClausula + " LIMIT "
				+ porPagina + " OFFSET " + offset;

		// consulta
		Query query = gerenciadorEntidades.createNativeQuery(jpql,
				Cliente.class);

		// define lista de clientes
		List<Cliente> listaCliente = query.getResultList();

		// fecha conex�o
		gerenciadorEntidades.close();

		// retorna uma lista de clientes
		return listaCliente;

	}// fim listaClientes()

	// obj: retorna o n�mero de pagina��es
	public Integer numeroDePaginacao(int porPagina, String nomePaginacao,
			String nomeBusca) {

		// inicializa
		String sqlNomeClausula = "";

		// verifica��o se a op��o � busca
		if (nomePaginacao.equalsIgnoreCase("BUSCA")) {
			sqlNomeClausula = "WHERE cli_nome like '%" + nomeBusca + "%'";
		}// fim if

		// verifica��o se a op��o � normal
		if (nomePaginacao.equalsIgnoreCase("NORMAL")) {
			sqlNomeClausula = "";
		}// fim if

		// retorna o n�mero de pagina��es
		return PaginacaoAuxiliarDao.numeroDePaginacao("Cliente", porPagina,
				sqlNomeClausula);

	}// fim numeroDePaginacao()

	// obj: deletar cliente
	public void deletar(Integer id) {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		try {

			// inicia transa��o
			gerenciadorEntidades.getTransaction().begin();

			// carrega objeto do tipo Cliente
			Cliente cliente = gerenciadorEntidades.find(Cliente.class, id);

			// exclui cliente
			gerenciadorEntidades.remove(cliente);

			// transa��o com sucesso
			gerenciadorEntidades.getTransaction().commit();

		} catch (Exception e) {

			// transa��o ocorreu erro
			gerenciadorEntidades.getTransaction().rollback();

		 

			// mostra erro
			e.getCause();

		}// fim catch e try

		// fecha conex�o
		gerenciadorEntidades.close();

	}// fim deletar()

	// obj: obter o n�mero total de clientes no sistema
	public String numeroClientes() {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		Query query = gerenciadorEntidades
				.createNamedQuery("Cliente.numeroClientes");

		// retorna n�mero total de clientes
		return query.getResultList().get(0).toString();

	}// fim numeroClientes()

	// obj: retorna o n�mero de registros
	public String numeroDeRegistrosBusca(String nomeBusca) {
		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// define estrutura jpql
		String jpql = "SELECT COUNT(c.cli_nome) FROM cliente c WHERE c.cli_nome like '%"
				+ nomeBusca + "%'";

		Query consulta = gerenciadorEntidades.createNativeQuery(jpql);

		String numeroTotalRegBusca = consulta.getResultList().get(0).toString();

		// retorna o n�mero de pagina��es
		return numeroTotalRegBusca;

	}// fim numeroDeRegistrosBusca()

}// fim class ClienteDao
