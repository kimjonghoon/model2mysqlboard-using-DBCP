package net.java_school.commons;

public enum WebContants {
	lineSeparator (System.getProperty("line.separator"));

	public String value;

	WebContants (String value) {
		this.value = value;
	}

}