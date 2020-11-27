package com.ffbb.resultats.core;

import java.net.URI;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.ffbb.resultats.RésultatsFFBB;
import com.ffbb.resultats.api.Appartenance;
import com.ffbb.resultats.api.Appartenances;
import com.ffbb.resultats.api.Organisation;

public class AppartenancesExtractor extends AbstractExtractor<List<Appartenance>> {
	
	public AppartenancesExtractor(RésultatsFFBB résultatsFFBB) {
		super(résultatsFFBB);
	}

	private Organisation organisation;
	
	public Organisation getOrganisation() {
		return organisation;
	}

	public void setOrganisation(Organisation organisation) {
		this.organisation = organisation;
	}
	
	public Appartenances doExtract(URI uri) throws Exception {
		if (this.doFind(Appartenances.class, uri) == null) {
			try {
				Appartenances appartenances = this.doParse(uri);
				this.doBind(Appartenances.class, uri, appartenances);
				organisation.getAppartenances().addAll(appartenances);
				return appartenances;
			} catch (Exception e) {
				return new Appartenances(organisation);
			}
		} else {
			return this.doFind(Appartenances.class, uri);
		}
	}

	private Appartenances doParse(URI uri) throws Exception {
		Appartenances appartenances = new Appartenances(organisation);
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
		Organisation ligue = this.getOrganisation(ligueLink.substring(3, ligueLink.length() - 5));
		Organisation comité = this.getOrganisation(comitéLink.substring(3, comitéLink.length() - 5));
		Appartenance appartenanceLigue = new Appartenance(organisation, ligue, Appartenance.Type.Ligue);
		Appartenance appartenanceComité = new Appartenance(organisation, comité, Appartenance.Type.Comité);
		appartenances.add(appartenanceLigue);
		appartenances.add(appartenanceComité);
		return appartenances;
	}
	
}
