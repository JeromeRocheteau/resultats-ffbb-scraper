package com.ffbb.resultats;

import java.net.URI;

import com.ffbb.resultats.api.Appartenances;
import com.ffbb.resultats.api.Championnat;
import com.ffbb.resultats.api.Classements;
import com.ffbb.resultats.api.Division;
import com.ffbb.resultats.api.Engagements;
import com.ffbb.resultats.api.Journée;
import com.ffbb.resultats.api.Journées;
import com.ffbb.resultats.api.Organisation;
import com.ffbb.resultats.api.Rencontres;
import com.ffbb.resultats.api.Salle;
import com.ffbb.resultats.api.Équipe;
import com.ffbb.resultats.core.AppartenancesExtractor;
import com.ffbb.resultats.core.ChampionnatExtractor;
import com.ffbb.resultats.core.ClassementsExtractor;
import com.ffbb.resultats.core.DivisionExtractor;
import com.ffbb.resultats.core.EngagementsExtractor;
import com.ffbb.resultats.core.JournéesExtractor;
import com.ffbb.resultats.core.OrganisationExtractor;
import com.ffbb.resultats.core.RencontresExtractor;
import com.ffbb.resultats.core.SalleExtractor;
import com.ffbb.resultats.core.ÉquipeExtractor;

public class RésultatsFFBB {
	
	private OrganisationExtractor organisationExtractor;
	
	private AppartenancesExtractor appartenancesExtractor;
	
	private SalleExtractor salleExtractor;
	
	private EngagementsExtractor engagementsExtractor;
	
	private ChampionnatExtractor championnatExtractor;
	
	private DivisionExtractor divisionExtractor;
	
	private ÉquipeExtractor équipeExtractor;
	
	private JournéesExtractor journéesExtractor;

	private RencontresExtractor rencontresExtractor;
	
	private ClassementsExtractor classementsExtractor;
	
	public RésultatsFFBB() {
		organisationExtractor = new OrganisationExtractor(this);
		appartenancesExtractor = new AppartenancesExtractor(this);
		salleExtractor = new SalleExtractor(this);
		engagementsExtractor = new EngagementsExtractor(this);
		championnatExtractor = new ChampionnatExtractor(this);
		divisionExtractor = new DivisionExtractor(this);
		équipeExtractor = new ÉquipeExtractor(this);
		journéesExtractor = new JournéesExtractor(this);
		rencontresExtractor = new RencontresExtractor(this);
		classementsExtractor = new ClassementsExtractor(this);
	}

	public Organisation getOrganisation(String code) throws Exception {
		String link = "http://resultats.ffbb.com/organisation/" + code + ".html";
		URI uri = URI.create(link);
		return organisationExtractor.doExtract(uri);
	}

	public Appartenances getAppartenances(Organisation organisation) throws Exception {
		String link = "http://resultats.ffbb.com/organisation/appartenance/" + organisation.getCode() + ".html";
		URI uri = URI.create(link);
		appartenancesExtractor.setOrganisation(organisation);
		return appartenancesExtractor.doExtract(uri);
	}

	public Salle getSalle(Organisation organisation) throws Exception {
		String link = "http://resultats.ffbb.com/organisation/salle/" + organisation.getCode() + ".html";
		URI uri = URI.create(link); 
		return salleExtractor.doExtract(uri);
	}

	public Salle getSalle(Long id) throws Exception {
		String link = "https://resultats.ffbb.com/here/here_popup.php?id=" + id;
		URI uri = URI.create(link); 
		return salleExtractor.doExtract(uri);
	}

	public Engagements getEngagements(Organisation organisation) throws Exception {
		String code = organisation.getCode();
		String link = "http://resultats.ffbb.com/organisation/engagements/" + code + ".html";
		URI uri = URI.create(link);
		engagementsExtractor.setOrganisation(organisation);
		return engagementsExtractor.doExtract(uri);
	}

	public Championnat getChampionnat(String code) throws Exception {
		String link = "https://resultats.ffbb.com/championnat/" + code + ".html";
		URI uri = URI.create(link);
		return championnatExtractor.doExtract(uri);
	}
	
	public Division getDivision(Long id, String code, Long division) throws Exception {
		String link = "https://resultats.ffbb.com/championnat/" + code + ".html?r=" + id + "&d=" + division;
		URI uri = URI.create(link);
		return divisionExtractor.doExtract(uri);
	}

	public Journées getJournées(Division division) throws Exception {
		String link = "https://resultats.ffbb.com/championnat/journees/" + division.getCode() + ".html";
		URI uri = URI.create(link);
		journéesExtractor.setDivision(division);
		return journéesExtractor.doExtract(uri);
	}

	public Rencontres getRencontres(Journée journée) throws Exception {
		String prefix = journée.getDivision().getCode();
		String suffix = Integer.toHexString(journée.getNuméro().intValue());
		String link = "https://resultats.ffbb.com/championnat/rencontres/" + prefix + suffix + ".html";
		URI uri = URI.create(link);
		rencontresExtractor.setJournée(journée);
		return rencontresExtractor.doExtract(uri);
	}

	public Classements getClassements(Division division) throws Exception {
		String link = "https://resultats.ffbb.com/championnat/classements/" + division.getCode() + ".html";
		URI uri = URI.create(link);
		classementsExtractor.setDivision(division);
		return classementsExtractor.doExtract(uri);
	}
	
	public Équipe getÉquipe(Division division, Long id, String code) throws Exception {
		Long r = division.getChampionnat().getId();
		Long p = division.getId();
		String link = "https://resultats.ffbb.com/championnat/equipe/" + code + ".html?r=" + r + "&p=" + p + "&d=" + id;
		URI uri = URI.create(link);
		équipeExtractor.setDivision(division);
		return équipeExtractor.doExtract(uri);
	}
	
}
