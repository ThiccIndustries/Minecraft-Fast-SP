package net.minecraft.src;

import java.util.*;

public class PotionHelper
{
    public static final String field_40367_a = null;
    public static final String sugarEffect = "-0+1-2-3&4-4+13";
    public static final String ghastTearEffect = "+0-1-2-3&4-4+13";
    public static final String spiderEyeEffect = "-0-1+2-3&4-4+13";
    public static final String fermentedSpiderEyeEffect = "-0+3-4+13";
    public static final String speckledMelonEffect = "+0-1+2-3&4-4+13";
    public static final String blazePowderEffect = "+0-1-2+3&4-4+13";
    public static final String magmaCreamEffect = "+0+1-2-3&4-4+13";
    public static final String redstoneEffect = "-5+6-7";
    public static final String glowstoneEffect = "+5-6-7";
    public static final String gunpowderEffect = "+14&13-13";
    private static final HashMap potionRequirements;
    private static final HashMap field_40371_m;
    private static final HashMap field_40368_n = new HashMap();
    private static final String potionPrefixes[] =
    {
        "potion.prefix.mundane", "potion.prefix.uninteresting", "potion.prefix.bland", "potion.prefix.clear", "potion.prefix.milky", "potion.prefix.diffuse", "potion.prefix.artless", "potion.prefix.thin", "potion.prefix.awkward", "potion.prefix.flat",
        "potion.prefix.bulky", "potion.prefix.bungling", "potion.prefix.buttered", "potion.prefix.smooth", "potion.prefix.suave", "potion.prefix.debonair", "potion.prefix.thick", "potion.prefix.elegant", "potion.prefix.fancy", "potion.prefix.charming",
        "potion.prefix.dashing", "potion.prefix.refined", "potion.prefix.cordial", "potion.prefix.sparkling", "potion.prefix.potent", "potion.prefix.foul", "potion.prefix.odorless", "potion.prefix.rank", "potion.prefix.harsh", "potion.prefix.acrid",
        "potion.prefix.gross", "potion.prefix.stinky"
    };

    public PotionHelper()
    {
    }

    /**
     * Is the bit given set to 1?
     */
    public static boolean checkFlag(int par0, int par1)
    {
        return (par0 & 1 << par1) != 0;
    }

    /**
     * Returns 1 if the flag is set, 0 if it is not set.
     */
    private static int isFlagSet(int par0, int par1)
    {
        return checkFlag(par0, par1) ? 1 : 0;
    }

    /**
     * Returns 0 if the flag is set, 1 if it is not set.
     */
    private static int isFlagUnset(int par0, int par1)
    {
        return checkFlag(par0, par1) ? 0 : 1;
    }

    public static int func_40352_a(int par0)
    {
        return func_40351_a(par0, 5, 4, 3, 2, 1);
    }

    public static int func_40354_a(Collection par0Collection)
    {
        int i = 0x385dc6;

        if (par0Collection == null || par0Collection.isEmpty())
        {
            return i;
        }

        float f = 0.0F;
        float f1 = 0.0F;
        float f2 = 0.0F;
        float f3 = 0.0F;

        for (Iterator iterator = par0Collection.iterator(); iterator.hasNext();)
        {
            PotionEffect potioneffect = (PotionEffect)iterator.next();
            int j = Potion.potionTypes[potioneffect.getPotionID()].getLiquidColor();
            int k = 0;

            while (k <= potioneffect.getAmplifier())
            {
                f += (float)(j >> 16 & 0xff) / 255F;
                f1 += (float)(j >> 8 & 0xff) / 255F;
                f2 += (float)(j >> 0 & 0xff) / 255F;
                f3++;
                k++;
            }
        }

        f = (f / f3) * 255F;
        f1 = (f1 / f3) * 255F;
        f2 = (f2 / f3) * 255F;
        return (int)f << 16 | (int)f1 << 8 | (int)f2;
    }

    public static int func_40358_a(int par0, boolean par1)
    {
        if (!par1)
        {
            if (field_40368_n.containsKey(Integer.valueOf(par0)))
            {
                return ((Integer)field_40368_n.get(Integer.valueOf(par0))).intValue();
            }
            else
            {
                int i = func_40354_a(getPotionEffects(par0, false));
                field_40368_n.put(Integer.valueOf(par0), Integer.valueOf(i));
                return i;
            }
        }
        else
        {
            return func_40354_a(getPotionEffects(par0, par1));
        }
    }

    public static String func_40359_b(int par0)
    {
        int i = func_40352_a(par0);
        return potionPrefixes[i];
    }

    private static int func_40347_a(boolean par0, boolean par1, boolean par2, int par3, int par4, int par5, int par6)
    {
        int i = 0;

        if (par0)
        {
            i = isFlagUnset(par6, par4);
        }
        else if (par3 != -1)
        {
            if (par3 == 0 && countSetFlags(par6) == par4)
            {
                i = 1;
            }
            else if (par3 == 1 && countSetFlags(par6) > par4)
            {
                i = 1;
            }
            else if (par3 == 2 && countSetFlags(par6) < par4)
            {
                i = 1;
            }
        }
        else
        {
            i = isFlagSet(par6, par4);
        }

        if (par1)
        {
            i *= par5;
        }

        if (par2)
        {
            i *= -1;
        }

        return i;
    }

