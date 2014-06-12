<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!-- Utilizando taglib core c do JSTL -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Utilizando tag jsp "include" - p/a incluir a p�gina topo.jsp dentro desta p�gina -->
<jsp:include flush="true" page="../partes/topo.jsp"></jsp:include>
<div id="search">
	<form
		action="http://localhost:8080/Sistema_Floricultura/produto/lista-buscar"
		method="post">
		<input required="required" type="text" name="busca"
			placeholder="Pesquisar produto...">

		<button type="submit" class="tip-left" data-original-title="Search">
			<i class="icon-search icon-white"></i>
		</button>
	</form>
</div>
<!-- In�cio conte�do principal -->
<div id="content">

	<div id="content-header">
		<div id="breadcrumb">
			<a href="<c:url value="/"/>" class="tip-bottom"><i
				class="icon-home"></i> In�cio</a> <a
				href="<c:url value="/produto/listar"/>" class="current">${titulo}</a>
		</div>
		<h1>${titulo}</h1>
	</div>

	<!-- Alerta de sucesso na remo��o -->
	<c:if test="${sucesso_remocao != null}">
		<div class="alert alert-success alert-block">
			<a class="close" data-dismiss="alert" href="#">�</a>
			<h4 class="alert-heading">
				Sucesso! <a class="btn btn-success btn-mini"
					href="<c:url value="/produto/novo"/>"> Adicionar produto</a>
			</h4>
			${sucesso_remocao}
		</div>
	</c:if>
	<!-- Fim sucesso no cadastro  -->

	<!-- Alerta de sucesso na pesquisa -->
	<c:if test="${pesquisa != null }">
		<div class="alert alert-info alert-block">
			<a class="close" data-dismiss="alert" href="#">�</a>
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
							<span class="label label-inverse">TOTAL (${totalProdutos})
								PRODUTO(S)</span>
						</div>
						<div class="widget-content">
							<table class="table table-bordered table-striped with-check">
								<thead>
									<tr>
										<th><div align="center">
												<input style="margin-bottom: 30%" onclick="check();"
													type="checkbox" id="title-table-checkbox"
													name="title-table-checkbox" />
											</div></th>
										<th>ID</th>
										<th>NOME</th>
										<th>PRE�O</th>
										<th>QUANTIDADE</th>
										<th>A��ES</th>

									</tr>
								</thead>
								<tbody>
									<!-- In�cio do loop -->
									<c:forEach items="${produtosList}" var="produto">
										<!-- In�cio do lopp -->
										<tr class="gradeX">
											<td><div align="center">
													<input class="check" name="produtos[].proId"
														value="${produto.proId}" type="checkbox" />
												</div></td>
											<td><div align="center">${produto.proId}</div></td>
											<td><div align="center">${produto.proNome}</div></td>
											<td><div align="center">${produto.proPreco}</div></td>
											<td><div align="center">${produto.proQuantidade}</div></td>
											<td><div align="center">
													<a class="btn btn-mini"
														href="<c:url value="/produto/visualizar?idProduto=${produto.proId}"/>">Visualizar</a>
													<a class="btn btn-primary btn-mini"
														href="<c:url value="/produto/novo"/>">Novo</a> <a
														style="margin-right: 1%" class="btn btn-danger btn-mini"
														href="<c:url value="/produto/excluir?idProduto=${produto.proId}"/>">
														Excluir</a><a class="btn btn-warning btn-mini"
														href="<c:url value="/produto/edita?idProduto=${produto.proId}"/>">Editar</a>

												</div></td>
										</tr>
									</c:forEach>
									<!-- Fim do lopp -->
									<c:if test="${totalProdutos <= 0 }">
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
				<!-- se pagina��o for maior que 1 mostra pagina��o -->
				<c:if test="${numeroDePaginacao >1 }">
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
<!-- Fim conte�do principal -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-1.3.2.js"></script>
<!-- Utilizando tag jsp "include" - p/a incluir a p�gina rodape.jsp dentro desta p�gina -->
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
		  }else{ // se n�o estiver selecionado
		      $('.check').attr('checked', false);} // desmarca a classe `check`
		}
</script>