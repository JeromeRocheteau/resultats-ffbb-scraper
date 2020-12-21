package com.ffbb.resultats.core;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ffbb.resultats.RésultatsFFBB;
import com.ffbb.resultats.api.Organisation;
import com.ffbb.resultats.api.Salle;

public class SalleExtractor extends AbstractExtractor<Salle> {
	
	public SalleExtractor(RésultatsFFBB résultatsFFBB) {
		super(résultatsFFBB);
	}

	public Salle doExtract(URI uri) throws Exception {
		String prefix = "https://resultats.ffbb.com/organisation/salle/";
		String link = uri.toString();
		if (link.startsWith(prefix)) {
			String code = link.substring(prefix.length(), link.length() - 5);
			Organisation organisation = this.getOrganisation(code);
			URI u = this.doGrab(organisation, uri);
			Salle salle = this.doFind(Salle.class, u);
			if (salle == null) {
				salle = this.doParse(u);
				organisation.setSalle(salle);
				this.doBind(Salle.class, u, salle);
				this.doLink(Organisation.class, Salle.class, uri, organisation, salle);
			}
			return salle;
		} else {
			Salle salle = this.doFind(Salle.class, uri);
			if (salle == null) {
				salle = this.doParse(uri);
				this.doBind(Salle.class, uri, salle);
			}
			return salle;
		}
	}
	
	private URI doGrab(Organisation organisation, URI uri) throws Exception {
		String prefix = "https://resultats.ffbb.com/here/here_popup.php?id=";
		Document doc = this.getDocument(uri);
		Element body = doc.getElementById("idIfType");
		String value = body.select("table").get(0)
				.select("tr").get(2)
				.select("td").get(1)
				.select("a").attr("href");
		String suffix = value.substring("javascript:openHere('".length(), value.length() - 2);
		String link = prefix + suffix;
		return URI.create(link);
	}
	
	private Salle doParse(URI uri) throws Exception {
		String prefix = "https://resultats.ffbb.com/here/here_popup.php?id=";
		String DATA_PREFIX = "/*<![CDATA[*/";
		String DATA_SUFFIX = ";/*]]>*/";
		String id = uri.toString().substring(prefix.length());
		Salle salle = new Salle(Long.valueOf(id));
		Document doc = this.getDocument(uri);
		Elements scripts = doc.getElementsByTag("script");
		for (Element script : scripts) {
			String data = script.data();
			if (data == null) {
			} else if (data.isEmpty()) {
			} else if (data.startsWith(DATA_PREFIX) && data.endsWith(DATA_SUFFIX)) {
				int inf = DATA_PREFIX.length();
				int sup = data.length() - DATA_SUFFIX.length();
				String code = data.substring(inf, sup);
				if (matchWithAddress(salle, code) == false) {
					if (matchWithoutAddress(salle, code) == false) {
						salle = null;
					}
				}
			}
		}
		return salle;
	}

	private boolean matchWithAddress(Salle salle, String code) {
		Pattern pattern = Pattern.compile("var cartoFbi=\\{\"latitude\":(.*),\"longitude\":(.*),\"title\":\"(.*)\",\"adress\":\"(.*)\",\"cp\":\"(.*)\",\"city\":\"(.*)\",\"errors\":\\[\\]\\};var appId=\"(.*)\";var appCode=\"(.*)\";");
		Matcher matcher = pattern.matcher(code);
		boolean hasMatches = matcher.matches();
		if (hasMatches && matcher.groupCount() == 8) {
			Float latitude = Float.valueOf(matcher.group(1));
			Float longitude = Float.valueOf(matcher.group(2));
			String nom = matcher.group(3);
			String adresse = matcher.group(4);
			String codePostal = matcher.group(5);
			String ville = matcher.group(6);
			salle.setLatitude(latitude);
			salle.setLongitude(longitude);
			salle.setNom(nom);
			salle.setAdresse(adresse);
			salle.setCodePostal(codePostal);
			salle.setVille(ville);
		}
		return hasMatches;
	}

	private boolean matchWithoutAddress(Salle salle, String code) {
		Pattern pattern = Pattern.compile("var cartoFbi=\\{\"latitude\":(.*),\"longitude\":(.*),\"title\":\"(.*)\",\"cp\":\"(.*)\",\"city\":\"(.*)\",\"errors\":\\[\\]\\};var appId=\"(.*)\";var appCode=\"(.*)\";");
		Matcher matcher = pattern.matcher(code);
		boolean hasMatches = matcher.matches();
		if (hasMatches && matcher.groupCount() == 7) {
			Float latitude = Float.valueOf(matcher.group(1));
			Float longitude = Float.valueOf(matcher.group(2));
			String nom = matcher.group(3);
			String codePostal = matcher.group(4);
			String ville = matcher.group(5);
			salle.setLatitude(latitude);
			salle.setLongitude(longitude);
			salle.setNom(nom);
			salle.setCodePostal(codePostal);
			salle.setVille(ville);
		}
		return hasMatches;
	}
		
}