    /**
     * Count the number of bits in an integer set to ON.
     */
    private static int countSetFlags(int par0)
    {
        int i;

        for (i = 0; par0 > 0; i++)
        {
            par0 &= par0 - 1;
        }

        return i;
    }

    private static int func_40355_a(String par0Str, int par1, int par2, int par3)
    {
        if (par1 >= par0Str.length() || par2 < 0 || par1 >= par2)
        {
            return 0;
        }

        int i = par0Str.indexOf('|', par1);

        if (i >= 0 && i < par2)
        {
            int j = func_40355_a(par0Str, par1, i - 1, par3);

            if (j > 0)
            {
                return j;
            }

            int l = func_40355_a(par0Str, i + 1, par2, par3);

            if (l > 0)
            {
                return l;
            }
            else
            {
                return 0;
            }
        }

        int k = par0Str.indexOf('&', par1);

        if (k >= 0 && k < par2)
        {
            int i1 = func_40355_a(par0Str, par1, k - 1, par3);

            if (i1 <= 0)
            {
                return 0;
            }

            int j1 = func_40355_a(par0Str, k + 1, par2, par3);

            if (j1 <= 0)
            {
                return 0;
            }

            if (i1 > j1)
            {
                return i1;
            }
            else
            {
                return j1;
            }
        }

        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        boolean flag4 = false;
        byte byte0 = -1;
        int k1 = 0;
        int l1 = 0;
        int i2 = 0;

        for (int j2 = par1; j2 < par2; j2++)
        {
            char c = par0Str.charAt(j2);

            if (c >= '0' && c <= '9')
            {
                if (flag)
                {
                    l1 = c - 48;
                    flag1 = true;
                }
                else
                {
                    k1 *= 10;
                    k1 += c - 48;
                    flag2 = true;
                }

                continue;
            }

            if (c == '*')
            {
                flag = true;
                continue;
            }

            if (c == '!')
            {
                if (flag2)
                {
                    i2 += func_40347_a(flag3, flag1, flag4, byte0, k1, l1, par3);
                    flag2 = flag1 = flag = flag4 = flag3 = false;
                    k1 = l1 = 0;
                    byte0 = -1;
                }

                flag3 = true;
                continue;
            }

            if (c == '-')
            {
                if (flag2)
                {
                    i2 += func_40347_a(flag3, flag1, flag4, byte0, k1, l1, par3);
                    flag2 = flag1 = flag = flag4 = flag3 = false;
                    k1 = l1 = 0;
                    byte0 = -1;
                }

                flag4 = true;
                continue;
            }

            if (c == '=' || c == '<' || c == '>')
            {
                if (flag2)
                {
                    i2 += func_40347_a(flag3, flag1, flag4, byte0, k1, l1, par3);
                    flag2 = flag1 = flag = flag4 = flag3 = false;
                    k1 = l1 = 0;
                    byte0 = -1;
                }

                if (c == '=')
                {
                    byte0 = 0;
                    continue;
                }

                if (c == '<')
                {
                    byte0 = 2;
                    continue;
                }

                if (c == '>')
                {
                    byte0 = 1;
                }

                continue;
            }

            if (c == '+' && flag2)
            {
                i2 += func_40347_a(flag3, flag1, flag4, byte0, k1, l1, par3);
                flag2 = flag1 = flag = flag4 = flag3 = false;
                k1 = l1 = 0;
                byte0 = -1;
            }
        }

        if (flag2)
        {
            i2 += func_40347_a(flag3, flag1, flag4, byte0, k1, l1, par3);
        }

        return i2;
    }

    /**
     * Returns a list of effects for the specified potion damage value.
     */
    public static List getPotionEffects(int par0, boolean par1)
    {
        ArrayList arraylist = null;
        Potion apotion[] = Potion.potionTypes;
        int i = apotion.length;

        for (int j = 0; j < i; j++)
        {
            Potion potion = apotion[j];

            if (potion == null || potion.isUsable() && !par1)
            {
                continue;
            }

            String s = (String)potionRequirements.get(Integer.valueOf(potion.getId()));

            if (s == null)
            {
                continue;
            }

            int k = func_40355_a(s, 0, s.length(), par0);

            if (k <= 0)
            {
                continue;
            }

            int l = 0;
            String s1 = (String)field_40371_m.get(Integer.valueOf(potion.getId()));

            if (s1 != null)
            {
                l = func_40355_a(s1, 0, s1.length(), par0);

                if (l < 0)
                {
                    l = 0;
                }
            }

            if (potion.isInstant())
            {
                k = 1;
            }
            else
            {
                k = 1200 * (k * 3 + (k - 1) * 2);
                k >>= l;
                k = (int)Math.round((double)k * potion.getEffectiveness());

                if ((par0 & 0x4000) != 0)
                {
                    k = (int)Math.round((double)k * 0.75D + 0.5D);
                }
            }

            if (arraylist == null)
            {
                arraylist = new ArrayList();
            }

            arraylist.add(new PotionEffect(potion.getId(), k, l));
        }

        return arraylist;
    }

