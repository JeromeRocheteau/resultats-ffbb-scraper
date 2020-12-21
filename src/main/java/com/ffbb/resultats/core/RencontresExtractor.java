package com.ffbb.resultats.core;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ffbb.resultats.RésultatsFFBB;
import com.ffbb.resultats.api.Journée;
import com.ffbb.resultats.api.Rencontre;
import com.ffbb.resultats.api.Rencontres;
import com.ffbb.resultats.api.Résultat;
import com.ffbb.resultats.api.Salle;
import com.ffbb.resultats.api.Équipe;

public class RencontresExtractor extends AbstractExtractor<Rencontres> {

	public RencontresExtractor(RésultatsFFBB résultatsFFBB) {
		super(résultatsFFBB);
	}

	private Journée journée;
	
	public Journée getJournée() {
		return journée;
	}

	public void setJournée(Journée journée) {
		this.journée = journée;
	}

	public Rencontres doExtract(URI uri) throws Exception {
		Rencontres rencontres = this.doFind(Rencontres.class, uri);
		if (rencontres == null) {
			rencontres = this.doParse(uri);
			this.doBind(Rencontres.class, uri, rencontres);
			journée.getRencontres().addAll(rencontres);
		}
		return rencontres;
	}

	public Rencontres doParse(URI uri) throws Exception {
		Rencontres rencontres = new Rencontres(journée);
		Document doc = this.getDocument(uri);
		Element table = doc.select("table.liste").first();
		Elements rows = table.select("tr");
		for (Element row : rows) {
			if (row.attr("class").endsWith("altern-2")) {
				Elements cols = row.select("td");
				if (cols.size() == 7) {
					Integer numéro = Integer.valueOf(cols.get(0).text());
					String date = cols.get(1).text() + " " + cols.get(2).text();
					Date horaire = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date);
					Équipe domicile = this.getÉquipe(cols.get(3));
					Équipe visiteur = this.getÉquipe(cols.get(4));
					Salle salle = this.getSalle(cols.get(6));
					Rencontre rencontre = new Rencontre(journée, numéro, domicile, visiteur, horaire, salle);
					Résultat résultat = this.getRésultat(rencontre, cols.get(5).text());
					rencontre.setRésultat(résultat);
					rencontres.add(rencontre);
					domicile.getRencontres().add(rencontre);
					visiteur.getRencontres().add(rencontre);
				}
			}
		}
		return rencontres;
	}

	private Résultat getRésultat(Rencontre rencontre, String text) throws Exception {
		String[] items = text.split("-");
		if (items.length == 2) {
			Integer domicile = Integer.valueOf(items[0].trim());
			Integer visiteur = Integer.valueOf(items[1].trim());
			return new Résultat(rencontre, domicile, visiteur);
		} else {
			return null;
		}
	}

	private Équipe getÉquipe(Element element) throws Exception {
		Element anchor = element.select("a").first();
		String nom = anchor.text();
		String link = anchor.attr("href").substring("../equipe/".length());
		Long id = Long.valueOf(link.substring(link.indexOf("&d=") + 3));
		String code = link.substring(0, link.indexOf(".html"));
		Équipe équipe = this.getÉquipe(journée.getDivision(), id, code);
		équipe.setNom(nom);
		return équipe;	
	}
	
	private Salle getSalle(Element element) throws Exception {
		Element a = element.select("a").first();
		String href = a.attr("href");
		int inf = "javascript:openHere('".length();
		int sup = href.length() - "')".length();
		Long id = Long.valueOf(href.substring(inf, sup));
		return this.getSalle(id);
	}
	
	/*
	
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
		// this.doInfo(uri.toString() + " -> " + link);
		this.getRencontres(URI.create("http://resultats.ffbb.com/championnat/equipe/" + link));
		return équipe.getRencontres();
	}
	
	private void getRencontres(URI uri) throws Exception {
		try {
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
					Salle salle = this.getSalle(cols.get(6));
					Rencontre rencontre = new Rencontre(équipe.getCompétition(), domicile, visiteur, journée, horaire, salle);
					rencontre.setRésultat(résultat);
					équipe.getRencontres().add(rencontre);
				}
			}
		}
		} catch (Exception e) {
			this.doWarn(e.getMessage() + " : " + uri.toString());
		}
	}

	
	private static final String PREFIX = "javascript:openHere('";
	private static final String SUFFIX = "')";
	
	private Salle getSalle(Element element) throws Exception {
		Element a = element.select("a").first();
		String href = a.attr("href");
		int inf = PREFIX.length();
		int sup = href.length() - SUFFIX.length();
		String id = href.substring(inf, sup);
		URI uri = URI.create("https://resultats.ffbb.com/here/here_popup.php?id=" + id);
		if (this.doFind(Salle.class, uri) == null) {
			Salle salle = new SalleExtractor().doExtract(uri);
			this.doBind(Salle.class, salle.getURI(), salle);
			return salle;
		} else {
			Salle salle = this.doFind(Salle.class, uri);
			return salle;
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
			équipe.setNom(name);
			this.doBind(Équipe.class, équipe.getURI(), équipe);
			return équipe;
		} else {
			Équipe équipe = this.doFind(Équipe.class, uri);
			équipe.setNom(name);
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
				return null; // throw new ExtractorException(1);
			}
		}
	}
	
	*/
	
}
