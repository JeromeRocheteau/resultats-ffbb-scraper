SELECT 
  com.`id` AS `compétitionId`,
  com.`code` AS `compétitionCode`,
  com.`type` AS `compétitionType`,
  com.`nom` AS `compétitionNom`,
  org.`id` AS `organisateurId`,
  org.`code` AS `organisateurCode`,
  org.`type` AS `organisateurType`,
  org.`ffbb` AS `organisateurFfbb`,
  org.`nom` AS `organisateurNom`,
  cha.`niveau` AS `championnatNiveau`,
  cha.`catégorie` AS `championnatCatégorie`,
  cha.`genre` AS `championnatGenre`,
  cha.`phase` AS `championnatPhase`
FROM `championnats` AS cha
INNER JOIN `compétitions` AS com ON com.`code` = cha.`code`
INNER JOIN `organisations` AS org ON org.`code` = com.`organisateur`
WHERE cha.`code` = ?;