package util;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.MissingResourceException;

/**
 * LanguageMain class is used to translate the login window based on user's system setting
 * Will translate between French and English
 */
public abstract class LanguageMain {
    public static ResourceBundle rb = ResourceBundle.getBundle("lang", Locale.getDefault());
    private static boolean hasText = false;

    public static String translate(String word) throws MissingResourceException {
        try {
            rb.getString(word);
            hasText = true;
        }
        catch (MissingResourceException e) {
            e.printStackTrace();
            hasText = false;
        }
        if (hasText) {
            return rb.getString(word);
        } else {
            return word;
        }
    }
}
