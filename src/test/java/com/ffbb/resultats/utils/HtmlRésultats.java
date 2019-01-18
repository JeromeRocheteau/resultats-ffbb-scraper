package com.ffbb.resultats.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.ffbb.resultats.api.Rencontre;
import com.ffbb.resultats.tests.ResultatsExtraction;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HtmlRésultats extends ResultatsExtraction {

	@Test
	public void test() throws Exception {
		Date début = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2019-01-12 00:00");
		Date fin = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2019-01-13 23:59");
		Organisation organisation = extractor.getOrganisation("2226");
		Assert.assertNotNull(organisation);
		List<Engagement> engagements = extractor.getEngagements(organisation);
		Assert.assertNotNull(engagements);
		for (Engagement engagement : engagements) {
			if (engagement.getCompétition().getType() == Compétition.Type.Championnat) {
				Championnat championnat = (Championnat) engagement.getCompétition();
				System.out.println(championnat);
				List<Rencontre> rencontres = extractor.getRencontres(organisation, championnat, début, fin);
				if (rencontres.size() > 0 && rencontres.size() == 1) {
					System.out.println(rencontres.get(0));
					Classement classement = extractor.getClassement(organisation, championnat);
					System.out.println(classement);
				}
			}
		}
	}
	
}
