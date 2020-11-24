package com.ffbb.resultats.tests;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.ffbb.resultats.api.Organisation;
import com.ffbb.resultats.api.Salle;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SalleExtraction extends ResultatsExtraction {

	@Test
	public void test_01_club() throws Exception {
		Organisation organisation = extractor.getOrganisation("2226");
		Assert.assertNotNull(organisation);
		Salle salle = extractor.getSalle(organisation);
		Assert.assertNotNull(salle);
		Assert.assertNotNull(salle.getId());
		Assert.assertNotNull(salle.getDénomination());
		Assert.assertNotNull(salle.getCodePostal());
		Assert.assertNotNull(salle.getVille());
		Assert.assertNotNull(salle.getLatitude());
		Assert.assertNotNull(salle.getLongitude());
	}
	
	@Test
	public void test_02_comité() throws Exception {
		Organisation organisation = extractor.getOrganisation("816");
		Assert.assertNotNull(organisation);
		Salle salle = extractor.getSalle(organisation);
		Assert.assertNull(salle);
	}
	
	@Test
	public void test_03_ligue() throws Exception {
		Organisation organisation = extractor.getOrganisation("5d1");
		Assert.assertNotNull(organisation);
		Salle salle = extractor.getSalle(organisation);
		Assert.assertNull(salle);
	}
	
	@Test
	public void test_04_entente() throws Exception {
		Organisation organisation = extractor.getOrganisation("b5e6211d599a");
		Assert.assertNotNull(organisation);
		Salle salle = extractor.getSalle(organisation);
		Assert.assertNotNull(salle);
		Assert.assertNotNull(salle.getId());
		Assert.assertNotNull(salle.getDénomination());
		Assert.assertNotNull(salle.getCodePostal());
		Assert.assertNotNull(salle.getVille());
		Assert.assertNotNull(salle.getLatitude());
		Assert.assertNotNull(salle.getLongitude());
	}
	
	@Test
	public void test_05_club_pro() throws Exception {
		Organisation organisation = extractor.getOrganisation("21ac"); 
		Assert.assertNotNull(organisation);
		Salle salle = extractor.getSalle(organisation);
		Assert.assertNotNull(salle);
		Assert.assertNotNull(salle.getId());
		Assert.assertNotNull(salle.getDénomination());
		Assert.assertNotNull(salle.getCodePostal());
		Assert.assertNotNull(salle.getVille());
		Assert.assertNotNull(salle.getLatitude());
		Assert.assertNotNull(salle.getLongitude());
	}

	@Test
	public void test_06_federation() throws Exception {
		Organisation organisation = extractor.getOrganisation("5c0"); 
		Assert.assertNotNull(organisation);
		Salle salle = extractor.getSalle(organisation);
		Assert.assertNull(salle);
	}
	
}
