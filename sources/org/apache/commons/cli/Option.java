package org.apache.commons.cli;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Option implements Cloneable, Serializable {
    public static final int UNINITIALIZED = -1;
    public static final int UNLIMITED_VALUES = -2;
    private static final long serialVersionUID = 1;
    private String argName;
    private String description;
    private String longOpt;
    private int numberOfArgs;
    private String opt;
    private boolean optionalArg;
    private boolean required;
    private Object type;
    private List values;
    private char valuesep;

    public Option(String opt2, String description2) throws IllegalArgumentException {
        this(opt2, null, false, description2);
    }

    public Option(String opt2, boolean hasArg, String description2) throws IllegalArgumentException {
        this(opt2, null, hasArg, description2);
    }

    public Option(String opt2, String longOpt2, boolean hasArg, String description2) throws IllegalArgumentException {
        this.argName = HelpFormatter.DEFAULT_ARG_NAME;
        this.numberOfArgs = -1;
        this.values = new ArrayList();
        OptionValidator.validateOption(opt2);
        this.opt = opt2;
        this.longOpt = longOpt2;
        if (hasArg) {
            this.numberOfArgs = 1;
        }
        this.description = description2;
    }

    public int getId() {
        return getKey().charAt(0);
    }

    /* access modifiers changed from: 0000 */
    public String getKey() {
        if (this.opt == null) {
            return this.longOpt;
        }
        return this.opt;
    }

    public String getOpt() {
        return this.opt;
    }

    public Object getType() {
        return this.type;
    }

    public void setType(Object type2) {
        this.type = type2;
    }

    public String getLongOpt() {
        return this.longOpt;
    }

    public void setLongOpt(String longOpt2) {
        this.longOpt = longOpt2;
    }

    public void setOptionalArg(boolean optionalArg2) {
        this.optionalArg = optionalArg2;
    }

    public boolean hasOptionalArg() {
        return this.optionalArg;
    }

    public boolean hasLongOpt() {
        return this.longOpt != null;
    }

    public boolean hasArg() {
        return this.numberOfArgs > 0 || this.numberOfArgs == -2;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description2) {
        this.description = description2;
    }

    public boolean isRequired() {
        return this.required;
    }

    public void setRequired(boolean required2) {
        this.required = required2;
    }

    public void setArgName(String argName2) {
        this.argName = argName2;
    }

    public String getArgName() {
        return this.argName;
    }

    public boolean hasArgName() {
        return this.argName != null && this.argName.length() > 0;
    }

    public boolean hasArgs() {
        return this.numberOfArgs > 1 || this.numberOfArgs == -2;
    }

    public void setArgs(int num) {
        this.numberOfArgs = num;
    }

    public void setValueSeparator(char sep) {
        this.valuesep = sep;
    }

    public char getValueSeparator() {
        return this.valuesep;
    }

    public boolean hasValueSeparator() {
        return this.valuesep > 0;
    }

    public int getArgs() {
        return this.numberOfArgs;
    }

    /* access modifiers changed from: 0000 */
    public void addValueForProcessing(String value) {
        switch (this.numberOfArgs) {
            case -1:
                throw new RuntimeException("NO_ARGS_ALLOWED");
            default:
                processValue(value);
                return;
        }
    }

    private void processValue(String value) {
        if (hasValueSeparator()) {
            char sep = getValueSeparator();
            int index = value.indexOf(sep);
            while (index != -1 && this.values.size() != this.numberOfArgs - 1) {
                add(value.substring(0, index));
                value = value.substring(index + 1);
                index = value.indexOf(sep);
            }
        }
        add(value);
    }

    private void add(String value) {
        if (this.numberOfArgs <= 0 || this.values.size() <= this.numberOfArgs - 1) {
            this.values.add(value);
            return;
        }
        throw new RuntimeException("Cannot add value, list full.");
    }

    public String getValue() {
        if (hasNoValues()) {
            return null;
        }
        return (String) this.values.get(0);
    }

    public String getValue(int index) throws IndexOutOfBoundsException {
        if (hasNoValues()) {
            return null;
        }
        return (String) this.values.get(index);
    }

    public String getValue(String defaultValue) {
        String value = getValue();
        return value != null ? value : defaultValue;
    }

    public String[] getValues() {
        if (hasNoValues()) {
            return null;
        }
        return (String[]) this.values.toArray(new String[this.values.size()]);
    }

    public List getValuesList() {
        return this.values;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer().append("[ option: ");
        buf.append(this.opt);
        if (this.longOpt != null) {
            buf.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(this.longOpt);
        }
        buf.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        if (hasArgs()) {
            buf.append("[ARG...]");
        } else if (hasArg()) {
            buf.append(" [ARG]");
        }
        buf.append(" :: ").append(this.description);
        if (this.type != null) {
            buf.append(" :: ").append(this.type);
        }
        buf.append(" ]");
        return buf.toString();
    }

    private boolean hasNoValues() {
        return this.values.isEmpty();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Option option = (Option) o;
        if (this.opt == null ? option.opt != null : !this.opt.equals(option.opt)) {
            return false;
        }
        if (this.longOpt != null) {
            if (this.longOpt.equals(option.longOpt)) {
                return true;
            }
        } else if (option.longOpt == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result;
        int i = 0;
        if (this.opt != null) {
            result = this.opt.hashCode();
        } else {
            result = 0;
        }
        int i2 = result * 31;
        if (this.longOpt != null) {
            i = this.longOpt.hashCode();
        }
        return i2 + i;
    }

    public Object clone() {
        try {
            Option option = (Option) super.clone();
            option.values = new ArrayList(this.values);
            return option;
        } catch (CloneNotSupportedException cnse) {
            throw new RuntimeException(new StringBuffer().append("A CloneNotSupportedException was thrown: ").append(cnse.getMessage()).toString());
        }
    }

    /* access modifiers changed from: 0000 */
    public void clearValues() {
        this.values.clear();
    }

    public boolean addValue(String value) {
        throw new UnsupportedOperationException("The addValue method is not intended for client use. Subclasses should use the addValueForProcessing method instead. ");
    }
}
