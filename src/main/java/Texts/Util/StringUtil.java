package Texts.Util;

public abstract class StringUtil {
    public static StringBuilder CharAsBreite(char s, int breite){
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < breite; i++) {
            b.append( s );
        }
        return b;
    }
    public static StringBuilder StringZuBreite(String s, int breite){
        StringBuilder white = new StringBuilder(s);
        int ergaenzung = breite-s.length();
        for (int i = 0; i < ergaenzung; i++) {
            white.append( ' ' );
        }
        return white;
    }
    public static StringBuilder StringMittigZuBreite(String s, int breite){
        StringBuilder white = new StringBuilder();
        int ergaenzung = breite-s.length();
        int vorne = ergaenzung /2;
        int hinten = ergaenzung-vorne;
        for (int i = 0; i < vorne; i++) {
            white.append( ' ' );
        }
        white.append( s );
        for (int i = 0; i < hinten; i++) {
            white.append( ' ' );
        }
        return white;
    }
    public static int countOccurrences(String haystack, char needle)
    {
        int count = 0;
        for (int i=0; i < haystack.length(); i++)
        {
            if (haystack.charAt(i) == needle)
            {
                count++;
            }
        }
        return count;
    }
}
