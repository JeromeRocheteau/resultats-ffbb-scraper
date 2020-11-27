package com.ffbb.resultats.tests;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.ffbb.resultats.api.Championnat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChampionnatExtraction extends ResultatsExtraction {
	
	@Test
	public void test_01_816_u13f() throws Exception {
		//"https://resultats.ffbb.com/championnat/b5e6211f1950.html";
		Championnat championnat = extractor.getChampionnat("b5e6211f1950");
		Assert.assertNotNull(championnat);
	}
	
	@Test
	public void test_02_816_prm() throws Exception {
		// "https://resultats.ffbb.com/championnat/b5e6211eeed6.html";
		Championnat championnat = extractor.getChampionnat("b5e6211eeed6");
		Assert.assertNotNull(championnat);
	}
	
	@Test
	public void test_03_816_u9m_bleu() throws Exception {
		// "https://resultats.ffbb.com/championnat/b5e6211f1931.html";
		Championnat championnat = extractor.getChampionnat("b5e6211f1931");
		Assert.assertNotNull(championnat);
	}
	
}
