package com.ffbb.resultats.api;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

public class Championnat extends Compétition implements Extractable {
	
	private Niveau niveau;
	
	private Catégorie catégorie;
	
	private Genre genre;
	
	private Integer phase;
	
	private List<Division> divisions;

	public Niveau getNiveau() {
		return niveau;
	}

	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}

	public Catégorie getCatégorie() {
		return catégorie;
	}

	public void setCatégorie(Catégorie catégorie) {
		this.catégorie = catégorie;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public Integer getPhase() {
		return phase;
	}

	public void setPhase(Integer phase) {
		this.phase = phase;
	}

	public List<Division> getDivisions() {
		return divisions;
	}

	public Championnat(Long id, String code, String nom, Organisation organisateur) {
		super();
		this.setId(id);
		this.setCode(code);
		this.setNom(nom);
		this.setOrganisateur(organisateur);
		this.setType(Type.Championnat);
		this.divisions = new LinkedList<Division>();
	}

	@Override
	public URI getURI() {
		String link = "http://resultats.ffbb.com/championnat/" + this.getCode() + ".html";
		return URI.create(link);
	}
	
}
