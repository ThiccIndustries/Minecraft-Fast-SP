package net.minecraft.src;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class AchievementMap
{
    /** Holds the singleton instance of AchievementMap. */
    public static AchievementMap instance = new AchievementMap();

    /** Maps a achievement id with it's unique GUID. */
    private Map guidMap;

    private AchievementMap()
    {
        guidMap = new HashMap();

        try
        {
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader((net.minecraft.src.AchievementMap.class).getResourceAsStream("/achievement/map.txt")));
            String s;

            while ((s = bufferedreader.readLine()) != null)
            {
                String as[] = s.split(",");
                int i = Integer.parseInt(as[0]);
                guidMap.put(Integer.valueOf(i), as[1]);
            }

            bufferedreader.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    /**
     * Returns the unique GUID of a achievement id.
     */
    public static String getGuid(int par0)
    {
        return (String)instance.guidMap.get(Integer.valueOf(par0));
    }
}
