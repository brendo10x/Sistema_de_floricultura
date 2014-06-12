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
				href="<c:url value="/relatorio/vendas-por-um-dia-ou-no-mes"/>"
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
						<h5>Em um dia:</h5>
					</div>


					<!-- datapiker jquery -->
					<script type="text/javascript">
						$(function() {
							$("#datepicker").datepicker(
									{
										changeMonth : true,
										changeYear : true,
										showButtonPanel : true,
										dateFormat : 'yy-mm-dd',
										dayNames : [ 'Domingo', 'Segunda',
												'Terça', 'Quarta', 'Quinta',
												'Sexta', 'Sábado' ],
										dayNamesMin : [ 'D', 'S', 'T', 'Q',
												'Q', 'S', 'S', 'D' ],
										dayNamesShort : [ 'Dom', 'Seg', 'Ter',
												'Qua', 'Qui', 'Sex', 'Sáb',
												'Dom' ],
										monthNames : [ 'Janeiro', 'Fevereiro',
												'Março', 'Abril', 'Maio',
												'Junho', 'Julho', 'Agosto',
												'Setembro', 'Outubro',
												'Novembro', 'Dezembro' ],
										monthNamesShort : [ 'Jan', 'Fev',
												'Mar', 'Abr', 'Mai', 'Jun',
												'Jul', 'Ago', 'Set', 'Out',
												'Nov', 'Dez' ],
										nextText : 'Próximo',
										prevText : 'Anterior',
										currentText : 'Hoje',
										closeText : 'Fechar'
									});
						});
					</script>

					<!-- obtendo o objeto data -->
					<jsp:useBean id="data" class="java.util.Date" />

					<div class="widget-content">
						<form id="vendas_por_um_dia_Form" method="post"
							action="vendas-por-um-dia">

							<div class="control-group">
								<label class="control-label">Data :</label>
								<div class="controls">
									<input type="text" name="dia"
										value="<fmt:formatDate type="time" value="${data}" pattern="yyyy-MM-dd" />"
										id="datepicker" />

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
						<h5>No mês:</h5>
					</div>
					<div class="widget-content">
						<form id="vendas_por_um_mes_form" method="post"
							action="vendas-por-um-mes">
							<div>

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
									<th>VENDEDOR</th>
									<th>DATA DA VENDA</th>
									<th>TOTAL</th>

								</tr>
							</thead>
							<tbody>

								<!-- Início do loop -->
								<c:forEach items="${vendaList}" var="venda">
									<!-- Início do lopp -->
									<tr class="gradeX">
										<td><div align="center">${venda.venId}</div></td>
										<td><div align="center">${venda.vendedorVenId.venNome}</div></td>
										<td><div align="center">${venda.venDataVenda}</div></td>
										<td><div align="center">${venda.venTotal}</div></td>

									</tr>
								</c:forEach>
								<!-- Fim do lopp -->

								<!-- SE NÃO TIVER NENHUM DADO -->
								<c:if test="${ empty vendaList}">
									<tr class="gradeX">
										<td colspan="4"><div align="center">NENHUM DADO
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
	$('#vendas_por_um_dia_Form').validate({
		rules : {

			"dia" : {
				required : true,
				minlength:10,
				maxlength:10
			}
		}
	});
</script>

<!-- validação dos campos -->
<script type="text/javascript">
	$('#vendas_por_um_mes_form').validate({
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
