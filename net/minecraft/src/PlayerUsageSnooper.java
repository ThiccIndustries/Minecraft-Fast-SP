package net.minecraft.src;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class PlayerUsageSnooper
{
    private Map field_52025_a;
    private final URL field_52024_b;

    public PlayerUsageSnooper(String par1Str)
    {
        field_52025_a = new HashMap();

        try
        {
            field_52024_b = new URL((new StringBuilder()).append("http://snoop.minecraft.net/").append(par1Str).toString());
        }
        catch (MalformedURLException malformedurlexception)
        {
            throw new IllegalArgumentException();
        }
    }

    public void func_52022_a(String par1Str, Object par2Obj)
    {
        field_52025_a.put(par1Str, par2Obj);
    }

    public void func_52021_a()
    {
        PlayerUsageSnooperThread playerusagesnooperthread = new PlayerUsageSnooperThread(this, "reporter");
        playerusagesnooperthread.setDaemon(true);
        playerusagesnooperthread.start();
    }

    static URL func_52023_a(PlayerUsageSnooper par0PlayerUsageSnooper)
    {
        return par0PlayerUsageSnooper.field_52024_b;
    }

    static Map func_52020_b(PlayerUsageSnooper par0PlayerUsageSnooper)
    {
        return par0PlayerUsageSnooper.field_52025_a;
    }
}
