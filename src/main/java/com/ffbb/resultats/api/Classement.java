package com.ffbb.resultats.api;

import java.util.Calendar;
import java.util.Date;

public class Classement implements Comparable<Classement> {
	
	private Équipe équipe;
	
	private Date date;
	
	private Integer rang;
	
	private Integer points;
	
	private Integer matchs;
	
	private Integer victoires;
	
	private Integer défaites;
	
	private Integer nuls;
	
	private Integer pour;
	
	private Integer contre;
	
	private Integer diff;

	public Integer getRang() {
		return rang;
	}

	public Integer getPoints() {
		return points;
	}

	public Integer getMatchs() {
		return matchs;
	}

	public Integer getVictoires() {
		return victoires;
	}

	public Integer getDéfaites() {
		return défaites;
	}

	public Integer getNuls() {
		return nuls;
	}

	public Integer getPour() {
		return pour;
	}

	public Integer getContre() {
		return contre;
	}

	public Integer getDiff() {
		return diff;
	}

	public Équipe getÉquipe() {
		return équipe;
	}

	public Date getDate() {
		return date;
	}

	public Classement(Équipe équipe) {
		super();
		this.équipe = équipe;
		this.date = Calendar.getInstance().getTime();
	}

	public Classement(Équipe équipe, Integer rang, Integer points, Integer matchs, Integer victoires, Integer défaites,	Integer nuls, Integer pour, Integer contre, Integer diff) {
		this(équipe);
		this.rang = rang;
		this.points = points;
		this.matchs = matchs;
		this.victoires = victoires;
		this.défaites = défaites;
		this.nuls = nuls;
		this.pour = pour;
		this.contre = contre;
		this.diff = diff;
	}

	public int compareTo(Classement classement) {
		return rang.compareTo(classement.getRang());
	}
	
	@Override
	public String toString() {
		return rang + ".\t" + équipe.getNom() + "\t" + points + "p | " + matchs + "m | " + victoires + "v | " + défaites + "d | " + nuls + "n ("+ pour + "p | "+ contre + "c |" + diff + ")"; 
	}
	
}
