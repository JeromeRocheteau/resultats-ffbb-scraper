package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ffbb.resultats.api.Catégorie;
import com.ffbb.resultats.api.Championnat;
import com.ffbb.resultats.api.Division;
import com.ffbb.resultats.api.Engagements;
import com.ffbb.resultats.api.Genre;
import com.ffbb.resultats.api.Niveau;
import com.ffbb.resultats.api.Organisation;
import com.ffbb.resultats.api.Équipe;

public class EngagementsReader extends Reader<String, Engagements> {

	@Override
	public String getScriptPath() {
		return "/engagements-select.sql";
	}

	@Override
	public Engagements getResult(ResultSet resultSet) throws Exception {
		Engagements engagements = null;
		while (resultSet.next()) {
			String équipeCode = resultSet.getString("équipeCode");
			String équipeNom = resultSet.getString("équipeNom");
			Long organisationId = resultSet.getLong("organisationId");
			String organisationCode = resultSet.getString("organisationCode");
			String organisationType = resultSet.getString("organisationType");
			String organisationFfbb = resultSet.getString("organisationFfbb");
			String organisationNom = resultSet.getString("organisationNom");
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
			Organisation organisation = new Organisation(organisationId, organisationCode, Organisation.Type.valueOf(organisationType), organisationFfbb, organisationNom);
			Organisation organisateur = new Organisation(organisateurId, organisateurCode, Organisation.Type.valueOf(organisateurType), organisateurFfbb, organisateurNom);			
			Championnat championnat = new Championnat(compétitionId, compétitionCode, compétitionNom, organisateur);
			championnat.setNiveau(Niveau.valueOf(championnatNiveau));
			championnat.setCatégorie(Catégorie.valueOf(championnatCatégorie));
			championnat.setGenre(Genre.valueOf(championnatGenre));
			championnat.setPhase(championnatPhase);
			Division division = new Division(divisionId, divisionCode, divisionNom, championnat);
			Équipe équipe = new Équipe(organisation, division);
			équipe.setCode(équipeCode);
			équipe.setNom(équipeNom);
			if (engagements == null) {
				engagements = new Engagements(organisation);
			}
			engagements.add(équipe);
		}
		return engagements;
	}

	@Override
	public void setParameters(PreparedStatement statement) throws Exception {
		String code = this.getObject();
		statement.setString(1, code);
	}

}
