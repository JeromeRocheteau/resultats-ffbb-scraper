package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ffbb.resultats.api.Classement;

public class ClassementUpdater extends Updater<Classement, Boolean> {

	@Override
	public String getScriptPath() {
		return "/classement-insert.sql";
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
		Classement classement = this.getObject();
		statement.setString(1, classement.getDivision().getCode());
		statement.setString(2, classement.getÉquipe().getCode());
		statement.setInt(3, classement.getRang());
		statement.setInt(4, classement.getPoints());
		statement.setInt(5, classement.getMatchs());
		statement.setInt(6, classement.getVictoires());
		statement.setInt(7, classement.getDéfaites());
		statement.setInt(8, classement.getNuls());
		statement.setInt(9, classement.getPour());
		statement.setInt(10, classement.getContre());
		statement.setInt(11, classement.getDiff());
	}

}
