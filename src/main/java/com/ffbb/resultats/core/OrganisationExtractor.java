package com.ffbb.resultats.core;

import java.net.URI;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.ffbb.resultats.api.Organisation;

public class OrganisationExtractor extends AbstractExtractor<Organisation> {
	
	private String code;
	
	public Organisation doExtract(String code) throws Exception {
		this.code = code;
		String link = "http://resultats.ffbb.com/organisation/" + code + ".html";
		URI uri = URI.create(link);
		if (this.doFind(Organisation.class, uri) == null) {
			return this.doExtract(uri);
		} else {
			return this.doFind(Organisation.class, uri);
		}
	}

	public Organisation doExtract(URI uri) throws Exception {
		Document doc = this.getDocument(uri);
		Element td = doc.getElementById("idTdOrganisme");
		td = td.selectFirst("table")
				.selectFirst("tbody")
				.selectFirst("tr")
				.selectFirst("td");
		String display = td.text();
		Element input = doc.getElementById("idOrganisme");
		Long id = Long.valueOf(input.attr("value"));
		Organisation organisation = this.doParse(id, display);
		this.doBind(Organisation.class, uri, organisation);
		return organisation;
	}

	private Organisation doParse(Long id, String display) throws Exception { // 
		Organisation organisation = new Organisation(id, code);
		if (display.endsWith(" - Club")) {
			organisation.setType(Organisation.Type.Club);
			this.doParse(organisation, display, true);
		} else if (display.endsWith(" - Entente")) {
			organisation.setType(Organisation.Type.Entente);
			this.doParse(organisation, display, true);
		} else if (display.endsWith(" - Association club professionnel")) {
			organisation.setType(Organisation.Type.ClubPro);
			this.doParse(organisation, display, true);
		} else if (display.equals("FÉDÉRATION FRANCAISE BASKET-BALL - FEDE")) {
			organisation.setType(Organisation.Type.Fédération);
			this.doParse(organisation, display, false);
		} else if (display.startsWith("COMITE")) {
			organisation.setType(Organisation.Type.Comité);
			this.doParse(organisation, display, false);
		} else if (display.startsWith("LIGUE")) {
			organisation.setType(Organisation.Type.Ligue);
			this.doParse(organisation, display, false);
		} else {
			throw new Exception(display);
		}
		return organisation;
	}

	private void doParse(Organisation organisation, String display, boolean skip) throws Exception {
		String[] items = display.split(" - ");
		StringBuilder name = new StringBuilder();
		int last = items.length - (skip ? 2 : 1);
		for (int i = 0; i < last; i++) {
			if (i > 0) {
				name.append(" - ");	
			}
			name.append(items[i]);
		}
		String ffbb = items[last].trim();
		organisation.setFfbb(ffbb);
		organisation.setNom(name.toString());
	}
	
}
