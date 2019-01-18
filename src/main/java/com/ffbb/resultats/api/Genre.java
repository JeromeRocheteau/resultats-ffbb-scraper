package com.ffbb.resultats.api;

public enum Genre {

	Masculin, Féminin;
	
	@Override
	public String toString() {
		if (this == Genre.Masculin) {
			return "M";
		} else if (this == Genre.Féminin) {
			return "F";
		} else {
			return "";
		}
	}
	
}
