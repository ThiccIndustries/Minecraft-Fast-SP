package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class IntCache
{
    private static int intCacheSize = 256;

    /**
     * A list of pre-allocated int[256] arrays that are currently unused and can be returned by getIntCache()
     */
    private static List freeSmallArrays = new ArrayList();

    /**
     * A list of pre-allocated int[256] arrays that were previously returned by getIntCache() and which will not be re-
     * used again until resetIntCache() is called.
     */
    private static List inUseSmallArrays = new ArrayList();

    /**
     * A list of pre-allocated int[cacheSize] arrays that are currently unused and can be returned by getIntCache()
     */
    private static List freeLargeArrays = new ArrayList();

    /**
     * A list of pre-allocated int[cacheSize] arrays that were previously returned by getIntCache() and which will not
     * be re-used again until resetIntCache() is called.
     */
    private static List inUseLargeArrays = new ArrayList();

    public IntCache()
    {
    }

    public static int[] getIntCache(int par0)
    {
        if (par0 <= 256)
        {
            if (freeSmallArrays.size() == 0)
            {
                int ai[] = new int[256];
                inUseSmallArrays.add(ai);
                return ai;
            }
            else
            {
                int ai1[] = (int[])freeSmallArrays.remove(freeSmallArrays.size() - 1);
                inUseSmallArrays.add(ai1);
                return ai1;
            }
        }

        if (par0 > intCacheSize)
        {
            intCacheSize = par0;
            freeLargeArrays.clear();
            inUseLargeArrays.clear();
            int ai2[] = new int[intCacheSize];
            inUseLargeArrays.add(ai2);
            return ai2;
        }

        if (freeLargeArrays.size() == 0)
        {
            int ai3[] = new int[intCacheSize];
            inUseLargeArrays.add(ai3);
            return ai3;
        }
        else
        {
            int ai4[] = (int[])freeLargeArrays.remove(freeLargeArrays.size() - 1);
            inUseLargeArrays.add(ai4);
            return ai4;
        }
    }

    /**
     * Mark all pre-allocated arrays as available for re-use by moving them to the appropriate free lists.
     */
    public static void resetIntCache()
    {
        if (freeLargeArrays.size() > 0)
        {
            freeLargeArrays.remove(freeLargeArrays.size() - 1);
        }

        if (freeSmallArrays.size() > 0)
        {
            freeSmallArrays.remove(freeSmallArrays.size() - 1);
        }

        freeLargeArrays.addAll(inUseLargeArrays);
        freeSmallArrays.addAll(inUseSmallArrays);
        inUseLargeArrays.clear();
        inUseSmallArrays.clear();
    }
}
