package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ffbb.resultats.api.Organisation;

public class OrganisationSalleUpdater extends Updater<Organisation, Boolean> {

	@Override
	public String getScriptPath() {
		return "/organisation-update.sql";
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
		Organisation organisation = this.getObject();
		statement.setLong(1, organisation.getSalle().getId());
		statement.setString(2, organisation.getCode());
	}
	
}