package com.ffbb.resultats.api;

import java.net.URI;
import java.util.List;

import com.ffbb.resultats.Extractable;

public class Organisation implements Identifiable, Encodable, Extractable, Comparable<Organisation> {
	
	public enum Type {Club, Entente, ClubPro, Comité, Ligue, Fédération};
	
	private Long id;
	
	private String code;
	
	private Type type;
	
	private String ffbb;
	
	private String name;
	
	private Appartenances appartenances;
	
	private Engagements engagements;
	
	private Salle salle;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public void setCode(String code) {
		this.code = code;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getFfbb() {
		return ffbb;
	}

	public void setFfbb(String ffbb) {
		this.ffbb = ffbb;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Appartenances getAppartenances() {
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
		this.setId(id);
		this.code = code;
		this.appartenances = new Appartenances(this);
		this.engagements = new Engagements(this);
	}
	
	@Override
	public String toString() {
		return name + " - " + ffbb + " - " + type.name();
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
