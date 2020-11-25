package com.ffbb.resultats;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.ffbb.resultats.api.Extractable;
import com.ffbb.resultats.api.Organisation;

public class Extract {

	private static Extract instance;
	
	public static Extract getInstance() {
		if (instance == null) {
			instance = new Extract();
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
	
	private Extract() {
		resources = new HashMap<String, Extractable>(1024);
		URI uri = URI.create("http://resultats.ffbb.com/organisation/0.html");
		Organisation exempt = new Organisation(0L, "0");
		exempt.setNom("Exempt");
		exempt.setType(Organisation.Type.Entente);
		this.doBind(Organisation.class, uri, exempt);
	}

	
}
