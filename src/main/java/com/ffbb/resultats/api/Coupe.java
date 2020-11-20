package com.ffbb.resultats.api;

public class Coupe extends Compétition {

	public Coupe(Paramètres parameter, Organisation organisation, Genre genre, Catégorie catégorie) {
		super(parameter, organisation, Compétition.Type.Coupe, genre, catégorie);
	}

	@Override
	public String toString() {
		return "Coupe " + super.toString();
	}

}
