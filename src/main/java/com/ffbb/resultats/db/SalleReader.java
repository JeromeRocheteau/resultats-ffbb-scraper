package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ffbb.resultats.api.Salle;

public class SalleReader extends Reader<Long, Salle> {

	@Override
	public String getScriptPath() {
		return "/salle-select.sql";
	}

	@Override
	public Salle getResult(ResultSet resultSet) throws Exception {
		if (resultSet.next()) {
			Long id = resultSet.getLong("id");
			Float latitude = resultSet.getFloat("latitude");
			Float longitude = resultSet.getFloat("longitude");
			String nom = resultSet.getString("nom");
			String adresse = resultSet.getString("adresse");
			String codePostal = resultSet.getString("codePostal");
			String ville = resultSet.getString("ville");
			return new Salle(id, latitude, longitude, nom, adresse, codePostal, ville);
		} else {
			return null;
		}
	}

	@Override
	public void setParameters(PreparedStatement statement) throws Exception {
		Long id = this.getObject();
		statement.setLong(1, id);
	}
	
}
