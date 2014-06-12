<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!-- Utilizando taglib core c do JSTL -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Utilizando taglib fmt fmt do JSTL para datas-->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- Utilizando tag jsp "include" - p/a incluir a página topo.jsp dentro desta página -->
<jsp:include flush="true" page="../partes/topo.jsp"></jsp:include>

<!-- obtendo o objeto data -->
<jsp:useBean id="data" class="java.util.Date" />

<div id="search">
	<form
		action="http://localhost:8080/Sistema_Floricultura/venda/lista-buscar"
		method="post">
		<input class="mascaraDataVenda" required="required" type="text"
			id="datepicker" name="busca" placeholder="Pesquisar venda..."
			title="ex: <fmt:formatDate type="time" value="${data}" pattern="yyyy-MM-dd"  />">

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
				href="<c:url value="/venda/listar"/>" class="current">${titulo}</a>
		</div>
		<h1>${titulo}</h1>
	</div>

	<!-- Alerta de sucesso na remoção -->
	<c:if test="${sucesso_remocao != null}">
		<div class="alert alert-success alert-block">
			<a class="close" data-dismiss="alert" href="#">×</a>
			<h4 class="alert-heading">
				Sucesso! <a class="btn btn-success btn-mini"
					href="<c:url value="/venda/novo"/>"> Adicionar venda</a>
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
					<li>${error.message}<a class="btn btn-success btn-mini"
						href="<c:url value="/venda/listar"/>"> Listar vendas</a></li>
				</c:forEach>
			</ul>
		</div>
	</c:if>
	<!-- Fim alerta de erro -->

	<!-- datapiker jquery -->
	<script type="text/javascript">
		$(function() {
			$("#datepicker")
					.datepicker(
							{
								changeMonth : true,
								changeYear : true,
								showButtonPanel : true,
								dateFormat : 'yy-mm-dd',
								dayNames : [ 'Domingo', 'Segunda', 'Terça',
										'Quarta', 'Quinta', 'Sexta', 'Sábado' ],
								dayNamesMin : [ 'D', 'S', 'T', 'Q', 'Q', 'S',
										'S', 'D' ],
								dayNamesShort : [ 'Dom', 'Seg', 'Ter', 'Qua',
										'Qui', 'Sex', 'Sáb', 'Dom' ],
								monthNames : [ 'Janeiro', 'Fevereiro', 'Março',
										'Abril', 'Maio', 'Junho', 'Julho',
										'Agosto', 'Setembro', 'Outubro',
										'Novembro', 'Dezembro' ],
								monthNamesShort : [ 'Jan', 'Fev', 'Mar', 'Abr',
										'Mai', 'Jun', 'Jul', 'Ago', 'Set',
										'Out', 'Nov', 'Dez' ],
								nextText : 'Próximo',
								prevText : 'Anterior',
								currentText : 'Hoje',
								closeText : 'Fechar'
							});
		});
	</script>

	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<form action="excluir-selecionados" method="post">
					<div class="widget-box">
						<div class="widget-title">
							<span class="icon"> <i class="icon-th"></i>
							</span>
							<h5>TABELA DE DADOS</h5>
							<span class="label label-inverse">TOTAL (${totalVendas})
								VENDA(S)</span>
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
										<th>VENDEDOR</th>
										<th>DATA</th>
										<th>FORMA PAGAMENTO</th>
										<th>TOTAL</th>
										<th>AÇÕES</th>

									</tr>
								</thead>
								<tbody>
									<!-- Início do loop -->
									<c:forEach items="${vendaList}" var="venda">
										<!-- Início do lopp -->
										<tr class="gradeX">
											<c:if test="${usuarioWeb.tipo == 'PROPRIETARIO' }">
												<td><div align="center">
														<input class="check" name="vendas[].venId"
															value="${venda.venId}" type="checkbox" />
													</div></td>
											</c:if>
											<td><div align="center">${venda.venId}</div></td>
											<td><div align="center">${venda.vendedorVenId.venNome}</div></td>
											<td><div align="center">${venda.venDataVenda}</div></td>
											<td><div align="center">${venda.venFormaPagamento}</div></td>
											<td><div align="center">${venda.venTotal}</div></td>

											<td><div align="center">
													<c:if test="${usuarioWeb.tipo == 'VENDEDOR' }">
														<a class="btn btn-primary btn-mini"
															href="<c:url value="/venda/novo"/>"><i
															class="icon-plus-sign"></i>Novo</a>
													</c:if>
													<c:if test="${usuarioWeb.tipo == 'PROPRIETARIO' }">
														<a style="margin-right: 0.50%%"
															class="btn btn-danger btn-mini"
															href="<c:url value="/venda/excluir?idVenda=${venda.venId}"/>"><i
															class="icon-trash"></i> Excluir</a>
													</c:if>
												</div></td>
										</tr>
									</c:forEach>
									<!-- Fim do lopp -->
									<c:if test="${totalVendas <= 0 }">
										<tr>
											<td colspan="6"><div align="center">NENHUM
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

	$(".mascaraDataVenda").mask("9999-99-99");
</script>