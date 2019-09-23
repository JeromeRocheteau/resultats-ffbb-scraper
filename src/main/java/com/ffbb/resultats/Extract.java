package com.ffbb.resultats;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.ffbb.resultats.api.Organisation;

public class Extract {

	protected Map<String, Extractable> resources;
	
	@SuppressWarnings("unchecked")
	public <U extends Extractable> U doFind(Class<U> type, URI uri) throws Exception {
		return (U) resources.get(uri.toString());
	}
	
	public <U extends Extractable> void doBind(Class<U> type, URI uri, U resource) {
		resources.put(uri.toString(), resource);
	}
	
	public Extract() {
		resources = new HashMap<String, Extractable>(1024);
		URI uri = URI.create("http://resultats.ffbb.com/organisation/fffffffffffffffb.html");
		Organisation exempt = new Organisation("fffffffffffffffb");
		exempt.setName("Exempt");
		exempt.setType(Organisation.Type.Entente);
		this.doBind(Organisation.class, uri, exempt);
	}

	
}
