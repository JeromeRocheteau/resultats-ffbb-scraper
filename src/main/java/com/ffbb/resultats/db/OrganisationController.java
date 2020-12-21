package com.ffbb.resultats.db;

import java.net.URI;
import java.sql.Connection;

import com.ffbb.resultats.api.Organisation;

public class OrganisationController implements Controller<Organisation> {

	private OrganisationReader reader;
	
	private OrganisationUpdater updater;
	
	public OrganisationController() {
		reader = new OrganisationReader();
		updater = new OrganisationUpdater();
	}

	
	@Override
	public Organisation doFind(Connection connection, URI uri) throws Exception {
		String link = uri.toString();
		int prefix = "https://resultats.ffbb.com/organisation/".length();
		int suffix = ".html".length();
		String code = link.substring(prefix, link.length() - suffix);
		reader.setObject(code);
		return reader.doRead(connection);
	}

	@Override
	public void doSave(Connection connection, Organisation organisation) throws Exception {
		updater.setObject(organisation);
		updater.doUpdate(connection);
	}

}
