//pacote
package br.com.sis.floricultura.controller;

//import's
import java.io.IOException;
import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.com.caelum.vraptor.view.Results;
import br.com.sis.floricultura.application.EnderecoApplication;
import br.com.sis.floricultura.application.EstadoApplication;
import br.com.sis.floricultura.application.UsuarioApplication;
import br.com.sis.floricultura.application.VendedorApplication;
import br.com.sis.floricultura.dao.EnderecoDao;
import br.com.sis.floricultura.dao.EstadoDao;
import br.com.sis.floricultura.dao.UsuarioDao;
import br.com.sis.floricultura.dao.VendedorDao;
import br.com.sis.floricultura.exception.CpfInvalidoException;
import br.com.sis.floricultura.exception.ExcluirSelecInvalidoException;
import br.com.sis.floricultura.exception.UsuarioInvalidoException;
import br.com.sis.floricultura.intercepts.BloqueiaVendedor;
import br.com.sis.floricultura.intercepts.Liberado;
import br.com.sis.floricultura.model.Endereco;
import br.com.sis.floricultura.model.Usuario;
import br.com.sis.floricultura.model.Vendedor;

/*Classe controladora VendedorController contém 
 as regras de negócio, com anotação @Resource para o
 Vraptor saber que essa classe é um controller*/

@Resource
public class VendedorController {

	private Result result;// mostra resultados
	private Validator validator; // validação
	private EstadoApplication estadoApplication; // estado app
	private VendedorApplication vendedorApplication; // vendedor app
	private EnderecoApplication enderecoApplication;
	private UsuarioApplication usuarioApplication;

	// contrutor padrão inicializa as dependências que seram usadas
	public VendedorController(Result result, Validator validator,
			EstadoApplication estadoApplication,
			VendedorApplication vendedorApplication,
			EnderecoApplication enderecoApplication,
			UsuarioApplication usuarioApplication) {

		this.result = result;
		this.validator = validator;
		this.estadoApplication = estadoApplication;
		this.vendedorApplication = vendedorApplication;
		this.enderecoApplication = enderecoApplication;
		this.usuarioApplication = usuarioApplication;

	}// fim contrutor padrão

	// Acesso
	@BloqueiaVendedor
	// redireciona para a página de formulário
	@Get
	// método get
	@Path("vendedor/novo")
	// url de caminho
	// lógica de negócio
	public void novo() {

		// define título da página
		result.include("titulo", "Adicionar vendedor");

		// define atributo para a jsp
		// retorna uma lista de estados
		result.include("estados", estadoApplication.listaTodosEstados());

	}// fim novo()

	// Acesso
	@BloqueiaVendedor
	// ação de adicionar efitivamente
	@Post
	// método post
	@Path("vendedor/adicionar")
	// url de caminho
	public void adicionar(Vendedor vendedor, Usuario usuario, Endereco endereco) {

		// define título da página
		result.include("titulo", "Adicionar vendedor");

		// define atributo para a jsp
		// retorna uma lista de estados
		result.include("estados", estadoApplication.listaTodosEstados());
		result.include("vendedor", vendedor);
		result.include("usuario", usuario);
		result.include("endereco", endereco);

		try {
			// processo de salvar vendedor
			vendedorApplication.salvarVendedor(vendedor, usuario, endereco);
		} catch (CpfInvalidoException e) {
			validator.add(new ValidationMessage(e.getMessage(), null));

		} catch (UsuarioInvalidoException e) {
			validator.add(new ValidationMessage(e.getMessage(), null));
		}

		// redireciona para a página novo() caso houver erro na validação
		validator.onErrorUsePageOf(VendedorController.class).novo();

		// define atributo para a jsp
		// adicionado com sucesso
		result.include("sucesso_adicionado",
				"Vendedor " + vendedor.getVenNome()
						+ " adicionado com sucesso!");

		// redirecionando para a página novo()
		result.redirectTo(VendedorController.class).novo();

	}// fim adicionar()

