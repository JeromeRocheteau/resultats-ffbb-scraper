package com.ffbb.resultats.tests;

import org.junit.Before;

import com.ffbb.resultats.core.ExtractorAPI;

public abstract class ResultatsExtraction {
	
	protected ExtractorAPI extractor;
	
	@Before
	public void setUp() {
		extractor = new ExtractorAPI();
	}
	
}
