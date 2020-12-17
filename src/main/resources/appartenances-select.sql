SELECT 
  o.`id` as `organisationId`,
  o.`code` as `organisationCode`,
  o.`type` as `organisationType`,
  o.`ffbb` as `organisationFfbb`,
  o.`nom` as `organisationNom`,
  s.`id` as `structureId`,
  s.`code` as `structureCode`,
  s.`type` as `structureType`,
  s.`ffbb` as `structureFfbb`,
  s.`nom` as `structureNom`,
  a.`type` as `type`
FROM `appartenances` as a
INNER JOIN `organisations` AS o ON o.`code`= a.`organisation`
INNER JOIN `organisations` AS s ON s.`code`= a.`structure`
WHERE a.`organisation` = ?;