package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ffbb.resultats.api.Appartenance;

public class AppartenanceUpdater extends Updater<Appartenance, Boolean> {

	@Override
	public String getScriptPath() {
		return "/appartenance-insert.sql";
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
		Appartenance appartenance = this.getObject();
		statement.setString(1, appartenance.getOrganisation().getCode());
		statement.setString(2, appartenance.getStructure().getCode());
		statement.setString(3, appartenance.getType().name());
	}

}
