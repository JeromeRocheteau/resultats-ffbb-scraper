package com.ffbb.resultats.api;

public abstract class Compétition implements Identifiable, Encodable {
	
	public enum Type {Championnat, Championnat3x3, Coupe, Plateau};
	
	private Long id;
	
	private String code;
		
	private String nom;
	
	private Organisation organisateur;
		
	private Type type;
	
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

	private void setCode(String code) {
		this.code = code;
	}

	public String getNom() {
		return nom;
	}

	private void setNom(String nom) {
		this.nom = nom;
	}

	public Organisation getOrganisateur() {
		return organisateur;
	}

	private void setOrganisateur(Organisation organisateur) {
		this.organisateur = organisateur;
	}

	public Type getType() {
		return type;
	}

	protected void setType(Type type) {
		this.type = type;
	}
	
	protected Compétition(Long id, String code, String nom, Organisation organisateur) {
		this.setId(id);
		this.setCode(code);
		this.setNom(nom);
		this.setOrganisateur(organisateur);
	}

	@Override
	public String toString() {
		return nom;
	}
		
}
