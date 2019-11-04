package com.ffbb.resultats;

import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ffbb.resultats.api.Catégorie;
import com.ffbb.resultats.api.Championnat;
import com.ffbb.resultats.api.Compétition;
import com.ffbb.resultats.api.Coupe;
import com.ffbb.resultats.api.Engagement;
import com.ffbb.resultats.api.Genre;
import com.ffbb.resultats.api.Niveau;
import com.ffbb.resultats.api.Organisation;
import com.ffbb.resultats.api.Paramètres;
import com.ffbb.resultats.api.Plateau;

public class EngagementsExtractor extends AbstractExtractor<List<Engagement>> {
		
	private static final String CHAMPIONNAT = "Equipes engagées en championnat";

	private static final String CHAMPIONNAT3x3 = "Equipes engagées en championnat 3x3";
	
	private static final String COUPE = "Equipes engagées en coupe";

	private static final String PLATEAU = "Equipes engagées en plateau";
	
	private static final String MASCULIN = "Equipes masculines";
	
	private static final String FÉMININ = "Equipes féminines";
	
	private Organisation organisation;
	
	public List<Engagement> doExtract(Organisation organisation) throws Exception {
		this.organisation = organisation;
		String code = organisation.getCode();
		String link = "http://resultats.ffbb.com/organisation/engagements/" + code + ".html";
		URI uri = URI.create(link);
		return this.doExtract(uri);
	}

	public List<Engagement> doExtract(URI uri) throws Exception {
		Document doc = this.getDocument(uri);
		Elements tbodies = doc.select("table.liste"); // FIXME
		for (Element tbody : tbodies) {
			this.getEngagements(tbody);
		}
		return organisation.getEngagements();
	}
	
	private void getEngagements(Element table) throws Exception {
		Elements rows = table.select("tr");
		Iterator<Element> iter = rows.iterator();
		Compétition.Type type = null; 
		Genre genre = null;
		while (iter.hasNext()) {
			Element row = iter.next();
			Elements cols = row.select("td");
			Element cell = cols.first();
			String text = cell.text().trim();
			if (CHAMPIONNAT.equals(text)) {
				type = Compétition.Type.Championnat;
			} else if (CHAMPIONNAT3x3.equals(text)) {
				type = Compétition.Type.Championnat3x3;
			} else if (COUPE.equals(text)) {
				type = Compétition.Type.Coupe;
			} else if (PLATEAU.equals(text)) {
				type = Compétition.Type.Plateau;
			} else if (MASCULIN.equals(text)) {
				genre = Genre.Masculin;
			} else if (FÉMININ.equals(text)) {
				genre = Genre.Féminin;
			} else if (cols.size() > 1) {
				String link = cell.select("a").attr("href");
				Paramètres paramètres = this.getParamètres(link);
				Catégorie catégorie = this.getCatégorie(cols.get(1).text());
				Compétition compétition = this.getCompétition(paramètres, genre, catégorie, type, text);
				Engagement engagement = new Engagement(organisation, compétition);
				organisation.getEngagements().add(engagement);
			}
		}
	}
		
	private Paramètres getParamètres(String link) throws Exception {
		Pattern pattern = Pattern.compile("\\.\\./\\.\\./championnat/(.*)\\.html\\?r=(.*)&d=(.*)");
		Matcher matcher = pattern.matcher(link);
		if (matcher.matches() && matcher.groupCount() == 3) {
			String id = matcher.group(1);
			Long r = Long.valueOf(matcher.group(2));
			Long d = Long.valueOf(matcher.group(3));
			return new Paramètres(id, r, d);
		} else {
			throw new Exception();
		}
	}

