package com.ffbb.resultats.api;

import java.net.URI;

public class Salle implements Identifiable, Extractable, Comparable<Salle> {

	private Long id;
	
	private Float latitude;
	
	private Float longitude;
	
	private String nom;
	
	private String adresse;
	
	private String codePostal;
	
	private String ville;
	
	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public Salle(Long id) {
		this.id = id;
	}
	
	public Salle(Long id, Float latitude, Float longitude, String nom, String adresse, String codePostal, String ville) {
		this(id);
		this.latitude = latitude;
		this.longitude = longitude;
		this.nom = nom;
		this.adresse = adresse;
		this.codePostal = codePostal;
		this.ville = ville;
	}

	@Override
	public URI getURI() {
		return URI.create("https://resultats.ffbb.com/here/here_popup.php?id=" + id);
	}

	@Override
	public String toString() {
		return id + " " + nom + " " + adresse + " "  + codePostal + " " + ville + "(" + latitude + "," + longitude + ")";
	}

	@Override
	public int compareTo(Salle salle) {
		return id.compareTo(salle.getId());
	}

	@Override
	public boolean equals(Object object) {
		try {
			Salle salle = (Salle) object;
			return this.compareTo(salle) == 0;
		} catch (Exception e) {
			return false;
		}
	}

}
