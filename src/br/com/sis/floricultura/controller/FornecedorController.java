//pacote
package br.com.sis.floricultura.controller;

//import's
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

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
import br.com.sis.floricultura.application.FornecedorApplication;
import br.com.sis.floricultura.dao.EnderecoDao;
import br.com.sis.floricultura.dao.EstadoDao;
import br.com.sis.floricultura.dao.FornecedorDao;
import br.com.sis.floricultura.exception.ExcluirSelecInvalidoException;
import br.com.sis.floricultura.intercepts.BloqueiaVendedor;
import br.com.sis.floricultura.intercepts.Liberado;
import br.com.sis.floricultura.model.Endereco;
import br.com.sis.floricultura.model.Fornecedor;

/*Classe controladora FornecedorController cont�m 
 as regras de neg�cio, com anota��o @Resource para o
 Vraptor saber que essa classe � um controller*/
@Resource
public class FornecedorController {

	private Result result;// mostra resultados
	private Validator validator; // valida��o
	private FornecedorApplication fornecedorApplication;
	private EstadoApplication estadoApplication;
	private EnderecoApplication enderecoApplication;

	// contrutor padr�o inicializa as depend�ncias que seram usadas
	public FornecedorController(Result result, Validator validator,
			FornecedorApplication fornecedorApplication,
			EstadoApplication estadoApplication,
			EnderecoApplication enderecoApplication) {

		this.validator = validator;
		this.result = result;
		this.fornecedorApplication = fornecedorApplication;
		this.estadoApplication = estadoApplication;
		this.enderecoApplication = enderecoApplication;

	}// fim do contrutor

	// Acesso
	@BloqueiaVendedor
	// redireciona para a p�gina de formul�rio
	@Get
	// m�todo get
	@Path("fornecedor/novo")
	// url de caminho
	// l�gica de neg�cio
	public void novo() {

		// define t�tulo da p�gina
		result.include("titulo", "Adicionar Fornecedor");

		// define atributo para a jsp
		// retorna uma lista de estados
		result.include("estados", estadoApplication.listaTodosEstados());

	}// fim novo()

	// Acesso
	@BloqueiaVendedor
	// a��o de adicionar efitivamente
	@Post
	// m�todo post
	@Path("fornecedor/adicionar")
	// url de caminho
	public void adicionar(Fornecedor fornecedor, Endereco endereco) {

		// define t�tulo da p�gina
		result.include("titulo", "Adicionar Fornecedor");

		// processo salvar
		fornecedorApplication.adicionarFornecedor(fornecedor, endereco);

		// define atributo para a jsp
		// adicionado com sucesso
		result.include("sucesso_adicionado",
				"Fornecedor " + fornecedor.getForNome()
						+ " adicionado com sucesso!");

		// redirecionando para a p�gina novo()
		result.redirectTo(FornecedorController.class).novo();

	}// fim adicionar()

	// Acesso
	@BloqueiaVendedor
	// p�gina de listar fornecedores
	@Get
	@Path("fornecedor/listar")
	public List<Fornecedor> listar(Integer pagina) {

		// define t�tulo da p�gina
		result.include("titulo", "Listar fornecedores");

		// total de registros
		result.include("totalFornecedores",
				fornecedorApplication.numeroTotalFornecedores());

		// define atributo para a jsp
		// N�mero de pagina��es
		result.include("numeroDePaginacao",
				fornecedorApplication.numeroDePaginacao("NORMAL", null));

		// define atributo para a jsp
		// P�gina atual
		result.include("paginaAtual", pagina == null ? pagina = 1 : pagina);

		// retorna uma lista de fornecedores
		return fornecedorApplication.listarFornecedores(pagina, "NORMAL", null);

	}// fim listar()

	// Acesso
	@BloqueiaVendedor
	// p�gina de listar fornecedores pesquisados
	@Post
	@Get
	@Path({ "fornecedor/lista-buscar", "fornecedor/lista-buscar/{busca}" })
	public List<Fornecedor> buscar(Integer pagina, String busca) {

		// define t�tulo da p�gina
		result.include("titulo", "Listar fornecedores");

		// total de registros
		result.include("totalFornecedores",
				fornecedorApplication.numeroRegistroFornecedorBuscar(busca));

		// define atributo para a jsp
		// N�mero de pagina��es
		result.include("numeroDePaginacao",
				fornecedorApplication.numeroDePaginacao("BUSCA", busca));

		// define atributo para a jsp
		// P�gina atual
		result.include("paginaAtual", pagina == null ? pagina = 1 : pagina);

		// define atributo para a jsp
		// Pesquisa
		result.include("pesquisa", busca);

		// retorna uma lista de fornecedores
		return fornecedorApplication.listarFornecedores(pagina, "BUSCA", busca);
	}// fim buscar()

