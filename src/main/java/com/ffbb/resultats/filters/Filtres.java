package com.ffbb.resultats.filtres;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.ffbb.resultats.api.Compétition;

public class MultipleFiltres implements Filtre {

	private List<Filtre> filtres;
	
	public MultipleFiltres filtres(Filtre... filtres) {
		this.filtres.clear();
		this.filtres.addAll(Arrays.asList(filtres));
		return this;
	}
	
	public MultipleFiltres() {
		filtres = new LinkedList<Filtre>();
	}
	
	public boolean match(Compétition compétition) {
		for (Filtre filtre : filtres) {
			if (filtre.match(compétition)) {
				return true;
			}
		}
		return false;
	}

}
