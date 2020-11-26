package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ffbb.resultats.api.Engagement;

public class EngagementReader extends Reader<Boolean> {

	private Engagement engagement;

	public EngagementReader(Engagement engagement) {
		this.engagement = engagement;
	}

	@Override
	public String getScriptPath() {
		return "/engagement-select.sql";
	}

	@Override
	public Boolean getResult(ResultSet resultSet) throws Exception {
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
		statement.setLong(1, engagement.getOrganisation().getId());
		statement.setLong(2, engagement.getDivision().getId());
	}

}
