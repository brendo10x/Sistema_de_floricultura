package br.com.sis.floricultura.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sis.floricultura.connection.FabricaDeConexao;
import br.com.sis.floricultura.model.Cidade;
import br.com.sis.floricultura.model.Estado;

@Component
public class EstadoDao {

	// vari�vel gerenciador de entidades
	private EntityManager gerenciadorEntidades;
	private List<Estado> listaEstados; // carrega todos os estados
	private Integer totalListaEstados; // total lista de estados

	public EstadoDao() {

		listaTodosEstados();// carrega todos os estados

	}// fim construtor padr�o

	// obj: retorna uma lista de todos os estados
	public List<Estado> listaTodosEstados() {

		// entrega inst�ncia do tipo EntityManager com conex�o mysql
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// consulta
		listaEstados = gerenciadorEntidades.createNamedQuery("Estado.findAll",
				Estado.class).getResultList();

		// total lista
		totalListaEstados = listaEstados.size();

		// fecha conex�o
		gerenciadorEntidades.close();

		// retorna lista de estados
		return listaEstados;

	}// fim listaTodosEstados()

	// obj: retorna uma lista de estados de acordo com consulta
	public List<Estado> buscaEstados(String consulta) {

		// define lista de estados achados
		List<Estado> estadosAchados = new ArrayList<>();

		// converte para letras min�sculas
		consulta.toLowerCase();

		// loop de verifica��o
		for (int i = 0; i < totalListaEstados; i++) {

			// se for
			if (listaEstados.get(i).getEstDescricao().toLowerCase()
					.startsWith(consulta)) {

				// adiciona estado a lista de estados
				estadosAchados.add(listaEstados.get(i));

			}// fim if

		}// fim for

		// retorna lista de estados
		return estadosAchados;

	}// fim buscaEstados()

	// obj: carrega objeto do tipo Estado
	public Estado carrega(Integer id) {

		// entrega inst�ncia do tipo EntityManager com conex�o postgres
		gerenciadorEntidades = FabricaDeConexao.getInstanciaConexaoMysql();

		// carrega objeto do tipo Estado
		Estado estado = gerenciadorEntidades.find(Estado.class, id);
		
		// fecha conex�o
		gerenciadorEntidades.close();
		
		//retorna objeto estado
		return estado;
		
	}// fim carrega()

}// fim class EstadoDao
