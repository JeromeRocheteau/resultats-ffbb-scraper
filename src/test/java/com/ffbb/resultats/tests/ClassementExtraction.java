package com.ffbb.resultats.tests;

import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.ffbb.resultats.api.Championnat;
import com.ffbb.resultats.api.Classement;
import com.ffbb.resultats.api.Compétition;
import com.ffbb.resultats.api.Engagement;
import com.ffbb.resultats.api.Organisation;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClassementExtraction extends ResultatsExtraction {

	@Test
	public void test_01() throws Exception {
		Organisation organisation = extractor.getOrganisation("2226");
		Assert.assertNotNull(organisation);
		List<Engagement> engagements = extractor.getEngagements(organisation);
		Assert.assertNotNull(engagements);
		for (Engagement engagement : engagements) {
			if (engagement.getCompétition().getType() == Compétition.Type.Championnat) {
				Championnat championnat = (Championnat) engagement.getCompétition();
				Classement classement = extractor.getClassement(organisation, championnat);
				System.out.println(championnat);
				System.out.println(classement);
			}
		}
	}
	
}
