package com.ffbb.resultats.tests;

import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.ffbb.resultats.api.Championnat;
import com.ffbb.resultats.api.Compétition;
import com.ffbb.resultats.api.Engagement;
import com.ffbb.resultats.api.Organisation;
import com.ffbb.resultats.api.Rencontre;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RencontresExtraction extends ResultatsExtraction {

	@Test
	public void test_01_club() throws Exception {
		Organisation organisation = extractor.getOrganisation("2226");
		Assert.assertNotNull(organisation);
		List<Engagement> engagements = extractor.getEngagements(organisation);
		Assert.assertNotNull(engagements);
		for (Engagement engagement : engagements) {
			if (engagement.getCompétition().getType() == Compétition.Type.Championnat) {
				String id = engagement.getCompétition().getParamètres().getId();
				if (id.equals("b5e6211e9bf9")) {
					System.out.println(engagement.getCompétition().toString());
					Championnat championnat = (Championnat) engagement.getCompétition();
					List<Rencontre> rencontres = extractor.getRencontres(organisation, championnat); 
				}
			}
		}
	}
	
}
