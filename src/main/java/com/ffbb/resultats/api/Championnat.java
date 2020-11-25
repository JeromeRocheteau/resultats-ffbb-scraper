package com.ffbb.resultats.api;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

public class Championnat extends Compétition implements Identifiable, Encodable, Extractable {
	
	private List<Équipe> équipes;
	
	public List<Équipe> getÉquipes() {
		return équipes;
	}

	public Championnat(Long id, String code, String nom, Organisation organisateur) {
		this.setId(id);
		this.setCode(code);
		this.setNom(nom);
		this.setOrganisateur(organisateur);
		this.setType(Type.Championnat);
		this.équipes= new LinkedList<Équipe>();
	}

	@Override
	public URI getURI() {
		String link = "http://resultats.ffbb.com/championnat/" + this.getCode() + ".html";
		return URI.create(link);
	}
	
}
