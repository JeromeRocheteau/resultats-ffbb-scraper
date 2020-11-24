package com.ffbb.resultats.api;

import java.net.URI;
import java.util.LinkedList;

import com.ffbb.resultats.Extractable;

public class Appartenances extends LinkedList<Appartenance> implements Extractable {

	private static final long serialVersionUID = 202011231500001L;

	private Organisation organisation;
	
	public Appartenances(Organisation organisation) {
		super();
		this.organisation = organisation;
	}
	
	public URI getURI() {
		return URI.create("http://resultats.ffbb.com/organisation/appartenance/" + organisation.getCode() + ".html");
	}

}
