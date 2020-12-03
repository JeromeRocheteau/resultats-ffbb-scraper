package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ffbb.resultats.api.Engagement;

public class EngagementUpdater extends Updater<Engagement, Long> {

	@Override
	public String getScriptPath() {
		return "/engagement-insert.sql";
	}

	@Override
	public Long getResult(int count, ResultSet resultSet) throws Exception {
		if (count > 0) {
			if (resultSet.next()) {
				return resultSet.getLong(1);
			} else {
				return 0L;	
			}
		} else {
			return 0L;
		}
	}

	@Override
	public void setParameters(PreparedStatement statement) throws Exception {
		Engagement engagement = this.getObject();
		statement.setLong(1, engagement.getOrganisation().getId());
		statement.setLong(2, engagement.getDivision().getId());
	}
	
}