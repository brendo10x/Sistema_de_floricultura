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
 framework se encarregará de gerenciar suas instâncias,isto é, ele criará uma instância
 e utilizará como argumento para o construtor controller.
 */

@Component
public class VendedorDao extends Dao<Vendedor> {

	// variável gerenciador de entidades
	private EntityManager gerenciadorEntidades;

	public VendedorDao() {

	}// contrutor padrão ClienteDao

	// obj: adiciona vendedor
	@Override
	public void adicionar(Vendedor vendedor) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		try {

			// inicia a transação
			gerenciadorEntidades.getTransaction().begin();

			// adiciona proprietário
			gerenciadorEntidades.persist(vendedor);

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

	// obj: verificar se existe rg do vendedor
	// Tem diferenciação de (A) para (a) ao validar
	// validação de rg, se rg do Vendedor já existe ou não
	public boolean validaRgAdicionar(Vendedor vendedor) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		Query query = gerenciadorEntidades
				.createNamedQuery("Vendedor.findAllVenRg");

		// define parâmetro
		query.setParameter("venRg", vendedor.getVenRg());

		// verifica
		if (query.getResultList().size() != 0) {
			return true;// rg já registrado
		}

		// fecha
		gerenciadorEntidades.close();

		return false;// rg não registrado

	}// fim validaRgAdicionar()

	// obj: verificar se existe rg do vendedor
	// Tem diferenciação de (A) para (a) ao validar
	// validação de rg, se rg do vendedor já existe ou não
	@SuppressWarnings("unchecked")
	public boolean validaRgEditar(Vendedor vendedor) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		TypedQuery<Vendedor> query = gerenciadorEntidades.createNamedQuery(
				"Vendedor.findAllNotInVenId", Vendedor.class);

		// define parâmetro
		query.setParameter("venId", vendedor.getVenId());

		// define lista de vendedores
		List<Vendedor> listaVendedores = query.getResultList();

		// verifica
		boolean existeRg = false;// Rg não registrado
		for (Vendedor vendedorLista : listaVendedores) {

			if (vendedorLista.getVenRg().equals(vendedor.getVenRg())) {

				existeRg = true;// Rg já registrado
				break;// encerra loop

			}// fim if
		}// fim for

		// fecha conexão
		gerenciadorEntidades.close();

