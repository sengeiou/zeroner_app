package org.apache.commons.cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class PosixParser extends Parser {
    private Option currentOption;
    private boolean eatTheRest;
    private Options options;
    private List tokens = new ArrayList();

    private void init() {
        this.eatTheRest = false;
        this.tokens.clear();
    }

    /* access modifiers changed from: protected */
    public String[] flatten(Options options2, String[] arguments, boolean stopAtNonOption) {
        init();
        this.options = options2;
        Iterator iter = Arrays.asList(arguments).iterator();
        while (iter.hasNext()) {
            String token = (String) iter.next();
            if (token.startsWith(HelpFormatter.DEFAULT_LONG_OPT_PREFIX)) {
                int pos = token.indexOf(61);
                String opt = pos == -1 ? token : token.substring(0, pos);
                if (!options2.hasOption(opt)) {
                    processNonOptionToken(token, stopAtNonOption);
                } else {
                    this.currentOption = options2.getOption(opt);
                    this.tokens.add(opt);
                    if (pos != -1) {
                        this.tokens.add(token.substring(pos + 1));
                    }
                }
            } else if (HelpFormatter.DEFAULT_OPT_PREFIX.equals(token)) {
                this.tokens.add(token);
            } else if (!token.startsWith(HelpFormatter.DEFAULT_OPT_PREFIX)) {
                processNonOptionToken(token, stopAtNonOption);
            } else if (token.length() == 2 || options2.hasOption(token)) {
                processOptionToken(token, stopAtNonOption);
            } else {
                burstToken(token, stopAtNonOption);
            }
            gobble(iter);
        }
        return (String[]) this.tokens.toArray(new String[this.tokens.size()]);
    }

    private void gobble(Iterator iter) {
        if (this.eatTheRest) {
            while (iter.hasNext()) {
                this.tokens.add(iter.next());
            }
        }
    }

    private void processNonOptionToken(String value, boolean stopAtNonOption) {
        if (stopAtNonOption && (this.currentOption == null || !this.currentOption.hasArg())) {
            this.eatTheRest = true;
            this.tokens.add(HelpFormatter.DEFAULT_LONG_OPT_PREFIX);
        }
        this.tokens.add(value);
    }

    private void processOptionToken(String token, boolean stopAtNonOption) {
        if (stopAtNonOption && !this.options.hasOption(token)) {
            this.eatTheRest = true;
        }
        if (this.options.hasOption(token)) {
            this.currentOption = this.options.getOption(token);
        }
        this.tokens.add(token);
    }

    /* access modifiers changed from: protected */
    public void burstToken(String token, boolean stopAtNonOption) {
        int i = 1;
        while (i < token.length()) {
            String ch = String.valueOf(token.charAt(i));
            if (this.options.hasOption(ch)) {
                this.tokens.add(new StringBuffer().append(HelpFormatter.DEFAULT_OPT_PREFIX).append(ch).toString());
                this.currentOption = this.options.getOption(ch);
                if (!this.currentOption.hasArg() || token.length() == i + 1) {
                    i++;
                } else {
                    this.tokens.add(token.substring(i + 1));
                    return;
                }
            } else if (stopAtNonOption) {
                processNonOptionToken(token.substring(i), true);
                return;
            } else {
                this.tokens.add(token);
                return;
            }
        }
    }
}
