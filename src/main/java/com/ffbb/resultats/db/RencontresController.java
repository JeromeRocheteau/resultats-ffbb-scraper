package com.ffbb.resultats.db;

import java.net.URI;
import java.sql.Connection;

import com.ffbb.resultats.api.Rencontre;
import com.ffbb.resultats.api.Rencontres;
import com.ffbb.resultats.api.Résultat;

public class RencontresController implements Controller<Rencontres> {

	private RencontresReader reader;
	
	private RencontreUpdater rencontreUpdater;
	
	private RésultatUpdater résultatUpdater;
	
	public RencontresController() {
		reader = new RencontresReader();
		rencontreUpdater = new RencontreUpdater();
		résultatUpdater = new RésultatUpdater();
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
		for (Rencontre rencontre : rencontres) {
			rencontreUpdater.setObject(rencontre);
			rencontreUpdater.doUpdate(connection);
			Résultat résultat = rencontre.getRésultat();
			if (résultat != null) {
				résultatUpdater.setObject(résultat);
				résultatUpdater.doUpdate(connection);
			}
		}
	}

}
