package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ffbb.resultats.api.Résultat;

public class RésultatUpdater extends Updater<Résultat, Boolean> {

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
		Résultat résultat = this.getObject();
		statement.setString(1, résultat.getRencontre().getCode());
		statement.setInt(2, résultat.getDomicile());
		statement.setInt(3, résultat.getVisiteur());
	}
	
}