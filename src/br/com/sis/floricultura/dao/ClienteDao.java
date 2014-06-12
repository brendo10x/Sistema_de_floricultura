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
  framework se encarregará de gerenciar suas instâncias,isto é, ele criará uma instância
  e utilizará como argumento para o construtor controller.
 */

@Component
public class ClienteDao extends Dao<Cliente> {

	// variável gerenciador de entidades
	private EntityManager gerenciadorEntidades;

	public ClienteDao() {

	}// contrutor padrão ClienteDao

	// obj: adiciona cliente
	@Override
	public void adicionar(Cliente cliente) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();
		
		try {

			// inicia a transação
			gerenciadorEntidades.getTransaction().begin();

			// adiciona proprietário
			gerenciadorEntidades.persist(cliente);

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

	// Tem diferenciação de (A) para (a) ao validar
	// validação de rg, se rg do cliente se existe ou não
	public boolean validaRgAdicionar(Cliente cliente) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		Query query = gerenciadorEntidades
				.createNamedQuery("Cliente.findAllCliRg");

		// define parâmetro
		query.setParameter("cliRg", cliente.getCliRg());

		// verifica
		if (query.getResultList().size() != 0) {
			return true;// rg já registrado
		}

		// fecha conexão
		gerenciadorEntidades.close();

		return false;// rg não registrado

	}// fim validaRgAdicionar()

	// Tem diferenciação de (A) para (a) ao validar
	// validação de rg, se rg do cliente se existe ou não
	public boolean validaRgEditar(Cliente cliente) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		TypedQuery<Cliente> query = gerenciadorEntidades.createNamedQuery(
				"Cliente.findAllNotInCliId", Cliente.class);

		// define parâmetro
		query.setParameter("cliId", cliente.getCliId());

		// define lista de clientes
		List<Cliente> listaClientes = query.getResultList();

		// verifica
		boolean existeRg = false;// Rg não registrado
		for (Cliente clienteLista : listaClientes) {
			if (clienteLista.getCliRg().equals(cliente.getCliRg())) {

				existeRg = true;// Rg já registrado

			}// fim if

		}// fim for

		// fecha conexão
		gerenciadorEntidades.close();

