-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  Dim 05 juil. 2020 à 02:13
-- Version du serveur :  5.7.24
-- Version de PHP :  7.2.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `phpapi_asp`
--

-- --------------------------------------------------------

--
-- Structure de la table `personne`
--

DROP TABLE IF EXISTS `personne`;
CREATE TABLE IF NOT EXISTS `personne` (
  `IdPersonne` int(11) NOT NULL AUTO_INCREMENT,
  `NomPrenomPersonne` varchar(255) NOT NULL,
  `AdressePersonne` varchar(255) NOT NULL,
  `TelPersonne` varchar(255) NOT NULL,
  `EmailPersonne` varchar(255) NOT NULL,
  `DateNaissancePersonne` date NOT NULL,
  PRIMARY KEY (`IdPersonne`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `personne`
--

INSERT INTO `personne` (`IdPersonne`, `NomPrenomPersonne`, `AdressePersonne`, `TelPersonne`, `EmailPersonne`, `DateNaissancePersonne`) VALUES
(4, 'Awa ly', 'OuestFoir', '74400000004', 'aly@yopmail.com', '2020-07-05'),
(2, 'Aida Gueye', 'Guediawaye', '71111111111', 'agueye@yopmail.com', '2020-07-13'),
(3, 'Moussa Ndiaye', 'Golf Sud', '72222222222', 'mndiaye@yopmail.com', '2020-07-08'),
(7, 'Bathie sow', 'OuestFoir', '7888888888', 'bsow@yopmail.com', '2020-07-03'),
(6, 'Djibi Gueye', 'Mbeuleukhe', '7000009999', 'dgueye@yopmail.com', '2020-07-22'),
(9, 'Mouhamadou', 'Mermoz', '7888888888', 'mouha@yopmail.com', '2020-07-05');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
