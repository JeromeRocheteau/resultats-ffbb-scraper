package com.ffbb.resultats.api;

import java.util.LinkedList;
import java.util.List;

public class Journée {
	
	private Division division;
	
	private Integer numéro;
	
	private List<Rencontre> rencontres;

	public Division getDivision() {
		return division;
	}

	public Integer getNuméro() {
		return numéro;
	}
	
	public List<Rencontre> getRencontres() {
		return rencontres;
	}

	public Journée(Integer numéro, Division division) {
		this.numéro = numéro;
		this.division = division;
		this.rencontres = new LinkedList<Rencontre>();
	}
	
}
