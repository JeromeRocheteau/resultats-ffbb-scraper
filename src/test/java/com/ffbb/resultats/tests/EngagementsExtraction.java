package com.ffbb.resultats.tests;

import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.ffbb.resultats.api.Engagement;
import com.ffbb.resultats.api.Organisation;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EngagementsExtraction extends ResultatsExtraction {

	@Test
	public void test_01_club() throws Exception {
		Organisation organisation = extractor.getOrganisation("2226");
		Assert.assertNotNull(organisation);
		List<Engagement> engagements = extractor.getEngagements(organisation);
		Assert.assertNotNull(engagements);
		Assert.assertEquals(58, engagements.size());
	}
	
}
