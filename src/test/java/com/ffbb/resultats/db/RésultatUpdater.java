package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ffbb.resultats.api.Résultat;

public class RésultatUpdater extends Updater<Boolean> {

	private Résultat résultat;
	
	public RésultatUpdater(Résultat résultat) {
		this.résultat = résultat;
	}

	@Override
	public String getScriptPath() {
		return "/résultat-insert.sql";
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
		statement.setLong(1, résultat.getId());
		statement.setInt(2, résultat.getDomicile());
		statement.setInt(3, résultat.getVisiteur());
	}
	
}