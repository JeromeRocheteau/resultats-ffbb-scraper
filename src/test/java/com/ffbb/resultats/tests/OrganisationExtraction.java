package com.ffbb.resultats.tests;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.ffbb.resultats.api.Organisation;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrganisationExtraction extends ResultatsExtraction {

	@Test
	public void test_01_club() throws Exception {
		Organisation org = extractor.getOrganisation("2226");
		Assert.assertNotNull(org);
		Assert.assertNotNull(org.getCode());
		Assert.assertNotNull(org.getId());
		Assert.assertNotNull(org.getName());
		Assert.assertNotNull(org.getFfbb());
		Assert.assertNotNull(org.getType());
		Assert.assertEquals(org.getCode(), "2226");
		Assert.assertEquals(org.getId().longValue(), 8742L);
		Assert.assertEquals(org.getName(), "NORT SUR ERDRE AC LES TOUCHES BASKET");
		Assert.assertEquals(org.getFfbb(), "PDL0044146");
		Assert.assertEquals(org.getType(), Organisation.Type.Club);
	}
	
	@Test
	public void test_02_comité() throws Exception {
		Organisation org = extractor.getOrganisation("816");
		Assert.assertNotNull(org);
		Assert.assertNotNull(org.getCode());
		Assert.assertNotNull(org.getId());
		Assert.assertNotNull(org.getName());
		Assert.assertNotNull(org.getFfbb());
		Assert.assertNotNull(org.getType());
		Assert.assertEquals(org.getCode(), "816");
		Assert.assertEquals(org.getId().longValue(), 2070L);
		Assert.assertEquals(org.getName(), "COMITE DE LA LOIRE-ATLANTIQUE DE BASKET-BALL");
		Assert.assertEquals(org.getFfbb(), "0044");
		Assert.assertEquals(org.getType(), Organisation.Type.Comité);
	}
	
	@Test
	public void test_03_ligue() throws Exception {
		Organisation org = extractor.getOrganisation("5d1");
		Assert.assertNotNull(org);
		Assert.assertNotNull(org.getCode());
		Assert.assertNotNull(org.getId());
		Assert.assertNotNull(org.getName());
		Assert.assertNotNull(org.getFfbb());
		Assert.assertNotNull(org.getType());
		Assert.assertEquals(org.getCode(), "5d1");
		Assert.assertEquals(org.getId().longValue(), 1489L);
		Assert.assertEquals(org.getName(), "LIGUE REGIONALE DES PAYS-DE-LA-LOIRE DE BASKET-BALL");
		Assert.assertEquals(org.getFfbb(), "PDL");
		Assert.assertEquals(org.getType(), Organisation.Type.Ligue);
	}
	
	@Test
	public void test_04_entente() throws Exception {
		Organisation org = extractor.getOrganisation("b5e6211d599a");
		Assert.assertNotNull(org);
		Assert.assertNotNull(org.getCode());
		Assert.assertNotNull(org.getId());
		Assert.assertNotNull(org.getName());
		Assert.assertNotNull(org.getFfbb());
		Assert.assertNotNull(org.getType());
		Assert.assertEquals(org.getCode(), "b5e6211d599a");
		Assert.assertEquals(org.getId().longValue(), 200000002677146L);
		Assert.assertEquals(org.getName(), "EN - Arthon / Pornic St Michel");
		Assert.assertEquals(org.getFfbb(), "PDL0044003");
		Assert.assertEquals(org.getType(), Organisation.Type.Entente);
	}
	
	@Test
	public void test_05_club_pro() throws Exception {
		Organisation org = extractor.getOrganisation("21ac"); 
		Assert.assertNotNull(org);
		Assert.assertNotNull(org.getCode());
		Assert.assertNotNull(org.getId());
		Assert.assertNotNull(org.getName());
		Assert.assertNotNull(org.getFfbb());
		Assert.assertNotNull(org.getType());
		Assert.assertEquals(org.getCode(), "21ac");
		Assert.assertEquals(org.getId().longValue(), 8620L);
		Assert.assertEquals(org.getName(), "REZE BASKET 44");
		Assert.assertEquals(org.getFfbb(), "PDL0044004");
		Assert.assertEquals(org.getType(), Organisation.Type.ClubPro);
	}

	@Test
	public void test_06_federation() throws Exception {
		Organisation org = extractor.getOrganisation("5c0");
		Assert.assertNotNull(org);
		Assert.assertNotNull(org.getCode());
		Assert.assertNotNull(org.getId());
		Assert.assertNotNull(org.getName());
		Assert.assertNotNull(org.getFfbb());
		Assert.assertNotNull(org.getType());
		Assert.assertEquals(org.getCode(), "5c0");
		Assert.assertEquals(org.getId().longValue(), 1472L);
		Assert.assertEquals(org.getName(), "FÉDÉRATION FRANCAISE BASKET-BALL");
		Assert.assertEquals(org.getFfbb(), "FEDE");
		Assert.assertEquals(org.getType(), Organisation.Type.Fédération);
	}	

}
