package com.ffbb.resultats.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.ffbb.resultats.api.Appartenance;
import com.ffbb.resultats.api.Catégorie;
import com.ffbb.resultats.api.Classements;
import com.ffbb.resultats.api.Division;
import com.ffbb.resultats.api.Engagement;
import com.ffbb.resultats.api.Genre;
import com.ffbb.resultats.api.Journée;
import com.ffbb.resultats.api.Journées;
import com.ffbb.resultats.api.Niveau;
import com.ffbb.resultats.api.Organisation;
import com.ffbb.resultats.api.Rencontres;
import com.ffbb.resultats.api.Salle;
import com.ffbb.resultats.tests.ResultatsExtraction;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RésultatsDB extends ResultatsExtraction {

	private static final SimpleDateFormat ffbbDateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private Connection connection;
	
	private Date début;
	
	private Date fin;
	
	public RésultatsDB() throws Exception {
		super();
		début = ffbbDateTimeFormatter.parse("2021-09-18 00:00");
		fin = ffbbDateTimeFormatter.parse("2021-10-24 23:59");
	}
	
	@Test
	public void doExtract() throws Exception {
		Logger.getAnonymousLogger().setLevel(Level.SEVERE);
		this.doInfo("connexion à la base de données");
		this.doConnect();
		this.doInfo("début de l'extraction");
		try {
			extractor.filtre()/*.niveaux(Niveau.Départemental).catégories(Catégorie.U13).genres(Genre.Féminin)*/.dates(début, fin);
			Organisation organisation = extractor.getOrganisation("2226");
			Assert.assertNotNull(organisation);
			List<Appartenance> appartenances = extractor.getAppartenances(organisation);
			Assert.assertNotNull(appartenances);
			this.doInfo("extraction de la salle de l'organisation: " + organisation.getCode());
			Salle salle = extractor.getSalle(organisation);
			Assert.assertNotNull(salle);
			List<Engagement> engagements = extractor.getEngagements(organisation);
			Assert.assertNotNull(engagements);
			for (Engagement engagement : engagements) {
				Division division = engagement.getDivision();
				this.doInfo("extraction de la division: " + division.toString());
				Assert.assertNotNull(division);
				Journées journées = extractor.getJournées(division);
				Assert.assertNotNull(journées);
				for (Journée journée : journées) {
					Rencontres rencontres = extractor.getRencontres(journée);
					Assert.assertNotNull(rencontres);
				}
				Classements classements = extractor.getClassements(division);
				Assert.assertNotNull(classements);
			}
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
		String database = "jdbc:mysql://app.icam.fr:3306/basketballdb";
		String parameters = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String username = "basketballdb";
		String password = "basketballdb";
	    connection = DriverManager.getConnection(database + parameters, username, password);
	    connection.setAutoCommit(false);
	    extractor.setConnection(connection);
	}
	
	private void doDisconnect() throws Exception {
	    connection.close();
	}
	
}
