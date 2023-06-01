package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;

public class PlayerControllerCreative extends PlayerController
{
    private int field_35647_c;

    public PlayerControllerCreative(Minecraft par1Minecraft)
    {
        super(par1Minecraft);
        isInTestMode = true;
    }

    /**
     * Enables creative abilities to the player
     */
    public static void enableAbilities(EntityPlayer par0EntityPlayer)
    {
        par0EntityPlayer.capabilities.allowFlying = true;
        par0EntityPlayer.capabilities.isCreativeMode = true;
        par0EntityPlayer.capabilities.disableDamage = true;
    }

    /**
     * Disables creative abilities to the player.
     */
    public static void disableAbilities(EntityPlayer par0EntityPlayer)
    {
        par0EntityPlayer.capabilities.allowFlying = false;
        par0EntityPlayer.capabilities.isFlying = false;
        par0EntityPlayer.capabilities.isCreativeMode = false;
        par0EntityPlayer.capabilities.disableDamage = false;
    }

    public void func_6473_b(EntityPlayer par1EntityPlayer)
    {
        enableAbilities(par1EntityPlayer);

        for (int i = 0; i < 9; i++)
        {
            if (par1EntityPlayer.inventory.mainInventory[i] == null)
            {
                par1EntityPlayer.inventory.mainInventory[i] = new ItemStack((Block)Session.registeredBlocksList.get(i));
            }
        }
    }

    /**
     * Called from a PlayerController when the player is hitting a block with an item in Creative mode. Args: Minecraft
     * instance, player controller, x, y, z, side
     */
    public static void clickBlockCreative(Minecraft par0Minecraft, PlayerController par1PlayerController, int par2, int par3, int par4, int par5)
    {
        if (!par0Minecraft.theWorld.func_48457_a(par0Minecraft.thePlayer, par2, par3, par4, par5))
        {
            par1PlayerController.onPlayerDestroyBlock(par2, par3, par4, par5);
        }
    }

    /**
     * Handles a players right click
     */
    public boolean onPlayerRightClick(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack, int par4, int par5, int par6, int par7)
    {
        int i = par2World.getBlockId(par4, par5, par6);

        if (i > 0 && Block.blocksList[i].blockActivated(par2World, par4, par5, par6, par1EntityPlayer))
        {
            return true;
        }

        if (par3ItemStack == null)
        {
            return false;
        }
        else
        {
            int j = par3ItemStack.getItemDamage();
            int k = par3ItemStack.stackSize;
            boolean flag = par3ItemStack.useItem(par1EntityPlayer, par2World, par4, par5, par6, par7);
            par3ItemStack.setItemDamage(j);
            par3ItemStack.stackSize = k;
            return flag;
        }
    }

    /**
     * Called by Minecraft class when the player is hitting a block with an item. Args: x, y, z, side
     */
    public void clickBlock(int par1, int par2, int par3, int par4)
    {
        clickBlockCreative(mc, this, par1, par2, par3, par4);
        field_35647_c = 5;
    }

    /**
     * Called when a player damages a block and updates damage counters
     */
    public void onPlayerDamageBlock(int par1, int par2, int par3, int par4)
    {
        field_35647_c--;

        if (field_35647_c <= 0)
        {
            field_35647_c = 5;
            clickBlockCreative(mc, this, par1, par2, par3, par4);
        }
    }

    /**
     * Resets current block damage and isHittingBlock
     */
    public void resetBlockRemoving()
    {
    }

    public boolean shouldDrawHUD()
    {
        return false;
    }

    /**
     * Called on world change with the new World as the only parameter.
     */
    public void onWorldChange(World par1World)
    {
        super.onWorldChange(par1World);
    }

    /**
     * player reach distance = 4F
     */
    public float getBlockReachDistance()
    {
        return 5F;
    }

    /**
     * Checks if the player is not creative, used for checking if it should break a block instantly
     */
    public boolean isNotCreative()
    {
        return false;
    }

    /**
     * returns true if player is in creative mode
     */
    public boolean isInCreativeMode()
    {
        return true;
    }

    /**
     * true for hitting entities far away.
     */
    public boolean extendedReach()
    {
        return true;
    }
}
