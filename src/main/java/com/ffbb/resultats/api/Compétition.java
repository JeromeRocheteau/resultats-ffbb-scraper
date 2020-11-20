package com.ffbb.resultats.api;

import java.net.URI;

import com.ffbb.resultats.Extractable;

public abstract class Compétition extends Identifier implements Extractable {
	
	public enum Type {Championnat, Championnat3x3, Coupe, Plateau};
	
	private Paramètres paramètres;
	
	private Organisation organisateur;
	
	private Type type;
	
	private Genre genre;
	
	private Catégorie catégorie;

	public Paramètres getParamètres() {
		return paramètres;
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

	protected Compétition(Paramètres paramètres, Organisation organisateur, Compétition.Type type, Genre genre, Catégorie catégorie) {
		super();
		this.paramètres = paramètres;
		this.organisateur = organisateur;
		this.type = type;
		this.genre = genre;
		this.catégorie = catégorie;
	}

	@Override
	public String toString() {
		return catégorie.toString() + this.getGenre().toString();
	}

	public URI getURI() {
		String link = "http://resultats.ffbb.com/championnat/" + paramètres.getId() + ".html" 
				+ "?r=" + paramètres.getR() 
				+ "&d=" + paramètres.getD();
		return URI.create(link);
	}
	
}
