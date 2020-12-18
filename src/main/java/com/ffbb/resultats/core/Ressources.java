package com.ffbb.resultats.core;

import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.ffbb.resultats.api.Appartenances;
import com.ffbb.resultats.api.Championnat;
import com.ffbb.resultats.api.Division;
import com.ffbb.resultats.api.Engagements;
import com.ffbb.resultats.api.Extractable;
import com.ffbb.resultats.api.Organisation;
import com.ffbb.resultats.api.Salle;
import com.ffbb.resultats.db.AppartenancesController;
import com.ffbb.resultats.db.ChampionnatController;
import com.ffbb.resultats.db.DivisionController;
import com.ffbb.resultats.db.EngagementsController;
import com.ffbb.resultats.db.OrganisationController;
import com.ffbb.resultats.db.SalleController;

public class Ressources {
	
	private static Ressources instance;
	
	public static Ressources getInstance() {
		if (instance == null) {
			instance = new Ressources();
		}
		return instance;
	}
	
	private Map<String, Object> resources;
	
	private Connection connection;

	private SalleController salleController;
	private OrganisationController organisationController;
	private AppartenancesController appartenancesController;
	private ChampionnatController championnatController;
	private DivisionController divisionController;
	private EngagementsController engagementsController;
	
	public void setConnection(Connection connection) throws SQLException {
		if (this.connection == null || this.connection.isClosed()) {
			this.connection = connection;			
		}
	}
	
	@SuppressWarnings("unchecked")
	public <U extends Extractable> U doFind(Class<U> type, URI uri) {
		Object resource = resources.get(uri.toString());
		if (resource == null) {
			try { resource = this.doRetrieve(type, uri); } 
			catch (Exception e) { e.printStackTrace(); }
		}
		return (U) resource;
	}

	public <U extends Extractable> void doBind(Class<U> type, URI uri, U resource) {
		resources.put(uri.toString(), resource);
		try { this.doInsert(type, resource); } 
		catch (Exception e) { e.printStackTrace(); }
	}

	private <U> void doInsert(Class<U> type, U resource) throws Exception {
		if (connection == null) {
			return;
		} else if (Salle.class.isInstance(resource)) {
			salleController.doSave(connection, (Salle) resource);
		} else if (Organisation.class.isInstance(resource)) {
			organisationController.doSave(connection, (Organisation) resource);
		} else if (Appartenances.class.isInstance(resource)) {
			appartenancesController.doSave(connection, (Appartenances) resource);
		} else if (Championnat.class.isInstance(resource)) {
			championnatController.doSave(connection, (Championnat) resource);
		} else if (Division.class.isInstance(resource)) {
			divisionController.doSave(connection, (Division) resource);
		} else if (Engagements.class.isInstance(resource)) {
			engagementsController.doSave(connection, (Engagements) resource);
		} else {
			return;
		}
	}

	@SuppressWarnings("unchecked")
	private <U> U doRetrieve(Class<U> type, URI uri) throws Exception {
		if (connection == null) {
			return null;
		} else if (Salle.class.isAssignableFrom(type)) {
			return (U) salleController.doFind(connection, uri);
		} else if (Organisation.class.isAssignableFrom(type)) {
			return (U) organisationController.doFind(connection, uri);
		} else if (Appartenances.class.isAssignableFrom(type)) {
			return (U) appartenancesController.doFind(connection, uri);
		} else if (Championnat.class.isAssignableFrom(type)) {
			return (U) championnatController.doFind(connection, uri);
		} else if (Division.class.isAssignableFrom(type)) {
			return (U) divisionController.doFind(connection, uri);
		} else if (Engagements.class.isAssignableFrom(type)) {
			return (U) engagementsController.doFind(connection, uri);
		} else {
			return null;
		}
	}
	
	public Ressources() {
		resources = new HashMap<String, Object>(1024);
		salleController = new SalleController();
		organisationController = new OrganisationController();
		appartenancesController = new AppartenancesController();
		championnatController = new ChampionnatController();
		divisionController = new DivisionController();
		engagementsController = new EngagementsController();
		this.setExempt();
	}
	
	private void setExempt() {
		URI uri = URI.create("http://resultats.ffbb.com/organisation/0.html");
		Organisation exempt = this.doFind(Organisation.class, uri);
		if (exempt == null) {
			exempt = new Organisation(0L, "0");
			exempt.setNom("Exempt");
			exempt.setType(Organisation.Type.Entente);
			this.doBind(Organisation.class, uri, exempt);
		}
	}
	
}
