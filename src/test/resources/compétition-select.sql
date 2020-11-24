SELECT 
  c.`id`,
  c.`code`,
  c.`organisateur`,
  c.`type`,
  c.`genre`,
  c.`catégorie`,
  c.`nom`
FROM `compétitions` AS c
WHERE c.`code` = ?
  AND c.`id` = ?;