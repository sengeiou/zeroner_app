package org.apache.commons.cli;

public class MissingArgumentException extends ParseException {
    private Option option;

    public MissingArgumentException(String message) {
        super(message);
    }

    public MissingArgumentException(Option option2) {
        this(new StringBuffer().append("Missing argument for option: ").append(option2.getKey()).toString());
        this.option = option2;
    }

    public Option getOption() {
        return this.option;
    }
}
