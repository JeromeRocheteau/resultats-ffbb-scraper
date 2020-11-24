package com.ffbb.resultats;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AbstractExtractor<T> {

	private Gson mapper;
	
	private Extract extract;
	
	protected void doInfo(String message) {
		Logger.getLogger(ExtractorAPI.class.getSimpleName()).log(Level.INFO, message);
	}

	protected void doWarn(String message) {
		Logger.getLogger(ExtractorAPI.class.getSimpleName()).log(Level.WARNING, message);
	}

	protected void doFailure(String message) {
		Logger.getLogger(ExtractorAPI.class.getSimpleName()).log(Level.SEVERE, message);
	}

	protected <U extends Extractable> U doFind(Class<U> type, URI uri) throws Exception {
		return extract.doFind(type, uri);
	}
	
	protected <U extends Extractable> void doBind(Class<U> type, URI uri, U resource) throws Exception {
		if (this.doFind(type, uri) == null) {
			extract.doBind(type, uri, resource);			
		} else {
			this.doWarn("Already bound resource of " + type.getSimpleName() + " for URI: " + uri);
		}
	}
	
	public void doStore(OutputStream output) throws IOException {
		Writer writer = new OutputStreamWriter(output);
		mapper.toJson(extract, writer);
	}
	
	public void doLoad(InputStream input) throws IOException {
		Reader reader = new InputStreamReader(input);
		extract = mapper.fromJson(reader, Extract.class);
	}
	
	protected AbstractExtractor() {
		mapper = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		extract = Extract.getInstance();
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
