package org.apache.commons.cli;

public class UnrecognizedOptionException extends ParseException {
    private String option;

    public UnrecognizedOptionException(String message) {
        super(message);
    }

    public UnrecognizedOptionException(String message, String option2) {
        this(message);
        this.option = option2;
    }

    public String getOption() {
        return this.option;
    }
}
