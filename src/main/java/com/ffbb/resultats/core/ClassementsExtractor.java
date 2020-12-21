package com.ffbb.resultats.core;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ffbb.resultats.RésultatsFFBB;
import com.ffbb.resultats.api.Classement;
import com.ffbb.resultats.api.Classements;
import com.ffbb.resultats.api.Division;
import com.ffbb.resultats.api.Équipe;
import com.ffbb.resultats.api.Organisation;

public class ClassementsExtractor extends AbstractExtractor<Classements> {

	public ClassementsExtractor(RésultatsFFBB résultatsFFBB) {
		super(résultatsFFBB);
	}

	private Division division;

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public Classements doExtract(URI uri) throws Exception {
		Classements classements = this.doFind(Classements.class, uri);
		if (classements == null) {
			try {
				classements = this.getClassements(uri);
				this.doBind(Classements.class, uri, classements);
			} catch (Exception e) { }
		}
		return classements;
	}
	
	private Classements getClassements(URI uri) throws Exception {
		Classements classements = new Classements(division);
		Document doc = this.getDocument(uri);
		Element table = doc.select("table.liste").first();
		Elements rows = table.select("tr");
		for (Element row : rows) {
			if (row.attr("class").endsWith("altern-2")) {
				Elements cols = row.select("td");
				if (cols.size() == 18) {
					Integer rang = Integer.valueOf(cols.get(0).text());
					Équipe équipe = this.getÉquipe(division, cols.get(1));
					Integer points = Integer.valueOf(cols.get(2).text());
					Integer matchs = Integer.valueOf(cols.get(3).text());
					Integer victoires = Integer.valueOf(cols.get(4).text());
					Integer défaites = Integer.valueOf(cols.get(5).text());
					Integer nuls = Integer.valueOf(cols.get(6).text());
					Integer pour = Integer.valueOf(cols.get(15).text());
					Integer contre = Integer.valueOf(cols.get(16).text());
					Integer diff = Integer.valueOf(cols.get(16).text());
					Classement classement = new Classement(division, équipe, rang, points, matchs, victoires, défaites, nuls, pour, contre, diff);
					classements.add(classement);
				} else if (cols.size() == 17) {
					Integer rang = Integer.valueOf(cols.get(0).text());
					Équipe équipe = this.getÉquipe(division, cols.get(1));
					Integer points = Integer.valueOf(cols.get(2).text());
					Integer matchs = Integer.valueOf(cols.get(3).text());
					Integer victoires = Integer.valueOf(cols.get(4).text());
					Integer défaites = Integer.valueOf(cols.get(5).text());
					Integer nuls = Integer.valueOf(cols.get(6).text());
					Integer pour = Integer.valueOf(cols.get(14).text());
					Integer contre = Integer.valueOf(cols.get(15).text());
					Integer diff = Integer.valueOf(cols.get(16).text());
					Classement classement = new Classement(division, équipe, rang, points, matchs, victoires, défaites, nuls, pour, contre, diff);
					classements.add(classement);
				}
			}
		}
		return classements;
	}
	
	private Équipe getÉquipe(Division division, Element element) throws Exception {
		Element anchor = element.select("a").first();
		String name = anchor.text();
		String link = anchor.attr("href");
		int inf = link.lastIndexOf('/') + 1;
		int sup = link.indexOf('?');
		String code = link.substring(inf, sup - 5);
		Organisation organisation = this.getOrganisation(code);
		URI uri = URI.create("https://resultats.ffbb.com/championnat/equipe/" + link.substring(inf));
		Équipe équipe = this.doFind(Équipe.class, uri);
		if (équipe == null) {
			try {
				Équipe equipe = new Équipe(organisation, division);
				equipe.setNom(name);
				this.doBind(Équipe.class, uri, equipe);
			} catch (Exception e) {
				Logger.getAnonymousLogger().log(Level.WARNING, "Impossible de récupérer l'organisation " + code + " pour l'équipe " + uri);
			}
		}
		return équipe;
	}
	
}
