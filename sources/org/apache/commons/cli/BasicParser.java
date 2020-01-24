package org.apache.commons.cli;

public class BasicParser extends Parser {
    /* access modifiers changed from: protected */
    public String[] flatten(Options options, String[] arguments, boolean stopAtNonOption) {
        return arguments;
    }
}