	// Acesso
	@BloqueiaVendedor
	// página de listar vendedor
	@Get
	@Path("vendedor/listar")
	public List<Vendedor> listar(Integer pagina) {

		// define título da página
		result.include("titulo", "Listar vendedores");

		// total de registros
		result.include("totalVendedores",
				vendedorApplication.numeroTotalVendedores());

		// define atributo para a jsp
		// Número de paginações
		result.include("numeroDePaginacao",
				vendedorApplication.numeroDePaginacao("NORMAL", ""));

		// define atributo para a jsp
		// Página atual
		result.include("paginaAtual", pagina == null ? pagina = 1 : pagina);

		// retorna uma lista de vendedores
		return vendedorApplication.listarVendedores(pagina, "NORMAL", "");

	}// fim listar()

	// Permissão
	@BloqueiaVendedor
	// página de listar vendedores pesquisados
	@Post
	@Get
	@Path({ "vendedor/lista-buscar", "vendedor/lista-buscar/{busca}" })
	public List<Vendedor> buscar(Integer pagina, String busca) {

		// define título da página
		result.include("titulo", "Listar vendedores");

		// define atributo para a jsp
		// Número de paginações
		result.include("numeroDePaginacao",
				vendedorApplication.numeroDePaginacao("BUSCA", busca));

		// total de registros
		result.include("totalVendedores",
				vendedorApplication.numeroRegistroFornecedorBuscar(busca));

		// define atributo para a jsp
		// Página atual
		result.include("paginaAtual", pagina == null ? pagina = 1 : pagina);

		// define atributo para a jsp
		// Pesquisa
		result.include("pesquisa", busca);

		// retorna uma lista de vendedores
		return vendedorApplication.listarVendedores(pagina, "BUSCA", busca);

	}// fim listar()

	// Permissão
	@BloqueiaVendedor
	// excluir vendedor efetivamente
	@Get
	@Path("vendedor/excluir")
	public void excluir(Integer idVendedor) {

		// processo de excluir
		vendedorApplication.excluirVendedor(idVendedor);

		// define atributo para a jsp
		// remoção com sucesso
		result.include("sucesso_remocao", "Vendedor removido com sucesso!");

		// redirecionando para a página listar
		result.redirectTo(VendedorController.class).listar(null);

	}// fim excluir()

	// Permissão
	@BloqueiaVendedor
	// excluir vendedor efetivamente
	@Post
	@Path("vendedor/excluir-selecionados")
	public void excluir(List<Vendedor> vendedores) {

		// processo de excluir selecionados
		try {
			vendedorApplication.excluirVendedorSelcionados(vendedores);
		} catch (ExcluirSelecInvalidoException e) {
			validator.add(new ValidationMessage(e.getMessage(), null));
		}
		// redireciona para a página listar caso houver erro na validação
		validator.onErrorUsePageOf(VendedorController.class).listar(null);

		// define atributo para a jsp
		// remoção com sucesso
		result.include("sucesso_remocao", "Vendedor removido com sucesso!");

		// redirecionando para a página listar
		result.redirectTo(VendedorController.class).listar(null);

	}// fim excluir()

	// Permisssão
	@BloqueiaVendedor
	// formulário de edita
	// método get
	@Get
	// url de caminho
	@Path("vendedor/edita")
	public void edita(Integer idVendedor) {

		// define título da página
		result.include("titulo", "Editar vendedor");

		if (idVendedor != null) {
			// carrega objeto do tipo vendedor
			Vendedor vendedor = vendedorApplication.carregaVendedor(idVendedor);

			// define atributo para a jsp
			result.include("vendedor", vendedor);

			// carrega objeto do tipo usuario
			Usuario usuario = usuarioApplication.carregaUsuario(vendedor
					.getUsuarioUsuId());

			// define atributo para a jsp
			result.include("usuario", usuario);

			// carrega objeto do tipo endereço
			Endereco endereco = enderecoApplication.carregaEndereco(vendedor
					.getEnderecoEndId().getEndId());

			// define atributo para a jsp
			result.include("endereco", endereco);
			// retorna uma lista de estados

			// define atributo para a jsp
			// inicializa os estados no select
			result.include("estados", estadoApplication.listaTodosEstados());

			// define atributo para a jsp
			// inicializa as cidades deste estado escolhido
			result.include("cidades", endereco.getCidadeCidId()
					.getEstadoEstId().getCidadeList());
		}
	}// fim edita()

