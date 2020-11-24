package com.ffbb.resultats.api;

public class Appartenance {
	
	public enum Type {Comité, Ligue};
	
	private Organisation organisation;
	
	private Organisation structure;
	
	private Type type;

	public Organisation getOrganisation() {
		return organisation;
	}

	public Organisation getStructure() {
		return structure;
	}
	
	public Type getType() {
		return type;
	}

	public Appartenance(Organisation organisation, Organisation structure, Type type) {
		super();
		this.organisation = organisation;
		this.structure = structure;
		this.type = type;
	}
	
}
