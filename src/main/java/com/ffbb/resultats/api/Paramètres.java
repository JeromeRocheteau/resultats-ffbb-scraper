package com.ffbb.resultats.api;

public class Paramètres {

	private Long id;

	private String code;
	
	private Long division;
	
	public Long getId() {
		return id;
	}
	
	public String getCode() {
		return code;
	}

	public Long getDivision() {
		return division;
	}

	public Paramètres(Long id, String code, Long division) {
		super();
		this.id = id;
		this.code = code;
		this.division = division;
	}
	
}
