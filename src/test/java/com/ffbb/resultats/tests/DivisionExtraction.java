package com.ffbb.resultats.tests;

import java.net.URI;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.ffbb.resultats.api.Division;
import com.ffbb.resultats.core.DivisionExtractor;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DivisionExtraction {

	private DivisionExtractor extractor;
	
	public DivisionExtraction() {
		extractor = new DivisionExtractor();
	}
	
	@Test
	public void test_01_816_u13f_acces_region() throws Exception {
		String url = "https://resultats.ffbb.com/championnat/b5e6211f1950.html?r=200000002791760&d=200000002873755";
		Division division = extractor.doExtract(URI.create(url));
		Assert.assertNotNull(division);
		System.out.println(division);
	}
	
	@Test
	public void test_02_816_prm() throws Exception {
		String url = "https://resultats.ffbb.com/championnat/b5e6211eeed6.html?r=200000002780886&d=200000002862119";
		Division division = extractor.doExtract(URI.create(url));
		Assert.assertNotNull(division);
		System.out.println(division);
	}
	
	@Test
	public void test_03_816_u9m_bleu_a() throws Exception {
		String url = "https://resultats.ffbb.com/championnat/b5e6211f1931.html?r=200000002791729&d=200000002873602";
		Division division = extractor.doExtract(URI.create(url));
		Assert.assertNotNull(division);
		System.out.println(division);
	}
	
}
