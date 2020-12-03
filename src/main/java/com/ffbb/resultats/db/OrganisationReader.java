package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ffbb.resultats.api.Organisation;

public class OrganisationReader extends Reader<Organisation, Boolean> {

	@Override
	public String getScriptPath() {
		return "/organisation-select.sql";
	}

	@Override
	public Boolean getResult(ResultSet resultSet) throws Exception {
		Organisation organisation = this.getObject();
		if (resultSet.next()) {
			Long id = resultSet.getLong(1);
			organisation.setId(id);
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	@Override
	public void setParameters(PreparedStatement statement) throws Exception {
		Organisation organisation = this.getObject();
		statement.setString(1, organisation.getCode());
	}

}
