<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!-- Utilizando taglib core c do JSTL -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Utilizando tag jsp "include" - p/a incluir a página topo.jsp dentro desta página -->
<jsp:include flush="true" page="../partes/topo.jsp"></jsp:include>
<div id="search">
	<form action="lista-buscar" method="post">
		<input required="required" type="text" name="busca" placeholder="Pesquisar produto...">
		
		<button type="submit" class="tip-left" data-original-title="Search">
			<i class="icon-search icon-white"></i>
		</button>
	</form>
</div>
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

	<!-- Alerta de erro  -->
	<c:if test="${errors != null}">
		<div class="alert alert-error alert-block">
			<a class="close" data-dismiss="alert" href="#">×</a>
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
	<c:if test="${sucesso_adicionado != null}">
		<div class="alert alert-success alert-block">
			<a class="close" data-dismiss="alert" href="#">×</a>
			<h4 class="alert-heading">
				Sucesso! <a class="btn btn-success btn-mini"
					href="<c:url value="/produto/listar"/>"> Listar produtos</a>
			</h4>
			${sucesso_adicionado}
		</div>
	</c:if>
	<!-- Fim sucesso no cadastro  -->

	<form id="produtoForm" action="adicionar" method="post" class="form-horizontal">
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
									<input type="text" class="span10" name="produto.proNome"
										placeholder="Ex: Flor verde" />
								</div>
							</div>

							<div class="control-group">
								<label class="control-label">Preço :</label>
								<div class="controls">
									<input type="text" id="precoPro" class="span3" name="produto.proPreco"
										placeholder="Ex: 21.00" />
								</div>
							</div>

							<div class="control-group">
								<label class="control-label">Quantidade :</label>
								<div class="controls">
									<input type="number" class="span2" name="produto.proQuantidade"
										placeholder="Ex: 10" value="1" />
								</div>
							</div>


							<div class="control-group">
								<label class="control-label">Escolha Tipo :</label>
								<div class="controls ">
									<select name="produto.proTipo">
										<option value="0">Flor</option>
										<option value="1">Vaso</option>
										<option value="2">Planta</option>
									</select>
								</div>
							</div>

							<div class="control-group">
								<label class="control-label">Escolha fornecedor :</label>
								<div class="controls">
									<input autocomplete="off" id="fornecedor" name="produto.fornecedorForId.forNome" type="text"
										class="span6" placeholder="Ex: Zantoin" />
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
							


							<input type="hidden" class="span10"  id="idFornecedor"
								name="produto.fornecedorForId.forId" />


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
<!-- Fim conteúdo principal -->

<!-- Utilizando tag jsp "include" - p/a incluir a página rodape.jsp dentro desta página -->
<jsp:include page="../partes/rodape.jsp" flush="true"></jsp:include>

<!-- validação dos campos -->
<script type="text/javascript">
	$('#produtoForm').validate({
		rules : {
			"produto.proNome" : {
				required : true,
				minlength : 2
			},
			"produto.proPreco" : {
				number:true,
				required : true,
				min : 1
				
			},
			"produto.proQuantidade" : {
				number:true,
				required : true,
				min : 1

			},
			"produto.fornecedorForId.forNome" : {
				required : true,

			}

		}
	});
	

</script>



