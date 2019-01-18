package com.ffbb.resultats.api;

public class Coupe extends Compétition {

	public Coupe(Paramètres parameter, Genre genre, Catégorie catégorie) {
		super(parameter, Compétition.Type.Coupe, genre, catégorie);
	}

	@Override
	public String toString() {
		return "Coupe " + super.toString();
	}

}
