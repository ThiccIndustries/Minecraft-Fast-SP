package net.minecraft.src;

class OsMap
{
    static final int osValues[];

    static
    {
        osValues = new int[EnumOS1.values().length];

        try
        {
            osValues[EnumOS1.linux.ordinal()] = 1;
        }
        catch (NoSuchFieldError nosuchfielderror) { }

        try
        {
            osValues[EnumOS1.solaris.ordinal()] = 2;
        }
        catch (NoSuchFieldError nosuchfielderror1) { }

        try
        {
            osValues[EnumOS1.windows.ordinal()] = 3;
        }
        catch (NoSuchFieldError nosuchfielderror2) { }

        try
        {
            osValues[EnumOS1.macos.ordinal()] = 4;
        }
        catch (NoSuchFieldError nosuchfielderror3) { }
    }
}
