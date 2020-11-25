package com.ffbb.resultats.api;

public class Engagement implements Identifiable {
	
	private Long id; 
	
	@Override
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
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
