package com.ffbb.resultats.tests;

import java.net.URI;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.ffbb.resultats.api.Championnat;
import com.ffbb.resultats.core.ChampionnatExtractor;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChampionnatExtraction {

	private ChampionnatExtractor extractor;
	
	public ChampionnatExtraction() {
		extractor = new ChampionnatExtractor();
	}
	
	@Test
	public void test_01_816_u13f() throws Exception {
		String url = "https://resultats.ffbb.com/championnat/b5e6211f1950.html";
		Championnat championnat = extractor.doExtract(URI.create(url));
		Assert.assertNotNull(championnat);
	}
	
	@Test
	public void test_02_816_prm() throws Exception {
		String url = "https://resultats.ffbb.com/championnat/b5e6211eeed6.html";
		Championnat championnat = extractor.doExtract(URI.create(url));
		Assert.assertNotNull(championnat);
	}
	
	@Test
	public void test_03_816_u9m_bleu() throws Exception {
		String url = "https://resultats.ffbb.com/championnat/b5e6211f1931.html";
		Championnat championnat = extractor.doExtract(URI.create(url));
		Assert.assertNotNull(championnat);
	}
	
}
