package ru.glavset.aprilfirst;

/**
 * Created by Roman on 29.01.2016.
 */
public class Cesar {

    // Шифровать
    public static String Encrypt(String text)
    {
        if (text.isEmpty())
            throw new RuntimeException("Text is empty");

        String res = "";
        for (int i=0; i<text.length(); i++)
            res += EncryptChar(text.charAt(i));

        return res;
    }

    // Расшифровать
    public static String Decrypt(String text)
    {
        if (text.isEmpty())
            throw new RuntimeException("Text is empty");

        String res = "";
        for (int i=0; i<text.length(); i++)
            res += DecryptChar(text.charAt(i));

        return res;
    }

    // Шифровать мимвол
    private static char EncryptChar(char c)
    {
        return c == Character.MAX_VALUE ? '\0' : (char)(c + 1);
    }

    // Расшифровать символ
    private static char DecryptChar(char c)
    {
        return c == '\0' ? Character.MAX_VALUE : (char)(c - 1);
    }

}