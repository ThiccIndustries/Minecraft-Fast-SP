package argo.saj;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

public final class SajParser
{
    public SajParser()
    {
    }

    public void parse(Reader par1Reader, JsonListener par2JsonListener) throws InvalidSyntaxException, IOException
    {
        PositionTrackingPushbackReader positiontrackingpushbackreader = new PositionTrackingPushbackReader(par1Reader);
        char c = (char)positiontrackingpushbackreader.read();

        switch (c)
        {
            case 123:
                positiontrackingpushbackreader.unread(c);
                par2JsonListener.startDocument();
                objectString(positiontrackingpushbackreader, par2JsonListener);
                break;

            case 91:
                positiontrackingpushbackreader.unread(c);
                par2JsonListener.startDocument();
                arrayString(positiontrackingpushbackreader, par2JsonListener);
                break;

            default:
                throw new InvalidSyntaxException((new StringBuilder()).append("Expected either [ or { but got [").append(c).append("].").toString(), positiontrackingpushbackreader);
        }

        int i = readNextNonWhitespaceChar(positiontrackingpushbackreader);

        if (i != -1)
        {
            throw new InvalidSyntaxException((new StringBuilder()).append("Got unexpected trailing character [").append((char)i).append("].").toString(), positiontrackingpushbackreader);
        }
        else
        {
            par2JsonListener.endDocument();
            return;
        }
    }

    private void arrayString(PositionTrackingPushbackReader par1PositionTrackingPushbackReader, JsonListener par2JsonListener) throws InvalidSyntaxException, IOException
    {
        char c = (char)readNextNonWhitespaceChar(par1PositionTrackingPushbackReader);

        if (c != '[')
        {
            throw new InvalidSyntaxException((new StringBuilder()).append("Expected object to start with [ but got [").append(c).append("].").toString(), par1PositionTrackingPushbackReader);
        }

        par2JsonListener.startArray();
        char c1 = (char)readNextNonWhitespaceChar(par1PositionTrackingPushbackReader);
        par1PositionTrackingPushbackReader.unread(c1);

        if (c1 != ']')
        {
            aJsonValue(par1PositionTrackingPushbackReader, par2JsonListener);
        }

        boolean flag = false;

        do
        {
            if (flag)
            {
                break;
            }

            char c2 = (char)readNextNonWhitespaceChar(par1PositionTrackingPushbackReader);

            switch (c2)
            {
                case 44:
                    aJsonValue(par1PositionTrackingPushbackReader, par2JsonListener);
                    break;

                case 93:
                    flag = true;
                    break;

                default:
                    throw new InvalidSyntaxException((new StringBuilder()).append("Expected either , or ] but got [").append(c2).append("].").toString(), par1PositionTrackingPushbackReader);
            }
        }
        while (true);

        par2JsonListener.endArray();
    }

    private void objectString(PositionTrackingPushbackReader par1PositionTrackingPushbackReader, JsonListener par2JsonListener) throws InvalidSyntaxException, IOException
    {
        char c = (char)readNextNonWhitespaceChar(par1PositionTrackingPushbackReader);

        if (c != '{')
        {
            throw new InvalidSyntaxException((new StringBuilder()).append("Expected object to start with { but got [").append(c).append("].").toString(), par1PositionTrackingPushbackReader);
        }

        par2JsonListener.startObject();
        char c1 = (char)readNextNonWhitespaceChar(par1PositionTrackingPushbackReader);
        par1PositionTrackingPushbackReader.unread(c1);

        if (c1 != '}')
        {
            aFieldToken(par1PositionTrackingPushbackReader, par2JsonListener);
        }

        boolean flag = false;

        do
        {
            if (flag)
            {
                break;
            }

            char c2 = (char)readNextNonWhitespaceChar(par1PositionTrackingPushbackReader);

            switch (c2)
            {
                case 44:
                    aFieldToken(par1PositionTrackingPushbackReader, par2JsonListener);
                    break;

                case 125:
                    flag = true;
                    break;

                default:
                    throw new InvalidSyntaxException((new StringBuilder()).append("Expected either , or } but got [").append(c2).append("].").toString(), par1PositionTrackingPushbackReader);
            }
        }
        while (true);

        par2JsonListener.endObject();
    }

