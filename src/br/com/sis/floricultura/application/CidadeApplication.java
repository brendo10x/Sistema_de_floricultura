package br.com.sis.floricultura.application;

import java.util.List;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sis.floricultura.dao.CidadeDao;
import br.com.sis.floricultura.model.Cidade;

@Component
public class CidadeApplication {

	private CidadeDao cidadeDao;
	
	public CidadeApplication() {
		cidadeDao = new CidadeDao();
	}
	
	public Cidade carregaCidade(Integer idCidade){
		return cidadeDao.carrega(idCidade);
		
	}
	
	public List<Cidade> listaDeCidades(Integer term){
		
		return cidadeDao.listaDeCidades(term);
	}
}
