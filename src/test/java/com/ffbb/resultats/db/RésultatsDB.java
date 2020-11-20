package com.ffbb.resultats.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.ffbb.resultats.api.Catégorie;
import com.ffbb.resultats.api.Championnat;
import com.ffbb.resultats.api.Compétition;
import com.ffbb.resultats.api.Engagement;
import com.ffbb.resultats.api.Genre;
import com.ffbb.resultats.api.Niveau;
import com.ffbb.resultats.api.Organisation;
import com.ffbb.resultats.api.Rencontre;
import com.ffbb.resultats.api.Résultat;
import com.ffbb.resultats.api.Salle;
import com.ffbb.resultats.api.Équipe;
import com.ffbb.resultats.filtres.ChampionnatFiltre;
import com.ffbb.resultats.filtres.Filtre;
import com.ffbb.resultats.tests.ResultatsExtraction;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RésultatsDB extends ResultatsExtraction {

	private static final SimpleDateFormat ffbbDateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private Connection connection;
	
	private Date début;
	
	private Date fin;
	
	private Queue<Organisation> organisations;
	private Map<Organisation, Boolean> statues;
	
	public RésultatsDB() throws Exception {
		super();
		début = ffbbDateTimeFormatter.parse("2020-09-01 00:00");
		fin = ffbbDateTimeFormatter.parse("2020-10-31 23:59");
		organisations = new LinkedList<Organisation>();
		statues = new HashMap<Organisation, Boolean>();
	}
	
	@Test
	public void doExtract() throws Exception {
		Logger.getAnonymousLogger().setLevel(Level.SEVERE);
		this.doInfo("connexion à la base de données");
		this.doConnect();
		this.doInfo("début de l'extraction");
		try {
			Organisation organisation = extractor.getOrganisation("2226");
			Assert.assertNotNull(organisation);
			List<Engagement> engagements = extractor.getEngagements(organisation);
			Assert.assertNotNull(engagements);
			ChampionnatFiltre filtre = new ChampionnatFiltre().niveaux(Niveau.Départemental).catégories(Catégorie.U13).phases(1).genres(Genre.Féminin).divisions(0);
			engagements.forEach(engagement -> doExtract(engagement, filtre));
			this.doInfo("fin de l'extraction");
		} finally {
			this.doInfo("déconnexion de la base de données");
			this.doDisconnect();			
		}
	}

	private void doInfo(String message) {
		Logger.getAnonymousLogger().log(Level.INFO, message);
	}

	private void doWarn(String message) {
		Logger.getAnonymousLogger().log(Level.WARNING, message);
	}

	private void doConnect() throws Exception {
	    Properties properties = new Properties();
	    properties.put("user", ""); // TODO
	    properties.put("password", ""); // TODO
	    connection = DriverManager.getConnection("jdbc:mysql://app.icam.fr:3306/basketballdb" + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "louR8jay");
	    connection.setAutoCommit(false);
	}
	
	private void doDisconnect() throws Exception {
	    connection.close();
	}
	
	private void doExtract(Engagement engagement, Filtre filtre) {
		try {
			organisations.clear();
			statues.clear();
			Organisation organisation = engagement.getOrganisation();
			Compétition compétition = engagement.getCompétition();
			if (filtre.match(compétition)) {
				Championnat championnat = (Championnat) compétition;
				Organisation current = organisation;
				organisations.offer(organisation);
				statues.put(organisation, Boolean.TRUE);
				do {
					this.doExtract(championnat, current);
					current = organisations.poll();
				} while (current != null);
			}
		} catch (Exception e) {
			this.doWarn(e.getMessage());
			e.printStackTrace();			
		}
	}

	private void doExtract(Championnat championnat, Organisation organisation) {
		try {
			List<Rencontre> rencontres = extractor.getRencontres(organisation, championnat, début, fin);
			rencontres.forEach(rencontre -> doExtract(championnat, organisation, rencontre));
		} catch (Exception e) {
			this.doWarn(e.getMessage());
			e.printStackTrace();
		}			
	}

	private void doExtract(Championnat championnat, Organisation organisation, Rencontre rencontre) {
		try {
			this.doSchedule(championnat, organisation, rencontre);
		} catch (Exception e) {
			this.doWarn(e.getMessage());
			e.printStackTrace();			
		}
		try {
			this.doPersist(championnat, organisation, rencontre);
		} catch (Exception e) {
			this.doWarn(e.getMessage());
			e.printStackTrace();			
		}
	}

	private void doSchedule(Championnat championnat, Organisation organisation, Rencontre rencontre) throws Exception {
		Organisation domicile = rencontre.getDomicile().getOrganisation();
		Organisation visiteur = rencontre.getVisiteur().getOrganisation();
		if (domicile.getCode().equals(organisation.getCode())) {
			if (todo(visiteur) == false) {
				this.doWarn("planification de l'organisation " + visiteur);
				organisations.offer(visiteur);
				statues.put(visiteur, Boolean.FALSE);
			}
		} else if (visiteur.getCode().equals(organisation.getCode())) {
			if (todo(domicile) == false) {
				this.doWarn("planification de l'organisation " + domicile);
				organisations.offer(domicile);
				statues.put(domicile, Boolean.FALSE);
			}
		}
	}
	
	private void doPersist(Championnat championnat, Organisation organisation, Rencontre rencontre) throws Exception {
		Organisation clubDomicile = rencontre.getDomicile().getOrganisation();
		if (this.done(clubDomicile) == false) {
			this.doing(clubDomicile);
		}
		Organisation clubVisiteur = rencontre.getVisiteur().getOrganisation();
		if (this.done(clubVisiteur) == false) {
			this.doing(clubVisiteur);
		}
		if (this.done(championnat) == false) {
			Compétition compétition = (Compétition) championnat;
			long id = this.doing(compétition);
			championnat.setId(id);
			this.doing(championnat);
		}
		Équipe équipeDomicile = rencontre.getDomicile();
		if (this.done(équipeDomicile) == false) {
			Engagement engagementDomicile = (Engagement) équipeDomicile;
			long id = this.doing(engagementDomicile);
			équipeDomicile.setId(id);
			this.doing(équipeDomicile);
		}
		Équipe équipeVisiteur = rencontre.getVisiteur();
		if (this.done(équipeVisiteur) == false) {
			Engagement engagementVisiteur = (Engagement) équipeVisiteur;
			long id = this.doing(engagementVisiteur);
			équipeVisiteur.setId(id);
			this.doing(équipeVisiteur);
		}
		Salle salle = rencontre.getSalle();
		if (this.defined(salle) == true && done(salle) == false) {
			this.doing(salle);
		}
		Résultat résultat = rencontre.getRésultat();
		if (this.done(rencontre) == false) {
			long id = this.doing(rencontre);
			if ((résultat == null) == false) {
				résultat.setId(id);
			}
		} else {
			if ((résultat == null) == false) {
				résultat.setId(rencontre.getId());
			}
		}
		if (this.defined(résultat) == true && done(résultat) == false) {
			this.doing(résultat);
		}
	}
	
	/* Organisation */

	private boolean todo(Organisation organisation) throws Exception {
		return organisations.contains(organisation) && statues.get(organisation).equals(Boolean.FALSE);
	}

	private boolean done(Organisation organisation) throws Exception {
		OrganisationReader organisationReader = new OrganisationReader(organisation);
		return organisationReader.doRead(connection).booleanValue();	
	}
	
	private void doing(Organisation organisation) throws Exception {
		OrganisationUpdater organisationUpdater = new OrganisationUpdater(organisation);
		boolean done = organisationUpdater.doUpdate(connection).booleanValue();
		this.doInfo("traitement de l'organisation " + organisation + " : " + (done ? "succès" : "échec"));		
	}
	
	/* Compétition */
	
	private long doing(Compétition compétition) throws Exception {
		CompétitionUpdater compétitionUpdater = new CompétitionUpdater(compétition);
		long id = compétitionUpdater.doUpdate(connection).longValue();
		this.doInfo("traitement de la rencontre de " + compétition + " : " + (id > 0 ? "succès" : "échec"));
		return id;
	}
	
	/* Championnat */

	private boolean done(Championnat championnat) throws Exception {
		ChampionnatReader championnatReader = new ChampionnatReader(championnat);
		return championnatReader.doRead(connection).booleanValue();	
	}

	private void doing(Championnat championnat) throws Exception {
		ChampionnatUpdater championnatUpdater = new ChampionnatUpdater(championnat);
		boolean done = championnatUpdater.doUpdate(connection).booleanValue();
		this.doInfo("traitement du championnat " + championnat + " : " + (done ? "succès" : "échec"));	
	}
	
	/* Engagement */

	private long doing(Engagement engagement) throws Exception {
		EngagementUpdater engagementUpdater = new EngagementUpdater(engagement);
		long id = engagementUpdater.doUpdate(connection).longValue();
		this.doInfo("traitement de l'engagement de " + engagement + " : " + (id > 0 ? "succès" : "échec"));
		return id;
	}
	
	/* Équipe */

	private boolean done(Équipe équipe) throws Exception {
		ÉquipeReader équipeReader = new ÉquipeReader(équipe);
		return équipeReader.doRead(connection).booleanValue();
	}

	private void doing(Équipe équipe) throws Exception {
		ÉquipeUpdater équipeUpdater = new ÉquipeUpdater(équipe);
		boolean done = équipeUpdater.doUpdate(connection).booleanValue();
		this.doInfo("traitement de l'équipe " + équipe + " : " + (done ? "succès" : "échec"));	
	}
	
	/* Rencontre */

	private boolean done(Rencontre rencontre) throws Exception {
		RencontreReader rencontreReader = new RencontreReader(rencontre);
		return rencontreReader.doRead(connection).booleanValue();
	}

	private long doing(Rencontre rencontre) throws Exception {
		RencontreUpdater rencontreUpdater = new RencontreUpdater(rencontre);
		long id = rencontreUpdater.doUpdate(connection).longValue();
		this.doInfo("traitement de la rencontre de " + rencontre + " : " + (id > 0 ? "succès" : "échec"));
		return id;
	}

	/* Salle */

	private boolean defined(Salle salle) {
		return (salle.getId() == null || salle.getLatitude() == null || salle.getLongitude() == null || salle.getDénomination() == null || salle.getAdresse() == null || salle.getCodePostal() == null || salle.getVille() == null) == false;
	}

	private boolean done(Salle salle) throws Exception {
		SalleReader salleReader = new SalleReader(salle);
		return salleReader.doRead(connection).booleanValue();		
	}

	private void doing(Salle salle) throws Exception {
		SalleUpdater salleUpdater = new SalleUpdater(salle);
		boolean done = salleUpdater.doUpdate(connection).booleanValue();
		this.doInfo("traitement de la salle " + salle + " : " + (done ? "succès" : "échec"));
	}
	
	/* Résultat */

	private boolean defined(Résultat résultat) {
		return (résultat == null || résultat.getDomicile() == null || résultat.getVisiteur() == null) == false;
	}

	private boolean done(Résultat résultat) throws Exception {
		RésultatReader résultatReader = new RésultatReader(résultat);
		return résultatReader.doRead(connection).booleanValue();		
	}

	private void doing(Résultat résultat) throws Exception {
		RésultatUpdater résultatUpdater = new RésultatUpdater(résultat);
		boolean done = résultatUpdater.doUpdate(connection).booleanValue();
		this.doInfo("traitement du résultat " + résultat + " : " + (done ? "succès" : "échec"));
	}
	
}
