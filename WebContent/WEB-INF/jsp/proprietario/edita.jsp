<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<!-- Utilizando taglib core c do JSTL -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Utilizando tag jsp "include" - incluindo a página topo.jsp dentro desta página -->
<jsp:include flush="true" page="../partes/topo.jsp"></jsp:include>


<!-- Início conteúdo principal -->
<div id="content">
	<div id="content-header">
		<div id="breadcrumb">
			<a href="<c:url value="/"/>"  class="tip-bottom"><i
				class="icon-home"></i> Início</a><a href="#" class="current">${titulo}</a>
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

	<!-- Alerta de sucesso no editar -->
	<c:if test="${sucesso_editado != null}">
		<div class="alert alert-success alert-block">
			<a class="close" data-dismiss="alert" href="#">×</a>
			<h4 class="alert-heading">
				Sucesso! 
			</h4>
			${sucesso_editado}
		</div>
	</c:if>
	<!-- Fim sucesso no editar  -->

	<form id="proprietarioForm" action="editar" method="post" class="form-horizontal">

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
									<input name="proprietario.proNome" type="text" class="span20"
										placeholder="Nome completo" value="${proprietario.proNome}" />

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
										placeholder="Nome de usuário" value="${usuario.usuNome }" />
								</div>
							</div>

							<div class="control-group">
								<label class="control-label">Senha :</label>
								<div class="controls">
									<input name="usuario.usuSenha" id="senha" type="password" class="span6"
										placeholder="Digite senha " value="${ usuario.usuSenha}" />

								</div>
							</div>
							
							
							<div class="control-group">
								<label class="control-label">Confirme a senha :</label>
								<div class="controls">
									<input type="password" class="span6" id="confirmacao" 
									equalTo="#senha" placeholder="Digite senha "/>

								</div>
							</div>

							<!-- carregando campos ocultos necessários para operação -->
							<input type="hidden" name="proprietario.proId"
								value="${proprietario.proId}" /> <input type="hidden"
								name="proprietario.usuarioId" value="${proprietario.usuarioId}" />

							<input type="hidden" name="usuario.usuId"
								value="${usuario.usuId }" /> <input type="hidden"
								name="usuario.usuTipo" value="${usuario.usuTipo }" />

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
	$('#proprietarioForm').validate({
		rules : {
			"proprietario.proNome" : {
				required : true,
				minlength : 2
			},
			"usuario.usuNome" : {
				required : true,
				minlength : 2
			},
			"usuario.usuSenha" : {
				required : true,
				minlength : 2,
				
			}
		}
	});
</script>
