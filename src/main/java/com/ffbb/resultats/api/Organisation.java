package com.ffbb.resultats.api;

import java.net.URI;
import java.util.List;

import com.ffbb.resultats.Extractable;

public class Organisation implements Extractable {
	
	public enum Type {Club, Entente, ClubPro, Comité, Ligue, Fédération};
	
	private String code;
	
	private Type type;

	private Long id;
	
	private String ffbb;
	
	private String name;
	
	private Engagements engagements;

	public String getCode() {
		return code;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFfbb() {
		return ffbb;
	}

	public void setFfbb(String ffbb) {
		this.ffbb = ffbb;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Engagement> getEngagements() {
		return engagements;
	}

	public Organisation(String code) {
		super();
		this.code = code;
		this.engagements = new Engagements(this);
	}

	public URI getURI() {
		return URI.create("http://resultats.ffbb.com/organisation/" + code + ".html");
	}
	
	@Override
	public String toString() {
		return name + " - " + ffbb + " - " + type.name();
	}
	
}
