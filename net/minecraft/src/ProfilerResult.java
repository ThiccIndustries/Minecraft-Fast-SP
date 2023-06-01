package net.minecraft.src;

public final class ProfilerResult implements Comparable
{
    /**
     * Percentage of time spent in this ProfilerResult relative to its parent ProfilerResult
     */
    public double sectionPercentage;

    /**
     * Percentage of time spent in this ProfilerResult relative to the entire game
     */
    public double globalPercentage;

    /** The name of this ProfilerResult */
    public String name;

    public ProfilerResult(String par1Str, double par2, double par4)
    {
        name = par1Str;
        sectionPercentage = par2;
        globalPercentage = par4;
    }

    /**
     * Called from compareTo()
     */
    public int compareProfilerResult(ProfilerResult par1ProfilerResult)
    {
        if (par1ProfilerResult.sectionPercentage < sectionPercentage)
        {
            return -1;
        }

        if (par1ProfilerResult.sectionPercentage > sectionPercentage)
        {
            return 1;
        }
        else
        {
            return par1ProfilerResult.name.compareTo(name);
        }
    }

    /**
     * Compute the color used to display this ProfilerResult on the debug screen
     */
    public int getDisplayColor()
    {
        return (name.hashCode() & 0xaaaaaa) + 0x444444;
    }

    public int compareTo(Object par1Obj)
    {
        return compareProfilerResult((ProfilerResult)par1Obj);
    }
}
