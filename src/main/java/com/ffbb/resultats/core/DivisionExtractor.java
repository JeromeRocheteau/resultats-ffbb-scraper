package com.ffbb.resultats.core;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.ffbb.resultats.api.Catégorie;
import com.ffbb.resultats.api.Championnat;
import com.ffbb.resultats.api.Division;
import com.ffbb.resultats.api.Genre;
import com.ffbb.resultats.api.Niveau;
import com.ffbb.resultats.api.Organisation;

public class DivisionExtractor extends AbstractExtractor<Division> {
			
	public Division doExtract(Long id, String code, Long division) throws Exception {
		String link = "https://resultats.ffbb.com/championnat/" + code + ".html?r=" + id + "&d=" + division;
		URI uri = URI.create(link);
		if (this.doFind(Division.class, uri) == null) {
			return this.doExtract(uri);
		} else {
			return this.doFind(Division.class, uri);
		}
	}

	public Division doExtract(URI uri) throws Exception {
		Document document = this.getDocument(uri);
		Championnat championnat = this.getChampionnat(document, uri);
		Element cadre = document.getElementById("idTableCoupeChampionnat");		
		Element element = cadre.getElementById("idTdDivision");
		String script = element.selectFirst("script").data();
		Division division = this.getDivision(document, script, championnat);
		this.doBind(Division.class, division.getURI(), division);
		this.doBind(Division.class, division.getAlternateURI(), division);
		if (championnat.getDivisions().contains(division) == false) {
			championnat.getDivisions().add(division);
		}
		return division;
	}
	
	private Championnat getChampionnat(Document document, URI uri) throws Exception {
		String championnatLink = uri.toString();
		championnatLink = championnatLink.substring(0, championnatLink.indexOf('?'));
		URI championnatURI = URI.create(championnatLink);
		if (this.doFind(Championnat.class, championnatURI) == null) {
			String code = championnatLink.substring("https://resultats.ffbb.com/championnat/".length(), championnatLink.length() - 5);
			Element cadre = document.getElementById("idTableCoupeChampionnat");		
			Element element = cadre.getElementById("idTdDivision");
			String script = element.selectFirst("script").data();
			String nom = element.text();
			Long id = this.getIdentifier(script);
			String href = cadre.select("tr").get(2).select("td").get(1).selectFirst("a").attr("href");
			String link = "http://resultats.ffbb.com/" + href.substring(3);
			Organisation organisateur = new OrganisationExtractor().doExtract(URI.create(link));
			Championnat championnat = new Championnat(id, code, nom, organisateur);
			String[] words = element.text().split("\\s+");
			Niveau niveau = this.getNiveau(words);
			Catégorie catégorie = this.getCatégorie(words);
			Genre genre = this.getGenre(words);
			championnat.setNiveau(niveau);
			championnat.setCatégorie(catégorie);
			championnat.setGenre(genre);
			this.doBind(Championnat.class, championnat.getURI(), championnat);
			return championnat;
		} else {
			return this.doFind(Championnat.class, championnatURI);
		}
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
			return division;
			
		} else {
			String nom = elt.text();
			Long id = codes.get(nom);
			String code = elt.attr("value");
			int inf = code.indexOf('/');
			int sup = code.lastIndexOf('.');
			code = code.substring(inf + 1, sup);
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

	private Niveau getNiveau(String[] words) throws Exception {
		boolean pré = false;
		for (String word : words) {
			pré = pré || this.getPré(word);
		}
		for (String word : words) {
			Niveau niveau = this.getNiveau(word, pré);
			if ((niveau == null) == false) {
				return niveau;
			}
		}
		return null;
	}
	
	private Niveau getNiveau(String word, boolean pré) throws Exception {
		String text = word.toLowerCase();
		if (text.startsWith(Niveau.Départemental.name().toLowerCase())) {
			return Niveau.Départemental;
		} else if (text.startsWith(Niveau.Régional.name().toLowerCase())) {
			return pré ? Niveau.PréRégional : Niveau.Régional;
		} else if (text.startsWith(Niveau.National.name().toLowerCase())) {
			return pré ? Niveau.PréNational : Niveau.National;
		} else {
			return null;
		}
	}
	
	private boolean getPré(String word) throws Exception {
		if (word.equals("Pré")) {
			return true;
		} else {
			return false;
		}
	}

	private Genre getGenre(String[] words) throws Exception {
		for (String word : words) {
			Genre genre = this.getGenre(word);
			if ((genre == null) == false) {
				return genre;
			}
		}
		return null;
	}

	private Genre getGenre(String word) throws Exception {
		if (word.toLowerCase().startsWith(Genre.Féminin.name().toLowerCase())) {
			return Genre.Féminin;
		} else if (word.toLowerCase().startsWith(Genre.Masculin.name().toLowerCase())) {
			return Genre.Masculin;
		} else {
			return null;
		}
	}

	private Catégorie getCatégorie(String[] words) throws Exception {
		for (String word : words) {
			Catégorie catégorie = this.getCatégorie(word);
			if ((catégorie == null) == false) {
				return catégorie;
			}
		}
		return Catégorie.Senior;
	}
	
	private Catégorie getCatégorie(String word) throws Exception {
		if (word.equalsIgnoreCase(Catégorie.Senior.name())) {
			return Catégorie.Senior;
		} else if (word.equalsIgnoreCase(Catégorie.U20.name())) {
			return Catégorie.U20;
		} else if (word.equalsIgnoreCase(Catégorie.U18.name())) {
			return Catégorie.U18;
		} else if (word.equalsIgnoreCase(Catégorie.U17.name())) {
			return Catégorie.U17;
		} else if (word.equalsIgnoreCase(Catégorie.U15.name())) {
			return Catégorie.U15;
		} else if (word.equalsIgnoreCase(Catégorie.U13.name())) {
			return Catégorie.U13;
		} else if (word.equalsIgnoreCase(Catégorie.U11.name())) {
			return Catégorie.U11;
		} else if (word.equalsIgnoreCase(Catégorie.U9.name())) {
			return Catégorie.U9;
		} else {
			return null;
		}
	}
	
}
