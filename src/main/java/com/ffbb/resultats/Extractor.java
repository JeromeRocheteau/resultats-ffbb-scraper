package com.ffbb.resultats;

import java.net.URI;

public interface Extractor<T extends Extractable> {

	public T doExtract(URI uri) throws Exception;
	
}
