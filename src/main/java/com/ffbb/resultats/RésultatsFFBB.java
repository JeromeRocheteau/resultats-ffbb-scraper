package com.ffbb.resultats;

import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;

import com.ffbb.resultats.api.Appartenances;
import com.ffbb.resultats.api.Championnat;
import com.ffbb.resultats.api.Classements;
import com.ffbb.resultats.api.Division;
import com.ffbb.resultats.api.Engagement;
import com.ffbb.resultats.api.Engagements;
import com.ffbb.resultats.api.Filtre;
import com.ffbb.resultats.api.Journée;
import com.ffbb.resultats.api.Journées;
import com.ffbb.resultats.api.Organisation;
import com.ffbb.resultats.api.Rencontre;
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
	
	private Filtre filtre;
	
	private String phantomJsPath;
	
	public String getPhantomJsPath() {
		return phantomJsPath;
	}
	
	public void setConnection(Connection connection) throws SQLException {
		organisationExtractor.setConnection(connection);
		appartenancesExtractor.setConnection(connection);
		salleExtractor.setConnection(connection);
		engagementsExtractor.setConnection(connection);
		championnatExtractor.setConnection(connection);
		divisionExtractor.setConnection(connection);
		équipeExtractor.setConnection(connection);
		journéesExtractor.setConnection(connection);
		rencontresExtractor.setConnection(connection);
		classementsExtractor.setConnection(connection);
	}

	public RésultatsFFBB(String phantomjsPath) {
		this.phantomJsPath = phantomjsPath;
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
		filtre = new Filtre();
	}

	public Filtre filtre() {
		filtre.clear();
		return filtre;
	}
	
	public Organisation getOrganisation(String code) throws Exception {
		String link = "https://resultats.ffbb.com/organisation/" + code + ".html";
		URI uri = URI.create(link);
		return organisationExtractor.doExtract(uri);
	}

	public Appartenances getAppartenances(Organisation organisation) throws Exception {
		String link = "https://resultats.ffbb.com/organisation/appartenance/" + organisation.getCode() + ".html";
		URI uri = URI.create(link);
		appartenancesExtractor.setOrganisation(organisation);
		return appartenancesExtractor.doExtract(uri);
	}

	public Salle getSalle(Organisation organisation) throws Exception {
		String link = "https://resultats.ffbb.com/organisation/salle/" + organisation.getCode() + ".html";
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
		String link = "https://resultats.ffbb.com/organisation/engagements/" + code + ".html";
		URI uri = URI.create(link);
		engagementsExtractor.setOrganisation(organisation);
		Engagements engagements = engagementsExtractor.doExtract(uri);
		return this.doFilter(organisation, engagements);
	}

	private Engagements doFilter(Organisation organisation, Engagements engagements) {
		Engagements results = new Engagements(organisation);
		for (Engagement engagement : engagements) {
			if (filtre.match(engagement.getDivision())) {
				results.add(engagement);
			}
		}
		return results;
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
		Journées journées = journéesExtractor.doExtract(uri);
		return this.doFilter(division, journées);
	}

	private Journées doFilter(Division division, Journées journées) {
		Journées results = new Journées(division);
		for (Journée journée : journées) {
			if (filtre.match(journée)) {
				results.add(journée);
			}
		}
		return results;
	}

	public Rencontres getRencontres(Journée journée) throws Exception {
		String prefix = journée.getDivision().getCode();
		String suffix = Integer.toHexString(journée.getNuméro().intValue());
		String link = "https://resultats.ffbb.com/championnat/rencontres/" + prefix + suffix + ".html";
		URI uri = URI.create(link);
		rencontresExtractor.setJournée(journée);
		Rencontres rencontres = rencontresExtractor.doExtract(uri);
		return this.doFilter(journée, rencontres);
	}

	private Rencontres doFilter(Journée journée, Rencontres rencontres) {
		Rencontres results = new Rencontres(journée);
		for (Rencontre rencontre : rencontres) {
			if (filtre.match(rencontre)) {
				results.add(rencontre);
			}
		}
		return results;
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
