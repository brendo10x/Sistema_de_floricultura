<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!-- Utilizando taglib core c do JSTL -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Utilizando tag jsp "include" - p/a incluir a página topo.jsp dentro desta página -->
<jsp:include flush="true" page="../partes/topo.jsp"></jsp:include>
<div id="search">
	<form
		action="<c:url value="http://localhost:8080/Sistema_Floricultura/cliente/lista-buscar"/>"
		method="post">
		<input autocomplete="off" required="required" id="b" type="text"
			name="busca" placeholder="Pesquisar cliente..."> <input
			type="hidden" name="condicao" value="true"
			placeholder="Pesquisar cliente...">
		<button type="submit" class="tip-left">
			<i class="icon-search icon-white"></i>
		</button>
	</form>
</div>


<!-- Início conteúdo principal -->
<div id="content">

	<div id="content-header">
		<div id="breadcrumb">
			<a href="<c:url value="/"/>" class="tip-bottom"><i
				class="icon-home"></i> Início</a> <a
				href="<c:url value="/cliente/listar"/>" class="current">${titulo}</a>
		</div>
		<h1>${titulo}</h1>
	</div>

	<!-- Alerta de sucesso na remoção -->
	<c:if test="${sucesso_remocao != null}">
		<div class="alert alert-success alert-block">
			<a class="close" data-dismiss="alert" href="#">×</a>
			<h4 class="alert-heading">
				Sucesso! <a class="btn btn-success btn-mini"
					href="<c:url value="/cliente/novo"/>"> Adicionar cliente</a>
			</h4>
			${sucesso_remocao}
		</div>
	</c:if>
	<!-- Fim sucesso no cadastro  -->

	<!-- Alerta de sucesso na pesquisa -->
	<c:if test="${pesquisa != null }">
		<div class="alert alert-info alert-block">
			<a class="close" data-dismiss="alert" href="#">×</a>
			<h4 class="alert-heading">Pesquisa!</h4>
			Resultado(s) da sua pesquisa por :'${pesquisa }'
		</div>
	</c:if>
	<!-- Alerta de sucesso na pesquisa -->

	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<form action="excluir-selecionados" method="post">
					<div class="widget-box">
						<div class="widget-title">
							<span class="icon"> <i class="icon-th"></i>
							</span>
							<h5>TABELA DE DADOS</h5>
							<span class="label label-inverse">TOTAL (${totalClientes})
								CLIENTE(S)</span>
						</div>
						<div class="widget-content">
							<table class="table table-bordered table-striped with-check">
								<thead>

									<tr>
										<c:if test="${usuarioWeb.tipo == 'PROPRIETARIO' }">
											<th><div align="center">
													<input style="margin-bottom: 30%" onclick="check();"
														type="checkbox" id="title-table-checkbox"
														name="title-table-checkbox" />
												</div></th>
										</c:if>
										<th>ID</th>
										<th>NOME</th>
										<th>E-MAIL</th>
										<th>ESTADO</th>
										<th>CIDADE</th>
										<th>AÇÕES</th>

									</tr>
								</thead>
								<tbody>
									<!-- Início do loop -->
									<c:forEach items="${clienteList}" var="cliente">
										<!-- Início do lopp -->
										<tr class="gradeX">
											<c:if test="${usuarioWeb.tipo == 'PROPRIETARIO' }">
												<td><div align="center">
														<input class="check" name="clientes[].cliId"
															value="${cliente.cliId}" type="checkbox" />
													</div></td>
											</c:if>
											<td><div align="center">${cliente.cliId}</div></td>
											<td><div align="center">${cliente.cliNome}</div></td>
											<td><div align="center">${cliente.cliEmail}</div></td>
											<td><div align="center">${cliente.enderecoEndId.cidadeCidId.estadoEstId.estDescricao}</div></td>
											<td><div align="center">${cliente.enderecoEndId.cidadeCidId.cidNome}</div></td>
											<td><div align="center">
													<c:if test="${usuarioWeb.tipo == 'VENDEDOR' }">
														<a class="btn btn-mini"
															href="<c:url value="/cliente/visualizar?idCliente=${cliente.cliId}"/>">Visualizar</a>

														<a class="btn btn-primary btn-mini"
															href="<c:url value="/cliente/novo"/>">Novo</a>
													</c:if>
													<c:if test="${usuarioWeb.tipo == 'PROPRIETARIO' }">
														<a style="margin-right: 0.50%"
															class="btn btn-danger btn-mini"
															href="<c:url value="/cliente/excluir?idCliente=${cliente.cliId}"/>">
															Excluir</a>
													</c:if>
													<c:if test="${usuarioWeb.tipo == 'VENDEDOR' }">
														<a style="margin-right: 0.50%"
															class="btn btn-warning btn-mini"
															href="<c:url value="/cliente/edita?idCliente=${cliente.cliId}"/>">Editar</a>
													</c:if>
												</div></td>
										</tr>
									</c:forEach>
									<!-- Fim do lopp -->
									<c:if test="${totalClientes <= 0 }">
										<tr>
											<td colspan="7"><div align="center">NENHUM
													REGISTRO ENCONTRADO!</div></td>
										</tr>
									</c:if>
								</tbody>
							</table>
							<c:if test="${usuarioWeb.tipo == 'PROPRIETARIO' }">
								<c:if test="${numeroDePaginacao >0  }">
									<button class="btn btn-danger btn-mini">EXCLUIR
										SELECIONADOS</button>

								</c:if>
							</c:if>
						</div>
					</div>
				</form>
				<!-- se paginação for maior que 1 mostra paginação -->
				<c:if test="${numeroDePaginacao >1  && numeroDePaginacao != null}">
					<div>
						<div class="pagination alternate">

							<div style="float: left" id="corpoPaginacao"></div>

						</div>

					</div>
				</c:if>
			</div>
		</div>
	</div>
</div>
<!-- Fim conteúdo principal -->

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-1.3.2.js"></script>
<!-- Utilizando tag jsp "include" - p/a incluir a página rodape.jsp dentro desta página -->
<jsp:include page="../partes/rodape.jsp"></jsp:include>


<script type="text/javascript">

	$("#corpoPaginacao").paginate({
    	 count : ${numeroDePaginacao},
		start :  ${paginaAtual},
		display : 4,
		busca: true,
		pesquisa: '${pesquisa}'
		
	
	});
	function check(){
		  if($('#title-table-checkbox').attr('checked') == true){ // se o checkbox estiver selecionado quando clicado
		      $('.check').attr('checked', true); // seleciona toda a classe `check`
		  }else{ // se não estiver selecionado
		      $('.check').attr('checked', false);} // desmarca a classe `check`
		}
	
</script>