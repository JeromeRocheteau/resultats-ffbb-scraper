package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ffbb.resultats.api.Compétition;

public class CompétitionReader extends Reader<Compétition, Boolean> {

	@Override
	public String getScriptPath() {
		return "/compétition-select.sql";
	}

	@Override
	public Boolean getResult(ResultSet resultSet) throws Exception {
		Compétition compétition = this.getObject();
		if (resultSet.next()) {
			Long id = resultSet.getLong(1);
			compétition.setId(id);
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	@Override
	public void setParameters(PreparedStatement statement) throws Exception {
		Compétition compétition = this.getObject();
		statement.setString(1, compétition.getCode());
		statement.setLong(2, compétition.getId());
	}

}
