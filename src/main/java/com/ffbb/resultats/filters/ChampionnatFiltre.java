package com.ffbb.resultats.filtres;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.ffbb.resultats.api.Catégorie;
import com.ffbb.resultats.api.Compétition.Type;
import com.ffbb.resultats.api.Genre;

public abstract class CompétitionFiltre implements Filtre {

	protected List<Type> types;
	
	protected List<Catégorie> catégories;
	
	protected List<Genre> genres;
	
	public CompétitionFiltre types(Type... types) {
		this.types.clear();
		this.types.addAll(Arrays.asList(types));
		return this;
	}

	public CompétitionFiltre catégories(Catégorie... catégories) {
		this.catégories.clear();
		this.catégories.addAll(Arrays.asList(catégories));
		return this;
	}

	public CompétitionFiltre genres(Genre... genres) {
		this.genres.clear();
		this.genres.addAll(Arrays.asList(genres));
		return this;
	}

	public CompétitionFiltre() {
		types = new LinkedList<Type>();
		catégories = new LinkedList<Catégorie>();
		genres = new LinkedList<Genre>();
	}
	
	/*
	 * public boolean match(Compétition compétition) { return
	 * this.match(compétition.getType()) && this.match(compétition.getCatégorie())
	 * && this.match(compétition.getGenre()); }
	 * 
	 * private boolean match(Type type) { if (types.isEmpty()) { return true; } else
	 * { for (Type filter : types) { if (filter == type) { return true; } } return
	 * false; } }
	 * 
	 * private boolean match(Catégorie catégorie) { if (catégories.isEmpty()) {
	 * return true; } else { for (Catégorie filter : catégories) { if (filter ==
	 * catégorie) { return true; } } return false; } }
	 * 
	 * private boolean match(Genre genre) { if (genres.isEmpty()) { return true; }
	 * else { for (Genre filter : genres) { if (filter == genre) { return true; } }
	 * return false; } }
	 */
	
}
