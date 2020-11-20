SELECT 
  ch.`id`,
  co.`organisateur`,
  co.`type`,
  co.`genre`,
  co.`catégorie`,
  ch.`niveau`,
  ch.`phase`,
  ch.`division`,
  ch.`poule`
FROM `championnats` AS ch
INNER JOIN `compétitions` AS co on co.`id` = ch.`id`
WHERE co.`genre` = ?
  AND co.`catégorie` = ?
  AND ch.`niveau` = ?
  AND ch.`phase` = ?
  AND ch.`division` = ?
  AND ch.`poule` = ?;