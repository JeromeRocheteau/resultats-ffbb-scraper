package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ffbb.resultats.api.Catégorie;
import com.ffbb.resultats.api.Championnat;
import com.ffbb.resultats.api.Classement;
import com.ffbb.resultats.api.Classements;
import com.ffbb.resultats.api.Division;
import com.ffbb.resultats.api.Genre;
import com.ffbb.resultats.api.Niveau;
import com.ffbb.resultats.api.Organisation;
import com.ffbb.resultats.api.Équipe;

public class ClassementsReader extends Reader<String, Classements> {

	@Override
	public String getScriptPath() {
		return "/classements-select.sql";
	}

	@Override
	public Classements getResult(ResultSet resultSet) throws Exception {
		Classements classements = null;
		while (resultSet.next()) {
			
			Integer rang = resultSet.getInt("rang");
			Integer points = resultSet.getInt("points");
			Integer matchs = resultSet.getInt("matchs");
			Integer victoires = resultSet.getInt("victoires");
			Integer défaites = resultSet.getInt("défaites");
			Integer nuls = resultSet.getInt("nuls");
			Integer pour = resultSet.getInt("pour");
			Integer contre = resultSet.getInt("contre");
			Integer diff = resultSet.getInt("diff");
			
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
			
			// String équipeCode = resultSet.getString("équipeCode");
			String équipeNom = resultSet.getString("équipeNom");
			Long clubId = resultSet.getLong("clubId");
			String clubCode = resultSet.getString("clubCode");
			String clubType = resultSet.getString("clubType");
			String clubFfbb = resultSet.getString("clubFfbb");
			String clubNom = resultSet.getString("clubNom");
			
			Organisation organisateur = new Organisation(organisateurId, organisateurCode, Organisation.Type.valueOf(organisateurType), organisateurFfbb, organisateurNom);			
			Championnat championnat = new Championnat(compétitionId, compétitionCode, compétitionNom, organisateur);
			championnat.setNiveau(Niveau.valueOf(championnatNiveau));
			championnat.setCatégorie(Catégorie.valueOf(championnatCatégorie));
			championnat.setGenre(Genre.valueOf(championnatGenre));
			championnat.setPhase(championnatPhase);
			Division division = new Division(divisionId, divisionCode, divisionNom, championnat);
			
			Organisation club = new Organisation(clubId, clubCode, Organisation.Type.valueOf(clubType), clubFfbb, clubNom);
			
			Équipe équipe = new Équipe(club, division);
			// équipeDomicile.setCode(équipeDomicileCode);
			équipe.setNom(équipeNom);
			
			Classement classement = new Classement(division, équipe, rang, points, matchs, victoires, défaites, nuls, pour, contre, diff);
			
			if (classements == null) {
				classements = new Classements(division);
			}
			classements.add(classement);
		}
		return classements;
	}

	@Override
	public void setParameters(PreparedStatement statement) throws Exception {
		String code = this.getObject();
		statement.setString(1, code);
	}

}
