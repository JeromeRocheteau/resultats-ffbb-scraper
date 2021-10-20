package com.ffbb.resultats.tests;

import org.junit.Before;

import com.ffbb.resultats.RésultatsFFBB;

public abstract class ResultatsExtraction {
	
	protected RésultatsFFBB extractor;
	
	@Before
	public void setUp() {
		// extractor = new RésultatsFFBB("/usr/bin/phantomjs");
		extractor = new RésultatsFFBB("C:\\Program Files (x86)\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
	}
	
}
