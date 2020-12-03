package com.ffbb.resultats.db;

import java.io.InputStream;
import java.sql.PreparedStatement;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.StringBuilderWriter;

public abstract class Adapter<T> {
	
	private T object;
	
	public final T getObject() {
		return this.object;
	}
	
	public final void setObject(T object) {
		this.object = object;
	}
	
	public final boolean isValidated() {
		return object != null;
	}
	
	public abstract String getScriptPath();
	
	public abstract void setParameters(PreparedStatement statement) throws Exception;

	protected String getContent(String name) throws Exception {
		InputStream inputStream = this.getClass().getResourceAsStream(name);
		if (inputStream == null) {
			throw new NullPointerException(name);
		} else {
			StringBuilderWriter writer = new StringBuilderWriter();
			IOUtils.copy(inputStream, writer, "UTF-8");
			return writer.toString();
		}
	}
	
}
