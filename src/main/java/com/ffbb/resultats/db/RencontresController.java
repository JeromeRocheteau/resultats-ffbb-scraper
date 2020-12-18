package com.ffbb.resultats.db;

import java.net.URI;
import java.sql.Connection;

import com.ffbb.resultats.api.Rencontre;
import com.ffbb.resultats.api.Rencontres;

public class RencontresController implements Controller<Rencontres> {

	private RencontresReader reader;
	
	private RencontreUpdater updater;
	
	public RencontresController() {
		reader = new RencontresReader();
		updater = new RencontreUpdater();
	}
	
	@Override
	public Rencontres doFind(Connection connection, URI uri) throws Exception {
		String link = uri.toString();
		int prefix = "https://resultats.ffbb.com/championnat/rencontres/".length();
		int suffix = ".html".length();
		String code = link.substring(prefix, link.length() - suffix);
		reader.setObject(code);
		return reader.doRead(connection);
	}

	@Override
	public void doSave(Connection connection, Rencontres rencontres) throws Exception {
		for (Rencontre encontre : rencontres) {
			updater.setObject(encontre);
			updater.doUpdate(connection);			
		}
	}

}
