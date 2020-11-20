CREATE TABLE `organisations` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `ffbb` varchar(45) DEFAULT NULL,
  `nom` varchar(90) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
);

CREATE TABLE `compétitions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `organisateur` bigint(20) DEFAULT NULL,
  `type` varchar(45) NOT NULL,
  `genre` varchar(45) NOT NULL,
  `catégorie` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`organisateur`) REFERENCES `organisations` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE `championnats` (
  `id` bigint(20) NOT NULL,
  `niveau` varchar(45) NOT NULL,
  `phase` int(11) NOT NULL,
  `division` int(11) NOT NULL,
  `poule` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`id`) REFERENCES `compétitions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
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

CREATE TABLE `salles` (
  `id` bigint(20) NOT NULL,
  `latitude` float NOT NULL,
  `longitude` float NOT NULL,
  `nom` varchar(180) NOT NULL,
  `adresse` varchar(90) NOT NULL,
  `codePostal` varchar(45) NOT NULL,
  `ville` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `coordonnées` (`latitude`,`longitude`,`nom`),
  UNIQUE KEY `dénomination` (`ville`,`codePostal`,`nom`)
);

CREATE TABLE `rencontres` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `compétition` bigint(20) NOT NULL,
  `journée` int(11) NOT NULL,
  `horaire` datetime NOT NULL,
  `domicile` bigint(20) NOT NULL,
  `visiteur` bigint(20) NOT NULL,
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
