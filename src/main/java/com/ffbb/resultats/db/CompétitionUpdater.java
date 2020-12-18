package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ffbb.resultats.api.Compétition;

public class CompétitionUpdater extends Updater<Compétition, Boolean> {

	@Override
	public String getScriptPath() {
		return "/compétition-insert.sql";
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
		Compétition compétition = this.getObject();
		statement.setLong(1, compétition.getId());
		statement.setString(2, compétition.getCode());
		statement.setString(3, compétition.getOrganisateur().getCode());
		statement.setString(4, compétition.getType().name());
		statement.setString(5, compétition.getNom());
	}
	
}