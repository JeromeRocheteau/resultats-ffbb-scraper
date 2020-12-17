SELECT 
  o.`id` as `id`,
  o.`code` as `code`,
  o.`type` as `type`,
  o.`ffbb` as `ffbb`,
  o.`nom` as `nom`
FROM `organisations` AS o
WHERE o.`code` = ?;