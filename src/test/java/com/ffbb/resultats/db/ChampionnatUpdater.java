package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ffbb.resultats.api.Championnat;

public class ChampionnatUpdater extends Updater<Boolean> {

	private Championnat championnat;
	
	public ChampionnatUpdater(Championnat championnat) {
		this.championnat = championnat;
	}

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
		statement.setLong(1, championnat.getId());
		statement.setString(2, championnat.getNiveau().name());
		statement.setInt(3, championnat.getPhase());
		statement.setInt(4, championnat.getDivision());
		statement.setString(5, championnat.getPoule());
	}
	
}