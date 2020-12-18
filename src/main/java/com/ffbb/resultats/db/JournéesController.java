package com.ffbb.resultats.db;

import java.net.URI;
import java.sql.Connection;

import com.ffbb.resultats.api.Journée;
import com.ffbb.resultats.api.Journées;

public class JournéesController implements Controller<Journées> {

	private JournéesReader reader;
	
	private JournéeUpdater updater;
	
	public JournéesController() {
		reader = new JournéesReader();
		updater = new JournéeUpdater();
	}
	
	@Override
	public Journées doFind(Connection connection, URI uri) throws Exception {
		String link = uri.toString();
		int prefix = "https://resultats.ffbb.com/championnat/journees/".length();
		int suffix = ".html".length();
		String code = link.substring(prefix, link.length() - suffix);
		reader.setObject(code);
		return reader.doRead(connection);
	}

	@Override
	public void doSave(Connection connection, Journées journées) throws Exception {
		for (Journée journée : journées) {
			updater.setObject(journée);
			updater.doUpdate(connection);			
		}
	}

}
