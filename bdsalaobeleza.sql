-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 29-Jun-2017 às 09:15
-- Versão do servidor: 10.1.16-MariaDB
-- PHP Version: 7.0.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bdsalaobeleza`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbagendamento`
--

CREATE TABLE `tbagendamento` (
  `codAgendamento` int(11) NOT NULL,
  `dataAgendamento` date NOT NULL,
  `codCliente` int(11) NOT NULL,
  `codHorarioInicio` int(11) NOT NULL,
  `horarioFim` varchar(5) NOT NULL,
  `codServico` int(11) NOT NULL,
  `codFuncionario` int(11) NOT NULL,
  `statusAgendamento` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbagendamento`
--

INSERT INTO `tbagendamento` (`codAgendamento`, `dataAgendamento`, `codCliente`, `codHorarioInicio`, `horarioFim`, `codServico`, `codFuncionario`, `statusAgendamento`) VALUES
(13, '2017-06-27', 21, 13, '11:30', 2, 9, 1),
(14, '2017-06-27', 46, 13, '12:30', 2, 11, 0),
(15, '2017-06-27', 46, 13, '12:30', 2, 11, 1),
(16, '2017-06-29', 46, 9, '12:00', 2, 7, 1),
(17, '2017-06-29', 19, 9, '13:00', 7, 9, 0),
(18, '2017-06-29', 3, 9, '13:00', 7, 9, 1),
(19, '2017-06-29', 19, 9, '14:00', 7, 6, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbcaixa`
--

CREATE TABLE `tbcaixa` (
  `codCaixa` int(11) NOT NULL,
  `statusCaixa` tinyint(1) DEFAULT NULL,
  `valorFinalCaixa` float NOT NULL,
  `valorInicialCaixa` float NOT NULL,
  `dataCaixa` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbcaixa`
--

INSERT INTO `tbcaixa` (`codCaixa`, `statusCaixa`, `valorFinalCaixa`, `valorInicialCaixa`, `dataCaixa`) VALUES
(1, 1, 30, 0, ''),
(2, 0, 20, 10, '16/06/2017'),
(3, 1, 45, 10, '17/06/2017'),
(4, 1, 160, 10, '20/06/2017'),
(5, 1, 12.15, 12.15, '19/06/2017'),
(6, 0, 0, 1, '21/06/2017'),
(7, 0, 0, 12, '24/06/2017'),
(8, 0, 150, 15, '25/06/2017'),
(9, 1, 105, 12, '28/06/2017'),
(10, 0, 15.8, 15.8, '26/06/2017'),
(11, 0, 0, 15, '27/06/2017'),
(12, 0, 0, 29.99, '29/06/2017');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbcargo`
--

CREATE TABLE `tbcargo` (
  `codCargo` int(11) NOT NULL,
  `nomeCargo` varchar(20) NOT NULL,
  `descricaoCargo` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbcartao`
--

CREATE TABLE `tbcartao` (
  `codCartao` int(11) NOT NULL,
  `codTipoCartao` int(11) NOT NULL,
  `codParcela` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbcartao`
--

INSERT INTO `tbcartao` (`codCartao`, `codTipoCartao`, `codParcela`) VALUES
(19, 1, 42),
(20, 1, 43),
(21, 1, 44),
(22, 2, 45),
(23, 1, 49),
(24, 1, 50),
(25, 1, 51),
(26, 2, 52),
(27, 2, 54),
(28, 2, 57),
(29, 1, 60),
(30, 1, 61),
(31, 1, 62),
(32, 2, 63),
(33, 1, 69),
(34, 1, 70),
(35, 1, 71),
(36, 2, 72),
(37, 2, 96);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbcheque`
--

CREATE TABLE `tbcheque` (
  `codCheque` int(11) NOT NULL,
  `nomeBanco` varchar(100) NOT NULL,
  `numeroCheque` varchar(100) NOT NULL,
  `codParcela` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbcheque`
--

INSERT INTO `tbcheque` (`codCheque`, `nomeBanco`, `numeroCheque`, `codParcela`) VALUES
(4, 'teste', '123456', 41),
(5, 'df', 'fg', 53),
(6, '121', '6232', 59),
(7, 'uil', ' m,', 73);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbcliente`
--

CREATE TABLE `tbcliente` (
  `codCliente` int(11) NOT NULL,
  `nomeCliente` varchar(70) NOT NULL,
  `dataNascimentoCliente` varchar(10) DEFAULT NULL,
  `rgCliente` varchar(20) DEFAULT NULL,
  `cpfCliente` varchar(14) DEFAULT NULL,
  `sexoCliente` varchar(1) DEFAULT NULL,
  `logradouro` varchar(30) DEFAULT NULL,
  `cep` varchar(20) DEFAULT NULL,
  `bairro` varchar(100) DEFAULT NULL,
  `cidade` varchar(30) DEFAULT NULL,
  `complemento` varchar(40) DEFAULT NULL,
  `estado` varchar(2) DEFAULT NULL,
  `numCliente` varchar(20) DEFAULT NULL,
  `atividadeCliente` tinyint(1) NOT NULL,
  `emailCliente` varchar(100) DEFAULT NULL,
  `fotoCliente` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbcliente`
--

INSERT INTO `tbcliente` (`codCliente`, `nomeCliente`, `dataNascimentoCliente`, `rgCliente`, `cpfCliente`, `sexoCliente`, `logradouro`, `cep`, `bairro`, `cidade`, `complemento`, `estado`, `numCliente`, `atividadeCliente`, `emailCliente`, `fotoCliente`) VALUES
(3, 'Wagner Santos Pereira', '15/04/2015', '123456789', '480.625.778-80', 'M', 'Abacatuaja', '08190-420', 'Vila Itaim', 'São Paulo', 'Ap', 'SP', '919', 1, 'wagner_pereira15@hotmail.com', '-----------'),
(4, 'Luan Lucas B.', '17/03/1997', '1234214235', '466.278.268-60', 'M', 'Rua Serra da auvorada', '08230-401', 'Tiradentes', 'São Paulo', '', 'SP', '174', 1, '', '-----------'),
(10, 'wagnot', '__/__/____', '', '___.___.___-__', '', '', '_____-___', '', '', '', '', '', 0, '', 'C:\\Royal\\src\\br\\com\\royal\\imagensClientes\\wagnot1.jpg'),
(19, 'static', '__/__/____', '', '___.___.___-__', '', '', '_____-___', '', '', '', '', '', 1, '', 'C:\\Royal\\src\\br\\com\\royal\\imagensClientes\\static.jpg'),
(20, 'Mariane Santana', '12/03/1999', '395095773', '470.532.818-33', 'F', '', '_____-___', '', '', '', '', '', 1, 'diuhsdaiusd@dsaygasd.com', 'C:\\Royal\\src\\br\\com\\royal\\imagensClientes\\Mariane Santana.jpg'),
(21, 'Aline', '__/__/____', '', '___.___.___-__', '', '', '_____-___', '', '', '', '', '', 1, '', 'C:\\Royal\\src\\br\\com\\royal\\imagensClientes\\Aline.jpg'),
(22, 'Mongols', '__/__/____', '', '___.___.___-__', '', '', '_____-___', '', '', '', '', '', 0, '', 'C:\\Royal\\src\\br\\com\\royal\\imagensClientes\\Mongols.jpg'),
(44, 'wagnot', '__/__/____', '', '___.___.___-__', '', '', '_____-___', '', '', '', '', '', 0, '', 'C:\\Royal\\src\\br\\com\\royal\\imagensClientes\\wagnot.jpg'),
(46, 'Lilian', '__/__/____', '', '___.___.___-__', '', '', '_____-___', '', '', '', '', '', 1, '', 'C:\\Royal\\src\\br\\com\\royal\\imagensClientes\\Lilian.jpg'),
(47, 'Marlene ', '__/__/____', '', '___.___.___-__', '', '', '_____-___', '', '', '', '', '', 1, '', 'C:\\Royal\\src\\br\\com\\royal\\imagensClientes\\Marlene .jpg'),
(48, 'wagner', '__/__/____', '', '___.___.___-__', '', '', '_____-___', '', '', '', '', '', 0, '', 'C:\\Royal\\src\\br\\com\\royal\\imagensClientes\\wagner.jpg'),
(49, 'wagner', '__/__/____', '', '___.___.___-__', '', '', '_____-___', '', '', '', '', '', 0, '', 'C:\\Royal\\src\\br\\com\\royal\\imagensClientes\\wagner1.jpg'),
(50, 'wagner', '__/__/____', '', '___.___.___-__', '', '', '_____-___', '', '', '', '', '', 0, '', 'C:\\Royal\\src\\br\\com\\royal\\imagensClientes\\wagner2.jpg'),
(54, 'aro', '__/__/____', '', '___.___.___-__', '', '', '_____-___', '', '', '', '', '', 0, '', 'C:\\Royal\\src\\br\\com\\royal\\imagensClientes\\Luiz-Gonzaga1.jpg'),
(64, 'aleatorio', '__/__/____', '', '___.___.___-__', '', '', '_____-___', '', '', '', '', '', 0, '', 'C:\\Royal\\src\\br\\com\\royal\\imagensClientes\\aleatorio.jpg'),
(65, 'aleatorio1', '__/__/____', '', '___.___.___-__', '', '', '_____-___', '', '', '', '', '', 0, '', 'C:\\Royal\\src\\br\\com\\royal\\imagensClientes\\aleatorio1.jpg'),
(66, 'aleatorio2', '__/__/____', '', '___.___.___-__', '', '', '_____-___', '', '', '', '', '', 0, '', 'C:\\Royal\\src\\br\\com\\royal\\imagensClientes\\aleatorio2.jpg'),
(67, 'sdadsad', '__/__/____', '', '___.___.___-__', '', '', '_____-___', '', '', '', '', '', 0, '', 'C:\\Royal\\src\\br\\com\\royal\\imagensClientes\\sdadsad1.jpg'),
(68, 'Irineu', '03/08/1998', '50.925.053-x', '272.948.658-50', 'M', 'Feliciano de Mendonça', '08460-365', 'Jardim São Paulo', 'São Paulo', 'Etec', 'SP', '290', 0, 'teste@teste,uol.br', 'C:\\Royal\\src\\br\\com\\royal\\imagensClientes\\Irineu.jpg'),
(69, 'Tico e teco', '__/__/____', '', '___.___.___-__', '', '', '_____-___', '', '', '', '', '', 0, '', 'C:\\Royal\\src\\br\\com\\royal\\imagensClientes\\Tico e teco.jpg');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbcomandaproduto`
--

CREATE TABLE `tbcomandaproduto` (
  `codComandaProduto` int(11) NOT NULL,
  `valorTotalComandaProduto` float NOT NULL,
  `dataComandaProduto` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbcomandaproduto`
--

INSERT INTO `tbcomandaproduto` (`codComandaProduto`, `valorTotalComandaProduto`, `dataComandaProduto`) VALUES
(19, 333.3, '20/06/2017'),
(20, 0, '20/06/2017'),
(21, 499.95, '20/06/2017'),
(22, 0, '20/06/2017'),
(23, 0, '20/06/2017'),
(24, 0, '20/06/2017'),
(25, 0, '20/06/2017'),
(26, 2222, '28/06/2017 17:00:55'),
(27, 2222, '28/06/2017 17:00:55'),
(28, 2477.53, '28/06/2017 17:05:08'),
(29, 2222, '28/06/2017 18:09:15'),
(30, 133.32, '28/06/2017 18:11:05'),
(31, 1233.21, '28/06/2017 18:14:50'),
(32, 133.32, '28/06/2017 18:19:19'),
(33, 11.11, '28/06/2017 18:21:02'),
(34, 11.11, '28/06/2017 18:29:31'),
(35, 1366.53, '28/06/2017 18:31:23'),
(36, 11.11, '28/06/2017 18:36:49'),
(37, 11.11, '28/06/2017 18:38:18'),
(38, 222.2, '28/06/2017 18:48:19'),
(39, 111.1, '28/06/2017 18:51:22'),
(40, 11.11, '28/06/2017 18:54:51'),
(41, 122.21, '28/06/2017 18:56:32'),
(42, 22.22, '28/06/2017 18:57:44'),
(43, 22.22, '28/06/2017 19:02:43'),
(44, 11.11, '28/06/2017 19:06:50'),
(45, 580, '29/06/2017 02:00:42'),
(46, 0, '29/06/2017 02:25:45'),
(47, 0, '29/06/2017 03:14:43'),
(48, 40, '29/06/2017 04:03:48');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbcomandaservico`
--

CREATE TABLE `tbcomandaservico` (
  `codComandaServico` int(11) NOT NULL,
  `valorTotalVendaServico` double NOT NULL,
  `dataComandaServico` varchar(20) NOT NULL,
  `observacao` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbcomandaservico`
--

INSERT INTO `tbcomandaservico` (`codComandaServico`, `valorTotalVendaServico`, `dataComandaServico`, `observacao`) VALUES
(15, 60, '20/06/2017', 'thesjkthasild'),
(16, 10, '20/06/2017', ''),
(17, 50, '20/06/2017', '	tf'),
(18, 20, '20/06/2017', ''),
(19, 500, '20/06/2017', ''),
(20, 10, '20/06/2017', ''),
(21, 500, '20/06/2017', ''),
(22, 80, '28/06/2017 17:00:55', 'Sou rica'),
(23, 80, '28/06/2017 17:00:55', 'Sou rica'),
(24, 80, '28/06/2017 17:05:08', 'SOU RICA'),
(25, 80, '28/06/2017 18:09:15', 'asdcsadasdsadsad'),
(26, 80, '28/06/2017 18:11:05', 'sadsadasd'),
(27, 80, '28/06/2017 18:14:50', 'sdadasd'),
(28, 80, '28/06/2017 18:19:19', '1sdadsad'),
(29, 80, '28/06/2017 18:21:02', 'asdad'),
(30, 80, '28/06/2017 18:29:31', 'sdsdsadasd'),
(31, 80, '28/06/2017 18:31:23', 'sadsad'),
(32, 30.05, '28/06/2017 18:36:49', 'sadadsadasdasd'),
(33, 80, '28/06/2017 18:38:18', 'sadadsadasdc'),
(34, 20, '28/06/2017 18:48:19', 'Sou muito delicioso'),
(35, 80, '28/06/2017 18:51:22', 'Jooj'),
(36, 80, '28/06/2017 18:54:51', 'sdadsada'),
(37, 30.05, '28/06/2017 18:56:32', 'sdadsadsadsadad'),
(38, 80, '28/06/2017 18:57:44', 'sdadsadasd'),
(39, 80, '28/06/2017 19:02:43', 'asdsakdsajd'),
(40, 80, '28/06/2017 19:06:50', 'sdadsadsad'),
(41, 0, '29/06/2017 02:00:42', ''),
(42, 30.05, '29/06/2017 02:25:45', ''),
(43, 16, '29/06/2017 03:14:43', ''),
(44, 16, '29/06/2017 04:03:48', '');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbentradacaixa`
--

CREATE TABLE `tbentradacaixa` (
  `codEntradaCaixa` int(11) NOT NULL,
  `valorEntradaCaixa` float NOT NULL,
  `dataEntradaCaixa` varchar(20) NOT NULL,
  `codMotivoEntradaCaixa` int(11) NOT NULL,
  `statusEntradaCaixa` tinyint(1) NOT NULL,
  `codFormaDePagamento` int(11) NOT NULL,
  `codCaixa` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbentradacaixa`
--

INSERT INTO `tbentradacaixa` (`codEntradaCaixa`, `valorEntradaCaixa`, `dataEntradaCaixa`, `codMotivoEntradaCaixa`, `statusEntradaCaixa`, `codFormaDePagamento`, `codCaixa`) VALUES
(14, 100, '20/12/2017', 1, 1, 1, 0),
(15, 33.3333, '20/12/2017', 1, 1, 1, 0),
(16, 100, '20/12/2017', 1, 1, 1, 0),
(17, 10, '20/06/2017', 3, 1, 0, 0),
(18, 33.3333, '21/06/2017', 1, 1, 1, 0),
(19, 100, '21/06/2017', 1, 1, 1, 0),
(20, 100, '21/06/2017', 1, 1, 1, 0),
(21, 100, '20/06/2017', 1, 1, 3, 0),
(22, 12.15, '19/06/2017', 1, 1, 3, 0),
(23, 1, '21/06/2017', 1, 1, 3, 0),
(24, 12, '24/06/2017', 1, 1, 3, 0),
(25, 12.35, '24/06/2017', 1, 1, 3, 0),
(26, 13.54, '24/06/2017', 1, 1, 3, 0),
(27, 15, '25/06/2017', 1, 1, 3, 0),
(28, 10, '25/06/2017', 4, 1, 0, 8),
(29, 10, '25/06/2017', 1, 1, 3, 8),
(30, 5, '25/06/2017', 4, 1, 0, 8),
(31, 5, '25/06/2017', 1, 1, 3, 8),
(32, 0, '25/06/2017', 1, 1, 3, 8),
(33, 0, '25/06/2017', 1, 1, 3, 8),
(34, 13, '25/06/2017', 1, 1, 3, 8),
(35, 12.2, '25/06/2017', 1, 1, 3, 8),
(36, 20, '25/06/2017', 4, 1, 0, 8),
(37, 20, '25/06/2017', 1, 1, 3, 8),
(38, 2, '25/06/2017', 4, 1, 0, 8),
(39, 2, '25/06/2017', 1, 1, 3, 8),
(40, 17, '25/06/2017', 4, 1, 0, 8),
(41, 10, '25/06/2017', 4, 1, 0, 8),
(42, 20, '25/06/2017 21:31:34', 4, 1, 0, 8),
(43, 200, '25/06/2017 23:26:31', 4, 1, 0, 8),
(44, 12, '28/06/2017 15:45:08', 3, 1, 3, 9),
(45, 15, '28/06/2017 15:56:46', 4, 1, 0, 9),
(46, 5, '28/06/2017 15:59:18', 4, 1, 0, 9),
(47, 15, '28/06/2017 15:59:18', 4, 1, 0, 9),
(48, 15, '28/06/2017 16:00:51', 4, 1, 0, 9),
(49, 668.53, '28/06/2017 17:05:08', 4, 1, 3, 9),
(50, 2302, '28/06/2017 18:09:15', 4, 1, 3, 9),
(51, 213.32, '28/06/2017 18:11:05', 4, 1, 3, 9),
(52, 1313.21, '28/06/2017 18:14:50', 4, 1, 3, 9),
(53, 213.32, '28/06/2017 18:19:19', 4, 1, 3, 9),
(54, 91.11, '28/06/2017 18:21:02', 4, 1, 3, 9),
(55, 91.11, '28/06/2017 18:29:31', 4, 1, 3, 9),
(56, 1446.53, '28/06/2017 18:31:23', 4, 1, 3, 9),
(57, 41.16, '28/06/2017 18:36:49', 4, 1, 3, 9),
(58, 91.11, '28/06/2017 18:38:18', 4, 1, 3, 9),
(59, 227.08, '28/06/2017 18:48:19', 4, 1, 3, 9),
(60, 1, '28/06/2017 18:51:22', 4, 1, 3, 9),
(61, 1.11, '28/06/2017 18:54:51', 4, 1, 3, 9),
(62, 142.26, '28/06/2017 18:56:32', 4, 1, 3, 9),
(63, 2.22, '28/06/2017 18:57:44', 4, 1, 3, 9),
(64, 2.22, '28/06/2017 19:02:43', 4, 1, 3, 9),
(65, 1.11, '28/06/2017 19:06:50', 4, 1, 3, 9),
(66, 15.8, '28/06/2017 22:27:39', 4, 1, 3, 9),
(67, 15, '28/06/2017 22:28:03', 4, 1, 3, 9),
(68, 17, '28/06/2017 22:28:50', 4, 1, 3, 9),
(69, 17.5, '28/06/2017 22:31:59', 4, 1, 3, 9),
(70, 15, '28/06/2017 23:09:17', 4, 1, 3, 9),
(71, 15, '28/06/2017 23:12:07', 4, 1, 3, 9),
(72, 15, '28/06/2017 23:12:21', 4, 1, 3, 9),
(73, 15, '28/06/2017 23:15:11', 4, 1, 3, 9),
(74, 15, '28/06/2017 23:17:56', 4, 1, 3, 9),
(75, 15, '28/06/2017 23:19:36', 4, 1, 3, 9),
(76, 15, '28/06/2017 23:27:30', 4, 1, 3, 9),
(77, 29.99, '29/06/2017 01:22:00', 3, 1, 3, 12),
(78, 25.61, '29/06/2017 02:25:45', 4, 1, 3, 12),
(79, 16, '29/06/2017 03:14:43', 4, 1, 3, 12),
(80, 56, '29/06/2017 04:03:48', 1, 1, 1, 12),
(81, 0, '29/06/2017 04:03:48', 4, 1, 3, 12),
(82, 40, '29/06/2017 04:04:55', 4, 1, 3, 12);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbentradaproduto`
--

CREATE TABLE `tbentradaproduto` (
  `codEntradaProduto` int(11) NOT NULL,
  `quantidadeEntradaProduto` smallint(6) NOT NULL,
  `dataEntradaProduto` varchar(10) NOT NULL,
  `codMotivoEntradaProduto` int(11) NOT NULL,
  `loteProduto` varchar(30) DEFAULT NULL,
  `dataValidadeLote` varchar(20) DEFAULT NULL,
  `codFornecedor` int(11) NOT NULL,
  `codProduto` int(11) NOT NULL,
  `atividadeEntrada` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbentradaproduto`
--

INSERT INTO `tbentradaproduto` (`codEntradaProduto`, `quantidadeEntradaProduto`, `dataEntradaProduto`, `codMotivoEntradaProduto`, `loteProduto`, `dataValidadeLote`, `codFornecedor`, `codProduto`, `atividadeEntrada`) VALUES
(36, 12121, '12/12/1212', 0, '1212', '12/12/1212', 0, 0, 0),
(37, 1231, '12/12/1212', 11, '123132', '12/12/1212', 0, 0, 0),
(38, 12, '11/11/1111', 0, '123', '11/11/1111', 0, 0, 0),
(39, 12, '11/11/1111', 0, '12', '11/11/1111', 0, 0, 0),
(40, 121, '12/12/1212', 16, '2121', '12/12/1212', 0, 0, 0),
(41, 1212, '12/12/1212', 11, '1212', '12/12/1212', 0, 0, 0),
(42, 123, '11/11/1111', 11, '123', '11/11/1111', 0, 0, 0),
(43, 1212, '12/12/1212', 19, '21212', '12/12/1212', 0, 0, 0),
(44, 12313, '11/11/1111', 12, '1231', '11/11/1111', 0, 0, 0),
(45, 1321, '11/11/1111', 12, '122123', '11/11/1111', 0, 0, 0),
(46, 2123, '12/12/3123', 12, '123', '54/56/4564', 0, 0, 0),
(47, 1231, '13/13/2000', 13, '12123', '12/12/1212', 0, 0, 0),
(48, 112, '12/31/3213', 13, '2323', '11/32/1231', 0, 0, 0),
(49, 2121, '11/11/1111', 13, 'as', '11/11/1111', 0, 0, 0),
(50, 231, '11/11/1111', 12, '12313', '11/11/1111', 0, 0, 0),
(51, 123, '11/11/1111', 12, '1231', '22/22/2222', 0, 0, 0),
(52, 123, '11/11/1111', 12, '123', '11/11/1111', 0, 0, 0),
(53, 312, '11/11/1111', 12, 'adas', '11/11/1111', 0, 0, 0),
(54, 32123, '11/11/1111', 12, '123', '11/11/1111', 0, 0, 0),
(55, 232, '11/11/1111', 12, 'asmska', '11/11/1111', 0, 0, 0),
(56, 2123, '11/11/1111', 12, 'asasa', '11/11/1111', 0, 0, 0),
(57, 1231, '11/11/1111', 12, 'aksoa', '11/11/1111', 0, 0, 0),
(58, 123, '11/11/1111', 12, '123', '11/11/1111', 0, 0, 0),
(59, 5, '20/01/2017', 19, 'jfwajfeaw', '20/05/2017', 0, 0, 0),
(60, 1231, '11/11/1111', 11, 'dsada', '11/11/1111', 0, 0, 0),
(61, 30, '12/02/2014', 18, '85p#ff7', '18/02/2014', 0, 0, 0),
(62, 333, '21/04/2017', 11, '33085KI', '22/04/2017', 30, 88, 0),
(63, 13, '13/04/2017', 11, '98FJE#*', '15/07/2018', 32, 89, 0),
(64, 12, '13/08/2014', 11, '*$h#', '12/08/2015', 31, 101, 0),
(65, 22, '12/02/2012', 13, 'sadsad', '13/08/2019', 30, 100, 0),
(66, 30, '11/11/1111', 16, 'dealk', '11/11/1112', 34, 113, 1),
(67, 20, '13/04/2017', 21, '123', '15/12/2017', 33, 117, 1),
(68, 40, '27/05/2017', 11, '32', '28/05/2018', 34, 118, 1),
(69, 30, '01/06/2017', 11, '15ax', '05/06/2018', 36, 122, 1),
(70, 20, '11/11/1111', 11, '123', '11/11/1111', 34, 201, 1),
(71, 999, '20/06/2017', 11, 'fsfew', '20/07/2017', 34, 201, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbestoque`
--

CREATE TABLE `tbestoque` (
  `codEstoque` int(11) NOT NULL,
  `codProduto` int(11) NOT NULL,
  `quantidadeEstoqueProduto` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbestoque`
--

INSERT INTO `tbestoque` (`codEstoque`, `codProduto`, `quantidadeEstoqueProduto`) VALUES
(1, 113, 30),
(2, 117, 20),
(3, 105, 0),
(4, 118, 40),
(5, 122, 30),
(6, 135, 0),
(7, 201, 90182),
(8, 203, 0);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbfabricante`
--

CREATE TABLE `tbfabricante` (
  `codFabricante` int(11) NOT NULL,
  `nomeFabricante` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbfabricante`
--

INSERT INTO `tbfabricante` (`codFabricante`, `nomeFabricante`) VALUES
(1, 'Garnier'),
(2, 'Dove'),
(4, 'Tresemme'),
(5, 'Seda'),
(6, 'Clear man'),
(7, 'loreal'),
(8, 'Jequiti'),
(9, 'Boticario'),
(10, 'Avon'),
(11, 'Monange'),
(12, 'Florus'),
(13, 'Collie'),
(14, 'Sweet Hair'),
(15, 'Natura');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbfiado`
--

CREATE TABLE `tbfiado` (
  `codFiado` int(11) NOT NULL,
  `dataAbate` varchar(10) NOT NULL,
  `codParcela` int(11) NOT NULL,
  `statusFiado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbfiado`
--

INSERT INTO `tbfiado` (`codFiado`, `dataAbate`, `codParcela`, `statusFiado`) VALUES
(4, '21/06/2017', 46, 0),
(5, '22/06/2017', 55, 0),
(6, '20/06/2018', 64, 0),
(7, '01/86/4540', 65, 0),
(8, '20/12/2017', 66, 0),
(9, '21/06/2017', 74, 0),
(10, '16/03/2020', 93, 0);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbformadepagamento`
--

CREATE TABLE `tbformadepagamento` (
  `codFormaDePagamento` int(11) NOT NULL,
  `descricaoFormaDePagamento` varchar(50) NOT NULL,
  `statusFormaDePagamento` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbformadepagamento`
--

INSERT INTO `tbformadepagamento` (`codFormaDePagamento`, `descricaoFormaDePagamento`, `statusFormaDePagamento`) VALUES
(1, 'Cheque', 1),
(2, 'Cartão', 1),
(3, 'Dinheiro', 1),
(4, 'Fiado', 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbfornecedor`
--

CREATE TABLE `tbfornecedor` (
  `codFornecedor` int(11) NOT NULL,
  `nomeFornecedor` varchar(30) NOT NULL,
  `contatoFornecedor` varchar(100) NOT NULL,
  `cnpjFornecedor` varchar(18) DEFAULT NULL,
  `cpfFornecedor` varchar(14) NOT NULL,
  `atividadeFornecedor` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbfornecedor`
--

INSERT INTO `tbfornecedor` (`codFornecedor`, `nomeFornecedor`, `contatoFornecedor`, `cnpjFornecedor`, `cpfFornecedor`, `atividadeFornecedor`) VALUES
(30, 'marcus', 'asasa', '------------------', '467.014.388-30', 0),
(31, 'Wagner', 'Wagner Santos', '------------------', '480.625.778-80', 0),
(32, 'Poderosíssimo Ninja', 'Nunca Mexa', '------------------', '466.278.268-60', 0),
(33, 'Jamelão', 'Sua mãe', '------------------', '466.278.268-60', 0),
(34, 'Luxus Int.', 'Cleiton', '12.352.136/5216-20', '--------------', 1),
(35, 'Fernando', 'Fernando', '------------------', '480.625.778-80', 1),
(36, 'Marcus', 'Telefone', '06.226.921/0001-53', '--------------', 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbfuncionario`
--

CREATE TABLE `tbfuncionario` (
  `codFuncionario` int(11) NOT NULL,
  `nomeFuncionario` varchar(80) NOT NULL,
  `rgFuncionario` varchar(20) DEFAULT NULL,
  `cpfFuncionario` varchar(14) NOT NULL,
  `sexoFuncionario` varchar(1) DEFAULT NULL,
  `dataNascimentoFuncionario` varchar(10) NOT NULL,
  `dataAdmissao` varchar(10) NOT NULL,
  `salarioFuncionario` float DEFAULT NULL,
  `logradouro` varchar(30) NOT NULL,
  `cep` varchar(20) NOT NULL,
  `bairro` varchar(100) NOT NULL,
  `cidade` varchar(30) NOT NULL,
  `complemento` varchar(50) DEFAULT NULL,
  `estado` varchar(2) NOT NULL,
  `numFuncionario` varchar(20) NOT NULL,
  `atividadeFuncionario` tinyint(1) NOT NULL,
  `emailFuncionario` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbfuncionario`
--

INSERT INTO `tbfuncionario` (`codFuncionario`, `nomeFuncionario`, `rgFuncionario`, `cpfFuncionario`, `sexoFuncionario`, `dataNascimentoFuncionario`, `dataAdmissao`, `salarioFuncionario`, `logradouro`, `cep`, `bairro`, `cidade`, `complemento`, `estado`, `numFuncionario`, `atividadeFuncionario`, `emailFuncionario`) VALUES
(4, 'Wagner', '2141279664', '480.625.778-80', 'M', '15/05/2000', '02/03/2015', 123.23, 'ruafaj', '23214-012', '21491209', 'weifoei', NULL, 'SP', 'jkljl', 0, ''),
(5, 'efwiyf', '12345678', '467.014.388-30', 'M', '15/05/2000', '15/02/2015', 15.6, 'khfkjs', '25387-523', 'hfdskjh', 'gdjk', NULL, 'AC', 'lyfewsy', 0, NULL),
(6, 'Wagner Santos Pereira ', '8978787878z', '850.585.999-53', '', '12/02/1998', '13/08/2014', 54, 'kjskjsdkjsdkjsdkjsd', '09293-218', '123123', '123213', '', 'AL', '333', 1, ''),
(7, 'Adm', '273288789', '480.625.778-80', 'M', '15/05/2000', '10/10/2010', 54, 'sdfsdgs', '32222-222', 'sd', 'dfd', 'casa', 'AC', 'dsf', 1, 'wagner_pereira15@hotmail.com'),
(8, 'Ja vou', '1321324546', '182.065.974-76', '', '15/05/2000', '18/04/2017', 0, 'Abacatuaja', '08190-420', 'Vila Itaim', 'São Paulo', NULL, 'SP', '919', 0, ''),
(9, 'Luan Lucas Brevinski', '509250531', '466.278.268-60', 'M', '03/08/1998', '22/03/2014', 3390.8, 'Igarapé Água Azul', '08485-310', 'Conjunto Habitacional Santa Etelvina II', 'São Paulo', 'BL 01 APTO 81', 'SP', '1360', 1, 'luanlucas10@gmail.com'),
(10, 'Jooj Joperson', '', '470.532.818-33', '', '20/03/2000', '18/08/2012', 0, 'Igarapé Água Azul', '08485-310', 'Conjunto Habitacional Santa Etelvina II', 'São Paulo', '', 'SP', '123', 0, ''),
(11, 'Marlene', '', '251.461.408-20', '', '03/08/1998', '01/10/2010', 0, 'Igarapé Água Azul', '08485-310', 'Conjunto Habitacional Santa Etelvina II', 'São Paulo', '', 'SP', '123', 1, '');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbhorario`
--

CREATE TABLE `tbhorario` (
  `codHorario` int(11) NOT NULL,
  `horaInicio` char(5) NOT NULL,
  `dataHorario` varchar(10) NOT NULL,
  `statusHorario` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbhorario`
--

INSERT INTO `tbhorario` (`codHorario`, `horaInicio`, `dataHorario`, `statusHorario`) VALUES
(8, '10:00', 'TODAS', 1),
(9, '11:00', 'TODAS', 1),
(10, '12:00', 'TODAS', 1),
(11, '13:00', 'TODAS', 1),
(12, '14:00', 'TODAS', 1),
(13, '09:30', '27/06/2017', 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbitensagendamento`
--

CREATE TABLE `tbitensagendamento` (
  `codItensAgendamento` int(11) NOT NULL,
  `codAgendamento` int(11) NOT NULL,
  `codServico` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbitenscomandaproduto`
--

CREATE TABLE `tbitenscomandaproduto` (
  `codItensComandaProduto` int(11) NOT NULL,
  `codComandaProduto` int(11) NOT NULL,
  `codProduto` int(11) NOT NULL,
  `subTotalComandaProduto` float NOT NULL,
  `quantidadeItensComandaProduto` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbitenscomandaproduto`
--

INSERT INTO `tbitenscomandaproduto` (`codItensComandaProduto`, `codComandaProduto`, `codProduto`, `subTotalComandaProduto`, `quantidadeItensComandaProduto`) VALUES
(13, 19, 201, 333.3, 30),
(14, 21, 201, 499.95, 45),
(15, 0, 201, 2222, 200),
(16, 26, 201, 2222, 200),
(17, 27, 201, 2222, 200),
(18, 28, 201, 2477.53, 223),
(19, 29, 201, 2222, 200),
(20, 30, 201, 133.32, 12),
(21, 31, 201, 1233.21, 111),
(22, 32, 201, 133.32, 12),
(23, 33, 201, 11.11, 1),
(24, 34, 201, 11.11, 1),
(25, 35, 201, 1366.53, 123),
(26, 36, 201, 11.11, 1),
(27, 37, 201, 11.11, 1),
(28, 38, 201, 222.2, 20),
(29, 39, 201, 111.1, 10),
(30, 40, 201, 11.11, 1),
(31, 41, 201, 122.21, 11),
(32, 42, 201, 22.22, 2),
(33, 43, 201, 22.22, 2),
(34, 44, 201, 11.11, 1),
(35, 45, 201, 580, 29),
(36, 48, 201, 40, 2);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbitenscomandaservico`
--

CREATE TABLE `tbitenscomandaservico` (
  `codItensComandaServico` int(11) NOT NULL,
  `codComandaServico` int(11) NOT NULL,
  `codServico` int(11) NOT NULL,
  `precoServico` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbitenscomandaservico`
--

INSERT INTO `tbitenscomandaservico` (`codItensComandaServico`, `codComandaServico`, `codServico`, `precoServico`) VALUES
(20, 15, 1, 10),
(21, 15, 2, 50),
(22, 16, 1, 10),
(23, 17, 2, 50),
(24, 18, 2, 20),
(25, 19, 2, 500),
(26, 20, 2, 10),
(27, 21, 7, 500),
(28, 0, 2, 80),
(29, 22, 2, 80),
(30, 23, 2, 80),
(31, 24, 2, 80),
(32, 25, 2, 80),
(33, 26, 2, 80),
(34, 27, 2, 80),
(35, 28, 2, 80),
(36, 29, 2, 80),
(37, 30, 2, 80),
(38, 31, 2, 80),
(39, 32, 1, 30.05),
(40, 33, 2, 80),
(41, 34, 9, 20),
(42, 35, 2, 80),
(43, 36, 2, 80),
(44, 37, 1, 30.05),
(45, 38, 2, 80),
(46, 39, 2, 80),
(47, 40, 2, 80),
(48, 42, 1, 30.05),
(49, 43, 1, 16),
(50, 44, 1, 16);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbitenspedidofornecedor`
--

CREATE TABLE `tbitenspedidofornecedor` (
  `codItensPedidoFornecedor` int(11) NOT NULL,
  `codPedidoFornecedor` int(11) NOT NULL,
  `codProduto` int(11) NOT NULL,
  `subTotalItensPedidoFornecedor` double NOT NULL,
  `quantidadeItensPedidoFornecedor` smallint(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbitensvendaproduto`
--

CREATE TABLE `tbitensvendaproduto` (
  `codItensVendaProduto` int(11) NOT NULL,
  `codVendaProduto` int(11) NOT NULL,
  `codProduto` int(11) NOT NULL,
  `quantidadeItensVendaProduto` int(11) NOT NULL,
  `subTotalItensVenda` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbitensvendaproduto`
--

INSERT INTO `tbitensvendaproduto` (`codItensVendaProduto`, `codVendaProduto`, `codProduto`, `quantidadeItensVendaProduto`, `subTotalItensVenda`) VALUES
(12, 15, 201, 30, 333.3),
(13, 17, 201, 45, 499.95),
(14, 22, 201, 223, 2477.53),
(15, 23, 201, 200, 2222),
(16, 24, 201, 12, 133.32),
(17, 25, 201, 111, 1233.21),
(18, 26, 201, 12, 133.32),
(19, 27, 201, 1, 11.11),
(20, 28, 201, 1, 11.11),
(21, 29, 201, 123, 1366.53),
(22, 30, 201, 1, 11.11),
(23, 31, 201, 1, 11.11),
(24, 32, 201, 20, 222.2),
(25, 33, 201, 10, 111.1),
(26, 34, 201, 1, 11.11),
(27, 35, 201, 11, 122.21),
(28, 36, 201, 2, 22.22),
(29, 37, 201, 2, 22.22),
(30, 38, 201, 1, 11.11),
(31, 41, 201, 2, 40);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbitensvendaservico`
--

CREATE TABLE `tbitensvendaservico` (
  `codItensVendaServico` int(11) NOT NULL,
  `codVendaServico` int(11) NOT NULL,
  `codServico` int(11) NOT NULL,
  `precoServico` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbitensvendaservico`
--

INSERT INTO `tbitensvendaservico` (`codItensVendaServico`, `codVendaServico`, `codServico`, `precoServico`) VALUES
(13, 15, 1, 10),
(14, 15, 2, 50),
(15, 16, 1, 10),
(16, 17, 2, 50),
(17, 18, 2, 20),
(18, 19, 2, 500),
(19, 20, 2, 10),
(20, 21, 7, 500),
(21, 22, 2, 80),
(22, 23, 2, 80),
(23, 24, 2, 80),
(24, 25, 2, 80),
(25, 26, 2, 80),
(26, 27, 2, 80),
(27, 28, 2, 80),
(28, 29, 2, 80),
(29, 30, 1, 30.05),
(30, 31, 2, 80),
(31, 32, 9, 20),
(32, 33, 2, 80),
(33, 34, 2, 80),
(34, 35, 1, 30.05),
(35, 36, 2, 80),
(36, 37, 2, 80),
(37, 38, 2, 80),
(38, 39, 1, 30.05),
(39, 40, 1, 16),
(40, 41, 1, 16);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbmotivoentradacaixa`
--

CREATE TABLE `tbmotivoentradacaixa` (
  `codMotivoEntradaCaixa` int(11) NOT NULL,
  `descricaoMotivoEntradaCaixa` varchar(30) NOT NULL,
  `statusMotivoEntradaCaixa` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbmotivoentradacaixa`
--

INSERT INTO `tbmotivoentradacaixa` (`codMotivoEntradaCaixa`, `descricaoMotivoEntradaCaixa`, `statusMotivoEntradaCaixa`) VALUES
(1, 'Venda', 1),
(2, 'Serviço concluido', 1),
(3, 'Abrir caixa', 1),
(4, 'Atualizar caixa', 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbmotivoentradaproduto`
--

CREATE TABLE `tbmotivoentradaproduto` (
  `codMotivoEntradaProduto` int(11) NOT NULL,
  `descricaoEntradaProduto` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbmotivoentradaproduto`
--

INSERT INTO `tbmotivoentradaproduto` (`codMotivoEntradaProduto`, `descricaoEntradaProduto`) VALUES
(11, 'Compra'),
(12, 'Devolução');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbmotivosaidacaixa`
--

CREATE TABLE `tbmotivosaidacaixa` (
  `codMotivoSaidaCaixa` int(11) NOT NULL,
  `descricaoMotivoSaidaCaixa` varchar(30) NOT NULL,
  `statusMotivoSaidaCaixa` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbmotivosaidacaixa`
--

INSERT INTO `tbmotivosaidacaixa` (`codMotivoSaidaCaixa`, `descricaoMotivoSaidaCaixa`, `statusMotivoSaidaCaixa`) VALUES
(1, 'Troco', 1),
(2, 'Pagamento Funcionários', 1),
(3, 'Fechar caixa', 1),
(4, 'Atualizar caixa', 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbmotivosaidaproduto`
--

CREATE TABLE `tbmotivosaidaproduto` (
  `codMotivoSaidaProduto` int(11) NOT NULL,
  `descricaoMotivoSaidaProduto` varchar(80) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbmotivosaidaproduto`
--

INSERT INTO `tbmotivosaidaproduto` (`codMotivoSaidaProduto`, `descricaoMotivoSaidaProduto`) VALUES
(1, 'Venda');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbpagamento`
--

CREATE TABLE `tbpagamento` (
  `codPagamento` int(11) NOT NULL,
  `codComandaProduto` int(11) NOT NULL,
  `codComandaServico` int(11) NOT NULL,
  `totalPagamento` float NOT NULL,
  `codCliente` int(11) NOT NULL,
  `desconto` float NOT NULL,
  `dataPagamento` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbpagamento`
--

INSERT INTO `tbpagamento` (`codPagamento`, `codComandaProduto`, `codComandaServico`, `totalPagamento`, `codCliente`, `desconto`, `dataPagamento`) VALUES
(14, 19, 15, 393.3, 66, 0, '21/06/2017'),
(15, 20, 16, 10, 66, 0, '20/06/2017'),
(16, 21, 17, 549.95, 66, 0, '22/06/2017'),
(17, 22, 18, 20, 64, 0, '20/06/2017'),
(18, 23, 19, 500, 66, 0, '20/12/2017'),
(19, 24, 20, 10, 64, 0, '20/06/2017'),
(20, 25, 21, 500, 66, 0, '21/06/2017'),
(21, 26, 22, 3413, 68, 0, '28/06/2017 17:00:55'),
(22, 27, 23, 3413, 68, 0, '28/06/2017 17:00:55'),
(23, 28, 24, -2331.47, 68, 3000, '28/06/2017 17:05:08'),
(24, 29, 25, 2302, 21, 0, '28/06/2017 18:09:15'),
(25, 30, 26, 213.32, 46, 0, '28/06/2017 18:11:05'),
(26, 31, 27, 1313.21, 68, 0, '28/06/2017 18:14:50'),
(27, 32, 28, 213.32, 21, 0, '28/06/2017 18:19:19'),
(28, 33, 29, 91.11, 21, 0, '28/06/2017 18:21:02'),
(29, 34, 30, 91.11, 50, 0, '28/06/2017 18:29:31'),
(30, 35, 31, 1446.53, 68, 0, '28/06/2017 18:31:23'),
(31, 36, 32, 41.16, 68, 0, '28/06/2017 18:36:49'),
(32, 37, 33, 91.11, 21, 0, '28/06/2017 18:38:18'),
(33, 38, 34, 211.96, 68, 15.12, '28/06/2017 18:48:19'),
(34, 39, 35, -189.1, 46, 190.1, '28/06/2017 18:51:22'),
(35, 40, 36, -88.89, 68, 90, '28/06/2017 18:54:51'),
(36, 41, 37, 132.26, 68, 10, '28/06/2017 18:56:32'),
(37, 42, 38, -97.78, 68, 100, '28/06/2017 18:57:44'),
(38, 43, 39, -97.78, 68, 100, '28/06/2017 19:02:43'),
(39, 44, 40, -88.89, 46, 90, '28/06/2017 19:06:50'),
(40, 45, 41, 580, 4, 0, '29/06/2017 02:00:42'),
(41, 46, 42, 30.05, 4, 0, '16/03/2020'),
(42, 47, 43, 16, 47, 0, '29/06/2017 03:14:43'),
(43, 48, 44, 56, 4, 0, '29/06/2017 04:03:48');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbparcela`
--

CREATE TABLE `tbparcela` (
  `codParcela` int(11) NOT NULL,
  `codFormaDePagamento` int(11) NOT NULL,
  `codQuantidadeParcela` int(11) NOT NULL,
  `valorParcela` float NOT NULL,
  `codVendaProduto` int(11) NOT NULL,
  `codVendaServico` int(11) NOT NULL,
  `codPagamento` int(11) NOT NULL,
  `dataVencimento` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbparcela`
--

INSERT INTO `tbparcela` (`codParcela`, `codFormaDePagamento`, `codQuantidadeParcela`, `valorParcela`, `codVendaProduto`, `codVendaServico`, `codPagamento`, `dataVencimento`) VALUES
(41, 1, 1, 100, 15, 15, 14, '21/06/2017'),
(42, 2, 3, 33.3333, 15, 15, 14, '20/6/2017'),
(43, 2, 3, 33.3333, 15, 15, 14, '20/7/2017'),
(44, 2, 3, 33.3333, 15, 15, 14, '20/8/2017'),
(45, 2, 1, 100, 15, 15, 14, '21/06/2017'),
(46, 4, 1, 23.3, 15, 15, 14, '21/06/2017'),
(47, 3, 1, 70, 15, 15, 14, '21/06/2017'),
(48, 3, 1, 10, 16, 16, 15, '20/06/2017'),
(49, 2, 3, 3.33333, 17, 17, 16, '20/6/2017'),
(50, 2, 3, 3.33333, 17, 17, 16, '20/7/2017'),
(51, 2, 3, 3.33333, 17, 17, 16, '20/8/2017'),
(52, 2, 1, 10, 17, 17, 16, '22/06/2017'),
(53, 1, 1, 10, 17, 17, 16, '22/06/2017'),
(54, 2, 1, 10, 17, 17, 16, '22/06/2017'),
(55, 4, 1, 500, 17, 17, 16, '22/06/2017'),
(56, 3, 1, 9.95, 17, 17, 16, '22/06/2017'),
(57, 2, 1, 10, 18, 18, 17, '20/06/2017'),
(58, 3, 1, 10, 18, 18, 17, '20/06/2017'),
(59, 1, 1, 100, 19, 19, 18, '20/12/2017'),
(60, 2, 3, 33.3333, 19, 19, 18, '20/6/2017'),
(61, 2, 3, 33.3333, 19, 19, 18, '20/7/2017'),
(62, 2, 3, 33.3333, 19, 19, 18, '20/8/2017'),
(63, 2, 1, 100, 19, 19, 18, '20/12/2017'),
(64, 4, 1, 0, 19, 19, 18, '20/12/2017'),
(65, 4, 1, 0, 19, 19, 18, '20/12/2017'),
(66, 4, 1, 100, 19, 19, 18, '20/12/2017'),
(67, 3, 1, 100, 19, 19, 18, '20/12/2017'),
(68, 3, 1, 10, 20, 20, 19, '20/06/2017'),
(69, 2, 3, 33.3333, 21, 21, 20, '20/6/2017'),
(70, 2, 3, 33.3333, 21, 21, 20, '20/7/2017'),
(71, 2, 3, 33.3333, 21, 21, 20, '20/8/2017'),
(72, 2, 1, 100, 21, 21, 20, '21/06/2017'),
(73, 1, 1, 100, 21, 21, 20, '21/06/2017'),
(74, 4, 1, 100, 21, 21, 20, '21/06/2017'),
(75, 3, 1, 100, 21, 21, 20, '21/06/2017'),
(76, 3, 1, 668.53, 22, 22, 23, '28/06/2017 17:05:08'),
(77, 3, 1, 2302, 23, 23, 24, '28/06/2017 18:09:15'),
(78, 3, 1, 213.32, 24, 24, 25, '28/06/2017 18:11:05'),
(79, 3, 1, 1313.21, 25, 25, 26, '28/06/2017 18:14:50'),
(80, 3, 1, 213.32, 26, 26, 27, '28/06/2017 18:19:19'),
(81, 3, 1, 91.11, 27, 27, 28, '28/06/2017 18:21:02'),
(82, 3, 1, 91.11, 28, 28, 29, '28/06/2017 18:29:31'),
(83, 3, 1, 1446.53, 29, 29, 30, '28/06/2017 18:31:23'),
(84, 3, 1, 41.16, 30, 30, 31, '28/06/2017 18:36:49'),
(85, 3, 1, 91.11, 31, 31, 32, '28/06/2017 18:38:18'),
(86, 3, 1, 227.08, 32, 32, 33, '28/06/2017 18:48:19'),
(87, 3, 1, 1, 33, 33, 34, '28/06/2017 18:51:22'),
(88, 3, 1, 1.11, 34, 34, 35, '28/06/2017 18:54:51'),
(89, 3, 1, 142.26, 35, 35, 36, '28/06/2017 18:56:32'),
(90, 3, 1, 2.22, 36, 36, 37, '28/06/2017 18:57:44'),
(91, 3, 1, 2.22, 37, 37, 38, '28/06/2017 19:02:43'),
(92, 3, 1, 1.11, 38, 38, 39, '28/06/2017 19:06:50'),
(93, 4, 1, 4.44, 39, 39, 41, '16/03/2020'),
(94, 3, 1, 25.61, 39, 39, 41, '16/03/2020'),
(95, 3, 1, 16, 40, 40, 42, '29/06/2017 03:14:43'),
(96, 2, 1, 56, 41, 41, 43, '29/06/2017 04:03:48'),
(97, 3, 1, 0, 41, 41, 43, '29/06/2017 04:03:48');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbpedidofornecedor`
--

CREATE TABLE `tbpedidofornecedor` (
  `codPedidoFornecedor` int(11) NOT NULL,
  `codFornecedor` int(11) NOT NULL,
  `descricaoPedidoFornecedor` varchar(50) NOT NULL,
  `valorTotalPedidoFornecedor` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbproduto`
--

CREATE TABLE `tbproduto` (
  `codProduto` int(11) NOT NULL,
  `nomeProduto` varchar(50) NOT NULL,
  `descricaoProduto` varchar(50) DEFAULT NULL,
  `valorProduto` float NOT NULL,
  `quantidadeMinima` int(11) NOT NULL,
  `fotoProduto` varchar(255) NOT NULL,
  `codFabricante` int(11) NOT NULL,
  `codigoDeBarras` varchar(255) DEFAULT NULL,
  `atividadeProduto` tinyint(4) NOT NULL,
  `custoProduto` float NOT NULL,
  `lucroProduto` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbproduto`
--

INSERT INTO `tbproduto` (`codProduto`, `nomeProduto`, `descricaoProduto`, `valorProduto`, `quantidadeMinima`, `fotoProduto`, `codFabricante`, `codigoDeBarras`, `atividadeProduto`, `custoProduto`, `lucroProduto`) VALUES
(121, 'Creme de pele', 'ice', 10, 10, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.jpg', 1, '1', 0, 0, 0),
(122, 'Shampoo Kerastase Gold', 'Para cabelos fortes', 9, 20, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download1.jpg', 1, '2222222222', 0, 0, 0),
(123, 'Shampo Kerastase Gold', 'Para cabelos fracos', 1.23, 12, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.jpg', 2, ' 123', 0, 0, 0),
(124, 'teste1', 'a mano', 1.23, 123, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download1.jpg', 2, '321 ', 0, 0, 0),
(125, 'teste2', '123', 1.23, 123, '-----------', 1, ' 1234', 0, 0, 0),
(126, 'testeBotao1', '123', 12.11, 123, '-----------', 2, ' 123415', 0, 0, 0),
(127, 'teste', 'asasa', 111.11, 123, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\ElfaMistica1.jpg', 4, ' 12443215r', 0, 0, 0),
(128, 'teste2', '1231', 1.11, 1111, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\ElfaMistica11.jpg', 5, ' 11', 0, 0, 0),
(129, 'teste3', '21312', 11.11, 123231, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\ElfaMistica111.jpg', 2, ' 12454465', 0, 0, 0),
(130, 'marcus', 'asasasa', 11.11, 123, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.jpg', 4, ' 45457856', 0, 0, 0),
(131, '3123123', '123123123', 1313.12, 3123123, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download1.jpg', 5, ' 2131245', 0, 0, 0),
(132, '112312312312', '312312', 31231.2, 123123123, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download11.jpg', 4, ' 12312545576', 0, 0, 0),
(133, 'ashauyshauysa', 'wdsdsds', 11.11, 123, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\Castor1.jpg', 2, '123', 0, 0, 0),
(134, 'Sou', 'Para cabelos lisos, longa duração', 20, 12, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\63894-0.jpg', 15, '123', 0, 17.7, 2.3),
(135, 'Marcus', '123', 11.11, 123, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\Castor1.jpg', 1, '12345', 0, 0, 0),
(136, 'asa', 'sasas', 11.11, 123, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\Castor1.jpg', 5, ' 123', 0, 0, 0),
(137, 'asa', 'sasa1', 11.11, 123, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\atualizado.jpg', 4, ' 24', 0, 0, 0),
(138, 'asasa', '21321', 31.21, 123, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\atualizado1.jpg', 4, ' 1234', 0, 0, 0),
(139, 'Shampoo kerastase gold', 'Para cabelos louros ', 122.56, 5, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.png', 4, '1', 0, 0, 0),
(140, 'Shampoo Kerastase Gold', 'Para cabelos louros', 122.56, 5, '-----------', 1, ' 1', 0, 0, 0),
(141, 'Shampoo Kerastase Gold', ' Para cabelos louros', 122.56, 5, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.png', 1, ' 1', 0, 0, 0),
(142, 'Shampoo Kerastase Gold', 'Para cabelos louros', 122.56, 5, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.png', 1, ' 1', 0, 0, 0),
(143, 'Shampoo Kerastase Gold', ' Para cabelos louros', 122.56, 123, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.jpg', 1, ' 1', 0, 0, 0),
(144, 'Shampoo Kerastase Gold', 'Para cabelos louros', 122.56, 5, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.png', 1, ' 1', 0, 0, 0),
(145, 'Shampoo Kerastase Gold', 'Para cabelos louros', 122.56, 5, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.jpg', 1, '1', 0, 0, 0),
(146, 'Shampoo Kerastase Gold', 'Para cabelos louros', 122.56, 5, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.png', 1, ' 1', 0, 0, 0),
(147, 'Shampoo Kerastase Gold', 'Para cabelos louros', 122.56, 5, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.jpg', 1, '1 ', 0, 0, 0),
(148, 'Shampoo', 'ahsuahsa', 1222.56, 321, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.jpg', 2, ' 12', 0, 0, 0),
(149, 'asasasa', 'asasa', 122.56, 123, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.jpg', 2, '123 ', 0, 0, 0),
(150, 'asas', 'asa', 12.22, 123, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.jpg', 1, ' 12', 0, 0, 0),
(151, 'Sou', 'Para cabelos lisos, longa duração', 20, 12, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\63894-0.jpg', 15, '123', 0, 17.7, 2.3),
(152, 'Sou', 'Para cabelos lisos, longa duração', 20, 12, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\63894-0.jpg', 15, '123', 0, 17.7, 2.3),
(153, 'Sou', 'Para cabelos lisos, longa duração', 20, 12, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\63894-0.jpg', 15, '123', 0, 17.7, 2.3),
(154, 'Sou', 'Para cabelos lisos, longa duração', 20, 12, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\63894-0.jpg', 15, '123', 0, 17.7, 2.3),
(155, 'asas', 'asasa', 11.11, 11, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.png', 1, '123 ', 0, 0, 0),
(156, 'sas', 'asasa', 1.23, 1, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download1.jpg', 1, '1', 0, 0, 0),
(157, 'asas', 'asasa', 11.11, 11, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.png', 1, '123 ', 0, 0, 0),
(158, 'asasa', '12', 1.23, 12, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.jpg', 1, ' 123', 0, 0, 0),
(159, 'asas', 'asasa', 11.11, 11, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.png', 1, '123 ', 0, 0, 0),
(160, 'asasa', '213', 11.12, 12, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.jpg', 1, ' 213', 0, 0, 0),
(161, 'asas', 'asasa', 11.11, 11, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.png', 1, '123 ', 0, 0, 0),
(162, 'asasa', 'asa', 1.11, 111, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.png', 1, ' 1', 0, 0, 0),
(163, 'asasa', 'asasa', 1.23, 12, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.jpg', 2, '123', 0, 0, 0),
(164, 'asas', '123', 1.23, 123, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.jpg', 1, '1 ', 0, 0, 0),
(165, 'asasa', 'sasasa', 1232.13, 123, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.jpg', 2, '123 ', 0, 0, 0),
(166, 'asas', 'asasasa', 123.21, 123, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.jpg', 1, '1', 0, 0, 0),
(167, '12', '12', 12.21, 12, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.jpg', 1, '212 ', 0, 0, 0),
(168, 'asas', 'asa', 213.12, 12, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.jpg', 2, '12 ', 0, 0, 0),
(169, 'asas', 'asasa', 11.11, 11, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.png', 1, '123 ', 0, 0, 0),
(170, 'asasa', 'ssa', 1.21, 12, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.jpg', 5, '1 ', 0, 0, 0),
(171, 'aasa', '12', 12.21, 12, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.jpg', 4, '1', 0, 0, 0),
(172, 'asas', 'asa', 11.12, 12, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.jpg', 1, '12 ', 0, 0, 0),
(173, 'asas', 'asa', 23.12, 12, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.jpg', 1, ' 12', 0, 0, 0),
(174, 'asas', 'a', 11.11, 12, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.jpg', 1, '12', 0, 0, 0),
(175, 'asa', 'as21', 1.11, 12, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.jpg', 1, ' 213', 0, 0, 0),
(176, 'asas', 'asasa', 11.11, 11, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.png', 1, '123 ', 0, 0, 0),
(177, 'asasa', 'asasa', 2213.21, 2312, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.jpg', 2, ' 1', 0, 0, 0),
(178, 'asas', 'asasa', 11.11, 11, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.png', 1, '123 ', 0, 0, 0),
(179, 'asas', 'asas', 123.12, 12, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.jpg', 2, ' 12', 0, 0, 0),
(180, 'asasa', 'sasasasa', 1.21, 1, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.jpg', 2, ' 23', 0, 0, 0),
(181, 'Sou', 'Para cabelos lisos, longa duração', 20, 12, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\63894-0.jpg', 15, '123', 0, 17.7, 2.3),
(182, 'asasa', 'asasa', 12.21, 1221, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\images.jpg', 1, ' 1', 0, 0, 0),
(183, 'asasa', 'asasa', 121.21, 5, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.jpg', 1, ' 1', 0, 0, 0),
(184, 'a', 'a', 1.11, 1, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\images.jpg', 1, ' 1', 0, 0, 0),
(185, 'a', 'a', 1.11, 1, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.jpg', 1, ' 1', 0, 0, 0),
(186, 'asasa', 'as', 123.12, 123, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.png', 2, '123', 0, 0, 0),
(187, 'asasa', '12312', 1.23, 123, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.png', 1, ' 1', 0, 0, 0),
(188, '12312', '31231', 1231.23, 13123, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.png', 1, ' 1234', 0, 0, 0),
(189, 'asas', 'asasa', 11.11, 11, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.png', 1, '123 ', 0, 0, 0),
(190, 'asasa', 'asa', 1.11, 111, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.png', 1, ' 1', 0, 0, 0),
(191, 'asasa', 'asa11111111', 1.23, 123, '-----------', 1, '123', 0, 0, 0),
(192, 'asasa', 'asa', 1.11, 111, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.png', 1, ' 1', 0, 0, 0),
(194, 'asaasa', 'sasa', 11.11, 11, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.png', 2, '1', 0, 0, 0),
(195, 'asas', 'asasa', 11.11, 11, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.png', 1, '123 ', 0, 0, 0),
(196, 'Sou', 'Para cabelos lisos, longa duração', 20, 12, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\63894-0.jpg', 15, '123', 0, 17.7, 2.3),
(197, 'asas', 'asasa', 11.11, 11, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.png', 1, '123 ', 0, 0, 0),
(198, 'asas', 'asasa', 1.11, 11, '-----------', 2, ' 1', 0, 0, 0),
(199, 'asasa', 'sasa1', 1.11, 11, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.jpg', 1, ' 412', 0, 0, 0),
(200, 'asas', 'asasa', 1.11, 11, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\download.jpg', 2, ' 123', 0, 0, 0),
(201, 'Sou', 'Para cabelos lisos, longa duração', 20, 12, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\63894-0.jpg', 15, '123', 1, 17.7, 2.3),
(202, 'Condicionador Seda', 'Cabelos lisos e sedosos', 30, 10, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\condicionador-512181.jpg', 5, '12', 0, 26, 4),
(203, 'Condicionador Seda', 'Cabelos lisos e sedosos', 30, 10, 'C:\\Royal\\src\\br\\com\\royal\\imagens\\condicionador-512181.jpg', 5, '12', 1, 26, 4);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbquantidadeparcela`
--

CREATE TABLE `tbquantidadeparcela` (
  `codQuantidadeParcela` int(11) NOT NULL,
  `descricaoQuantidadeParcela` varchar(5) NOT NULL,
  `statusQuantidadeParcela` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbquantidadeparcela`
--

INSERT INTO `tbquantidadeparcela` (`codQuantidadeParcela`, `descricaoQuantidadeParcela`, `statusQuantidadeParcela`) VALUES
(1, '1', 1),
(2, '2', 1),
(3, '3', 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbregimecontratacaofuncionario`
--

CREATE TABLE `tbregimecontratacaofuncionario` (
  `codRegimeContratacaoFuncionario` int(11) NOT NULL,
  `descricaoRegime` varchar(30) NOT NULL,
  `cargaHorario` time NOT NULL,
  `codFuncionario` int(11) NOT NULL,
  `codCargo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbsaidacaixa`
--

CREATE TABLE `tbsaidacaixa` (
  `codSaidaCaixa` int(11) NOT NULL,
  `valorSaidaCaixa` double NOT NULL,
  `dataSaidaCaixa` varchar(20) NOT NULL,
  `codMotivoSaidaCaixa` int(11) NOT NULL,
  `statusSaidaCaixa` tinyint(1) NOT NULL,
  `codCaixa` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbsaidacaixa`
--

INSERT INTO `tbsaidacaixa` (`codSaidaCaixa`, `valorSaidaCaixa`, `dataSaidaCaixa`, `codMotivoSaidaCaixa`, `statusSaidaCaixa`, `codCaixa`) VALUES
(7, 1, '21/06/2017', 3, 1, 0),
(8, 12, '24/06/2017', 3, 1, 0),
(9, 12.35, '24/06/2017', 3, 1, 0),
(10, 13.54, '24/06/2017', 3, 1, 0),
(11, 12.2, '25/06/2017', 4, 1, 0),
(12, 20, '25/06/2017', 3, 1, 0),
(13, 15, '25/06/2017', 3, 1, 0),
(14, 12, '25/06/2017', 3, 1, 0),
(15, 1.2, '25/06/2017', 4, 1, 0),
(16, 50, '25/06/2017', 4, 1, 0),
(17, 20, '25/06/2017 21:28:29', 4, 1, 0),
(18, 20, '25/06/2017 21:31:13', 4, 1, 0),
(19, 50, '25/06/2017 23:26:46', 4, 1, 8),
(20, 1, '28/06/2017 15:58:22', 4, 1, 9);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbsaidaproduto`
--

CREATE TABLE `tbsaidaproduto` (
  `codSaidaProduto` int(11) NOT NULL,
  `codMotivoSaidaProduto` int(11) NOT NULL,
  `dataSaidaProduto` varchar(20) NOT NULL,
  `quantidadeSaidaProduto` smallint(6) NOT NULL,
  `codProduto` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbsaidaproduto`
--

INSERT INTO `tbsaidaproduto` (`codSaidaProduto`, `codMotivoSaidaProduto`, `dataSaidaProduto`, `quantidadeSaidaProduto`, `codProduto`) VALUES
(6, 1, '20/06/2017', 30, 201),
(7, 1, '20/06/2017', 45, 201),
(8, 1, '28/06/2017', 223, 201),
(9, 1, '28/06/2017', 200, 201),
(10, 1, '28/06/2017', 12, 201),
(11, 1, '28/06/2017', 111, 201),
(12, 1, '28/06/2017', 12, 201),
(13, 1, '28/06/2017', 1, 201),
(14, 1, '28/06/2017', 1, 201),
(15, 1, '28/06/2017', 123, 201),
(16, 1, '28/06/2017', 1, 201),
(17, 1, '28/06/2017', 1, 201),
(18, 1, '28/06/2017', 20, 201),
(19, 1, '28/06/2017', 10, 201),
(20, 1, '28/06/2017', 1, 201),
(21, 1, '28/06/2017', 11, 201),
(22, 1, '28/06/2017', 2, 201),
(23, 1, '28/06/2017', 2, 201),
(24, 1, '28/06/2017', 1, 201),
(25, 1, '29/06/2017', 2, 201);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbservico`
--

CREATE TABLE `tbservico` (
  `codServico` int(11) NOT NULL,
  `descricaoServico` varchar(50) NOT NULL,
  `valorServico` double NOT NULL,
  `atividadeServico` tinyint(1) NOT NULL,
  `nomeServico` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbservico`
--

INSERT INTO `tbservico` (`codServico`, `descricaoServico`, `valorServico`, `atividadeServico`, `nomeServico`) VALUES
(1, 'corte masculino ', 16, 1, 'Corte masculino'),
(2, 'Progressiva completa', 80, 1, 'Progressiva'),
(7, 'Cabelo, pele e sombrancelha', 250, 1, 'Super combo'),
(9, '456', 20, 0, 'corte'),
(15, 'Design com pigmentação', 80, 1, 'Sobrancelha');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbservicofuncionario`
--

CREATE TABLE `tbservicofuncionario` (
  `codServicoFuncionario` int(11) NOT NULL,
  `codServico` int(11) NOT NULL,
  `codFuncionario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbservicofuncionario`
--

INSERT INTO `tbservicofuncionario` (`codServicoFuncionario`, `codServico`, `codFuncionario`) VALUES
(6, 1, 2),
(8, 1, 1),
(13, 2, 5),
(30, 1, 4),
(31, 2, 4),
(52, 0, 8),
(53, 2, 8),
(54, 7, 8),
(66, 0, 9),
(67, 5, 9),
(68, 7, 9),
(69, 0, 10),
(70, 0, 11),
(72, 7, 6),
(73, 2, 7),
(74, 7, 7);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbtelefonecliente`
--

CREATE TABLE `tbtelefonecliente` (
  `codTelefoneCliente` int(11) NOT NULL,
  `numeroTelefoneCliente` varchar(20) NOT NULL,
  `codCliente` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbtelefonecliente`
--

INSERT INTO `tbtelefonecliente` (`codTelefoneCliente`, `numeroTelefoneCliente`, `codCliente`) VALUES
(11, '(11) 1111-11111', 5),
(12, '(33) 3333-33333', 5),
(24, '(23) 2222-22222', 7),
(28, '(11) 1111-11111', 6),
(29, '(21) 2323-33333', 8),
(41, '(11) 1111-11111', 9),
(51, '(11) 1111-11111', 11),
(52, '(22) 2222-22222', 11),
(54, '(11) 1111-11111', 10),
(55, '(22) 2222-22222', 12),
(56, '(11) 1111-11111', 13),
(58, '(22) 2222-22222', 14),
(59, '(22) 2222-22222', 15),
(60, '(11) 1111-11111', 16),
(61, '(22) 2222-22222', 17),
(62, '(11) 1111-11111', 18),
(63, '(22) 2222-22222', 19),
(64, '(11) 1111-11111', 20),
(65, '(54) 5663-36345', 20),
(66, '(22) 2222-22222', 21),
(67, '(11) 1111-11111', 22),
(68, '(22) 2222-22222', 23),
(71, '(22) 2222-22222', 24),
(72, '(22) 2222-22222', 25),
(79, '(22) 2222-22222', 26),
(80, '(22) 2222-22222', 27),
(81, '(22) 2222-22222', 28),
(83, '(11) 1111-11111', 29),
(84, '(33) 3333-33333', 30),
(85, '(22) 2222-22222', 31),
(88, '(33) 3333-33333', 32),
(89, '(22) 2222-22222', 33),
(96, '(33) 3333-33333', 34),
(97, '(33) 3333-33333', 35),
(99, '(33) 3333-33333', 37),
(100, '(33) 3333-33333', 36),
(101, '(33) 3333-33333', 38),
(102, '(33) 3333-33333', 39),
(104, '(33) 3333-33333', 40),
(106, '(33) 3333-33333', 41),
(108, '(33) 3333-33333', 42),
(109, '(33) 3333-33333', 43),
(114, '(11) 1111-11111', 44),
(120, '(22) 2222-22222', 45),
(121, '(11) 1111-11111', 46),
(122, '(11) 1111-11111', 47),
(123, '(11) 1111-11111', 48),
(124, '(11) 1111-11111', 49),
(125, '(33) 3333-33333', 50),
(126, '(33) 3333-33333', 51),
(127, '(23) 3333-33333', 52),
(128, '(33) 3333-33333', 53),
(130, '(33) 3333-33333', 54),
(131, '(33) 3333-33333', 55),
(132, '(44) 4444-44444', 56),
(133, '(44) 4444-44444', 57),
(134, '(44) 4444-44444', 58),
(135, '(33) 3333-33333', 59),
(136, '(22) 2222-22222', 60),
(137, '(55) 5555-55555', 61),
(140, '(22) 2222-22222', 62),
(143, '(22) 2222-22222', 63),
(144, '(11) 1111-11111', 64),
(145, '(22) 2222-22222', 65),
(146, '(63) 3333-33333', 66),
(151, '(23) 3333-33333', 67),
(159, '(11) 1111-11111', 68),
(170, '(11) 1111-11111', 69),
(171, '(22) 2222-22222', 4),
(172, '(11) 9853-71080', 3),
(173, '(11) 2025-2092', 3);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbtelefonefornecedor`
--

CREATE TABLE `tbtelefonefornecedor` (
  `codTelefoneFornecedor` int(11) NOT NULL,
  `numeroTelefoneFornecedor` varchar(20) NOT NULL,
  `codFornecedor` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbtelefonefornecedor`
--

INSERT INTO `tbtelefonefornecedor` (`codTelefoneFornecedor`, `numeroTelefoneFornecedor`, `codFornecedor`) VALUES
(22, '(10) 0000-0000', 19),
(23, '(12) 3132-1322', 20),
(24, '(11) 1111-1111', 22),
(36, '(22) 2222-2222', 25),
(40, '(23) 1321-2312', 27),
(41, '(10) 2312-3123', 26),
(46, '(12) 3132-1231', 28),
(47, '(11) 1111-1111', 21),
(52, '(11) 2285-5934', 29),
(55, '(33) 3333-3333', 32),
(58, '(11) 1111-1111', 30),
(59, '(33) 3333-3333', 30),
(70, '(11) 1111-1111', 33),
(73, '(11) 1111-1111', 36),
(78, '(11) 1111-1111', 34),
(79, '(92) 8402-4097', 35);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbtelefonefuncionario`
--

CREATE TABLE `tbtelefonefuncionario` (
  `codTelefoneFuncionario` int(11) NOT NULL,
  `numeroTelefoneFuncionario` varchar(20) NOT NULL,
  `codFuncionario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbtelefonefuncionario`
--

INSERT INTO `tbtelefonefuncionario` (`codTelefoneFuncionario`, `numeroTelefoneFuncionario`, `codFuncionario`) VALUES
(5, '(22) 2222-22222', 2),
(6, '(11) 9853-71080', 1),
(18, '(11) 1111-11111', 4),
(19, '(43) 3333-33333', 4),
(20, '(44) 4444-44444', 4),
(49, '(11) 9825-73120', 8),
(50, '(11) 2025-2092', 8),
(62, '(11) 1111-1111', 9),
(63, '(22) 2222-22222', 9),
(64, '(11) 1111-11111', 10),
(65, '(11) 1111-11111', 11),
(69, '(22) 2222-22222', 6),
(70, '(22) 2222-22223', 6),
(71, '(33) 3333-33333', 6),
(72, '(22) 2222-22222', 7),
(73, '(33) 3333-33333', 7);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbtipocartao`
--

CREATE TABLE `tbtipocartao` (
  `codTipoCartao` int(11) NOT NULL,
  `descricaoTipoCartao` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbtipocartao`
--

INSERT INTO `tbtipocartao` (`codTipoCartao`, `descricaoTipoCartao`) VALUES
(1, 'Débito'),
(2, 'Crédito');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbusuario`
--

CREATE TABLE `tbusuario` (
  `codUsuario` int(11) NOT NULL,
  `loginUsuario` varchar(75) NOT NULL,
  `senhaUsuario` varchar(255) NOT NULL,
  `codFuncionario` int(11) NOT NULL,
  `tipoUsuario` varchar(50) NOT NULL,
  `respostaSecreta` varchar(200) NOT NULL,
  `usuarioAtivo` tinyint(1) NOT NULL,
  `perguntaUsuario` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbusuario`
--

INSERT INTO `tbusuario` (`codUsuario`, `loginUsuario`, `senhaUsuario`, `codFuncionario`, `tipoUsuario`, `respostaSecreta`, `usuarioAtivo`, `perguntaUsuario`) VALUES
(4, 'Primao', '8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92', 4, 'Administrador', 'Irineu', 0, 'Qual o nome do seu primeiro cachorro?'),
(5, 'Super Yago', '8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92', 6, 'Administrador', 'Uchoa', 0, 'Quem é o meu BFF'),
(6, 'Adm', '8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92', 7, 'Administrador', 'Irineu', 1, 'Qual o nome do seu primeiro cachorro?'),
(7, 'jooj', '8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92', 6, 'Administrador', 'berg', 0, 'Qual o nome do seu primeiro cachorro?'),
(9, 'funcionario', '8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92', 6, 'Funcionário', 'Irineu', 1, 'Qual foi o destino da sua primeira viagem?');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbvendaproduto`
--

CREATE TABLE `tbvendaproduto` (
  `codVendaProduto` int(11) NOT NULL,
  `valorTotalVendaProduto` float NOT NULL,
  `codCliente` int(11) NOT NULL,
  `codFuncionario` int(11) NOT NULL,
  `codComandaProduto` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbvendaproduto`
--

INSERT INTO `tbvendaproduto` (`codVendaProduto`, `valorTotalVendaProduto`, `codCliente`, `codFuncionario`, `codComandaProduto`) VALUES
(15, 333.3, 66, 6, 19),
(16, 0, 66, 6, 20),
(17, 499.95, 66, 6, 21),
(18, 0, 64, 6, 22),
(19, 0, 66, 6, 23),
(20, 0, 64, 6, 24),
(21, 0, 66, 6, 25),
(22, 2477.53, 68, 7, 28),
(23, 2222, 21, 7, 29),
(24, 133.32, 46, 7, 30),
(25, 1233.21, 68, 7, 31),
(26, 133.32, 21, 7, 32),
(27, 11.11, 21, 7, 33),
(28, 11.11, 50, 7, 34),
(29, 1366.53, 68, 7, 35),
(30, 11.11, 68, 7, 36),
(31, 11.11, 21, 7, 37),
(32, 222.2, 68, 7, 38),
(33, 111.1, 46, 7, 39),
(34, 11.11, 68, 7, 40),
(35, 122.21, 68, 7, 41),
(36, 22.22, 68, 7, 42),
(37, 22.22, 68, 7, 43),
(38, 11.11, 46, 7, 44),
(39, 0, 4, 7, 46),
(40, 0, 47, 7, 47),
(41, 40, 4, 7, 48);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbvendaservico`
--

CREATE TABLE `tbvendaservico` (
  `codVendaServico` int(11) NOT NULL,
  `valorTotalVendaServico` double NOT NULL,
  `codCliente` int(11) NOT NULL,
  `codFuncionario` int(11) NOT NULL,
  `codComandaServico` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbvendaservico`
--

INSERT INTO `tbvendaservico` (`codVendaServico`, `valorTotalVendaServico`, `codCliente`, `codFuncionario`, `codComandaServico`) VALUES
(15, 60, 66, 6, 15),
(16, 10, 66, 6, 16),
(17, 50, 66, 6, 17),
(18, 20, 64, 6, 18),
(19, 500, 66, 6, 19),
(20, 10, 64, 6, 20),
(21, 500, 66, 6, 21),
(22, 80, 68, 7, 24),
(23, 80, 21, 7, 25),
(24, 80, 46, 7, 26),
(25, 80, 68, 7, 27),
(26, 80, 21, 7, 28),
(27, 80, 21, 7, 29),
(28, 80, 50, 7, 30),
(29, 80, 68, 7, 31),
(30, 30.05, 68, 7, 32),
(31, 80, 21, 7, 33),
(32, 20, 68, 7, 34),
(33, 80, 46, 7, 35),
(34, 80, 68, 7, 36),
(35, 30.05, 68, 7, 37),
(36, 80, 68, 7, 38),
(37, 80, 68, 7, 39),
(38, 80, 46, 7, 40),
(39, 30.05, 4, 7, 42),
(40, 16, 47, 7, 43),
(41, 16, 4, 7, 44);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbagendamento`
--
ALTER TABLE `tbagendamento`
  ADD PRIMARY KEY (`codAgendamento`),
  ADD KEY `codCliente` (`codCliente`),
  ADD KEY `codHorarioInicio` (`codHorarioInicio`),
  ADD KEY `codServico` (`codServico`),
  ADD KEY `codFuncionario` (`codFuncionario`);

--
-- Indexes for table `tbcaixa`
--
ALTER TABLE `tbcaixa`
  ADD PRIMARY KEY (`codCaixa`);

--
-- Indexes for table `tbcargo`
--
ALTER TABLE `tbcargo`
  ADD PRIMARY KEY (`codCargo`);

--
-- Indexes for table `tbcartao`
--
ALTER TABLE `tbcartao`
  ADD PRIMARY KEY (`codCartao`);

--
-- Indexes for table `tbcheque`
--
ALTER TABLE `tbcheque`
  ADD PRIMARY KEY (`codCheque`);

--
-- Indexes for table `tbcliente`
--
ALTER TABLE `tbcliente`
  ADD PRIMARY KEY (`codCliente`);

--
-- Indexes for table `tbcomandaproduto`
--
ALTER TABLE `tbcomandaproduto`
  ADD PRIMARY KEY (`codComandaProduto`);

--
-- Indexes for table `tbcomandaservico`
--
ALTER TABLE `tbcomandaservico`
  ADD PRIMARY KEY (`codComandaServico`);

--
-- Indexes for table `tbentradacaixa`
--
ALTER TABLE `tbentradacaixa`
  ADD PRIMARY KEY (`codEntradaCaixa`);

--
-- Indexes for table `tbentradaproduto`
--
ALTER TABLE `tbentradaproduto`
  ADD PRIMARY KEY (`codEntradaProduto`);

--
-- Indexes for table `tbestoque`
--
ALTER TABLE `tbestoque`
  ADD PRIMARY KEY (`codEstoque`);

--
-- Indexes for table `tbfabricante`
--
ALTER TABLE `tbfabricante`
  ADD PRIMARY KEY (`codFabricante`);

--
-- Indexes for table `tbfiado`
--
ALTER TABLE `tbfiado`
  ADD PRIMARY KEY (`codFiado`);

--
-- Indexes for table `tbformadepagamento`
--
ALTER TABLE `tbformadepagamento`
  ADD PRIMARY KEY (`codFormaDePagamento`);

--
-- Indexes for table `tbfornecedor`
--
ALTER TABLE `tbfornecedor`
  ADD PRIMARY KEY (`codFornecedor`);

--
-- Indexes for table `tbfuncionario`
--
ALTER TABLE `tbfuncionario`
  ADD PRIMARY KEY (`codFuncionario`);

--
-- Indexes for table `tbhorario`
--
ALTER TABLE `tbhorario`
  ADD PRIMARY KEY (`codHorario`);

--
-- Indexes for table `tbitensagendamento`
--
ALTER TABLE `tbitensagendamento`
  ADD PRIMARY KEY (`codItensAgendamento`);

--
-- Indexes for table `tbitenscomandaproduto`
--
ALTER TABLE `tbitenscomandaproduto`
  ADD PRIMARY KEY (`codItensComandaProduto`);

--
-- Indexes for table `tbitenscomandaservico`
--
ALTER TABLE `tbitenscomandaservico`
  ADD PRIMARY KEY (`codItensComandaServico`);

--
-- Indexes for table `tbitenspedidofornecedor`
--
ALTER TABLE `tbitenspedidofornecedor`
  ADD PRIMARY KEY (`codItensPedidoFornecedor`);

--
-- Indexes for table `tbitensvendaproduto`
--
ALTER TABLE `tbitensvendaproduto`
  ADD PRIMARY KEY (`codItensVendaProduto`);

--
-- Indexes for table `tbitensvendaservico`
--
ALTER TABLE `tbitensvendaservico`
  ADD PRIMARY KEY (`codItensVendaServico`);

--
-- Indexes for table `tbmotivoentradacaixa`
--
ALTER TABLE `tbmotivoentradacaixa`
  ADD PRIMARY KEY (`codMotivoEntradaCaixa`);

--
-- Indexes for table `tbmotivoentradaproduto`
--
ALTER TABLE `tbmotivoentradaproduto`
  ADD PRIMARY KEY (`codMotivoEntradaProduto`);

--
-- Indexes for table `tbmotivosaidacaixa`
--
ALTER TABLE `tbmotivosaidacaixa`
  ADD PRIMARY KEY (`codMotivoSaidaCaixa`);

--
-- Indexes for table `tbmotivosaidaproduto`
--
ALTER TABLE `tbmotivosaidaproduto`
  ADD PRIMARY KEY (`codMotivoSaidaProduto`);

--
-- Indexes for table `tbpagamento`
--
ALTER TABLE `tbpagamento`
  ADD PRIMARY KEY (`codPagamento`);

--
-- Indexes for table `tbparcela`
--
ALTER TABLE `tbparcela`
  ADD PRIMARY KEY (`codParcela`);

--
-- Indexes for table `tbpedidofornecedor`
--
ALTER TABLE `tbpedidofornecedor`
  ADD PRIMARY KEY (`codPedidoFornecedor`);

--
-- Indexes for table `tbproduto`
--
ALTER TABLE `tbproduto`
  ADD PRIMARY KEY (`codProduto`);

--
-- Indexes for table `tbquantidadeparcela`
--
ALTER TABLE `tbquantidadeparcela`
  ADD PRIMARY KEY (`codQuantidadeParcela`);

--
-- Indexes for table `tbregimecontratacaofuncionario`
--
ALTER TABLE `tbregimecontratacaofuncionario`
  ADD PRIMARY KEY (`codRegimeContratacaoFuncionario`);

--
-- Indexes for table `tbsaidacaixa`
--
ALTER TABLE `tbsaidacaixa`
  ADD PRIMARY KEY (`codSaidaCaixa`);

--
-- Indexes for table `tbsaidaproduto`
--
ALTER TABLE `tbsaidaproduto`
  ADD PRIMARY KEY (`codSaidaProduto`);

--
-- Indexes for table `tbservico`
--
ALTER TABLE `tbservico`
  ADD PRIMARY KEY (`codServico`);

--
-- Indexes for table `tbservicofuncionario`
--
ALTER TABLE `tbservicofuncionario`
  ADD PRIMARY KEY (`codServicoFuncionario`);

--
-- Indexes for table `tbtelefonecliente`
--
ALTER TABLE `tbtelefonecliente`
  ADD PRIMARY KEY (`codTelefoneCliente`);

--
-- Indexes for table `tbtelefonefornecedor`
--
ALTER TABLE `tbtelefonefornecedor`
  ADD PRIMARY KEY (`codTelefoneFornecedor`);

--
-- Indexes for table `tbtelefonefuncionario`
--
ALTER TABLE `tbtelefonefuncionario`
  ADD PRIMARY KEY (`codTelefoneFuncionario`);

--
-- Indexes for table `tbtipocartao`
--
ALTER TABLE `tbtipocartao`
  ADD PRIMARY KEY (`codTipoCartao`);

--
-- Indexes for table `tbusuario`
--
ALTER TABLE `tbusuario`
  ADD PRIMARY KEY (`codUsuario`);

--
-- Indexes for table `tbvendaproduto`
--
ALTER TABLE `tbvendaproduto`
  ADD PRIMARY KEY (`codVendaProduto`);

--
-- Indexes for table `tbvendaservico`
--
ALTER TABLE `tbvendaservico`
  ADD PRIMARY KEY (`codVendaServico`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbagendamento`
--
ALTER TABLE `tbagendamento`
  MODIFY `codAgendamento` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
--
-- AUTO_INCREMENT for table `tbcaixa`
--
ALTER TABLE `tbcaixa`
  MODIFY `codCaixa` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT for table `tbcargo`
--
ALTER TABLE `tbcargo`
  MODIFY `codCargo` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tbcartao`
--
ALTER TABLE `tbcartao`
  MODIFY `codCartao` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;
--
-- AUTO_INCREMENT for table `tbcheque`
--
ALTER TABLE `tbcheque`
  MODIFY `codCheque` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `tbcliente`
--
ALTER TABLE `tbcliente`
  MODIFY `codCliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=70;
--
-- AUTO_INCREMENT for table `tbcomandaproduto`
--
ALTER TABLE `tbcomandaproduto`
  MODIFY `codComandaProduto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;
--
-- AUTO_INCREMENT for table `tbcomandaservico`
--
ALTER TABLE `tbcomandaservico`
  MODIFY `codComandaServico` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;
--
-- AUTO_INCREMENT for table `tbentradacaixa`
--
ALTER TABLE `tbentradacaixa`
  MODIFY `codEntradaCaixa` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=83;
--
-- AUTO_INCREMENT for table `tbentradaproduto`
--
ALTER TABLE `tbentradaproduto`
  MODIFY `codEntradaProduto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=72;
--
-- AUTO_INCREMENT for table `tbestoque`
--
ALTER TABLE `tbestoque`
  MODIFY `codEstoque` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `tbfabricante`
--
ALTER TABLE `tbfabricante`
  MODIFY `codFabricante` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT for table `tbfiado`
--
ALTER TABLE `tbfiado`
  MODIFY `codFiado` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `tbformadepagamento`
--
ALTER TABLE `tbformadepagamento`
  MODIFY `codFormaDePagamento` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `tbfornecedor`
--
ALTER TABLE `tbfornecedor`
  MODIFY `codFornecedor` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;
--
-- AUTO_INCREMENT for table `tbfuncionario`
--
ALTER TABLE `tbfuncionario`
  MODIFY `codFuncionario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT for table `tbhorario`
--
ALTER TABLE `tbhorario`
  MODIFY `codHorario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT for table `tbitenscomandaproduto`
--
ALTER TABLE `tbitenscomandaproduto`
  MODIFY `codItensComandaProduto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;
--
-- AUTO_INCREMENT for table `tbitenscomandaservico`
--
ALTER TABLE `tbitenscomandaservico`
  MODIFY `codItensComandaServico` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;
--
-- AUTO_INCREMENT for table `tbitensvendaproduto`
--
ALTER TABLE `tbitensvendaproduto`
  MODIFY `codItensVendaProduto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;
--
-- AUTO_INCREMENT for table `tbitensvendaservico`
--
ALTER TABLE `tbitensvendaservico`
  MODIFY `codItensVendaServico` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;
--
-- AUTO_INCREMENT for table `tbmotivoentradacaixa`
--
ALTER TABLE `tbmotivoentradacaixa`
  MODIFY `codMotivoEntradaCaixa` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `tbmotivoentradaproduto`
--
ALTER TABLE `tbmotivoentradaproduto`
  MODIFY `codMotivoEntradaProduto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT for table `tbmotivosaidacaixa`
--
ALTER TABLE `tbmotivosaidacaixa`
  MODIFY `codMotivoSaidaCaixa` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `tbmotivosaidaproduto`
--
ALTER TABLE `tbmotivosaidaproduto`
  MODIFY `codMotivoSaidaProduto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `tbpagamento`
--
ALTER TABLE `tbpagamento`
  MODIFY `codPagamento` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;
--
-- AUTO_INCREMENT for table `tbparcela`
--
ALTER TABLE `tbparcela`
  MODIFY `codParcela` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=98;
--
-- AUTO_INCREMENT for table `tbproduto`
--
ALTER TABLE `tbproduto`
  MODIFY `codProduto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=204;
--
-- AUTO_INCREMENT for table `tbquantidadeparcela`
--
ALTER TABLE `tbquantidadeparcela`
  MODIFY `codQuantidadeParcela` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `tbsaidacaixa`
--
ALTER TABLE `tbsaidacaixa`
  MODIFY `codSaidaCaixa` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
--
-- AUTO_INCREMENT for table `tbsaidaproduto`
--
ALTER TABLE `tbsaidaproduto`
  MODIFY `codSaidaProduto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;
--
-- AUTO_INCREMENT for table `tbservico`
--
ALTER TABLE `tbservico`
  MODIFY `codServico` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT for table `tbservicofuncionario`
--
ALTER TABLE `tbservicofuncionario`
  MODIFY `codServicoFuncionario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=75;
--
-- AUTO_INCREMENT for table `tbtelefonecliente`
--
ALTER TABLE `tbtelefonecliente`
  MODIFY `codTelefoneCliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=174;
--
-- AUTO_INCREMENT for table `tbtelefonefornecedor`
--
ALTER TABLE `tbtelefonefornecedor`
  MODIFY `codTelefoneFornecedor` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=80;
--
-- AUTO_INCREMENT for table `tbtelefonefuncionario`
--
ALTER TABLE `tbtelefonefuncionario`
  MODIFY `codTelefoneFuncionario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=74;
--
-- AUTO_INCREMENT for table `tbtipocartao`
--
ALTER TABLE `tbtipocartao`
  MODIFY `codTipoCartao` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `tbusuario`
--
ALTER TABLE `tbusuario`
  MODIFY `codUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT for table `tbvendaproduto`
--
ALTER TABLE `tbvendaproduto`
  MODIFY `codVendaProduto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;
--
-- AUTO_INCREMENT for table `tbvendaservico`
--
ALTER TABLE `tbvendaservico`
  MODIFY `codVendaServico` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
