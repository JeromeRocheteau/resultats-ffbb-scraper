SELECT 
  o.`id`,
  o.`code`,
  o.`type`,
  o.`ffbb`,
  o.`nom`
FROM `organisations` AS o
WHERE o.`code` = ?;
