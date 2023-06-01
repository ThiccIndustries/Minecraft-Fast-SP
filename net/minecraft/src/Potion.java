package net.minecraft.src;

public class Potion
{
    public static final Potion potionTypes[] = new Potion[32];
    public static final Potion field_35676_b = null;
    public static final Potion moveSpeed = (new Potion(1, false, 0x7cafc6)).setPotionName("potion.moveSpeed").setIconIndex(0, 0);
    public static final Potion moveSlowdown = (new Potion(2, true, 0x5a6c81)).setPotionName("potion.moveSlowdown").setIconIndex(1, 0);
    public static final Potion digSpeed = (new Potion(3, false, 0xd9c043)).setPotionName("potion.digSpeed").setIconIndex(2, 0).setEffectiveness(1.5D);
    public static final Potion digSlowdown = (new Potion(4, true, 0x4a4217)).setPotionName("potion.digSlowDown").setIconIndex(3, 0);
    public static final Potion damageBoost = (new Potion(5, false, 0x932423)).setPotionName("potion.damageBoost").setIconIndex(4, 0);
    public static final Potion heal = (new PotionHealth(6, false, 0xf82423)).setPotionName("potion.heal");
    public static final Potion harm = (new PotionHealth(7, true, 0x430a09)).setPotionName("potion.harm");
    public static final Potion jump = (new Potion(8, false, 0x786297)).setPotionName("potion.jump").setIconIndex(2, 1);
    public static final Potion confusion = (new Potion(9, true, 0x551d4a)).setPotionName("potion.confusion").setIconIndex(3, 1).setEffectiveness(0.25D);

    /** The regeneration Potion object. */
    public static final Potion regeneration = (new Potion(10, false, 0xcd5cab)).setPotionName("potion.regeneration").setIconIndex(7, 0).setEffectiveness(0.25D);
    public static final Potion resistance = (new Potion(11, false, 0x99453a)).setPotionName("potion.resistance").setIconIndex(6, 1);

    /** The fire resistance Potion object. */
    public static final Potion fireResistance = (new Potion(12, false, 0xe49a3a)).setPotionName("potion.fireResistance").setIconIndex(7, 1);

    /** The water breathing Potion object. */
    public static final Potion waterBreathing = (new Potion(13, false, 0x2e5299)).setPotionName("potion.waterBreathing").setIconIndex(0, 2);

    /** The invisibility Potion object. */
    public static final Potion invisibility = (new Potion(14, false, 0x7f8392)).setPotionName("potion.invisibility").setIconIndex(0, 1).setPotionUnusable();

    /** The blindness Potion object. */
    public static final Potion blindness = (new Potion(15, true, 0x1f1f23)).setPotionName("potion.blindness").setIconIndex(5, 1).setEffectiveness(0.25D);

    /** The night vision Potion object. */
    public static final Potion nightVision = (new Potion(16, false, 0x1f1fa1)).setPotionName("potion.nightVision").setIconIndex(4, 1).setPotionUnusable();

    /** The hunger Potion object. */
    public static final Potion hunger = (new Potion(17, true, 0x587653)).setPotionName("potion.hunger").setIconIndex(1, 1);

    /** The weakness Potion object. */
    public static final Potion weakness = (new Potion(18, true, 0x484d48)).setPotionName("potion.weakness").setIconIndex(5, 0);

    /** The poison Potion object. */
    public static final Potion poison = (new Potion(19, true, 0x4e9331)).setPotionName("potion.poison").setIconIndex(6, 0).setEffectiveness(0.25D);
    public static final Potion field_35688_v = null;
    public static final Potion field_35687_w = null;
    public static final Potion field_35697_x = null;
    public static final Potion field_35696_y = null;
    public static final Potion field_35695_z = null;
    public static final Potion field_35667_A = null;
    public static final Potion field_35668_B = null;
    public static final Potion field_35669_C = null;
    public static final Potion field_35663_D = null;
    public static final Potion field_35664_E = null;
    public static final Potion field_35665_F = null;
    public static final Potion field_35666_G = null;

    /** The Id of a Potion object. */
    public final int id;

    /** The name of the Potion. */
    private String name;

    /** The index for the icon displayed when the potion effect is active. */
    private int statusIconIndex;

    /**
     * This field indicated if the effect is 'bad' - negative - for the entity.
     */
    private final boolean isBadEffect;
    private double effectiveness;
    private boolean usable;

    /** Is the color of the liquid for this potion. */
    private final int liquidColor;

