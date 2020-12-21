package com.ffbb.resultats.core;

import java.net.URI;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ffbb.resultats.RésultatsFFBB;
import com.ffbb.resultats.api.Division;
import com.ffbb.resultats.api.Journée;
import com.ffbb.resultats.api.Journées;

public class JournéesExtractor extends AbstractExtractor<Journées> {

	public JournéesExtractor(RésultatsFFBB résultatsFFBB) {
		super(résultatsFFBB);
	}

	private Division division;
	
	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public Journées doExtract(URI uri) throws Exception {
		Journées journées = this.doFind(Journées.class, uri);
		if (journées == null) {
			journées = this.doParse(uri);
			this.doBind(Journées.class, uri, journées);
			division.getJournées().addAll(journées);
		}
		return journées;
	}

	public Journées doParse(URI uri) throws Exception {
		String code = uri.toString();
		code = code.substring("https://resultats.ffbb.com/championnat/journees/".length(), code.length() - 5);
		Journées journées = this.getJournées(division, uri);
		return journées;
	}

	private Journées getJournées(Division division, URI uri) throws Exception {
		Journées journées = new Journées(division);
		Document document = this.getDocument(uri);
		Element body = document.getElementById("idIfType");
		Elements tds = body.select("td");
		for (Element td : tds) {
			String id = td.attr("id");
			if (id == null) {
				continue;
			} else if (id.startsWith("p")) {
				Integer numéro = Integer.valueOf(td.text());
				Journée journée = new Journée(numéro, division);
				journées.add(journée);
			}
		}
		return journées;
	}
		
}
