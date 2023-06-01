package net.minecraft.src;

import java.util.*;

public class Profiler
{
    /** Flag profiling enabled */
    public static boolean profilingEnabled = true;

    /** List of parent sections */
    private static List sectionList = new ArrayList();

    /** List of timestamps (System.nanoTime) */
    private static List timestampList = new ArrayList();

    /** Current profiling section */
    private static String profilingSection = "";

    /** Profiling map */
    private static Map profilingMap = new HashMap();
    public static boolean profilerGlobalEnabled;
    private static boolean profilerLocalEnabled;

    public Profiler()
    {
    }

    /**
     * Clear profiling
     */
    public static void clearProfiling()
    {
        profilingMap.clear();
        profilerLocalEnabled = profilerGlobalEnabled;
    }

    /**
     * Start section
     */
    public static void startSection(String par0Str)
    {
        if (!profilerLocalEnabled)
        {
            return;
        }

        if (!profilingEnabled)
        {
            return;
        }

        if (profilingSection.length() > 0)
        {
            profilingSection = (new StringBuilder()).append(profilingSection).append(".").toString();
        }

        profilingSection = (new StringBuilder()).append(profilingSection).append(par0Str).toString();
        sectionList.add(profilingSection);
        timestampList.add(Long.valueOf(System.nanoTime()));
    }

    /**
     * End section
     */
    public static void endSection()
    {
        if (!profilerLocalEnabled)
        {
            return;
        }

        if (!profilingEnabled)
        {
            return;
        }

        long l = System.nanoTime();
        long l1 = ((Long)timestampList.remove(timestampList.size() - 1)).longValue();
        sectionList.remove(sectionList.size() - 1);
        long l2 = l - l1;

        if (profilingMap.containsKey(profilingSection))
        {
            profilingMap.put(profilingSection, Long.valueOf(((Long)profilingMap.get(profilingSection)).longValue() + l2));
        }
        else
        {
            profilingMap.put(profilingSection, Long.valueOf(l2));
        }

        profilingSection = sectionList.size() > 0 ? (String)sectionList.get(sectionList.size() - 1) : "";

        if (l2 <= 0x5f5e100L);
    }

    /**
     * Get profiling data
     */
    public static List getProfilingData(String par0Str)
    {
        profilerLocalEnabled = profilerGlobalEnabled;

        if (!profilerLocalEnabled)
        {
            return new ArrayList(Arrays.asList(new ProfilerResult[]
                    {
                        new ProfilerResult("root", 0.0D, 0.0D)
                    }));
        }

        if (!profilingEnabled)
        {
            return null;
        }

        String s = par0Str;
        long l = profilingMap.containsKey("root") ? ((Long)profilingMap.get("root")).longValue() : 0L;
        long l1 = profilingMap.containsKey(par0Str) ? ((Long)profilingMap.get(par0Str)).longValue() : -1L;
        ArrayList arraylist = new ArrayList();

        if (par0Str.length() > 0)
        {
            par0Str = (new StringBuilder()).append(par0Str).append(".").toString();
        }

        long l2 = 0L;
        Iterator iterator = profilingMap.keySet().iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            String s1 = (String)iterator.next();

            if (s1.length() > par0Str.length() && s1.startsWith(par0Str) && s1.indexOf(".", par0Str.length() + 1) < 0)
            {
                l2 += ((Long)profilingMap.get(s1)).longValue();
            }
        }
        while (true);

        float f = l2;

        if (l2 < l1)
        {
            l2 = l1;
        }

        if (l < l2)
        {
            l = l2;
        }

        Iterator iterator1 = profilingMap.keySet().iterator();

        do
        {
            if (!iterator1.hasNext())
            {
                break;
            }

            String s2 = (String)iterator1.next();

            if (s2.length() > par0Str.length() && s2.startsWith(par0Str) && s2.indexOf(".", par0Str.length() + 1) < 0)
            {
                long l3 = ((Long)profilingMap.get(s2)).longValue();
                double d = ((double)l3 * 100D) / (double)l2;
                double d1 = ((double)l3 * 100D) / (double)l;
                String s4 = s2.substring(par0Str.length());
                arraylist.add(new ProfilerResult(s4, d, d1));
            }
        }
        while (true);

        String s3;

        for (Iterator iterator2 = profilingMap.keySet().iterator(); iterator2.hasNext(); profilingMap.put(s3, Long.valueOf((((Long)profilingMap.get(s3)).longValue() * 999L) / 1000L)))
        {
            s3 = (String)iterator2.next();
        }

        if ((float)l2 > f)
        {
            arraylist.add(new ProfilerResult("unspecified", ((double)((float)l2 - f) * 100D) / (double)l2, ((double)((float)l2 - f) * 100D) / (double)l));
        }

        Collections.sort(arraylist);
        arraylist.add(0, new ProfilerResult(s, 100D, ((double)l2 * 100D) / (double)l));
        System.out.println("profiler data size: " + arraylist.size());
        return arraylist;
    }

    /**
     * End current section and start a new section
     */
    public static void endStartSection(String par0Str)
    {
        if (!profilerLocalEnabled)
        {
            return;
        }
        else
        {
            endSection();
            startSection(par0Str);
            return;
        }
    }

    static
    {
        profilerGlobalEnabled = true;
        profilerLocalEnabled = profilerGlobalEnabled;
    }
}
