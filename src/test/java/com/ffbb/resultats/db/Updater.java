package com.ffbb.resultats.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class Updater<T> extends Adapter<T> {
	
	public abstract T getResult(int count, ResultSet resultSet) throws Exception;

	protected T doUpdate(Connection connection) throws Exception {
		PreparedStatement statement = null;
		String path = this.getScriptPath();
		try {
			String query = this.getContent(path);
			statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			this.setParameters(statement);
			int count = statement.executeUpdate();
			connection.commit();
			ResultSet resultSet = statement.getGeneratedKeys();
			T result = this.getResult(count, resultSet);
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
	}
	
}