    /**
     * Does bit operations for brewPotionData, given data, the index of the bit being operated upon, whether the bit
     * will be removed, whether the bit will be toggled (NOT), or whether the data field will be set to 0 if the bit is
     * not present.
     */
    private static int brewBitOperations(int par0, int par1, boolean par2, boolean par3, boolean par4)
    {
        if (par4)
        {
            if (!checkFlag(par0, par1))
            {
                return 0;
            }
        }
        else if (par2)
        {
            par0 &= ~(1 << par1);
        }
        else if (par3)
        {
            if ((par0 & 1 << par1) != 0)
            {
                par0 &= ~(1 << par1);
            }
            else
            {
                par0 |= 1 << par1;
            }
        }
        else
        {
            par0 |= 1 << par1;
        }

        return par0;
    }

    /**
     * Generate a data value for a potion, given its previous data value and the encoded string of new effects it will
     * receive
     */
    public static int applyIngredient(int par0, String par1Str)
    {
        boolean flag = false;
        int i = par1Str.length();
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        boolean flag4 = false;
        int j = 0;

        for (int k = ((flag) ? 1 : 0); k < i; k++)
        {
            char c = par1Str.charAt(k);

            if (c >= '0' && c <= '9')
            {
                j *= 10;
                j += c - 48;
                flag1 = true;
                continue;
            }

            if (c == '!')
            {
                if (flag1)
                {
                    par0 = brewBitOperations(par0, j, flag3, flag2, flag4);
                    flag1 = flag3 = flag2 = flag4 = false;
                    j = 0;
                }

                flag2 = true;
                continue;
            }

            if (c == '-')
            {
                if (flag1)
                {
                    par0 = brewBitOperations(par0, j, flag3, flag2, flag4);
                    flag1 = flag3 = flag2 = flag4 = false;
                    j = 0;
                }

                flag3 = true;
                continue;
            }

            if (c == '+')
            {
                if (flag1)
                {
                    par0 = brewBitOperations(par0, j, flag3, flag2, flag4);
                    flag1 = flag3 = flag2 = flag4 = false;
                    j = 0;
                }

                continue;
            }

            if (c != '&')
            {
                continue;
            }

            if (flag1)
            {
                par0 = brewBitOperations(par0, j, flag3, flag2, flag4);
                flag1 = flag3 = flag2 = flag4 = false;
                j = 0;
            }

            flag4 = true;
        }

        if (flag1)
        {
            par0 = brewBitOperations(par0, j, flag3, flag2, flag4);
        }

        return par0 & 0x7fff;
    }

    public static int func_40351_a(int par0, int par1, int par2, int par3, int par4, int par5)
    {
        return (checkFlag(par0, par1) ? 0x10 : 0) | (checkFlag(par0, par2) ? 8 : 0) | (checkFlag(par0, par3) ? 4 : 0) | (checkFlag(par0, par4) ? 2 : 0) | (checkFlag(par0, par5) ? 1 : 0);
    }

    static
    {
        potionRequirements = new HashMap();
        field_40371_m = new HashMap();
        potionRequirements.put(Integer.valueOf(Potion.regeneration.getId()), "0 & !1 & !2 & !3 & 0+6");
        potionRequirements.put(Integer.valueOf(Potion.moveSpeed.getId()), "!0 & 1 & !2 & !3 & 1+6");
        potionRequirements.put(Integer.valueOf(Potion.fireResistance.getId()), "0 & 1 & !2 & !3 & 0+6");
        potionRequirements.put(Integer.valueOf(Potion.heal.getId()), "0 & !1 & 2 & !3");
        potionRequirements.put(Integer.valueOf(Potion.poison.getId()), "!0 & !1 & 2 & !3 & 2+6");
        potionRequirements.put(Integer.valueOf(Potion.weakness.getId()), "!0 & !1 & !2 & 3 & 3+6");
        potionRequirements.put(Integer.valueOf(Potion.harm.getId()), "!0 & !1 & 2 & 3");
        potionRequirements.put(Integer.valueOf(Potion.moveSlowdown.getId()), "!0 & 1 & !2 & 3 & 3+6");
        potionRequirements.put(Integer.valueOf(Potion.damageBoost.getId()), "0 & !1 & !2 & 3 & 3+6");
        field_40371_m.put(Integer.valueOf(Potion.moveSpeed.getId()), "5");
        field_40371_m.put(Integer.valueOf(Potion.digSpeed.getId()), "5");
        field_40371_m.put(Integer.valueOf(Potion.damageBoost.getId()), "5");
        field_40371_m.put(Integer.valueOf(Potion.regeneration.getId()), "5");
        field_40371_m.put(Integer.valueOf(Potion.harm.getId()), "5");
        field_40371_m.put(Integer.valueOf(Potion.heal.getId()), "5");
        field_40371_m.put(Integer.valueOf(Potion.resistance.getId()), "5");
        field_40371_m.put(Integer.valueOf(Potion.poison.getId()), "5");
    }
}
