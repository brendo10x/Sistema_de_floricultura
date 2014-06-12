Sistema de floricultura
=======================

Trabalho do 4° Semestre 10/11/2013 - FJN - SI - Programação 3 e Banco de dados 2.

O sistema utiliza Java EE, Vraptor, Tomcat, JPA, Hibernate, Mysql, HTML5, CSS3, JQUERY e JS.


Para utilizar do código é preciso fazer algumas alterações.

- 1- Criar um banco de dados chamado "banco-trabalho-sistema" de preferência no Mysql, usuário: root, senha:"".
- 2- Importar as tabelas que estão no arquivo "banco-trabalho-sistema.sql" para o banco de dados criado.
- 3- Modificar o arquivo "persistence.xml" e informar sobre o banco de dados criado.

Para entrar no sistema você precisa informar um usuário, as tabelas importadas tem 2 tipos de usuário.

Usuário do proprietário:
- Usuário: brendo10x
-   Senha: admin
 
Usuário do vendedor:
- Usuário: brendo7x
-   Senha: admin 

Funcionalidades do sistema
- Autenticação de 2 tipos de nível de acesso, no caso o proprietário e vendedor.
- Cadastro, atualização, exclusão, busca, listagem e paginação de clientes.
- Cadastro, atualização, exclusão, busca, listagem e paginação de vendedores.
- Cadastro, atualização, exclusão, busca, listagem e paginação de fornecedores.
- Cadastro, atualização, exclusão, busca, listagem e paginação de vendas.
- Cadastro, atualização, exclusão, busca, listagem e paginação de produtos.
- Listar todas as vendas realizadas em um dia ou mês.
- Listar todas as vendas realizadas por vendedor em um dia ou no mês.
- Listar todos os clientes de uma determinada cidade ou estado.
- Listar os produtos mais vendidos no mês.
- Listar a quantidade de cada produto em estoque.
