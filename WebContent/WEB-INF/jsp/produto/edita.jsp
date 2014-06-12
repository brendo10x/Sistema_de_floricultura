<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!-- Utilizando taglib core c do JSTL -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Utilizando tag jsp "include" - p/a incluir a p�gina topo.jsp dentro desta p�gina -->
<jsp:include flush="true" page="../partes/topo.jsp"></jsp:include>

<!-- In�cio conte�do principal -->
<div id="content">
	<div id="content-header">
		<div id="breadcrumb">
			<a href="<c:url value="/"/>" class="tip-bottom"><i
				class="icon-home"></i>In�cio</a><a href="#" class="current">${titulo}</a>
		</div>
		<h1>${titulo}</h1>
	</div>

	<!-- Alerta de erro  -->
	<c:if test="${errors != null}">
		<div class="alert alert-error alert-block">
			<a class="close" data-dismiss="alert" href="#">�</a>
			<h4 class="alert-heading">Error!</h4>
			<ul>
				<c:forEach items="${errors}" var="error">
					<li>${error.message}</li>
				</c:forEach>
			</ul>
		</div>
	</c:if>
	<!-- Fim alerta de erro -->

	<!-- Alerta de sucesso no cadastro -->
	<c:if test="${sucesso_editado != null}">
		<div class="alert alert-success alert-block">
			<a class="close" data-dismiss="alert" href="#">�</a>
			<h4 class="alert-heading">
				Sucesso! <a class="btn btn-success btn-mini"
					href="<c:url value="/produto/listar"/>"> Listar produtos</a>
			</h4>
			${sucesso_editado}
		</div>
	</c:if>
	<!-- Fim sucesso no cadastro  -->

	<form action="editar" method="post" class="form-horizontal">
		<div class="container-fluid">


			<div class="row-fluid">
				<div class="span12">
					<div class="widget-box">
						<div class="widget-title">
							<span class="icon"> <i class="icon-align-justify"></i>
							</span>
							<h5>Informa��es</h5>
						</div>
						<div class="widget-content nopadding">

							<div class="control-group">
								<label class="control-label">Nome :</label>
								<div class="controls">
									<input type="text" class="span10" name="produto.proNome"
										value="${produto.proNome }" placeholder="Ex: Flor verde" />
								</div>
							</div>

							<div class="control-group">
								<label class="control-label">Pre�o :</label>
								<div class="controls">
									<input type="text" class="span3" name="produto.proPreco"
										value="${produto.proPreco }" placeholder="Ex: 21.00" />
								</div>
							</div>

							<div class="control-group">
								<label class="control-label">Quantidade :</label>
								<div class="controls">
									<input type="number" class="span2" name="produto.proQuantidade"
										value="${produto.proQuantidade}" placeholder="Ex: 10" />
								</div>
							</div>


							<div class="control-group">
								<label class="control-label">Escolha Tipo :</label>
								<div class="controls ">
									<select name="produto.proTipo">
										<c:choose>
											<c:when test="${produto.proTipo == 'FLOR' }">
												<option selected="selected" value="0">Flor</option>
											</c:when>
											<c:otherwise>
												<option value="0">Flor</option>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${produto.proTipo == 'VASO' }">
												<option selected="selected" value="1">Vaso</option>
											</c:when>
											<c:otherwise>
												<option value="1">Vaso</option>
											</c:otherwise>
										</c:choose>

										<c:choose>
											<c:when test="${produto.proTipo == 'PLANTA' }">
												<option selected="selected" value="2">Planta</option>
											</c:when>
											<c:otherwise>
												<option value="2">Planta</option>
											</c:otherwise>
										</c:choose>

									</select>
								</div>
							</div>

							<div class="control-group">
								<label class="control-label">Escolha fornecedor :</label>
								<div class="controls">
									<input autocomplete="off" id="fornecedor"
										name="fornecedor.forNome" type="text" class="span6"
										placeholder="Ex: Zantoin"
										value="${produto.fornecedorForId.forNome }" />

								</div>
							</div>
							<script type="text/javascript">
								$(document)
										.ready(
												function() {
													$("#fornecedor")
															.autocomplete(
																	{
																		width : 300,
																		max : 10,
																		delay : 100,
																		minLength : 1,
																		source : function(
																				request,
																				response) {
																			$
																					.ajax({
																						url : '<c:url value="/fornecedor/busca.json" />',
																						dataType : "json",
																						data : request,
																						success : function(
																								data) {
																							response($
																									.map(
																											data,
																											function(
																													item) {
																												return {
																													label : item.forNome
																															+ " id = "
																															+ item.forId,
																													value : item.forNome,
																													variavel : item.forId
																												}

																											}));
																						}

																					});
																		},
																		minLength : 1,
																		select : function(
																				event,
																				ui) {

																			$(
																					"#idFornecedor")
																					.attr(
																							"value",
																							ui.item.variavel);

																		}

																	});
												});
							</script>




							<input type="hidden" class="span10" name="produto.proId"
								value="${produto.proId}" />
								
								 <input type="hidden" class="span10"
								id="idFornecedor" name="produto.fornecedorForId.forId"
								value="${produto.fornecedorForId.forId}" />


							<div class="form-actions">
								<button type="submit" class="btn btn-success">Salvar</button>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>
<!-- Fim conte�do principal -->

<!-- Utilizando tag jsp "include" - p/a incluir a p�gina rodape.jsp dentro desta p�gina -->
<jsp:include page="../partes/rodape.jsp" flush="true"></jsp:include>
