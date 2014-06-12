<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!-- Utilizando taglib core c do JSTL -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Utilizando taglib fmt fmt do JSTL para datas-->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<head>
<title>${titulo}</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap-responsive.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/maruti-login.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
</head>
<body>
	<div id="logo">
		<img src="img/login-logo.png" alt="" />
	</div>
	<!-- Alerta de atenção  -->
	<c:if test="${atencao != null}">
		<div class="alert alert-block">
			<a class="close" data-dismiss="alert" href="#">×</a>
			<h4 class="alert-heading">Atenção!</h4>

			<ul>

				<li>${atencao}</li>

			</ul>
		</div>
	</c:if>
	<!-- Fim alerta de erro -->
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

	<div id="loginbox">
		<form id="loginform" method="post" class="form-vertical"
			action="login">
			<div class="control-group normal_text">
				<span><img
					src="${pageContext.request.contextPath}/img/logo.png" width="180"
					height="180" /></span>

			</div>
			<div class="control-group">
				<div class="controls">
					<div class="main_input_box">
						<span class="add-on"><i class="icon-user"></i></span><input
							type="text"  name="usuario.usuNome"
							placeholder="Nome de usuário" />
					</div>
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<div class="main_input_box">
						<span class="add-on"><i class="icon-lock"></i></span><input
							type="password"  name="usuario.usuSenha"
							placeholder="Senha" />
					</div>
				</div>
			</div>

			<!-- obtendo o objeto data -->
			<jsp:useBean id="data" class="java.util.Date" />

			<div class="form-actions">
				<span class="pull-left"> SISTEMA FLORICULTURA @<fmt:formatDate
						type="time" value="${data}" pattern="yyyy" /></span> <span
					class="pull-right"><input type="submit"
					class="btn btn-success" value="Entrar" /></span>
			</div>
		</form>
		<!-- <form id="recoverform" action="#" class="form-vertical">
			<p class="normal_text">
				Enter your e-mail address below and we will send you instructions <br />
				<font color="#FF6633">how to recover a password.</font>
			</p>

			<div class="controls">
				<div class="main_input_box">
					<span class="add-on"><i class="icon-envelope"></i></span><input
						type="text" placeholder="E-mail address" />
				</div>
			</div>

			<div class="form-actions">
				<span class="pull-left"><a href="#"
					class="flip-link btn btn-warning" id="to-login">&laquo; Back to
						login</a></span> <span class="pull-right"><input type="submit"
					class="btn btn-info" value="Recover" /></span>
			</div> 
		</form>-->
	</div>

	<!--  <script src="js/jquery.min.js"></script>  
        <script src="js/maruti.login.js"></script> -->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/maruti.login.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
</body>

</html>
<!-- validação dos campos -->
<script type="text/javascript">
	$('#loginform').validate({
		rules : {
			"usuario.usuNome" : {
				required : true

			},
			"usuario.usuSenha" : {

				required : true

			}

		}
	});
</script>
