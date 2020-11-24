package com.ffbb.resultats.api;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Rencontre implements Identifiable, Comparable<Rencontre> {

	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	
	private Long id; 
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	private Compétition compétition;
	
	private Integer journée;
	
	private Date horaire;
	
	private Équipe domicile;
	
	private Équipe visiteur;
	
	private Résultat résultat;
	
	private Salle salle;

	public Compétition getCompétition() {
		return compétition;
	}

	public void setCompétition(Compétition compétition) {
		this.compétition = compétition;
	}

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

	public Salle getSalle() {
		return salle;
	}

	public void setSalle(Salle salle) {
		this.salle = salle;
	}

	public Rencontre(Compétition compétition, Équipe domicile, Équipe visiteur, Integer journée, Date horaire, Salle salle) {
		this.compétition = compétition;
		this.domicile = domicile;
		this.visiteur = visiteur;
		this.journée = journée;
		this.horaire = horaire;
		this.salle = salle;
	}
	
	@Override
	public String toString() {
		return journée + "\t" + horaire.toString()
				+ "\t" + (domicile == null ? "Exempt" : domicile.getNom())
				+ "\t" + (résultat == null ? "-" : résultat.getDomicile() + "-" + résultat.getVisiteur())
				+ "\t" + (visiteur == null ? "Exempt" : visiteur.getNom());
	}
	
	public String code() {
		String dom = domicile.getOrganisation().getCode();
		String vis = visiteur.getOrganisation().getCode();
		Integer day = journée;
		String date = dateFormatter.format(horaire);
		return dom + "-" + vis + "-" + day + "-" + date;
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
