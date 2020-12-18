package com.ffbb.resultats.db;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import com.ffbb.resultats.api.Rencontre;

public class RencontreUpdater extends Updater<Rencontre, Long> {

	@Override
	public String getScriptPath() {
		return "/rencontre-insert.sql";
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
		Rencontre rencontre = this.getObject();
		// statement.setLong(1, rencontre.getCompétition().getId());
		// statement.setInt(2, rencontre.getJournée());
		statement.setDate(3, new Date(rencontre.getHoraire().getTime()));
		// statement.setLong(4, rencontre.getDomicile().getId());
		// statement.setLong(5, rencontre.getVisiteur().getId());
		if (rencontre.getSalle() == null) {
			statement.setNull(6, Types.BIGINT);
		} else {
			statement.setLong(6, rencontre.getSalle().getId());	
		}
	}
	
}