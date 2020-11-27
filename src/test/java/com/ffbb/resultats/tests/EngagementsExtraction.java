package com.ffbb.resultats.tests;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.ffbb.resultats.api.Engagements;
import com.ffbb.resultats.api.Organisation;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EngagementsExtraction extends ResultatsExtraction {

	@Test
	public void test_01_club() throws Exception {
		Organisation organisation = extractor.getOrganisation("2226");
		Assert.assertNotNull(organisation);
		Engagements engagements = extractor.getEngagements(organisation);
		Assert.assertNotNull(engagements);
		Assert.assertEquals(25, engagements.size());
	}
	
}
