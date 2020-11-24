package com.ffbb.resultats.api;

public abstract class Compétition implements Identifiable, Encodable {
	
	public enum Type {Championnat, Championnat3x3, Coupe, Plateau};
	
	private Long id;
	
	private String code;
		
	private String nom;
	
	private Organisation organisateur;
		
	private Type type;
	
	private Genre genre;
	
	private Catégorie catégorie;
	
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

	public String getNom() {
		return nom;
	}

	public Organisation getOrganisateur() {
		return organisateur;
	}

	public void setOrganisateur(Organisation organisateur) {
		this.organisateur = organisateur;
	}

	public Type getType() {
		return type;
	}

	public Genre getGenre() {
		return genre;
	}

	public Catégorie getCatégorie() {
		return catégorie;
	}

	protected Compétition(Long id, String code, String nom, Organisation organisateur, Compétition.Type type, Genre genre, Catégorie catégorie) {
		super();
		this.setId(id);
		this.setCode(code);
		this.organisateur = organisateur;
		this.type = type;
		this.genre = genre;
		this.catégorie = catégorie;
		this.nom = nom;
	}

	@Override
	public String toString() {
		return nom;
	}
		
}