	private Compétition getCompétition(Paramètres paramètres, Genre genre, Catégorie catégorie, Compétition.Type type, String text) throws Exception {
		String head = this.getHead(text).toLowerCase();
		if (type == null) {
			throw new Exception();
		} else if (type == Compétition.Type.Championnat) {
			String tail = this.getTail(text).toLowerCase();
			return getChampionnat(paramètres, genre, catégorie, head, tail);
		} else if (type == Compétition.Type.Championnat3x3) {
			throw new Exception();
		} else if (type == Compétition.Type.Coupe) {
			return new Coupe(paramètres, genre, catégorie);
		} else if (type == Compétition.Type.Plateau) {
			Integer numero = this.getNumero(text);
			return new Plateau(paramètres, genre, catégorie, numero);
		} else {
			throw new Exception();
		}
	}

	private Compétition getChampionnat(Paramètres paramètres, Genre genre, Catégorie catégorie, String head, String tail) throws Exception {
		Niveau niveau = this.getNiveau(head);
		Integer phase = this.getPhase(head);
		Integer division = this.getDivision(catégorie, niveau, phase, head, tail);
		String poule = this.getPoule(catégorie, niveau, phase, tail);
		return new Championnat(paramètres, genre, catégorie, niveau, phase, division, poule);
	}

	private Integer getDivision(Catégorie catégorie, Niveau niveau, Integer phase, String head, String tail) throws Exception {
		if (niveau == Niveau.Départemental) {
			return getDivisionDep(catégorie, niveau, phase, head, tail);			
		} else if (niveau == Niveau.PréRégional) {
			return 1;
		} else if (niveau == Niveau.Régional) {
			return getDivisionReg(phase, head, tail);
		} else if (niveau == Niveau.PréNational) {
			return 1;
		} else if (niveau == Niveau.National) {
			return null;
		} else {
			throw new Exception();
		}
	}

	private Integer getDivisionDep(Catégorie catégorie, Niveau niveau, Integer phase, String head, String tail) throws Exception {
		if (catégorie == Catégorie.Senior || catégorie == Catégorie.U9) {
			return this.getDivisionDep(niveau, phase, head);
		} else {
			String[] items = tail.split("-");
			if (items.length == 2) {
				String fst = items[0].trim();
				String snd = items[1].trim();
				if (snd.equals("elite")) {
					return 0;
				} else {
					return getDivisionDep(niveau, fst);
				}
			} else {
				throw new Exception();
			}
		}
	}

	private Integer getDivisionDep(Niveau niveau, Integer phase, String text) throws Exception {
		if (phase == 1) {
			return this.getDivisionDep(text);
		} else if (phase == 2) {
			String suffix = "- phase 2";
			return this.getDivisionDep(text.substring(0, text.length() - suffix.length()));
		} else {
			throw new Exception();
		}
	}

	private Integer getDivisionDep(String text) throws Exception {
		String[] items = text.split("-");
		if (items.length == 2) {
			String snd = items[1].trim();
			if (snd.startsWith("division ")) {
				return Integer.valueOf(snd.substring("division ".length()));
			} else {
				throw new Exception();	
			}
		} else {
			return null;
		}
	}

	private Integer getDivisionDep(Niveau niveau, String text) throws Exception {
		if (text.startsWith(niveau.toString().toLowerCase())) {
			if (text.startsWith("d")) {
				return Integer.valueOf(text.substring(1, 2));
			} else {
				return null;
			}
		} else {
			throw new Exception(niveau.toString() + " '" + text + "'");
		}
	}

	private Integer getDivisionReg(Integer phase, String head, String tail) {
		if (tail.startsWith("r")) {
			return Integer.valueOf(tail.substring(1, 2));
		} else {
			return null;
		}
	}

	private String getPoule(Catégorie catégorie, Niveau niveau, Integer phase, String text) throws Exception {
		if (niveau == Niveau.Départemental) {
			return getPouleDep(catégorie, text);			
		} else if (niveau == Niveau.PréRégional) {
			return null;
		} else if (niveau == Niveau.Régional) {
			return getPouleReg(phase, text);
		} else if (niveau == Niveau.PréNational) {
			return null;
		} else if (niveau == Niveau.National) {
			return null;
		} else {
			throw new Exception();
		}
	}

