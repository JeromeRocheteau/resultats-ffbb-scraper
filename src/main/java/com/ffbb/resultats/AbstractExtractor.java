package com.ffbb.resultats;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
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

import com.ffbb.resultats.api.Organisation;

public abstract class AbstractExtractor<T> implements Extractor<T> {

	protected void doInfo(String message) {
		Logger.getLogger(ExtractorAPI.class.getSimpleName()).log(Level.INFO, message);
	}

	protected void doWarn(String message) {
		Logger.getLogger(ExtractorAPI.class.getSimpleName()).log(Level.WARNING, message);
	}

	protected void doFailure(String message) {
		Logger.getLogger(ExtractorAPI.class.getSimpleName()).log(Level.SEVERE, message);
	}

	protected Map<String, Object> resources;
	
	protected ExtractorAPI extractor;
	
	@SuppressWarnings("unchecked")
	protected <U> U doFind(Class<U> type, URI uri) {
		return (U) resources.get(uri.toString());
	}
	
	protected <U> void doBind(Class<U> type, URI uri, U resource) {
		if (this.doFind(type, uri) == null) {
			resources.put(uri.toString(), resource);			
		} else {
			this.doWarn("Already bound resource of " + type.getSimpleName() + " for URI: " + uri);
		}
	}
	
	protected AbstractExtractor() {
		resources = new HashMap<String, Object>(1024);
		URI uri = URI.create("http://resultats.ffbb.com/organisation/fffffffffffffffb.html");
		Organisation exempt = new Organisation("fffffffffffffffb");
		exempt.setName("Exempt");exempt.setType(Organisation.Type.Entente);
		this.doBind(Organisation.class, uri, exempt);
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
