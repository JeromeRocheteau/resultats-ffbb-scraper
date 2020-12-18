package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ffbb.resultats.api.Organisation;
import com.ffbb.resultats.api.Salle;
import com.ffbb.resultats.api.Organisation.Type;

public class OrganisationReader extends Reader<String, Organisation> {

	@Override
	public String getScriptPath() {
		return "/organisation-select.sql";
	}

	@Override
	public Organisation getResult(ResultSet resultSet) throws Exception {
		if (resultSet.next()) {
			Long id = resultSet.getLong("id");
			String code = resultSet.getString("code");
			String type = resultSet.getString("type");
			String ffbb = resultSet.getString("ffbb");
			String nom = resultSet.getString("nom");
			Organisation organisation = new Organisation(id, code, Type.valueOf(type), ffbb, nom);
			Long salleId = resultSet.getLong("salleId");
			if (salleId != null) {
				Float salleLatitude = resultSet.getFloat("salleLatitude");
				Float salleLongitude = resultSet.getFloat("salleLongitude");
				String salleNom = resultSet.getString("salleNom");
				String salleAdresse = resultSet.getString("salleAdresse");
				String salleCodePostal = resultSet.getString("salleCodePostal");
				String salleVille = resultSet.getString("salleVille");
				Salle salle = new Salle(salleId, salleLatitude, salleLongitude, salleNom, salleAdresse, salleCodePostal, salleVille);
				organisation.setSalle(salle);
			}
			return organisation;
		} else {
			return null;
		}
	}

	@Override
	public void setParameters(PreparedStatement statement) throws Exception {
		String code = this.getObject();
		statement.setString(1, code);
	}

}
