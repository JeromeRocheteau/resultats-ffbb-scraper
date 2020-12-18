package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

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
		statement.setString(1, championnat.getCode());
		statement.setString(2, championnat.getNiveau().name());
		statement.setString(3, championnat.getCatégorie().name());
		statement.setString(4, championnat.getGenre().name());
		if (championnat.getPhase() == null) {
			statement.setNull(5, Types.INTEGER);
		} else {
			statement.setInt(5, championnat.getPhase());
		}
	}
	
}