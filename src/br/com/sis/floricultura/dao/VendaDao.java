//pacote
package br.com.sis.floricultura.dao;

//import's
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
 

import br.com.caelum.vraptor.ioc.Component;
import br.com.sis.floricultura.connection.FabricaDeConexao;
import br.com.sis.floricultura.model.Venda;
import br.com.sis.floricultura.model.Vendedor;

/*
 Esta classe (VendaDao) anotada com @Component notifica para o Vraptor que o 
 framework se encarregará de gerenciar suas instâncias,isto é, ele criará uma instância
 e utilizará como argumento para o construtor controller.
 */

@Component
public class VendaDao extends Dao<Venda> {

	// variável gerenciador de entidades
	private EntityManager gerenciadorEntidades;

	public VendaDao() {

	}// contrutor padrão VendaDao

	// obj: adiciona venda
	@Override
	public void adicionar(Venda venda) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		try {

			// inicia a transação
			gerenciadorEntidades.getTransaction().begin();

			// adiciona proprietário
			gerenciadorEntidades.persist(venda);

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

	// obj: excluir venda
	@Override
	public void deletar(Integer id) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		try {

			// inicia a transação
			gerenciadorEntidades.getTransaction().begin();

			// carrega objeto do tipo Venda
			Venda venda = gerenciadorEntidades.find(Venda.class, id);

			// exclui venda
			gerenciadorEntidades.remove(venda);

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

	}// fim delete()

	// obj: recuperar o id do última venda adicionada
	public Venda buscarUltimaVenda() {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		Query query = gerenciadorEntidades
				.createNamedQuery("Venda.findUltimoVenda");

		// define id venda
		Integer vendaId = (Integer) query.getResultList().get(0);

		// carrega objeto do tipo Venda
		Venda venda = gerenciadorEntidades.find(Venda.class, vendaId);

		// retorna id
		return venda;

	}// fim buscarUltimaVenda()

	// obj: listar todas as vendas
	@Override
	public List<Venda> listarTodos() {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		TypedQuery<Venda> query = gerenciadorEntidades.createNamedQuery(
				"Venda.findAllOrderByVenIdDesc", Venda.class);

		// define lista de vendas
		List<Venda> listaVendas = query.getResultList();

		// retorna uma lista de vendas
		return listaVendas;

	}// fim listAll()

	// obj: recupera venda
	public Venda carregar(Integer id) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// carrega objeto do tipo Venda
		Venda venda = gerenciadorEntidades.find(Venda.class, id);

		// fecha conexão
		gerenciadorEntidades.close();

		// retorna venda
		return venda;

	}// fim loadVenda()

	// relatórios
	// Obs: vendas por um dia
	public List<Venda> listarVendasPorDia(String dia) throws ParseException {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// atribuir e receber formato
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		// Date
		java.util.Date diaUtil = new Date();

		// e converte para Date
		diaUtil = df.parse(dia);

		// consulta
		TypedQuery<Venda> query = gerenciadorEntidades.createNamedQuery(
				"Venda.findByVenDataVenda", Venda.class);

		// define parâmetro
		query.setParameter("venDataVenda", diaUtil);

		// define lista de vendas
		List<Venda> listaVendas = query.getResultList();

		// retorna uma lista de vendas
		return listaVendas;

	}// fim listVendasPorDia()

	// Obj: vendas por um mês
	public List<Venda> listarVendasPorMes(String mes, String ano) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// recupero todas as vendas
		List<Venda> listaVendas = listarTodos();

		// retorna a lista se satisfazer a condição
		List<Venda> listaVendasEncontradas = new ArrayList<>();

		// obtem ano
		SimpleDateFormat obtemAno = new SimpleDateFormat("yyyy");

		// obtem mês
		SimpleDateFormat obtemMes = new SimpleDateFormat("MM");

		// ano
		String anoData = null;

		// mês
		String mesData = null;

		// data
		Date data;

		// loop verificação
		for (Venda venda : listaVendas) {

			// obtem a data
			data = venda.getVenDataVenda();

			// extrai o ano da data
			anoData = obtemAno.format(data);

			// extrai o mês da data
			mesData = obtemMes.format(data);

			// se data recebida for igual a data de registro do banco
			if (anoData.equalsIgnoreCase(ano) && mesData.equalsIgnoreCase(mes)) {

				// adiciona venda a lista de vendas
				listaVendasEncontradas.add(venda);

			}// fim if

		}// for

