-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3308
-- Généré le : mar. 21 juin 2022 à 11:44
-- Version du serveur : 5.7.36
-- Version de PHP : 7.4.26

SET FOREIGN_KEY_CHECKS=0;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `keunotor`
--
CREATE DATABASE IF NOT EXISTS `keunotor` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `keunotor`;

-- --------------------------------------------------------

--
-- Structure de la table `client_sheet`
--

DROP TABLE IF EXISTS `client_sheet`;
CREATE TABLE IF NOT EXISTS `client_sheet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `mobile_number` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `street_number` int(11) NOT NULL,
  `street` varchar(255) NOT NULL,
  `zip_code` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `client_sheet`
--

INSERT INTO `client_sheet` (`id`, `first_name`, `last_name`, `email`, `mobile_number`, `city`, `street_number`, `street`, `zip_code`) VALUES
(2, 'Jane', 'Smith', 'jsmith@acme.com', '06.22.17.15.79', 'Rennes', 12, 'Rue de Nantes', '1000'),
(21, 'Raphaël', 'Mercier', 'raphaelboitecours@gmail.com', '0635397037', 'Goven', 37, 'Les Allanteries', '35580');

-- --------------------------------------------------------

--
-- Structure de la table `order_detail`
--

DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE IF NOT EXISTS `order_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vendorname` varchar(45) DEFAULT NULL,
  `client_sheet_id` int(11) NOT NULL,
  `status` varchar(45) DEFAULT 'Proccessing',
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `order_detail`
--

INSERT INTO `order_detail` (`id`, `vendorname`, `client_sheet_id`, `status`, `date`) VALUES
(27, 'STAFF1', 21, 'Refund', '2022-06-21 00:11:31'),
(28, 'STAFF1', 2, 'Proccessing', '2022-06-21 00:11:31'),
(30, 'STAFF1', 21, 'Delivered', '2022-06-21 00:11:31'),
(31, 'STAFF1', 2, 'Proccessing', '2022-06-21 10:36:09'),
(34, 'STAFF1', 2, 'Proccessing', '2022-06-21 11:33:18');

-- --------------------------------------------------------

--
-- Structure de la table `order_detail_has_product_sheet`
--

DROP TABLE IF EXISTS `order_detail_has_product_sheet`;
CREATE TABLE IF NOT EXISTS `order_detail_has_product_sheet` (
  `order_detail_id` int(11) NOT NULL,
  `product_sheet_id` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `order_detail_has_product_sheet`
--

INSERT INTO `order_detail_has_product_sheet` (`order_detail_id`, `product_sheet_id`) VALUES
(28, 5),
(28, 2),
(30, 2),
(30, 5),
(27, 2),
(31, 2),
(34, 5),
(34, 2);

-- --------------------------------------------------------

--
-- Structure de la table `product_sheet`
--

DROP TABLE IF EXISTS `product_sheet`;
CREATE TABLE IF NOT EXISTS `product_sheet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `release_date` varchar(255) DEFAULT NULL,
  `description` text,
  `pegi` int(11) DEFAULT NULL,
  `grade` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `platform` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `product_sheet`
--

INSERT INTO `product_sheet` (`id`, `name`, `release_date`, `description`, `pegi`, `grade`, `type`, `platform`) VALUES
(2, 'Borderlands 1', 'October 2009', 'The game takes place on a fictional planet called Pandora. Once exploited by large companies for its hidden alien technology and mining resources, the planet was left to be abandoned by these companies, and is now plagued by bandits and dangerous wildlife. According to a legend, an \"Ark\" is located on Pandora, containing all the treasures of the universe. This legend attracts many \"Ark hunters\" to Pandora, ready to do anything to find and open it. The game allows the player to embody four of them in their quest to find the Ark, which will be both helped by the natives, and yet threatened by the hostile nature of the planet.', 18, 17, 'FPS, Hack\'n\'slash, Action-RPG, Shooter Looter', 'PlayStation 3, Nintendo Switch, Xbox 360, PC'),
(5, 'Super Metroid', 'March 19 1994', 'During her previous mission, Samus Aran had cleared the planet SR388 of all its metroids, keeping only one copy for research. In a secret laboratory of the Academy of Space Sciences, researchers are conducting a series of studies on the larva. Samus is on her way back to her planet when an alert comes from the research laboratories. When she arrives, the guards and the research team have been eliminated and the metroid has disappeared. Thanks to a recording, she discovers the cause of the disaster. Space pirates broke in to take possession of the larva. The outlaws are holed up on Zebes, led by Mother Brain\'s former disciple, Ridley. For the third time, Samus must reclaim the peace of the galaxy.', 7, 19, 'Action-adventure', 'SNES');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `login`, `password`, `role`) VALUES
(11, 'Jack', '$2a$10$Bp1xLXFt6sR5.Ug1/ox.zuf88mUT4xFKEba7SdS1AFbGzpLXkY1Ay', 'ROLE_STAFF'),
(9, 'Andrea', '$2a$10$8VSrZfsQmF0dFPx85ttHTuhDqWvrgB.pYhbLu1DZGBekmG5ToQ2uO', 'ROLE_ADMIN'),
(18, 'ADMIN1', '$2a$10$N5aBDBcyLSYxTjNzRWGh4uR78SmhDq/.dld1yAndylmL03QYCM7ru', 'ROLE_ADMIN'),
(15, 'ZERO', '$2a$10$Lxw8Z/LMS1epKiEZLP2cquAS6PkjMi3w2SVvvcTFwHajnetk.Diii', 'ROLE_ADMIN'),
(16, 'SALER1', '$2a$10$cQfiGSXT21FEqKrhnsr3P.GWUW2.vNrMB7JJmP9albOFljTdYxWhy', 'ROLE_SALESREP'),
(17, 'STAFF1', '$2a$10$nuOxRvOut29eD2ForgYNXeVdMjPqdLIlb5aHY814Myigs0tygtzxK', 'ROLE_STAFF');
SET FOREIGN_KEY_CHECKS=1;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
