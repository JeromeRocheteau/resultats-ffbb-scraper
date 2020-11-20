package com.ffbb.resultats.db;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ffbb.resultats.api.Rencontre;

public class RencontreReader extends Reader<Boolean> {

	private Rencontre rencontre;

	public RencontreReader(Rencontre rencontre) {
		this.rencontre = rencontre;
	}

	@Override
	public String getScriptPath() {
		return "/rencontre-select.sql";
	}

	@Override
	public Boolean getResult(ResultSet resultSet) throws Exception {
		if (resultSet.next()) {
			Long id = resultSet.getLong(1);
			rencontre.setId(id);
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	@Override
	public void setParameters(PreparedStatement statement) throws Exception {
		statement.setLong(1, rencontre.getCompétition().getId());
		statement.setInt(2, rencontre.getJournée());
		statement.setDate(3, new Date(rencontre.getHoraire().getTime()));
		statement.setLong(4, rencontre.getDomicile().getId());
		statement.setLong(5, rencontre.getVisiteur().getId());
	}

}
