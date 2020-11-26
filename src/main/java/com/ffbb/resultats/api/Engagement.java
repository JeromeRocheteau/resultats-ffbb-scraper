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
	
	private Division division;

	public Organisation getOrganisation() {
		return organisation;
	}

	public Division getDivision() {
		return division;
	}
	
	public Engagement(Organisation organisation, Division division) {
		super();
		this.organisation = organisation;
		this.division = division;
	}
	
}
