package net.minecraft.src;

import java.util.Random;

public class ItemEnderEye extends Item
{
    public ItemEnderEye(int par1)
    {
        super(par1);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS !
     */
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7)
    {
        int i = par3World.getBlockId(par4, par5, par6);
        int j = par3World.getBlockMetadata(par4, par5, par6);

        if (par2EntityPlayer.canPlayerEdit(par4, par5, par6) && i == Block.endPortalFrame.blockID && !BlockEndPortalFrame.isEnderEyeInserted(j))
        {
            if (par3World.isRemote)
            {
                return true;
            }

            par3World.setBlockMetadataWithNotify(par4, par5, par6, j + 4);
            par1ItemStack.stackSize--;

            for (int k = 0; k < 16; k++)
            {
                double d = (float)par4 + (5F + itemRand.nextFloat() * 6F) / 16F;
                double d1 = (float)par5 + 0.8125F;
                double d2 = (float)par6 + (5F + itemRand.nextFloat() * 6F) / 16F;
                double d3 = 0.0D;
                double d4 = 0.0D;
                double d5 = 0.0D;
                par3World.spawnParticle("smoke", d, d1, d2, d3, d4, d5);
            }

            int l = j & 3;
            int i1 = 0;
            int j1 = 0;
            boolean flag = false;
            boolean flag1 = true;
            int k1 = Direction.enderEyeMetaToDirection[l];

            for (int l1 = -2; l1 <= 2; l1++)
            {
                int l2 = par4 + Direction.offsetX[k1] * l1;
                int l3 = par6 + Direction.offsetZ[k1] * l1;
                int l4 = par3World.getBlockId(l2, par5, l3);

                if (l4 != Block.endPortalFrame.blockID)
                {
                    continue;
                }

                int l5 = par3World.getBlockMetadata(l2, par5, l3);

                if (!BlockEndPortalFrame.isEnderEyeInserted(l5))
                {
                    flag1 = false;
                    break;
                }

                if (!flag)
                {
                    i1 = l1;
                    j1 = l1;
                    flag = true;
                }
                else
                {
                    j1 = l1;
                }
            }

            if (flag1 && j1 == i1 + 2)
            {
                int i2 = i1;

                do
                {
                    if (i2 > j1)
                    {
                        break;
                    }

                    int i3 = par4 + Direction.offsetX[k1] * i2;
                    int i4 = par6 + Direction.offsetZ[k1] * i2;
                    i3 += Direction.offsetX[l] * 4;
                    i4 += Direction.offsetZ[l] * 4;
                    int i5 = par3World.getBlockId(i3, par5, i4);
                    int i6 = par3World.getBlockMetadata(i3, par5, i4);

                    if (i5 != Block.endPortalFrame.blockID || !BlockEndPortalFrame.isEnderEyeInserted(i6))
                    {
                        flag1 = false;
                        break;
                    }

                    i2++;
                }
                while (true);

                label0:

                for (int j2 = i1 - 1; j2 <= j1 + 1; j2 += 4)
                {
                    int j3 = 1;

                    do
                    {
                        if (j3 > 3)
                        {
                            continue label0;
                        }

                        int j4 = par4 + Direction.offsetX[k1] * j2;
                        int j5 = par6 + Direction.offsetZ[k1] * j2;
                        j4 += Direction.offsetX[l] * j3;
                        j5 += Direction.offsetZ[l] * j3;
                        int j6 = par3World.getBlockId(j4, par5, j5);
                        int k6 = par3World.getBlockMetadata(j4, par5, j5);

                        if (j6 != Block.endPortalFrame.blockID || !BlockEndPortalFrame.isEnderEyeInserted(k6))
                        {
                            flag1 = false;
                            continue label0;
                        }

                        j3++;
                    }
                    while (true);
                }

                if (flag1)
                {
                    for (int k2 = i1; k2 <= j1; k2++)
                    {
                        for (int k3 = 1; k3 <= 3; k3++)
                        {
                            int k4 = par4 + Direction.offsetX[k1] * k2;
                            int k5 = par6 + Direction.offsetZ[k1] * k2;
                            k4 += Direction.offsetX[l] * k3;
                            k5 += Direction.offsetZ[l] * k3;
                            par3World.setBlockWithNotify(k4, par5, k5, Block.endPortal.blockID);
                        }
                    }
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, false);

        if (movingobjectposition != null && movingobjectposition.typeOfHit == EnumMovingObjectType.TILE)
        {
            int i = par2World.getBlockId(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ);

            if (i == Block.endPortalFrame.blockID)
            {
                return par1ItemStack;
            }
        }

        if (!par2World.isRemote)
        {
            ChunkPosition chunkposition = par2World.findClosestStructure("Stronghold", (int)par3EntityPlayer.posX, (int)par3EntityPlayer.posY, (int)par3EntityPlayer.posZ);

            if (chunkposition != null)
            {
                EntityEnderEye entityendereye = new EntityEnderEye(par2World, par3EntityPlayer.posX, (par3EntityPlayer.posY + 1.6200000000000001D) - (double)par3EntityPlayer.yOffset, par3EntityPlayer.posZ);
                entityendereye.func_40090_a(chunkposition.x, chunkposition.y, chunkposition.z);
                par2World.spawnEntityInWorld(entityendereye);
                par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
                par2World.playAuxSFXAtEntity(null, 1002, (int)par3EntityPlayer.posX, (int)par3EntityPlayer.posY, (int)par3EntityPlayer.posZ, 0);

                if (!par3EntityPlayer.capabilities.isCreativeMode)
                {
                    par1ItemStack.stackSize--;
                }
            }
        }

        return par1ItemStack;
    }
}
