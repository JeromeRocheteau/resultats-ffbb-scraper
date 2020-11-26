package com.ffbb.resultats.core;

public class ExtractorException extends Exception {

	private static final long serialVersionUID = 201901172012001L;

	private int code;
	
	public ExtractorException(int code) {
		this.code = code;
	}
	
}
