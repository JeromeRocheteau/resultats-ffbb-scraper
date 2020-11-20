package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import com.ffbb.resultats.api.Organisation;

public class OrganisationUpdater extends Updater<Boolean> {

	private Organisation organisation;
	
	public OrganisationUpdater(Organisation organisation) {
		this.organisation = organisation;
	}

	@Override
	public String getScriptPath() {
		return "/organisation-insert.sql";
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
		statement.setString(1, organisation.getCode());
		statement.setString(2, organisation.getType().name());
		if (organisation.getFfbb() == null) {
			statement.setNull(3, Types.VARCHAR);
		} else {
			statement.setString(3, organisation.getFfbb());			
		}
		statement.setString(4, organisation.getName());
	}
	
}