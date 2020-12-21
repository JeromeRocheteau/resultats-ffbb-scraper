package com.ffbb.resultats.db;

import java.net.URI;
import java.sql.Connection;

import com.ffbb.resultats.api.Appartenance;
import com.ffbb.resultats.api.Appartenances;

public class AppartenancesController implements Controller<Appartenances> {

	private AppartenancesReader reader;
	
	private AppartenanceUpdater updater;
	
	public AppartenancesController() {
		reader = new AppartenancesReader();
		updater = new AppartenanceUpdater();
	}
	
	@Override
	public Appartenances doFind(Connection connection, URI uri) throws Exception {
		String link = uri.toString();
		int prefix = "https://resultats.ffbb.com/organisation/appartenance/".length();
		int suffix = ".html".length();
		String code = link.substring(prefix, link.length() - suffix);
		reader.setObject(code);
		return reader.doRead(connection);
	}

	@Override
	public void doSave(Connection connection, Appartenances appartenances) throws Exception {
		for (Appartenance appartenance : appartenances) {
			updater.setObject(appartenance);
			updater.doUpdate(connection);			
		}
	}

}
