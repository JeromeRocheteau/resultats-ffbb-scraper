package com.ffbb.resultats.api;

import java.net.URI;
import java.util.LinkedList;

public class Journées extends LinkedList<Journée> implements Extractable {

	private static final long serialVersionUID = 202011261000001L;

	private Division division;
	
	public Journées(Division division) {
		super();
		this.division = division;
	}
	
	public URI getURI() {
		return URI.create("https://resultats.ffbb.com/championnat/journees/" + division.getCode() + ".html");
	}

}
