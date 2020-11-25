package com.ffbb.resultats.api;

import java.util.Calendar;
import java.util.Date;

public class Classement implements Comparable<Classement> {

	private Équipe équipe;
	
	private Date date;
	
	private Integer rang;
	
	private Integer victoires;
	
	private Integer défaites;
	
	private Integer pour;
	
	private Integer contre;

	public Integer getRang() {
		return rang;
	}

	public void setRang(Integer rang) {
		this.rang = rang;
	}

	public Integer getVictoires() {
		return victoires;
	}

	public void setVictoires(Integer victoires) {
		this.victoires = victoires;
	}

	public Integer getDéfaites() {
		return défaites;
	}

	public void setDéfaites(Integer défaites) {
		this.défaites = défaites;
	}

	public Integer getPour() {
		return pour;
	}

	public void setPour(Integer pour) {
		this.pour = pour;
	}

	public Integer getContre() {
		return contre;
	}

	public void setContre(Integer contre) {
		this.contre = contre;
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

	public Classement(Équipe équipe, Integer rang, Integer victoires, Integer défaites, Integer pour, Integer contre) {
		this(équipe);
		this.rang = rang;
		this.victoires = victoires;
		this.défaites = défaites;
		this.pour = pour;
		this.contre = contre;
	}

	public int compareTo(Classement classement) {
		return rang.compareTo(classement.getRang());
	}
	
	@Override
	public String toString() {
		String rank = rang + (rang > 1 ? "ers" : "es");
		return "\t" + équipe.getNom() + "\t" + victoires + "v-" + défaites + "d (" + rank + ")"; 
	}
	
}