		// retorna boolean
		return existeRg;

	}// fim validaUsuario()

	// validação de email, se emial do cliente já existe ou não
	// Tem diferenciação de (A) para (a) ao validar
	// validação de Email, se Email do cliente já existe ou não
	public boolean validaEmailAdicionar(Cliente cliente) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		Query query = gerenciadorEntidades
				.createNamedQuery("Cliente.findAllCliEmail");

		// define parâmetro
		query.setParameter("cliEmail", cliente.getCliEmail());

		// verifica
		if (query.getResultList().size() != 0) {
			return true;// email já registrado
		}

		// fecha
		gerenciadorEntidades.close();

		return false;// email não registrado

	}// fim validaEmailAdicionar()

	// Tem diferenciação de (A) para (a) ao validar
	// validação de Email, se Email do cliente já existe ou não

	public boolean validaEmailEditar(Cliente cliente) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		TypedQuery<Cliente> query = gerenciadorEntidades.createNamedQuery(
				"Cliente.findAllNotInCliId", Cliente.class);

		// define parâmetro
		query.setParameter("cliId", cliente.getCliId());

		// define lista de clientes
		List<Cliente> listaClientes = query.getResultList();

		// verifica
		boolean existeEmail = false;// Email não registrado
		for (Cliente clienteLista : listaClientes) {
			if (clienteLista.getCliEmail().equals(cliente.getCliEmail())) {
				existeEmail = true;// Email já registrado
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

		// entrega instância do tipo EntityManager com conexão mysql
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

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// carrega objeto do tipo Cliente
		Cliente cliente = gerenciadorEntidades.find(Cliente.class, id);

		// fecha conexão
		gerenciadorEntidades.close();

		// retorna cliente
		return cliente;

	}// fim loadCliente()

	// obj: atuliza cliente
	@Override
	public void atualizar(Cliente cliente) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		try {

			// inicia a transação
			gerenciadorEntidades.getTransaction().begin();

			// atuliza fornecedor
			gerenciadorEntidades.merge(cliente);

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

	// obj: buscar lista de clientes de acordo com consulta
	public List<Cliente> buscarClientes(String consulta) {

		// define lista de clientes achados
		List<Cliente> clientesAchados = new ArrayList<>();

		// define lista de todos os clientes do banco
		List<Cliente> clientes = listarTodos();

		// transforma todas as letras para minúscula
		consulta = consulta.toLowerCase();

		// loop verificando se a consulta é igual a do banco
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

	// obj: relatório: Listar todos os clientes de uma determinada cidade
	public List<Cliente> clientesPorCidade(Cidade cidade, Estado estado) {

		// define lista de todos os clientes do banco
		List<Cliente> listaClientesAchados = new ArrayList<>();

		// define lista de clientes achados
		List<Cliente> listaClientes = listarTodos();

		// compara se cidade id e estado id são iguais a do banco
		for (Cliente cliente : listaClientes) {

			// carrega id
			int cidadeCliente = cliente.getEnderecoEndId().getCidadeCidId()
					.getCidId();

			// carrega id
			int cidadeEscolhida = cidade.getCidId();

			// verifica se são iguais
			if (cidadeCliente == cidadeEscolhida) {

				// se for
				// adiciona cliente a lista de clientes achados
				listaClientesAchados.add(cliente);

			}
		}

		// retorna lista de clientes achados
		return listaClientesAchados;

	}// fim clientesPorCidade()

	// obj: relatório: Listar todos os clientes de um determinado estado
	public List<Cliente> clientesPorEstado(Estado estado) {

		// define lista de clientes achados
		List<Cliente> listaClientesAchados = new ArrayList<>();

		// define lista de todos os clientes do banco
		List<Cliente> listaClientes = listarTodos();

		// compara se cidade id e estado id são iguais a do banco
		for (Cliente cliente : listaClientes) {

			// carrega id
			int estadoCliente = cliente.getEnderecoEndId().getCidadeCidId()
					.getEstadoEstId().getEstId();

			// carrega id
			int estadoEscolhido = estado.getEstId();

			// verifica se são iguais
			if (estadoCliente == estadoEscolhido) {

				// se for
				// adiciona cliente a lista de clientes achados
				listaClientesAchados.add(cliente);

			}
		}

		// retorna lista de clientes achados
		return listaClientesAchados;

	}// fim clientesPorEstado()

	// obj: Lista todos os clientes de acordo com os parâmetros
	@SuppressWarnings("unchecked")
	public List<Cliente> listarClientes(Integer porPagina, Integer pagAtual,
			String nomePaginacao, String nomeBusca) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// define offset
		Integer offset = PaginacaoAuxiliarDao.getOffset(porPagina, pagAtual);

		// inicializa
		String sqlNomeClausula = "";

		// verificação se a opção é busca
		if (nomePaginacao.equalsIgnoreCase("BUSCA")) {
			sqlNomeClausula = "WHERE cli_nome like '%" + nomeBusca + "%'";
		}// fim if

		// verificação se a opção é normal
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

		// fecha conexão
		gerenciadorEntidades.close();

		// retorna uma lista de clientes
		return listaCliente;

	}// fim listaClientes()

	// obj: retorna o número de paginações
	public Integer numeroDePaginacao(int porPagina, String nomePaginacao,
			String nomeBusca) {

		// inicializa
		String sqlNomeClausula = "";

		// verificação se a opção é busca
		if (nomePaginacao.equalsIgnoreCase("BUSCA")) {
			sqlNomeClausula = "WHERE cli_nome like '%" + nomeBusca + "%'";
		}// fim if

		// verificação se a opção é normal
		if (nomePaginacao.equalsIgnoreCase("NORMAL")) {
			sqlNomeClausula = "";
		}// fim if

		// retorna o número de paginações
		return PaginacaoAuxiliarDao.numeroDePaginacao("Cliente", porPagina,
				sqlNomeClausula);

	}// fim numeroDePaginacao()

	// obj: deletar cliente
	public void deletar(Integer id) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		try {

			// inicia transação
			gerenciadorEntidades.getTransaction().begin();

			// carrega objeto do tipo Cliente
			Cliente cliente = gerenciadorEntidades.find(Cliente.class, id);

			// exclui cliente
			gerenciadorEntidades.remove(cliente);

			// transação com sucesso
			gerenciadorEntidades.getTransaction().commit();

		} catch (Exception e) {

			// transação ocorreu erro
			gerenciadorEntidades.getTransaction().rollback();

		 

			// mostra erro
			e.getCause();

		}// fim catch e try

		// fecha conexão
		gerenciadorEntidades.close();

	}// fim deletar()

	// obj: obter o número total de clientes no sistema
	public String numeroClientes() {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		Query query = gerenciadorEntidades
				.createNamedQuery("Cliente.numeroClientes");

		// retorna número total de clientes
		return query.getResultList().get(0).toString();

	}// fim numeroClientes()

	// obj: retorna o número de registros
	public String numeroDeRegistrosBusca(String nomeBusca) {
		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// define estrutura jpql
		String jpql = "SELECT COUNT(c.cli_nome) FROM cliente c WHERE c.cli_nome like '%"
				+ nomeBusca + "%'";

		Query consulta = gerenciadorEntidades.createNativeQuery(jpql);

		String numeroTotalRegBusca = consulta.getResultList().get(0).toString();

		// retorna o número de paginações
		return numeroTotalRegBusca;

	}// fim numeroDeRegistrosBusca()

}// fim class ClienteDao
