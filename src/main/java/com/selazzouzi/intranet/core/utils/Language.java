package com.selazzouzi.intranet.core.utils;

public enum Language {

	ENGLISH("ENGLISH"),
	DUTCH("DUTCH"),
    FRENCH("FRENCH"),
    GERMAN("GERMAN"),
    ITALIAN("ITALIAN");
	
	private final String text;

	/**
	 * @param text
	 */
	private Language(final String text) {
		this.text = text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return text;
	}
}
