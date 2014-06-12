//pacote
package br.com.sis.floricultura.dao;

//import's
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.JOptionPane;

import org.apache.commons.collections.iterators.ArrayIterator;
import org.apache.commons.collections.iterators.ArrayListIterator;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sis.floricultura.connection.FabricaDeConexao;
import br.com.sis.floricultura.model.Cliente;
import br.com.sis.floricultura.model.Produtos;
import br.com.sis.floricultura.model.ProdutosVendidos;

/*
 Esta classe (ProdutoDao) anotada com @Component notifica para o Vraptor que o 
 framework se encarregará de gerenciar suas instâncias,isto é, ele criará uma instância
 e utilizará como argumento para o construtor controller.
 */

@Component
public class ProdutoDao extends Dao<Produtos> {

	// variável gerenciador de entidades
	private EntityManager gerenciadorEntidades;

	public ProdutoDao() {

	}// contrutor padrão ProdutoDao

	// obj: adiciona produto
	@Override
	public void adicionar(Produtos produto) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		try {

			// inicia a transação
			gerenciadorEntidades.getTransaction().begin();

			// adiciona proprietário
			gerenciadorEntidades.persist(produto);

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

	// obj: retornar uma lista de produtos
	@Override
	public List<Produtos> listarTodos() {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		TypedQuery<Produtos> query = gerenciadorEntidades.createNamedQuery(
				"Produtos.findAllOrderByProIdDesc", Produtos.class);

		// define lista de Produtos
		List<Produtos> listaProdutos = query.getResultList();

		// retorna uma lista de objetos do tipo Produtos
		return listaProdutos;

	}// fim listAll()

	// obj: excluir Produto
	@Override
	public void deletar(Integer id) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();
		try {

			// inicia a transação
			gerenciadorEntidades.getTransaction().begin();

			// carrega objeto do tipo Produtos
			Produtos produto = gerenciadorEntidades.find(Produtos.class, id);

			// excluir Produto
			gerenciadorEntidades.remove(produto);

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

	// obj: Carrega objeto do tipo Produtos
	@Override
	public Produtos carregar(Integer id) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// carrega objeto do tipo Produtos
		Produtos produto = gerenciadorEntidades.find(Produtos.class, id);

		// fecha conexão
		gerenciadorEntidades.close();

		// retorna produto
		return produto;

	}// fim loadProduto()

	// obj: atuliza produto
	@Override
	public void atualizar(Produtos produto) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		try {

			// inicia a transação
			gerenciadorEntidades.getTransaction().begin();

			// atuliza fornecedor
			gerenciadorEntidades.merge(produto);

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

	// obj: buscar lista de produtos de acordo com a consulta
	public List<Produtos> buscarProdutos(String consulta) {

		// define lista de produtos achados
		List<Produtos> produtosAchados = new ArrayList<>();

		// define lista de todos os produtos do banco
		List<Produtos> produtos = listarTodos();

		// transforma todas as letras para minúscula
		consulta = consulta.toLowerCase();

		// loop verificando se a consulta é igual a do banco
		for (int i = 0; i < produtos.size(); i++) {

			// se for
			if (produtos.get(i).getProNome().toLowerCase().startsWith(consulta)) {

				// adiciona produtos a lista de produtos
				produtosAchados.add(produtos.get(i));

			}// fim if

		}// fim for

		// retorna lista de produtos achados
		return produtosAchados;

	}// fim buscarProdutos()

	// obj: retornar uma lista de produtos mais vendidos
	@SuppressWarnings("unchecked")
	public List<Produtos> produtosMaisVendidos(String ano, String mes) {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// define lista de produtos achados
		List<Produtos> produtosAchados = new ArrayList<>();

		// define lista de produtos vendidos achados
		List<ProdutosVendidos> produtosVendidosAchados = new ArrayList<>();

		// define lista de todos os produtos do banco
		List<Produtos> listaProdutos = listarTodos();

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
		for (Produtos produtos : listaProdutos) {

			// verifica se o produtos de produtos vendidos tem a data compatível
			// com a consulta
			for (ProdutosVendidos produtosVendidos : produtos
					.getProdutosVendidosList()) {
				// obtem a data
				data = produtosVendidos.getVendaVenId().getVenDataVenda();
				// extrai o ano da data
				anoData = obtemAno.format(data);
				// extrai o mês da data
				mesData = obtemMes.format(data);

				// se for
				if (anoData.equalsIgnoreCase(ano)
						&& mesData.equalsIgnoreCase(mes)) {

					// adiciona a lista de produtos vendidos
					produtosVendidosAchados.add(produtosVendidos);

				}// fim if

			}// fim for

			// define atributo
			produtos.setProdutosVendidosList(produtosVendidosAchados);

			// verificação - Concatenca IN Sql
			if (produtos.getProdutosVendidosList().size() > 0) {
				String concatenaIN = "IN(";
				int TamanhadoLista = produtos.getProdutosVendidosList().size();
				for (int i = 0; i < TamanhadoLista; i++) {

					ProdutosVendidos produtosVendidos = produtos
							.getProdutosVendidosList().get(i);
					concatenaIN = concatenaIN + produtosVendidos.getProVendid();

					if (TamanhadoLista > i + 1) {

						concatenaIN = concatenaIN + ",";

					}// fim if

				}// fim for

				// concatena
				concatenaIN = concatenaIN + ")";

				// define estrutura sql
				String sql = "select p.pro_nome,SUM(p_v.proVend_quantidades) AS total "
						+ "from produtos p inner join produtos_vendidos p_v on p_v.produtos_pro_id = p.pro_id INNER JOIN "
						+ "venda v ON p_v.venda_ven_id = v.ven_id WHERE p_v.produtos_pro_id = "
						+ produtos.getProId()
						+ " AND p_v.proVend_id "
						+ concatenaIN
						+ " GROUP BY p.pro_nome ORDER BY total DESC";

				// consulta
				Query nativeQuery = gerenciadorEntidades.createNativeQuery(sql);

				// recupera lista de objetos da consulta
				List<Object[]> listaObjetos = nativeQuery.getResultList();

				// recupera os objetos da lista escolhida 0
				Object[] objects = listaObjetos.get(0);

				// define atributo ou atualiza quantidade
				produtos.setProQuantidade(Integer.parseInt(objects[1]
						.toString()));

				// adiciona produto a lista de produtos achados
				produtosAchados.add(produtos);

				// limpa lista para a próx verificação
				produtosVendidosAchados.clear();

			}// fim if

		}// fim for

		// carrega um array de produtos de uma lista
		Produtos[] produtosArray = new Produtos[produtosAchados.size()];
		for (int i = 0; i < produtosAchados.size(); i++) {
			produtosArray[i] = produtosAchados.get(i);
		}

		// Ordenação por quantidade
		Produtos produto = null;
		for (int i = 0; i < produtosArray.length; i++) {

			for (int j = 0; j < produtosArray.length - 1; j++) {
				if (produtosArray[j].getProQuantidade() <= produtosArray[j + 1]
						.getProQuantidade()) {
					produto = produtosArray[j];
					produtosArray[j] = produtosArray[j + 1];
					produtosArray[j + 1] = produto;
				}
			}
		}

		// carrega um lista de produtos de uma array ordenado
		List<Produtos> listaProdutosProntos = new ArrayList<>();
		for (int i = 0; i < produtosArray.length; i++) {
			listaProdutosProntos.add(i, produtosArray[i]);
		}

		// retorna lista de produtos
		return listaProdutosProntos;

	}// fim produtosMaisVendidos()

	// obj: Lista todos os produtos de acordo com os parâmetros
	@SuppressWarnings("unchecked")
	public List<Produtos> listarProdutos(Integer porPagina, Integer pagAtual,
			String nomePaginacao, String nomeBusca) {

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
		String jpql = "SELECT * FROM produtos " + sqlNomeClausula + " LIMIT "
				+ porPagina + " OFFSET " + offset;

		// consulta
		Query query = gerenciadorEntidades.createNativeQuery(jpql,
				Produtos.class);

		// define lista de produtos
		List<Produtos> listaProduto = query.getResultList();

		// retorna uma lista de produtos
		return listaProduto;

	}// fim listarProdutos()

	// obj: retorna o número de paginações
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
		return PaginacaoAuxiliarDao.numeroDePaginacao("produtos", porPagina,
				sqlNomeClausula);

	}// fim numeroDePaginacao()

	// obj: retorna o número de registros
	public String numeroDeRegistrosBusca(String nomeBusca) {
		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// define estrutura jpql
		String jpql = "SELECT COUNT(p.pro_nome) FROM produtos p WHERE p.pro_nome like '%"
				+ nomeBusca + "%'";

		Query consulta = gerenciadorEntidades.createNativeQuery(jpql);

		String numeroTotalRegBusca = consulta.getResultList().get(0).toString();

		// retorna o número de paginações
		return numeroTotalRegBusca;

	}// fim numeroDeRegistrosBusca()

	// obj: retorna o número total de produtos no sistema
	public String numeroProdutos() {

		// entrega instância do tipo EntityManager com conexão mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		Query query = gerenciadorEntidades
				.createNamedQuery("Produtos.numeroProdutos");

		// retorna número total de produtos
		return query.getResultList().get(0).toString();

	}// fim numeroProdutos()

}// fim class ProdutoDao
