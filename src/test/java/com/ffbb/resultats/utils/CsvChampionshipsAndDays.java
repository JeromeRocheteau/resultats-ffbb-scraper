package com.ffbb.resultats.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.text.WordUtils;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.ffbb.resultats.api.Catégorie;
import com.ffbb.resultats.api.Championnat;
import com.ffbb.resultats.api.Classement;
import com.ffbb.resultats.api.Compétition;
import com.ffbb.resultats.api.Engagement;
import com.ffbb.resultats.api.Genre;
import com.ffbb.resultats.api.Niveau;
import com.ffbb.resultats.api.Organisation;
import com.ffbb.resultats.api.Rencontre;
import com.ffbb.resultats.api.Salle;
import com.ffbb.resultats.api.Équipe;
import com.ffbb.resultats.filtres.ChampionnatFiltre;
import com.ffbb.resultats.filtres.Filtre;
import com.ffbb.resultats.filtres.MultipleFiltres;
import com.ffbb.resultats.tests.ResultatsExtraction;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CsvChampionshipsAndDays extends ResultatsExtraction {

	private static final SimpleDateFormat frenchDateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	private static final SimpleDateFormat frenchTimeFormatter = new SimpleDateFormat("HH:mm:ss");
	private static final SimpleDateFormat ffbbDateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	private ChampionnatComparator comparator;

	private Map<String, Integer> nums;
	
	private Map<String, Filtre> organisations;
	
	private List<Championnat> championnats;
	
	private Map<Championnat, Rencontre> rencontres;
	
	private Map<Championnat, Classement> classements;
	
	private Date début;
	
	private Date fin;
	
	public CsvChampionshipsAndDays() throws Exception {
		super();
		comparator = new ChampionnatComparator();
		nums = new HashMap<String, Integer>();
		championnats = new LinkedList<Championnat>();
		rencontres = new HashMap<Championnat, Rencontre>();
		classements = new HashMap<Championnat, Classement>();
		
		ChampionnatFiltre filtre0 = new ChampionnatFiltre()
				.niveaux(Niveau.Départemental)
				.catégories(Catégorie.U9)
				.divisions(3)
				.phases(1);
		ChampionnatFiltre filtre1 = new ChampionnatFiltre()
				.niveaux(Niveau.Départemental)
				.phases(2);
		ChampionnatFiltre filtre2 = new ChampionnatFiltre()
				.niveaux(Niveau.Régional)
				.catégories(Catégorie.U15,Catégorie.U18)
				.genres(Genre.Féminin)
				.phases(2);
		ChampionnatFiltre filtre3 = new ChampionnatFiltre()
				.niveaux(Niveau.Départemental)
				.catégories(Catégorie.Senior)
				.genres(Genre.Féminin)
				.divisions(2)
				.phases(1);
		ChampionnatFiltre filtre4 = new ChampionnatFiltre()
				.niveaux(Niveau.PréNational)
				.catégories(Catégorie.Senior)
				.genres(Genre.Masculin)
				.phases(1);
		MultipleFiltres filtreNort = new MultipleFiltres();
		filtreNort.filtres(filtre0, filtre1, filtre2, filtre3, filtre4);
		ChampionnatFiltre filtreSaffre = new ChampionnatFiltre()
				.niveaux(Niveau.Départemental)
				.genres(Genre.Féminin)
				.catégories(Catégorie.Senior)
				.phases(2);
		organisations = new HashMap<String, Filtre>();
		organisations.put("2226", filtreNort);
		organisations.put("223c", filtreSaffre);
		début = ffbbDateTimeFormatter.parse("2020-01-11 00:00");
		fin = ffbbDateTimeFormatter.parse("2020-05-24 23:59");
	}
	
	@Test
	public void doChampionnats() throws Exception {
		Logger.getAnonymousLogger().setLevel(Level.SEVERE);
		Set<Salle> salles = new HashSet<Salle>(512);
		// this.doInfo("début de l'extraction");
		for (String code : organisations.keySet()) {
			this.doExtract(salles, code, organisations.get(code));			
		}
		// this.doInfo("fin de l'extraction");
		// System.out.println("nombre de salles : " + salles.size());
	}

	private void doExtract(Set<Salle> salles, String code, Filtre filtre) throws Exception {
		Organisation organisation = extractor.getOrganisation(code); 
		Assert.assertNotNull(organisation);
		List<Engagement> engagements = extractor.getEngagements(organisation);
		Assert.assertNotNull(engagements);
		engagements.forEach(engagement -> doExtract(engagement, filtre));
	}

	private void doPrint(Salle salle) {
		try {
			if (salle != null) {
				String string = (salle.getDénomination() == null ? "" : salle.getDénomination());
				string += "\t" + (salle.getVille() == null ? "" : salle.getVille());
				string += "\t" + (salle.getAdresse() == null ? "" : salle.getAdresse());
				string += "\t" + (salle.getCodePostal() == null ? "" : salle.getCodePostal());
				string += "\t" + (salle.getLatitude() == null ? "" : this.getPosition(salle.getLatitude()));
				string += "\t" + (salle.getLongitude() == null ? "" : this.getPosition(salle.getLongitude()));
				string += "\t" + salle.getId();
				System.out.println(string);
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	private String getPosition(Float position) {
		try {
			return position.toString().replace('.', ',');
		} catch (Exception e) {
			try {
				return position.toString();
			} catch (Exception ee) {
				return "";
			}
		}
	}

	private void doExtract(Engagement engagement, Filtre filtre) {
		Organisation organisation = engagement.getOrganisation();
		Compétition compétition = engagement.getCompétition();
		if (filtre.match(compétition)) {
			Championnat championnat = (Championnat) compétition;
			try {
				System.out.println(championnat);
				List<Rencontre> rencontres = extractor.getRencontres(organisation, championnat, début, fin);
				this.doProcess(organisation, rencontres);
				System.out.println("\n");
			} catch (Exception e) {
				this.doWarn(e.getMessage());
				e.printStackTrace();
			}

		}
	}

	/*
	private void doInsert(Set<Salle> salles, Salle salle) {
		salles.add(salle);
	}
	*/

	private void doProcess(Organisation organisation, List<Rencontre> rencontres) {
		String[] headers = new String[]{"11/01/2020 - 12/01/2020","18/01/2020 - 19/01/2020","25/01/2020 - 26/01/2020","01/02/2020 - 02/02/2020","08/02/2020 - 09/02/2020","15/02/2020 - 16/02/2020","22/02/2020 - 23/02/2020","29/02/2020 - 01/03/2020","07/03/2020 - 08/03/2020","14/03/2020 - 15/03/2020","21/03/2020 - 24/03/2020","28/03/2020 - 29/03/2020","04/04/2020 - 05/04/2020","11/04/2020 - 12/04/2020","18/04/2020 - 19/04/2020","25/04/2020 - 26/04/2020","02/05/2020 - 03/05/2020","09/05/2020 - 10/05/2020","16/05/2020 - 17/05/2020","23/05/2020 - 24/05/2020"};
		String[][] range = new String[headers.length][9];
		rencontres.forEach(rencontre -> doPopulate(organisation, headers, range, rencontre));
		List<Integer> indexes = Arrays.asList(0, 1, 2, 3);
		this.doPrint(indexes, headers, range);
	}

	private void doPrint(List<Integer> indexes, String[] headers, String[][] range) {
		StringBuilder builder = new StringBuilder(2048);
		for (int j = 0; j < 9; j++) {
			if (indexes.contains(j)) {
				if (j > 0) {
					builder.append('\n');
				}				
				for (int i = 0; i < headers.length ; i++) {	
					if (i > 0) {
						builder.append('\t');
					}
					String cell = range[i][j];
					builder.append(cell == null ? "" : cell);
				}
			} else {
				continue;
			}
		}
		System.out.println(builder.toString());
	}

	private void doPopulate(Organisation organisation, String[] headers, String[][] range, Rencontre rencontre) {
		String date = frenchDateFormatter.format(rencontre.getHoraire());
		String time = frenchTimeFormatter.format(rencontre.getHoraire());
		Équipe host = rencontre.getDomicile();
		Équipe vist = rencontre.getVisiteur();
		Salle salle = rencontre.getSalle();
		boolean hosting = organisation.getCode() == host.getOrganisation().getCode();
		String adversaire = hosting ? vist.getDénomination() : host.getDénomination();
		String place = hosting ? "Doumer" : salle.getDénomination() + " - " + salle.getAdresse() + " " + salle.getVille();
		int i = getIndex(headers, date);
		if (i >= 0) {
			range[i][0] = adversaire;
			range[i][1] = place;
			range[i][2] = date;
			range[i][3] = time;
			range[i][4] = "";
			range[i][5] = "";
			range[i][6] = "";
			range[i][7] = "";
			range[i][8] = "";			
		} else {
			
		}
	}

	private int getIndex(String[] headers, String date) {
		for (int i = 0; i< headers.length; i++) {
			String header = headers[i];
			if (header.startsWith(date) || header.endsWith(date)) {
				return i;
			}
		}
		return -1;
	}

	private String doCapitalize(String poule) {
		return WordUtils.capitalize(poule);
	}

	private String getDisplayItem(Catégorie catégorie, Genre genre) {
		String string = "";
		if (genre == Genre.Masculin) {
			if (catégorie == Catégorie.Senior) {
				
			} else {
				string += catégorie.toString() + genre.toString();	
			}
		} else {
			string += catégorie.toString() + genre.toString();
		}
		return string;
	}

	private String getDisplayItem(Catégorie catégorie, Genre genre, Niveau niveau, Integer division) {
		String string = "";
		if (catégorie == Catégorie.U9) {
			string += getDisplayItem(catégorie, genre) + " - ";
		} else if (catégorie == Catégorie.Senior) {
			string += niveau.toString() + genre.toString() + division;
		} else {
			string += getDisplayItem(catégorie, genre) + " - ";
			if (niveau == Niveau.Régional) {
				string += niveau.toString() + genre.toString() + division;
			} else if (niveau == Niveau.Départemental) {
				if (division == null) {
					
				} else if (division == 0) {
					string += "Elite ";
				} else {
					string += niveau.toString() + division;
				}				
			}
		}
		return string;
	}

	private void doInfo(String message) {
		Logger.getAnonymousLogger().log(Level.INFO, message);
	}

	private void doWarn(String message) {
		Logger.getAnonymousLogger().log(Level.WARNING, message);
	}

	private class ChampionnatComparator implements Comparator<Championnat> {

		private CatégorieComparator catégorieComparator;
		
		private GenreComparator genreComparator;
		
		private NiveauComparator niveauComparator;
		
		private DivisionComparator divisionComparator;
		
		public ChampionnatComparator() {
			catégorieComparator = new CatégorieComparator();
			genreComparator = new GenreComparator();
			niveauComparator = new NiveauComparator();
			divisionComparator = new DivisionComparator();
		}
		
		public int compare(Championnat c1, Championnat c2) {
			int diff = catégorieComparator.compare(c1.getCatégorie(), c2.getCatégorie());
			if (diff == 0) {
				diff = genreComparator.compare(c1.getGenre(), c2.getGenre());
				if (diff == 0) {
					diff = niveauComparator.compare(c1.getNiveau(), c2.getNiveau());
					if (diff == 0) {
						diff = divisionComparator.compare(c1.getDivision(),  c2.getDivision());
						if (diff == 0) {
							return c1.getPoule().compareTo(c2.getPoule());
						} else {
							return diff;
						}
					} else {
						return diff;
					}
				} else {
					return diff;
				}
			} else {
				return diff;
			}
		}
		
	}
	
	private class CatégorieComparator implements Comparator<Catégorie> {

		public int compare(Catégorie c1, Catégorie c2) {
			if (c1 == c2) {
				return 0;
			} else if (c1 == Catégorie.U9) {
				return -1;
			} else if (c1 == Catégorie.U11 && c2 == Catégorie.U9) {
				return 1;
			} else if (c1 == Catégorie.U13 && (c2 == Catégorie.U9 || c2 == Catégorie.U11)) {
				return 1;
			} else if (c1 == Catégorie.U15 && (c2 == Catégorie.U9 || c2 == Catégorie.U11 || c2 == Catégorie.U13)) {
				return 1;
			} else if (c1 == Catégorie.U17 && (c2 == Catégorie.U9 || c2 == Catégorie.U11 || c2 == Catégorie.U13 || c2 == Catégorie.U15)) {
				return 1;
			} else if (c1 == Catégorie.U18 && (c2 == Catégorie.U9 || c2 == Catégorie.U11 || c2 == Catégorie.U13 || c2 == Catégorie.U15 || c2 == Catégorie.U17)) {
				return 1;
			} else if (c1 == Catégorie.U20 && (c2 == Catégorie.U9 || c2 == Catégorie.U11 || c2 == Catégorie.U13 || c2 == Catégorie.U15 || c2 == Catégorie.U17 || c2 == Catégorie.U18)) {
				return 1;
			} else if (c1 == Catégorie.Senior && (c2 == Catégorie.U9 || c2 == Catégorie.U11 || c2 == Catégorie.U13 || c2 == Catégorie.U15 || c2 == Catégorie.U17 || c2 == Catégorie.U18 || c2 == Catégorie.U20)) {
				return 1;
			} else {
				return -1;
			}
		}
		
	}
	
	private class GenreComparator implements Comparator<Genre> {

		public int compare(Genre g1, Genre g2) {
			if (g1 == g2) {
				return 0;
			} else if (g1 == Genre.Féminin) {
				return 1;
			} else {
				return -1;				
			}
		}
		
	}
	
	private class NiveauComparator implements Comparator<Niveau> {

		public int compare(Niveau n1, Niveau n2) {
			if (n1 == n2) {
				return 0;
			} else if (n1 == Niveau.National) {
				return -1;
			} else if (n1 == Niveau.Régional && n2 == Niveau.National) {
				return 1;
			} else if (n1 == Niveau.Régional && n2 == Niveau.Départemental) {
				return -1;
			} else {
				return 1;
			}
		}
		
	}
	
	private class DivisionComparator implements Comparator<Integer> {

		public int compare(Integer i1, Integer i2) {
			if (i1 == i2) {
				return 0;
			} else if (i1 == null) {
				return -1;
			} else if (i2 == null) {
				return 1;
			} else {
				return i1.compareTo(i2);
			}
		}
		
	}
}
