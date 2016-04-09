package com.selazzouzi.intranet.core.utils;

public enum RoleType {
	ADMIN("ROLE_ADMIN"), 
	USER("ROLE_USER"),
	DBA("ROLE_DBA");

	private final String text;

	/**
	 * @param text
	 */
	private RoleType(final String text) {
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
