//pacote
package br.com.sis.floricultura.controller;

//import's
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sis.floricultura.application.EstadoApplication;
import br.com.sis.floricultura.dao.EstadoDao;
import br.com.sis.floricultura.intercepts.Liberado;
import br.com.sis.floricultura.model.Estado;

/*Classe controladora EstadoController contém 
 as regras de negócio, com anotação @Resource para o
 Vraptor saber que essa classe é um controller*/

@Resource
public class EstadoController {

	private Result result;// mostra resultados
	private EstadoApplication estadoApplication;

	public EstadoController(Result result, EstadoDao estadoDao,
			EstadoApplication estadoApplication) {
		this.result = result;
		this.estadoApplication = estadoApplication;
	}// fim do construtor

	// Acesso
	@Liberado
	@Get
	// método get
	@Path("/estado/busca.json")
	// url de caminho
	// lógica de negócio
	public void buscarEstado(String term) throws IOException {

		// recebe uma lista de objetos e retorna uma lista no formato json
		result.use(Results.json()).withoutRoot()
				.from(estadoApplication.buscaEstados(term)).exclude("estSigla")
				.serialize();

	}// fim novo()

}// fim class EstadoController
