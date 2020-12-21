package com.ffbb.resultats.db;

import java.net.URI;
import java.sql.Connection;

import com.ffbb.resultats.api.Paramètres;
import com.ffbb.resultats.api.Équipe;

public class ÉquipeController implements Controller<Équipe> {

	private ÉquipeReader reader;
	
	private ÉquipeUpdater updater;
	
	public ÉquipeController() {
		reader = new ÉquipeReader();
		updater = new ÉquipeUpdater();
	}
	
	@Override
	public Équipe doFind(Connection connection, URI uri) throws Exception {
		String link = uri.toString();
		Paramètres paramètres = this.getParamètres(link);
		reader.setObject(paramètres);
		return reader.doRead(connection);
	}

	@Override
	public void doSave(Connection connection, Équipe équipe) throws Exception {
		updater.setObject(équipe);
		updater.doUpdate(connection);
	}
	
	private Paramètres getParamètres(String link) {
		int prefix = "https://resultats.ffbb.com/championnat/equipe/".length();
		int suffix = ".html".length();
		int cut = link.indexOf('?');
		String code = link.substring(prefix, cut - suffix);
		String query = link.substring(cut + 1);
		Long r = null;
		// Long p = null;
		Long d = null;
		String[] parameters = query.split("&");
		for (String parameter : parameters) {
			if (parameter.startsWith("r=")) {
				r = Long.valueOf(parameter.substring(2));
			} else if (parameter.startsWith("p=")) {
				d = Long.valueOf(parameter.substring(2));
			} /* else if (parameter.startsWith("d=")) {
				d = Long.valueOf(parameter.substring(2));
			} */
		}
		return new Paramètres(code, r, d);
	}
	
}
