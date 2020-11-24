package com.ffbb.resultats.tests;

import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.ffbb.resultats.api.Appartenance;
import com.ffbb.resultats.api.Appartenance.Type;
import com.ffbb.resultats.api.Organisation;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppartenancesExtraction extends ResultatsExtraction {

	@Test
	public void test_01_club() throws Exception {
		Organisation organisation = extractor.getOrganisation("2226");
		Assert.assertNotNull(organisation);
		List<Appartenance> appartenaces = extractor.getAppartenances(organisation);
		Assert.assertNotNull(appartenaces);
		Assert.assertEquals(2, appartenaces.size());
		Assert.assertEquals(Type.Ligue, appartenaces.get(0).getType());
		Assert.assertEquals(Type.Comité, appartenaces.get(1).getType());
	}
	
	@Test
	public void test_02_comité() throws Exception {
		Organisation organisation = extractor.getOrganisation("816");
		Assert.assertNotNull(organisation);
		List<Appartenance> appartenaces = extractor.getAppartenances(organisation);
		Assert.assertNotNull(appartenaces);
		Assert.assertEquals(0, appartenaces.size());
	}
	
	@Test
	public void test_03_ligue() throws Exception {
		Organisation organisation = extractor.getOrganisation("5d1");
		Assert.assertNotNull(organisation);
		List<Appartenance> appartenaces = extractor.getAppartenances(organisation);
		Assert.assertNotNull(appartenaces);
		Assert.assertEquals(0, appartenaces.size());
	}
	
	@Test
	public void test_04_entente() throws Exception {
		Organisation organisation = extractor.getOrganisation("b5e6211d599a");
		Assert.assertNotNull(organisation);
		List<Appartenance> appartenaces = extractor.getAppartenances(organisation);
		Assert.assertNotNull(appartenaces);
		Assert.assertEquals(2, appartenaces.size());
		Assert.assertEquals(Type.Ligue, appartenaces.get(0).getType());
		Assert.assertEquals(Type.Comité, appartenaces.get(1).getType());
	}
	
	@Test
	public void test_05_club_pro() throws Exception {
		Organisation organisation = extractor.getOrganisation("21ac"); 
		Assert.assertNotNull(organisation);
		List<Appartenance> appartenaces = extractor.getAppartenances(organisation);
		Assert.assertNotNull(appartenaces);
		Assert.assertEquals(2, appartenaces.size());
		Assert.assertEquals(Type.Ligue, appartenaces.get(0).getType());
		Assert.assertEquals(Type.Comité, appartenaces.get(1).getType());
	}

	@Test
	public void test_06_federation() throws Exception {
		Organisation organisation = extractor.getOrganisation("5c0"); 
		Assert.assertNotNull(organisation);
		List<Appartenance> appartenaces = extractor.getAppartenances(organisation);
		Assert.assertNotNull(appartenaces);
		Assert.assertEquals(0, appartenaces.size());
	}
	
}
