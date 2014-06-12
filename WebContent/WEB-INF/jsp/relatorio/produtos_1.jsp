<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!-- Utilizando taglib core c do JSTL -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Utilizando taglib fmt fmt do JSTL para datas-->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
	
	<div class="span6">
		<div class="widget-box">
			<div class="widget-title">
				<span class="icon"> <i class="icon-th-list"></i>
				</span>
				<h5>No mês:</h5>
			</div>
			<div class="widget-content">
				<form id="produtos_mais_vendidos_no_mes" method="post" action="produtos-mais-vendidos-no-mes">
					<div>
						<!-- obtendo o objeto data -->
						<jsp:useBean id="data" class="java.util.Date" />
						<div class="control-group">
							<label class="control-label">Ano :</label>
							<div class="controls">
								<input type="text" name="ano" class="span3"
									value="<fmt:formatDate type="time" value="${data}" pattern="yyyy" />" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">Escolha o mês</label>
							<div class="controls ">
								<select name="mes">
									<option value="01">Janeiro</option>
									<option value="02">Fevereiro</option>
									<option value="03">Março</option>
									<option value="04">Abril</option>
									<option value="05">Maio</option>
									<option value="06">Junho</option>
									<option value="07">Julho</option>
									<option value="08">Agosto</option>
									<option value="09">Setembro</option>
									<option value="10">Outubro</option>
									<option value="11">Novembro</option>
									<option value="12">Dezembro</option>
								</select>
							</div>
						</div>
						<input type="submit" value="Buscar"
							class="btn btn-warning btn-mini">
					</div>
				</form>
			</div>
		</div>
	</div>

	<div class="container-fluid">
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
									<th>NOME</th>
									<th>VENDIDOS</th>
									<th>PREÇO</th>

								</tr>
							</thead>
							<tbody>
								<!-- Início do loop -->
								<c:forEach items="${produtosList}" var="produto">
									<!-- Início do lopp -->
									<tr class="gradeX">
										<td><div align="center">${produto.proId}</div></td>
										<td><div align="center">${produto.proNome}</div></td>
										<td><div align="center">${produto.proQuantidade}</div></td>
										<td><div align="center">${produto.proPreco}</div></td>

									</tr>
								</c:forEach>
								<!-- Fim do lopp -->
								<!-- SE NÃO TIVER NENHUM DADO -->
								<c:if test="${ empty produtosList}">
									<tr class="gradeX">
										<td colspan="3"><div align="center">NENHUM DADO
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

<!-- validação dos campos -->
<script type="text/javascript">
	$('#produtos_mais_vendidos_no_mes').validate({
		rules : {

			"ano" : {
				required : true,
				number : true,
				minlength:4,
				maxlength:4
			
			}
		}
	});
</script>
