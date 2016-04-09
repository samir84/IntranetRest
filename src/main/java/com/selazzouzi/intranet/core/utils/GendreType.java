package com.selazzouzi.intranet.core.utils;

public enum GendreType {

	Male("M"),
	Female("F");
	
	private final String text;

	/**
	 * @param text
	 */
	private GendreType(final String text) {
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
