package com.ffbb.resultats.api;

import java.net.URI;
import java.util.List;

public class Organisation implements Identifiable, Encodable, Extractable, Comparable<Organisation> {
	
	public enum Type {Club, Entente, ClubPro, Comité, Ligue, Fédération};
	
	private Long id;
	
	private String code;
	
	private Type type;
	
	private String ffbb;
	
	private String nom;
	
	private Appartenances appartenances;
	
	private Engagements engagements;
	
	private Salle salle;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getCode() {
		return code;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setType(String type) {
		if (type.equals(Type.Club.name())) {
			this.type = Type.Club;
		} else if (type.equals(Type.ClubPro.name())) {
			this.type = Type.ClubPro;
		} else if (type.equals(Type.Entente.name())) {
			this.type = Type.Entente;
		} else if (type.equals(Type.Comité.name())) {
			this.type = Type.Comité;
		} else if (type.equals(Type.Ligue.name())) {
			this.type = Type.Ligue;
		} else if (type.equals(Type.Fédération.name())) {
			this.type = Type.Fédération;
		}
	}

	public String getFfbb() {
		return ffbb;
	}

	public void setFfbb(String ffbb) {
		this.ffbb = ffbb;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<Appartenance> getAppartenances() {
		return appartenances;
	}

	public List<Engagement> getEngagements() {
		return engagements;
	}

	public Salle getSalle() {
		return salle;
	}

	public void setSalle(Salle salle) {
		this.salle = salle;
	}

	public Organisation(Long id, String code) {
		super();
		this.id = id;
		this.code = code;
		this.appartenances = new Appartenances(this);
		this.engagements = new Engagements(this);
	}
	
	public Organisation(Long id, String code, String type, String ffbb, String nom) {
		this(id, code);
		this.setType(type);
		this.setFfbb(ffbb);
		this.setNom(nom);
	}

	@Override
	public String toString() {
		return nom + " - " + ffbb + " - " + type.name();
	}

	@Override
	public int compareTo(Organisation organisation) {
		return code.compareTo(organisation.getCode());
	}

	@Override
	public boolean equals(Object object) {
		try {
			Organisation organisation = (Organisation) object;
			return this.compareTo(organisation) == 0;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public URI getURI() {
		return URI.create("http://resultats.ffbb.com/organisation/" + code + ".html");
	}
	
}
