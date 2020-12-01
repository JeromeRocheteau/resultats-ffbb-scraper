package com.ffbb.resultats.api;

import java.util.Date;

public class Dates {

	private Date début;
	
	private Date fin;

	public Date getDébut() {
		return début;
	}

	public Date getFin() {
		return fin;
	}

	public Dates(Date début, Date fin) {
		super();
		this.début = début;
		this.fin = fin;
	}
	
}
