package com.ffbb.resultats.db;

import java.net.URI;
import java.sql.Connection;

import com.ffbb.resultats.api.Salle;

public class SalleController implements Controller<Salle> {

	private SalleReader reader;
	
	private SalleUpdater updater;
	
	public SalleController() {
		reader = new SalleReader();
		updater = new SalleUpdater();
	}
	
	@Override
	public Salle doFind(Connection connection, URI uri) throws Exception {
		String link = uri.toString();
		int prefix = "https://resultats.ffbb.com/here/here_popup.php?id=".length();
		String code = link.substring(prefix);
		Long id = Long.valueOf(code);
		reader.setObject(id);
		return reader.doRead(connection);
	}

	@Override
	public void doSave(Connection connection, Salle salle) throws Exception {
		updater.setObject(salle);
		updater.doUpdate(connection);
	}

}
