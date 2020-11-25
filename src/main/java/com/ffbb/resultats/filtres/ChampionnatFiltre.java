package com.ffbb.resultats.filtres;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.ffbb.resultats.api.Catégorie;
import com.ffbb.resultats.api.Compétition;
import com.ffbb.resultats.api.Genre;
import com.ffbb.resultats.api.Compétition.Type;
import com.ffbb.resultats.api.Niveau;

public class ChampionnatFiltre extends CompétitionFiltre implements Filtre {

	protected List<Niveau> niveaux;
	
	protected List<Integer> phases;
	
	protected List<Integer> divisions;
	
	protected List<String> poules;
	
	@Override
	public ChampionnatFiltre types(Type... types) { 
		this.types.clear();
		this.types.add(Type.Championnat);
		return this;
	}

	@Override
	public ChampionnatFiltre catégories(Catégorie... catégories) {
		return (ChampionnatFiltre) super.catégories(catégories);
	}

	@Override
	public ChampionnatFiltre genres(Genre... genres) {
		return (ChampionnatFiltre) super.genres(genres);
	}
	
	public ChampionnatFiltre niveaux(Niveau... niveaux) {
		this.niveaux.clear();
		this.niveaux.addAll(Arrays.asList(niveaux));
		return this;
	}
	
	public ChampionnatFiltre phases(Integer... phases) {
		this.phases.clear();
		this.phases.addAll(Arrays.asList(phases));
		return this;
	}
	
	public ChampionnatFiltre divisions(Integer... divisions) {
		this.divisions.clear();
		this.divisions.addAll(Arrays.asList(divisions));
		return this;
	}
	
	public ChampionnatFiltre poules(String... poules) {
		this.poules.clear();
		this.poules.addAll(Arrays.asList(poules));
		return this;
	}

	public ChampionnatFiltre() {
		super();
		niveaux = new LinkedList<Niveau>();
		divisions = new LinkedList<Integer>();
		poules = new LinkedList<String>();
	}

	@Override
	public boolean match(Compétition compétition) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
}
