package com.ffbb.resultats;

import java.net.URI;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.ffbb.resultats.api.Catégorie;
import com.ffbb.resultats.api.Championnat;
import com.ffbb.resultats.api.Compétition;
import com.ffbb.resultats.api.Genre;
import com.ffbb.resultats.api.Niveau;
import com.ffbb.resultats.api.Organisation;

public class ChampionnatExtractor extends AbstractExtractor<Championnat> {
	
	private Long id;
	
	private String code;
	
	private Long index;
		
	public Championnat doExtract(Long id, String code, Long index) throws Exception {
		this.id = id;
		this.code = code;
		this.index = index;
		String link = "https://resultats.ffbb.com/championnat/" + code + ".html?r=" + id + "&d=" + index;
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
		// String script = element.selectFirst("script").data();
		// Map<String, Long> codes = this.getDivisions(script);

		String[] words = element.text().split("\\s+");
		Niveau niveau = this.getNiveau(words);
		Genre genre = this.getGenre(words);
		Catégorie catégorie = this.getCatégorie(words);
		
		Element tag = td.getElementById("idTdPoule").select("option[selected]").first();
		String poule = tag.text();
		
		Integer phase = 1; // FIXME
		
		String href = td.select("tr").get(2).select("td").get(1).selectFirst("a").attr("href");
		String link = "http://resultats.ffbb.com/" + href.substring(3);

		Organisation organisateur = new OrganisationExtractor().doExtract(URI.create(link));
		System.out.println(organisateur.toString());
		
		Championnat championnat = new Championnat(this.id, this.code, nom, organisateur, Compétition.Type.Championnat, genre, catégorie, index, niveau, phase, poule);
		System.out.println(championnat.toString());
		
		this.doBind(Championnat.class, uri, championnat);
		return championnat;
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
