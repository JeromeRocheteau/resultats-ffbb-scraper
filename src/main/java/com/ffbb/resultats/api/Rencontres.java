package com.ffbb.resultats.api;

import java.net.URI;
import java.util.LinkedList;

public class Rencontres extends LinkedList<Rencontre> implements Extractable {

	private static final long serialVersionUID = 202011261130002L;

	private Journée journée;
	
	public Rencontres(Journée journée) {
		super();
		this.journée = journée;
	}
	
	@Override
	public URI getURI() {
		String code = journée.getDivision().getCode() + Integer.toHexString(journée.getNuméro());
		return URI.create("https://resultats.ffbb.com/championnat/rencontres/" + code + ".html");
	}

}
