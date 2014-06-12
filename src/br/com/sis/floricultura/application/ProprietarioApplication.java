package br.com.sis.floricultura.application;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sis.floricultura.dao.ProprietarioDao;
import br.com.sis.floricultura.dao.UsuarioDao;
import br.com.sis.floricultura.exception.UsuarioInvalidoException;
import br.com.sis.floricultura.model.Proprietario;
import br.com.sis.floricultura.model.Usuario;
import br.com.sis.floricultura.service.UsuarioService;

@Component
public class ProprietarioApplication {

	private ProprietarioDao proprietarioDao; // proprietário dao
	private UsuarioDao usuarioDao; // usuário dao
	private UsuarioService usuarioService;

	// numero de registros por página- paginação
	// private final Integer NUMERO_DE_REGISTROS_POR_PAGINA = 2;

	// construtor
	public ProprietarioApplication() {
		super();
		proprietarioDao = new ProprietarioDao();
		usuarioDao = new UsuarioDao();
		usuarioService = new UsuarioService(usuarioDao);
	}// fim construtor

	public void editar(Proprietario proprietario, Usuario usuario)
			throws UsuarioInvalidoException {

		// valida
		usuarioService.usuarioExisteEditar(usuario);

		// atualiza usuário
		usuarioDao.atualizar(usuario);

		// atualiza proprietário
		proprietarioDao.atualizar(proprietario);

	}// fim editar()

	public String obterNomeProprietarioUsuarioId(Integer usuarioId) {
		return proprietarioDao.obterProprietarioUsuarioId(usuarioId)
				.getProNome();

	}// fim obterNomeProprietarioUsuarioId()

	public Integer obterIdProprietarioUsuarioId(Integer usuarioId) {
		return proprietarioDao.obterProprietarioUsuarioId(usuarioId).getProId();

	}// fim obterIdProprietarioUsuarioId()

	public Proprietario carregaProprietario(Integer idProprietario) {

		return proprietarioDao.carregar(idProprietario);
	}
}// fim class
