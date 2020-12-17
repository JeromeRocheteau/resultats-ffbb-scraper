package com.ffbb.resultats.db;

import java.net.URI;
import java.sql.Connection;

public interface Controller<T> {

	public T doFind(Connection connection, URI uri) throws Exception;
		
	public void doSave(Connection connection, T resource) throws Exception;
	
}
