package com.ffbb.resultats.tests;

import java.net.URI;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.ffbb.resultats.ChampionnatExtractor;
import com.ffbb.resultats.api.Championnat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChampionnatExtraction {

	private ChampionnatExtractor extractor;
	
	public ChampionnatExtraction() {
		extractor = new ChampionnatExtractor();
	}
	
	@Test
	public void test_01_naclt_u13f1() throws Exception {
		String url = "https://resultats.ffbb.com/championnat/b5e6211f1950.html?r=200000002791760&d=200000002873755";
		Championnat championnat = extractor.doExtract(URI.create(url));
		Assert.assertNotNull(championnat);
	}
	
	@Test
	public void test_02_naclt_sm1() throws Exception {
		String url = "https://resultats.ffbb.com/championnat/b5e6211eeed6.html?r=200000002780886&d=200000002862119";
		Championnat championnat = extractor.doExtract(URI.create(url));
		Assert.assertNotNull(championnat);
	}
	
}