	private String getPouleReg(Integer phase, String text) throws Exception {
		if (phase == 1) {
			String poule = getPoule(text);
			int index = poule.lastIndexOf("-");
            if (index == -1) {
                return poule;
            } else {
            	return poule.substring(index);
            }
		} else if (phase == 2) {
			String[] items = text.split("\\s+");
			if (items.length == 3) {
				if (items[1].trim().equals("poule")) {
					return items[2].trim();
				} else {
					throw new Exception();	
				}
			} else {
				throw new Exception();		
			}
		} else {
			throw new Exception();
		}
	}

	private String getPouleDep(Catégorie catégorie, String text) throws Exception {
		if (catégorie == Catégorie.Senior || catégorie == Catégorie.U9) {
			return getPoule(text);
		} else {
			String[] items = text.split("-");
			if (items.length == 2) {
				String fst = items[0].trim();
				String snd = items[1].trim();
				if (snd.equals("elite")) {
					return fst;
				} else {
					return getPoule(snd);
				}
			} else {
				throw new Exception();
			}
		}
	}

	private String getPoule(String text) throws Exception {
		if (text.startsWith("poule ")) {
			return text.substring("poule ".length());
		} else if (text.startsWith("défi ")) {
			// int index = text.lastIndexOf("-");
			// return text.substring(index + 2);
			return text;
		} else {
			throw new Exception();
		}
	}

	private Integer getPhase(String text) {
		if (text.endsWith("- phase 2")) {
			return 2;
		} else {
			return 1;
		}
	}

	private Niveau getNiveau(String text) throws Exception {
		if (text.startsWith("pré ")) {
			Niveau niveau = this.getNiveau(text.substring(4));
			if (niveau == Niveau.National) {
				return Niveau.PréNational;
			} else if (niveau == Niveau.Régional) {
				return Niveau.PréRégional;
			} else {
				throw new Exception("unexepected niveau: '" + text + "'");
			}
		} if (text.startsWith(Niveau.Départemental.name().toLowerCase())) {
			return Niveau.Départemental;
		} else if (text.startsWith(Niveau.Régional.name().toLowerCase())) {
			return Niveau.Régional;
		} else if (text.startsWith(Niveau.National.name().toLowerCase())) {
			return Niveau.National;
		} else {
			throw new Exception();
		}
	}

	private Catégorie getCatégorie(String text) throws Exception {
		if (text.startsWith(Catégorie.Senior.name())) {
			return Catégorie.Senior;
		} else if (text.equals(Catégorie.U20.name())) {
			return Catégorie.U20;
		} else if (text.equals(Catégorie.U18.name())) {
			return Catégorie.U18;
		} else if (text.equals(Catégorie.U17.name())) {
			return Catégorie.U17;
		} else if (text.equals(Catégorie.U15.name())) {
			return Catégorie.U15;
		} else if (text.equals(Catégorie.U13.name())) {
			return Catégorie.U13;
		} else if (text.equals(Catégorie.U11.name())) {
			return Catégorie.U11;
		} else if (text.equals(Catégorie.U9.name())) {
			return Catégorie.U9;
		} else {
			throw new Exception();
		}
	}

	private Integer getNumero(String text) throws Exception {
		String[] items = text.split("-");
		if (items.length == 2) {
			String item = items[1].trim();
			if (item.startsWith("N°")) {
				return Integer.valueOf(item.substring(2));
			} else {
				throw new Exception("unexepected plateau number format: '" + item + "'");
			}
		} else {
			throw new Exception("unexepected plateau format: '" + text + "'");
		}
	}

	private String getHead(String text) {
		int inf = text.indexOf("(");
		if (inf > 0) {
			return text.substring(0, inf);
		} else {
			return text;
		}
	}
	
	private String getTail(String text) {
		int inf = text.indexOf("(");
		if (inf > 0) {
			int sup = text.indexOf(")");
			return text.substring(inf + 1, sup);
		} else {
			return text;
		}
	}
	
}
