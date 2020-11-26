SELECT 
  equ.`id`,
  eng.`organisation`,
  eng.`compétition`,
  equ.`nom`
FROM `équipes` AS equ
INNER JOIN `engagements` AS eng ON eng.`id` = equ.`id`
WHERE eng.`organisation` = ?
  AND eng.`compétition` = ?;