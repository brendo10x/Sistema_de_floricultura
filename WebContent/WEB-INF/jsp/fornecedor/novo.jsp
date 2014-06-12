<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!-- Utilizando taglib core c do JSTL -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Utilizando tag jsp "include" - p/a incluir a p�gina topo.jsp dentro desta p�gina -->
<jsp:include flush="true" page="../partes/topo.jsp"></jsp:include>

<div id="search">
	<form action="lista-buscar" method="post">
		<input required="required" type="text" name="busca"
			placeholder="Pesquisar fornecedor...">

		<button type="submit" class="tip-left" >
			<i class="icon-search icon-white"></i>
		</button>
	</form>
</div>

<!-- In�cio conte�do principal -->
<div id="content">
	<div id="content-header">
		<div id="breadcrumb">
			<a href="<c:url value="/"/>" 
				class="tip-bottom"><i class="icon-home"></i>In�cio</a><a href="#"
				class="current">${titulo}</a>
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
	<c:if test="${sucesso_adicionado != null}">
		<div class="alert alert-success alert-block">
			<a class="close" data-dismiss="alert" href="#">�</a>
			<h4 class="alert-heading">
				Sucesso! <a class="btn btn-success btn-mini"
					href="<c:url value="/fornecedor/listar"/>"> Listar fornecedores</a>
			</h4>
			${sucesso_adicionado}
		</div>
	</c:if>
	<!-- Fim sucesso no cadastro  -->

	<form id="fornecedorForm" action="adicionar" method="post"
		class="form-horizontal">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<div class="widget-box">
						<div class="widget-title">
							<span class="icon"> <i class="icon-align-justify"></i>
							</span>
							<h5>Informa��es b�sicas</h5>
						</div>
						<div class="widget-content nopadding">
							<div class="control-group">
								<label class="control-label">Nome :</label>
								<div class="controls">
									<input name="fornecedor.forNome" type="text" class="span20"
										placeholder="Nome completo" />
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row-fluid">
				<div class="span12">
					<div class="widget-box">
						<div class="widget-title">
							<span class="icon"> <i class="icon-align-justify"></i>
							</span>
							<h5>Endere�o</h5>
						</div>
						<div class="widget-content nopadding">
							<div class="control-group">
								<label class="control-label">Rua :</label>
								<div class="controls">
									<input type="text" name="endereco.endRua" class="span6"
										placeholder="Ex: S�o jos�" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Bairro :</label>
								<div class="controls">
									<input type="text" name="endereco.endBairro" class="span6"
										placeholder="Ex: Vila s�o matheus" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">N�mero :</label>
								<div class="controls">
									<input name="endereco.endNumero" type="text" class="span2"
										placeholder="Ex: 120-1" />
								</div>
							</div>


							<!-- explica��o - http://www.daviferreira.com/posts/populando-selects-de-cidades-e-estados-com-ajax-php-e-jquery -->
							<script src="http://code.jquery.com/jquery-1.6.1.js"></script>
							<script type="text/javascript">
								$(function() {
									$('#cod_estados')
											.change(
													function() {

														if ($(this).val()) {
															$('#cod_cidades')
																	.hide();
															$('.carregando')
																	.show();
															$
																	.getJSON(
																			'<c:url value="/cidade/busca.json?" />',
																			{
																				term : $(
																						this)
																						.val()
																			},
																			function(
																					item) {
																				var options = '';
																				for (var i = 0; i < item.length; i++) {
																					options += '<option value="' + item[i].cidId + '">'
																							+ item[i].cidNome
																							+ '</option>';
																				}
																				$(
																						'#cod_cidades')
																						.html(
																								options)
																						.show();
																				$(
																						'.carregando')
																						.hide();
																			});
														} else {
															$('#cod_cidades')
																	.html(
																			'<option value="">-- Escolha um estado antes --</option>');
														}
													});
								});
							</script>

							<div class="control-group">
								<label class="control-label">Estado :</label>
								<div class="controls ">
									<select name="cod_estados" id="cod_estados">
										<option value="">-- Escolha um estado --</option>
										<c:forEach items="${estados}" var="estado">
											<option value="${estado.estId }">${estado.estDescricao }</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Cidade :</label>
								<div class="controls ">
									<span class="carregando" style="display: none;">Aguarde,
										carregando...</span> <select name="endereco.cidadeCidId.cidId"
										id="cod_cidades">
										<option value="">-- Escolha um estado antes --</option>
									</select>
								</div>
							</div>
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

<!-- valida��o dos campos -->
<script type="text/javascript">
	$('#fornecedorForm').validate({
		rules : {
			"fornecedor.forNome" : {
				required : true,
				minlength : 2
			},
			"endereco.endRua" : {
				required : true,
				minlength : 2
			},
			"endereco.endBairro" : {
				required : true,
				minlength : 2

			},
			"endereco.endNumero" : {
				required : true,
				minlength : 2

			},
			"endereco.cidadeCidId.cidId" : {
				required : true,

			}

		}
	});
</script>
