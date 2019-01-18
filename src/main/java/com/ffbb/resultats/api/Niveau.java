package com.ffbb.resultats.api;

public enum Niveau {

	National, Régional, Départemental;
	
	@Override
	public String toString() {
		if (this == Niveau.Départemental) {
			return "D";
		} else if (this == Niveau.Régional) {
			return "R";
		} else if (this == Niveau.National) {
			return "N";
		} else {
			return "";
		}
	}
	
}
