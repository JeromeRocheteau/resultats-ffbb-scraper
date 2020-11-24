package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ffbb.resultats.api.Championnat;

public class ChampionnatReader extends Reader<Boolean> {

	private Championnat championnat;

	public ChampionnatReader(Championnat championnat) {
		this.championnat = championnat;
	}

	@Override
	public String getScriptPath() {
		return "/championnat-select.sql";
	}

	@Override
	public Boolean getResult(ResultSet resultSet) throws Exception {
		if (resultSet.next()) {
			Long id = resultSet.getLong(1);
			championnat.setId(id);
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	@Override
	public void setParameters(PreparedStatement statement) throws Exception {
		statement.setString(1, championnat.getCode());
		statement.setLong(2, championnat.getId());
		statement.setLong(3, championnat.getIndex());
	}

}
