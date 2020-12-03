package com.ffbb.resultats.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class Reader<T,V> extends Adapter<T> {
	
	public abstract V getResult(ResultSet resultSet) throws Exception;
	
	public V doRead(Connection connection) throws Exception {
		if (this.isValidated()) {
			PreparedStatement statement = null;
			String path = this.getScriptPath();
			try {
				String query = this.getContent(path);
				statement = connection.prepareStatement(query);
				this.setParameters(statement);
				ResultSet resultSet = statement.executeQuery();
				connection.commit();
				V result = this.getResult(resultSet);
				resultSet.close();
				return result;
			} catch (Exception e) {
				try {
					connection.rollback();
					throw new Exception(e);
				} catch (Exception ex) {
					throw new Exception(ex.getMessage(), e);
				}
			} finally {
			   try {
					statement.close();
			   } catch (Exception e) {
				   throw new Exception(path, e);
			   }
			}			
		} else {
			throw new Exception("validation violation of class " + this.getClass().getCanonicalName());
		}
	}

}
