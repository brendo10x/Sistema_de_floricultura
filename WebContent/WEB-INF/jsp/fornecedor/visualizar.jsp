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

		<button type="submit" class="tip-left">
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



	<form action="#" method="post" class="form-horizontal">
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
									<input name="fornecedor.forNome" disabled="disabled"
										value="${ fornecedor.forNome}" type="text" class="span20"
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
									<input type="text" disabled="disabled" name="endereco.endRua"
										value="${endereco.endRua}" class="span6"
										placeholder="ex: S�o jos�" />
								</div>
							</div>


							<div class="control-group">
								<label class="control-label">Bairro :</label>
								<div class="controls">
									<input name="endereco.endBairro" disabled="disabled"
										value="${endereco.endBairro}" type="text" class="span6"
										placeholder="ex: Vila s�o matheus" />
								</div>
							</div>


							<div class="control-group">
								<label class="control-label">N�mero :</label>
								<div class="controls">
									<input name="endereco.endNumero" disabled="disabled"
										value="${endereco.endNumero}" type="text" class="span2"
										placeholder="ex: 120-1" />
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
									<select disabled="disabled" name="cod_estados" id="cod_estados">
										<option value="">-- Escolha um estado --</option>
										<c:forEach items="${estados}" var="estado">

											<c:choose>
												<c:when
													test="${endereco.cidadeCidId.estadoEstId.estId == estado.estId}">
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
										carregando...</span> <select disabled="disabled"
										name="endereco.cidadeCidId.cidId" id="cod_cidades">
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
