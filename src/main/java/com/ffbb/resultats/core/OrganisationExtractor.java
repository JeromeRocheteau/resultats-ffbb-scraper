package com.ffbb.resultats.core;

import java.net.URI;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.ffbb.resultats.RésultatsFFBB;
import com.ffbb.resultats.api.Organisation;

public class OrganisationExtractor extends AbstractExtractor<Organisation> {
	
	public OrganisationExtractor(RésultatsFFBB résultatsFFBB) {
		super(résultatsFFBB);
	}

	public Organisation doExtract(URI uri) throws Exception {
		Organisation organisation = this.doFind(Organisation.class, uri);
		if (organisation == null) {
			organisation = this.doParse(uri);
			this.doBind(Organisation.class, uri, organisation);
		}
		return organisation;
	}

	private Organisation doParse(URI uri) throws Exception {
		String prefix = "https://resultats.ffbb.com/organisation/";
		String link = uri.toString();
		String code = link.substring(prefix.length(), link.length() - 5);
		Document doc = this.getDocument(uri);
		
		this.doInfo("uri = '" + uri.toString() + "'");
		this.doInfo("data = '" + doc.data() + "'");
		
		Element td = doc.getElementById("idTdOrganisme");
		
		this.doInfo("'" + td.data() + "'");
		
		td = td.selectFirst("table")
				.selectFirst("tbody")
				.selectFirst("tr")
				.selectFirst("td");
		String display = td.text();
		Element input = doc.getElementById("idOrganisme");
		Long id = Long.valueOf(input.attr("value"));
		Organisation organisation = this.doParse(id, code, display);
		return organisation;
	}

	private Organisation doParse(Long id, String code, String display) throws Exception { // 
		Organisation organisation = new Organisation(id, code);
		String[] items = display.split("-");
		String name = items[0].trim();
		String ffbb = items[1].trim();
		String type = items[2].trim(); 
		// FIXME
		if (type.equalsIgnoreCase("Club")) {
			organisation.setType(Organisation.Type.Club);
			this.doParse(organisation, display, true);
		} else if (display.equalsIgnoreCase("Entente")) {
			organisation.setType(Organisation.Type.Entente);
			this.doParse(organisation, display, true);
		} else if (display.equalsIgnoreCase("Association club professionnel")) {
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
