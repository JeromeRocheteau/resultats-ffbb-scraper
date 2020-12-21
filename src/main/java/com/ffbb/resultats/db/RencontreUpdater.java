package com.ffbb.resultats.db;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ffbb.resultats.api.Rencontre;

public class RencontreUpdater extends Updater<Rencontre, Boolean> {

	@Override
	public String getScriptPath() {
		return "/rencontre-insert.sql";
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
		Rencontre rencontre = this.getObject();
		statement.setString(1, rencontre.getCode());
		statement.setInt(2, rencontre.getNuméro());
		statement.setString(3, rencontre.getJournée().getCode());
		statement.setDate(4, new Date(rencontre.getHoraire().getTime()));
		statement.setString(5, rencontre.getDomicile().getCode());
		statement.setString(6, rencontre.getVisiteur().getCode());
		statement.setLong(7, rencontre.getSalle().getId());
	}
	
}