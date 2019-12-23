package com.ffbb.resultats.api;

import java.net.URI;

import com.ffbb.resultats.Extractable;

public class Salle implements Extractable, Comparable<Salle> {

	private String id;
	
	private Float latitude;
	
	private Float longitude;
	
	private String dénomination;
	
	private String adresse;
	
	private String codePostal;
	
	private String ville;

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

	public String getDénomination() {
		return dénomination;
	}

	public void setDénomination(String dénomination) {
		this.dénomination = dénomination;
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

	public String getId() {
		return id;
	}

	public Salle(String id) {
		this.id = id;
	}
	
	public Salle(String id, Float latitude, Float longitude, String dénomnation, String adresse, String codePostal, String ville) {
		this(id);
		this.latitude = latitude;
		this.longitude = longitude;
		this.dénomination = dénomnation;
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
		return dénomination + " " + adresse + " "  + codePostal + " " + ville;
	}

	@Override
	public int compareTo(Salle salle) {
		return id.compareTo(salle.getId());
	}

}
