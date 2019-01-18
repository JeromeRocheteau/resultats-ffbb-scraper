package com.ffbb.resultats;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ffbb.resultats.api.Championnat;
import com.ffbb.resultats.api.Equipe;
import com.ffbb.resultats.api.Organisation;
import com.ffbb.resultats.api.Rencontre;
import com.ffbb.resultats.api.Résultat;

public class RencontresExtractor extends AbstractExtractor<List<Rencontre>> {
		
	private Equipe equipe;
	
	private Date début;
	
	private Date fin;
	
	public List<Rencontre> doExtract(Organisation organisation, Championnat championnat) throws Exception {
		this.equipe = new Equipe(organisation, championnat);
		return this.doExtract(equipe.getURI());
	}

	public List<Rencontre> doExtract(Organisation organisation, Championnat championnat, Date début, Date fin) throws Exception {
		this.début = début;
		this.fin = fin;
		return this.doExtract(organisation, championnat);
	}

	public List<Rencontre> doExtract(URI uri) throws Exception {
		Document doc = this.getDocument(uri);
		Element iframe = doc.getElementById("idIframeRencontres");
		String link = iframe.attr("src");
		this.getRencontres(URI.create("http://resultats.ffbb.com/championnat/equipe/" + link));
		return equipe.getRencontres();
	}
	
	private void getRencontres(URI uri) throws Exception {
		Document doc = this.getDocument(uri);
		Element table = doc.select("table.liste").first();
		Elements rows = table.select("tr");
		for (Element row : rows) {
			if (row.attr("class").endsWith("altern-2")) {
				Elements cols = row.select("td");
				if (cols.size() == 7) {
					Integer journée = Integer.valueOf(cols.get(0).text());
					String date = cols.get(1).text() + " " + cols.get(2).text();
					Date horaire = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date);
					Equipe domicile = this.getEquipe(cols.get(3));
					Equipe visiteur = this.getEquipe(cols.get(4));
					Résultat résultat = this.getRésultat(cols.get(5).text());
					Rencontre rencontre = new Rencontre(domicile, visiteur, journée, horaire);
					rencontre.setRésultat(résultat);
				}
			}
		}

	}

	private Equipe getEquipe(Element element) {
		// TODO Auto-generated method stub
		return null;
	}

	private Résultat getRésultat(String text) throws Exception {
		if (text.trim().equals("-")) {
			return null;
		} else {
			String[] items = text.split("-");
			if (items.length == 2) {
				Integer domicile = Integer.valueOf(items[0].trim());
				Integer visiteur = Integer.valueOf(items[1].trim());
				return new Résultat(domicile, visiteur);				
			} else {
				throw new ExtractorException(1);
			}
		}
	}
	
}
