package org.apache.commons.codec.language.bm;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import org.apache.commons.codec.language.bm.Languages.LanguageSet;

public class Lang {
    private static final String LANGUAGE_RULES_RN = "org/apache/commons/codec/language/bm/%s_lang.txt";
    private static final Map<NameType, Lang> Langs = new EnumMap(NameType.class);
    private final Languages languages;
    private final List<LangRule> rules;

    private static final class LangRule {
        /* access modifiers changed from: private */
        public final boolean acceptOnMatch;
        /* access modifiers changed from: private */
        public final Set<String> languages;
        private final Pattern pattern;

        private LangRule(Pattern pattern2, Set<String> languages2, boolean acceptOnMatch2) {
            this.pattern = pattern2;
            this.languages = languages2;
            this.acceptOnMatch = acceptOnMatch2;
        }

        public boolean matches(String txt) {
            return this.pattern.matcher(txt).find();
        }
    }

    static {
        NameType[] arr$;
        for (NameType s : NameType.values()) {
            Langs.put(s, loadFromResource(String.format(LANGUAGE_RULES_RN, new Object[]{s.getName()}), Languages.getInstance(s)));
        }
    }

    public static Lang instance(NameType nameType) {
        return (Lang) Langs.get(nameType);
    }

    /* JADX INFO: finally extract failed */
    public static Lang loadFromResource(String languageRulesResourceName, Languages languages2) {
        List<LangRule> rules2 = new ArrayList<>();
        InputStream lRulesIS = Lang.class.getClassLoader().getResourceAsStream(languageRulesResourceName);
        if (lRulesIS == null) {
            throw new IllegalStateException("Unable to resolve required resource:org/apache/commons/codec/language/bm/%s_lang.txt");
        }
        Scanner scanner = new Scanner(lRulesIS, "UTF-8");
        boolean inExtendedComment = false;
        while (scanner.hasNextLine()) {
            try {
                String rawLine = scanner.nextLine();
                String line = rawLine;
                if (inExtendedComment) {
                    if (line.endsWith("*/")) {
                        inExtendedComment = false;
                    }
                } else if (line.startsWith("/*")) {
                    inExtendedComment = true;
                } else {
                    int cmtI = line.indexOf("//");
                    if (cmtI >= 0) {
                        line = line.substring(0, cmtI);
                    }
                    String line2 = line.trim();
                    if (line2.length() != 0) {
                        String[] parts = line2.split("\\s+");
                        if (parts.length != 3) {
                            throw new IllegalArgumentException("Malformed line '" + rawLine + "' in language resource '" + languageRulesResourceName + "'");
                        }
                        Pattern pattern = Pattern.compile(parts[0]);
                        String[] langs = parts[1].split("\\+");
                        rules2.add(new LangRule(pattern, new HashSet(Arrays.asList(langs)), parts[2].equals("true")));
                    } else {
                        continue;
                    }
                }
            } catch (Throwable th) {
                scanner.close();
                throw th;
            }
        }
        scanner.close();
        return new Lang(rules2, languages2);
    }

    private Lang(List<LangRule> rules2, Languages languages2) {
        this.rules = Collections.unmodifiableList(rules2);
        this.languages = languages2;
    }

    public String guessLanguage(String text) {
        LanguageSet ls = guessLanguages(text);
        return ls.isSingleton() ? ls.getAny() : Languages.ANY;
    }

    public LanguageSet guessLanguages(String input) {
        String text = input.toLowerCase(Locale.ENGLISH);
        Set<String> langs = new HashSet<>(this.languages.getLanguages());
        for (LangRule rule : this.rules) {
            if (rule.matches(text)) {
                if (rule.acceptOnMatch) {
                    langs.retainAll(rule.languages);
                } else {
                    langs.removeAll(rule.languages);
                }
            }
        }
        LanguageSet ls = LanguageSet.from(langs);
        return ls.equals(Languages.NO_LANGUAGES) ? Languages.ANY_LANGUAGE : ls;
    }
}
