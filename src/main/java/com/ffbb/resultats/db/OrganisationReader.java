package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ffbb.resultats.api.Organisation;
import com.ffbb.resultats.api.Organisation.Type;

public class OrganisationReader extends Reader<String, Organisation> {

	@Override
	public String getScriptPath() {
		return "/organisation-select.sql";
	}

	@Override
	public Organisation getResult(ResultSet resultSet) throws Exception {
		if (resultSet.next()) {
			Long id = resultSet.getLong("id");
			String code = resultSet.getString("code");
			String type = resultSet.getString("type");
			String ffbb = resultSet.getString("ffbb");
			String nom = resultSet.getString("nom");
			return new Organisation(id, code, Type.valueOf(type), ffbb, nom);
		} else {
			return null;
		}
	}

	@Override
	public void setParameters(PreparedStatement statement) throws Exception {
		String code = this.getObject();
		statement.setString(1, code);
	}

}
