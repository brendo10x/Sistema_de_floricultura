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
	<c:if test="${sucesso_editado != null}">
		<div class="alert alert-success alert-block">
			<a class="close" data-dismiss="alert" href="#">×</a>
			<h4 class="alert-heading">
				Sucesso! <a class="btn btn-success btn-mini"
					href="<c:url value="/cliente/listar"/>"> Listar clientes</a>
			</h4>
			${sucesso_editado}
		</div>
	</c:if>
	<!-- Fim sucesso no cadastro  -->

	<form id="clienteForm" action="editar" method="post" class="form-horizontal">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<div class="widget-box">
						<div class="widget-title">
							<span class="icon"> <i class="icon-align-justify"></i>
							</span>
							<h5>Informações básicas</h5>
						</div>
						<div class="widget-content nopadding">
							<div class="control-group">
								<label class="control-label">Nome :</label>
								<div class="controls">
									<input type="text" name="cliente.cliNome" class="span20"
										value="${cliente.cliNome }" placeholder="Ex: Marcos" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">RG:</label>
								<div class="controls">
									<input type="text"  name="cliente.cliRg" class="span5"
										value="${cliente.cliRg }" placeholder="Ex: 12345678901" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Telefone Fixo :</label>
								<div class="controls">
									<input type="text" class="telefones" name="cliente.cliTelefoneFixo" class="span5"
										value="${cliente.cliTelefoneFixo }"
										placeholder="Ex: (88)3523-4545" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Telefone Móvel :</label>
								<div class="controls">
									<input type="text" class="telefones" name="cliente.cliTelefoneMovel"
										value="${cliente.cliTelefoneMovel}" class="span5"
										placeholder="Ex: (88)9999-9999" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">E-mail :</label>
								<div class="controls">
									<input type="text" name="cliente.cliEmail" class="span5"
										value="${cliente.cliEmail}" placeholder="Ex: joao@com.zantoin" />
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
							<h5>Endereço</h5>
						</div>

						<div class="widget-content nopadding">

							<div class="control-group">
								<label class="control-label">Rua :</label>
								<div class="controls">
									<input type="text" name="endereco.endRua"
										value="${endereco.endRua}" class="span6"
										placeholder="ex: São josé" />
								</div>
							</div>


							<div class="control-group">
								<label class="control-label">Bairro :</label>
								<div class="controls">
									<input name="endereco.endBairro" value="${endereco.endBairro}"
										type="text" class="span6" placeholder="ex: Vila são matheus" />
								</div>
							</div>


							<div class="control-group">
								<label class="control-label">Número :</label>
								<div class="controls">
									<input name="endereco.endNumero" value="${endereco.endNumero}"
										type="text" class="span2" placeholder="ex: 120-1" />
								</div>
							</div>
							<!-- explicação - http://www.daviferreira.com/posts/populando-selects-de-cidades-e-estados-com-ajax-php-e-jquery -->
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

											<c:choose>
												<c:when
													test="${endereco.cidadeCidId.estadoEstId.estId == estado.estId || estadoEscolhido == estado.estId}">
													<option selected="selected" value="${estado.estId }">${estado.estDescricao }</option>
												</c:when>
												<c:otherwise>
													<option value="${estado.estId }">${estado.estDescricao }</option>
												</c:otherwise>
											</c:choose>

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
										<c:forEach items="${cidades}" var="cidade">

											<c:choose>
												<c:when test="${endereco.cidadeCidId.cidId == cidade.cidId}">
													<option selected="selected" value="${cidade.cidId}">${cidade.cidNome }</option>
												</c:when>
												<c:otherwise>
													<option value="${cidade.cidId}">${cidade.cidNome}</option>
												</c:otherwise>
											</c:choose>

										</c:forEach>
									</select>
								</div>
							</div>

							<input type="hidden" name="cliente.cliId"
								value="${cliente.cliId}" /> <input type="hidden"
								name="endereco.endId" value="${endereco.endId}" />

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
	$('#clienteForm').validate({
		rules : {
			"cliente.cliNome" : {
				required : true,
				minlength : 2
			},
			"cliente.cliRg" : {
				required : true,
				minlength : 2
			},
			"cliente.cliTelefoneFixo" : {
				required : true,
				minlength : 10

			},
			"cliente.cliTelefoneMovel" : {
				required : true,
				minlength : 10
			},
			"cliente.cliEmail" : {
				required : true,
				minlength : 2,
				email:true

			},
			"endereco.endRua" : {
				required : true,
				minlength : 2

			},
			"endereco.endNumero" : {
				required : true,
				minlength : 2

			},
			"endereco.endBairro" : {
				required : true,
				minlength : 2
			},
			"endereco.cidadeCidId.cidId" : {
				required : true

			}

		}
	});
	$(".telefones").mask("(99)9999-9999");
</script>
