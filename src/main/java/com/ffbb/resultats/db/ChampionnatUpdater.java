package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ffbb.resultats.api.Championnat;

public class ChampionnatUpdater extends Updater<Championnat, Boolean> {

	@Override
	public String getScriptPath() {
		return "/championnat-insert.sql";
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
		Championnat championnat = this.getObject();
		statement.setLong(1, championnat.getId());
		statement.setLong(2, 0L);
		// FIXME statement.setString(3, championnat.getNiveau().name());
		// statement.setString(4, championnat.getPoule());
	}
	
}