package br.com.sis.floricultura.application;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sis.floricultura.dao.EnderecoDao;
import br.com.sis.floricultura.model.Endereco;

@Component
public class EnderecoApplication {

	private EnderecoDao enderecoDao;

	public EnderecoApplication() {
		enderecoDao = new EnderecoDao();
	}

	public Endereco carregaEndereco(Integer idEndereco) {

		return enderecoDao.carregar(idEndereco);
	}

	public void atualizaEndereco(Endereco endereco) {
		enderecoDao.atualizar(endereco);

	}
	
	public void salvaEndereco(Endereco endereco){
		
		enderecoDao.adicionar(endereco);
	}
	
	public Endereco buscarUltimoEndereco(){
		return enderecoDao.buscarUltimoEndereco();
	}
}
