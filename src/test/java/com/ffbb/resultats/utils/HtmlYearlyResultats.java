package com.ffbb.resultats.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.ffbb.resultats.api.Catégorie;
import com.ffbb.resultats.api.Championnat;
import com.ffbb.resultats.api.Classement;
import com.ffbb.resultats.api.Compétition;
import com.ffbb.resultats.api.Engagement;
import com.ffbb.resultats.api.Filtre;
import com.ffbb.resultats.api.Genre;
import com.ffbb.resultats.api.Niveau;
import com.ffbb.resultats.api.Organisation;
import com.ffbb.resultats.api.Rencontre;
import com.ffbb.resultats.api.Équipe;
import com.ffbb.resultats.tests.ResultatsExtraction;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HtmlYearlyResultats extends ResultatsExtraction {

	/*
	 * private ChampionnatComparator comparator;
	 * 
	 * private Map<String, Integer> nums;
	 * 
	 * private ChampionnatFiltre filtreChampionnat;
	 * 
	 * private MultipleFiltres filtreNort;
	 * 
	 * // private ChampionnatFiltre filtreSaffré;
	 * 
	 * private Map<String, Filtre> organisations;
	 * 
	 * private List<Championnat> championnats;
	 * 
	 * private Map<Championnat, Classement> classements;
	 * 
	 * public HtmlYearlyResultats() throws Exception { super(); comparator = new
	 * ChampionnatComparator(); nums = new HashMap<String, Integer>(); championnats
	 * = new LinkedList<Championnat>(); classements = new HashMap<Championnat,
	 * Classement>(); filtreChampionnat = new
	 * ChampionnatFiltre().catégories(Catégorie.U9); filtreNort = new
	 * MultipleFiltres().filtres(filtreChampionnat);
	 * 
	 * filtreSaffré = new ChampionnatFiltre() .genres(Genre.Féminin)
	 * .catégories(Catégorie.Senior) .niveaux(Niveau.Départemental);
	 * 
	 * organisations = new HashMap<String, Filtre>(); organisations.put("2226",
	 * filtreNort); // organisations.put("223c", filtreSaffré); }
	 * 
	 * @Test public void test() throws Exception {
	 * Logger.getAnonymousLogger().setLevel(Level.SEVERE);
	 * this.doInfo("début de l'extraction"); for (String code :
	 * organisations.keySet()) { this.doExtract(code, organisations.get(code)); }
	 * this.doInfo("fin de l'extraction"); this.doWrite(); }
	 * 
	 * private void doExtract(String code, Filtre filtre) throws Exception {
	 * this.doInfo("extraction de l'organisation"); Organisation organisation =
	 * extractor.getOrganisation(code); Assert.assertNotNull(organisation);
	 * this.doInfo("extraction des engagements de l'organisation " +
	 * organisation.getName()); List<Engagement> engagements =
	 * extractor.getEngagements(organisation); Assert.assertNotNull(engagements);
	 * for (Engagement engagement : engagements) { Compétition compétition =
	 * engagement.getCompétition(); if (filtre.match(compétition)) { Championnat
	 * championnat = (Championnat) compétition; if (championnat.getCatégorie() ==
	 * Catégorie.U9) { this.doInfo("extraction des rencontres du championnat " +
	 * championnat); List<Rencontre> rencontres =
	 * extractor.getRencontres(organisation, championnat); Classement classement =
	 * this.getClassement(organisation, championnat, rencontres);
	 * this.championnats.add(championnat); this.classements.put(championnat,
	 * classement); } else { this.doInfo("extraction du classement du championnat "
	 * + championnat); Classement classement = extractor.getClassement(organisation,
	 * championnat); this.championnats.add(championnat);
	 * this.classements.put(championnat, classement); } } } }
	 * 
	 * private Classement getClassement(Organisation organisation, Championnat
	 * championnat, List<Rencontre> rencontres) { Équipe équipe = null; int rang =
	 * 0; int victoires = 0; int défaites = 0; int pour = 0; int contre = 0; for
	 * (Rencontre rencontre : rencontres) { Équipe domicile =
	 * rencontre.getDomicile(); Équipe visiteur = rencontre.getVisiteur(); if
	 * (rencontre.getRésultat() == null) { continue; } int domicileScore =
	 * rencontre.getRésultat().getDomicile(); int visiteurScore =
	 * rencontre.getRésultat().getVisiteur(); if
	 * (domicile.getOrganisation().getCode() == organisation.getCode()) { if (équipe
	 * == null) { équipe = domicile; } pour += domicileScore; contre +=
	 * visiteurScore; if (domicileScore > visiteurScore) { victoires++; } else if
	 * (domicileScore < visiteurScore) { défaites++; } else {
	 * this.doWarn("match nul"); } } else if (visiteur.getOrganisation().getCode()
	 * == organisation.getCode()) { if (équipe == null) { équipe = visiteur; } pour
	 * += visiteurScore; contre += domicileScore; if (domicileScore > visiteurScore)
	 * { victoires++; } else if (domicileScore < visiteurScore) { défaites++; } else
	 * { this.doWarn("match nul"); } } } Classement classement = new
	 * Classement(équipe, rang, victoires, défaites, pour, contre); return
	 * classement; }
	 * 
	 * private void doWrite() throws Exception { this.doInfo("début de l'écriture");
	 * Collections.sort(championnats, comparator); StringBuilder html = new
	 * StringBuilder(1024 * 1024); for (Championnat championnat : championnats) {
	 * Classement classement = classements.get(championnat);
	 * this.doBody(championnat, classement, html); } File file = new
	 * File("/home/jerome/Bureau/naclt-all-resultats.html");
	 * this.doInfo("écriture dans le fichier " + file.getAbsolutePath());
	 * OutputStream out = new FileOutputStream(file);
	 * out.write(html.toString().getBytes()); out.close();
	 * this.doInfo("fin de l'écriture"); }
	 * 
	 * private void doBody(Championnat championnat, Classement classement,
	 * StringBuilder html) { String link = this.doLink(championnat);
	 * html.append("<p>"); html.append("<strong>"); this.doTeam(championnat, html);
	 * html.append("</strong>"); html.append("</p>"); html.append("\n");
	 * html.append("<dl>"); html.append("\n"); html.append("  <dt>");
	 * html.append(" en "); html.append("<a href=\""); html.append(link);
	 * html.append("\">"); this.doChampionnat(championnat, html);
	 * html.append("</a>"); html.append("  </dt>"); html.append("\n");
	 * html.append("  <dd>"); this.doClassement(championnat, classement, html);
	 * html.append("  </dd>"); html.append("\n"); html.append("</dl>");
	 * html.append("\n"); html.append("\n"); }
	 * 
	 * private String doLink(Championnat championnat) { String id =
	 * championnat.getCode(); Long d = championnat.getId(); Long r = 0L; // FIXME
	 * championnat.getParamètres().getR(); return
	 * "http://resultats.ffbb.com/championnat/" + id + ".html?r=" + r.toString() +
	 * "&d=" + d.toString(); }
	 * 
	 * private void doTeam(Championnat championnat, StringBuilder html) { String cat
	 * = championnat.getCatégorie() == Catégorie.Senior ? "S" :
	 * championnat.getCatégorie().name(); String gen = (championnat.getCatégorie()
	 * == Catégorie.Senior && championnat.getGenre() == Genre.Masculin) ? "G" :
	 * (championnat.getGenre() == Genre.Masculin ? "M" : "F"); String num =
	 * this.getNum(cat, gen); html.append(cat); html.append(gen); html.append(num);
	 * }
	 * 
	 * private String getNum(String cat, String gen) { String key = cat + gen;
	 * Integer num = nums.get(key); if (num == null) { nums.put(key, 1); return
	 * String.valueOf(1); } else { nums.put(key, num + 1); return String.valueOf(num
	 * + 1); } }
	 * 
	 * private void doChampionnat(Championnat championnat, StringBuilder html) {
	 * html.append(championnat.getNom() + " " + championnat.getPoule()); }
	 * 
	 * private void doClassement(Championnat championnat, Classement classement,
	 * StringBuilder html) { if (classement == null) {
	 * this.doWarn("pas de classement extrait !"); } else { boolean male =
	 * championnat.getGenre() == Genre.Masculin; boolean first =
	 * classement.getRang() == 1; html.append(classement.getVictoires().intValue());
	 * if (classement.getVictoires().intValue() > 1) { html.append(" victoires - ");
	 * } else { html.append(" victoire - "); }
	 * html.append(classement.getDéfaites().intValue()); if
	 * (classement.getDéfaites().intValue() > 1) { html.append(" défaites"); } else
	 * { html.append(" défaite"); } if (classement.getRang() > 0) {
	 * html.append(" - "); html.append(classement.getRang()); if (first && male) {
	 * html.append("<sup>er</sup>"); } else if (first && ! male) {
	 * html.append("<sup>re</sup>"); } else { html.append("<sup>e</sup>"); } } } }
	 * 
	 * private void doInfo(String message) {
	 * Logger.getAnonymousLogger().log(Level.INFO, message); }
	 * 
	 * private void doWarn(String message) {
	 * Logger.getAnonymousLogger().log(Level.WARNING, message); }
	 * 
	 * private class ChampionnatComparator implements Comparator<Championnat> {
	 * 
	 * private CatégorieComparator catégorieComparator;
	 * 
	 * private GenreComparator genreComparator;
	 * 
	 * private NiveauComparator niveauComparator;
	 * 
	 * private DivisionComparator divisionComparator;
	 * 
	 * public ChampionnatComparator() { catégorieComparator = new
	 * CatégorieComparator(); genreComparator = new GenreComparator();
	 * niveauComparator = new NiveauComparator(); divisionComparator = new
	 * DivisionComparator(); }
	 * 
	 * public int compare(Championnat c1, Championnat c2) { int diff =
	 * catégorieComparator.compare(c1.getCatégorie(), c2.getCatégorie()); if (diff
	 * == 0) { diff = genreComparator.compare(c1.getGenre(), c2.getGenre()); if
	 * (diff == 0) { diff = niveauComparator.compare(c1.getNiveau(),
	 * c2.getNiveau()); if (diff == 0) { return diff;
	 * 
	 * diff = divisionComparator.compare(c1.getDivision(), c2.getDivision()); if
	 * (diff == 0) { return c1.getPoule().compareTo(c2.getPoule()); } else { return
	 * diff; }
	 * 
	 * } else { return diff; } } else { return diff; } } else { return diff; } }
	 * 
	 * }
	 * 
	 * private class CatégorieComparator implements Comparator<Catégorie> {
	 * 
	 * public int compare(Catégorie c1, Catégorie c2) { if (c1 == c2) { return 0; }
	 * else if (c1 == Catégorie.U9) { return -1; } else if (c1 == Catégorie.U11 &&
	 * c2 == Catégorie.U9) { return 1; } else if (c1 == Catégorie.U13 && (c2 ==
	 * Catégorie.U9 || c2 == Catégorie.U11)) { return 1; } else if (c1 ==
	 * Catégorie.U15 && (c2 == Catégorie.U9 || c2 == Catégorie.U11 || c2 ==
	 * Catégorie.U13)) { return 1; } else if (c1 == Catégorie.U17 && (c2 ==
	 * Catégorie.U9 || c2 == Catégorie.U11 || c2 == Catégorie.U13 || c2 ==
	 * Catégorie.U15)) { return 1; } else if (c1 == Catégorie.U18 && (c2 ==
	 * Catégorie.U9 || c2 == Catégorie.U11 || c2 == Catégorie.U13 || c2 ==
	 * Catégorie.U15 || c2 == Catégorie.U17)) { return 1; } else if (c1 ==
	 * Catégorie.U20 && (c2 == Catégorie.U9 || c2 == Catégorie.U11 || c2 ==
	 * Catégorie.U13 || c2 == Catégorie.U15 || c2 == Catégorie.U17 || c2 ==
	 * Catégorie.U18)) { return 1; } else if (c1 == Catégorie.Senior && (c2 ==
	 * Catégorie.U9 || c2 == Catégorie.U11 || c2 == Catégorie.U13 || c2 ==
	 * Catégorie.U15 || c2 == Catégorie.U17 || c2 == Catégorie.U18 || c2 ==
	 * Catégorie.U20)) { return 1; } else { return -1; } }
	 * 
	 * }
	 * 
	 * private class GenreComparator implements Comparator<Genre> {
	 * 
	 * public int compare(Genre g1, Genre g2) { if (g1 == g2) { return 0; } else if
	 * (g1 == Genre.Féminin) { return 1; } else { return -1; } }
	 * 
	 * }
	 * 
	 * private class NiveauComparator implements Comparator<Niveau> {
	 * 
	 * public int compare(Niveau n1, Niveau n2) { if (n1 == n2) { return 0; } else
	 * if (n1 == Niveau.National) { return -1; } else if (n1 == Niveau.Régional &&
	 * n2 == Niveau.National) { return 1; } else if (n1 == Niveau.Régional && n2 ==
	 * Niveau.Départemental) { return -1; } else { return 1; } }
	 * 
	 * }
	 * 
	 * private class DivisionComparator implements Comparator<Integer> {
	 * 
	 * public int compare(Integer i1, Integer i2) { if (i1 == i2) { return 0; } else
	 * if (i1 == null) { return -1; } else if (i2 == null) { return 1; } else {
	 * return i1.compareTo(i2); } }
	 * 
	 * }
	 */
}
