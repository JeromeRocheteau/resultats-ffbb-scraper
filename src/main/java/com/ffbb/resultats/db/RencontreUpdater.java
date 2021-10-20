package com.ffbb.resultats.db;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

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
		statement.setDate(4, rencontre.getHoraire());
		statement.setString(5, rencontre.getDomicile().getCode());
		statement.setString(6, rencontre.getVisiteur().getCode());
		if (rencontre.getSalle() == null) {
			statement.setNull(7, Types.BIGINT);
		} else {
			statement.setLong(7, rencontre.getSalle().getId());
		}
	}
	
}