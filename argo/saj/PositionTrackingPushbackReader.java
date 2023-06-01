package argo.saj;

import java.io.*;

final class PositionTrackingPushbackReader implements ThingWithPosition
{
    private final PushbackReader pushbackReader;
    private int characterCount;
    private int lineCount;
    private boolean lastCharacterWasCarriageReturn;

    public PositionTrackingPushbackReader(Reader par1Reader)
    {
        characterCount = 0;
        lineCount = 1;
        lastCharacterWasCarriageReturn = false;
        pushbackReader = new PushbackReader(par1Reader);
    }

    public void unread(char par1) throws IOException
    {
        characterCount--;

        if (characterCount < 0)
        {
            characterCount = 0;
        }

        pushbackReader.unread(par1);
    }

    public void uncount(char par1ArrayOfCharacter[])
    {
        characterCount = characterCount - par1ArrayOfCharacter.length;

        if (characterCount < 0)
        {
            characterCount = 0;
        }
    }

    public int read() throws IOException
    {
        int i = pushbackReader.read();
        updateCharacterAndLineCounts(i);
        return i;
    }

    public int read(char par1ArrayOfCharacter[]) throws IOException
    {
        int i = pushbackReader.read(par1ArrayOfCharacter);
        char ac[] = par1ArrayOfCharacter;
        int j = ac.length;

        for (int k = 0; k < j; k++)
        {
            char c = ac[k];
            updateCharacterAndLineCounts(c);
        }

        return i;
    }

    private void updateCharacterAndLineCounts(int par1)
    {
        if (13 == par1)
        {
            characterCount = 0;
            lineCount++;
            lastCharacterWasCarriageReturn = true;
        }
        else
        {
            if (10 == par1 && !lastCharacterWasCarriageReturn)
            {
                characterCount = 0;
                lineCount++;
            }
            else
            {
                characterCount++;
            }

            lastCharacterWasCarriageReturn = false;
        }
    }

    public int getColumn()
    {
        return characterCount;
    }

    public int getRow()
    {
        return lineCount;
    }
}
