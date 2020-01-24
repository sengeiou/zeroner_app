package org.apache.commons.cli;

import java.util.Iterator;
import java.util.List;

public class MissingOptionException extends ParseException {
    private List missingOptions;

    public MissingOptionException(String message) {
        super(message);
    }

    public MissingOptionException(List missingOptions2) {
        this(createMessage(missingOptions2));
        this.missingOptions = missingOptions2;
    }

    public List getMissingOptions() {
        return this.missingOptions;
    }

    private static String createMessage(List missingOptions2) {
        StringBuffer buff = new StringBuffer("Missing required option");
        buff.append(missingOptions2.size() == 1 ? "" : "s");
        buff.append(": ");
        Iterator it = missingOptions2.iterator();
        while (it.hasNext()) {
            buff.append(it.next());
            if (it.hasNext()) {
                buff.append(", ");
            }
        }
        return buff.toString();
    }
}
