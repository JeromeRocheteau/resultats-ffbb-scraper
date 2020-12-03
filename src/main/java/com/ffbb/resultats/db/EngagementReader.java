package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ffbb.resultats.api.Engagement;

public class EngagementReader extends Reader<Engagement, Boolean> {

	@Override
	public String getScriptPath() {
		return "/engagement-select.sql";
	}

	@Override
	public Boolean getResult(ResultSet resultSet) throws Exception {
		Engagement engagement = this.getObject();
		if (resultSet.next()) {
			Long id = resultSet.getLong(1);
			engagement.setId(id);
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	@Override
	public void setParameters(PreparedStatement statement) throws Exception {
		Engagement engagement = this.getObject();
		statement.setLong(1, engagement.getOrganisation().getId());
		statement.setLong(2, engagement.getDivision().getId());
	}

}
