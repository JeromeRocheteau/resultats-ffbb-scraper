package com.ffbb.resultats.api;

import java.util.Date;

public class Rencontre implements Comparable<Rencontre> {
	
	private Journée journée;
	
	private Integer numéro;
	
	private Date horaire;
	
	private Équipe domicile;
	
	private Équipe visiteur;
	
	private Résultat résultat;
	
	private Salle salle;

	public Journée getJournée() {
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

	public Salle getSalle() {
		return salle;
	}

	public void setSalle(Salle salle) {
		this.salle = salle;
	}

	public Rencontre(Journée journée, Integer numéro, Équipe domicile, Équipe visiteur, Date horaire, Salle salle) {
		this.journée = journée;
		this.numéro = numéro;
		this.domicile = domicile;
		this.visiteur = visiteur;
		this.journée = journée;
		this.horaire = horaire;
		this.salle = salle;
	}
	
	@Override
	public String toString() {
		return numéro + "\t" + journée.getNuméro() + "\t" + horaire.toString()
				+ "\t" + (domicile == null ? "Exempt" : domicile.getNom())
				+ "\t" + (résultat == null ? "-" : résultat.getDomicile() + "-" + résultat.getVisiteur())
				+ "\t" + (visiteur == null ? "Exempt" : visiteur.getNom());
	}
	
	public String code() {
		return journée.getDivision().getCode() + "-" + numéro;
	}

	@Override
	public int compareTo(Rencontre rencontre) {
		return code().compareTo(rencontre.code());
	}

	@Override
	public boolean equals(Object object) {
		try {
			Rencontre rencontre = (Rencontre) object;
			return this.compareTo(rencontre) == 0;
		} catch (Exception e) {
			return false;
		}
	}
	
}
