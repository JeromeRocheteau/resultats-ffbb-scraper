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
import com.ffbb.resultats.api.Division;
import com.ffbb.resultats.api.Extractable;
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
		capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "/usr/bin/phantomjs");
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

}
