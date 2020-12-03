package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ffbb.resultats.api.Salle;

public class SalleReader extends Reader<Salle, Boolean> {

	@Override
	public String getScriptPath() {
		return "/salle-select.sql";
	}

	@Override
	public Boolean getResult(ResultSet resultSet) throws Exception {
		Salle salle = this.getObject();
		if (resultSet.next()) {
			Long id = resultSet.getLong(1);
			salle.setId(id);
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	@Override
	public void setParameters(PreparedStatement statement) throws Exception {
		Salle salle = this.getObject();
		statement.setLong(1, salle.getId().longValue());
	}
	
}
