package com.ffbb.resultats.core;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ffbb.resultats.api.Division;
import com.ffbb.resultats.api.Journée;
import com.ffbb.resultats.api.Rencontre;
import com.ffbb.resultats.api.Rencontres;
import com.ffbb.resultats.api.Résultat;
import com.ffbb.resultats.api.Salle;
import com.ffbb.resultats.api.Équipe;

public class RencontresExtractor extends AbstractExtractor<Rencontres> {
	
	private SalleExtractor salleExtractor;
	
	private ÉquipeExtractor équipeExtractor;
	
	public RencontresExtractor() {
		salleExtractor = new SalleExtractor();
		équipeExtractor = new ÉquipeExtractor();
	}
		
	public Rencontres doExtract(String code, Integer numéro) throws Exception {
		String link = "https://resultats.ffbb.com/championnat/rencontres/" + code + Integer.toHexString(numéro.intValue()) + ".html";
		URI uri = URI.create(link);
		if (this.doFind(Rencontres.class, uri) == null) {
			return this.doExtract(uri);
		} else {
			return this.doFind(Rencontres.class, uri);
		}
	}

	public Rencontres doExtract(URI uri) throws Exception {
		String link = uri.toString();
		link = link.substring("https://resultats.ffbb.com/championnat/rencontres/".length(), link.length() - 5);
		String code = link.substring(0, 24);
		Integer numéro = Integer.parseInt(link.substring(24), 16);
		Division division = this.getDivision(code);
		équipeExtractor.setDivision(division);
		Journée journée = new Journée(numéro, division);
		Rencontres rencontres = this.getRencontres(journée, uri);
		return rencontres;
	}
	
	private Rencontres getRencontres(Journée journée, URI uri) throws Exception {
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
					Résultat résultat = this.getRésultat(cols.get(5).text());
					Salle salle = this.getSalle(cols.get(6));
					Rencontre rencontre = new Rencontre(journée, numéro, domicile, visiteur, horaire, salle);
					rencontre.setRésultat(résultat);
					rencontres.add(rencontre);
					domicile.getRencontres().add(rencontre);
					visiteur.getRencontres().add(rencontre);
				}
			}
		}
		return rencontres;
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

	private Résultat getRésultat(String text) throws Exception {
		String[] items = text.split("-");
		if (items.length == 2) {
			Integer domicile = Integer.valueOf(items[0].trim());
			Integer visiteur = Integer.valueOf(items[1].trim());
			return new Résultat(domicile, visiteur);
		} else {
			return null;
		}
	}

	private Équipe getÉquipe(Element element) throws Exception {
		Element anchor = element.select("a").first();
		String nom = anchor.text();
		String prefix = "https://resultats.ffbb.com/championnat/";
		String link = prefix + anchor.attr("href").substring(3);
		URI uri = URI.create(link);
		if (this.doFind(Équipe.class, uri) == null) {
			Équipe équipe = équipeExtractor.doExtract(uri);
			équipe.setNom(nom);
			return équipe;			
		} else {
			return this.doFind(Équipe.class, uri);
		}
	}
	
	private Salle getSalle(Element element) throws Exception {
		Element a = element.select("a").first();
		String href = a.attr("href");
		int inf = "javascript:openHere('".length();
		int sup = href.length() - "')".length();
		String id = href.substring(inf, sup);
		URI uri = URI.create("https://resultats.ffbb.com/here/here_popup.php?id=" + id);
		if (this.doFind(Salle.class, uri) == null) {
			Salle salle = salleExtractor.doExtract(uri);
			this.doBind(Salle.class, salle.getURI(), salle);
			return salle;
		} else {
			Salle salle = this.doFind(Salle.class, uri);
			return salle;
		}
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
