package com.ffbb.resultats.db;

public abstract class Controller<T,U,V> implements Finder<T> {

	private Reader<T,U> reader;
	
	private Updater<T,V> updater;

	public Reader<T,U> getReader() {
		return reader;
	}

	public Updater<T,V> getUpdater() {
		return updater;
	}

	public Controller(Reader<T, U> reader, Updater<T, V> updater) {
		super();
		this.reader = reader;
		this.updater = updater;
	}
	
}
