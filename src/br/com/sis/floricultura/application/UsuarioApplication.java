package br.com.sis.floricultura.application;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.caelum.vraptor.ioc.SessionScoped;
import br.com.sis.floricultura.dao.UsuarioDao;
import br.com.sis.floricultura.exception.UsuarioInvalidoException;
import br.com.sis.floricultura.model.Usuario;
import br.com.sis.floricultura.service.UsuarioService;

@Component
public class UsuarioApplication {

	private UsuarioDao usuarioDao;
	private UsuarioService usuarioService;

	public UsuarioApplication() {
		usuarioDao = new UsuarioDao();
		usuarioService = new UsuarioService(usuarioDao);
	}

	public Usuario autenticaoLogin(Usuario usuario) throws UsuarioInvalidoException {

		// validação
		usuarioService.validacaoLogin(usuario);

		// se válido retorna
		return usuarioDao.carregaUsuario(usuario);
	}

	public Usuario carregaUsuario(Integer idUsuario) {

		return usuarioDao.carregar(idUsuario);
	}

	public void excluirUsuario(Integer idUsuario) {
		usuarioDao.deletar(idUsuario);
	}

	public void atualizaUsuario(Usuario usuario) {
		usuarioDao.atualizar(usuario);

	}

	public boolean validaUsuarioAdicionar(Usuario usuario) {
		return usuarioDao.validaUsuarioAdicionar(usuario);
	}

	public boolean validaUsuarioEditar(Usuario usuario) {

		return validaUsuarioEditar(usuario);
	}

	public void salvaUsuario(Usuario usuario) {

		usuarioDao.adicionar(usuario);
	}

	public Integer buscarUltimoUsuario() {
		return usuarioDao.buscarUltimoUsuario();
	}

	public String numeroTotalUsuarios(){
		
		return usuarioDao.numeroUsuarios();
	}
	
}
