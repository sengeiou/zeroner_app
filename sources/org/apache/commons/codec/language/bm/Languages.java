package org.apache.commons.codec.language.bm;

import java.io.InputStream;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

public class Languages {
    public static final String ANY = "any";
    public static final LanguageSet ANY_LANGUAGE = new LanguageSet() {
        public boolean contains(String language) {
            return true;
        }

        public String getAny() {
            throw new NoSuchElementException("Can't fetch any language from the any language set.");
        }

        public boolean isEmpty() {
            return false;
        }

        public boolean isSingleton() {
            return false;
        }

        public LanguageSet restrictTo(LanguageSet other) {
            return other;
        }

        public LanguageSet merge(LanguageSet other) {
            return other;
        }

        public String toString() {
            return "ANY_LANGUAGE";
        }
    };
    private static final Map<NameType, Languages> LANGUAGES = new EnumMap(NameType.class);
    public static final LanguageSet NO_LANGUAGES = new LanguageSet() {
        public boolean contains(String language) {
            return false;
        }

        public String getAny() {
            throw new NoSuchElementException("Can't fetch any language from the empty language set.");
        }

        public boolean isEmpty() {
            return true;
        }

        public boolean isSingleton() {
            return false;
        }

        public LanguageSet restrictTo(LanguageSet other) {
            return this;
        }

        public LanguageSet merge(LanguageSet other) {
            return other;
        }

        public String toString() {
            return "NO_LANGUAGES";
        }
    };
    private final Set<String> languages;

    public static abstract class LanguageSet {
        public abstract boolean contains(String str);

        public abstract String getAny();

        public abstract boolean isEmpty();

        public abstract boolean isSingleton();

        /* access modifiers changed from: 0000 */
        public abstract LanguageSet merge(LanguageSet languageSet);

        public abstract LanguageSet restrictTo(LanguageSet languageSet);

        public static LanguageSet from(Set<String> langs) {
            return langs.isEmpty() ? Languages.NO_LANGUAGES : new SomeLanguages(langs);
        }
    }

    public static final class SomeLanguages extends LanguageSet {
        private final Set<String> languages;

        private SomeLanguages(Set<String> languages2) {
            this.languages = Collections.unmodifiableSet(languages2);
        }

        public boolean contains(String language) {
            return this.languages.contains(language);
        }

        public String getAny() {
            return (String) this.languages.iterator().next();
        }

        public Set<String> getLanguages() {
            return this.languages;
        }

        public boolean isEmpty() {
            return this.languages.isEmpty();
        }

        public boolean isSingleton() {
            return this.languages.size() == 1;
        }

        public LanguageSet restrictTo(LanguageSet other) {
            if (other == Languages.NO_LANGUAGES) {
                return other;
            }
            if (other == Languages.ANY_LANGUAGE) {
                return this;
            }
            SomeLanguages sl = (SomeLanguages) other;
            Set<String> ls = new HashSet<>(Math.min(this.languages.size(), sl.languages.size()));
            for (String lang : this.languages) {
                if (sl.languages.contains(lang)) {
                    ls.add(lang);
                }
            }
            return from(ls);
        }

        /* Debug info: failed to restart local var, previous not found, register: 5 */
        public LanguageSet merge(LanguageSet other) {
            if (other == Languages.NO_LANGUAGES) {
                return this;
            }
            if (other == Languages.ANY_LANGUAGE) {
                return other;
            }
            SomeLanguages sl = (SomeLanguages) other;
            Set<String> ls = new HashSet<>(this.languages);
            for (String lang : sl.languages) {
                ls.add(lang);
            }
            return from(ls);
        }

        public String toString() {
            return "Languages(" + this.languages.toString() + ")";
        }
    }

    static {
        NameType[] arr$;
        for (NameType s : NameType.values()) {
            LANGUAGES.put(s, getInstance(langResourceName(s)));
        }
    }

    public static Languages getInstance(NameType nameType) {
        return (Languages) LANGUAGES.get(nameType);
    }

    /* JADX INFO: finally extract failed */
    public static Languages getInstance(String languagesResourceName) {
        Set<String> ls = new HashSet<>();
        InputStream langIS = Languages.class.getClassLoader().getResourceAsStream(languagesResourceName);
        if (langIS == null) {
            throw new IllegalArgumentException("Unable to resolve required resource: " + languagesResourceName);
        }
        Scanner lsScanner = new Scanner(langIS, "UTF-8");
        boolean inExtendedComment = false;
        while (lsScanner.hasNextLine()) {
            try {
                String line = lsScanner.nextLine().trim();
                if (inExtendedComment) {
                    if (line.endsWith("*/")) {
                        inExtendedComment = false;
                    }
                } else if (line.startsWith("/*")) {
                    inExtendedComment = true;
                } else if (line.length() > 0) {
                    ls.add(line);
                }
            } catch (Throwable th) {
                lsScanner.close();
                throw th;
            }
        }
        lsScanner.close();
        return new Languages(Collections.unmodifiableSet(ls));
    }

    private static String langResourceName(NameType nameType) {
        return String.format("org/apache/commons/codec/language/bm/%s_languages.txt", new Object[]{nameType.getName()});
    }

    private Languages(Set<String> languages2) {
        this.languages = languages2;
    }

    public Set<String> getLanguages() {
        return this.languages;
    }
}
