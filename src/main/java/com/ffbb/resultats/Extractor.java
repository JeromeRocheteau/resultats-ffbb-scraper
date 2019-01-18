package com.ffbb.resultats;

import java.net.URI;

public interface Extractor<T> {

	public T doExtract(URI uri) throws Exception;
	
}
