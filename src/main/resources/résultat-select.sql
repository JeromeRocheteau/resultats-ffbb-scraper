SELECT 
  r.`code` AS `code`,
  r.`domicile` AS `domicile`,
  r.`visiteur` AS `visisteur`
FROM `résultats` AS r
WHERE r.`code` = ?;