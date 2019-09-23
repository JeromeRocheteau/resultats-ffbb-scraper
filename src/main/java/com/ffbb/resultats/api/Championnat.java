package com.ffbb.resultats.api;

import java.util.LinkedList;
import java.util.List;

public class Championnat extends Compétition {
	
	private Niveau niveau;

	private Integer phase;
	
	private Integer division;
	
	private String poule;
	
	private List<Équipe> équipes;
	
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

	public Integer getDivision() {
		return division;
	}

	public void setDivision(Integer division) {
		this.division = division;
	}

	public String getPoule() {
		return poule;
	}

	public void setPoule(String poule) {
		this.poule = poule;
	}

	public List<Équipe> getÉquipes() {
		return équipes;
	}

	public Championnat(Paramètres parameter, Genre genre, Catégorie catégorie, Niveau niveau, Integer phase, Integer division, String poule) {
		super(parameter, Compétition.Type.Championnat, genre, catégorie);
		this.niveau = niveau;
		this.phase = phase;
		this.division = division;
		this.poule = poule;
		this.équipes= new LinkedList<Équipe>();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(128);
		builder.append("Championnat : ");
		if (this.getCatégorie() == Catégorie.Senior) {
			builder.append(this.getNiveau().name());
			builder.append("e ");
			builder.append(this.getGenre().name().toLowerCase());
			builder.append("e ");
			builder.append("seniors");
		} else {
			builder.append(this.getNiveau().name());
			builder.append(" ");
			builder.append(this.getGenre().name().toLowerCase());
			builder.append(" ");
			builder.append(this.getCatégorie().name());
		}
		if (phase > 1) {
			builder.append(" - Phase ");
			builder.append(phase.toString());
		}
		if (poule != null) {
			builder.append(" - Poule ");
			builder.append(poule.toUpperCase());
		} else {
			if (division > 0) {
				if (this.getCatégorie() == Catégorie.Senior || this.getCatégorie() == Catégorie.U9) {
					builder.append(" - Division ");
				} else {
					builder.append(" - ");
					builder.append(this.getNiveau().toString());
				}
				builder.append(division);
				if (poule != null) {
					builder.append(" - Poule ");
					builder.append(poule.toUpperCase());					
				}
			} else if (division == 0) {
				builder.append(" - ");
				builder.append(poule);
				builder.append(" - Elite");
			}	
		}
		return builder.toString();
	}
	
}
