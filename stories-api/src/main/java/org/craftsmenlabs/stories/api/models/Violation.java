package org.craftsmenlabs.stories.api.models;

public interface Violation
{
	ViolationType getViolationType();

	String getCause();

	default String toColoredString(){
        StringBuilder s = new StringBuilder();
        s.append(ANSI_RED);
        s.append(getViolationType());
        s.append(", ");
        s.append(getCause());
        s.append(ANSI_RESET);


		return s.toString();
	}


    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

}
