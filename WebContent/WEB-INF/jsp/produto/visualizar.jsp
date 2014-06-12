<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!-- Utilizando taglib core c do JSTL -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Utilizando tag jsp "include" - p/a incluir a página topo.jsp dentro desta página -->
<jsp:include flush="true" page="../partes/topo.jsp"></jsp:include>

<!-- Início conteúdo principal -->
<div id="content">
	<div id="content-header">
		<div id="breadcrumb">
			<a href="<c:url value="/"/>" 
				class="tip-bottom"><i class="icon-home"></i>Início</a><a href="#"
				class="current">${titulo}</a>
		</div>
		<h1>${titulo}</h1>
	</div>

	

	<form action="#" method="post" class="form-horizontal">
		<div class="container-fluid">


			<div class="row-fluid">
				<div class="span12">
					<div class="widget-box">
						<div class="widget-title">
							<span class="icon"> <i class="icon-align-justify"></i>
							</span>
							<h5>Informações</h5>
						</div>
						<div class="widget-content nopadding">

							<div class="control-group">
								<label class="control-label">Nome :</label>
								<div class="controls">
									<input type="text" class="span10" disabled="disabled" name="produto.proNome"
										value="${produto.proNome }" placeholder="Ex: Flor verde" />
								</div>
							</div>

							<div class="control-group">
								<label class="control-label">Preço :</label>
								<div class="controls">
									<input type="text" class="span3" disabled="disabled" name="produto.proPreco"
										value="${produto.proPreco }" placeholder="Ex: 21.00" />
								</div>
							</div>

							<div class="control-group">
								<label class="control-label">Quantidade :</label>
								<div class="controls">
									<input type="number" disabled="disabled" class="span2" name="produto.proQuantidade"
										value="${produto.proQuantidade}" placeholder="Ex: 10" />
								</div>
							</div>


							<div class="control-group">
								<label class="control-label">Escolha Tipo :</label>
								<div class="controls ">
									<select disabled="disabled" name="produto.proTipo">
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
									<input disabled="disabled" autocomplete="off" id="fornecedor" name="fornecedor.forNome"
										type="text" class="span6" placeholder="Ex: Zantoin"
										value="${produto.fornecedorForId.forNome } (id = ${produto.fornecedorForId.forId})" />

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

							<!-- explicação - http://www.daviferreira.com/posts/populando-selects-de-cidades-e-estados-com-ajax-php-e-jquery -->
							<script src="http://code.jquery.com/jquery-1.6.1.js"></script>


						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>
<!-- Fim conteúdo principal -->

<!-- Utilizando tag jsp "include" - p/a incluir a página rodape.jsp dentro desta página -->
<jsp:include page="../partes/rodape.jsp" flush="true"></jsp:include>