		// retorna uma lista de vendas
		return listaVendasEncontradas;

	}// fim listVendasPorMes()

	// Obj: vendas por um dia
	public List<Venda> listarVendasPorVendedorDia(String dia, Vendedor vendedor)
			throws ParseException {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// atribuir e receber formato
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		// Date
		java.util.Date diaUtil = new Date();

		// e converte para Date
		diaUtil = df.parse(dia);

		// consulta
		TypedQuery<Venda> query = gerenciadorEntidades.createNamedQuery(
				"Venda.findByVenDataVendaPorVendedor", Venda.class);

		// define parâmetro
		query.setParameter("venDataVenda", diaUtil);

		// define parâmetro
		query.setParameter("vendedorId", vendedor);

		// define lista de vendas
		List<Venda> listaVendas = query.getResultList();

		// retorna uma lista de vendas
		return listaVendas;

	}// fim listVendasPorDia()

	// obj: Vendas realizadas por vendedor em um determinado mês
	public List<Venda> listarVendasPorVendedorMes(String mes, String ano,
			Vendedor vendedor) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// recupero todas as vendas
		List<Venda> listaVendas = listarTodos();

		// retorna a lista se satisfazer a condição
		List<Venda> listaVendasEncontradas = new ArrayList<>();

		// obtem ano
		SimpleDateFormat obtemAno = new SimpleDateFormat("yyyy");

		// obtem mês
		SimpleDateFormat obtemMes = new SimpleDateFormat("MM");

		// ano
		String anoData = null;

		// mês
		String mesData = null;

		// data
		Date data;

		// loop de verificação
		for (Venda venda : listaVendas) {

			// obtem a data
			data = venda.getVenDataVenda();

			// extrai o ano da data
			anoData = obtemAno.format(data);

			// extrai o mês da data
			mesData = obtemMes.format(data);

			// se data recebida for igual a data de registro do banco
			if (anoData.equalsIgnoreCase(ano)
					&& mesData.equalsIgnoreCase(mes)
					&& venda.getVendedorVenId().getVenId() == vendedor
							.getVenId()) {

				// adiciona venda a lista vendas
				listaVendasEncontradas.add(venda);

			}// fim if

		}// fim for

		// retorna uma lista de vendas
		return listaVendasEncontradas;

	}// fim listVendasPorMes()

	// obj: Retornar uma lista vendas de acordo com os parâmetros
	@SuppressWarnings("unchecked")
	public List<Venda> listarVendas(Integer porPagina, Integer pagAtual,
			String nomePaginacao, String nomeBusca) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// define offset
		Integer offset = PaginacaoAuxiliarDao.getOffset(porPagina, pagAtual);

		// inicializa
		String sqlNomeClausula = "";

		// verificação se a opção é busca
		if (nomePaginacao.equalsIgnoreCase("BUSCA")) {
			sqlNomeClausula = "WHERE ven_data_venda	 = '" + nomeBusca + "'";
		}// fim if

		// verificação se a opção é normal
		if (nomePaginacao.equalsIgnoreCase("NORMAL")) {
			sqlNomeClausula = "";
		}// fim if

		// define estrutura jpql
		String jpql = "SELECT * FROM venda " + sqlNomeClausula + " LIMIT "
				+ porPagina + " OFFSET " + offset;

		// consulta
		Query query = gerenciadorEntidades.createNativeQuery(jpql, Venda.class);

		// define lista de vendas
		List<Venda> listaVenda = query.getResultList();

		// fecha conexão
		gerenciadorEntidades.close();

		// retorna uma lista de fornecedores
		return listaVenda;

	}// fim listarVendas()

	// obj: retorna o número de paginações
	public Integer numeroDePaginacao(int porPagina, String nomePaginacao,
			String nomeBusca) {

		// inicializa
		String sqlNomeClausula = "";

		// verificação se a opção é busca
		if (nomePaginacao.equalsIgnoreCase("BUSCA")) {
			sqlNomeClausula = "WHERE ven_data_venda	 = '" + nomeBusca + "'";
		}// fim if

		// verificação se a opção é normal
		if (nomePaginacao.equalsIgnoreCase("NORMAL")) {
			sqlNomeClausula = "";
		}// fim if

		// retorna o número de paginações
		return PaginacaoAuxiliarDao.numeroDePaginacao("venda", porPagina,
				sqlNomeClausula);

	}// fim numeroDePaginacao()

	// obj: Número total de vendas do sistema
	public String numeroVendas() {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		Query query = gerenciadorEntidades
				.createNamedQuery("Venda.numeroVendas");

		// retorna o número total de vendsa
		return query.getResultList().get(0).toString();

	}// fim numeroVendas()

	// obj: retorna o número de registros
	public String numeroDeRegistrosBusca(String nomeBusca) {
		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// define estrutura jpql
		String jpql = "SELECT COUNT(v.ven_data_venda) FROM venda v WHERE v.ven_data_venda = '"
				+ nomeBusca + "'";

		Query consulta = gerenciadorEntidades.createNativeQuery(jpql);

		String numeroTotalRegBusca = consulta.getResultList().get(0).toString();

		// retorna o número de paginações
		return numeroTotalRegBusca;

	}// fim numeroDeRegistrosBusca()

	// obj: atualizar venda
	@Override
	public void atualizar(Venda venda) {

	}
}// fim class VendaDao