	// Permissão
	@BloqueiaVendedor
	@Post
	// método post
	@Path("vendedor/editar")
	// url de caminho
	public void editando(Vendedor vendedor, Usuario usuario, Endereco endereco) {

		// define título da página
		result.include("titulo", "Editar vendedor");

		// define atributo para a jsp
		result.include("vendedor", vendedor);

		// define atributo para a jsp
		result.include("usuario", usuario);

		// define atributo para a jsp
		result.include("endereco", endereco);

		// carrega objeto do tipo endereço
		Endereco objEndereco = enderecoApplication.carregaEndereco(endereco
				.getEndId());

		// define atributo para a jsp
		// inicializa os estados no select
		result.include("estados", estadoApplication.listaTodosEstados());

		// inicializa as cidades deste estado escolhido
		// define atributo para a jsp
		result.include("cidades", objEndereco.getCidadeCidId().getEstadoEstId()
				.getCidadeList());

		// define atributo para a jsp
		result.include("estadoEscolhido", objEndereco.getCidadeCidId()
				.getEstadoEstId().getEstId());

		// processo editar
		try {
			vendedorApplication.atualizaVendedor(vendedor, usuario, endereco);
		} catch (CpfInvalidoException e) {
			validator.add(new ValidationMessage(e.getMessage(), null));

		} catch (UsuarioInvalidoException e) {
			validator.add(new ValidationMessage(e.getMessage(), null));
		}

		// redireciona para a página edita() caso houver erro na validação
		validator.onErrorUsePageOf(VendedorController.class).edita(null);

		// carrega objeto do tipo endereço
		objEndereco = enderecoApplication.carregaEndereco(endereco.getEndId());

		// define atributo para a jsp
		result.include("endereco", objEndereco);

		// define atributo para a jsp
		// inicializa os estados no select
		result.include("estados", estadoApplication.listaTodosEstados());

		// inicializa as cidades deste estado escolhido
		// define atributo para a jsp
		result.include("cidades", objEndereco.getCidadeCidId().getEstadoEstId()
				.getCidadeList());

		// define atributo para a jsp
		result.include("estadoEscolhido", objEndereco.getCidadeCidId()
				.getEstadoEstId().getEstId());

		// define um atributo de atualizado com sucesso para a jsp
		result.include("sucesso_editado", "Vendedor " + vendedor.getVenNome()
				+ " editado com sucesso!");

		// redirecionando para a edita
		result.redirectTo(VendedorController.class).edita(vendedor.getVenId());

	}// fim editando()

	// Permisssão
	@BloqueiaVendedor
	// formulário de visualizar
	// método get
	@Get
	// url de caminho
	@Path("vendedor/visualizar")
	public void visualizar(Integer idVendedor) {

		// define título da página
		result.include("titulo", "Visualizar vendedor");

		// carrega objeto do tipo vendedor
		Vendedor vendedor = vendedorApplication.carregaVendedor(idVendedor);

		// define atributo para a jsp
		result.include("vendedor", vendedor);

		// carrega objeto do tipo usuario
		Usuario usuario = usuarioApplication.carregaUsuario(vendedor
				.getUsuarioUsuId());

		// define atributo para a jsp
		result.include("usuario", usuario);

		// carrega objeto do tipo endereço
		Endereco endereco = enderecoApplication.carregaEndereco(vendedor
				.getEnderecoEndId().getEndId());

		// define atributo para a jsp
		result.include("endereco", endereco);
		// retorna uma lista de estados

		// define atributo para a jsp
		// inicializa os estados no select
		result.include("estados", estadoApplication.listaTodosEstados());

		// define atributo para a jsp
		// inicializa as cidades deste estado escolhido
		result.include("cidades", endereco.getCidadeCidId().getEstadoEstId()
				.getCidadeList());

	}// fim visualizar()

	// Permissão
	@Liberado
	@Get
	// método get
	@Path("/vendedor/busca.json")
	// url de caminho
	// lógica de negócio
	public void buscarVendedores(String term) throws IOException {

		// recebe uma lista de objetos e retorna uma lista no formato json
		result.use(Results.json()).withoutRoot()
				.from(vendedorApplication.buscarVendedores(term))
				.exclude("venCpf", "venRg", "usuarioUsuId").serialize();

	}// fim buscarProdutos()

}// fim class VendedorController
