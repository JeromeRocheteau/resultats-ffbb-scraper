package com.ffbb.resultats.api;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

public class Équipe extends Engagement implements Extractable {

	private String nom;
	
	private List<Rencontre> rencontres;
		
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<Rencontre> getRencontres() {
		return rencontres;
	}

	public void setRencontres(List<Rencontre> rencontres) {
		this.rencontres = rencontres;
	}

	public Équipe(Organisation organisation, Division division) {
		super(organisation, division);
		this.rencontres = new LinkedList<Rencontre>();
	}

	public URI getURI() {
		String link = "http://resultats.ffbb.com/championnat/equipe/" + this.getOrganisation().getCode() + ".html" 
				+ "?r=" + this.getDivision().getId() 
				+ "&p=" + this.getDivision().getId()
				+ "&d=" + this.getOrganisation().getId();
		return URI.create(link);
	}

}
