package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ffbb.resultats.api.Équipe;

public class ÉquipeUpdater extends Updater<Boolean> {

	private Équipe équipe;
	
	public ÉquipeUpdater(Équipe équipe) {
		this.équipe = équipe;
	}

	@Override
	public String getScriptPath() {
		return "/équipe-insert.sql";
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
		statement.setLong(1, équipe.getId());
		statement.setString(2, équipe.getNom());
	}
	
}