	// Acesso
	@BloqueiaVendedor
	// excluir fornecedor efetivamente
	@Get
	@Path("fornecedor/excluir")
	public void excluir(Integer idFornecedor) {

		// processo remover
		fornecedorApplication.excluirFornecedor(idFornecedor);

		// define atributo para a jsp
		// remo��o com sucesso
		result.include("sucesso_remocao", "Fornecedor removido com sucesso!");

		// redirecionando para a p�gina listar
		result.redirectTo(FornecedorController.class).listar(null);

	}// fim excluir()

	// Acesso
	@BloqueiaVendedor
	// excluir fornecedor efetivamente
	@Post
	@Get
	@Path("fornecedor/excluir-selecionados")
	public void excluirSelecionados(List<Fornecedor> fornecedores) {

		// processo remover
		try {
			fornecedorApplication.excluirFornecedorSelecionados(fornecedores);
		} catch (ExcluirSelecInvalidoException e) {
			
			validator.add(new ValidationMessage(e.getMessage(), null));
		}
		// redireciona para a p�gina edita() caso houver erro na valida��o
		validator.onErrorUsePageOf(FornecedorController.class).listar(null);

		// define atributo para a jsp
		// remo��o com sucesso
		result.include("sucesso_remocao",
				"Selecionado(s) removido(s) com sucesso!");

		// redirecionando para a p�gina listar
		result.redirectTo(FornecedorController.class).listar(1);

	}// fim excluir()

	// Acesso
	@BloqueiaVendedor
	// formul�rio de edita
	// m�todo get
	@Get
	// url de caminho
	@Path("fornecedor/edita")
	public void edita(Integer idFornecedor) {

		// define t�tulo da p�gina
		result.include("titulo", "Editar fornecedor");

		// carrega objeto do tipo fornecedor
		Fornecedor fornecedor = fornecedorApplication
				.carregaFornecedor(idFornecedor);

		// define atributo para a jsp
		result.include("fornecedor", fornecedor);

		// carrega objeto do tipo endere�o
		Endereco endereco = enderecoApplication.carregaEndereco(fornecedor
				.getEnderecoEndId().getEndId());

		// define atributo para a jsp
		result.include("endereco", endereco);

		// define atributo para a jsp
		// inicializa os estados no select
		result.include("estados", estadoApplication.listaTodosEstados());

		// define atributo para a jsp
		// inicializa as cidades deste estado escolhido
		result.include("cidades", endereco.getCidadeCidId().getEstadoEstId()
				.getCidadeList());

		// esse resulta vai ser recuperar na p�gina que o m�todo invoca

	}// fim edita()

	// Acesso
	@BloqueiaVendedor
	// a��o de editar efetivamente
	@Post
	// m�todo post
	@Path("fornecedor/editar")
	// url de caminho
	public void editando(Fornecedor fornecedor, Endereco endereco) {

		// define t�tulo da p�gina
		result.include("titulo", "Editar fornecedor");

		// processo atualiza
		fornecedorApplication.atualizaFornecedor(fornecedor, endereco);

		// define um atributo de atualizado com sucesso para a jsp
		result.include("sucesso_editado",
				"Fornecedor " + fornecedor.getForNome()
						+ " editado com sucesso!");

		// redirecionando para a edita
		result.redirectTo(FornecedorController.class).edita(
				fornecedor.getForId());

	}// fim editando()

	// Acesso
	@BloqueiaVendedor
	// formul�rio de visualizar
	// m�todo get
	@Get
	// url de caminho
	@Path("fornecedor/visualizar")
	public void visualizar(Integer idFornecedor) {

		// define t�tulo da p�gina
		result.include("titulo", "Visualizar fornecedor");

		// carrega objeto do tipo fornecedor
		Fornecedor fornecedor = fornecedorApplication
				.carregaFornecedor(idFornecedor);

		// define atributo para a jsp
		result.include("fornecedor", fornecedor);

		// carrega objeto do tipo endere�o
		Endereco endereco = enderecoApplication.carregaEndereco(fornecedor
				.getEnderecoEndId().getEndId());

		// define atributo para a jsp
		result.include("endereco", endereco);

		// define atributo para a jsp
		// inicializa os estados no select
		result.include("estados", estadoApplication.listaTodosEstados());

		// define atributo para a jsp
		// inicializa as cidades deste estado escolhido
		result.include("cidades", endereco.getCidadeCidId().getEstadoEstId()
				.getCidadeList());

		// esse resulta vai ser recuperar na p�gina que o m�todo invoca

	}// fim edita()

	// Acesso
	@Liberado
	@Get
	// m�todo get
	@Path("/fornecedor/busca.json")
	// url de caminho
	// l�gica de neg�cio
	public void buscarfornecedores(String term) throws IOException {

		// recebe uma lista de objetos e retorna uma lista no formato json
		result.use(Results.json()).withoutRoot()
				.from(fornecedorApplication.buscarFornecedores(term))
				.serialize();

	}// fim buscarfornecedores()

}// fim class FornecedorController
