-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 21, 2023 at 05:42 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `documentatie`
--

-- --------------------------------------------------------

--
-- Table structure for table `inkomen`
--

CREATE TABLE `inkomen` (
  `id` int(11) NOT NULL,
  `jaar` year(4) NOT NULL,
  `inkomen` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `inkomen`
--

INSERT INTO `inkomen` (`id`, `jaar`, `inkomen`) VALUES
(1, '2023', 0),
(2, '2022', 89655),
(3, '2021', 43820),
(4, '2020', 138622),
(5, '2019', 128021);

-- --------------------------------------------------------

--
-- Table structure for table `kosten`
--

CREATE TABLE `kosten` (
  `id` int(11) NOT NULL,
  `jaar` year(4) NOT NULL,
  `kosten` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `kosten`
--

INSERT INTO `kosten` (`id`, `jaar`, `kosten`) VALUES
(1, '2023', 0),
(2, '2022', 44850),
(3, '2021', 62236),
(4, '2020', 53972),
(5, '2019', 49231);

-- --------------------------------------------------------

--
-- Table structure for table `medewerkers`
--

CREATE TABLE `medewerkers` (
  `id` int(11) NOT NULL,
  `naam` text NOT NULL,
  `functie` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `medewerkers`
--

INSERT INTO `medewerkers` (`id`, `naam`, `functie`) VALUES
(1, 'Karel Egbert', 'Software Engineer'),
(2, 'Gerard Joling', 'Conciërge'),
(3, 'Damion Gans', 'Drummer'),
(4, 'Guus Koper', 'Manager'),
(5, 'Stijn Bylandt', 'HR Manager');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `inkomen`
--
ALTER TABLE `inkomen`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `kosten`
--
ALTER TABLE `kosten`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `medewerkers`
--
ALTER TABLE `medewerkers`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `inkomen`
--
ALTER TABLE `inkomen`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `kosten`
--
ALTER TABLE `kosten`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `medewerkers`
--
ALTER TABLE `medewerkers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
