package com.ffbb.resultats.api;

public class Paramètres {

	private String id;

	private Long r;
	
	private Long d;
	
	public String getId() {
		return id;
	}
	
	public Long getR() {
		return r;
	}

	public Long getD() {
		return d;
	}

	public Paramètres(String id, Long r, Long d) {
		super();
		this.id = id;
		this.r = r;
		this.d = d;
	}
	
}
