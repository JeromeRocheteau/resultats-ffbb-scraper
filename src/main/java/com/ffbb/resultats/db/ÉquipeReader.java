package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ffbb.resultats.api.Équipe;

public class ÉquipeReader extends Reader<Équipe, Boolean> {

	@Override
	public String getScriptPath() {
		return "/équipe-select.sql";
	}

	@Override
	public Boolean getResult(ResultSet resultSet) throws Exception {
		Équipe équipe = this.getObject();
		if (resultSet.next()) {
			Long id = resultSet.getLong(1);
			équipe.setId(id);
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	@Override
	public void setParameters(PreparedStatement statement) throws Exception {
		Équipe équipe = this.getObject();
		statement.setLong(1, équipe.getOrganisation().getId());
		statement.setLong(2, équipe.getDivision().getId());
	}

}
