-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: mysql
-- Generation Time: Dec 03, 2019 at 01:19 PM
-- Server version: 10.3.13-MariaDB-1:10.3.13+maria~bionic
-- PHP Version: 7.2.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `projet_sgbd`
--

-- --------------------------------------------------------

--
-- Table structure for table `Client`
--

CREATE TABLE `Client` (
  `idClient` int(11) NOT NULL,
  `nom` varchar(100) DEFAULT NULL,
  `prenom` varchar(100) DEFAULT NULL,
  `adresse` varchar(100) DEFAULT NULL,
  `reduction` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Client`
--

INSERT INTO `Client` (`idClient`, `nom`, `prenom`, `adresse`, `reduction`) VALUES
(1, 'Suchel', 'Tom', '26 Rue Jean Boyer', 0.1),
(2, 'Millochau', 'Olivier', '123 Rue Victor Hugo', 0.5);

-- --------------------------------------------------------

--
-- Table structure for table `FactureClient`
--

CREATE TABLE `FactureClient` (
  `idFactureClient` int(11) NOT NULL,
  `idClient` int(11) NOT NULL,
  `montant` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `FactureFournisseur`
--

CREATE TABLE `FactureFournisseur` (
  `idFactureFournisseur` int(11) NOT NULL,
  `idFournisseur` int(11) NOT NULL,
  `montant` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Fournisseur`
--

CREATE TABLE `Fournisseur` (
  `idFournisseur` int(11) NOT NULL,
  `nom` varchar(100) DEFAULT NULL,
  `prenom` varchar(100) DEFAULT NULL,
  `adresse` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Fournisseur`
--

INSERT INTO `Fournisseur` (`idFournisseur`, `nom`, `prenom`, `adresse`) VALUES
(1, 'Ferrari', 'Bruno', '99, rue des Dunes'),
(2, 'Dominique', 'Gabois', '50, rue Reine Elisabeth');

-- --------------------------------------------------------

--
-- Table structure for table `FournitProduit`
--

CREATE TABLE `FournitProduit` (
  `idFournisseur` int(11) NOT NULL,
  `nomProduit` varchar(100) NOT NULL,
  `prix` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `FournitProduit`
--

INSERT INTO `FournitProduit` (`idFournisseur`, `nomProduit`, `prix`) VALUES
(1, 'Fougere', 5),
(1, 'Herbe', 2),
(2, 'Coquelicot', 8),
(2, 'Rose', 12);

-- --------------------------------------------------------

--
-- Table structure for table `Produit`
--

CREATE TABLE `Produit` (
  `nom` varchar(100) NOT NULL,
  `categorie` varchar(100) DEFAULT NULL,
  `espece` varchar(100) DEFAULT NULL,
  `prix` int(11) DEFAULT 0,
  `quantite` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Produit`
--

INSERT INTO `Produit` (`nom`, `categorie`, `espece`, `prix`, `quantite`) VALUES
('Coquelicot', 'fleur', 'coquelicotus', 15, 18),
('Fougere', 'plante', 'fougerus ', 10, 4),
('Herbe', 'plante', 'herbasaurus', 3, 10),
('Rose', 'fleur', 'rosacae', 20, 0);

-- --------------------------------------------------------

--
-- Table structure for table `ProduitsFactureClient`
--

CREATE TABLE `ProduitsFactureClient` (
  `idFactureClient` int(11) NOT NULL,
  `nomProduit` varchar(100) NOT NULL,
  `quantite` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ProduitsFactureFournisseur`
--

CREATE TABLE `ProduitsFactureFournisseur` (
  `idFactureFournisseur` int(11) NOT NULL,
  `nomProduit` varchar(100) NOT NULL,
  `quantite` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Client`
--
ALTER TABLE `Client`
  ADD PRIMARY KEY (`idClient`);

--
-- Indexes for table `FactureClient`
--
ALTER TABLE `FactureClient`
  ADD PRIMARY KEY (`idFactureClient`),
  ADD KEY `ctClient` (`idClient`);

--
-- Indexes for table `FactureFournisseur`
--
ALTER TABLE `FactureFournisseur`
  ADD PRIMARY KEY (`idFactureFournisseur`),
  ADD KEY `ctFournisseur2` (`idFournisseur`);

--
-- Indexes for table `Fournisseur`
--
ALTER TABLE `Fournisseur`
  ADD PRIMARY KEY (`idFournisseur`);

--
-- Indexes for table `FournitProduit`
--
ALTER TABLE `FournitProduit`
  ADD PRIMARY KEY (`idFournisseur`,`nomProduit`),
  ADD KEY `ctProduit` (`nomProduit`);

--
-- Indexes for table `Produit`
--
ALTER TABLE `Produit`
  ADD PRIMARY KEY (`nom`);

--
-- Indexes for table `ProduitsFactureClient`
--
ALTER TABLE `ProduitsFactureClient`
  ADD PRIMARY KEY (`idFactureClient`,`nomProduit`),
  ADD KEY `ctProduit2` (`nomProduit`);

--
-- Indexes for table `ProduitsFactureFournisseur`
--
ALTER TABLE `ProduitsFactureFournisseur`
  ADD PRIMARY KEY (`idFactureFournisseur`,`nomProduit`),
  ADD KEY `ctProduit3` (`nomProduit`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Client`
--
ALTER TABLE `Client`
  MODIFY `idClient` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `FactureClient`
--
ALTER TABLE `FactureClient`
  MODIFY `idFactureClient` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `FactureFournisseur`
--
ALTER TABLE `FactureFournisseur`
  MODIFY `idFactureFournisseur` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `Fournisseur`
--
ALTER TABLE `Fournisseur`
  MODIFY `idFournisseur` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `FactureClient`
--
ALTER TABLE `FactureClient`
  ADD CONSTRAINT `ctClient` FOREIGN KEY (`idClient`) REFERENCES `Client` (`idClient`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `FactureFournisseur`
--
ALTER TABLE `FactureFournisseur`
  ADD CONSTRAINT `ctFournisseur2` FOREIGN KEY (`idFournisseur`) REFERENCES `Fournisseur` (`idFournisseur`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `FournitProduit`
--
ALTER TABLE `FournitProduit`
  ADD CONSTRAINT `ctFournisseur` FOREIGN KEY (`idFournisseur`) REFERENCES `Fournisseur` (`idFournisseur`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ctProduit` FOREIGN KEY (`nomProduit`) REFERENCES `Produit` (`nom`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `ProduitsFactureClient`
--
ALTER TABLE `ProduitsFactureClient`
  ADD CONSTRAINT `ctFactureClient` FOREIGN KEY (`idFactureClient`) REFERENCES `FactureClient` (`idFactureClient`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ctProduit2` FOREIGN KEY (`nomProduit`) REFERENCES `Produit` (`nom`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `ProduitsFactureFournisseur`
--
ALTER TABLE `ProduitsFactureFournisseur`
  ADD CONSTRAINT `ctFactureFournisseur` FOREIGN KEY (`idFactureFournisseur`) REFERENCES `FactureFournisseur` (`idFactureFournisseur`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ctProduit3` FOREIGN KEY (`nomProduit`) REFERENCES `Produit` (`nom`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
