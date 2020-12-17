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

	public Appartenance(Organisation organisation, Organisation structure, String type) {
		this(organisation, structure);
		this.setType(type);
	}

	private void setType(String type) {
		if (type.equals(Type.Comité.name())) {
			this.type = Type.Comité;
		} else if (type.equals(Type.Ligue.name())) {
			this.type = Type.Ligue;
		}
	}
	
}
