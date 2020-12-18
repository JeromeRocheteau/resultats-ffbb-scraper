package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import com.ffbb.resultats.api.Équipe;

public class ÉquipeUpdater extends Updater<Équipe, Boolean> {

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
		Équipe équipe = this.getObject();
		statement.setString(1, équipe.getCode());
		statement.setString(2, équipe.getOrganisation().getCode());
		statement.setString(3, équipe.getDivision().getCode());
		if (équipe.getNom() == null) {
			statement.setNull(4, Types.VARCHAR);
		} else {
			statement.setString(4, équipe.getNom());
		}
	}
	
}