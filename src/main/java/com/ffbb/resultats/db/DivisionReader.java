package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ffbb.resultats.api.Catégorie;
import com.ffbb.resultats.api.Championnat;
import com.ffbb.resultats.api.Division;
import com.ffbb.resultats.api.Genre;
import com.ffbb.resultats.api.Niveau;
import com.ffbb.resultats.api.Organisation;
import com.ffbb.resultats.api.Paramètres;

public class DivisionReader extends Reader<Paramètres, Division> {

	@Override
	public String getScriptPath() {
		return "/division-select.sql";
	}

	@Override
	public Division getResult(ResultSet resultSet) throws Exception {
		if (resultSet.next()) {
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
			Organisation organisateur = new Organisation(organisateurId, organisateurCode, Organisation.Type.valueOf(organisateurType), organisateurFfbb, organisateurNom);			
			Championnat championnat = new Championnat(compétitionId, compétitionCode, compétitionNom, organisateur);
			championnat.setNiveau(Niveau.valueOf(championnatNiveau));
			championnat.setCatégorie(Catégorie.valueOf(championnatCatégorie));
			championnat.setGenre(Genre.valueOf(championnatGenre));
			championnat.setPhase(championnatPhase);
			return new Division(divisionId, divisionCode, divisionNom, championnat);
		} else {
			return null;
		}
	}

	@Override
	public void setParameters(PreparedStatement statement) throws Exception {
		Paramètres paramètres = this.getObject();
		statement.setString(1, paramètres.getCode());
		statement.setLong(2, paramètres.getId());
		statement.setLong(3, paramètres.getDivision());
	}

}
