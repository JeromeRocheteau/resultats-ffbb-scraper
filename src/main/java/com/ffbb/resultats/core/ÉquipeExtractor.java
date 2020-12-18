package com.ffbb.resultats.core;

import java.net.URI;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ffbb.resultats.RésultatsFFBB;
import com.ffbb.resultats.api.Division;
import com.ffbb.resultats.api.Organisation;
import com.ffbb.resultats.api.Équipe;

public class ÉquipeExtractor extends AbstractExtractor<Équipe> {

	public ÉquipeExtractor(RésultatsFFBB résultatsFFBB) {
		super(résultatsFFBB);
	}

	private Division division;
	
	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public Équipe doExtract(URI uri) throws Exception {
		if (this.doFind(Équipe.class, uri) == null) {
			Équipe équipe = this.doParse(uri);
			this.doBind(Équipe.class, uri, équipe);
			return équipe;
		} else {
			return this.doFind(Équipe.class, uri);
		}
	}

	public Équipe doParse(URI uri) throws Exception {
		/*
		String link = uri.toString();
		link = link.substring("https://resultats.ffbb.com/championnat/equipe/".length());
		String code = link.substring(0, link.indexOf('?'));
		code = code.substring(0, code.length() - 5);
		Long chp= Long.valueOf(parameters.substring(parameters.indexOf("r=") + 2, parameters.indexOf("&p=")));
		Long div = Long.valueOf(parameters.substring(parameters.indexOf("&p=") + 3, parameters.indexOf("&d=")));
		*/
		String link = uri.toString();
		String parameters = link.substring(link.indexOf('?') + 1);
		Long org = Long.valueOf(parameters.substring(parameters.indexOf("&d=") + 3));
		
		Document document = this.getDocument(uri);
		Element td = document.getElementById("idTableClub");
		Elements trs = td.select("tr");
		Element a = trs.get(0).select("a").first();
		String code = a.attr("href");
		code = code.substring("../../organisation/".length(), code.length() - 5);
		Organisation organisation = this.getOrganisation(code);
		organisation.setId(org);
		/*
		Elements elts = trs.get(3).select("option");
		for (Element elt : elts) {
			String value = elt.attr("value");
			String cd = "";
			if (value.length() > 24) {
				cd = value.substring(0, 24);
			} else if (value.length() > 12) {
				cd = value.substring(0, 12);
			}
			String nom = elt.text();
			System.out.println(nom + " --> " + cd);
		}
		*/
		Équipe équipe = new Équipe(organisation, division);
		return équipe;
	}
	
}
