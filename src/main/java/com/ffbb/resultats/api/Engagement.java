package com.ffbb.resultats.api;

public class Engagement {
	
	private Organisation organisation;
	
	private Compétition compétition;

	public Organisation getOrganisation() {
		return organisation;
	}

	public Compétition getCompétition() {
		return compétition;
	}
	
	public Engagement(Organisation organisation, Compétition compétition) {
		super();
		this.organisation = organisation;
		this.compétition = compétition;
	}
	
}
