package com.ffbb.resultats.core;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.ffbb.resultats.RésultatsFFBB;
import com.ffbb.resultats.api.Catégorie;
import com.ffbb.resultats.api.Championnat;
import com.ffbb.resultats.api.Division;
import com.ffbb.resultats.api.Genre;
import com.ffbb.resultats.api.Niveau;
import com.ffbb.resultats.api.Organisation;

public class DivisionExtractor extends AbstractExtractor<Division> {
			
	public DivisionExtractor(RésultatsFFBB résultatsFFBB) {
		super(résultatsFFBB);
	}

	public Division doExtract(URI uri) throws Exception {
		Division division = this.doFind(Division.class, uri);
		if (division == null) {
			division = this.doParse(uri);
			this.doBind(Division.class, division.getURI(), division);
			this.doBind(Division.class, division.getAlternateURI(), division);
		}
		return division;
	}

	private Division doParse(URI uri) throws Exception {
		Document document = this.getDocument(uri);
		Championnat championnat = this.getChampionnat(document, uri);
		Element cadre = document.getElementById("idTableCoupeChampionnat");		
		Element element = cadre.getElementById("idTdDivision");
		String script = element.selectFirst("script").data();
		Division division = this.getDivision(document, script, championnat);
		if (championnat.getDivisions().contains(division) == false) {
			championnat.getDivisions().add(division);
		}
		return division;
	}
	
	private Championnat getChampionnat(Document document, URI uri) throws Exception {
		String championnatLink = uri.toString();
		championnatLink = championnatLink.substring(0, championnatLink.indexOf('?'));
		URI championnatURI = URI.create(championnatLink);
		Championnat championnat = this.doFind(Championnat.class, championnatURI); 
		if (championnat == null) {
			String code = championnatLink.substring("https://resultats.ffbb.com/championnat/".length(), championnatLink.length() - 5);
			Element cadre = document.getElementById("idTableCoupeChampionnat");		
			Element element = cadre.getElementById("idTdDivision");
			String script = element.selectFirst("script").data();
			String nom = element.text();
			Long id = this.getIdentifier(script);
			String href = cadre.select("tr").get(2).select("td").get(1).selectFirst("a").attr("href");
			String org = href.substring("../organisation/".length(), href.length() - 5);
			Organisation organisateur = this.getOrganisation(org);
			championnat = new Championnat(id, code, nom, organisateur);
			String[] words = element.text().split("\\s+");
			Niveau niveau = this.getNiveau(words);
			Catégorie catégorie = this.getCatégorie(words);
			Genre genre = this.getGenre(words);
			championnat.setNiveau(niveau);
			championnat.setCatégorie(catégorie);
			championnat.setGenre(genre);
			this.doBind(Championnat.class, championnat.getURI(), championnat);
		}
		return championnat;
	}

	private Division getDivision(Document document, String script, Championnat championnat) {
		Element td = document.getElementById("idTableCoupeChampionnat");
		Map<String, Long> codes = this.getIdentifiers(script);
		Element element = td.getElementById("idTdPoule");
		Element elt = element.select("option[selected]").first();
		if (elt == null) {
			String nom = element.text();
			Long id = codes.get(nom);
			elt = element.select("input[type=hidden]").first();
			String code = elt.attr("value");
			int inf = code.indexOf('/');
			int sup = code.lastIndexOf('.');
			code = code.substring(inf + 1, sup);
			Division division = new Division(id, code, nom, championnat);
			// division.setNum(num);
			return division;
		} else {
			String nom = elt.text();
			Long id = codes.get(nom);
			String code = elt.attr("value");
			int inf = code.indexOf('/');
			int sup = code.lastIndexOf('.');
			code = code.substring(inf + 1, sup);
			assert(id != null);
			Division division = new Division(id, code, nom, championnat);
			return division;
		}
	}

	private Long getIdentifier(String script) {
		String data = script.substring(script.indexOf("poules[") + 7, script.indexOf("]"));
		return Long.valueOf(data);
	}

	private Map<String, Long> getIdentifiers(String script) {
		String data = script.substring(script.indexOf("[[") + 2, script.lastIndexOf("]]"))
				.replaceAll("\\],\\[", "\n")
				.replaceAll("'", "")
				.replaceAll(",", "\t");
		String[] lines = data.split("\n");
		Map<String, Long> codes = new HashMap<String, Long>(lines.length);
		for (String line : lines) {
			String[] rows = line.split("\t");
			String nom = rows[1];
			Long id = Long.valueOf(rows[0]);
			codes.put(nom, id);
		}
		return codes;
	}
	
}
