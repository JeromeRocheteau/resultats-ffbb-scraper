package com.ffbb.resultats.api;

import java.net.URI;
import java.util.Collections;
import java.util.LinkedList;

public class Classements extends LinkedList<Classement> implements Extractable {

	private static final long serialVersionUID = 202011270900001L;

	private Division division;
	
	public Classements(Division division) {
		super();
		this.division = division;
	}
	
	public void doSort() {
		Collections.sort(this);
	}
	
	@Override
	public String toString() {
		this.doSort();
		StringBuilder builder = new StringBuilder();
		for (Classement classement : this) {
			builder.append(classement.toString());
			builder.append('\n');
		}
		return builder.toString();
	}
	
	public URI getURI() {
		String link = "https://resultats.ffbb.com/championnat/classements/" + division.getCode() + ".html";
		return URI.create(link);
	}

}
