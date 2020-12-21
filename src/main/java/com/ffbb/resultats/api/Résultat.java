package com.ffbb.resultats.api;

public class Résultat {

	private Rencontre rencontre;

	private Integer domicile;
	
	private Integer visiteur;

	public Rencontre getRencontre() {
		return rencontre;
	}

	public Integer getDomicile() {
		return domicile;
	}

	public Integer getVisiteur() {
		return visiteur;
	}

	public Résultat(Rencontre rencontre, Integer domicile, Integer visiteur) {
		super();
		this.rencontre = rencontre;
		this.domicile = domicile;
		this.visiteur = visiteur;
	}
	
}
