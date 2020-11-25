package com.ffbb.resultats.api;

import java.util.LinkedList;
import java.util.List;

public class Journée {

	private Division division;
	
	private Integer journée;
	
	private List<Rencontre> rencontres;

	public Division getDivision() {
		return division;
	}

	public Integer getJournée() {
		return journée;
	}
	
	public List<Rencontre> getRencontres() {
		return rencontres;
	}

	public Journée(Integer journée, Division division) {
		this.journée = journée;
		this.division = division;
		this.rencontres = new LinkedList<Rencontre>();
	}
	
}
