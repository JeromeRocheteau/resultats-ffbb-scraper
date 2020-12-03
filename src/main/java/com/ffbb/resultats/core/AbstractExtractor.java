package com.ffbb.resultats.core;

import java.net.URI;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.ProtocolHandshake;

import com.ffbb.resultats.RésultatsFFBB;
import com.ffbb.resultats.api.Catégorie;
import com.ffbb.resultats.api.Championnat;
import com.ffbb.resultats.api.Division;
import com.ffbb.resultats.api.Extractable;
import com.ffbb.resultats.api.Genre;
import com.ffbb.resultats.api.Niveau;
import com.ffbb.resultats.api.Organisation;
import com.ffbb.resultats.api.Salle;
import com.ffbb.resultats.api.Équipe;

public abstract class AbstractExtractor<T> {

	private RésultatsFFBB résultatsFFBB;
	
	private Ressources ressources;
	
	protected Organisation getOrganisation(String code) throws Exception {
		return résultatsFFBB.getOrganisation(code); 
	}
	
	protected Salle getSalle(Long id) throws Exception {
		return résultatsFFBB.getSalle(id); 
	}
	
	protected Division getDivision(Long id, String code, Long division) throws Exception {
		return résultatsFFBB.getDivision(id, code, division);
	}
	
	protected Équipe getÉquipe(Division division, Long id, String code) throws Exception {
		return résultatsFFBB.getÉquipe(division, id, code);
	}
	
	protected void doInfo(String message) {
		Logger.getLogger(RésultatsFFBB.class.getSimpleName()).log(Level.INFO, message);
	}

	protected void doWarn(String message) {
		Logger.getLogger(RésultatsFFBB.class.getSimpleName()).log(Level.WARNING, message);
	}

	protected void doFailure(String message) {
		Logger.getLogger(RésultatsFFBB.class.getSimpleName()).log(Level.SEVERE, message);
	}

	protected <U extends Extractable> U doFind(Class<U> type, URI uri) throws Exception {
		return ressources.doFind(type, uri);
	}
	
	protected <U extends Extractable> void doBind(Class<U> type, URI uri, U resource) throws Exception {
		if (this.doFind(type, uri) == null) {
			ressources.doBind(type, uri, resource);			
		} else {
			this.doWarn("Already bound resource of " + type.getSimpleName() + " for URI: " + uri);
		}
	}
	
	protected AbstractExtractor(RésultatsFFBB résultatsFFBB) {
		ressources = Ressources.getInstance();
		this.résultatsFFBB = résultatsFFBB;
	}
	
	protected Document getDocument(URI uri) throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setJavascriptEnabled(true);  
		capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, résultatsFFBB.getPhantomJsPath());
		capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[] {"--webdriver-loglevel=NONE"});
		Logger.getLogger(PhantomJSDriverService.class.getName()).setLevel(Level.OFF);
		Logger.getLogger(ProtocolHandshake.class.getName()).setLevel(Level.OFF);		
		WebDriver driver = new  PhantomJSDriver(capabilities);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);		
		try {
			driver.get(uri.toString());
			return Jsoup.parse(driver.getPageSource());
		} finally {
			driver.quit();
		}
	}	
	
	protected Niveau getNiveau(String[] words) throws Exception {
		boolean pré = false;
		for (String word : words) {
			pré = pré || this.getPré(word);
		}
		for (String word : words) {
			Niveau niveau = this.getNiveau(word, pré);
			if ((niveau == null) == false) {
				return niveau;
			}
		}
		return null;
	}
	
	private Niveau getNiveau(String word, boolean pré) throws Exception {
		String text = word.toLowerCase();
		if (text.startsWith(Niveau.Départemental.name().toLowerCase())) {
			return Niveau.Départemental;
		} else if (text.startsWith(Niveau.Régional.name().toLowerCase())) {
			return pré ? Niveau.PréRégional : Niveau.Régional;
		} else if (text.startsWith(Niveau.National.name().toLowerCase())) {
			return pré ? Niveau.PréNational : Niveau.National;
		} else {
			return null;
		}
	}
	
	private boolean getPré(String word) throws Exception {
		if (word.equals("Pré")) {
			return true;
		} else {
			return false;
		}
	}

	protected Integer getPhase(String[] words) throws Exception {
		boolean next = false;
		for (String word : words) {
			if (next) {
				return Integer.valueOf(word);
			} else if (word.equalsIgnoreCase("phase")) {
				next = true;
			}
			
		}
		return 1;
	}
	
	protected Integer getDivNum(String[] words) {
		boolean next = false;
		for (String word : words) {
			if (next) {
				return Integer.valueOf(word);
			} else if (word.equalsIgnoreCase("division")) {
				next = true;
			}
			
		}
		return null;
	}
	
	protected Integer getDivNum(Niveau niveau, String[] words) {
		String niv = niveau.toString();
		int len = niv.length();
		for (String word : words) {
			if (word.startsWith(niv)) {
				try {
					String val = word.substring(len);
					return Integer.valueOf(val);
				} catch (Exception e) {
					continue;
				}
			}
			
		}
		return null;
	}
	
	protected void setDivNum(Championnat championnat, String[] words) {
		if (championnat.getNum() == null) {
			Integer num = this.getDivNum(championnat.getNiveau(), words);
			System.out.println(championnat + " | division = " + num);
			championnat.setNum(num);
		} else {
			System.out.println(championnat + " | division = null");
		}
	}
	
	protected String getPouleNum(String[] words) {
		boolean next = false;
		for (String word : words) {
			if (next) {
				System.out.println("poule = " + word);
				return word;
			} else if (word.equalsIgnoreCase("poule")) {
				next = true;
			}
			
		}
		return null;
	}

	protected Genre getGenre(String[] words) throws Exception {
		for (String word : words) {
			Genre genre = this.getGenre(word);
			if ((genre == null) == false) {
				return genre;
			}
		}
		return null;
	}

	private Genre getGenre(String word) throws Exception {
		if (word.toLowerCase().startsWith(Genre.Féminin.name().toLowerCase())) {
			return Genre.Féminin;
		} else if (word.toLowerCase().startsWith(Genre.Masculin.name().toLowerCase())) {
			return Genre.Masculin;
		} else {
			return null;
		}
	}

	protected Catégorie getCatégorie(String[] words) throws Exception {
		for (String word : words) {
			Catégorie catégorie = this.getCatégorie(word);
			if ((catégorie == null) == false) {
				return catégorie;
			}
		}
		return Catégorie.Senior;
	}
	
	private Catégorie getCatégorie(String word) throws Exception {
		if (word.equalsIgnoreCase(Catégorie.Senior.name())) {
			return Catégorie.Senior;
		} else if (word.equalsIgnoreCase(Catégorie.U20.name())) {
			return Catégorie.U20;
		} else if (word.equalsIgnoreCase(Catégorie.U18.name())) {
			return Catégorie.U18;
		} else if (word.equalsIgnoreCase(Catégorie.U17.name())) {
			return Catégorie.U17;
		} else if (word.equalsIgnoreCase(Catégorie.U15.name())) {
			return Catégorie.U15;
		} else if (word.equalsIgnoreCase(Catégorie.U13.name())) {
			return Catégorie.U13;
		} else if (word.equalsIgnoreCase(Catégorie.U11.name())) {
			return Catégorie.U11;
		} else if (word.equalsIgnoreCase(Catégorie.U9.name())) {
			return Catégorie.U9;
		} else {
			return null;
		}
	}


}
