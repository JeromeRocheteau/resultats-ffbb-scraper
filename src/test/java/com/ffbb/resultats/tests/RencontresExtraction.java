package com.ffbb.resultats.tests;

import java.text.SimpleDateFormat;
import java.util.Date;
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
	public void test_01() throws Exception {
		Organisation organisation = extractor.getOrganisation("2226");
		Assert.assertNotNull(organisation);
		List<Engagement> engagements = extractor.getEngagements(organisation);
		Assert.assertNotNull(engagements);
		for (Engagement engagement : engagements) {
			if (engagement.getCompétition().getType() == Compétition.Type.Championnat) {
				Championnat championnat = (Championnat) engagement.getCompétition();
				List<Rencontre> rencontres = extractor.getRencontres(organisation, championnat);
				Assert.assertNotNull(rencontres);
			}
		}
	}

	@Test
	public void test_02_begin() throws Exception {
		Date début = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2019-01-12 00:00");
		Organisation organisation = extractor.getOrganisation("2226");
		Assert.assertNotNull(organisation);
		List<Engagement> engagements = extractor.getEngagements(organisation);
		Assert.assertNotNull(engagements);
		for (Engagement engagement : engagements) {
			if (engagement.getCompétition().getType() == Compétition.Type.Championnat) {
				Championnat championnat = (Championnat) engagement.getCompétition();
				List<Rencontre> rencontres = extractor.getRencontres(organisation, championnat, début, null);
				Assert.assertNotNull(rencontres);
				if (rencontres.size() > 0) {
					if (rencontres.size() > 0) {
						for (Rencontre rencontre : rencontres) {
							Assert.assertTrue(rencontre.getHoraire().after(début));
						}
					}
				}
			}
		}
	}

	@Test
	public void test_03_end() throws Exception {
		Date fin = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2018-09-30 23:59");
		Organisation organisation = extractor.getOrganisation("2226");
		Assert.assertNotNull(organisation);
		List<Engagement> engagements = extractor.getEngagements(organisation);
		Assert.assertNotNull(engagements);
		for (Engagement engagement : engagements) {
			if (engagement.getCompétition().getType() == Compétition.Type.Championnat) {
				Championnat championnat = (Championnat) engagement.getCompétition();
				List<Rencontre> rencontres = extractor.getRencontres(organisation, championnat, null, fin);
				Assert.assertNotNull(rencontres);
				if (rencontres.size() > 0) {
					for (Rencontre rencontre : rencontres) {
						Assert.assertTrue(rencontre.getHoraire().before(fin));
					}
				}
			}
		}
	}

	@Test
	public void test_04_begin_end() throws Exception {
		Date début = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2019-01-12 00:00");
		Date fin = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2019-01-13 23:59");
		Organisation organisation = extractor.getOrganisation("2226");
		Assert.assertNotNull(organisation);
		List<Engagement> engagements = extractor.getEngagements(organisation);
		Assert.assertNotNull(engagements);
		for (Engagement engagement : engagements) {
			if (engagement.getCompétition().getType() == Compétition.Type.Championnat) {
				Championnat championnat = (Championnat) engagement.getCompétition();
				List<Rencontre> rencontres = extractor.getRencontres(organisation, championnat, début, fin);
				Assert.assertNotNull(rencontres);
				if (rencontres.size() > 0) {
					Assert.assertEquals(1, rencontres.size());
					for (Rencontre rencontre : rencontres) {
						Assert.assertTrue(rencontre.getHoraire().after(début));
						Assert.assertTrue(rencontre.getHoraire().before(fin));
					}
				}
			}
		}
	}
	
}
