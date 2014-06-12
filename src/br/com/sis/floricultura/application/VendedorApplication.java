package br.com.sis.floricultura.application;

import java.util.List;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sis.floricultura.dao.EnderecoDao;
import br.com.sis.floricultura.dao.UsuarioDao;
import br.com.sis.floricultura.dao.VendedorDao;
import br.com.sis.floricultura.exception.CpfInvalidoException;
import br.com.sis.floricultura.exception.ExcluirSelecInvalidoException;
import br.com.sis.floricultura.exception.UsuarioInvalidoException;
import br.com.sis.floricultura.model.Endereco;
import br.com.sis.floricultura.model.Produtos;
import br.com.sis.floricultura.model.Usuario;
import br.com.sis.floricultura.model.Vendedor;
import br.com.sis.floricultura.service.UsuarioService;
import br.com.sis.floricultura.service.VendedorService;

@Component
public class VendedorApplication {

	private final Integer REGISTROS_POR_PAGINA = 4;
	private VendedorDao vendedorDao;
	private UsuarioDao usuarioDao;
	private EnderecoDao enderecoDao;
	private VendedorService vendedorService;
	private UsuarioService usuarioService;

	public VendedorApplication() {
		vendedorDao = new VendedorDao();
		vendedorService = new VendedorService(vendedorDao);
		usuarioDao = new UsuarioDao();
		usuarioService = new UsuarioService(usuarioDao);
		enderecoDao = new EnderecoDao();

	}

	public List<Vendedor> listarVendedores(Integer pagAtual,
			String nomePaginacao, String nomeBusca) {

		return vendedorDao.listarVendedores(REGISTROS_POR_PAGINA, pagAtual,
				nomePaginacao, nomeBusca);
	}

	public Integer numeroDePaginacao(String nomePaginacao, String nomeBusca) {

		return vendedorDao.numeroDePaginacao(REGISTROS_POR_PAGINA,
				nomePaginacao, nomeBusca);

	}

	public void excluirVendedor(Integer idVendedor) {

		// carrega objeto do tipo vendedor
		Vendedor vendedor = vendedorDao.carregar(idVendedor);

		// carrega objeto do tipo usuário
		Usuario usuario = usuarioDao.carregar(vendedor.getUsuarioUsuId());

		// exclui usuário
		usuarioDao.deletar(usuario.getUsuId());

		// exclui vendedor
		vendedorDao.deletar(idVendedor);
	}

	public void excluirVendedorSelcionados(List<Vendedor> vendedores)
			throws ExcluirSelecInvalidoException {

		// validação
		vendedorService.validaSeExisteSelecValido(vendedores);

		for (Vendedor vendedor : vendedores) {

			// carrega objeto do tipo vendedor
			vendedor = vendedorDao.carregar(vendedor.getVenId());

			// carrega objeto do tipo usuário
			Usuario usuario = usuarioDao.carregar(vendedor.getUsuarioUsuId());

			// exclui usuário
			usuarioDao.deletar(usuario.getUsuId());

			// exclui vendedor
			vendedorDao.deletar(vendedor.getVenId());
		}
	}

	public Vendedor carregaVendedor(Integer idVendedor) {

		return vendedorDao.carregar(idVendedor);
	}

	public void atualizaVendedor(Vendedor vendedor) {

	}

	public List<Vendedor> buscarProdutos(String term) {

		return vendedorDao.buscarVendedores(term);
	}

	public void salvarVendedor(Vendedor vendedor, Usuario usuario,
			Endereco endereco) throws CpfInvalidoException,
			UsuarioInvalidoException {

		// validação cpf do vendedor
		// se não existe valida cpf
		vendedorService.validaCPFVendedor(vendedor.getVenCpf());
		
		// se existe outro
		vendedorService.validaCPFVendedorAdicionar(vendedor);

		// validação do nome de usuário se outro existe
		usuarioService.usuarioExisteAdicionar(usuario);

		// adiciona endereço
		enderecoDao.adicionar(endereco);

		/*
		 * define atributo do cliente vinculando o último endereço adicionado a
		 * ele
		 */
		// carrega objeto do tipo endereço
		Endereco ultimoEndereco = enderecoDao.buscarUltimoEndereco();

		// define atributo
		vendedor.setEnderecoEndId(ultimoEndereco);

		// adiciona usuário
		usuarioDao.adicionar(usuario);

		// define atributo
		vendedor.setUsuarioUsuId(usuarioDao.buscarUltimoUsuario());

		// adiciona vendedor
		vendedorDao.adicionar(vendedor);

	}

	public void atualizaVendedor(Vendedor vendedor, Usuario usuario,
			Endereco endereco) throws CpfInvalidoException,
			UsuarioInvalidoException {

		// validação cpf do vendedor

		// se existe outro
		vendedorService.validaCPFVendedorEditar(vendedor);

		// se não existe valida cpf
		vendedorService.validaCPFVendedor(vendedor.getVenCpf());

		// validação do nome de usuário se outro existe
		usuarioService.usuarioExisteEditar(usuario);

		// adiciona endereço
		enderecoDao.atualizar(endereco);

		/*
		 * define atributo do cliente vinculando o último endereço adicionado a
		 * ele
		 */
		// carrega objeto do tipo endereço
		Endereco enderecoCarregado = enderecoDao.carregar(endereco.getEndId());

		// define atributo
		vendedor.setEnderecoEndId(enderecoCarregado);

		// adiciona usuário
		usuarioDao.atualizar(usuario);

		Integer idUsuario = usuario.getUsuId();
		// define atributo
		vendedor.setUsuarioUsuId(idUsuario);

		// adiciona vendedor
		vendedorDao.atualizar(vendedor);
	}

	public boolean validaCpfEditar(Vendedor vendedor) {

		return vendedorDao.validaCpfEditar(vendedor);
	}

	public boolean validaCpfAdicionar(Vendedor vendedor) {

		return vendedorDao.validaCpfAdicionar(vendedor);
	}

	public List<Vendedor> buscarVendedores(String term) {

		return vendedorDao.buscarVendedores(term);
	}

	public String numeroTotalVendedores() {

		return vendedorDao.numeroVendedores();
	}

	public String numeroRegistroFornecedorBuscar(String busca) {

		return vendedorDao.numeroDeRegistrosBusca(busca);
	}

	public String obterNomeVendedorUsuarioId(Integer idUsuario) {

		return vendedorDao.obterNomeVendedorUsuarioId(idUsuario);
	}
}
