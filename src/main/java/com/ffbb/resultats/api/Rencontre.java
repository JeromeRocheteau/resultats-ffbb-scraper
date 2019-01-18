package com.ffbb.resultats.api;

import java.util.Date;

public class Rencontre {

	private Integer journée;
	
	private Date horaire;
	
	private Equipe domicile;
	
	private Equipe visiteur;
	
	private Résultat résultat;

	public Integer getJournée() {
		return journée;
	}

	public Date getHoraire() {
		return horaire;
	}

	public Résultat getRésultat() {
		return résultat;
	}

	public void setRésultat(Résultat résultat) {
		this.résultat = résultat;
	}

	public Equipe getDomicile() {
		return domicile;
	}

	public Equipe getVisiteur() {
		return visiteur;
	}

	public Rencontre(Equipe domicile, Equipe visiteur, Integer journée, Date horaire) {
		this.domicile = domicile;
		this.visiteur = visiteur;
		this.journée = journée;
		this.horaire = horaire;
	}
	
}
