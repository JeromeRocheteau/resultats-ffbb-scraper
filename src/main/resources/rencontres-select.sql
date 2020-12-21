SELECT
  r.`code` AS `rencontreCode`,
  r.`numéro` AS `rencontreNuméro`,
  r.`horaire` AS `rencontreHoraire`,
  
  res.`code` AS `scoreCode`,
  res.`domicile` AS `scoreDomicile`,
  res.`visiteur` AS `scoreVisiteur`,

  j.`code` AS `journéeCode`,
  j.`numéro` AS `journéeNuméro`,
  
  d.`id` AS `divisionId`,
  d.`code` AS `divisionCode`,
  d.`nom` AS `divisionNom`,
  
  com.`id` AS `compétitionId`,
  com.`code` AS `compétitionCode`,
  com.`type` AS `compétitionType`,
  com.`nom` AS `compétitionNom`,
  
  cha.`niveau` AS `championnatNiveau`,
  cha.`catégorie` AS `championnatCatégorie`,
  cha.`genre` AS `championnatGenre`,
  cha.`phase` AS `championnatPhase`,
  
  org.`id` AS `organisateurId`,
  org.`code` AS `organisateurCode`,
  org.`type` AS `organisateurType`,
  org.`ffbb` AS `organisateurFfbb`,
  org.`nom` AS `organisateurNom`,

  dom.`code` AS `équipeDomicileCode`,
  dom.`nom` AS `équipeDomicileNom`,
  domorg.`id` AS `clubDomicileId`,
  domorg.`code` AS `clubDomicileCode`,
  domorg.`type` AS `clubDomicileType`,
  domorg.`ffbb` AS `clubDomicileFfbb`,
  domorg.`nom` AS `clubDomicileNom`,
  
  vis.`code` AS `équipeVisiteurCode`,
  vis.`nom` AS `équipeVisiteurNom`,
  visorg.`id` AS `clubVisiteurId`,
  visorg.`code` AS `clubVisiteurCode`,
  visorg.`type` AS `clubVisiteurType`,
  visorg.`ffbb` AS `clubVisiteurFfbb`,
  visorg.`nom` AS `clubVisiteurNom`,
  
  s.`id` as `salleId`,
  s.`latitude` as `salleLatitude`,
  s.`longitude` as `salleLongitude`,
  s.`nom` as `salleNom`,
  s.`adresse` as `salleAdresse`,
  s.`codePostal` as `salleCodePostal`,
  s.`ville` as `salleVille`
  
FROM `rencontres` AS r 
INNER JOIN `journées` AS j ON j.`code` = r.`journée` 
INNER JOIN `divisions` AS d ON d.`code` = j.`division`
INNER JOIN `championnats` AS cha ON d.`championnat` = cha.`code`
INNER JOIN `compétitions` AS com ON com.`code` = cha.`code`
INNER JOIN `organisations` AS org ON org.`code` = com.`organisateur` 
INNER JOIN `équipes` AS dom ON dom.`code` = r.`domicile` 
INNER JOIN `équipes` AS vis ON vis.`code` = r.`visiteur`
INNER JOIN `organisations` AS domorg ON domorg.`code` = dom.`organisation`
INNER JOIN `organisations` AS visorg ON visorg.`code` = vis.`organisation`
INNER JOIN `salles` AS s ON s.`id` = r.`salle`
LEFT JOIN `résultats` AS res on res.`code` = r.`code` 
WHERE r.`journée`= ?;