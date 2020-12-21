package com.ffbb.resultats.db;

import java.net.URI;
import java.sql.Connection;

import com.ffbb.resultats.api.Classement;
import com.ffbb.resultats.api.Classements;

public class ClassementsController implements Controller<Classements> {

	private ClassementsReader reader;
	
	private ClassementUpdater updater;
	
	public ClassementsController() {
		reader = new ClassementsReader();
		updater = new ClassementUpdater();
	}
	
	@Override
	public Classements doFind(Connection connection, URI uri) throws Exception {
		String link = uri.toString();
		int prefix = "https://resultats.ffbb.com/championnat/classements/".length();
		int suffix = ".html".length();
		String code = link.substring(prefix, link.length() - suffix);
		reader.setObject(code);
		return reader.doRead(connection);
	}

	@Override
	public void doSave(Connection connection, Classements classements) throws Exception {
		for (Classement classement : classements) {
			updater.setObject(classement);
			updater.doUpdate(connection);			
		}
	}

}
