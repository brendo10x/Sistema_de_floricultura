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
import br.com.sis.floricultura.application.ClienteApplication;
import br.com.sis.floricultura.application.EnderecoApplication;
import br.com.sis.floricultura.application.EstadoApplication;
import br.com.sis.floricultura.dao.ClienteDao;
import br.com.sis.floricultura.dao.EnderecoDao;
import br.com.sis.floricultura.dao.EstadoDao;
import br.com.sis.floricultura.exception.EmailInvalidoException;
import br.com.sis.floricultura.exception.ExcluirSelecInvalidoException;
import br.com.sis.floricultura.exception.RgInvalidoException;
import br.com.sis.floricultura.intercepts.Liberado;
import br.com.sis.floricultura.intercepts.BloqueiaProprietario;
import br.com.sis.floricultura.intercepts.BloqueiaVendedor;
import br.com.sis.floricultura.model.Cliente;
import br.com.sis.floricultura.model.Endereco;

/*Classe controladora ClienteController cont�m 
 as regras de neg�cio, com anota��o @Resource para o
 Vraptor saber que essa classe � um controller*/

@Resource
public class ClienteController {

	private Result result;// mostra resultados
	private Validator validator; // valida��o
	private ClienteApplication clienteApplication;
	private EstadoApplication estadoApplication;
	private EnderecoApplication enderecoApplication;

	// contrutor padr�o inicializa as depend�ncias que seram usadas
	public ClienteController(Result result, Validator validator,
			ClienteApplication clienteApplication,
			EstadoApplication estadoApplication,
			EnderecoApplication enderecoApplication) {

		this.result = result;
		this.validator = validator;
		this.clienteApplication = clienteApplication;
		this.estadoApplication = estadoApplication;
		this.enderecoApplication = enderecoApplication;

	}// fim contrutor padr�o

	// Acesso
	@BloqueiaProprietario
	// redireciona para a p�gina de formul�rio
	@Get
	// m�todo get
	@Path("cliente/novo")
	// url de caminho
	// l�gica de neg�cio
	public void novo() {

		// define t�tulo da p�gina
		result.include("titulo", "Adicionar cliente");

		// define atributo para a jsp
		// retorna uma lista de estados
		result.include("estados", estadoApplication.listaTodosEstados());

	}// fim novo()

	// Acesso
	@BloqueiaProprietario
	// a��o de adicionar efitivamente
	@Post
	// m�todo post
	@Path("cliente/adicionar")
	// url de caminho
	public void adicionar(Cliente cliente, Endereco endereco) {

		// define t�tulo da p�gina
		result.include("titulo", "Adicionar cliente");

		// define atributo para a jsp
		// lista de estado
		result.include("estados", estadoApplication.listaTodosEstados());

		try {
			// processo salvar cliente
			clienteApplication.salvarCliente(cliente, endereco);
		} catch (EmailInvalidoException e) {
			validator.add(new ValidationMessage(e.getMessage(), null));
		} catch (RgInvalidoException e) {
			validator.add(new ValidationMessage(e.getMessage(), null));
		}

		// redireciona para a p�gina novo() caso houver erro na valida��o
		validator.onErrorUsePageOf(ClienteController.class).novo();

		// define atributo para a jsp
		// adicionado com sucesso
		result.include("sucesso_adicionado", "Cliente " + cliente.getCliNome()
				+ " adicionado com sucesso!");

		// redirecionando para a p�gina novo()
		result.redirectTo(ClienteController.class).novo();

	}// fim adicionar()

	// Acesso
	@Liberado
	// p�gina de listar cliente
	@Get
	@Path("cliente/listar")
	public List<Cliente> listar(Integer pagina) {

		// define t�tulo da p�gina
		result.include("titulo", "Listar clientes");

		// define atributo para a jsp
		// P�gina atual
		result.include("paginaAtual", pagina == null ? pagina = 1 : pagina);

		// total de registros
		result.include("totalClientes",
				clienteApplication.numeroTotalClientes());

		// define atributo para a jsp
		// N�mero de pagina��es
		result.include("numeroDePaginacao",
				clienteApplication.numeroDePaginacao("NORMAL", ""));

		// retorna uma lista de clientes
		return clienteApplication.listarClientes(pagina, "NORMAL", "");

	}// fim listar()

	// Acesso
	@Liberado
	// p�gina de listar cliente pesquisados
	@Post
	@Get
	@Path({ "cliente/lista-buscar", "cliente/lista-buscar/{busca}" })
	public List<Cliente> buscar(Integer pagina, String busca) {

		// define t�tulo da p�gina
		result.include("titulo", "Listar clientes");

		// define atributo para a jsp
		// N�mero de pagina��es
		result.include("numeroDePaginacao",
				clienteApplication.numeroDePaginacao("BUSCA", busca));

		// total de registros
		result.include("totalClientes",
				clienteApplication.numeroRegistroFornecedorBuscar(busca));

		// define atributo para a jsp
		// P�gina atual
		result.include("paginaAtual", pagina == null ? pagina = 1 : pagina);

		// define atributo para a jsp
		// Pesquisa
		result.include("pesquisa", busca);

		// retorna uma lista de clientes
		return clienteApplication.listarClientes(pagina, "BUSCA", busca);

	}// fim listar()

