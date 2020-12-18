package com.ffbb.resultats.api;

public class Engagement implements Encodable {

	private static final String SEP = "-";
	
	private String code; 
	
	@Override
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
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
		this.setCode(division.getCode() + SEP + organisation.getCode());
	}
	
	@Override
	public String toString() {
		return division.getChampionnat().getNom() + " " + division.getNom() + " | " + organisation.getNom(); 
	}
	
}
