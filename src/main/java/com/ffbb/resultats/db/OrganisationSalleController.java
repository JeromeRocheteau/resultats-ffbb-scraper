package com.ffbb.resultats.db;

import java.net.URI;
import java.sql.Connection;

import com.ffbb.resultats.api.Organisation;

public class OrganisationSalleController implements Controller<Organisation> {

	private OrganisationReader reader;
	
	private OrganisationSalleUpdater updater;
	
	public OrganisationSalleController() {
		reader = new OrganisationReader();
		updater = new OrganisationSalleUpdater();
	}

	
	@Override
	public Organisation doFind(Connection connection, URI uri) throws Exception {
		String prefix = "https://resultats.ffbb.com/organisation/salle/";
		String link = uri.toString();
		String code = link.substring(prefix.length(), link.length() - 5);
		reader.setObject(code);
		return reader.doRead(connection);
	}

	@Override
	public void doSave(Connection connection, Organisation organisation) throws Exception {
		updater.setObject(organisation);
		updater.doUpdate(connection);
	}

}
