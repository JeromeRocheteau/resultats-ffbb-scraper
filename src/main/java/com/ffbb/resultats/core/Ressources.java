package com.ffbb.resultats.core;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.ffbb.resultats.api.Extractable;
import com.ffbb.resultats.api.Organisation;

public class Ressources {
	
	private static Ressources instance;
	
	public static Ressources getInstance() {
		if (instance == null) {
			instance = new Ressources();
		}
		return instance;
	}
	
	protected Map<String, Extractable> resources;
	
	@SuppressWarnings("unchecked")
	public <U extends Extractable> U doFind(Class<U> type, URI uri) throws Exception {
		return (U) resources.get(uri.toString());
	}
	
	public <U extends Extractable> void doBind(Class<U> type, URI uri, U resource) {
		resources.put(uri.toString(), resource);
	}
	
	public Ressources() {
		resources = new HashMap<String, Extractable>(1024);
		this.setExempt();
	}
	
	private void setExempt() {
		URI uri = URI.create("http://resultats.ffbb.com/organisation/0.html");
		Organisation exempt = new Organisation(0L, "0");
		exempt.setNom("Exempt");
		exempt.setType(Organisation.Type.Entente);
		this.doBind(Organisation.class, uri, exempt);
	}
	
}