    private void aFieldToken(PositionTrackingPushbackReader par1PositionTrackingPushbackReader, JsonListener par2JsonListener) throws InvalidSyntaxException, IOException
    {
        char c = (char)readNextNonWhitespaceChar(par1PositionTrackingPushbackReader);

        if ('"' != c)
        {
            throw new InvalidSyntaxException((new StringBuilder()).append("Expected object identifier to begin with [\"] but got [").append(c).append("].").toString(), par1PositionTrackingPushbackReader);
        }

        par1PositionTrackingPushbackReader.unread(c);
        par2JsonListener.startField(stringToken(par1PositionTrackingPushbackReader));
        char c1 = (char)readNextNonWhitespaceChar(par1PositionTrackingPushbackReader);

        if (c1 != ':')
        {
            throw new InvalidSyntaxException((new StringBuilder()).append("Expected object identifier to be followed by : but got [").append(c1).append("].").toString(), par1PositionTrackingPushbackReader);
        }
        else
        {
            aJsonValue(par1PositionTrackingPushbackReader, par2JsonListener);
            par2JsonListener.endField();
            return;
        }
    }

    private void aJsonValue(PositionTrackingPushbackReader par1PositionTrackingPushbackReader, JsonListener par2JsonListener) throws InvalidSyntaxException, IOException
    {
        char c = (char)readNextNonWhitespaceChar(par1PositionTrackingPushbackReader);

        switch (c)
        {
            case 34:
                par1PositionTrackingPushbackReader.unread(c);
                par2JsonListener.stringValue(stringToken(par1PositionTrackingPushbackReader));
                break;

            case 116:
                char ac[] = new char[3];
                int i = par1PositionTrackingPushbackReader.read(ac);

                if (i != 3 || ac[0] != 'r' || ac[1] != 'u' || ac[2] != 'e')
                {
                    par1PositionTrackingPushbackReader.uncount(ac);
                    throw new InvalidSyntaxException((new StringBuilder()).append("Expected 't' to be followed by [[r, u, e]], but got [").append(Arrays.toString(ac)).append("].").toString(), par1PositionTrackingPushbackReader);
                }

                par2JsonListener.trueValue();
                break;

            case 102:
                char ac1[] = new char[4];
                int j = par1PositionTrackingPushbackReader.read(ac1);

                if (j != 4 || ac1[0] != 'a' || ac1[1] != 'l' || ac1[2] != 's' || ac1[3] != 'e')
                {
                    par1PositionTrackingPushbackReader.uncount(ac1);
                    throw new InvalidSyntaxException((new StringBuilder()).append("Expected 'f' to be followed by [[a, l, s, e]], but got [").append(Arrays.toString(ac1)).append("].").toString(), par1PositionTrackingPushbackReader);
                }

                par2JsonListener.falseValue();
                break;

            case 110:
                char ac2[] = new char[3];
                int k = par1PositionTrackingPushbackReader.read(ac2);

                if (k != 3 || ac2[0] != 'u' || ac2[1] != 'l' || ac2[2] != 'l')
                {
                    par1PositionTrackingPushbackReader.uncount(ac2);
                    throw new InvalidSyntaxException((new StringBuilder()).append("Expected 'n' to be followed by [[u, l, l]], but got [").append(Arrays.toString(ac2)).append("].").toString(), par1PositionTrackingPushbackReader);
                }

                par2JsonListener.nullValue();
                break;

            case 45:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
                par1PositionTrackingPushbackReader.unread(c);
                par2JsonListener.numberValue(numberToken(par1PositionTrackingPushbackReader));
                break;

            case 123:
                par1PositionTrackingPushbackReader.unread(c);
                objectString(par1PositionTrackingPushbackReader, par2JsonListener);
                break;

            case 91:
                par1PositionTrackingPushbackReader.unread(c);
                arrayString(par1PositionTrackingPushbackReader, par2JsonListener);
                break;

            default:
                throw new InvalidSyntaxException((new StringBuilder()).append("Invalid character at start of value [").append(c).append("].").toString(), par1PositionTrackingPushbackReader);
        }
    }

