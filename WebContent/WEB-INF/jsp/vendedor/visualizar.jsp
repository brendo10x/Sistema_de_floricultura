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

	<form  action="#" method="post"
		class="form-horizontal">
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
									<input name="vendedor.venNome" disabled="disabled" type="text" class="span20"
										value="${vendedor.venNome }" placeholder="Nome completo" />
								</div>
							</div>

							<div class="control-group">
								<label class="control-label">CPF :</label>
								<div class="controls">
									<input name="vendedor.venCpf" disabled="disabled"  type="text" class="span5"
										value="${vendedor.venCpf}" placeholder="Ex: 12345678911" />
								</div>
							</div>

							<div class="control-group">
								<label class="control-label">RG:</label>
								<div class="controls">
									<input name="vendedor.venRg" type="text" class="span5"
										value="${vendedor.venRg}" disabled="disabled"  placeholder="Ex: 12345678901" />
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
							<h5>Informações de usuário</h5>
						</div>
						<div class="widget-content nopadding">

							<div class="control-group">
								<label class="control-label">Nome :</label>
								<div class="controls">
									<input name="usuario.usuNome" type="text" class="span20"
										value="${usuario.usuNome } " disabled="disabled"  placeholder="Nome de usuário" />
								</div>
							</div>

							<div class="control-group">
								<label class="control-label">Senha :</label>
								<div class="controls">
									<input name="usuario.usuSenha" id="senha" type="password" class="span6"
										value="${usuario.usuSenha}" disabled="disabled"  placeholder="Digite senha " />
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
									<input type="text" disabled="disabled" name="endereco.endRua" class="span6"
										value="${endereco.endRua}" placeholder="Ex: São josé" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Bairro :</label>
								<div class="controls">
									<input type="text"  disabled="disabled" name="endereco.endBairro" class="span6"
										value="${endereco.endBairro}"
										placeholder="Ex: Vila são matheus" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Número :</label>
								<div class="controls">
									<input name="endereco.endNumero"  disabled="disabled" type="text" class="span2"
										value="${endereco.endNumero}" placeholder="Ex: 120-1" />
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
									<select  disabled="disabled" name="cod_estados" id="cod_estados">
										<option value="">-- Escolha um estado --</option>
										<c:forEach items="${estados}" var="estado">

											<c:choose>
												<c:when
													test="${endereco.cidadeCidId.estadoEstId.estId == estado.estId || estadoEscolhido == estado.estId}">
													<option selected="selected" value="${estado.estId }">${estado.estDescricao } </option>
												</c:when>
												<c:otherwise>
													<option value="${estado.estId }">${estado.estDescricao } </option>
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
										carregando...</span> <select disabled="disabled" name="endereco.cidadeCidId.cidId"
										id="cod_cidades">
										<option value="">-- Escolha um estado antes --</option>
										<c:forEach items="${cidades}" var="cidade">

											<c:choose>
												<c:when test="${endereco.cidadeCidId.cidId == cidade.cidId}">
													<option selected="selected" value="${cidade.cidId}">${cidade.cidNome }</option>
												</c:when>
												<c:otherwise>
													<option value="${cidade.cidId}">${cidade.cidNome} </option>
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
<!-- Fim conteúdo principal -->

<!-- Utilizando tag jsp "include" - p/a incluir a página rodape.jsp dentro desta página -->
<jsp:include page="../partes/rodape.jsp" flush="true"></jsp:include>

