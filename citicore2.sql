-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : lun. 03 mars 2025 à 21:52
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
-- Base de données : `citicore2`
--

-- --------------------------------------------------------

--
-- Structure de la table `association`
--

CREATE TABLE `association` (
  `id_association` int(11) NOT NULL,
  `Nom` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `Telephone` varchar(15) NOT NULL,
  `Description` text DEFAULT NULL,
  `Adresse` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `association`
--

INSERT INTO `association` (`id_association`, `Nom`, `Email`, `Telephone`, `Description`, `Adresse`) VALUES
(2, 'We Love Bizerte', 'wlb@example.com', '123456789', 'Helping community.', 'Bizerte'),
(8, 'We Love Bizerte', 'wlbl@example.com', '123456789', 'test modification', 'Bizerte'),
(9, 'We Love Bizerte2', 'wlbml@example.com', '123456789', 'test modification', 'Bizerte'),
(18, 'croissant rouge', 'croissantrouge@gmail.com', '77000222', 'croissant rouge description', 'tunis'),
(19, 'associationss', 'association@gmail.com', '12345678', 'aaaa', 'aaa@gmail.com');

-- --------------------------------------------------------

--
-- Structure de la table `avis`
--

CREATE TABLE `avis` (
  `avis_id` int(11) NOT NULL,
  `Utilisateur_id` int(11) NOT NULL,
  `commentaire` text NOT NULL,
  `date_avis` datetime NOT NULL DEFAULT current_timestamp(),
  `Demande_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `categorie`
--

CREATE TABLE `categorie` (
  `categorie_id` int(11) NOT NULL,
  `nom_categorie` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `imageUrl` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `categorie`
--

INSERT INTO `categorie` (`categorie_id`, `nom_categorie`, `description`, `imageUrl`) VALUES
(11, 'Dons Alimentaires', 'Événements pour collecter et distribuer des denrées alimentaires', 'C:\\Achref\\3eme\\PI_DEV\\WorkshopJDBC\\src\\main\\resources\\images\\food-donations.jpg'),
(12, 'Dons de Vêtements', 'Événements pour collecter des vêtements pour les personnes dans le besoin', 'C:\\Achref\\3eme\\PI_DEV\\WorkshopJDBC\\src\\main\\resources\\images\\don-objet-vetement.jpg'),
(13, 'Aide aux Orphelins', 'Collectes pour soutenir les orphelins en matière d’éducation et de besoins quotidiens', 'C:\\Achref\\3eme\\PI_DEV\\WorkshopJDBC\\src\\main\\resources\\images\\images (4).jpg');

-- --------------------------------------------------------

--
-- Structure de la table `commande`
--

CREATE TABLE `commande` (
  `id_commande` int(11) NOT NULL,
  `date_commande` date NOT NULL DEFAULT curdate(),
  `status` varchar(200) NOT NULL DEFAULT 'en attente'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `commande`
--

INSERT INTO `commande` (`id_commande`, `date_commande`, `status`) VALUES
(1, '2025-02-19', 'rejeter'),
(2, '2025-02-19', 'accepter'),
(3, '2025-02-19', 'en attente'),
(4, '2025-02-19', 'en attente'),
(5, '2025-02-19', 'en attente'),
(6, '2025-02-19', 'en attente'),
(7, '2025-02-19', 'en attente'),
(8, '2025-02-19', 'en attente'),
(9, '2025-02-19', 'en attente'),
(10, '2025-02-19', 'en attente'),
(11, '2025-02-20', 'en attente'),
(12, '2025-02-20', 'en attente'),
(13, '2025-02-20', 'en attente'),
(14, '2025-02-20', 'en attente'),
(15, '2025-02-20', 'en attente'),
(16, '2025-02-20', 'en attente'),
(17, '2025-02-20', 'en attente'),
(18, '2025-02-20', 'en attente');

-- --------------------------------------------------------

--
-- Structure de la table `commande_produit`
--

CREATE TABLE `commande_produit` (
  `id_commande` int(11) NOT NULL,
  `id_produit` int(11) NOT NULL,
  `quantite` int(11) NOT NULL CHECK (`quantite` > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `commande_produit`
--

INSERT INTO `commande_produit` (`id_commande`, `id_produit`, `quantite`) VALUES
(16, 5, 12),
(17, 5, 1),
(17, 6, 3),
(18, 5, 3);

-- --------------------------------------------------------

--
-- Structure de la table `demande`
--

CREATE TABLE `demande` (
  `Demande_id` int(11) NOT NULL,
  `Utilisateur_id` int(11) NOT NULL,
  `contenu` text NOT NULL,
  `date_demande` date NOT NULL,
  `statut` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `evenement`
--

CREATE TABLE `evenement` (
  `id_evenement` int(11) NOT NULL,
  `nom_evenement` varchar(255) NOT NULL,
  `date_evenement` varchar(255) NOT NULL,
  `lieu_evenement` varchar(255) NOT NULL,
  `organisateur_id` int(11) NOT NULL,
  `categorie_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `evenement`
--

INSERT INTO `evenement` (`id_evenement`, `nom_evenement`, `date_evenement`, `lieu_evenement`, `organisateur_id`, `categorie_id`) VALUES
(50, 'Collecte Alimentaire Ramadan', '2025-03-15', 'Maison de la Solidarité, Tunis', 1, 11),
(51, 'Campagne Hiver Chaud', '2025-12-01', 'Centre Culturel de Sfax', 1, 12),
(53, 'Soutien aux Orphelins', '2025-08-20', 'Foyer Dar El Amal, Ariana', 1, 13);

-- --------------------------------------------------------

--
-- Structure de la table `feedback`
--

CREATE TABLE `feedback` (
  `id_FeedBack` int(11) NOT NULL,
  `contenu` text NOT NULL,
  `Cin_Participant` int(11) NOT NULL,
  `Cin_Organisateur` int(11) NOT NULL,
  `date_creation` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

CREATE TABLE `produit` (
  `id_produit` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `prix` decimal(10,2) NOT NULL,
  `vendeur_id` int(11) NOT NULL,
  `date_ajout` date NOT NULL,
  `photo` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `produit`
--

INSERT INTO `produit` (`id_produit`, `nom`, `description`, `prix`, `vendeur_id`, `date_ajout`, `photo`) VALUES
(5, 'colier', 'trés belle', 24.00, 2, '2025-02-11', 'C:\\Achref\\3eme\\PI_DEV\\WorkshopJDBC\\src\\images\\colier.png'),
(6, 'book', 'trés riche', 23.00, 2, '2025-02-21', 'C:\\Achref\\3eme\\PI_DEV\\WorkshopJDBC\\src\\images\\book1.jpg');

-- --------------------------------------------------------

--
-- Structure de la table `projet_don`
--

CREATE TABLE `projet_don` (
  `id_Projet_Don` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `montantRecu` decimal(10,2) NOT NULL,
  `objectif` decimal(10,2) NOT NULL,
  `date_debut` date NOT NULL,
  `date_fin` date NOT NULL,
  `id_association` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `projet_don`
--

INSERT INTO `projet_don` (`id_Projet_Don`, `nom`, `montantRecu`, `objectif`, `date_debut`, `date_fin`, `id_association`) VALUES
(32, 'test2', 0.00, 50000.00, '2025-02-18', '2025-02-20', 2),
(34, 'test3', 0.00, 10000.00, '2025-02-01', '2025-02-22', 2),
(35, 'projetdon111', 0.00, 10000.00, '2025-02-07', '2025-02-28', 9),
(36, 'ddd', 1002500.00, 200000.00, '2025-02-07', '2025-02-28', 19);

-- --------------------------------------------------------

--
-- Structure de la table `reclamation`
--

CREATE TABLE `reclamation` (
  `ID_Reclamation` int(11) NOT NULL,
  `Sujet` varchar(255) NOT NULL,
  `Description` text NOT NULL,
  `Date_Creation` datetime DEFAULT current_timestamp(),
  `Date_Resolution` datetime DEFAULT NULL,
  `Type_Reclamation` varchar(20) DEFAULT NULL CHECK (`Type_Reclamation` in ('Marketplace','Avis','ProjetDons','Demande','Evenement')),
  `Cin_Utilisateur` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `reclamation`
--

INSERT INTO `reclamation` (`ID_Reclamation`, `Sujet`, `Description`, `Date_Creation`, `Date_Resolution`, `Type_Reclamation`, `Cin_Utilisateur`) VALUES
(52, 'Marketplace', 'nouveau_Produit', '2025-02-20 04:31:58', '1970-01-01 01:00:00', 'Marketplace', 11675549),
(54, 'Event', 'nouveau_Evenement', '2025-02-20 04:31:58', '2025-02-22 20:43:55', 'Evenement', 14309087),
(55, 'Chyheeb', 'Medini', '2025-02-20 04:31:58', '1970-01-01 01:00:00', 'Evenement', 14309087),
(60, 'HOHOHO', 'HAHAHA', '2025-02-21 21:51:36', '2025-02-22 20:32:50', 'Avis', 14278077),
(63, 'Event', 'bienvenue', '2025-02-22 20:40:45', '1970-01-01 01:00:00', 'Evenement', 14278077),
(64, 'Welcome', 'EveryOne', '2025-02-23 22:29:22', '2025-02-23 22:31:56', 'ProjetDons', 11677720),
(65, 'GOODBye', 'nouveau_Demande', '2025-02-23 22:29:22', '1970-01-01 01:00:00', 'Demande', 14526156),
(66, 'Hello', 'Medini', '2025-02-23 22:29:22', '2025-02-23 22:31:14', 'Demande', 14309087),
(68, 'beehy', 'non', '2025-02-24 18:58:39', '2025-02-24 18:59:17', 'Marketplace', 14278077),
(76, 'API', 'Confirmé?', '2025-02-25 18:56:33', '2025-02-27 23:08:24', 'Demande', 14278077),
(77, 'Chiheb', 'Chou', '2025-02-25 19:01:21', '2025-02-25 19:01:37', 'Marketplace', 14278077),
(83, 'Bnsr', 'Bnjrr', '2025-03-03 21:14:27', '2025-03-03 21:25:14', 'Avis', 14278077);

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
(47, 55, 'bnjr', '2025-02-20 15:44:56', 'Rejetée'),
(48, 60, 'bbbbb', '2025-02-21 21:55:22', 'En Cours'),
(70, 60, 'cncnceuzn', '2025-02-21 23:01:03', 'En Cours'),
(71, 60, 'ahahahah', '2025-02-21 23:04:20', 'Traitée'),
(74, 60, 'hooooooohohoh', '2025-02-21 23:39:40', 'Traitée'),
(116, 54, 'SAyeee', '2025-02-22 00:00:00', 'Traitée'),
(117, 66, 'hahahahahhaha', '2025-02-23 22:31:14', 'Traitée'),
(118, 64, 'hohohohohohoho', '2025-02-23 22:31:56', 'En Cours'),
(120, 68, 'oui', '2025-02-24 18:59:17', 'En Cours'),
(122, 77, 'chouchou', '2025-02-25 19:01:37', 'Traitée'),
(127, 76, 'za3ma', '2025-02-27 23:02:34', 'En Cours'),
(128, 76, 'hahah', '2025-02-27 23:04:08', 'En Cours'),
(129, 76, 'w tw ?', '2025-02-27 23:08:24', 'En Cours');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `Cin` int(11) NOT NULL,
  `Nom` varchar(50) NOT NULL,
  `Prenom` varchar(50) NOT NULL,
  `Num_Tel` varchar(15) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Genre` enum('HOMME','FEMME','AUTRE') NOT NULL,
  `Photo_Utilisateur` varchar(255) NOT NULL,
  `Mot_De_Passe` varchar(255) NOT NULL,
  `Role` enum('Admin','Participant','Organisateur') NOT NULL DEFAULT 'Participant',
  `Token` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`Cin`, `Nom`, `Prenom`, `Num_Tel`, `Email`, `Genre`, `Photo_Utilisateur`, `Mot_De_Passe`, `Role`, `Token`) VALUES
(3000301, 'aaaaaaq', 'aqqa', '12345678', 'aaspjhj@gmail.com', 'HOMME', 'C:\\Achref\\3eme\\PI_DEV\\CitiCore-utilisateur\\src\\main\\resources\\ImageUser\\UserLogin.png', '000', 'Organisateur', '0'),
(11111111, 'aaaaaaq', 'aqqa', '12345678', 'aasp@gmail.com', 'HOMME', 'C:\\Achref\\3eme\\PI_DEV\\CitiCore-utilisateur\\src\\main\\resources\\ImageUser\\UserLogin.png', '000', 'Organisateur', '0'),
(11122233, '124', 'eeee', '28702766', 'aaa@gmail.com', 'HOMME', 'PHOOOOOOOOOOTOOO', '123', 'Participant', '0'),
(11675549, 'Dupont', 'Jean', '0612345678', 'jean.dupont@example.com', 'HOMME', 'C:\\Achref\\3eme\\PI_DEV\\CitiCore-utilisateur\\src\\main\\resources\\ImageUser\\UserLogin.png', '123', 'Admin', NULL),
(14523698, 'waddey', 'yass', '22702766', 'yass@gmail.com', 'FEMME', 'default.jpg', '123', 'Participant', '0'),
(14785236, 'azerty', 'azerty', '14785236', 'azerty@gmail.com', 'HOMME', 'default.jpg', 'azerty', 'Participant', '0');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `association`
--
ALTER TABLE `association`
  ADD PRIMARY KEY (`id_association`);

--
-- Index pour la table `commande`
--
ALTER TABLE `commande`
  ADD PRIMARY KEY (`id_commande`);

--
-- Index pour la table `commande_produit`
--
ALTER TABLE `commande_produit`
  ADD PRIMARY KEY (`id_commande`,`id_produit`),
  ADD KEY `id_produit` (`id_produit`);

--
-- Index pour la table `feedback`
--
ALTER TABLE `feedback`
  ADD PRIMARY KEY (`id_FeedBack`),
  ADD KEY `Cin_Participant` (`Cin_Participant`),
  ADD KEY `Cin_Organisateur` (`Cin_Organisateur`);

--
-- Index pour la table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`id_produit`);

--
-- Index pour la table `projet_don`
--
ALTER TABLE `projet_don`
  ADD PRIMARY KEY (`id_Projet_Don`),
  ADD KEY `id_association` (`id_association`);

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
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`Cin`),
  ADD UNIQUE KEY `Email` (`Email`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `association`
--
ALTER TABLE `association`
  MODIFY `id_association` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT pour la table `commande`
--
ALTER TABLE `commande`
  MODIFY `id_commande` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT pour la table `feedback`
--
ALTER TABLE `feedback`
  MODIFY `id_FeedBack` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `produit`
--
ALTER TABLE `produit`
  MODIFY `id_produit` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `projet_don`
--
ALTER TABLE `projet_don`
  MODIFY `id_Projet_Don` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- AUTO_INCREMENT pour la table `reclamation`
--
ALTER TABLE `reclamation`
  MODIFY `ID_Reclamation` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=84;

--
-- AUTO_INCREMENT pour la table `reponse`
--
ALTER TABLE `reponse`
  MODIFY `ID_Reponse` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=131;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `commande_produit`
--
ALTER TABLE `commande_produit`
  ADD CONSTRAINT `commande_produit_ibfk_1` FOREIGN KEY (`id_commande`) REFERENCES `commande` (`id_commande`) ON DELETE CASCADE,
  ADD CONSTRAINT `commande_produit_ibfk_2` FOREIGN KEY (`id_produit`) REFERENCES `produit` (`id_produit`) ON DELETE CASCADE;

--
-- Contraintes pour la table `feedback`
--
ALTER TABLE `feedback`
  ADD CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`Cin_Participant`) REFERENCES `utilisateur` (`Cin`),
  ADD CONSTRAINT `feedback_ibfk_2` FOREIGN KEY (`Cin_Organisateur`) REFERENCES `utilisateur` (`Cin`);

--
-- Contraintes pour la table `projet_don`
--
ALTER TABLE `projet_don`
  ADD CONSTRAINT `projet_don_ibfk_1` FOREIGN KEY (`id_association`) REFERENCES `association` (`id_association`);

--
-- Contraintes pour la table `reponse`
--
ALTER TABLE `reponse`
  ADD CONSTRAINT `idR` FOREIGN KEY (`ID_Reclamation`) REFERENCES `reclamation` (`ID_Reclamation`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
