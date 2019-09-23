package com.ffbb.resultats.api;

public class Plateau extends Compétition {

	private Integer numero;
	
	public Plateau(Paramètres parameter, Genre genre, Catégorie catégorie, Integer numero) {
		super(parameter, Compétition.Type.Plateau, genre, catégorie);
		this.numero = numero;
	}

	@Override
	public String toString() {
		return "Plateau n°" + numero + super.toString();
	}

}