    protected Potion(int par1, boolean par2, int par3)
    {
        name = "";
        statusIconIndex = -1;
        id = par1;
        potionTypes[par1] = this;
        isBadEffect = par2;

        if (par2)
        {
            effectiveness = 0.5D;
        }
        else
        {
            effectiveness = 1.0D;
        }

        liquidColor = par3;
    }

    /**
     * Sets the index for the icon displayed in the player's inventory when the status is active.
     */
    protected Potion setIconIndex(int par1, int par2)
    {
        statusIconIndex = par1 + par2 * 8;
        return this;
    }

    /**
     * returns the ID of the potion
     */
    public int getId()
    {
        return id;
    }

    public void performEffect(EntityLiving par1EntityLiving, int par2)
    {
        if (id == regeneration.id)
        {
            if (par1EntityLiving.getHealth() < par1EntityLiving.getMaxHealth())
            {
                par1EntityLiving.heal(1);
            }
        }
        else if (id == poison.id)
        {
            if (par1EntityLiving.getHealth() > 1)
            {
                par1EntityLiving.attackEntityFrom(DamageSource.magic, 1);
            }
        }
        else if (id == hunger.id && (par1EntityLiving instanceof EntityPlayer))
        {
            ((EntityPlayer)par1EntityLiving).addExhaustion(0.025F * (float)(par2 + 1));
        }
        else if (id == heal.id && !par1EntityLiving.isEntityUndead() || id == harm.id && par1EntityLiving.isEntityUndead())
        {
            par1EntityLiving.heal(6 << par2);
        }
        else if (id == harm.id && !par1EntityLiving.isEntityUndead() || id == heal.id && par1EntityLiving.isEntityUndead())
        {
            par1EntityLiving.attackEntityFrom(DamageSource.magic, 6 << par2);
        }
    }

    /**
     * Hits the provided entity with this potion's instant effect.
     */
    public void affectEntity(EntityLiving par1EntityLiving, EntityLiving par2EntityLiving, int par3, double par4)
    {
        if (id == heal.id && !par2EntityLiving.isEntityUndead() || id == harm.id && par2EntityLiving.isEntityUndead())
        {
            int i = (int)(par4 * (double)(6 << par3) + 0.5D);
            par2EntityLiving.heal(i);
        }
        else if (id == harm.id && !par2EntityLiving.isEntityUndead() || id == heal.id && par2EntityLiving.isEntityUndead())
        {
            int j = (int)(par4 * (double)(6 << par3) + 0.5D);

            if (par1EntityLiving == null)
            {
                par2EntityLiving.attackEntityFrom(DamageSource.magic, j);
            }
            else
            {
                par2EntityLiving.attackEntityFrom(DamageSource.causeIndirectMagicDamage(par2EntityLiving, par1EntityLiving), j);
            }
        }
    }

    /**
     * Returns true if the potion has an instant effect instead of a continuous one (eg Harming)
     */
    public boolean isInstant()
    {
        return false;
    }

    /**
     * checks if Potion effect is ready to be applied this tick.
     */
    public boolean isReady(int par1, int par2)
    {
        if (id == regeneration.id || id == poison.id)
        {
            int i = 25 >> par2;

            if (i > 0)
            {
                return par1 % i == 0;
            }
            else
            {
                return true;
            }
        }

        return id == hunger.id;
    }

    /**
     * Set the potion name.
     */
    public Potion setPotionName(String par1Str)
    {
        name = par1Str;
        return this;
    }

    /**
     * returns the name of the potion
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns true if the potion has a associated status icon to display in then inventory when active.
     */
    public boolean hasStatusIcon()
    {
        return statusIconIndex >= 0;
    }

    /**
     * Returns the index for the icon to display when the potion is active.
     */
    public int getStatusIconIndex()
    {
        return statusIconIndex;
    }

    /**
     * This method returns true if the potion effect is bad - negative - for the entity.
     */
    public boolean isBadEffect()
    {
        return isBadEffect;
    }

    public static String getDurationString(PotionEffect par0PotionEffect)
    {
        int i = par0PotionEffect.getDuration();
        int j = i / 20;
        int k = j / 60;
        j %= 60;

        if (j < 10)
        {
            return (new StringBuilder()).append(k).append(":0").append(j).toString();
        }
        else
        {
            return (new StringBuilder()).append(k).append(":").append(j).toString();
        }
    }

    protected Potion setEffectiveness(double par1)
    {
        effectiveness = par1;
        return this;
    }

    public double getEffectiveness()
    {
        return effectiveness;
    }

    public Potion setPotionUnusable()
    {
        usable = true;
        return this;
    }

    public boolean isUsable()
    {
        return usable;
    }

    /**
     * Returns the color of the potion liquid.
     */
    public int getLiquidColor()
    {
        return liquidColor;
    }
}
