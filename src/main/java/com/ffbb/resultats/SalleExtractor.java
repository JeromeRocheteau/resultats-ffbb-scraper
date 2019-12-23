package com.ffbb.resultats;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ffbb.resultats.api.Salle;

public class SalleExtractor extends AbstractExtractor<Salle> {
	
	private static final String PREFIX = "https://resultats.ffbb.com/here/here_popup.php?id=";
	
	private static final String DATA_PREFIX = "/*<![CDATA[*/";
	private static final String DATA_SUFFIX = ";/*]]>*/";
	
	private static final Pattern PATTERN = Pattern.compile("var cartoFbi=\\{\"latitude\":(.*),\"longitude\":(.*),\"title\":\"(.*)\",\"adress\":\"(.*)\",\"cp\":\"(.*)\",\"city\":\"(.*)\",\"errors\":\\[\\]\\};var appId=\"(.*)\";var appCode=\"(.*)\";");
	
	public Salle doExtract(URI uri) throws Exception {
		String id = uri.toString().substring(PREFIX.length());
		Salle salle = new Salle(id);
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
				Matcher matcher = PATTERN.matcher(code);
				boolean hasMatches = matcher.matches();
				if (hasMatches && matcher.groupCount() == 8) {
					Float latitude = Float.valueOf(matcher.group(1));
					Float longitude = Float.valueOf(matcher.group(2));
					String dénomination = matcher.group(3);
					String adresse = matcher.group(4);
					String codePostal = matcher.group(5);
					String ville = matcher.group(6);
					salle.setLatitude(latitude);
					salle.setLongitude(longitude);
					salle.setDénomination(dénomination);
					salle.setAdresse(adresse);
					salle.setCodePostal(codePostal);
					salle.setVille(ville);
				}
			}
		}
		return salle;
	}
		
}
