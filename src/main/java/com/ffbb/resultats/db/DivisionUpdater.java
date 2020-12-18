package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ffbb.resultats.api.Division;

public class DivisionUpdater extends Updater<Division, Boolean> {

	@Override
	public String getScriptPath() {
		return "/division-insert.sql";
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
		Division championnat = this.getObject();
		statement.setLong(1, championnat.getId());
		statement.setString(2, championnat.getCode());
		statement.setString(3, championnat.getChampionnat().getCode());
		statement.setString(4, championnat.getNom());
	}
	
}