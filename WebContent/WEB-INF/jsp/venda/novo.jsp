<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!-- Utilizando taglib core c do JSTL tomada de decisão, repetição e url -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Utilizando taglib fmt fmt do JSTL para datas-->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- Utilizando tag jsp "include" - p/a incluir a página topo.jsp dentro desta página -->
<jsp:include flush="true" page="../partes/topo.jsp"></jsp:include>

<!-- obtendo o objeto data -->
<jsp:useBean id="data" class="java.util.Date" />
<div id="search">
	<form action="lista-buscar" method="post">
		<input class="mascaraDataVenda" autocomplete="off" required="required" type="text"
			title="ex: <fmt:formatDate type="time" value="${data}" pattern="yyyy-MM-dd" />"
			name="busca" placeholder="Pesquisar venda...">

		<button type="submit" class="tip-left">
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
					href="<c:url value="/venda/listar"/>"> Listar vendas</a>
			</h4>
			${sucesso_adicionado}
		</div>
	</c:if>
	<!-- Fim sucesso no cadastro  -->


	<form id="VENDA" action="adicionar" method="post"
		class="form-horizontal">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<div class="widget-box">
						<div class="widget-title">
							<span class="icon"> <i class="icon-align-justify"></i>
							</span>
							<h5>Informações sobre cliente</h5>
						</div>
						<div class="widget-content nopadding">

							<div class="control-group">
								<label class="control-label">Cliente :</label>
								<div class="controls">
									<input type="text" name="cliente2" id="cliente"
										autocomplete="off" class="span20"
										placeholder="Escolha cliente" />
								</div>
							</div>

							<!-- autocomplete -->
							<script type="text/javascript">
								$(document)
										.ready(
												function() {
													$("#cliente")
															.autocomplete(
																	{
																		width : 300,
																		max : 10,
																		delay : 100,
																		minLength : 1,
																		source : function(
																				request,
																				response) {
																			$
																					.ajax({
																						url : '<c:url value="/cliente/busca.json" />',
																						dataType : "json",
																						data : request,
																						success : function(
																								data) {

																							response($
																									.map(
																											data,
																											function(
																													item) {
																												return {
																													label : item.cliNome
																															+ " (id = "
																															+ item.cliId
																															+ ")",
																													value : item.cliNome,
																													variavel : item.cliId

																												}

																											}));
																						}

																					});
																		},
																		minLength : 1,
																		select : function(
																				event,
																				ui) {

																			$(
																					"#idCliente")
																					.attr(
																							"value",
																							ui.item.variavel);

																		}

																	});
												});
							</script>


							<!-- id do cliente -->
							<input type="hidden" id="idCliente" name="cliente.cliId"
								class="span20" />

							<!-- forma de pagamento -->
							<div class="control-group">
								<label class="control-label">Forma de pagamento :</label>
								<div class="controls ">
									<select name="venda.venFormaPagamento">
										<option value="0">Cartão</option>
										<option value="1">Boleto</option>
										<option value="2">Dinheiro</option>
									</select>
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
							<h5>Informações sobre venda</h5>
						</div>
						<div class="widget-content nopadding">


							<!-- datapiker jquery -->
							<script type="text/javascript">
								$(function() {
									$(".datepicker")
											.datepicker(
													{
														changeMonth : true,
														changeYear : true,
														showButtonPanel : true,
														dateFormat : 'yy-mm-dd',
														dayNames : [ 'Domingo',
																'Segunda',
																'Terça',
																'Quarta',
																'Quinta',
																'Sexta',
																'Sábado' ],
														dayNamesMin : [ 'D',
																'S', 'T', 'Q',
																'Q', 'S', 'S',
																'D' ],
														dayNamesShort : [
																'Dom', 'Seg',
																'Ter', 'Qua',
																'Qui', 'Sex',
																'Sáb', 'Dom' ],
														monthNames : [
																'Janeiro',
																'Fevereiro',
																'Março',
																'Abril',
																'Maio',
																'Junho',
																'Julho',
																'Agosto',
																'Setembro',
																'Outubro',
																'Novembro',
																'Dezembro' ],
														monthNamesShort : [
																'Jan', 'Fev',
																'Mar', 'Abr',
																'Mai', 'Jun',
																'Jul', 'Ago',
																'Set', 'Out',
																'Nov', 'Dez' ],
														nextText : 'Próximo',
														prevText : 'Anterior',
														currentText : 'Hoje',
														closeText : 'Fechar'
													});
								});
							</script>


							<!-- autocomplete vendedor 2 -->
							<script type="text/javascript">
								$(document)
										.ready(
												function() {
													$("#vendedor2")
															.autocomplete(
																	{
																		width : 300,
																		max : 10,
																		delay : 100,
																		minLength : 1,
																		source : function(
																				request,
																				response) {
																			$
																					.ajax({
																						url : '<c:url value="/vendedor/busca.json" />',
																						dataType : "json",
																						data : request,
																						success : function(
																								data) {
																							response($
																									.map(
																											data,
																											function(
																													item) {
																												return {
																													label : item.venNome
																															+ " (id = "
																															+ item.venId
																															+ ")",
																													value : item.venNome,
																													id : item.venId
																												}

																											}));
																						}

																					});
																		},
																		minLength : 1,
																		select : function(
																				event,
																				ui) {

																			$(
																					"#idvendedor2")
																					.attr(
																							"value",
																							ui.item.id);

																		}

																	});
												});
							</script>

							<!-- explicação - http://www.daviferreira.com/posts/populando-selects-de-cidades-e-estados-com-ajax-php-e-jquery -->

							<div class="control-group">
								<label class="control-label">Escolha vendedor :</label>
								<div class="controls">
									<input autocomplete="off" name="vendedor2" id="vendedor2"
										type="text" class="span20" placeholder="Ex: Zantõin" />

								</div>
							</div>

							<input name="vendedor.venId" id="idvendedor2" type="hidden"
								class="span20" placeholder="Ex: Zantõin" />



							<!-- 	data da venda -->
							<div class="control-group">
								<label class="control-label">Data :</label>
								<div class="controls">
									<input type="text" name="vendaData"
										value="<fmt:formatDate type="time" value="${data}" pattern="yyyy-MM-dd" />"
										class="datepicker" />
								</div>
							</div>

							<!-- adiciona e remove campos + autocomplete -->
							<script type="text/javascript">
								$(document)
										.ready(
												function() {

													var MaxInputs = 8; //maximum input boxes allowed
													var InputsWrapper = $("#InputsWrapper"); //Input boxes wrapper ID
													var AddButton = $("#AddMoreFileBox"); //Add button ID

													var x = InputsWrapper.length; //initlal text box count
													if (x == 0) {
														x = 1;
													}
													var FieldCount = 0; //to keep track of text box added

													$(AddButton)
															.click(
																	function(e) //on add input button click
																	{
																		if (x <= MaxInputs) //max input box allowed
																		{
																			FieldCount++; //text box added increment
																			//add input box
																			$(
																					InputsWrapper)
																					.append(
																							'<div class="control-group"><label class="control-label">Escolha produto :</label><div class="controls"><input type="text" disabled="disabled"   class="span4"  id="produto_'+ FieldCount+'"  placeholder="Nome do produto"  /> <span style="margin-left: 2%;"> Preço : <input type="text" disabled="disabled"  name="#" id="preco_'+ FieldCount+'" value=" '+ FieldCount +'" class="span2" placeholder="Preço" /></span> <span style="margin-left: 2%;"> Qtd : <input type="text"   class="span2" name="produtosVendidos[].proVendquantidades" onChange="recalc();" id="quantidade_'
																									+ FieldCount
																									+ '" value="'
																									+ 1
																									+ '"  placeholder="Quantidade" /></span><span style="margin-left:1%" class="removeclass" ><a href="#" class="btn btn-danger btn-mini" ><i class="icon-trash"></i>Remover produto</a><span><span style="color: green; margin-left: 1%; font-size: 15px" id="total_item_'
																									+ FieldCount
																									+ '">$'
																									+ 1
																									+ '.00</span><input value="" id="idProduto_'+ FieldCount+'" type="hidden"name="produtosVendidos[].produtosProId.proId" /></div>');
																			x++; //text box increment
																			recalc();
																		}
																		return false;
																	});

													$("body")
															.on(
																	"click",
																	".removeclass",
																	function(e) { //user click on remove text
																		if (x > 1) {
																			$(
																					this)
																					.parents(
																							'div.control-group')
																					.remove(); //remove text box

																			x--; //decrement textbox

																			recalc();
																		}
																		return false;
																	})

													$("#produto")
															.autocomplete(
																	{
																		width : 300,
																		max : 10,
																		delay : 100,
																		minLength : 1,
																		source : function(
																				request,
																				response) {
																			$
																					.ajax({
																						url : '<c:url value="/produto/busca.json" />',
																						dataType : "json",
																						data : request,
																						success : function(
																								data) {
																							response($
																									.map(
																											data,
																											function(
																													item) {
																												return {
																													label : item.proNome
																															+ " (id = "
																															+ item.proId
																															+ ")",
																													value : item.proNome,
																													idProduto : item.proId,
																													nomeProduto : item.proNome,
																													precoPoduto : item.proPreco

																												}

																											}));
																						}

																					});
																		},
																		minLength : 1,
																		select : function(
																				event,
																				ui) {
																			$(
																					"#AddMoreFileBox")
																					.click();

																			$(
																					"#produto_"
																							+ FieldCount)
																					.attr(
																							"value",
																							ui.item.nomeProduto
																									+ " (id = "
																									+ ui.item.idProduto
																									+ ")");

																			$(
																					"#preco_"
																							+ FieldCount)
																					.attr(
																							"value",
																							ui.item.precoPoduto);
																			$(
																					"#idProduto_"
																							+ FieldCount)
																					.attr(
																							"value",
																							ui.item.idProduto);

																			recalc();

																		}

																	});

												});
							</script>
							<!-- cálculo automática da venda -->
							<script type="text/javascript">
								var bIsFirebugReady = (!!window.console && !!window.console.log);

								$(document)
										.ready(
												function() {
													// update the plug-in version
													$("#idPluginVersion")
															.text(
																	$.Calculation.version);

													/*			
																$.Calculation.setDefaults({
																	onParseError: function(){
																		this.css("backgroundColor", "#cc0000")
																	}
																	, onParseClear: function (){
																		this.css("backgroundColor", "");
																	}
																});
													 */

													// bind the recalc function to the quantity fields
													$("[id^=quantidade_]")
															.bind("keyup",
																	recalc);
													// run the calculation function now
													recalc();

													// automatically update the "#totalSum" field every time
													// the values are changes via the keyup event
													$("input[name^=sum]").sum(
															"keyup",
															"#totalSum");

													// automatically update the "#totalAvg" field every time
													// the values are changes via the keyup event
													$("input[name^=avg]")
															.avg(
																	{
																		bind : "keyup",
																		selector : "#totalAvg"
																		// if an invalid character is found, change the background color
																		,
																		onParseError : function() {
																			this
																					.css(
																							"backgroundColor",
																							"#cc0000")
																		}
																		// if the error has been cleared, reset the bgcolor
																		,
																		onParseClear : function() {
																			this
																					.css(
																							"backgroundColor",
																							"");
																		}
																	});

													// automatically update the "#minNumber" field every time
													// the values are changes via the keyup event
													$("input[name^=min]").min(
															"keyup",
															"#numberMin");

													// automatically update the "#minNumber" field every time
													// the values are changes via the keyup event
													$("input[name^=max]")
															.max(
																	"keyup",
																	{
																		selector : "#numberMax",
																		oncalc : function(
																				value,
																				options) {
																			// you can use this to format the value
																			$(
																					options.selector)
																					.val(
																							value);
																		}
																	});

													// this calculates the sum for some text nodes
													$("#idTotalTextSum")
															.click(
																	function() {
																		// get the sum of the elements
																		var sum = $(
																				".textSum")
																				.sum();

																		// update the total
																		$(
																				"#totalTextSum")
																				.text(
																						"$"
																								+ sum
																										.toString());
																	});

													// this calculates the average for some text nodes
													$("#idTotalTextAvg")
															.click(
																	function() {
																		// get the average of the elements
																		var avg = $(
																				".textAvg")
																				.avg();

																		// update the total
																		$(
																				"#totalTextAvg")
																				.text(
																						avg
																								.toString());
																	});
												});

								//calcula automática só mudando a quantidade
								function recalc() {
									$("[id^=total_item]")
											.calc(
													// the equation to use for the calculation
													"qty * price",
													// define the variables used in the equation, these can be a jQuery object
													{
														qty : $("input[id^=quantidade_]"),
														price : $("[id^=preco_]")
													},
													// define the formatting callback, the results of the calculation are passed to this function
													function(s) {
														// return the number as a dollar amount
														return "$"
																+ s.toFixed(2);
													},
													// define the finish callback, this runs after the calculation has been complete
													function($this) {
														// sum the total of the $("[id^=total_item]") selector
														var sum = $this.sum();

														$("#grandTotal").text(
														// round the results to 2 digits
														"$" + sum.toFixed(2));
														//retorna o valor tbm para o input do formulário o total

														$("#totalVenda").attr(
																"value",
																sum.toFixed(2));

													});
								}
							</script>



							<div id="InputsWrapper">


								<!-- chamada para o autocomplete de produto -->
								<div class="control-group">
									<label class="control-label">Escolha produto :</label>
									<div class="controls">
										<input id="produto" type="text" name="#" class="span4"
											placeholder="Nome do produto" /> <span id="AddMoreFileBox"></span>
									</div>
									<!-- fim .controls -->
								</div>
								<!-- fim .control-group -->


							</div>
							<!-- fim #InputsWrapper -->
							<!-- total venda -->
							<input type="hidden" name="venda.venTotal" id="totalVenda" />



							<div class="form-actions">
								<button type="submit" class="btn btn-success">Finalizar
									venda</button>

								<div style="float: right">
									Total : <b style="color: #51A351; font-size: 40px"><span
										id="grandTotal">2013</span></br>
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

<!-- validação dos campos -->
<script type="text/javascript">
	$('#VENDA').validate({
		rules : {

			"cliente2" : {
				required : true
			},
			"vendedor2" : {
				required : true
			},
			"vendaData" : {
				required : true
			},
			"produtosVendidos[].proVendquantidades" : {
				number : true,
				required : true,
				min : 1
			

			}

		}
	});
	
	$(".mascaraDataVenda").mask("9999-99-99");
</script>


