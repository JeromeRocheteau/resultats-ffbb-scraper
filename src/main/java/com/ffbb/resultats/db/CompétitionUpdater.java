package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import com.ffbb.resultats.api.Compétition;

public class CompétitionUpdater extends Updater<Compétition,Long> {

	@Override
	public String getScriptPath() {
		return "/compétition-insert.sql";
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
		Compétition compétition = this.getObject();
		statement.setLong(1, compétition.getId());
		statement.setString(2, compétition.getCode());
		if (compétition.getOrganisateur() == null || compétition.getOrganisateur().getId() == null) {
			statement.setNull(3, Types.BIGINT);
		} else {
			statement.setLong(3, compétition.getOrganisateur().getId());
		}
		statement.setString(4, compétition.getType().name());
		// statement.setString(5, compétition.getGenre().name());
		// statement.setString(6, compétition.getCatégorie().name());
		statement.setString(7, compétition.getNom());
	}
	
}