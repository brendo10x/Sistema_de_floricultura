<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Utilizando taglib fmt fmt do JSTL para datas-->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${titulo}</title>

<!-- importes padrões css -->
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap-responsive.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/maruti-style.css" />
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/maruti-media.css"
	class="skin-color" />
<%-- <link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/uniform.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/select2.css" /> --%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" />


<!-- importantes -->
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css" />


</head>
<body>
	<!-- Início topo -->

	<!--Header-part-->
	<div id="header">
		<span style="margin-left: 3%;"><img
			src="${pageContext.request.contextPath}/img/logo.png" width="90"
			height="90" /></span>

	</div>
	<!--close-Header-part-->

	<!-- obtendo o objeto data -->
	<jsp:useBean id="data" class="java.util.Date" />

	<!--top-Header-menu-->
	<div id="user-nav" class="navbar navbar-inverse">
		<ul class="nav">


			<li class=""><a title="" href="#"> <span class="text">Hoje:
						<fmt:formatDate type="time" value="${data}" pattern="yyyy-MM-dd" />
				</span></a></li>



			<c:if test="${ usuarioWeb.logado}">

				<li class=""><a title=""
					href="
					<c:if test="${usuarioWeb.tipo == 'PROPRIETARIO' }">
					<c:url value="/proprietario/edita?idProprietario=${usuarioWeb.idEntidadeUsuario}&idUsuario=${usuarioWeb.usuarioId}"/>
						</c:if>
					">

						<i class="icon icon-user"></i> <span class="text"> Olá, <c:if
								test="${usuarioWeb.tipo == 'PROPRIETARIO' }">
							proprietário 
						</c:if> <c:if test="${usuarioWeb.tipo == 'VENDEDOR' }">
							vendedor
						</c:if> ${usuarioWeb.nome }!
					</span>
				</a></li>

			</c:if>

			<c:if test="${usuarioWeb.logado}">
				<li class=""><a title="" href="<c:url value="/logout"/>"><i
						class="icon icon-share-alt"></i> <span class="text">Sair</span></a></li>
			</c:if>

		</ul>
	</div>

	<!--close-top-Header-menu-->

	<div id="sidebar">
		<a href="#" class="visible-phone"><i class="icon icon-home"></i>
			Menu de controle</a>
		<ul>
			<li class="active"><a href="<c:url value="/"/>"><i
					class="icon icon-home"></i> <span>Painel principal</span></a></li>

			<li class="submenu"><a href="#"><i class="icon icon-th"></i>

					<span>Listar</span></a>
				<ul>
					<c:if test="${usuarioWeb.tipo == 'PROPRIETARIO' }">
						<%-- <li><a href="<c:url value="/proprietario/listar"/>">Proprietários</a></li> --%>
						<li><a href="<c:url value="/fornecedor/listar"/>">Fornecedores</a></li>
						<li><a href="<c:url value="/produto/listar"/>">Produtos</a></li>
						<li><a href="<c:url value="/vendedor/listar"/>">Vendedores</a></li>
					</c:if>

					<li><a href="<c:url value="/cliente/listar"/>">Clientes</a></li>


				</ul></li>

			<li class="submenu"><a href="#"><i class="icon icon-th-list"></i>
					<span>Adicionar</span></a>
				<ul>
					<c:if test="${usuarioWeb.tipo == 'PROPRIETARIO' }">
						<%-- <li><a href="<c:url value="/proprietario/novo"/>">Proprietário</a></li> --%>
						<li><a href="<c:url value="/fornecedor/novo"/>">Fornecedor</a></li>
						<li><a href="<c:url value="/produto/novo"/>">Produto</a></li>
						<li><a href="<c:url value="/vendedor/novo"/>">Vendedor</a></li>
					</c:if>
					<c:if test="${usuarioWeb.tipo == 'VENDEDOR' }">
						<li><a href="<c:url value="/cliente/novo"/>">Cliente</a></li>
					</c:if>

				</ul></li>
			<c:if test="${usuarioWeb.tipo == 'VENDEDOR' }">
				<li class="active"><a href="<c:url value="/venda/novo"/>"><i
						class="icon-leaf"></i> <span>Adicionar venda</span></a>
					<ul>
						<li><a href="<c:url value="/venda/listar"/>">Listar
								vendas</a></li>
					</ul></li>
			</c:if>

			<c:if test="${usuarioWeb.tipo == 'PROPRIETARIO' }">

				<li class="active"><a href="<c:url value="/venda/listar"/>"><i
						class="icon-leaf"></i><span>Listar vendas</span></a>
			</c:if>

			<c:if test="${usuarioWeb.tipo == 'PROPRIETARIO' }">
				<li class="submenu"><a href="#"><i class="icon-list-alt"></i>
						<span>Relatório</span></a>
					<ul>

						<li><a
							href="<c:url value="/relatorio/vendas-por-um-dia-ou-no-mes"/>">Vendas
								por dia ou mês</a></li>
						<li><a
							href="<c:url value="/relatorio/vendas-por-um-vendedor-em-um-dia-ou-no-mes"/>">Vendas
								por vendedor</a></li>
						<li><a
							href="<c:url value="/relatorio/clientes-por-estado-ou-cidade"/>">Clientes
								por estado ou cidade</a></li>
						<li><a
							href="<c:url value="/relatorio/produtos-mais-vendidos-no-mes"/>">Produtos
								+ vendidos no mês</a></li>
						<li><a href="<c:url value="/relatorio/produtos-em-estoque"/>">Qtd.
								produtos em estoque</a></li>

					</ul></li>
			</c:if>
		</ul>
	</div>

	<!-- Fim topo -->