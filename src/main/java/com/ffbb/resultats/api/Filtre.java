package com.ffbb.resultats.filtres;

import com.ffbb.resultats.api.Compétition;

public interface Filtre {

	public boolean match(Compétition compétition);
	
}
