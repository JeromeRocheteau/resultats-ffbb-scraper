SELECT 
  ch.`id`,
  co.`id`,
  co.`code`,
  co.`organisateur`,
  co.`type`,
  co.`genre`,
  co.`catégorie`,
  co.`nom`,
  ch.`niveau`,
  ch.`phase`,
  ch.`poule`
FROM `championnats` AS ch
INNER JOIN `compétitions` AS co on co.`id` = ch.`compétition`
WHERE co.`code` = ?
  AND co.`id` = ?
  AND ch.`id` = ?;