    private String numberToken(PositionTrackingPushbackReader par1PositionTrackingPushbackReader) throws IOException, InvalidSyntaxException
    {
        StringBuilder stringbuilder = new StringBuilder();
        char c = (char)par1PositionTrackingPushbackReader.read();

        if ('-' == c)
        {
            stringbuilder.append('-');
        }
        else
        {
            par1PositionTrackingPushbackReader.unread(c);
        }

        stringbuilder.append(nonNegativeNumberToken(par1PositionTrackingPushbackReader));
        return stringbuilder.toString();
    }

    private String nonNegativeNumberToken(PositionTrackingPushbackReader par1PositionTrackingPushbackReader) throws IOException, InvalidSyntaxException
    {
        StringBuilder stringbuilder = new StringBuilder();
        char c = (char)par1PositionTrackingPushbackReader.read();

        if ('0' == c)
        {
            stringbuilder.append('0');
            stringbuilder.append(possibleFractionalComponent(par1PositionTrackingPushbackReader));
            stringbuilder.append(possibleExponent(par1PositionTrackingPushbackReader));
        }
        else
        {
            par1PositionTrackingPushbackReader.unread(c);
            stringbuilder.append(nonZeroDigitToken(par1PositionTrackingPushbackReader));
            stringbuilder.append(digitString(par1PositionTrackingPushbackReader));
            stringbuilder.append(possibleFractionalComponent(par1PositionTrackingPushbackReader));
            stringbuilder.append(possibleExponent(par1PositionTrackingPushbackReader));
        }

        return stringbuilder.toString();
    }

    private char nonZeroDigitToken(PositionTrackingPushbackReader par1PositionTrackingPushbackReader) throws IOException, InvalidSyntaxException
    {
        char c1 = (char)par1PositionTrackingPushbackReader.read();
        char c;

        switch (c1)
        {
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
                c = c1;
                break;

            default:
                throw new InvalidSyntaxException((new StringBuilder()).append("Expected a digit 1 - 9 but got [").append(c1).append("].").toString(), par1PositionTrackingPushbackReader);
        }

        return c;
    }

    private char digitToken(PositionTrackingPushbackReader par1PositionTrackingPushbackReader) throws IOException, InvalidSyntaxException
    {
        char c1 = (char)par1PositionTrackingPushbackReader.read();
        char c;

        switch (c1)
        {
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
                c = c1;
                break;

            default:
                throw new InvalidSyntaxException((new StringBuilder()).append("Expected a digit 1 - 9 but got [").append(c1).append("].").toString(), par1PositionTrackingPushbackReader);
        }

        return c;
    }

    private String digitString(PositionTrackingPushbackReader par1PositionTrackingPushbackReader) throws IOException
    {
        StringBuilder stringbuilder = new StringBuilder();
        boolean flag = false;

        do
        {
            while (!flag)
            {
                char c = (char)par1PositionTrackingPushbackReader.read();

                switch (c)
                {
                    case 48:
                    case 49:
                    case 50:
                    case 51:
                    case 52:
                    case 53:
                    case 54:
                    case 55:
                    case 56:
                    case 57:
                        stringbuilder.append(c);
                        break;

                    default:
                        flag = true;
                        par1PositionTrackingPushbackReader.unread(c);
                        break;
                }
            }

            return stringbuilder.toString();
        }
        while (true);
    }

    private String possibleFractionalComponent(PositionTrackingPushbackReader par1PositionTrackingPushbackReader) throws IOException, InvalidSyntaxException
    {
        StringBuilder stringbuilder = new StringBuilder();
        char c = (char)par1PositionTrackingPushbackReader.read();

        if (c == '.')
        {
            stringbuilder.append('.');
            stringbuilder.append(digitToken(par1PositionTrackingPushbackReader));
            stringbuilder.append(digitString(par1PositionTrackingPushbackReader));
        }
        else
        {
            par1PositionTrackingPushbackReader.unread(c);
        }

        return stringbuilder.toString();
    }

