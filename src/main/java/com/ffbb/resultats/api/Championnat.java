package com.ffbb.resultats.api;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import com.ffbb.resultats.Extractable;

public class Championnat extends Compétition implements Extractable {
	
	private Long index;
	
	private Niveau niveau;

	private Integer phase;
	
	private String poule;
	
	private List<Équipe> équipes;
	
	public Long getIndex() {
		return index;
	}

	public Niveau getNiveau() {
		return niveau;
	}

	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}

	public Integer getPhase() {
		return phase;
	}

	public void setPhase(Integer phase) {
		this.phase = phase;
	}

	public String getPoule() {
		return poule;
	}

	public List<Équipe> getÉquipes() {
		return équipes;
	}

	public Championnat(Long id, String code, String nom, Organisation organisateur, Compétition.Type type, Genre genre, Catégorie catégorie, Long index, Niveau niveau, Integer phase, String poule) {
		super(id, code, nom, organisateur, type, genre, catégorie);
		this.index = index;
		this.niveau = niveau;
		this.phase = phase;
		this.poule = poule;
		this.équipes= new LinkedList<Équipe>();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(128);
		builder.append(super.toString());
		builder.append(" | ");
		builder.append(poule);
		return builder.toString();
	}

	@Override
	public URI getURI() {
		String link = "http://resultats.ffbb.com/championnat/" + super.getCode() + ".html" 
				+ "?r=" + super.getId()
				+ "&d=" + index;
		return URI.create(link);
	}

	
}
