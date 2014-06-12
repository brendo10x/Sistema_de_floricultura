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
import br.com.sis.floricultura.application.CidadeApplication;
import br.com.sis.floricultura.dao.CidadeDao;
import br.com.sis.floricultura.dao.EstadoDao;
import br.com.sis.floricultura.intercepts.Liberado;
import br.com.sis.floricultura.model.Estado;

/*Classe controladora CidadeController contém 
 as regras de negócio, com anotação @Resource para o
 Vraptor saber que essa classe é um controller*/

@Resource
public class CidadeController {
	
	private Result result;// mostra resultados
	private CidadeApplication cidadeApplication;
	
	public CidadeController(Result result,CidadeApplication cidadeApplication) {
		this.result = result;// mostra resultados
		this.cidadeApplication = cidadeApplication; // cidade dao
		
	}// fim do construtor
	
	
	@Get
	// método get
	@Path("/cidade/busca.json")
	// url de caminho
	// lógica de negócio
	public void buscarCidade(String term) throws IOException {

		// recebe uma lista de objetos e retorna uma lista no formato json
		result.use(Results.json()).withoutRoot()
				.from(cidadeApplication.listaDeCidades(Integer.parseInt(term)))
				.serialize();

	}// fim novo()
	
}// fim class CidadeController
