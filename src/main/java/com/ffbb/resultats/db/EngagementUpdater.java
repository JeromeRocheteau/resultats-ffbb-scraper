package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ffbb.resultats.api.Engagement;

public class EngagementUpdater extends Updater<Engagement, Boolean> {

	@Override
	public String getScriptPath() {
		return "/engagement-insert.sql";
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
		Engagement engagement = this.getObject();
		statement.setString(1, engagement.getCode());
		statement.setString(2, engagement.getOrganisation().getCode());
		statement.setString(3, engagement.getDivision().getCode());
	}
	
}