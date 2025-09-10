-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Host: mysql
-- Generation Time: Sep 10, 2025 at 12:20 PM
-- Server version: 8.0.43
-- PHP Version: 8.2.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `integrador_1`
--

-- --------------------------------------------------------

--
-- Table structure for table `Cliente`
--

CREATE TABLE `Cliente` (
  `idCliente` int NOT NULL,
  `nombre` varchar(500) NOT NULL,
  `email` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Factura`
--

CREATE TABLE `Factura` (
  `idFactura` int NOT NULL,
  `idCliente` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Factura_Producto`
--

CREATE TABLE `Factura_Producto` (
  `idFactura` int NOT NULL,
  `idProducto` int NOT NULL,
  `cantidad` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Producto`
--

CREATE TABLE `Producto` (
  `idProducto` int NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `valor` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Cliente`
--
ALTER TABLE `Cliente`
  ADD PRIMARY KEY (`idCliente`);

--
-- Indexes for table `Factura`
--
ALTER TABLE `Factura`
  ADD PRIMARY KEY (`idFactura`),
  ADD KEY `fk_factura_cliente` (`idCliente`) USING BTREE;

--
-- Indexes for table `Factura_Producto`
--
ALTER TABLE `Factura_Producto`
  ADD PRIMARY KEY (`idFactura`,`idProducto`),
  ADD KEY `fk_factura_producto_factura` (`idFactura`),
  ADD KEY `fk_factura_producto_producto` (`idProducto`);

--
-- Indexes for table `Producto`
--
ALTER TABLE `Producto`
  ADD PRIMARY KEY (`idProducto`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Factura`
--
ALTER TABLE `Factura`
  ADD CONSTRAINT `Factura_ibfk_1` FOREIGN KEY (`idCliente`) REFERENCES `Cliente` (`idCliente`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `Factura_Producto`
--
ALTER TABLE `Factura_Producto`
  ADD CONSTRAINT `Factura_Producto_ibfk_1` FOREIGN KEY (`idProducto`) REFERENCES `Producto` (`idProducto`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `Factura_Producto_ibfk_2` FOREIGN KEY (`idFactura`) REFERENCES `Factura` (`idFactura`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
