package com.selazzouzi.intranet.core.utils;

public enum AbsenceType {
	VACATION("Vacation"), 
	HOLIDAY("Holiday"),
	SICK("Sick"),
	LEAVE_OF_ABSENCE("Leave of absence"),
	PARENTAL("Parental"),
	PATERNITY_LEAVE("Paternity leave");

	private final String text;

	/**
	 * @param text
	 */
	private AbsenceType(final String text) {
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
