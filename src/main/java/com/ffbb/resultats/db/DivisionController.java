package com.ffbb.resultats.db;

import java.net.URI;
import java.sql.Connection;

import com.ffbb.resultats.api.Division;
import com.ffbb.resultats.api.Paramètres;

public class DivisionController implements Controller<Division> {

	private DivisionReader reader;

	private DivisionAlternativeReader alternativeReader;
	
	private DivisionUpdater updater;
	
	public DivisionController() {
		reader = new DivisionReader();
		alternativeReader = new DivisionAlternativeReader();
		updater = new DivisionUpdater();
	}
	
	@Override
	public Division doFind(Connection connection, URI uri) throws Exception {
		String link = uri.toString();
		String prefix = "https://resultats.ffbb.com/championnat/division/";
		if (link.startsWith(prefix)) {
			int offset = prefix.length();
			int suffix = ".html".length();
			String code = link.substring(offset, link.length() - suffix);
			alternativeReader.setObject(code);
			return alternativeReader.doRead(connection);			
		} else {
			Paramètres paramètres = this.getParamètres(link);
			reader.setObject(paramètres);
			return reader.doRead(connection);			
		}
	}

	private Paramètres getParamètres(String link) {
		int prefix = "https://resultats.ffbb.com/championnat/".length();
		int suffix = ".html".length();
		int cut = link.indexOf('?');
		String code = link.substring(prefix, cut - suffix);
		String query = link.substring(cut + 1);
		Long id = null;
		Long division = null;
		String[] parameters = query.split("&");
		for (String parameter : parameters) {
			if (parameter.startsWith("r=")) {
				id = Long.valueOf(parameter.substring(2));
			} else if (parameter.startsWith("d=")) {
				division = Long.valueOf(parameter.substring(2));
			}
		}
		return new Paramètres(code, id, division);
	}

	@Override
	public void doSave(Connection connection, Division division) throws Exception {
		if (this.doFind(connection, division.getAlternateURI()) == null) {
			updater.setObject(division);
			updater.doUpdate(connection);			
		}
	}

}
