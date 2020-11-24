package com.ffbb.resultats.api;

public class Résultat implements Identifiable {
	
	private Long id; 
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

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

	@Override
	public String toString() {
		return this.getId() + " : " + domicile + "-" + visiteur;
	}
	
}
