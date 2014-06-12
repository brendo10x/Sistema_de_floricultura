<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<!-- Utilizando taglib core c do JSTL -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Utilizando tag jsp "include" - p/a incluir a página topo.jsp dentro desta página -->
<jsp:include flush="true" page="../partes/topo.jsp"></jsp:include>
<div id="search">
	<form action="lista-buscar" method="post">
		<input required="required" type="text" name="busca"
		placeholder="Pesquisar vendedor...">

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
		href="<c:url value="/vendedor/listar"/>"> Listar vendedores</a>
	</h4>
	${sucesso_adicionado}
</div>
</c:if>
<!-- Fim sucesso no cadastro  -->

<form id="vendedorForm" action="adicionar" method="post"
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
							<input name="vendedor.venNome" type="text" class="span20"
							placeholder="Nome completo" />
						</div>
					</div>

					<div class="control-group">
						<label class="control-label">CPF :</label>
						<div class="controls">
							<input id="vendedor_venCpf" name="vendedor.venCpf" type="text" class="span5"
							placeholder="Ex: 12345678911" />
						</div>
					</div>

					<div class="control-group">
						<label class="control-label">RG:</label>
						<div class="controls">
							<input name="vendedor.venRg" type="text" class="span5"
							placeholder="Ex: 12345678901" />
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
							placeholder="Nome de usuário" />
						</div>
					</div>

					<div class="control-group">
						<label class="control-label">Senha :</label>
						<div class="controls">
							<input name="usuario.usuSenha" id="senha" type="password"
							class="span6" placeholder="Digite senha " />
						</div>
					</div>

					<div class="control-group">
						<label class="control-label">Confirme a senha :</label>
						<div class="controls">
							<input type="password" class="span6" id="confirmacao"
							equalTo="#senha" placeholder="Digite senha " />

						</div>
					</div>

					<input name="usuario.usuTipo" type="hidden" value="1" />


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
							<input type="text" name="endereco.endRua" class="span6"
							placeholder="Ex: São josé" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">Bairro :</label>
						<div class="controls">
							<input type="text" name="endereco.endBairro" class="span6"
							placeholder="Ex: Vila são matheus" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">Número :</label>
						<div class="controls">
							<input name="endereco.endNumero" type="text" class="span2"
							placeholder="Ex: 120-1" />
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
<!-- Fim conteúdo principal -->

<!-- Utilizando tag jsp "include" - p/a incluir a página rodape.jsp dentro desta página -->
<jsp:include page="../partes/rodape.jsp" flush="true"></jsp:include>


<!-- validação dos campos -->
<script type="text/javascript">
$('#vendedorForm').validate({
	rules : {
		"vendedor.venNome" : {
			required : true,
			minlength : 2
		},
		"vendedor.venCpf" : {
			required : true,
			minlength : 11
		},
		"vendedor.venRg" : {
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

$("#vendedor_venCpf").mask("999.999.999-99");
</script>

