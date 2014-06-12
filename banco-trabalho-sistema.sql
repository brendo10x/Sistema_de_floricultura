-- phpMyAdmin SQL Dump
-- version 3.3.9
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tempo de Geração: Jun 12, 2014 as 03:57 AM
-- Versão do Servidor: 5.1.53
-- Versão do PHP: 5.3.4

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Banco de Dados: `banco-trabalho-sistema`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `cidade`
--

CREATE TABLE IF NOT EXISTS `cidade` (
  `cid_id` int(11) NOT NULL AUTO_INCREMENT,
  `cid_nome` varchar(255) DEFAULT NULL,
  `estado_est_id` int(11) NOT NULL,
  PRIMARY KEY (`cid_id`),
  UNIQUE KEY `estado_est_id` (`estado_est_id`),
  KEY `FKAEE65724B36B3DE0` (`estado_est_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Extraindo dados da tabela `cidade`
--

INSERT INTO `cidade` (`cid_id`, `cid_nome`, `estado_est_id`) VALUES
(1, 'Roadsa', 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `cliente`
--

CREATE TABLE IF NOT EXISTS `cliente` (
  `cli_id` int(11) NOT NULL AUTO_INCREMENT,
  `cli_email` varchar(255) NOT NULL,
  `cli_nome` varchar(255) NOT NULL,
  `cli_rg` varchar(255) NOT NULL,
  `cli_telefone_fixo` varchar(255) NOT NULL,
  `cli_telefone_movel` varchar(255) NOT NULL,
  `endereco_end_id` int(11) NOT NULL,
  PRIMARY KEY (`cli_id`),
  UNIQUE KEY `endereco_end_id` (`endereco_end_id`),
  KEY `FK334B85FAEE030DCB` (`endereco_end_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Extraindo dados da tabela `cliente`
--

INSERT INTO `cliente` (`cli_id`, `cli_email`, `cli_nome`, `cli_rg`, `cli_telefone_fixo`, `cli_telefone_movel`, `endereco_end_id`) VALUES
(1, 'brendo9x@live.com', 'dffsdf', '2342342234', '(23)4234-2432', '(23)4234-3234', 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `endereco`
--

CREATE TABLE IF NOT EXISTS `endereco` (
  `end_id` int(11) NOT NULL AUTO_INCREMENT,
  `end_bairro` varchar(255) DEFAULT NULL,
  `end_numero` varchar(255) DEFAULT NULL,
  `end_rua` varchar(255) DEFAULT NULL,
  `cidade_cid_id` int(11) NOT NULL,
  PRIMARY KEY (`end_id`),
  UNIQUE KEY `cidade_cid_id` (`cidade_cid_id`),
  KEY `FK672D67C9246209E8` (`cidade_cid_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Extraindo dados da tabela `endereco`
--

INSERT INTO `endereco` (`end_id`, `end_bairro`, `end_numero`, `end_rua`, `cidade_cid_id`) VALUES
(1, 'ede', 'wedw', 'ewdewd', 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `estado`
--

CREATE TABLE IF NOT EXISTS `estado` (
  `est_id` int(11) NOT NULL AUTO_INCREMENT,
  `est_descricao` varchar(255) DEFAULT NULL,
  `est_sigla` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`est_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Extraindo dados da tabela `estado`
--

INSERT INTO `estado` (`est_id`, `est_descricao`, `est_sigla`) VALUES
(1, 'Pernabuca', 'as');

-- --------------------------------------------------------

--
-- Estrutura da tabela `fornecedor`
--

CREATE TABLE IF NOT EXISTS `fornecedor` (
  `for_id` int(11) NOT NULL AUTO_INCREMENT,
  `for_nome` varchar(255) DEFAULT NULL,
  `endereco_end_id` int(11) NOT NULL,
  PRIMARY KEY (`for_id`),
  UNIQUE KEY `endereco_end_id` (`endereco_end_id`),
  KEY `FK4E1EA165EE030DCB` (`endereco_end_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Extraindo dados da tabela `fornecedor`
--


-- --------------------------------------------------------

--
-- Estrutura da tabela `produtos`
--

CREATE TABLE IF NOT EXISTS `produtos` (
  `pro_id` int(11) NOT NULL AUTO_INCREMENT,
  `pro_nome` varchar(255) DEFAULT NULL,
  `pro_preco` double DEFAULT NULL,
  `pro_quantidade` int(11) DEFAULT NULL,
  `pro_tipo` int(11) DEFAULT NULL,
  `fornecedor_for_id` int(11) NOT NULL,
  PRIMARY KEY (`pro_id`),
  UNIQUE KEY `fornecedor_for_id` (`fornecedor_for_id`),
  KEY `FKC42C109A7749545D` (`fornecedor_for_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Extraindo dados da tabela `produtos`
--


-- --------------------------------------------------------

--
-- Estrutura da tabela `produtos_vendidos`
--

CREATE TABLE IF NOT EXISTS `produtos_vendidos` (
  `proVend_id` int(11) NOT NULL AUTO_INCREMENT,
  `proVend_quantidades` int(11) DEFAULT NULL,
  `produtos_pro_id` int(11) DEFAULT NULL,
  `venda_ven_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`proVend_id`),
  KEY `FK4000C0699B8E5D99` (`produtos_pro_id`),
  KEY `FK4000C0696C600CAD` (`venda_ven_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Extraindo dados da tabela `produtos_vendidos`
--


-- --------------------------------------------------------

--
-- Estrutura da tabela `proprietario`
--

CREATE TABLE IF NOT EXISTS `proprietario` (
  `pro_id` int(11) NOT NULL AUTO_INCREMENT,
  `pro_nome` varchar(255) DEFAULT NULL,
  `usuario_usu_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`pro_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Extraindo dados da tabela `proprietario`
--

INSERT INTO `proprietario` (`pro_id`, `pro_nome`, `usuario_usu_id`) VALUES
(1, 'rewrwer', 2);

-- --------------------------------------------------------

--
-- Estrutura da tabela `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
  `usu_id` int(11) NOT NULL AUTO_INCREMENT,
  `usu_nome` varchar(255) DEFAULT NULL,
  `usu_senha` varchar(255) DEFAULT NULL,
  `usu_tipo` int(11) DEFAULT NULL,
  PRIMARY KEY (`usu_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Extraindo dados da tabela `usuario`
--

INSERT INTO `usuario` (`usu_id`, `usu_nome`, `usu_senha`, `usu_tipo`) VALUES
(1, 'brendo10x', 'admin', 1),
(2, 'brendo7x', 'admin', 0);

-- --------------------------------------------------------

--
-- Estrutura da tabela `venda`
--

CREATE TABLE IF NOT EXISTS `venda` (
  `ven_id` int(11) NOT NULL AUTO_INCREMENT,
  `ven_data_venda` date DEFAULT NULL,
  `ven_forma_pagamento` int(11) DEFAULT NULL,
  `ven_total` double DEFAULT NULL,
  `cliente_cli_id` int(11) NOT NULL,
  `vendedor_ven_id` int(11) NOT NULL,
  PRIMARY KEY (`ven_id`),
  UNIQUE KEY `cliente_cli_id` (`cliente_cli_id`),
  UNIQUE KEY `vendedor_ven_id` (`vendedor_ven_id`),
  KEY `FK6AE685C1C884AC7` (`vendedor_ven_id`),
  KEY `FK6AE685C7FA1610C` (`cliente_cli_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Extraindo dados da tabela `venda`
--


-- --------------------------------------------------------

--
-- Estrutura da tabela `vendedor`
--

CREATE TABLE IF NOT EXISTS `vendedor` (
  `ven_id` int(11) NOT NULL AUTO_INCREMENT,
  `usuario_usu_id` int(11) NOT NULL,
  `ven_cpf` varchar(255) NOT NULL,
  `ven_nome` varchar(255) NOT NULL,
  `ven_rg` varchar(255) DEFAULT NULL,
  `endereco_end_id` int(11) NOT NULL,
  PRIMARY KEY (`ven_id`),
  KEY `FK8205C0E7EE030DCB` (`endereco_end_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Extraindo dados da tabela `vendedor`
--

INSERT INTO `vendedor` (`ven_id`, `usuario_usu_id`, `ven_cpf`, `ven_nome`, `ven_rg`, `endereco_end_id`) VALUES
(1, 1, 'asdasdsad', 'asdad', 'edwed', 1);
