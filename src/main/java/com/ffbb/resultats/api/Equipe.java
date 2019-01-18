package com.ffbb.resultats.api;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import com.ffbb.resultats.Extractable;

public class Equipe implements Extractable {

	private Organisation organisation;
	
	private Compétition compétition;
	
	private List<Rencontre> rencontres;
	
	public Organisation getOrganisation() {
		return organisation;
	}

	public Compétition getCompétition() {
		return compétition;
	}

	public List<Rencontre> getRencontres() {
		return rencontres;
	}

	public void setRencontres(List<Rencontre> rencontres) {
		this.rencontres = rencontres;
	}

	public Equipe(Organisation organisation, Compétition compétition) {
		super();
		this.organisation = organisation;
		this.compétition = compétition;
		this.rencontres = new LinkedList<Rencontre>();
	}

	public URI getURI() {
		String link = "http://resultats.ffbb.com/championnat/equipe/" + organisation.getCode() + ".html" 
				+ "?r=" + compétition.getParamètres().getR() 
				+ "&p=" + compétition.getParamètres().getD()
				+ "&d=" + organisation.getId();
		return URI.create(link);
	}

}
