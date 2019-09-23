package com.ffbb.resultats.api;

public enum Niveau {

	National, PréNational, Régional, PréRégional, Départemental;

	@Override
	public String toString() {
		if (this == Niveau.Départemental) {
			return "D";
		} if (this == Niveau.PréRégional) {
			return "D";
		} else if (this == Niveau.Régional) {
			return "R";
		} else if (this == Niveau.PréNational) {
			return "R";
		} else if (this == Niveau.National) {
			return "N";
		} else {
			return "";
		}
	}
	
}
