package net.minecraft.src;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChatAllowedCharacters
{
    /**
     * This String have the characters allowed in any text drawing of minecraft.
     */
    public static final String allowedCharacters = getAllowedCharacters();
    public static final char allowedCharactersArray[] =
    {
        '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\',
        '<', '>', '|', '"', ':'
    };

    public ChatAllowedCharacters()
    {
    }

    /**
     * Load the font.txt resource file, that is on UTF-8 format. This file contains the characters that minecraft can
     * render Strings on screen.
     */
    private static String getAllowedCharacters()
    {
        String s = "";

        try
        {
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader((net.minecraft.src.ChatAllowedCharacters.class).getResourceAsStream("/font.txt"), "UTF-8"));
            String s1 = "";

            do
            {
                String s2;

                if ((s2 = bufferedreader.readLine()) == null)
                {
                    break;
                }

                if (!s2.startsWith("#"))
                {
                    s = (new StringBuilder()).append(s).append(s2).toString();
                }
            }
            while (true);

            bufferedreader.close();
        }
        catch (Exception exception) { }

        return s;
    }

    public static final boolean isAllowedCharacter(char par0)
    {
        return par0 != '\247' && (allowedCharacters.indexOf(par0) >= 0 || par0 > ' ');
    }

    public static String func_52019_a(String par0Str)
    {
        StringBuilder stringbuilder = new StringBuilder();
        char ac[] = par0Str.toCharArray();
        int i = ac.length;

        for (int j = 0; j < i; j++)
        {
            char c = ac[j];

            if (isAllowedCharacter(c))
            {
                stringbuilder.append(c);
            }
        }

        return stringbuilder.toString();
    }
}
