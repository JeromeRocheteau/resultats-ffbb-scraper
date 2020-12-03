package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import com.ffbb.resultats.api.Salle;

public class SalleUpdater extends Updater<Salle, Boolean> {

	@Override
	public String getScriptPath() {
		return "/salle-insert.sql";
	}

	@Override
	public Boolean getResult(int count, ResultSet resultSet) throws Exception {
		if (count > 0) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	@Override
	public void setParameters(PreparedStatement statement) throws Exception {
		Salle salle = this.getObject();
		statement.setLong(1, salle.getId().longValue());
		statement.setFloat(2, salle.getLatitude().floatValue());
		statement.setFloat(3, salle.getLongitude().floatValue());
		statement.setString(4, salle.getNom());
		if (salle.getAdresse() == null) {
			statement.setNull(5, Types.VARCHAR);
		} else {
			statement.setString(5, salle.getAdresse());
		}
		statement.setString(6, salle.getCodePostal());
		statement.setString(7, salle.getVille());
	}
	
}