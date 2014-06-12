<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!-- Utilizando taglib core c do JSTL -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Utilizando tag jsp "include" - p/a incluir a página topo.jsp dentro desta página -->
<jsp:include page="../partes/topo.jsp"></jsp:include>

<!-- Início conteúdo principal -->
<div id="content">
	<div id="content-header">
		<div id="breadcrumb">
			<a href="<c:url value="/"/>" 
				class="tip-bottom"><i class="icon-home"></i> Início</a>
		</div>
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
	<!-- Fim alerta de atenção -->

	<div class="quick-actions_homepage">
		<ul class="quick-actions">
			<li><a href="<c:url value="/"/>"> <i class="icon-dashboard"></i>Painel
					principal
			</a></li>
			<c:if test="${usuarioWeb.tipo == 'VENDEDOR' }">
			<li><a href="<c:url value="/venda/novo"/>"> <i
					class="icon-shopping-bag"></i> Adicionar venda
			</a></li>
			</c:if>
			<li><a href="<c:url value="/cliente/listar"/>"> <i
					class="icon-client"></i> Listar clientes
			</a></li>
			<c:if test="${usuarioWeb.tipo == 'PROPRIETARIO' }">
				<li><a
					href="<c:url value="/relatorio/vendas-por-um-dia-ou-no-mes"/>">
						<i class="icon-search"></i> Vendas por dia ou mês
				</a></li>
				<li><a href="<c:url value="/vendedor/novo"/>"> <i
						class="icon-user"></i> Adicionar vendedor
				</a></li>
			</c:if>

		</ul>
	</div>
 <div class="row-fluid">
      <div class="span12">
        <div class="widget-box widget-plain">
          <div class="center">
            <ul class="stat-boxes stat-boxes2">
             <li>
               <div class="left peity_bar_good">TOTAL</div>
                <div class="right"> <strong>${numeroVendas}</strong><span style="color: green;">VENDAS</span></div>
              </li>
              <li>
              <div class="left peity_bar_good">TOTAL</div>
                <div class="right"> <strong>${numeroUsuarios}</strong><span style="color: red;">USUÁRIOS</span></div>
              </li>
              <li>
               <div class="left peity_bar_good">TOTAL</div>
                <div class="right"> <strong>${numeroClientes}</strong><span style="color: blue;">CLIENTES</span></div>
              </li>
              <li>
                <div class="left peity_bar_good">TOTAL</div>
                <div class="right"> <strong>${numeroProdutos}</strong><span style="color: orange;">PRODUTOS</span></div>
              </li>
              <li>
                <div class="left peity_bar_good">TOTAL</div>
                <div class="right"> <strong>${numeroFornecedores}</strong><span style="color: lime;">FORNECEDORES</span></div>
              </li>
              <li>
                <div class="left peity_bar_good">TOTAL</div>
                <div class="right"> <strong>${numeroVendedores}</strong><span style="color: purple;">VENDEDORES</span></div>
              </li>
            </ul>
          </div>
        </div>
      </div>k
    </div>


</div>
<!-- Fim conteúdo principal -->


<!-- Utilizando tag jsp "include" - p/a incluir a página rodape.jsp dentro desta página -->
<jsp:include page="../partes/rodape.jsp"></jsp:include>
