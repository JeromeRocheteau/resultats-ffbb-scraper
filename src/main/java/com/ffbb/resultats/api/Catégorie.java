package com.ffbb.resultats.api;

public enum Catégorie {

	U9, U11, U13, U15, U17, U18, U20, Senior;
	
	@Override
	public String toString() {
		if (this == Catégorie.Senior) {
			return "S";
		} else {
			return this.name();
		}
	}
	
}
