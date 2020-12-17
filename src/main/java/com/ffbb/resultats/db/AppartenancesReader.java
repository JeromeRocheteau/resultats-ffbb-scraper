package com.ffbb.resultats.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ffbb.resultats.api.Appartenance;
import com.ffbb.resultats.api.Appartenances;
import com.ffbb.resultats.api.Organisation;

public class AppartenancesReader extends Reader<String, Appartenances> {

	@Override
	public String getScriptPath() {
		return "/appartenances-select.sql";
	}

	@Override
	public Appartenances getResult(ResultSet resultSet) throws Exception {
		Appartenances appartenances = null;
		while (resultSet.next()) {
			Long organisationId = resultSet.getLong("organisationId");
			String organisationCode = resultSet.getString("organisationCode");
			String organisationType = resultSet.getString("organisationType");
			String organisationFfbb = resultSet.getString("organisationFfbb");
			String organisationNom = resultSet.getString("organisationNom");
			Organisation organisation = new Organisation(organisationId, organisationCode, organisationType, organisationFfbb, organisationNom);
			Long structureId = resultSet.getLong("structureId");
			String structureCode = resultSet.getString("structureCode");
			String structureType = resultSet.getString("structureType");
			String structureFfbb = resultSet.getString("structureFfbb");
			String structureNom = resultSet.getString("structureNom");
			Organisation structure = new Organisation(structureId, structureCode, structureType, structureFfbb, structureNom);
			String type = resultSet.getString("type");
			Appartenance appartenance = new Appartenance(organisation, structure, type);
			if (appartenances == null) {
				appartenances = new Appartenances(organisation);
			}
			appartenances.add(appartenance);
		}
		return appartenances;
	}

	@Override
	public void setParameters(PreparedStatement statement) throws Exception {
		String code = this.getObject();
		statement.setString(1, code);
	}

}
