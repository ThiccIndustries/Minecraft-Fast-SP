package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class PlayerControllerMP extends PlayerController
{
    /** PosX of the current block being destroyed */
    private int currentBlockX;

    /** PosY of the current block being destroyed */
    private int currentBlockY;

    /** PosZ of the current block being destroyed */
    private int currentblockZ;

    /** Current block damage (MP) */
    private float curBlockDamageMP;

    /** Previous block damage (MP) */
    private float prevBlockDamageMP;

    /**
     * Tick counter, when it hits 4 it resets back to 0 and plays the step sound
     */
    private float stepSoundTickCounter;

    /**
     * Delays the first damage on the block after the first click on the block
     */
    private int blockHitDelay;

    /** Tells if the player is hitting a block */
    private boolean isHittingBlock;
    private boolean creativeMode;
    private NetClientHandler netClientHandler;

    /** Index of the current item held by the player in the inventory hotbar */
    private int currentPlayerItem;

    public PlayerControllerMP(Minecraft par1Minecraft, NetClientHandler par2NetClientHandler)
    {
        super(par1Minecraft);
        currentBlockX = -1;
        currentBlockY = -1;
        currentblockZ = -1;
        curBlockDamageMP = 0.0F;
        prevBlockDamageMP = 0.0F;
        stepSoundTickCounter = 0.0F;
        blockHitDelay = 0;
        isHittingBlock = false;
        currentPlayerItem = 0;
        netClientHandler = par2NetClientHandler;
    }

    public void setCreative(boolean par1)
    {
        creativeMode = par1;

        if (creativeMode)
        {
            PlayerControllerCreative.enableAbilities(mc.thePlayer);
        }
        else
        {
            PlayerControllerCreative.disableAbilities(mc.thePlayer);
        }
    }

    /**
     * Flips the player around. Args: player
     */
    public void flipPlayer(EntityPlayer par1EntityPlayer)
    {
        par1EntityPlayer.rotationYaw = -180F;
    }

    public boolean shouldDrawHUD()
    {
        return !creativeMode;
    }

    /**
     * Called when a player completes the destruction of a block
     */
    public boolean onPlayerDestroyBlock(int par1, int par2, int par3, int par4)
    {
        if (creativeMode)
        {
            return super.onPlayerDestroyBlock(par1, par2, par3, par4);
        }

        int i = mc.theWorld.getBlockId(par1, par2, par3);
        boolean flag = super.onPlayerDestroyBlock(par1, par2, par3, par4);
        ItemStack itemstack = mc.thePlayer.getCurrentEquippedItem();

        if (itemstack != null)
        {
            itemstack.onDestroyBlock(i, par1, par2, par3, mc.thePlayer);

            if (itemstack.stackSize == 0)
            {
                itemstack.onItemDestroyedByUse(mc.thePlayer);
                mc.thePlayer.destroyCurrentEquippedItem();
            }
        }

        return flag;
    }

    /**
     * Called by Minecraft class when the player is hitting a block with an item. Args: x, y, z, side
     */
    public void clickBlock(int par1, int par2, int par3, int par4)
    {
        if (creativeMode)
        {
            netClientHandler.addToSendQueue(new Packet14BlockDig(0, par1, par2, par3, par4));
            PlayerControllerCreative.clickBlockCreative(mc, this, par1, par2, par3, par4);
            blockHitDelay = 5;
        }
        else if (!isHittingBlock || par1 != currentBlockX || par2 != currentBlockY || par3 != currentblockZ)
        {
            netClientHandler.addToSendQueue(new Packet14BlockDig(0, par1, par2, par3, par4));
            int i = mc.theWorld.getBlockId(par1, par2, par3);

            if (i > 0 && curBlockDamageMP == 0.0F)
            {
                Block.blocksList[i].onBlockClicked(mc.theWorld, par1, par2, par3, mc.thePlayer);
            }

            if (i > 0 && Block.blocksList[i].blockStrength(mc.thePlayer) >= 1.0F)
            {
                onPlayerDestroyBlock(par1, par2, par3, par4);
            }
            else
            {
                isHittingBlock = true;
                currentBlockX = par1;
                currentBlockY = par2;
                currentblockZ = par3;
                curBlockDamageMP = 0.0F;
                prevBlockDamageMP = 0.0F;
                stepSoundTickCounter = 0.0F;
            }
        }
    }

    /**
     * Resets current block damage and isHittingBlock
     */
    public void resetBlockRemoving()
    {
        curBlockDamageMP = 0.0F;
        isHittingBlock = false;
    }

    /**
     * Called when a player damages a block and updates damage counters
     */
    public void onPlayerDamageBlock(int par1, int par2, int par3, int par4)
    {
        syncCurrentPlayItem();

        if (blockHitDelay > 0)
        {
            blockHitDelay--;
            return;
        }

        if (creativeMode)
        {
            blockHitDelay = 5;
            netClientHandler.addToSendQueue(new Packet14BlockDig(0, par1, par2, par3, par4));
            PlayerControllerCreative.clickBlockCreative(mc, this, par1, par2, par3, par4);
            return;
        }

        if (par1 == currentBlockX && par2 == currentBlockY && par3 == currentblockZ)
        {
            int i = mc.theWorld.getBlockId(par1, par2, par3);

            if (i == 0)
            {
                isHittingBlock = false;
                return;
            }

            Block block = Block.blocksList[i];
            curBlockDamageMP += block.blockStrength(mc.thePlayer);

            if (stepSoundTickCounter % 4F == 0.0F && block != null)
            {
                mc.sndManager.playSound(block.stepSound.getStepSound(), (float)par1 + 0.5F, (float)par2 + 0.5F, (float)par3 + 0.5F, (block.stepSound.getVolume() + 1.0F) / 8F, block.stepSound.getPitch() * 0.5F);
            }

            stepSoundTickCounter++;

            if (curBlockDamageMP >= 1.0F)
            {
                isHittingBlock = false;
                netClientHandler.addToSendQueue(new Packet14BlockDig(2, par1, par2, par3, par4));
                onPlayerDestroyBlock(par1, par2, par3, par4);
                curBlockDamageMP = 0.0F;
                prevBlockDamageMP = 0.0F;
                stepSoundTickCounter = 0.0F;
                blockHitDelay = 5;
            }
        }
        else
        {
            clickBlock(par1, par2, par3, par4);
        }
    }

    public void setPartialTime(float par1)
    {
        if (curBlockDamageMP <= 0.0F)
        {
            mc.ingameGUI.damageGuiPartialTime = 0.0F;
            mc.renderGlobal.damagePartialTime = 0.0F;
        }
        else
        {
            float f = prevBlockDamageMP + (curBlockDamageMP - prevBlockDamageMP) * par1;
            mc.ingameGUI.damageGuiPartialTime = f;
            mc.renderGlobal.damagePartialTime = f;
        }
    }

    /**
     * player reach distance = 4F
     */
    public float getBlockReachDistance()
    {
        return !creativeMode ? 4.5F : 5F;
    }

    /**
     * Called on world change with the new World as the only parameter.
     */
    public void onWorldChange(World par1World)
    {
        super.onWorldChange(par1World);
    }

    public void updateController()
    {
        syncCurrentPlayItem();
        prevBlockDamageMP = curBlockDamageMP;
        mc.sndManager.playRandomMusicIfReady();
    }

    /**
     * Syncs the current player item with the server
     */
    private void syncCurrentPlayItem()
    {
        int i = mc.thePlayer.inventory.currentItem;

        if (i != currentPlayerItem)
        {
            currentPlayerItem = i;
            netClientHandler.addToSendQueue(new Packet16BlockItemSwitch(currentPlayerItem));
        }
    }

    /**
     * Handles a players right click
     */
    public boolean onPlayerRightClick(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack, int par4, int par5, int par6, int par7)
    {
        syncCurrentPlayItem();
        netClientHandler.addToSendQueue(new Packet15Place(par4, par5, par6, par7, par1EntityPlayer.inventory.getCurrentItem()));
        int i = par2World.getBlockId(par4, par5, par6);

        if (i > 0 && Block.blocksList[i].blockActivated(par2World, par4, par5, par6, par1EntityPlayer))
        {
            return true;
        }

        if (par3ItemStack == null)
        {
            return false;
        }

        if (creativeMode)
        {
            int j = par3ItemStack.getItemDamage();
            int k = par3ItemStack.stackSize;
            boolean flag = par3ItemStack.useItem(par1EntityPlayer, par2World, par4, par5, par6, par7);
            par3ItemStack.setItemDamage(j);
            par3ItemStack.stackSize = k;
            return flag;
        }
        else
        {
            return par3ItemStack.useItem(par1EntityPlayer, par2World, par4, par5, par6, par7);
        }
    }

    /**
     * Notifies the server of things like consuming food, etc...
     */
    public boolean sendUseItem(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack)
    {
        syncCurrentPlayItem();
        netClientHandler.addToSendQueue(new Packet15Place(-1, -1, -1, 255, par1EntityPlayer.inventory.getCurrentItem()));
        boolean flag = super.sendUseItem(par1EntityPlayer, par2World, par3ItemStack);
        return flag;
    }

    public EntityPlayer createPlayer(World par1World)
    {
        return new EntityClientPlayerMP(mc, par1World, mc.session, netClientHandler);
    }

    /**
     * Attacks an entity
     */
    public void attackEntity(EntityPlayer par1EntityPlayer, Entity par2Entity)
    {
        syncCurrentPlayItem();
        netClientHandler.addToSendQueue(new Packet7UseEntity(par1EntityPlayer.entityId, par2Entity.entityId, 1));
        par1EntityPlayer.attackTargetEntityWithCurrentItem(par2Entity);
    }

    /**
     * Interacts with an entity
     */
    public void interactWithEntity(EntityPlayer par1EntityPlayer, Entity par2Entity)
    {
        syncCurrentPlayItem();
        netClientHandler.addToSendQueue(new Packet7UseEntity(par1EntityPlayer.entityId, par2Entity.entityId, 0));
        par1EntityPlayer.useCurrentItemOnEntity(par2Entity);
    }

    public ItemStack windowClick(int par1, int par2, int par3, boolean par4, EntityPlayer par5EntityPlayer)
    {
        short word0 = par5EntityPlayer.craftingInventory.getNextTransactionID(par5EntityPlayer.inventory);
        ItemStack itemstack = super.windowClick(par1, par2, par3, par4, par5EntityPlayer);
        netClientHandler.addToSendQueue(new Packet102WindowClick(par1, par2, par3, par4, itemstack, word0));
        return itemstack;
    }

    public void func_40593_a(int par1, int par2)
    {
        netClientHandler.addToSendQueue(new Packet108EnchantItem(par1, par2));
    }

    /**
     * Used in PlayerControllerMP to update the server with an ItemStack in a slot.
     */
    public void sendSlotPacket(ItemStack par1ItemStack, int par2)
    {
        if (creativeMode)
        {
            netClientHandler.addToSendQueue(new Packet107CreativeSetSlot(par2, par1ItemStack));
        }
    }

    public void func_35639_a(ItemStack par1ItemStack)
    {
        if (creativeMode && par1ItemStack != null)
        {
            netClientHandler.addToSendQueue(new Packet107CreativeSetSlot(-1, par1ItemStack));
        }
    }

    public void func_20086_a(int par1, EntityPlayer par2EntityPlayer)
    {
        if (par1 == -9999)
        {
            return;
        }
        else
        {
            return;
        }
    }

    public void onStoppedUsingItem(EntityPlayer par1EntityPlayer)
    {
        syncCurrentPlayItem();
        netClientHandler.addToSendQueue(new Packet14BlockDig(5, 0, 0, 0, 255));
        super.onStoppedUsingItem(par1EntityPlayer);
    }

    public boolean func_35642_f()
    {
        return true;
    }

    /**
     * Checks if the player is not creative, used for checking if it should break a block instantly
     */
    public boolean isNotCreative()
    {
        return !creativeMode;
    }

    /**
     * returns true if player is in creative mode
     */
    public boolean isInCreativeMode()
    {
        return creativeMode;
    }

    /**
     * true for hitting entities far away.
     */
    public boolean extendedReach()
    {
        return creativeMode;
    }
}
