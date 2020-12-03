package com.ffbb.resultats.db;

import java.net.URI;

public interface Finder<T> {

	public T doFind(URI uri);
	
}
