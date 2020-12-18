package com.ffbb.resultats.api;

public class Paramètres {

	private String code;
	
	private Long r;

	private Long d;
	
	public String getCode() {
		return code;
	}

	public Long getR() {
		return r;
	}
	
	public Long getD() {
		return d;
	}

	public Paramètres(String code, Long r, Long d) {
		super();
		this.code = code;
		this.r = r;
		this.d = d;
	}
	
	@Override
	public String toString() {
		return this.code + ".html?r=" + this.r + "&d=" + this.d; 
	}
	
}
