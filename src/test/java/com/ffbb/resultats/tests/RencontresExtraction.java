package com.ffbb.resultats.tests;

import java.net.URI;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.ffbb.resultats.api.Division;
import com.ffbb.resultats.api.Journée;
import com.ffbb.resultats.api.Rencontres;
import com.ffbb.resultats.core.DivisionExtractor;
import com.ffbb.resultats.core.RencontresExtractor;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RencontresExtraction extends ResultatsExtraction {

	private DivisionExtractor divisionExtractor;
	
	private RencontresExtractor rencontresExtractor;
	
	public RencontresExtraction() {
		divisionExtractor = new DivisionExtractor();
		rencontresExtractor = new RencontresExtractor();
	}
	
	@Test
	public void test_01_816_u13f_acces_region_j1() throws Exception {
		String url = "https://resultats.ffbb.com/championnat/b5e6211f1950.html?r=200000002791760&d=200000002873755";
		Division division = divisionExtractor.doExtract(URI.create(url));
		Assert.assertNotNull(division);
		Journée journée = new Journée(1, division);
		String link = "https://resultats.ffbb.com/championnat/rencontres/" + division.getCode() + Integer.toHexString(journée.getNuméro()) + ".html";
		Rencontres rencontres = rencontresExtractor.doExtract(URI.create(link));
		Assert.assertNotNull(rencontres);
		Assert.assertEquals(3, rencontres.size());
		rencontres.forEach(rencontre -> System.out.println(rencontre));
	}
	
	@Test
	public void test_02_816_prm_j24() throws Exception {
		String url = "https://resultats.ffbb.com/championnat/b5e6211eeed6.html?r=200000002780886&d=200000002862119";
		Division division = divisionExtractor.doExtract(URI.create(url));
		Assert.assertNotNull(division);
		Journée journée = new Journée(24, division);
		String link = "https://resultats.ffbb.com/championnat/rencontres/" + division.getCode() + Integer.toHexString(journée.getNuméro()) + ".html";
		Rencontres rencontres = rencontresExtractor.doExtract(URI.create(link));
		Assert.assertNotNull(rencontres);
		Assert.assertEquals(7, rencontres.size());
		rencontres.forEach(rencontre -> System.out.println(rencontre));
	}
	
	@Test
	public void test_02_816_u9m_a_j10() throws Exception {
		String url = "https://resultats.ffbb.com/championnat/b5e6211f1931.html?r=200000002791729&d=200000002873608";
		Division division = divisionExtractor.doExtract(URI.create(url));
		Assert.assertNotNull(division);
		Journée journée = new Journée(10, division);
		String link = "https://resultats.ffbb.com/championnat/rencontres/" + division.getCode() + Integer.toHexString(journée.getNuméro()) + ".html";
		Rencontres rencontres = rencontresExtractor.doExtract(URI.create(link));
		Assert.assertNotNull(rencontres);
		Assert.assertEquals(2, rencontres.size());
		rencontres.forEach(rencontre -> System.out.println(rencontre));
	}
	
}
