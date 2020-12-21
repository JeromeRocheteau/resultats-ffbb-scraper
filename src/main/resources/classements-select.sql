SELECT 
  c.`date` AS `date`,
  c.`division` AS `division`,
  c.`équipe` AS `équipe`,
  c.`rang` AS `rang`,
  c.`points` AS `points`,
  c.`matchs` AS `matchs`,
  c.`victoires` AS `victoires`,
  c.`défaites` AS `défaites`,
  c.`nuls` AS `nuls`,
  c.`pour` AS `pour`,
  c.`contre` AS `contre`,
  c.`diff`  AS `diff`,
  
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
  cha.`phase` AS `championnatPhase`,
  
  equ.`code` AS `équipeCode`,
  equ.`nom` AS `équipeNom`,
  
  clu.`id` AS `clubId`,
  clu.`code` AS `clubCode`,
  clu.`type` AS `clubType`,
  clu.`ffbb` AS `clubFfbb`,
  clu.`nom` AS `clubNom`
  
FROM `classements` AS c
INNER JOIN `divisions` AS d ON d.`code` = c.`division` 
INNER JOIN `championnats` AS cha ON d.`championnat` = cha.`code`
INNER JOIN `compétitions` AS com ON com.`code` = cha.`code`
INNER JOIN `organisations` AS org ON org.`code` = com.`organisateur`
INNER JOIN `équipes` AS equ ON equ.`code` = c.`équipe` 
INNER JOIN `organisations` AS clu ON clu.`code` = equ.`organisation`
WHERE c.`division` = ?;