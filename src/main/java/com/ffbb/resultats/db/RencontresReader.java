package com.ffbb.resultats.db;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ffbb.resultats.api.Catégorie;
import com.ffbb.resultats.api.Championnat;
import com.ffbb.resultats.api.Division;
import com.ffbb.resultats.api.Genre;
import com.ffbb.resultats.api.Journée;
import com.ffbb.resultats.api.Niveau;
import com.ffbb.resultats.api.Organisation;
import com.ffbb.resultats.api.Rencontre;
import com.ffbb.resultats.api.Rencontres;
import com.ffbb.resultats.api.Résultat;
import com.ffbb.resultats.api.Salle;
import com.ffbb.resultats.api.Équipe;

public class RencontresReader extends Reader<String, Rencontres> {

	@Override
	public String getScriptPath() {
		return "/rencontres-select.sql";
	}

	@Override
	public Rencontres getResult(ResultSet resultSet) throws Exception {
		Rencontres rencontres = null;
		while (resultSet.next()) {
			// String rencontreCode = resultSet.getString("rencontreCode");
			Integer rencontreNuméro = resultSet.getInt("rencontreNuméro");
			Date rencontreHoraire = resultSet.getDate("rencontreHoraire");
			
			String scoreCode = resultSet.getString("scoreCode");
			Integer scoreDomicile = resultSet.getInt("scoreDomicile");
			Integer scoreVisiteur = resultSet.getInt("scoreVisiteur");

			// String journéeCode = resultSet.getString("journéeCode");
			Integer journéeNuméro = resultSet.getInt("journéeNuméro");
			
			Long divisionId = resultSet.getLong("divisionId");
			String divisionCode = resultSet.getString("divisionCode");
			String divisionNom = resultSet.getString("divisionNom");
			
			Long compétitionId = resultSet.getLong("compétitionId");
			String compétitionCode = resultSet.getString("compétitionCode");
			String compétitionNom = resultSet.getString("compétitionNom");
			
			Long organisateurId = resultSet.getLong("organisateurId");
			String organisateurCode = resultSet.getString("organisateurCode");
			String organisateurType = resultSet.getString("organisateurType");
			String organisateurFfbb = resultSet.getString("organisateurFfbb");
			String organisateurNom = resultSet.getString("organisateurNom");
			
			String championnatNiveau = resultSet.getString("championnatNiveau");
			String championnatCatégorie = resultSet.getString("championnatCatégorie");
			String championnatGenre = resultSet.getString("championnatGenre");
			Integer championnatPhase = resultSet.getInt("championnatPhase");
			
			// String équipeDomicileCode = resultSet.getString("équipeDomicileCode");
			String équipeDomicileNom = resultSet.getString("équipeDomicileNom");
			Long clubDomicileId = resultSet.getLong("clubDomicileId");
			String clubDomicileCode = resultSet.getString("clubDomicileCode");
			String clubDomicileType = resultSet.getString("clubDomicileType");
			String clubDomicileFfbb = resultSet.getString("clubDomicileFfbb");
			String clubDomicileNom = resultSet.getString("clubDomicileNom");
			
			// String équipeVisiteurCode = resultSet.getString("équipeVisiteurCode");
			String équipeVisiteurNom = resultSet.getString("équipeVisiteurNom");
			Long clubVisiteurId = resultSet.getLong("clubVisiteurId");
			String clubVisiteurCode = resultSet.getString("clubVisiteurCode");
			String clubVisiteurType = resultSet.getString("clubVisiteurType");
			String clubVisiteurFfbb = resultSet.getString("clubVisiteurFfbb");
			String clubVisiteurNom = resultSet.getString("clubVisiteurNom");
			
			Long salleId = resultSet.getLong("salleId");
			Float salleLatitude = resultSet.getFloat("salleLatitude");
			Float salleLongitude = resultSet.getFloat("salleLongitude");
			String salleNom = resultSet.getString("salleNom");
			String salleAdresse = resultSet.getString("salleAdresse");
			String salleCodePostal = resultSet.getString("salleCodePostal");
			String salleVille = resultSet.getString("salleVille");
			
			Organisation organisateur = new Organisation(organisateurId, organisateurCode, Organisation.Type.valueOf(organisateurType), organisateurFfbb, organisateurNom);			
			
			Championnat championnat = new Championnat(compétitionId, compétitionCode, compétitionNom, organisateur);
			championnat.setNiveau(Niveau.valueOf(championnatNiveau));
			championnat.setCatégorie(Catégorie.valueOf(championnatCatégorie));
			championnat.setGenre(Genre.valueOf(championnatGenre));
			championnat.setPhase(championnatPhase);
			
			Division division = new Division(divisionId, divisionCode, divisionNom, championnat);
			
			Journée journée = new Journée(journéeNuméro, division);
			
			Organisation clubDomicile = new Organisation(clubDomicileId, clubDomicileCode, Organisation.Type.valueOf(clubDomicileType), clubDomicileFfbb, clubDomicileNom);
			
			Équipe équipeDomicile = new Équipe(clubDomicile, division);
			// équipeDomicile.setCode(équipeDomicileCode);
			équipeDomicile.setNom(équipeDomicileNom);
			
			Organisation clubVisiteur = new Organisation(clubVisiteurId, clubVisiteurCode, Organisation.Type.valueOf(clubVisiteurType), clubVisiteurFfbb, clubVisiteurNom);
			
			Équipe équipeVisiteur = new Équipe(clubVisiteur, division);
			// équipeVisiteur.setCode(équipeVisiteurCode);
			équipeVisiteur.setNom(équipeVisiteurNom);
			
			Salle salle = new Salle(salleId, salleLatitude, salleLongitude, salleNom, salleAdresse, salleCodePostal, salleVille);
			
			if (rencontres == null) {
				rencontres = new Rencontres(journée);
			}
			
			Rencontre rencontre = new Rencontre(journée, rencontreNuméro, équipeDomicile, équipeVisiteur, rencontreHoraire, salle);
			
			Résultat résultat = scoreCode == null ? null : new Résultat(rencontre, scoreDomicile, scoreVisiteur);
			rencontre.setRésultat(résultat);
			
			rencontres.add(rencontre);
		}
		return rencontres;
	}

	@Override
	public void setParameters(PreparedStatement statement) throws Exception {
		String code = this.getObject();
		statement.setString(1, code);
	}

}
