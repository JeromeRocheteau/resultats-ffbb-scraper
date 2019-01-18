package com.ffbb.resultats.api;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import com.ffbb.resultats.Extractable;

public class Équipe implements Extractable {

	private Organisation organisation;
	
	private Compétition compétition;
	
	private String dénomination;
	
	private List<Rencontre> rencontres;
	
	private Classement classement;
	
	public Organisation getOrganisation() {
		return organisation;
	}

	public Compétition getCompétition() {
		return compétition;
	}

	public String getDénomination() {
		return dénomination;
	}

	public void setDénomination(String dénomination) {
		this.dénomination = dénomination;
	}

	public Classement getClassement() {
		return classement;
	}

	public void setClassement(Classement classement) {
		this.classement = classement;
	}

	public List<Rencontre> getRencontres() {
		return rencontres;
	}

	public void setRencontres(List<Rencontre> rencontres) {
		this.rencontres = rencontres;
	}

	public Équipe(Organisation organisation, Compétition compétition) {
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
