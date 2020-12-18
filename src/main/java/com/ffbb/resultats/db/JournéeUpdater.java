package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ffbb.resultats.api.Journée;

public class JournéeUpdater extends Updater<Journée, Boolean> {

	@Override
	public String getScriptPath() {
		return "/journée-insert.sql";
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
		Journée journée = this.getObject();
		statement.setString(1, journée.getCode());
		statement.setInt(2, journée.getNuméro());
		statement.setString(3, journée.getDivision().getCode());
	}

}
