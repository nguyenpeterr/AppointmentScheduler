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

    /**
     * Method to take a string as a parameter to compare to the lang resource bundle. This will grab the word and translate it (if it is translatable in the lang_properties)
     *
     * @param word Word to be translated
     * @return Translated word
     * @throws MissingResourceException if the word does not exist in the lang properties
     */
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
