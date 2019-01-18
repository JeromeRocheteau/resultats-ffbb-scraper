package com.ffbb.resultats;

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

public abstract class AbstractExtractor<T> implements Extractor<T> {
	
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