	// Acesso
	@BloqueiaVendedor
	// excluir cliente efetivamente
	@Get
	@Path("cliente/excluir")
	public void excluir(Integer idCliente) {

		// processo excluir
		clienteApplication.excluirCliente(idCliente);

		// define atributo para a jsp
		// remo��o com sucesso
		result.include("sucesso_remocao", "Cliente removido com sucesso!");

		// redirecionando para a p�gina listar
		result.redirectTo(ClienteController.class).listar(null);

	}// fim excluir()

	// Acesso
	@BloqueiaVendedor
	// excluir cliente efetivamente
	@Post
	@Path("cliente/excluir-selecionados")
	public void excluirClientesSelecionados(List<Cliente> clientes) {

		// processo excluir
		try {
			clienteApplication.excluirClienteSelecionados(clientes);
		} catch (ExcluirSelecInvalidoException e) {
			validator.add(new ValidationMessage(e.getMessage(), null));
		}
		// redireciona para a p�gina listar caso houver erro na valida��o
		validator.onErrorUsePageOf(ClienteController.class).listar(null);

		// define atributo para a jsp
		// remo��o com sucesso
		result.include("sucesso_remocao", "Cliente removido com sucesso!");

		// redirecionando para a p�gina listar
		result.redirectTo(ClienteController.class).listar(null);

	}// fim excluirClientesSelecionados()

	// Acesso
	@BloqueiaProprietario
	// formul�rio de edita
	// m�todo get
	@Get
	// url de caminho
	@Path("cliente/edita")
	public void edita(Integer idCliente) {

		// define t�tulo da p�gina
		result.include("titulo", "Editar cliente");

		if (idCliente != null) {

			// carrega objeto do tipo cliente
			Cliente cliente = clienteApplication.carregaCliente(idCliente);

			// define atributo para a jsp
			result.include("cliente", cliente);

			// carrega objeto do tipo endere�o
			Endereco endereco = enderecoApplication.carregaEndereco(cliente
					.getEnderecoEndId().getEndId());

			// define atributo para a jsp
			result.include("endereco", endereco);

			// define atributo para a jsp
			// inicializa os estados no select
			result.include("estados", estadoApplication.listaTodosEstados());

			// define atributo para a jsp
			// inicializa as cidades deste estado escolhido
			result.include("cidades", endereco.getCidadeCidId()
					.getEstadoEstId().getCidadeList());
		}
	}// fim edita()

	// Acesso
	@BloqueiaProprietario
	// a��o de editar efetivamente
	@Post
	// m�todo post
	@Path("cliente/editar")
	// url de caminho
	public void editando(Cliente cliente, Endereco endereco) {

		// define t�tulo da p�gina
		result.include("titulo", "Editar cliente");

		try {
			// processo atualizar
			clienteApplication.atualizarCliente(cliente, endereco);
		} catch (EmailInvalidoException e) {
			validator.add(new ValidationMessage(e.getMessage(), null));
		} catch (RgInvalidoException e) {
			validator.add(new ValidationMessage(e.getMessage(), null));
		}

		// redireciona para a p�gina edita() caso houver erro na valida��o
		validator.onErrorUsePageOf(ClienteController.class).edita(null);

		// carrega objeto do tipo endere�o
		Endereco objEndereco = enderecoApplication.carregaEndereco(endereco
				.getEndId());

		// define atributo para a jsp
		result.include("endereco", objEndereco);

		// define atributo para a jsp
		// inicializa os estados no select
		result.include("estados", estadoApplication.listaTodosEstados());

		result.include("estadoEscolhido", objEndereco.getCidadeCidId()
				.getEstadoEstId().getEstId());
		// define atributo para a jsp
		// inicializa as cidades deste estado escolhido
		result.include("cidades", objEndereco.getCidadeCidId().getEstadoEstId()
				.getCidadeList());

		// define atributo para a jsp
		result.include("cliente", cliente);

		// define um atributo de atualizado com sucesso para a jsp
		result.include("sucesso_editado", "Cliente " + cliente.getCliNome()
				+ " editado com sucesso!");

		// redirecionando para a edita
		result.redirectTo(ClienteController.class).edita(cliente.getCliId());

	}// fim editando()

	// Acesso
	@BloqueiaProprietario
	// formul�rio de edita
	// m�todo get
	@Get
	// url de caminho
	@Path("cliente/visualizar")
	public void visualizar(Integer idCliente) {

		// define t�tulo da p�gina
		result.include("titulo", "Visualizar cliente");

		// carrega objeto do tipo cliente
		Cliente cliente = clienteApplication.carregaCliente(idCliente);

		// define atributo para a jsp
		result.include("cliente", cliente);

		// carrega objeto do tipo endere�o
		Endereco endereco = enderecoApplication.carregaEndereco(cliente
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

	}// fim visualizar()

	// Acesso
	@Liberado
	@Get
	// m�todo get
	@Path("/cliente/busca.json")
	// url de caminho
	// l�gica de neg�cio
	public void buscarClientes(String term) throws IOException {

		// recebe uma lista de objetos e retorna uma lista no formato json
		result.use(Results.json())
				.withoutRoot()
				.from(clienteApplication.buscarClientes(term))
				.exclude("cliRg", "cliEmail", "cliTelefoneFixo",
						"cliTelefoneMovel").serialize();

	}// fim buscarClientes()

}// fim class ClienteController
