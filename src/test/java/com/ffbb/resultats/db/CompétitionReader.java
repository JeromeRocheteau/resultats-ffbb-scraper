package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ffbb.resultats.api.Compétition;

public class CompétitionReader extends Reader<Boolean> {

	private Compétition compétition;

	public CompétitionReader(Compétition compétition) {
		this.compétition = compétition;
	}

	@Override
	public String getScriptPath() {
		return "/compétition-select.sql";
	}

	@Override
	public Boolean getResult(ResultSet resultSet) throws Exception {
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
		statement.setString(1, compétition.getCode());
		statement.setLong(2, compétition.getId());
	}

}
