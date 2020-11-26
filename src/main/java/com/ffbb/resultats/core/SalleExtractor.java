package com.ffbb.resultats.core;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ffbb.resultats.api.Organisation;
import com.ffbb.resultats.api.Salle;

public class SalleExtractor extends AbstractExtractor<Salle> {
	
	private static final String PREFIX = "https://resultats.ffbb.com/here/here_popup.php?id=";
	
	private static final String DATA_PREFIX = "/*<![CDATA[*/";
	private static final String DATA_SUFFIX = ";/*]]>*/";

	private static final Pattern PATTERN_1 = Pattern.compile("var cartoFbi=\\{\"latitude\":(.*),\"longitude\":(.*),\"title\":\"(.*)\",\"cp\":\"(.*)\",\"city\":\"(.*)\",\"errors\":\\[\\]\\};var appId=\"(.*)\";var appCode=\"(.*)\";");

	private static final Pattern PATTERN_2 = Pattern.compile("var cartoFbi=\\{\"latitude\":(.*),\"longitude\":(.*),\"title\":\"(.*)\",\"adress\":\"(.*)\",\"cp\":\"(.*)\",\"city\":\"(.*)\",\"errors\":\\[\\]\\};var appId=\"(.*)\";var appCode=\"(.*)\";");
	
	private Organisation organisation;
	
	public Salle doExtract(Organisation organisation) throws Exception {
		this.organisation = organisation;
		String code = organisation.getCode();
		String link = "http://resultats.ffbb.com/organisation/salle/" + code + ".html";
		URI uri = URI.create(link);
		return this.doParse(uri);
	}
	
	public Salle doParse(URI uri) throws Exception {
		try {
			Document doc = this.getDocument(uri);
			Element body = doc.getElementById("idIfType");
			String value = body.select("table").get(0)
					.select("tr").get(2)
					.select("td").get(1)
					.select("a").attr("href");
			String suffix = value.substring("javascript:openHere('".length(), value.length() - 2);
			String link = PREFIX + suffix;
			Salle salle = this.doExtract(URI.create(link));
			this.organisation.setSalle(salle);
			this.doInfo(salle.toString());
			return salle;
		} catch (Exception e) {
			this.doWarn(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	public Salle doExtract(URI uri) throws Exception {
		String id = uri.toString().substring(PREFIX.length());
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
				this.doInfo("processing : " + code);
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
		Matcher matcher = PATTERN_2.matcher(code);
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
		Matcher matcher = PATTERN_1.matcher(code);
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
