SELECT 
  e.`id`,
  e.`organisation`,
  e.`compétition`
FROM `enagements` AS e
WHERE e.`organisation` = ? 
  AND e.`compétition` = ?;