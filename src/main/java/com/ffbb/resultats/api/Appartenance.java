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

	private Appartenance(Organisation organisation, Organisation structure) {
		super();
		this.organisation = organisation;
		this.structure = structure;
	}

	public Appartenance(Organisation organisation, Organisation structure, Type type) {
		this(organisation, structure);
		this.type = type;
	}
	
}
