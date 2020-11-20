SELECT s.`id`,
    s.`latitude`,
    s.`longitude`,
    s.`nom`,
    s.`adresse`,
    s.`codePostal`,
    s.`ville`
FROM `salles` AS s
WHERE s.`id` = ?;
