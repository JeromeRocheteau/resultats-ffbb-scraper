package com.ffbb.resultats.core;

import java.net.URI;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.ffbb.resultats.api.Appartenance;
import com.ffbb.resultats.api.Organisation;

public class AppartenancesExtractor extends AbstractExtractor<List<Appartenance>> {
	
	private Organisation organisation;
	
	private OrganisationExtractor extractor = new OrganisationExtractor();
	
	public List<Appartenance> doExtract(Organisation organisation) throws Exception {
		this.organisation = organisation;
		String code = organisation.getCode();
		String link = "http://resultats.ffbb.com/organisation/appartenance/" + code + ".html";
		URI uri = URI.create(link);
		return this.doExtract(uri);
	}

	public List<Appartenance> doExtract(URI uri) throws Exception {
		try {
			Document doc = this.getDocument(uri);
			Element body = doc.getElementById("idIfType");
			String ligueLink = body.select("table").get(0)
					.select("tr").get(0)
					.select("td").get(0)
					.select("a").get(0).attr("href");
			String comitéLink = body.select("table").get(0)
					.select("tr").get(1)
					.select("td").get(0)
					.select("a").get(0).attr("href");
			Organisation ligue = extractor.doExtract(ligueLink.substring(3, ligueLink.length() - 5));
			Organisation comité = extractor.doExtract(comitéLink.substring(3, comitéLink.length() - 5));
			Appartenance appartenanceLigue = new Appartenance(organisation, ligue, Appartenance.Type.Ligue);
			Appartenance appartenanceComité = new Appartenance(organisation, comité, Appartenance.Type.Comité);
			organisation.getAppartenances().add(appartenanceLigue);
			organisation.getAppartenances().add(appartenanceComité);
		} catch (Exception e) {
			this.doWarn(e.getMessage());
			e.printStackTrace();
		}
		return organisation.getAppartenances();
	}
	
}
