package com.ffbb.resultats.core;

import java.net.URI;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ffbb.resultats.RésultatsFFBB;
import com.ffbb.resultats.api.Catégorie;
import com.ffbb.resultats.api.Championnat;
import com.ffbb.resultats.api.Division;
import com.ffbb.resultats.api.Genre;
import com.ffbb.resultats.api.Niveau;
import com.ffbb.resultats.api.Organisation;

public class ChampionnatExtractor extends AbstractExtractor<Championnat> {
			
	public ChampionnatExtractor(RésultatsFFBB résultatsFFBB) {
		super(résultatsFFBB);
	}

	public Championnat doExtract(URI uri) throws Exception {
		if (this.doFind(Championnat.class, uri) == null) {
			return this.doParse(uri);
		} else {
			return this.doFind(Championnat.class, uri);
		}
	}

	private Championnat doParse(URI uri) throws Exception {
		String code = uri.toString();
		code = code.substring("https://resultats.ffbb.com/championnat/".length(), code.length() - 5);
		Document document = this.getDocument(uri);
		Element cadre = document.getElementById("idTableCoupeChampionnat");		
		Element element = cadre.getElementById("idTdDivision");
		String script = element.selectFirst("script").data();
		String nom = element.text();
		Long id = this.getIdentifier(script);
		String href = cadre.select("tr").get(2).select("td").get(1).selectFirst("a").attr("href");
		String org = href.substring("../organisation/".length(), href.length() - 5);
		Organisation organisateur = this.getOrganisation(org);
		Championnat championnat = new Championnat(id, code, nom, organisateur);
		String[] words = element.text().split("\\s+");
		Niveau niveau = this.getNiveau(words);
		Catégorie catégorie = this.getCatégorie(words);
		Genre genre = this.getGenre(words);
		Integer phase = this.getPhase(words);
		championnat.setNiveau(niveau);
		championnat.setCatégorie(catégorie);
		championnat.setGenre(genre);
		championnat.setPhase(phase);
		this.doBind(Championnat.class, championnat.getURI(), championnat);
		List<Division> divisions = this.getDivisions(document, script, championnat);
		for (Division division : divisions) {
			this.doBind(Division.class, division.getURI(), division);
			this.doBind(Division.class, division.getAlternateURI(), division);
		}
		championnat.getDivisions().addAll(divisions);
		return championnat;
	}
	
	private List<Division> getDivisions(Document document, String script, Championnat championnat) {
		Element td = document.getElementById("idTableCoupeChampionnat");
		Map<String, Long> codes = this.getIdentifiers(script);
		Element element = td.getElementById("idTdPoule");
		List<Division> divisions = new LinkedList<Division>();
		Elements elements = element.select("option");
		if (elements == null || elements.size() == 0) {
			String nom = element.text();
			Long id = codes.get(nom);
			Element elt = element.select("input[type=hidden]").first();
			String code = elt.attr("value");
			int inf = code.indexOf('/');
			int sup = code.lastIndexOf('.');
			code = code.substring(inf + 1, sup);
			Division division = new Division(id, code, nom, championnat);
			divisions.add(division);
		} else {
			for (Element elt : elements) {
				String nom = elt.text();
				Long id = codes.get(nom);
				String code = elt.attr("value");
				int inf = code.indexOf('/');
				int sup = code.lastIndexOf('.');
				code = code.substring(inf + 1, sup);
				Division division = new Division(id, code, nom, championnat);
				divisions.add(division);
			}			
		}
		return divisions;
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
