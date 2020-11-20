SELECT 
  r.`id`,
  r.`domicile`,
  r.`visiteur`
FROM `résultats` AS r
WHERE r.`id` = ?;