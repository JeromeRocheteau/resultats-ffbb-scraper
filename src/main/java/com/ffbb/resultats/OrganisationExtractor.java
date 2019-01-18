package com.ffbb.resultats;

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
		return this.doExtract(uri);
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
		return this.doParse(id, display);
	}

	private Organisation doParse(Long id, String display) throws Exception { // 
		Organisation organisation = new Organisation(code);
		organisation.setId(id);
		if (display.endsWith(" - Club")) {
			organisation.setType(Organisation.Type.Club);
			this.doParse(organisation, display, true);
		} else if (display.endsWith(" - Entente")) {
			organisation.setType(Organisation.Type.Entente);
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
			organisation.setType(Organisation.Type.ClubPro);
			this.doParse(organisation, display, false);
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
		organisation.setName(name.toString());
	}
	
}
