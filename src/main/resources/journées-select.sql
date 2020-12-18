SELECT 
  j.`code` AS `journéeCode`,
  j.`numéro` AS `journéeNuméro`,
  d.`id` AS `divisionId`,
  d.`code` AS `divisionCode`,
  d.`nom` AS `divisionNom`,
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
FROM `journées` AS j
INNER JOIN `divisions` AS d ON d.`code` = j.`division` 
INNER JOIN `championnats` AS cha ON d.`championnat` = cha.`code`
INNER JOIN `compétitions` AS com ON com.`code` = cha.`code`
INNER JOIN `organisations` AS org ON org.`code` = com.`organisateur`
WHERE d.`code` = ?;