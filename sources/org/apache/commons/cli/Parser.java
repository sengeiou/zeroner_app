package org.apache.commons.cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;

public abstract class Parser implements CommandLineParser {
    protected CommandLine cmd;
    private Options options;
    private List requiredOptions;

    /* access modifiers changed from: protected */
    public abstract String[] flatten(Options options2, String[] strArr, boolean z);

    /* access modifiers changed from: protected */
    public void setOptions(Options options2) {
        this.options = options2;
        this.requiredOptions = new ArrayList(options2.getRequiredOptions());
    }

    /* access modifiers changed from: protected */
    public Options getOptions() {
        return this.options;
    }

    /* access modifiers changed from: protected */
    public List getRequiredOptions() {
        return this.requiredOptions;
    }

    public CommandLine parse(Options options2, String[] arguments) throws ParseException {
        return parse(options2, arguments, null, false);
    }

    public CommandLine parse(Options options2, String[] arguments, Properties properties) throws ParseException {
        return parse(options2, arguments, properties, false);
    }

    public CommandLine parse(Options options2, String[] arguments, boolean stopAtNonOption) throws ParseException {
        return parse(options2, arguments, null, stopAtNonOption);
    }

    public CommandLine parse(Options options2, String[] arguments, Properties properties, boolean stopAtNonOption) throws ParseException {
        for (Option opt : options2.helpOptions()) {
            opt.clearValues();
        }
        setOptions(options2);
        this.cmd = new CommandLine();
        boolean eatTheRest = false;
        if (arguments == null) {
            arguments = new String[0];
        }
        ListIterator iterator = Arrays.asList(flatten(getOptions(), arguments, stopAtNonOption)).listIterator();
        while (iterator.hasNext()) {
            String t = (String) iterator.next();
            if (HelpFormatter.DEFAULT_LONG_OPT_PREFIX.equals(t)) {
                eatTheRest = true;
            } else if (HelpFormatter.DEFAULT_OPT_PREFIX.equals(t)) {
                if (stopAtNonOption) {
                    eatTheRest = true;
                } else {
                    this.cmd.addArg(t);
                }
            } else if (!t.startsWith(HelpFormatter.DEFAULT_OPT_PREFIX)) {
                this.cmd.addArg(t);
                if (stopAtNonOption) {
                    eatTheRest = true;
                }
            } else if (!stopAtNonOption || getOptions().hasOption(t)) {
                processOption(t, iterator);
            } else {
                eatTheRest = true;
                this.cmd.addArg(t);
            }
            if (eatTheRest) {
                while (iterator.hasNext()) {
                    String str = (String) iterator.next();
                    if (!HelpFormatter.DEFAULT_LONG_OPT_PREFIX.equals(str)) {
                        this.cmd.addArg(str);
                    }
                }
            }
        }
        processProperties(properties);
        checkRequiredOptions();
        return this.cmd;
    }

    /* access modifiers changed from: protected */
    public void processProperties(Properties properties) {
        if (properties != null) {
            Enumeration e = properties.propertyNames();
            while (e.hasMoreElements()) {
                String option = e.nextElement().toString();
                if (!this.cmd.hasOption(option)) {
                    Option opt = getOptions().getOption(option);
                    String value = properties.getProperty(option);
                    if (opt.hasArg()) {
                        if (opt.getValues() == null || opt.getValues().length == 0) {
                            try {
                                opt.addValueForProcessing(value);
                            } catch (RuntimeException e2) {
                            }
                        }
                    } else if (!"yes".equalsIgnoreCase(value) && !"true".equalsIgnoreCase(value) && !"1".equalsIgnoreCase(value)) {
                        return;
                    }
                    this.cmd.addOption(opt);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void checkRequiredOptions() throws MissingOptionException {
        if (!getRequiredOptions().isEmpty()) {
            throw new MissingOptionException(getRequiredOptions());
        }
    }

    public void processArgs(Option opt, ListIterator iter) throws ParseException {
        while (true) {
            if (!iter.hasNext()) {
                break;
            }
            String str = (String) iter.next();
            if (getOptions().hasOption(str) && str.startsWith(HelpFormatter.DEFAULT_OPT_PREFIX)) {
                iter.previous();
                break;
            } else {
                try {
                    opt.addValueForProcessing(Util.stripLeadingAndTrailingQuotes(str));
                } catch (RuntimeException e) {
                    iter.previous();
                }
            }
        }
        if (opt.getValues() == null && !opt.hasOptionalArg()) {
            throw new MissingArgumentException(opt);
        }
    }

    /* access modifiers changed from: protected */
    public void processOption(String arg, ListIterator iter) throws ParseException {
        if (!getOptions().hasOption(arg)) {
            throw new UnrecognizedOptionException(new StringBuffer().append("Unrecognized option: ").append(arg).toString(), arg);
        }
        Option opt = (Option) getOptions().getOption(arg).clone();
        if (opt.isRequired()) {
            getRequiredOptions().remove(opt.getKey());
        }
        if (getOptions().getOptionGroup(opt) != null) {
            OptionGroup group = getOptions().getOptionGroup(opt);
            if (group.isRequired()) {
                getRequiredOptions().remove(group);
            }
            group.setSelected(opt);
        }
        if (opt.hasArg()) {
            processArgs(opt, iter);
        }
        this.cmd.addOption(opt);
    }
}