    private String possibleExponent(PositionTrackingPushbackReader par1PositionTrackingPushbackReader) throws IOException, InvalidSyntaxException
    {
        StringBuilder stringbuilder = new StringBuilder();
        char c = (char)par1PositionTrackingPushbackReader.read();

        if (c == '.' || c == 'E')
        {
            stringbuilder.append('E');
            stringbuilder.append(possibleSign(par1PositionTrackingPushbackReader));
            stringbuilder.append(digitToken(par1PositionTrackingPushbackReader));
            stringbuilder.append(digitString(par1PositionTrackingPushbackReader));
        }
        else
        {
            par1PositionTrackingPushbackReader.unread(c);
        }

        return stringbuilder.toString();
    }

    private String possibleSign(PositionTrackingPushbackReader par1PositionTrackingPushbackReader) throws IOException
    {
        StringBuilder stringbuilder = new StringBuilder();
        char c = (char)par1PositionTrackingPushbackReader.read();

        if (c == '+' || c == '-')
        {
            stringbuilder.append(c);
        }
        else
        {
            par1PositionTrackingPushbackReader.unread(c);
        }

        return stringbuilder.toString();
    }

    private String stringToken(PositionTrackingPushbackReader par1PositionTrackingPushbackReader) throws InvalidSyntaxException, IOException
    {
        StringBuilder stringbuilder = new StringBuilder();
        char c = (char)par1PositionTrackingPushbackReader.read();

        if ('"' != c)
        {
            throw new InvalidSyntaxException((new StringBuilder()).append("Expected [\"] but got [").append(c).append("].").toString(), par1PositionTrackingPushbackReader);
        }

        boolean flag = false;

        do
        {
            if (flag)
            {
                break;
            }

            char c1 = (char)par1PositionTrackingPushbackReader.read();

            switch (c1)
            {
                case 34:
                    flag = true;
                    break;

                case 92:
                    char c2 = escapedStringChar(par1PositionTrackingPushbackReader);
                    stringbuilder.append(c2);
                    break;

                default:
                    stringbuilder.append(c1);
                    break;
            }
        }
        while (true);

        return stringbuilder.toString();
    }

    private char escapedStringChar(PositionTrackingPushbackReader par1PositionTrackingPushbackReader) throws IOException, InvalidSyntaxException
    {
        char c1 = (char)par1PositionTrackingPushbackReader.read();
        char c;

        switch (c1)
        {
            case 34:
                c = '"';
                break;

            case 92:
                c = '\\';
                break;

            case 47:
                c = '/';
                break;

            case 98:
                c = '\b';
                break;

            case 102:
                c = '\f';
                break;

            case 110:
                c = '\n';
                break;

            case 114:
                c = '\r';
                break;

            case 116:
                c = '\t';
                break;

            case 117:
                c = (char)hexadecimalNumber(par1PositionTrackingPushbackReader);
                break;

            default:
                throw new InvalidSyntaxException((new StringBuilder()).append("Unrecognised escape character [").append(c1).append("].").toString(), par1PositionTrackingPushbackReader);
        }

        return c;
    }

    private int hexadecimalNumber(PositionTrackingPushbackReader par1PositionTrackingPushbackReader) throws IOException, InvalidSyntaxException
    {
        char ac[] = new char[4];
        int i = par1PositionTrackingPushbackReader.read(ac);

        if (i != 4)
        {
            throw new InvalidSyntaxException((new StringBuilder()).append("Expected a 4 digit hexidecimal number but got only [").append(i).append("], namely [").append(String.valueOf(ac, 0, i)).append("].").toString(), par1PositionTrackingPushbackReader);
        }

        int j;

        try
        {
            j = Integer.parseInt(String.valueOf(ac), 16);
        }
        catch (NumberFormatException numberformatexception)
        {
            par1PositionTrackingPushbackReader.uncount(ac);
            throw new InvalidSyntaxException((new StringBuilder()).append("Unable to parse [").append(String.valueOf(ac)).append("] as a hexidecimal number.").toString(), numberformatexception, par1PositionTrackingPushbackReader);
        }

        return j;
    }

    private int readNextNonWhitespaceChar(PositionTrackingPushbackReader par1PositionTrackingPushbackReader) throws IOException
    {
        boolean flag = false;
        int i;

        do
        {
            i = par1PositionTrackingPushbackReader.read();

            switch (i)
            {
                default:
                    flag = true;
                    break;

                case 9:
                case 10:
                case 13:
                case 32:
                    break;
            }
        }
        while (!flag);

        return i;
    }
}
