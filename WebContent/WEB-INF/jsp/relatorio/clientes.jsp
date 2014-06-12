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
				class="tip-bottom"><i class="icon-home"></i> Início</a> <a
				href="<c:url value="/proprietario/listar"/>" class="current">${titulo}</a>
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
	
<!-- Alerta de sucesso na pesquisa -->
	<c:if test="${pesquisa != null }">
		<div class="alert alert-info alert-block">
			<a class="close" data-dismiss="alert" href="#">×</a>
			<h4 class="alert-heading">Pesquisa!</h4>
			Resultado(s) da sua pesquisa por :'${pesquisa }'
		</div>
	</c:if>
	<!-- Alerta de sucesso na pesquisa -->

	<div class="container-fluid">

		<div class="row-fluid">
			<div class="span6">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon"> <i class="icon-th-list"></i>
						</span>
						<h5>Em uma cidade:</h5>
					</div>
					<div class="widget-content">
						<form method="post" action="clientes-por-uma-cidade" >

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
									<select name="estado.estId" id="cod_estados">
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
										carregando...</span> <select name="cidade.cidId" id="cod_cidades">
										<option value="">-- Escolha um estado antes --</option>
									</select>
								</div>
							</div>

							<input type="submit" value="Buscar"
								class="btn btn-warning btn-mini">
						</form>
					</div>
				</div>
			</div>
			<div class="span6">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon"> <i class="icon-th-list"></i>
						</span>
						<h5>Em um estado:</h5>
					</div>
					<div class="widget-content">
						<form method="post" action="clientes-por-um-estado">

							<div class="control-group">
								<label class="control-label">Estado :</label>
								<div class="controls ">
									<select name="estado.estId">
										<option value="">-- Escolha um estado --</option>
										<c:forEach items="${estados}" var="estado">
											<option value="${estado.estId }">${estado.estDescricao }</option>
										</c:forEach>
									</select>
								</div>
							</div>

							<input type="submit" value="Buscar"
								class="btn btn-warning btn-mini">

						</form>
					</div>
				</div>
			</div>
		</div>

		<div class="row-fluid">
			<div class="span12">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon"><i class="icon-th"></i></span>
						<h5>TABELA DE DADOS</h5>
					</div>
					<div class="widget-content nopadding">
						<table class="table table-bordered data-table">
							<thead>
								<tr>
									<th>ID</th>
									<th>CLIENTE</th>
									<th>ESTADO</th>
									<th>CIDADE</th>
									

								</tr>
							</thead>
							<tbody>
								<!-- Início do loop -->
								<c:forEach items="${clienteList}" var="cliente">
									<!-- Início do lopp -->
									<tr class="gradeX">
										<td><div align="center">${cliente.cliId}</div></td>
										<td><div align="center">${cliente.cliNome}</div></td>
										<td><div align="center">${cliente.enderecoEndId.cidadeCidId.estadoEstId.estDescricao}</div></td>
										<td><div align="center">${cliente.enderecoEndId.cidadeCidId.cidNome}</div></td>
										
									</tr>
								</c:forEach>
								<!-- Fim do lopp -->
								<!-- SE NÃO TIVER NENHUM DADO -->
								<c:if test="${ empty clienteList}">
									<tr class="gradeX">
										<td colspan="5"><div align="center">NENHUM DADO
												ENCONTRADO!</div></td>

									</tr>
								</c:if>
								<!-- fim if -->
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- Fim conteúdo principal -->

<!-- Utilizando tag jsp "include" - p/a incluir a página rodape.jsp dentro desta página -->
<jsp:include page="../partes/rodape.jsp"></jsp:include>

