CREATE TABLE `salles` (
  `id` bigint(20) NOT NULL,
  `latitude` float NOT NULL,
  `longitude` float NOT NULL,
  `nom` varchar(180) NOT NULL,
  `adresse` varchar(90) DEFAULT NULL,
  `codePostal` varchar(45) NOT NULL,
  `ville` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `coordonnées` (`latitude`,`longitude`,`nom`),
  UNIQUE KEY `dénomination` (`ville`,`codePostal`,`nom`)
);

CREATE TABLE `organisations` (
  `id` bigint(20) DEFAULT NULL,
  `salle` bigint(20) DEFAULT NULL,
  `code` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `ffbb` varchar(45) NOT NULL,
  `nom` varchar(90) NOT NULL,
  PRIMARY KEY (`code`),
  UNIQUE KEY `id` (`id`),
  FOREIGN KEY (`salle`) REFERENCES `salles` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE `appartenances` (
  `organisation` varchar(45) NOT NULL,
  `structure` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`organisation`,`structure`),
  FOREIGN KEY (`organisation`) REFERENCES `organisations` (`code`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`structure`) REFERENCES `organisations` (`code`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `compétitions` (
  `id` bigint(20) NOT NULL,
  `code` varchar(45) NOT NULL,
  `organisateur` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `nom` varchar(90) NOT NULL,
  PRIMARY KEY (`code`),
  UNIQUE KEY `id` (`id`),
  FOREIGN KEY (`organisateur`) REFERENCES `organisations` (`code`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `championnats` (
  `code` varchar(45) NOT NULL,
  `niveau` varchar(45) NOT NULL,
  `catégorie` varchar(45) NOT NULL,
  `genre` varchar(45) NOT NULL,
  `phase` int(11) DEFAULT NULL,
  PRIMARY KEY (`code`),
  FOREIGN KEY (`code`) REFERENCES `compétitions` (`code`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `divisions` (
  `id` bigint(20) NOT NULL,
  `code` varchar(45) NOT NULL,
  `nom` varchar(45) NOT NULL,
  `championnat` varchar(45) NOT NULL,
  PRIMARY KEY (`code`),
  UNIQUE KEY `id` (`id`),
  FOREIGN KEY (`championnat`) REFERENCES `championnats` (`code`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `équipes` (
  `code` varchar(90) NOT NULL,
  `organisation` varchar(45) NOT NULL,
  `division` varchar(45) NOT NULL,
  `nom` varchar(180) DEFAULT NULL,
  PRIMARY KEY (`code`),
  UNIQUE KEY `équipe` (`organisation`,`division`),
  FOREIGN KEY (`organisation`) REFERENCES `organisations` (`code`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`division`) REFERENCES `divisions` (`code`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `journées` (
  `code` varchar(45) NOT NULL,
  `numéro` int(11) NOT NULL,
  `division` varchar(45) NOT NULL,
  PRIMARY KEY (`code`),
  UNIQUE KEY `journée` (`numéro`,`division`),
  FOREIGN KEY (`division`) REFERENCES `divisions` (`code`) ON DELETE CASCADE ON UPDATE CASCADE
);

/***************** DOING ********************/

CREATE TABLE `rencontres` (
  `code` varchar(45) NOT NULL,
  `numéro` int(11) NOT NULL,
  `journée` varchar(45) NOT NULL,
  `horaire` datetime NOT NULL,
  `domicile` varchar(90) NOT NULL,
  `visiteur` varchar(90) NOT NULL,
  `salle` bigint(20) NOT NULL,
  PRIMARY KEY (`code`),
  FOREIGN KEY (`journée`) REFERENCES `journées` (`code`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`domicile`) REFERENCES `équipes` (`code`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`visiteur`) REFERENCES `équipes` (`code`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`salle`) REFERENCES `salles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);
/***************** TODO ********************/
CREATE TABLE `résultats` (
  `id` bigint(20) NOT NULL,
  `domicile` int(11) NOT NULL,
  `visiteur` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`id`) REFERENCES `rencontres` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE `résultats`;
DROP TABLE `rencontres`;
DROP TABLE `journées`;
DROP TABLE `équipes`;
DROP TABLE `divisions`;
DROP TABLE `championnats`;
DROP TABLE `compétitions`;
DROP TABLE `appartenances`;
DROP TABLE `organisations`;
DROP TABLE `salles`;