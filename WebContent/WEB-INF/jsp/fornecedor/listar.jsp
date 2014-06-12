<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!-- Utilizando taglib core c do JSTL -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Utilizando tag jsp "include" - p/a incluir a página topo.jsp dentro desta página -->
<jsp:include flush="true" page="../partes/topo.jsp"></jsp:include>
<div id="search">
	<form action="lista-buscar" method="post">
		<input required="required" type="text" name="busca"
			placeholder="Pesquisar fornecedor...">

		<button type="submit" class="tip-left" data-original-title="Search">
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
				href="<c:url value="/fornecedor/listar"/>" class="current">${titulo}</a>
		</div>
		<h1>${titulo}</h1>
	</div>

	<!-- Alerta de sucesso na remoção -->
	<c:if test="${sucesso_remocao != null}">
		<div class="alert alert-success alert-block">
			<a class="close" data-dismiss="alert" href="#">×</a>
			<h4 class="alert-heading">
				Sucesso! <a class="btn btn-success btn-mini"
					href="<c:url value="/fornecedor/novo"/>"> Adicionar fornecedor</a>
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


	<!-- Alerta de erro  -->
	<c:if test="${errors != null}">
		<div class="alert alert-error alert-block">
			<a class="close" data-dismiss="alert" href="#">×</a>
			<h4 class="alert-heading">Error!</h4>
			<ul>
				<c:forEach items="${errors}" var="error">
					<li>${error.message}  <a class="btn btn-success btn-mini"
						href="<c:url value="/fornecedor/listar"/>"> Listar
							fornecedores</a></li>
				</c:forEach>
			</ul>
		</div>
	</c:if>
	<!-- Fim alerta de erro -->

	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<form action="excluir-selecionados" method="post">
					<div class="widget-box">
						<div class="widget-title">
							<span class="icon"> <i class="icon-th"></i>
							</span>
							<h5>TABELA DE DADOS</h5>
							<span class="label label-inverse">TOTAL
								(${totalFornecedores}) FORNECEDOR(ES)</span>
						</div>
						<div class="widget-content">
							<table class="table table-bordered table-striped with-check">
								<thead>
									<tr>
										<th><div align="center">
												<input  style="margin-bottom: 30%"  onclick="check();" type="checkbox"
													id="title-table-checkbox" name="title-table-checkbox" />
											</div></th>
										<th>ID</th>
										<th>NOME</th>
										<th>ESTADO</th>
										<th>CIDADE</th>
										<th>AÇÕES</th>

									</tr>
								</thead>
								<tbody>
									<!-- Início do loop -->
									<c:forEach items="${fornecedorList}" var="fornecedor">
										<!-- Início do lopp -->
										<tr class="gradeX">
											<td><div align="center">
													<input class="check" name="fornecedores[].forId"
														value="${fornecedor.forId}" type="checkbox" />
												</div></td>
											<td><div align="center">${fornecedor.forId}</div></td>
											<td><div align="center">${fornecedor.forNome}</div></td>
											<td><div align="center">${fornecedor.enderecoEndId.cidadeCidId.estadoEstId.estDescricao}</div></td>
											<td><div align="center">${fornecedor.enderecoEndId.cidadeCidId.cidNome}</div></td>
											<td><div align="center">

													<a class="btn btn-mini" style="margin-right: 0.2%"
														href="<c:url value="/fornecedor/visualizar?idFornecedor=${fornecedor.forId}"/>">Visualizar</a>
													<a class="btn btn-primary btn-mini"
														style="margin-right: 1%"
														href="<c:url value="/fornecedor/novo"/>">Novo</a><a
														style="margin-right: 1%" class="btn btn-danger btn-mini"
														href="<c:url value="/fornecedor/excluir?idFornecedor=${fornecedor.forId}"/>">
														Excluir</a><a style="margin-right: 1%"
														class="btn btn-warning btn-mini"
														href="<c:url value="/fornecedor/edita?idFornecedor=${fornecedor.forId}"/>">Editar</a>

												</div></td>
										</tr>
									</c:forEach>
									<!-- Fim do lopp -->

									<c:if test="${totalFornecedores <= 0 }">
										<tr>
											<td colspan="6"><div align="center">NENHUM
													REGISTRO ENCONTRADO!</div></td>
										</tr>
									</c:if>
								</tbody>

							</table>

							<c:if test="${numeroDePaginacao >0  }">
								<button class="btn btn-danger btn-mini">EXCLUIR
									SELECIONADOS</button>

							</c:if>

						</div>
					</div>

				</form>
				<!-- fim formulário -->
				<!-- se paginação for maior que 1 mostra paginação -->
				<c:if test="${numeroDePaginacao >1 }">

					<div class="pagination alternate">

						<div id="corpoPaginacao1"></div>

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

	$("#corpoPaginacao1").paginate({
        count : ${numeroDePaginacao},
		start : ${paginaAtual},
		display : 4
		
		
	});
	
	function check(){
		  if($('#title-table-checkbox').attr('checked') == true){ // se o checkbox estiver selecionado quando clicado
		      $('.check').attr('checked', true); // seleciona toda a classe `check`
		  }else{ // se não estiver selecionado
		      $('.check').attr('checked', false);} // desmarca a classe `check`
		}
</script>
