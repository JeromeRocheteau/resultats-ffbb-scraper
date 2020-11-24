package com.ffbb.resultats;

import java.util.Date;
import java.util.List;

import com.ffbb.resultats.api.Appartenance;
import com.ffbb.resultats.api.Championnat;
import com.ffbb.resultats.api.Classement;
import com.ffbb.resultats.api.Engagement;
import com.ffbb.resultats.api.Organisation;
import com.ffbb.resultats.api.Rencontre;
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
	
    public Classement getClassement(Organisation organisation, Championnat championnat) throws Exception {
    	return new ClassementExtractor().doExtract(organisation, championnat);
    }

	public List<Rencontre> getRencontres(Organisation organisation, Championnat championnat) throws Exception {
		return new RencontresExtractor().doExtract(organisation, championnat);
	}

	public List<Rencontre> getRencontres(Organisation organisation, Championnat championnat, Date début, Date fin) throws Exception {
		return new RencontresExtractor().doExtract(organisation, championnat, début, fin);
	}
    
}
