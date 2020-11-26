package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ffbb.resultats.api.Résultat;

public class RésultatReader extends Reader<Boolean> {

	private Résultat résultat;

	public RésultatReader(Résultat résultat) {
		this.résultat = résultat;
	}

	@Override
	public String getScriptPath() {
		return "/résultat-select.sql";
	}

	@Override
	public Boolean getResult(ResultSet resultSet) throws Exception {
		if (resultSet.next()) {
			Long id = resultSet.getLong(1);
			résultat.setId(id);
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	@Override
	public void setParameters(PreparedStatement statement) throws Exception {
		statement.setLong(1, résultat.getId());
	}

}
