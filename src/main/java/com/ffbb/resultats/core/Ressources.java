package com.ffbb.resultats.core;

import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.ffbb.resultats.api.Appartenances;
import com.ffbb.resultats.api.Championnat;
import com.ffbb.resultats.api.Classements;
import com.ffbb.resultats.api.Division;
import com.ffbb.resultats.api.Engagements;
import com.ffbb.resultats.api.Extractable;
import com.ffbb.resultats.api.Journées;
import com.ffbb.resultats.api.Organisation;
import com.ffbb.resultats.api.Rencontres;
import com.ffbb.resultats.api.Salle;
import com.ffbb.resultats.api.Équipe;
import com.ffbb.resultats.db.AppartenancesController;
import com.ffbb.resultats.db.ChampionnatController;
import com.ffbb.resultats.db.ClassementsController;
import com.ffbb.resultats.db.OrganisationSalleController;
import com.ffbb.resultats.db.RencontresController;
import com.ffbb.resultats.db.DivisionController;
import com.ffbb.resultats.db.EngagementsController;
import com.ffbb.resultats.db.JournéesController;
import com.ffbb.resultats.db.OrganisationController;
import com.ffbb.resultats.db.SalleController;
import com.ffbb.resultats.db.ÉquipeController;

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
	private OrganisationSalleController organisationSalleController;
	private AppartenancesController appartenancesController;
	private ChampionnatController championnatController;
	private DivisionController divisionController;
	private EngagementsController engagementsController;
	private ÉquipeController équipeController;
	private JournéesController journéesController;
	private RencontresController rencontresController;
	private ClassementsController classementsController;
	
	public void setConnection(Connection connection) throws SQLException {
		if (this.connection == null || this.connection.isClosed()) {
			this.connection = connection;
			this.setExempt();
		}
	}
	
	@SuppressWarnings("unchecked")
	public <U extends Extractable> U doFind(Class<U> type, URI uri) {
		Object resource = resources.get(uri.toString());
		if (resource == null) {
			try { 
				resource = this.doRetrieve(type, uri);
			} catch (Exception e) { e.printStackTrace(); }
		}
		return (U) resource;
	}

	public <U extends Extractable> void doBind(Class<U> type, URI uri, U resource) {
		resources.put(uri.toString(), resource);
		try { 
			this.doInsert(type, resource); 
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	public <U extends Extractable,V extends Extractable> void doLink(Class<U> resourceType, Class<V> linkedResourceType, URI uri, U resource, V linkedResource) throws Exception {
		// System.out.println("LINK " + linkedResource.getURI() + " to " + resource.getURI());
		if (connection == null) {
			return;
		} else if (resources.get(uri.toString()) == null) {
			if (Salle.class.isInstance(linkedResource) && Organisation.class.isInstance(resource)) {
				resources.put(uri.toString(), resource);
				organisationSalleController.doSave(connection, (Organisation) resource);
			}
		} 
	}

	private <U extends Extractable> void doInsert(Class<U> type, U resource) throws Exception {
		// System.out.println("SAVE " + resource.getURI() + " as " + type.getSimpleName().toLowerCase());
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
		} else if (Équipe.class.isInstance(resource)) {
			équipeController.doSave(connection, (Équipe) resource);
		} else if (Journées.class.isInstance(resource)) {
			journéesController.doSave(connection, (Journées) resource);
		} else if (Rencontres.class.isInstance(resource)) {
			rencontresController.doSave(connection, (Rencontres) resource);
		} else if (Classements.class.isInstance(resource)) {
			classementsController.doSave(connection, (Classements) resource);
		} else {
			return;
		}
	}

	@SuppressWarnings("unchecked")
	private <U extends Extractable> U doRetrieve(Class<U> type, URI uri) throws Exception {
		// System.out.println("FIND " + uri + " as " + type.getSimpleName().toLowerCase());
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
		} else if (Équipe.class.isAssignableFrom(type)) {
			return (U) équipeController.doFind(connection, uri);
		} else if (Journées.class.isAssignableFrom(type)) {
			return (U) journéesController.doFind(connection, uri);
		} else if (Rencontres.class.isAssignableFrom(type)) {
			return (U) rencontresController.doFind(connection, uri);
		} else if (Classements.class.isAssignableFrom(type)) {
			return (U) classementsController.doFind(connection, uri);
		} else {
			return null;
		}
	}
	
	public Ressources() {
		resources = new HashMap<String, Object>(1024);
		salleController = new SalleController();
		organisationController = new OrganisationController();
		organisationSalleController = new OrganisationSalleController();
		appartenancesController = new AppartenancesController();
		championnatController = new ChampionnatController();
		divisionController = new DivisionController();
		engagementsController = new EngagementsController();
		équipeController = new ÉquipeController();
		journéesController = new JournéesController();
		rencontresController = new RencontresController();
		classementsController = new ClassementsController();
		this.setExempt();
	}
	
	private void setExempt() {
		URI uri = URI.create("https://resultats.ffbb.com/organisation/0.html");
		Organisation exempt = this.doFind(Organisation.class, uri);
		if (exempt == null) {
			exempt = new Organisation(0L, "0");
			exempt.setNom("Exempt");
			exempt.setFfbb("");
			exempt.setType(Organisation.Type.Entente);
			this.doBind(Organisation.class, uri, exempt);
		} else if (connection == null) {
			
		} else {
			try { 
				if (this.doRetrieve(Organisation.class, uri) == null) {
					this.doInsert(Organisation.class, exempt); 
				}
			} catch (Exception e) { 
				e.printStackTrace(); 
			}
		}
	}
	
}
