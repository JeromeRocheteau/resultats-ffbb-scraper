SELECT 
  r.`id`,
  r.`compétition`,
  r.`journée`,
  r.`horaire`,
  r.`domicile`,
  r.`visiteur`,
  r.`salle`
FROM `rencontres` AS r
WHERE r.`compétition` = ?
  AND r.`journée` = ?
  AND r.`horaire` = ?
  AND r.`domicile` = ?
  AND r.`visiteur` = ?;
