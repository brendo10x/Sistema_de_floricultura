package br.com.sis.floricultura.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.JOptionPane;

import org.hibernate.Criteria;

import br.com.sis.floricultura.connection.FabricaDeConexao;

public abstract class PaginacaoAuxiliarDao {

	// vari�vel gerenciador de entidades
	private static EntityManager gerenciadorEntidades;

	// obj: retorna o n�mero de offset
	// aqui vai me recuperar o offset para a pagina��o
	// paremetros -> Qtd. de itens por p�gina, p�gina atual
	public static Integer getOffset(int PorPagina, int pagAtual) {
		int offset;
		int pagReal;

		pagReal = pagAtual - 1;
		offset = pagReal * PorPagina;
		return offset;

	}// fim getOffset

	// obj: retorna o n�mero de pagina��es
	public static Integer numeroDePaginacao(String entidade, int porPagina,
			String clausula) {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		boolean condicao = true;
		int pAtual = 1;
		Query consulta;
		while (condicao) {

			int offset = getOffset(porPagina, pAtual);

			String jpql = "SELECT * FROM " + entidade + " " + clausula
					+ " LIMIT " + porPagina + " OFFSET " + offset;
			consulta = gerenciadorEntidades.createNativeQuery(jpql);

			int resultado = consulta.getResultList().size();

			if (resultado == 0) {
				break;
			}// fim if

			pAtual++;

		}// fim while

		return pAtual - 1;

	}// fim numeroDePaginacao()

	// obj: retorna o n�mero de pagina��es
	public static Integer numeroTotalRegistros(String entidade, String clausula) {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		String jpql = "SELECT SUM(*) AS total FROM " + entidade + " "
				+ clausula;

		return (Integer) gerenciadorEntidades
				.createNativeQuery(jpql).getResultList().get(0);

	}// fim numeroDePaginacao()

}// fim class PaginacaoAuxiliar