		// retorna um boolean
		return existeRg;

	}// fim validaRgEditar()

	// obj: verifica se existe cpf do vendedor
	// validação de CPF, se CPF do cliente já existe ou não
	// Tem diferenciação de (A) para (a) ao validar
	public boolean validaCpfAdicionar(Vendedor vendedor) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		Query query = gerenciadorEntidades
				.createNamedQuery("Vendedor.findAllVenCpf");

		// define parâmetro
		query.setParameter("venCpf", vendedor.getVenCpf());

		// verifica
		if (query.getResultList().size() != 0) {
			return true;// CPF já registrado
		}// fim if

		// fecha conexão
		gerenciadorEntidades.close();

		return false;// CPF não registrado

	}// fim validaCpfAdicionar()

	// obj: verifica se existe cpf do vendedor
	// Tem diferenciação de (A) para (a) ao validar
	// validação de Cpf, se Cpf do vendedor já existe ou não
	public boolean validaCpfEditar(Vendedor vendedor) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		TypedQuery<Vendedor> query = gerenciadorEntidades.createNamedQuery(
				"Vendedor.findAllNotInVenId", Vendedor.class);

		// define parâmetro
		query.setParameter("venId", vendedor.getVenId());

		// define lista de vendedores
		List<Vendedor> listaVendedores = query.getResultList();

		// verifica
		boolean existeCpf = false;// Cpf não registrado
		for (Vendedor vendedorLista : listaVendedores) {

			if (vendedorLista.getVenCpf().equals(vendedor.getVenCpf())) {
				existeCpf = true;// Cpf já registrado

			}// fim if
		}// fim for

		// fecha conexão
		gerenciadorEntidades.close();

		// retorna boolean
		return existeCpf;

	}// fim validaEmailEditar()

	// obj: retorna uma lista de vendedores
	@Override
	public List<Vendedor> listarTodos() {

		// entrega instância do tipo EntityManager com conexão mysql
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

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// retorna um objeto do tipo Vendedor
		Vendedor vendedor = gerenciadorEntidades.find(Vendedor.class, id);

		// fecha conexão
		gerenciadorEntidades.close();

		// retorna um vendedor
		return vendedor;

	}// fim carregar()

	// obj: atuliza cliente
	@Override
	public void atualizar(Vendedor vendedor) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		try {

			// inicia a transação
			gerenciadorEntidades.getTransaction().begin();

			// atuliza fornecedor
			gerenciadorEntidades.merge(vendedor);

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

	// excluir vendedor
	@Override
	public void deletar(Integer id) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		try {

			// inicia a transação
			gerenciadorEntidades.getTransaction().begin();

			// carrega objeto do tipo Vendedor
			Vendedor vendedor = gerenciadorEntidades.find(Vendedor.class, id);

			// excluir vendedor
			gerenciadorEntidades.remove(vendedor);

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

	// obj: retorna uma lista de vendedores de acordo com a consulta
	public List<Vendedor> buscarVendedores(String consulta) {

		// define lista de vendedores achados
		List<Vendedor> vendedoresAchados = new ArrayList<>();

		// define lista de todos os vendedores do banco
		List<Vendedor> vendedores = listarTodos();

		// transforma todas as letras para minúscula
		consulta = consulta.toLowerCase();

		// loop verificando se a consulta é igual a do banco
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

	// obj: Lista todos os vendedores de acordo com os parâmetros
	@SuppressWarnings("unchecked")
	public List<Vendedor> listarVendedores(Integer porPagina, Integer pagAtual,
			String nomePaginacao, String nomeBusca) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// define offset
		Integer offset = PaginacaoAuxiliarDao.getOffset(porPagina, pagAtual);

		// inicializa
		String sqlNomeClausula = "";

		// verificação se a opção é busca
		if (nomePaginacao.equalsIgnoreCase("BUSCA")) {
			sqlNomeClausula = "WHERE ven_nome like '%" + nomeBusca + "%'";
		}// fim if

		// verificação se a opção é normal
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

	// obj: retorna o número de paginações
	public Integer numeroDePaginacao(int porPagina, String nomePaginacao,
			String nomeBusca) {

		// inicializa
		String sqlNomeClausula = "";

		// verificação se a opção é busca
		if (nomePaginacao.equalsIgnoreCase("BUSCA")) {
			sqlNomeClausula = "WHERE ven_nome like '%" + nomeBusca + "%'";
		}// fim if

		// verificação se a opção é normal
		if (nomePaginacao.equalsIgnoreCase("NORMAL")) {
			sqlNomeClausula = "";
		}// fim if

		// retorna o número de paginações
		return PaginacaoAuxiliarDao.numeroDePaginacao("vendedor", porPagina,
				sqlNomeClausula);

	} // fim numeroDePaginacao()

	// obj: retorna o número total de vendedores
	public String numeroVendedores() {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		Query query = gerenciadorEntidades
				.createNamedQuery("Vendedor.numeroVendedores");

		// retorna o número total
		return query.getResultList().get(0).toString();

	}// fim numeroVendedores()

	// obj: carregar vendedor com id usuário, para recuperar o nome
	public String obterNomeVendedorUsuarioId(Integer idUsuario) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		TypedQuery<Vendedor> query = gerenciadorEntidades.createNamedQuery(
				"Vendedor.usuarioUsuId", Vendedor.class);

		// define parâmetro
		query.setParameter("IdUsuario", idUsuario);

		// carrega vendedor
		Vendedor vendedor = query.getResultList().get(0);

		// define nome vendedor
		String nomeVendedor = vendedor.getVenNome();

		// fecha conexão
		gerenciadorEntidades.close();

		// retorna o nome do vendedor
		return nomeVendedor;

	}// fim obterNomeVendedorUsuarioId()

	// obj: retorna o número de registros
	public String numeroDeRegistrosBusca(String nomeBusca) {
		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// define estrutura jpql
		String jpql = "SELECT COUNT(v.ven_nome) FROM vendedor v WHERE v.ven_nome like '%"
				+ nomeBusca + "%'";

		Query consulta = gerenciadorEntidades.createNativeQuery(jpql);

		String numeroTotalRegBusca = consulta.getResultList().get(0).toString();

		// retorna o número de paginações
		return numeroTotalRegBusca;

	}// fim numeroDeRegistrosBusca()

}// fim class ClienteDao
