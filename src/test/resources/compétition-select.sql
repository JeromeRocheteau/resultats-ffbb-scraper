SELECT 
  c.`id`,
  c.`organisateur`,
  c.`type`,
  c.`genre`,
  c.`catégorie`
FROM `compétitions` AS c
WHERE c.`type` = ?
  AND c.`genre` = ?
  AND  c.`catégorie` = ?;