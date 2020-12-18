package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ffbb.resultats.api.Catégorie;
import com.ffbb.resultats.api.Championnat;
import com.ffbb.resultats.api.Division;
import com.ffbb.resultats.api.Genre;
import com.ffbb.resultats.api.Journée;
import com.ffbb.resultats.api.Journées;
import com.ffbb.resultats.api.Niveau;
import com.ffbb.resultats.api.Organisation;

public class JournéesReader extends Reader<String, Journées> {

	@Override
	public String getScriptPath() {
		return "/journées-select.sql";
	}

	@Override
	public Journées getResult(ResultSet resultSet) throws Exception {
		Journées journées = null;
		while (resultSet.next()) {
			Integer numéro = resultSet.getInt("journéeNuméro");
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
			Division division = new Division(divisionId, divisionCode, divisionNom, championnat);
			Journée journée = new Journée(numéro, division);
			if (journées == null) {
				journées = new Journées(division);
			}
			journées.add(journée);
		}
		return journées;
	}

	@Override
	public void setParameters(PreparedStatement statement) throws Exception {
		String code = this.getObject();
		statement.setString(1, code);
	}

}
