package com.ffbb.resultats.api;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

public class Division implements Identifiable, Encodable, Extractable, Comparable<Division> {
	
	private Long id;
	
	private String code;
		
	private String nom;
	
	private String num;
	
	private Championnat championnat;
	
	private List<Équipe> équipes;
	
	private List<Journée> journées;
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getCode() {
		return code;
	}

	public String getNom() {
		return nom;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public Championnat getChampionnat() {
		return championnat;
	}
	
	public List<Équipe> getÉquipes() {
		return équipes;
	}
	
	public List<Journée> getJournées() {
		return journées;
	}

	public Division(Long id, String code, String nom, Championnat championnat) {
		super();
		this.id = id;
		this.code = code;
		this.nom = nom;
		this.championnat = championnat;
		this.équipes = new LinkedList<Équipe>();
		this.journées = new LinkedList<Journée>();
	}

	@Override
	public URI getURI() {
		String link = "http://resultats.ffbb.com/championnat/" + this.getChampionnat().getCode() + ".html"
				+ "?r=" + this.getChampionnat().getId()
				+ "&d=" + this.getId();
		return URI.create(link);
	}

	public URI getAlternateURI() {
		String link = "http://resultats.ffbb.com/championnat/division/" + this.getCode() + ".html";
		return URI.create(link);
	}

	@Override
	public String toString() {
		return championnat.toString() + " | " + nom;
	}

	@Override
	public int compareTo(Division division) {
		return code.compareTo(division.getCode());
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Division) {
			Division division = (Division) object;
			return this.compareTo(division) == 0;
		} else {
			return false;
		}
	}
	
}
