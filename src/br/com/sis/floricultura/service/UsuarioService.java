package br.com.sis.floricultura.service;

import br.com.sis.floricultura.dao.UsuarioDao;
import br.com.sis.floricultura.exception.UsuarioInvalidoException;
import br.com.sis.floricultura.model.Usuario;

public class UsuarioService {

	private UsuarioDao usuarioDao;

	public UsuarioService(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	public void usuarioExisteAdicionar(Usuario usuario) throws UsuarioInvalidoException {

		if (usuarioDao.validaUsuarioAdicionar(usuario)) {

			throw new UsuarioInvalidoException("Nome de usuário '"
					+ usuario.getUsuNome() + "' já existe no adicionar!");

		}// fim if

	}// usuarioExisteAdicionar()

	public void usuarioExisteEditar(Usuario usuario) throws UsuarioInvalidoException {

		if (usuarioDao.validaUsuarioEditar(usuario)) {

			throw new UsuarioInvalidoException("Nome de usuário '"
					+ usuario.getUsuNome() + "' já existe no editar!");
		}// fim if

	}// usuarioExisteEditar()

	public void validacaoLogin(Usuario usuario) throws UsuarioInvalidoException {

		if (usuarioDao.carregaUsuario(usuario) == null) {

			throw new UsuarioInvalidoException("Login e/ou senha inválidos");
		}
	}
}
