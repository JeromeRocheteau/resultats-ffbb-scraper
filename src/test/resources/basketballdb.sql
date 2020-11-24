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
  `id` bigint(20) NOT NULL,
  `salle` bigint(20) DEFAULT NULL,
  `code` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `ffbb` varchar(45) NOT NULL,
  `nom` varchar(90) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  FOREIGN KEY (`salle`) REFERENCES `salles` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE `appartenances` (
  `organisation` bigint(20) NOT NULL,
  `structure` bigint(20) NOT NULL,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`organisation`,`structure`)
);

CREATE TABLE `compétitions` (
  `id` bigint(20) NOT NULL,
  `code` varchar(45) NOT NULL,
  `organisateur` bigint(20) DEFAULT NULL,
  `type` varchar(45) NOT NULL,
  `genre` varchar(45) NOT NULL,
  `catégorie` varchar(45) NOT NULL,
  `nom` varchar(90) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  FOREIGN KEY (`organisateur`) REFERENCES `organisations` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE `championnats` (
  `id` bigint(20) NOT NULL,
  `compétition` bigint(20) DEFAULT NULL,
  `niveau` varchar(45) NOT NULL,
  `phase` int(11) NOT NULL,
  `poule` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`compétition`) REFERENCES `compétitions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `engagements` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `organisation` bigint(20) NOT NULL,
  `compétition` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`organisation`) REFERENCES `organisations` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`compétition`) REFERENCES `compétitions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `équipes` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(180) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`id`) REFERENCES `engagements` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `rencontres` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `compétition` bigint(20) NOT NULL,
  `journée` int(11) NOT NULL,
  `horaire` datetime DEFAULT NULL,
  `domicile` bigint(20) DEFAULT NULL,
  `visiteur` bigint(20) DEFAULT NULL,
  `salle` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`compétition`) REFERENCES `compétitions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`domicile`) REFERENCES `équipes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`visiteur`) REFERENCES `équipes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`salle`) REFERENCES `salles` (`id`) ON DELETE CASCADE ON UPDATE SET NULL
);

CREATE TABLE `résultats` (
  `id` bigint(20) NOT NULL,
  `domicile` int(11) NOT NULL,
  `visiteur` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`id`) REFERENCES `rencontres` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE `résultats`;
DROP TABLE `rencontres`;
DROP TABLE `équipes`;
DROP TABLE `engagements`;
DROP TABLE `championnats`;
DROP TABLE `compétitions`;
DROP TABLE `appartenances`;
DROP TABLE `organisations`;
DROP TABLE `salles`;