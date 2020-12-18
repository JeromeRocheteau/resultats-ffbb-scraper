package com.ffbb.resultats.db;

import java.net.URI;
import java.sql.Connection;

import com.ffbb.resultats.api.Engagement;
import com.ffbb.resultats.api.Engagements;

public class EngagementsController implements Controller<Engagements> {

	private EngagementsReader reader;
	
	private EngagementUpdater updater;
	
	public EngagementsController() {
		reader = new EngagementsReader();
		updater = new EngagementUpdater();
	}
	
	@Override
	public Engagements doFind(Connection connection, URI uri) throws Exception {
		String link = uri.toString();
		int prefix = "http://resultats.ffbb.com/organisation/engagements/".length();
		int suffix = ".html".length();
		String code = link.substring(prefix, link.length() - suffix);
		reader.setObject(code);
		return reader.doRead(connection);
	}

	@Override
	public void doSave(Connection connection, Engagements engagements) throws Exception {
		for (Engagement engagement : engagements) {
			System.out.println("SAVE " + engagement.getCode());
			updater.setObject(engagement);
			updater.doUpdate(connection);			
		}
	}

}
