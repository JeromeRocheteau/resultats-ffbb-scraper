package com.ffbb.resultats.tests;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.ffbb.resultats.ChampionnatExtractor;
import com.ffbb.resultats.RencontresExtractor;
import com.ffbb.resultats.api.Championnat;
import com.ffbb.resultats.api.Compétition;
import com.ffbb.resultats.api.Engagement;
import com.ffbb.resultats.api.Organisation;
import com.ffbb.resultats.api.Rencontre;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RencontreExtract {

	private static final String URL = "https://resultats.ffbb.com/championnat/b5e6211f1950.html";
	
	private ChampionnatExtractor extractor;
	
	public RencontreExtract() {
		extractor = new ChampionnatExtractor();
	}
	
	@Test
	public void test_01() throws Exception {
		Championnat championnat = extractor.doExtract(URI.create(URL));
		Assert.assertNotNull(championnat);
	}
	
}
