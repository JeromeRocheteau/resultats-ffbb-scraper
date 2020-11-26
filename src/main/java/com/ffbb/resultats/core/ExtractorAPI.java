package com.ffbb.resultats.core;

import java.util.List;

import com.ffbb.resultats.api.Appartenance;
import com.ffbb.resultats.api.Engagement;
import com.ffbb.resultats.api.Organisation;
import com.ffbb.resultats.api.Salle;

public class ExtractorAPI {

	public Organisation getOrganisation(String code) throws Exception {
		return new OrganisationExtractor().doExtract(code);
	}

	public List<Appartenance> getAppartenances(Organisation organisation) throws Exception {
		return new AppartenancesExtractor().doExtract(organisation);
	}

	public List<Engagement> getEngagements(Organisation organisation) throws Exception {
		return new EngagementsExtractor().doExtract(organisation);
	}

	public Salle getSalle(Organisation organisation) throws Exception {
		return new SalleExtractor().doExtract(organisation);
	}
    
}
