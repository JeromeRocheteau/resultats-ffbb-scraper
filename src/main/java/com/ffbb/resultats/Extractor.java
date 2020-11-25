package com.ffbb.resultats;

import java.net.URI;

import com.ffbb.resultats.api.Extractable;

public interface Extractor<T extends Extractable> {

	public T doExtract(URI uri) throws Exception;
	
}
