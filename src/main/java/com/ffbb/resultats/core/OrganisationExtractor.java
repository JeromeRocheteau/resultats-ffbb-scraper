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
		int length = items.length;
		String name = null;
		String ffbb = null;
		String type = null; 
		if (length < 3) {
			throw new Exception(display);
		} else if (length == 3 || length == 4) {
			name = items[0].trim();
			ffbb = items[1].trim();
			type = items[2].trim();
		} else if (length > 4) {
			for (int i = 0; i < length - 3; i++) {
				if (i > 0) {
					name +=  " - ";
				}
				name += items[i];
			}
			ffbb = items[length - 3].trim();
			type = items[length - 2].trim();
		}
		if (type.equalsIgnoreCase("Club")) {
			organisation.setType(Organisation.Type.Club);
		} else if (type.equalsIgnoreCase("Entente")) {
			organisation.setType(Organisation.Type.Entente);
		} else if (type.equalsIgnoreCase("Association club professionnel")) {
			organisation.setType(Organisation.Type.ClubPro);
		} else if (type.equals("FÉDÉRATION FRANCAISE BASKET-BALL - FEDE")) {
			organisation.setType(Organisation.Type.Fédération);
		} else if (type.startsWith("COMITE")) {
			organisation.setType(Organisation.Type.Comité);
		} else if (type.startsWith("LIGUE")) {
			organisation.setType(Organisation.Type.Ligue);
		} else {
			throw new Exception(display + " " + type);
		}
		organisation.setFfbb(ffbb);
		organisation.setNom(name.toString());
		return organisation;
	}
	
}
