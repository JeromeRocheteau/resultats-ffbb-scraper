package com.ffbb.resultats.core;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.ffbb.resultats.api.Catégorie;
import com.ffbb.resultats.api.Championnat;
import com.ffbb.resultats.api.Compétition;
import com.ffbb.resultats.api.Division;
import com.ffbb.resultats.api.Genre;
import com.ffbb.resultats.api.Niveau;
import com.ffbb.resultats.api.Organisation;

public class ChampionnatExtractor extends AbstractExtractor<Championnat> {
	
	private Long id;
	
	private String code;
	
	private Long idDivision;
		
	public Championnat doExtract(Long id, String code, Long division) throws Exception {
		this.id = id;
		this.code = code;
		this.idDivision = division;
		String link = "https://resultats.ffbb.com/championnat/" + code + ".html?r=" + id + "&d=" + division;
		URI uri = URI.create(link);
		if (this.doFind(Championnat.class, uri) == null) {
			return this.doExtract(uri);
		} else {
			return this.doFind(Championnat.class, uri);
		}
	}

	public Championnat doExtract(URI uri) throws Exception {
		Document doc = this.getDocument(uri);
		
		Element td = doc.getElementById("idTableCoupeChampionnat");
		
		Element element = td.getElementById("idTdDivision");
		String nom = element.text();
		String script = element.selectFirst("script").data();

		// String[] words = element.text().split("\\s+");
		// Niveau niveau = this.getNiveau(words);
		// Genre genre = this.getGenre(words);
		// Catégorie catégorie = this.getCatégorie(words);
		
		Element pouleElement = td.getElementById("idTdPoule");
		String nomDivision = this.getPoule(pouleElement);
		String codeDivision = this.getCode(pouleElement);
		System.out.println(nomDivision + " -> " + codeDivision);
				
		String href = td.select("tr").get(2).select("td").get(1).selectFirst("a").attr("href");
		String link = "http://resultats.ffbb.com/" + href.substring(3);

		Organisation organisateur = new OrganisationExtractor().doExtract(URI.create(link));
		System.out.println(organisateur.toString());
		
		Championnat championnat = new Championnat(id, code, nom, organisateur);
		this.doBind(Championnat.class, uri, championnat);
		System.out.println(championnat.toString());

		Division division = new Division(this.idDivision, codeDivision, nomDivision, championnat);
		this.doBind(Division.class, uri, division);
		System.out.println(championnat.toString());
		
		return championnat;
	}
	
	private Map<String, Long> getDivisions(String script) {
		String data = script.substring(script.indexOf("[[") + 2, script.lastIndexOf("]]"))
				.replaceAll("\\],\\[", "\n")
				.replaceAll("'", "")
				.replaceAll(",", "\t");
		String[] items = data.split("\n");
		Map<String, Long> codes = new HashMap<String, Long>(items.length);
		for (String item : items) {
			String[] elements = item.split("\t");
			String name = elements[1];
			Long numD = Long.valueOf(elements[0]);
			codes.put(name, numD);
		}
		return codes;
	}

	private String getPoule(Element pouleElement) {
		String poule = pouleElement.text();
		try { poule = pouleElement.select("option[selected]").first().text(); } catch (Exception e) { }
		return poule;
	}

	private String getCode(Element pouleElement) {
		Element defaultPoule = null;
		String journée = "";
		try { 
			defaultPoule = pouleElement.select("option[selected]").first();
			journée = defaultPoule.attr("value");
			int inf = journée.indexOf('/');
			int sup = journée.lastIndexOf('.');
			journée = journée.substring(inf + 1, sup);
		} catch (Exception e) {
			defaultPoule = pouleElement.select("input[type=hidden]").first();
			journée = defaultPoule.attr("value");
			int inf = journée.indexOf('/');
			int sup = journée.lastIndexOf('.');
			journée = journée.substring(inf + 1, sup);
		}
		return journée;
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
		if (text.equalsIgnoreCase(Niveau.Départemental.name().toLowerCase())) {
			return Niveau.Départemental;
		} else if (text.equalsIgnoreCase(Niveau.Régional.name().toLowerCase())) {
			return pré ? Niveau.PréRégional : Niveau.Régional;
		} else if (text.equalsIgnoreCase(Niveau.National.name().toLowerCase())) {
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
