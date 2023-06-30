package net.minecraft.src;

public class Timer
{
    /** The number of timer ticks per second of real time */
    float ticksPerSecond;

    /**
     * The time reported by the high-resolution clock at the last call of updateTimer(), in seconds
     */
    private double lastHRTime;

    /**
     * How many full ticks have turned over since the last call to updateTimer(), capped at 10.
     */
    public int elapsedTicks;

    /**
     * How much time has elapsed since the last tick, in ticks, for use by display rendering routines (range: 0.0 -
     * 1.0).  This field is frozen if the display is paused to eliminate jitter.
     */
    public float renderPartialTicks;

    /**
     * A multiplier to make the timer (and therefore the game) go faster or slower.  0.5 makes the game run at half-
     * speed.
     */
    public float timerSpeed;

    /**
     * How much time has elapsed since the last tick, in ticks (range: 0.0 - 1.0).
     */
    public float elapsedPartialTicks;

    /**
     * The time reported by the system clock at the last sync, in milliseconds
     */
    private long lastSyncSysClock;

    /**
     * The time reported by the high-resolution clock at the last sync, in milliseconds
     */
    private long lastSyncHRClock;
    private long field_28132_i;

    /**
     * A ratio used to sync the high-resolution clock to the system clock, updated once per second
     */
    private double timeSyncAdjustment;

    public Timer(float par1)
    {
        timerSpeed = 1.0F;
        elapsedPartialTicks = 0.0F;
        timeSyncAdjustment = 1.0D;
        ticksPerSecond = par1;
        lastSyncSysClock = System.currentTimeMillis();
        lastSyncHRClock = System.nanoTime() / 0xf4240L;
    }

    /**
     * Updates all fields of the Timer using the current time
     */
    public void updateTimer()
    {
        long l = System.currentTimeMillis();
        long l1 = l - lastSyncSysClock;
        long l2 = System.nanoTime() / 0xf4240L;
        double d = (double)l2 / 1000D;

        if (l1 > 1000L)
        {
            lastHRTime = d;
        }
        else if (l1 < 0L)
        {
            lastHRTime = d;
        }
        else
        {
            field_28132_i += l1;

            if (field_28132_i > 1000L)
            {
                long l3 = l2 - lastSyncHRClock;
                double d2 = (double)field_28132_i / (double)l3;
                timeSyncAdjustment += (d2 - timeSyncAdjustment) * 0.20000000298023224D;
                lastSyncHRClock = l2;
                field_28132_i = 0L;
            }

            if (field_28132_i < 0L)
            {
                lastSyncHRClock = l2;
            }
        }

        lastSyncSysClock = l;
        double d1 = (d - lastHRTime) * timeSyncAdjustment;
        lastHRTime = d;

        if (d1 < 0.0D)
        {
            d1 = 0.0D;
        }

        if (d1 > 1.0D)
        {
            d1 = 1.0D;
        }

        elapsedPartialTicks += d1 * (double)timerSpeed * (double)ticksPerSecond;
        elapsedTicks = (int)elapsedPartialTicks;
        elapsedPartialTicks -= elapsedTicks;

        if (elapsedTicks > 10)
        {
            elapsedTicks = 10;
        }

        renderPartialTicks = elapsedPartialTicks;
    }
}
