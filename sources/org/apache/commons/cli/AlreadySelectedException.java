package org.apache.commons.cli;

public class AlreadySelectedException extends ParseException {
    private OptionGroup group;
    private Option option;

    public AlreadySelectedException(String message) {
        super(message);
    }

    public AlreadySelectedException(OptionGroup group2, Option option2) {
        this(new StringBuffer().append("The option '").append(option2.getKey()).append("' was specified but an option from this group ").append("has already been selected: '").append(group2.getSelected()).append("'").toString());
        this.group = group2;
        this.option = option2;
    }

    public OptionGroup getOptionGroup() {
        return this.group;
    }

    public Option getOption() {
        return this.option;
    }
}
