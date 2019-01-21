package com.ffbb.resultats;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ffbb.resultats.api.Championnat;
import com.ffbb.resultats.api.Équipe;
import com.ffbb.resultats.api.Organisation;
import com.ffbb.resultats.api.Rencontre;
import com.ffbb.resultats.api.Résultat;

public class RencontresExtractor extends AbstractExtractor<List<Rencontre>> {
		
	private Équipe équipe;
	
	private Date début;
	
	private Date fin;
	
	public List<Rencontre> doExtract(Organisation organisation, Championnat championnat) throws Exception {
		this.équipe = new Équipe(organisation, championnat);
		this.doBind(Équipe.class, équipe.getURI(), équipe);
		return this.doExtract(équipe.getURI());
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
		return équipe.getRencontres();
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
					if (début != null && horaire.before(début)) {
						continue;
					}
					if (fin != null && horaire.after(fin)) {
						continue;
					}
					Équipe domicile = this.getEquipe(cols.get(3));
					Équipe visiteur = this.getEquipe(cols.get(4));
					Résultat résultat = this.getRésultat(cols.get(5).text());
					Rencontre rencontre = new Rencontre(domicile, visiteur, journée, horaire);
					rencontre.setRésultat(résultat);
					équipe.getRencontres().add(rencontre);
				}
			}
		}
	}

	private Équipe getEquipe(Element element) throws Exception {
		Element anchor = element.select("a").first();
		String name = anchor.text();
		String link = anchor.attr("href").substring(3);
		URI uri = URI.create("http://resultats.ffbb.com/championnat/equipe/" + link);
		if (this.doFind(Équipe.class, uri) == null) {
			String code = this.getCode(link);
			Organisation organisation = new OrganisationExtractor().doExtract(code);
			Équipe équipe = new Équipe(organisation, this.équipe.getCompétition());
			équipe.setDénomination(name);
			this.doBind(Équipe.class, équipe.getURI(), équipe);
			return équipe;
		} else {
			Équipe équipe = this.doFind(Équipe.class, uri);
			équipe.setDénomination(name);
			return équipe;
		}
	}
	
	private String getCode(String link) throws Exception {
		Pattern pattern = Pattern.compile("(.*)\\.html\\?r=(.*)&p=(.*)&d=(.*)");
		Matcher matcher = pattern.matcher(link);
		if (matcher.matches() && matcher.groupCount() == 4) {
			String code = matcher.group(1);
			return code;
		} else {
			throw new Exception();
		}
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
