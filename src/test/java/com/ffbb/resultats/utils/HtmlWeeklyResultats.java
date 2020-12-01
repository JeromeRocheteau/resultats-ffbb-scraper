package com.ffbb.resultats.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
import com.ffbb.resultats.tests.ResultatsExtraction;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HtmlWeeklyResultats extends ResultatsExtraction {

	/*
	 * private ChampionnatComparator comparator;
	 * 
	 * private Map<String, Integer> nums;
	 * 
	 * private ChampionnatFiltre filtre1;
	 * 
	 * private ChampionnatFiltre filtre2;
	 * 
	 * private MultipleFiltres filtre3;
	 * 
	 * private ChampionnatFiltre filtre4;
	 * 
	 * private Map<String, Filtre> organisations;
	 * 
	 * private List<Championnat> championnats;
	 * 
	 * private Map<Championnat, Rencontre> rencontres;
	 * 
	 * private Map<Championnat, Classement> classements;
	 * 
	 * private Date début;
	 * 
	 * private Date fin;
	 * 
	 * public HtmlWeeklyResultats() throws Exception { super(); comparator = new
	 * ChampionnatComparator(); nums = new HashMap<String, Integer>(); championnats
	 * = new LinkedList<Championnat>(); rencontres = new HashMap<Championnat,
	 * Rencontre>(); classements = new HashMap<Championnat, Classement>(); filtre1 =
	 * new ChampionnatFiltre() .catégories(Catégorie.U15) .genres(Genre.Féminin)
	 * .niveaux(Niveau.Régional) .phases(1); filtre2 = new ChampionnatFiltre()
	 * .niveaux(Niveau.PréRégional, Niveau.Départemental) .phases(1); filtre3 = new
	 * MultipleFiltres().filtres(filtre1, filtre2); filtre4 = new
	 * ChampionnatFiltre() .niveaux(Niveau.Départemental) .genres(Genre.Féminin)
	 * .catégories(Catégorie.Senior) .phases(1); organisations = new HashMap<String,
	 * Filtre>(); organisations.put("2226", filtre3); organisations.put("223c",
	 * filtre4); début = new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2019-12-07 00:00"); fin = new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2019-12-08 23:59"); }
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
	 * this.doInfo("nombre d'engagements : " + engagements.size());
	 * this.doInfo("extraction des rencontres pour le week-end du " + new
	 * SimpleDateFormat("dd/MM/yyyy").format(début) + " au " + new
	 * SimpleDateFormat("dd/MM/yyyy").format(fin)); for (Engagement engagement :
	 * engagements) { Compétition compétition = engagement.getCompétition(); if
	 * (filtre.match(compétition)) { Championnat championnat = (Championnat)
	 * compétition; this.doInfo("extraction des rencontres du " + championnat);
	 * List<Rencontre> liste = extractor.getRencontres(organisation, championnat,
	 * début, fin); if (liste.size() > 0 && liste.size() == 1) { Rencontre rencontre
	 * = liste.get(0); if (championnat.getCatégorie() == Catégorie.U9) {
	 * championnats.add(championnat); rencontres.put(championnat, rencontre); } else
	 * { this.doInfo("extraction du classement du " + championnat); Classement
	 * classement = extractor.getClassement(organisation, championnat);
	 * championnats.add(championnat); rencontres.put(championnat, rencontre);
	 * classements.put(championnat, classement); } } } } }
	 * 
	 * private void doWrite() throws Exception { this.doInfo("début de l'écriture");
	 * Collections.sort(championnats, comparator); StringBuilder html = new
	 * StringBuilder(1024 * 1024); this.doHead(html); for (Championnat championnat :
	 * championnats) { Rencontre rencontre = rencontres.get(championnat); Classement
	 * classement = classements.get(championnat); this.doBody(championnat,
	 * rencontre, classement, html); } this.doTail(html); File file = new
	 * File("/home/jerome/Bureau/naclt-resultats.html");
	 * this.doInfo("écriture dans le fichier " + file.getAbsolutePath());
	 * OutputStream out = new FileOutputStream(file);
	 * out.write(html.toString().getBytes()); out.close();
	 * this.doInfo("fin de l'écriture"); }
	 * 
	 * private static final String SEP = "\n";
	 * 
	 * private void doHead(StringBuilder html) { html.append("<ul>");
	 * html.append(SEP); }
	 * 
	 * private void doTail(StringBuilder html) { html.append("</ul>");
	 * html.append(SEP); }
	 * 
	 * private void doBody(Championnat championnat, Rencontre rencontre, Classement
	 * classement, StringBuilder html) { String href = this.doLink(championnat,
	 * rencontre); html.append("\t"); html.append("<li>"); html.append("<strong>");
	 * this.doTeam(championnat, html); html.append("</strong>");
	 * html.append(" (<a href=\"" + href + "\">"); this.doChampionnat(championnat,
	 * html); html.append("</a>) : "); this.doRencontre(championnat, rencontre,
	 * html); html.append("<br/>"); this.doClassement(championnat, classement,
	 * html); html.append("</li>"); html.append(SEP); }
	 * 
	 * private String doLink(Championnat championnat, Rencontre rencontre) { Integer
	 * journée = rencontre.getJournée(); String id = championnat.getCode(); Long d =
	 * championnat.getId(); Long r = 0L; // FIXME
	 * championnat.getParamètres().getR(); return
	 * "http://resultats.ffbb.com/championnat/" + id + ".html?r=" + r.toString() +
	 * "&d=" + d.toString() + "&p=" + journée.toString(); }
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
	 * private void doRencontre(Championnat championnat, Rencontre rencontre,
	 * StringBuilder html) { boolean domicile = this.isDomicile(rencontre); if
	 * (domicile) { String adversaires = rencontre.getVisiteur().getNom(); if
	 * (rencontre.getRésultat() == null) { this.doRencontre(domicile, adversaires,
	 * 0, 0, false, html); } else { int nous =
	 * rencontre.getRésultat().getDomicile(); int eux =
	 * rencontre.getRésultat().getVisiteur(); this.doRencontre(domicile,
	 * adversaires, nous, eux, nous > eux, html); } } else { String adversaires =
	 * rencontre.getDomicile().getNom(); if (rencontre.getRésultat() == null) {
	 * this.doRencontre(domicile, adversaires, 0, 0, false, html); } else { int nous
	 * = rencontre.getRésultat().getVisiteur(); int eux =
	 * rencontre.getRésultat().getDomicile(); this.doRencontre(domicile,
	 * adversaires, nous, eux, nous > eux, html); } } }
	 * 
	 * private boolean isDomicile(Rencontre rencontre) { String code =
	 * rencontre.getDomicile().getOrganisation().getCode(); for (String c :
	 * organisations.keySet()) { if (c.equals(code)) { return true; } } return
	 * false; }
	 * 
	 * private void doRencontre(boolean domicile, String adversaires, int nous, int
	 * eux, boolean victoire, StringBuilder html) { this.doVictoire(victoire, html);
	 * this.doScore(nous, eux, html); this.doAdversaire(domicile, adversaires,
	 * html); }
	 * 
	 * private void doAdversaire(boolean domicile, String adversaires, StringBuilder
	 * html) { html.append(domicile ? "contre " : "chez ");
	 * html.append(adversaires); }
	 * 
	 * private void doVictoire(boolean victoire, StringBuilder html) {
	 * html.append(victoire ? "victoire" : "défaite"); }
	 * 
	 * private void doScore(int nous, int eux, StringBuilder html) { if (nous == 0&&
	 * eux == 0) { html.append(" - "); } else { html.append(" "); html.append(nous);
	 * html.append(" - "); html.append(eux); html.append(" "); } }
	 * 
	 * private void doClassement(Championnat championnat, Classement classement,
	 * StringBuilder html) { if (classement == null) {
	 * this.doWarn("pas de classement extrait !"); } else { boolean male =
	 * championnat.getGenre() == Genre.Masculin; boolean first =
	 * classement.getRang() == 1; // html.append(" (");
	 * html.append(classement.getVictoires().intValue()); if
	 * (classement.getVictoires().intValue() > 1) { html.append(" victoires - "); }
	 * else { html.append(" victoire - "); }
	 * html.append(classement.getDéfaites().intValue()); if
	 * (classement.getDéfaites().intValue() > 1) { html.append(" défaites - "); }
	 * else { html.append(" défaite, "); } html.append(classement.getRang()); if
	 * (first && male) { html.append("<sup>er</sup>"); } else if (first && ! male) {
	 * html.append("<sup>re</sup>"); } else { html.append("<sup>e</sup>"); } //
	 * html.append(")"); } }
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
