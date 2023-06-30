package net.minecraft.src;

public class NaturalProperties
{
    public int rotation;
    public boolean flip;

    public NaturalProperties(String s)
    {
        rotation = 1;
        flip = false;

        if (s.equals("4"))
        {
            rotation = 4;
            return;
        }

        if (s.equals("2"))
        {
            rotation = 2;
            return;
        }

        if (s.equals("F"))
        {
            flip = true;
            return;
        }

        if (s.equals("4F"))
        {
            rotation = 4;
            flip = true;
            return;
        }

        if (s.equals("2F"))
        {
            rotation = 2;
            flip = true;
            return;
        }
        else
        {
            Config.dbg((new StringBuilder()).append("Unknown natural texture type: ").append(s).toString());
            return;
        }
    }

    public boolean isValid()
    {
        if (rotation == 2 || rotation == 4)
        {
            return true;
        }

        return flip;
    }
}
