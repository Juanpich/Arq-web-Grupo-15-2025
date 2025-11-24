-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Host: mysql
-- Generation Time: Nov 24, 2025 at 06:32 PM
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
-- Database: `journeys`
--

-- --------------------------------------------------------

--
-- Table structure for table `journey`
--

CREATE TABLE `journey` (
  `journey_id` bigint NOT NULL,
  `date` date DEFAULT NULL,
  `finish_hour` time(6) DEFAULT NULL,
  `init_hour` time(6) DEFAULT NULL,
  `km_traveled` int NOT NULL,
  `pause_minutes` varchar(255) DEFAULT NULL,
  `scooter_id` bigint DEFAULT NULL,
  `total_houres` decimal(21,0) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `finish_date` date DEFAULT NULL,
  `finish_pause` time(6) DEFAULT NULL,
  `init_pause` time(6) DEFAULT NULL,
  `total_minutespause` decimal(21,0) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `journey`
--

INSERT INTO `journey` (`journey_id`, `date`, `finish_hour`, `init_hour`, `km_traveled`, `pause_minutes`, `scooter_id`, `total_houres`, `user_id`, `finish_date`, `finish_pause`, `init_pause`, `total_minutespause`) VALUES
(5, '2025-11-17', '15:36:13.510000', '15:26:32.488000', 42, NULL, 1, 581022031900, 5, '2025-11-17', '15:29:39.804000', '15:28:33.596000', 66208455400),
(6, '2025-11-17', '16:33:00.680000', '16:32:48.265000', 42, NULL, 1, 12414908200, 5, '2025-11-17', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `rate`
--

CREATE TABLE `rate` (
  `rate_id` bigint NOT NULL,
  `finish_date` datetime(6) DEFAULT NULL,
  `init_date` datetime(6) NOT NULL,
  `price` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `rate`
--

INSERT INTO `rate` (`rate_id`, `finish_date`, `init_date`, `price`) VALUES
(1, '2025-12-31 23:59:59.000000', '2025-01-01 00:00:00.000000', 15);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `journey`
--
ALTER TABLE `journey`
  ADD PRIMARY KEY (`journey_id`);

--
-- Indexes for table `rate`
--
ALTER TABLE `rate`
  ADD PRIMARY KEY (`rate_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `journey`
--
ALTER TABLE `journey`
  MODIFY `journey_id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `rate`
--
ALTER TABLE `rate`
  MODIFY `rate_id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
