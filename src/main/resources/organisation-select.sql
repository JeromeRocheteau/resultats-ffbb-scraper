SELECT 
  o.`id` as `id`,
  o.`code` as `code`,
  o.`type` as `type`,
  o.`ffbb` as `ffbb`,
  o.`nom` as `nom`,
  s.`id` as `salleId`,
  s.`latitude` as `salleLatitude`,
  s.`longitude` as `salleLongitude`,
  s.`nom` as `salleNom`,
  s.`adresse` as `salleAdresse`,
  s.`codePostal` as `salleCodePostal`,
  s.`ville` as `salleVille`
FROM `organisations` AS o
LEFT JOIN `salles` AS s ON s.`id` = o.`salle`
WHERE o.`code` = ?;