package br.com.sis.floricultura.application;

import java.util.List;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sis.floricultura.dao.EstadoDao;
import br.com.sis.floricultura.model.Estado;

@Component
public class EstadoApplication {

	private EstadoDao estadoDao;

	public EstadoApplication() {
		
		estadoDao = new EstadoDao();
	}

	public List<Estado> listaTodosEstados() {

		return estadoDao.listaTodosEstados();
	}

	public Estado carregaEstado(Integer idEstado) {

		return estadoDao.carrega(idEstado);
	}
	
	public List<Estado> buscaEstados(String term){
		
		return estadoDao.buscaEstados(term);
	}
}
