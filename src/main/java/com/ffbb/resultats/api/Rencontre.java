package com.ffbb.resultats.api;

import java.util.Date;

public class Rencontre {

	private Integer journée;
	
	private Date horaire;
	
	private Équipe domicile;
	
	private Équipe visiteur;
	
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

	public Équipe getDomicile() {
		return domicile;
	}

	public Équipe getVisiteur() {
		return visiteur;
	}

	public Rencontre(Équipe domicile, Équipe visiteur, Integer journée, Date horaire) {
		this.domicile = domicile;
		this.visiteur = visiteur;
		this.journée = journée;
		this.horaire = horaire;
	}
	
	@Override
	public String toString() {
		return journée + "\t" + horaire.toString()
				+ "\t" + (domicile == null ? "Exempt" : domicile.getDénomination())
				+ "\t" + (résultat == null ? "-" : résultat.getDomicile() + "-" + résultat.getVisiteur())
				+ "\t" + (visiteur == null ? "Exempt" : visiteur.getDénomination());
	}
	
}
