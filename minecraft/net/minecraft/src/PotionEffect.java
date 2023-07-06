package net.minecraft.src;

import java.io.PrintStream;

public class PotionEffect
{
    /** ID value of the potion this effect matches. */
    private int potionID;

    /** The duration of the potion effect */
    private int duration;

    /** The amplifier of the potion effect */
    private int amplifier;

    public PotionEffect(int par1, int par2, int par3)
    {
        potionID = par1;
        duration = par2;
        amplifier = par3;
    }

    public PotionEffect(PotionEffect par1PotionEffect)
    {
        potionID = par1PotionEffect.potionID;
        duration = par1PotionEffect.duration;
        amplifier = par1PotionEffect.amplifier;
    }

    /**
     * merges the input PotionEffect into this one if this.amplifier <= tomerge.amplifier. The duration in the supplied
     * potion effect is assumed to be greater.
     */
    public void combine(PotionEffect par1PotionEffect)
    {
        if (potionID != par1PotionEffect.potionID)
        {
            System.err.println("This method should only be called for matching effects!");
        }

        if (par1PotionEffect.amplifier > amplifier)
        {
            amplifier = par1PotionEffect.amplifier;
            duration = par1PotionEffect.duration;
        }
        else if (par1PotionEffect.amplifier == amplifier && duration < par1PotionEffect.duration)
        {
            duration = par1PotionEffect.duration;
        }
    }

    /**
     * Retrieve the ID of the potion this effect matches.
     */
    public int getPotionID()
    {
        return potionID;
    }

    public int getDuration()
    {
        return duration;
    }

    public int getAmplifier()
    {
        return amplifier;
    }

    public boolean onUpdate(EntityLiving par1EntityLiving)
    {
        if (duration > 0)
        {
            if (Potion.potionTypes[potionID].isReady(duration, amplifier))
            {
                performEffect(par1EntityLiving);
            }

            deincrementDuration();
        }

        return duration > 0;
    }

    private int deincrementDuration()
    {
        return --duration;
    }

    public void performEffect(EntityLiving par1EntityLiving)
    {
        if (duration > 0)
        {
            Potion.potionTypes[potionID].performEffect(par1EntityLiving, amplifier);
        }
    }

    public String getEffectName()
    {
        return Potion.potionTypes[potionID].getName();
    }

    public int hashCode()
    {
        return potionID;
    }

    public String toString()
    {
        String s = "";

        if (getAmplifier() > 0)
        {
            s = (new StringBuilder()).append(getEffectName()).append(" x ").append(getAmplifier() + 1).append(", Duration: ").append(getDuration()).toString();
        }
        else
        {
            s = (new StringBuilder()).append(getEffectName()).append(", Duration: ").append(getDuration()).toString();
        }

        if (Potion.potionTypes[potionID].isUsable())
        {
            return (new StringBuilder()).append("(").append(s).append(")").toString();
        }
        else
        {
            return s;
        }
    }

    public boolean equals(Object par1Obj)
    {
        if (!(par1Obj instanceof PotionEffect))
        {
            return false;
        }
        else
        {
            PotionEffect potioneffect = (PotionEffect)par1Obj;
            return potionID == potioneffect.potionID && amplifier == potioneffect.amplifier && duration == potioneffect.duration;
        }
    }
}
