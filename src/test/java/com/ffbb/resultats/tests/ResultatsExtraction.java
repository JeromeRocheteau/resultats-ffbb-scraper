package com.ffbb.resultats.tests;

import org.junit.Before;

import com.ffbb.resultats.RésultatsFFBB;

public abstract class ResultatsExtraction {
	
	protected RésultatsFFBB extractor;
	
	@Before
	public void setUp() {
		extractor = new RésultatsFFBB();
	}
	
}
