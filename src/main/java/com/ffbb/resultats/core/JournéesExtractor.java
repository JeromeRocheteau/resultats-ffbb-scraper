package com.ffbb.resultats.core;

import java.net.URI;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ffbb.resultats.api.Division;
import com.ffbb.resultats.api.Journée;
import com.ffbb.resultats.api.Journées;

public class JournéesExtractor extends AbstractExtractor<Journées> {
	
	public Journées doExtract(String code) throws Exception {
		String link = "https://resultats.ffbb.com/championnat/journees/" + code + ".html";
		URI uri = URI.create(link);
		if (this.doFind(Journées.class, uri) == null) {
			return this.doExtract(uri);
		} else {
			return this.doFind(Journées.class, uri);
		}
	}

	public Journées doExtract(URI uri) throws Exception {
		String code = uri.toString();
		code = code.substring("https://resultats.ffbb.com/championnat/journees/".length(), code.length() - 5);
		Division division = this.getDivision(code);
		Journées journées = this.getJournées(division, uri);
		return journées;
	}

	private Journées getJournées(Division division, URI uri) throws Exception {
		Journées journées = new Journées(division);
		Document document = this.getDocument(uri);
		Element body = document.getElementById("idIfType");
		Elements tds = body.select("td");
		for (Element td : tds) {
			String id = td.attr("id");
			if (id == null) {
				continue;
			} else if (id.startsWith("p")) {
				Integer numéro = Integer.valueOf(td.text());
				Journée journée = new Journée(numéro, division);
				journées.add(journée);
			}
		}
		return journées;
	}

	private Division getDivision(String code) throws Exception {
		String link = "http://resultats.ffbb.com/championnat/division/" + code + ".html";
		URI uri = URI.create(link);
		if (this.doFind(Division.class, uri) == null) {
			/*
			String subcode = code.substring(0, 12);
			Championnat championnat = extractor.doExtract(subcode);
			for (Division division : championnat.getDivisions()) {
				if (division.getCode().equals(code)) {
					return division;
				}
			}
			*/
			throw new Exception("no division found for code: " + code);
		} else {
			return this.doFind(Division.class, uri);
		}
	}
		
}
