package com.ffbb.resultats.core;

import java.net.URI;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ffbb.resultats.api.Division;
import com.ffbb.resultats.api.Organisation;
import com.ffbb.resultats.api.Équipe;

public class ÉquipeExtractor extends AbstractExtractor<Équipe> {

	private Division division;
	
	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	private OrganisationExtractor organisationExtractor;
	
	public ÉquipeExtractor() {
		organisationExtractor = new OrganisationExtractor();
	}
			
	public Équipe doExtract(URI uri) throws Exception {
		// String link = "https://resultats.ffbb.com/championnat/equipe/" + code + ".html?r=" + championnat + "&p=" + division + "&d=" + organisation;
		// URI uri = URI.create(link);
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
		String parameters = link.substring(link.indexOf('?') + 1);
		Long chp= Long.valueOf(parameters.substring(parameters.indexOf("r=") + 2, parameters.indexOf("&p=")));
		Long div = Long.valueOf(parameters.substring(parameters.indexOf("&p=") + 3, parameters.indexOf("&d=")));
		Long org = Long.valueOf(parameters.substring(parameters.indexOf("&d=") + 3));
		*/
		
		Document document = this.getDocument(uri);
		Element td = document.getElementById("idTableClub");
		Elements trs = td.select("tr");
		Element a = trs.get(0).select("a").first();
		Organisation organisation = this.getOrganisation(a);
		
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

	private Organisation getOrganisation(Element a) throws Exception {
		String prefix = "http://resultats.ffbb.com/";
		String link = prefix + a.attr("href").substring("../../".length());
		URI uri = URI.create(link);
		return organisationExtractor.doExtract(uri);
	}	
	
}
