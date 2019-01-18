package com.ffbb.resultats.api;

import java.net.URI;
import java.util.LinkedList;

import com.ffbb.resultats.Extractable;

public class Engagements extends LinkedList<Engagement> implements Extractable {

	private static final long serialVersionUID = 201901172240002L;

	private Organisation organisation;
	
	public Engagements(Organisation organisation) {
		super();
		this.organisation = organisation;
	}
	
	public URI getURI() {
		return URI.create("http://resultats.ffbb.com/organisation/engagements/" + organisation.getCode() + ".html");
	}

}
