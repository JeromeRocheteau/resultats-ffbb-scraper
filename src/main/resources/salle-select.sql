SELECT s.`id` as `id`,
    s.`latitude` as `latitude`,
    s.`longitude` as `longitude`,
    s.`nom` as `nom`,
    s.`adresse` as `adresse`,
    s.`codePostal` as `codePostal`,
    s.`ville` as `ville`
FROM `salles` AS s
WHERE s.`id` = ?;