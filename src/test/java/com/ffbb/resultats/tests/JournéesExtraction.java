package com.ffbb.resultats.tests;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.ffbb.resultats.api.Division;
import com.ffbb.resultats.api.Journées;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JournéesExtraction extends ResultatsExtraction {
	
	@Test
	public void test_01_816_u13f_acces_region() throws Exception {
		// "https://resultats.ffbb.com/championnat/b5e6211f1950.html?r=200000002791760&d=200000002873755";
		Division division = extractor.getDivision(200000002791760L, "b5e6211f1950", 200000002873755L);
		Assert.assertNotNull(division);
		Journées journées = extractor.getJournées(division);
		Assert.assertNotNull(journées);
		Assert.assertEquals(10, journées.size());
	}
	
	@Test
	public void test_02_816_prm() throws Exception {
		// "https://resultats.ffbb.com/championnat/b5e6211eeed6.html?r=200000002780886&d=200000002862119";
		Division division = extractor.getDivision(200000002780886L, "b5e6211eeed6", 200000002862119L);
		Assert.assertNotNull(division);
		Journées journées = extractor.getJournées(division);
		Assert.assertNotNull(journées);
		Assert.assertEquals(26, journées.size());
	}
	
	@Test
	public void test_03_816_u9m_bleu_a() throws Exception {
		// "https://resultats.ffbb.com/championnat/b5e6211f1931.html?r=200000002791729&d=200000002873608";
		Division division = extractor.getDivision(200000002791729L, "b5e6211f1931", 200000002873608L);
		Assert.assertNotNull(division);
		Journées journées = extractor.getJournées(division);
		Assert.assertNotNull(journées);
		Assert.assertEquals(10, journées.size());
	}
	
}
