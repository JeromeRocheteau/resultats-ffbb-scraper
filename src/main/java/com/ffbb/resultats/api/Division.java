package com.ffbb.resultats.api;

import java.net.URI;

public class Division implements Identifiable, Encodable, Extractable {
	
	private Long id;
	
	private String code;
		
	private String nom;
	
	private Championnat championnat;
	
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

	public Championnat getChampionnat() {
		return championnat;
	}

	public Division(Long id, String code, String nom, Championnat championnat) {
		super();
		this.id = id;
		this.code = code;
		this.nom = nom;
		this.championnat = championnat;
	}

	@Override
	public URI getURI() {
		String link = "http://resultats.ffbb.com/championnat/" + this.getChampionnat().getCode() + ".html"
				+ "?r=" + this.getChampionnat().getId()
				+ "&d=" + this.getId();
		return URI.create(link);
	}
	
}
