package com.ffbb.resultats.filtres;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.ffbb.resultats.api.Catégorie;
import com.ffbb.resultats.api.Championnat;
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
		phases = new LinkedList<Integer>();
		divisions = new LinkedList<Integer>();
		poules = new LinkedList<String>();
	}
	
	public boolean match(Compétition compétition) {
		return super.match(compétition) 
				&& (compétition instanceof Championnat ? this.match((Championnat) compétition) : false); 
	}

	private boolean match(Championnat championnat) {
		return this.match(championnat.getNiveau()) 
				&& this.phase(championnat.getPhase())
				&& this.division(championnat.getDivision())
				&& this.poule(championnat.getPoule());
	}
	
	private boolean match(Niveau niveau) {
		if (niveaux.isEmpty()) {
			return true;
		} else {
			for (Niveau filter : niveaux) {
				if (filter == niveau) {
					return true;
				}
			}
			return false;
		}
	}
	
	private boolean phase(Integer phase) {
		if (phases.isEmpty()) {
			return true;
		} else {
			for (Integer filter : phases) {
				if (filter == phase) {
					return true;
				}
			}
			return false;
		}
	}
	
	private boolean division(Integer division) {
		if (divisions.isEmpty()) {
			return true;
		} else {
			for (Integer filter : divisions) {
				if (filter == division) {
					return true;
				}
			}
			return false;
		}
	}
	
	private boolean poule(String poule) {
		if (poules.isEmpty()) {
			return true;
		} else {
			for (String filter : poules) {
				if (filter == poule) {
					return true;
				}
			}
			return false;
		}
	}
	
}
