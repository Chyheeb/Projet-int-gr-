-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : jeu. 20 fév. 2025 à 07:47
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `horus`
--

-- --------------------------------------------------------

--
-- Structure de la table `reclamation`
--

CREATE TABLE `reclamation` (
  `ID_Reclamation` int(11) NOT NULL,
  `Sujet` varchar(255) NOT NULL,
  `Description` text NOT NULL,
  `Date_Creation` datetime DEFAULT current_timestamp(),
  `Date_Resolution` datetime NOT NULL,
  `Type_Reclamation` varchar(20) DEFAULT NULL CHECK (`Type_Reclamation` in ('Marketplace','Avis','ProjetDons','Demande','Evenement')),
  `Cin_Utilisateur` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `reclamation`
--

INSERT INTO `reclamation` (`ID_Reclamation`, `Sujet`, `Description`, `Date_Creation`, `Date_Resolution`, `Type_Reclamation`, `Cin_Utilisateur`) VALUES
(39, 'Projetdons', 'nouveau_Evenement', '2025-02-19 17:24:06', '1970-01-01 01:00:00', 'ProjetDons', 11677720),
(40, 'Demande', 'Avis', '2025-02-19 17:24:06', '1970-01-01 01:00:00', 'ProjetDons', 11675549),
(47, 'Bilel', 'chyheeb', '2025-02-19 23:44:46', '1970-01-01 01:00:00', 'Marketplace', 14278077),
(49, 'Chyheeeb', 'Bolbolllll', '2025-02-20 01:03:48', '1970-01-01 01:00:00', 'ProjetDons', 14278077),
(50, 'dons', 'nouveau_Avis', '2025-02-20 04:31:57', '1970-01-01 01:00:00', 'Avis', 14278077),
(51, 'Projetdons', 'nouveau_Evenement', '2025-02-20 04:31:58', '1970-01-01 01:00:00', 'ProjetDons', 11677720),
(52, 'Marketplace', 'nouveau_Produit', '2025-02-20 04:31:58', '1970-01-01 01:00:00', 'Marketplace', 11675549),
(53, 'Demande', 'nouveau_Demande', '2025-02-20 04:31:58', '1970-01-01 01:00:00', 'Demande', 14526156),
(54, 'Event', 'nouveau_Evenement', '2025-02-20 04:31:58', '1970-01-01 01:00:00', 'Evenement', 14309087),
(55, 'Chyheeb', 'Medini', '2025-02-20 04:31:58', '1970-01-01 01:00:00', 'Evenement', 14309087);

-- --------------------------------------------------------

--
-- Structure de la table `reponse`
--

CREATE TABLE `reponse` (
  `ID_Reponse` int(11) NOT NULL,
  `ID_Reclamation` int(11) NOT NULL,
  `Contenu` text NOT NULL,
  `Date_Reponse` datetime DEFAULT current_timestamp(),
  `Statut` varchar(20) DEFAULT NULL CHECK (`Statut` in ('Traitée','En Cours','Rejetée'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `reponse`
--

INSERT INTO `reponse` (`ID_Reponse`, `ID_Reclamation`, `Contenu`, `Date_Reponse`, `Statut`) VALUES
(31, 52, 'Projet', '2025-02-20 00:00:00', 'Rejetée'),
(34, 55, 'Event', '2025-02-20 00:00:00', 'En Cours'),
(43, 55, 'Demande', '2025-02-20 00:00:00', 'Traitée'),
(45, 50, 'Chyheeb', '2025-02-20 00:00:00', 'Traitée');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `reclamation`
--
ALTER TABLE `reclamation`
  ADD PRIMARY KEY (`ID_Reclamation`),
  ADD KEY `Cin_Utilisateur` (`Cin_Utilisateur`);

--
-- Index pour la table `reponse`
--
ALTER TABLE `reponse`
  ADD PRIMARY KEY (`ID_Reponse`),
  ADD KEY `idR` (`ID_Reclamation`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `reclamation`
--
ALTER TABLE `reclamation`
  MODIFY `ID_Reclamation` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=59;

--
-- AUTO_INCREMENT pour la table `reponse`
--
ALTER TABLE `reponse`
  MODIFY `ID_Reponse` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `reponse`
--
ALTER TABLE `reponse`
  ADD CONSTRAINT `idR` FOREIGN KEY (`ID_Reclamation`) REFERENCES `reclamation` (`ID_Reclamation`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
