package net.minecraft.src;

public enum EnumEnchantmentType
{
    all,
    armor,
    armor_feet,
    armor_legs,
    armor_torso,
    armor_head,
    weapon,
    digger,
    bow;

    /**
     * Return true if the item passed can be enchanted by a enchantment of this type.
     */
    public boolean canEnchantItem(Item par1Item)
    {
        if (this == all)
        {
            return true;
        }

        if (par1Item instanceof ItemArmor)
        {
            if (this == armor)
            {
                return true;
            }

            ItemArmor itemarmor = (ItemArmor)par1Item;

            if (itemarmor.armorType == 0)
            {
                return this == armor_head;
            }

            if (itemarmor.armorType == 2)
            {
                return this == armor_legs;
            }

            if (itemarmor.armorType == 1)
            {
                return this == armor_torso;
            }

            if (itemarmor.armorType == 3)
            {
                return this == armor_feet;
            }
            else
            {
                return false;
            }
        }

        if (par1Item instanceof ItemSword)
        {
            return this == weapon;
        }

        if (par1Item instanceof ItemTool)
        {
            return this == digger;
        }

        if (par1Item instanceof ItemBow)
        {
            return this == bow;
        }
        else
        {
            return false;
        }
    }
}
