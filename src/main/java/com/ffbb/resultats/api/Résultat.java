package com.ffbb.resultats.api;

public class Résultat {

	private Integer domicile;
	
	private Integer visiteur;

	public Integer getDomicile() {
		return domicile;
	}

	public Integer getVisiteur() {
		return visiteur;
	}

	public Résultat(Integer domicile, Integer visiteur) {
		super();
		this.domicile = domicile;
		this.visiteur = visiteur;
	}
	
}
