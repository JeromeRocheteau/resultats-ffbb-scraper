package com.ffbb.resultats.tests;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.ffbb.resultats.api.Catégorie;
import com.ffbb.resultats.api.Compétition.Type;
import com.ffbb.resultats.api.Engagements;
import com.ffbb.resultats.api.Genre;
import com.ffbb.resultats.api.Niveau;
import com.ffbb.resultats.api.Organisation;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EngagementsExtraction extends ResultatsExtraction {

	@Test
	public void test_01_2226() throws Exception {
		Organisation organisation = extractor.getOrganisation("2226");
		Assert.assertNotNull(organisation);
		extractor.filtre();
		Engagements engagements = extractor.getEngagements(organisation);
		Assert.assertNotNull(engagements);
		Assert.assertEquals(25, engagements.size());
	}

	@Test
	public void test_02_2226() throws Exception {
		Organisation organisation = extractor.getOrganisation("2226");
		Assert.assertNotNull(organisation);
		extractor.filtre().types(Type.Championnat).genres(Genre.Féminin);
		Engagements engagements = extractor.getEngagements(organisation);
		Assert.assertNotNull(engagements);
		Assert.assertEquals(10, engagements.size());
	}

	@Test
	public void test_03_2226() throws Exception {
		Organisation organisation = extractor.getOrganisation("2226");
		Assert.assertNotNull(organisation);
		extractor.filtre().types(Type.Championnat).genres(Genre.Féminin).niveaux(Niveau.Départemental);
		Engagements engagements = extractor.getEngagements(organisation);
		Assert.assertNotNull(engagements);
		Assert.assertEquals(9, engagements.size());
	}

	@Test
	public void test_04_2226() throws Exception {
		Organisation organisation = extractor.getOrganisation("2226");
		Assert.assertNotNull(organisation);
		extractor.filtre().types(Type.Championnat).genres(Genre.Féminin).niveaux(Niveau.Régional);
		Engagements engagements = extractor.getEngagements(organisation);
		Assert.assertNotNull(engagements);
		Assert.assertEquals(1, engagements.size());
	}

	@Test
	public void test_05_2226() throws Exception {
		Organisation organisation = extractor.getOrganisation("2226");
		Assert.assertNotNull(organisation);
		extractor.filtre().types(Type.Championnat).catégories(Catégorie.U13).genres(Genre.Féminin);
		Engagements engagements = extractor.getEngagements(organisation);
		Assert.assertNotNull(engagements);
		Assert.assertEquals(3, engagements.size());
	}

	@Test
	public void test_06_2226() throws Exception {
		Organisation organisation = extractor.getOrganisation("2226");
		Assert.assertNotNull(organisation);
		extractor.filtre().types(Type.Championnat).catégories(Catégorie.U13).genres(Genre.Féminin).niveaux(Niveau.Départemental).poules("Accès Région");
		Engagements engagements = extractor.getEngagements(organisation);
		Assert.assertNotNull(engagements);
		Assert.assertEquals(1, engagements.size());
	}

	@Test
	public void test_07_2226() throws Exception {
		Organisation organisation = extractor.getOrganisation("2226");
		Assert.assertNotNull(organisation);
		extractor.filtre().types(Type.Championnat).catégories(Catégorie.U13).genres(Genre.Féminin).niveaux(Niveau.Départemental).poules("D2 - Poule D");
		Engagements engagements = extractor.getEngagements(organisation);
		Assert.assertNotNull(engagements);
		Assert.assertEquals(1, engagements.size());
	}

	@Test
	public void test_08_2226() throws Exception {
		Organisation organisation = extractor.getOrganisation("2226");
		Assert.assertNotNull(organisation);
		extractor.filtre().types(Type.Championnat).catégories(Catégorie.U13).genres(Genre.Féminin).niveaux(Niveau.Départemental).divisions(2).poules("D");
		Engagements engagements = extractor.getEngagements(organisation);
		Assert.assertNotNull(engagements);
		Assert.assertEquals(1, engagements.size());
	}

	@Test
	public void test_09_2226() throws Exception {
		Organisation organisation = extractor.getOrganisation("2226");
		Assert.assertNotNull(organisation);
		extractor.filtre().types(Type.Championnat).catégories(Catégorie.U13).genres(Genre.Féminin).niveaux(Niveau.Départemental).divisions(3).poules("C");
		Engagements engagements = extractor.getEngagements(organisation);
		Assert.assertNotNull(engagements);
		Assert.assertEquals(1, engagements.size());
	}

	@Test
	public void test_10_2226() throws Exception {
		Organisation organisation = extractor.getOrganisation("2226");
		Assert.assertNotNull(organisation);
		extractor.filtre().types(Type.Championnat).catégories(Catégorie.U13).genres(Genre.Féminin).niveaux(Niveau.Départemental).divisions(2, 3);
		Engagements engagements = extractor.getEngagements(organisation);
		Assert.assertNotNull(engagements);
		Assert.assertEquals(2, engagements.size());
	}
	
}
