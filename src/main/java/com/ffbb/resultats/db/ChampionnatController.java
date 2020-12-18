package com.ffbb.resultats.db;

import java.net.URI;
import java.sql.Connection;

import com.ffbb.resultats.api.Championnat;

public class ChampionnatController implements Controller<Championnat> {

	private ChampionnatReader reader;
	
	private ChampionnatUpdater championnatUpdater;
	
	private CompétitionUpdater compétitionUpdater;
	
	public ChampionnatController() {
		reader = new ChampionnatReader();
		championnatUpdater = new ChampionnatUpdater();
		compétitionUpdater = new CompétitionUpdater();
	}
	
	@Override
	public Championnat doFind(Connection connection, URI uri) throws Exception {
		String link = uri.toString();
		int prefix = "https://resultats.ffbb.com/championnat/".length();
		int suffix = ".html".length();
		String code = link.substring(prefix, link.length() - suffix);
		reader.setObject(code);
		return reader.doRead(connection);
	}

	@Override
	public void doSave(Connection connection, Championnat championnat) throws Exception {
		compétitionUpdater.setObject(championnat);
		compétitionUpdater.doUpdate(connection);
		championnatUpdater.setObject(championnat);
		championnatUpdater.doUpdate(connection);
	}

}
