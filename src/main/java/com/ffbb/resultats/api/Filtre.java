package com.ffbb.resultats.api;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.ffbb.resultats.api.Compétition.Type;

public class Filtre {

	private List<Type> types;

	private List<Niveau> niveaux;
	
	private List<Catégorie> catégories;
	
	private List<Genre> genres;

	private List<Integer> phases;
	
	private List<Integer> divisions;
	
	private List<String> poules;
	
	private List<Integer> journées;
	
	private List<Dates> dates;
	
	public Filtre clear() {
		this.types.clear();
		this.catégories.clear();
		this.genres.clear();
		this.niveaux.clear();
		this.phases.clear();
		this.divisions.clear();
		this.poules.clear();
		this.journées.clear();
		this.dates.clear();
		return this;
	}
	
	public Filtre types(Type... types) {
		this.types.addAll(Arrays.asList(types));
		return this;
	}

	public Filtre catégories(Catégorie... catégories) {
		this.catégories.addAll(Arrays.asList(catégories));
		return this;
	}

	public Filtre genres(Genre... genres) {
		this.genres.addAll(Arrays.asList(genres));
		return this;
	}

	public Filtre niveaux(Niveau... niveaux) {
		this.niveaux.addAll(Arrays.asList(niveaux));
		return this;
	}

	public Filtre phases(Integer... phases) {
		this.phases.addAll(Arrays.asList(phases));
		return this;
	}

	public Filtre divisions(Integer... divisions) {
		this.divisions.addAll(Arrays.asList(divisions));
		return this;
	}
	
	public Filtre poules(String... poules) {
		this.poules.addAll(Arrays.asList(poules));
		return this;
	}

	public Filtre journées(Integer... journées) {
		this.journées.addAll(Arrays.asList(journées));
		return this;
	}

	public Filtre dates(Date début, Date fin) {
		this.dates.add(new Dates(début, fin));
		return this;
	}
	
	public Filtre() {
		types = new LinkedList<Type>();
		catégories = new LinkedList<Catégorie>();
		genres = new LinkedList<Genre>();
		niveaux = new LinkedList<Niveau>();
		phases = new LinkedList<Integer>();
		divisions = new LinkedList<Integer>();
		poules = new LinkedList<String>();
		journées = new LinkedList<Integer>();
		dates = new LinkedList<Dates>();
	}

	public boolean match(Division division) {
		Championnat championnat = division.getChampionnat();
		return this.matchType(championnat.getType()) 
				&& this.matchCatégorie(championnat.getCatégorie())
				&& this.matchGenre(championnat.getGenre())
				&& this.matchNiveau(championnat.getNiveau())
				&& this.matchPhase(championnat.getPhase())
				&& this.matchNum(championnat.getNum())
				&& this.matchPoule(division.getNom(), division.getNum());
	}

	public boolean match(Journée journée) {
		if (journées.isEmpty()) {
			return true;
		} else {
			for (Integer filter : journées) {
				if (filter == journée.getNuméro()) {
					return true;
				}
			}
			return false;
		}	
	}

	public boolean match(Rencontre rencontre) {
		return this.match(rencontre.getJournée()) 
				&& this.matchPériode(rencontre.getHoraire());
	}

	private boolean matchPériode(Date date) {
		if (dates.isEmpty()) {
			return true;
		} else {
			for (Dates filter : dates) {
				if (filter.getDébut().before(date)) {
					if (filter.getFin().after(date)) {
						return true;
					}
				}
			}
			return false;
		}
	}

	private boolean matchType(Type type) {
		if (types.isEmpty()) {
			return true;
		} else {
			for (Type filter : types) {
				if (filter == type) {
					return true;
				}
			}
			return false;
		}
	}

	private boolean matchCatégorie(Catégorie catégorie) {
		if (catégories.isEmpty()) {
			return true;
		} else {
			for (Catégorie filter : catégories) {
				if (filter == catégorie) {
					return true;
				}
			}
			return false;
		}
	}

	private boolean matchGenre(Genre genre) {
		if (genres.isEmpty()) {
			return true;
		} else {
			for (Genre filter : genres) {
				if (filter == genre) {
					return true;
				}
			}
			return false;
		}
	}

	private boolean matchNiveau(Niveau niveau) {
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

	private boolean matchPhase(Integer phase) {
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

	private boolean matchNum(Integer division) {
		if (divisions.isEmpty()) {
			return true;
		} else if (division == null) {
			return false;
		} else {
			for (Integer filter : divisions) {
				if (filter == division) {
					return true;
				}
			}
			return false;
		}
	}
	
	private boolean matchPoule(String poule, String num) {
		if (poules.isEmpty()) {
			return true;
		} else {
			for (String filter : poules) {
				if (poule.equalsIgnoreCase(filter)) {
					return true;
				} else if (num == null) {
					continue;
				} else if (num.equalsIgnoreCase(filter)) {
					return true;
				}
			}
			return false;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(1024);
		builder.append("types :");
		for (Type type : types) {
			builder.append(" ");
			builder.append(type.name());
		}
		builder.append("\n");
		builder.append("catégories :");
		for (Catégorie catégorie : catégories) {
			builder.append(" ");
			builder.append(catégorie.name());
		}
		builder.append("\n");
		builder.append("genres :");
		for (Genre genre : genres) {
			builder.append(" ");
			builder.append(genre.name());
		}
		builder.append("\n");
		builder.append("niveaux :");
		for (Niveau niveau : niveaux) {
			builder.append(" ");
			builder.append(niveau.name());
		}
		builder.append("\n");
		builder.append("phases :");
		for (Integer phase : phases) {
			builder.append(" ");
			builder.append(phase);
		}
		builder.append("\n");
		builder.append("poules :");
		for (String poule : poules) {
			builder.append(" ");
			builder.append(poule);
		}
		builder.append("\n");
		builder.append("journées :");
		for (Integer journée : journées) {
			builder.append(" ");
			builder.append(journée);
		}
		return builder.toString();
	}
	
}
