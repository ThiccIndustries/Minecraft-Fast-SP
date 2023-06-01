package net.minecraft.src;

public class ExtendedBlockStorage
{
    /**
     * Contains the bottom-most Y block represented by this ExtendedBlockStorage. Typically a multiple of 16.
     */
    private int yBase;

    /**
     * A total count of the number of non-air blocks in this block storage's Chunk.
     */
    private int blockRefCount;

    /**
     * Contains the number of blocks in this block storage's parent chunk that require random ticking. Used to cull the
     * Chunk from random tick updates for performance reasons.
     */
    private int tickRefCount;
    private byte blockLSBArray[];

    /**
     * Contains the most significant 4 bits of each block ID belonging to this block storage's parent Chunk.
     */
    private NibbleArray blockMSBArray;

    /**
     * Stores the metadata associated with blocks in this ExtendedBlockStorage.
     */
    private NibbleArray blockMetadataArray;

    /** The NibbleArray containing a block of Block-light data. */
    private NibbleArray blocklightArray;

    /** The NibbleArray containing a block of Sky-light data. */
    private NibbleArray skylightArray;

    public ExtendedBlockStorage(int par1)
    {
        yBase = par1;
        blockLSBArray = new byte[4096];
        blockMetadataArray = new NibbleArray(blockLSBArray.length, 4);
        skylightArray = new NibbleArray(blockLSBArray.length, 4);
        blocklightArray = new NibbleArray(blockLSBArray.length, 4);
    }

    /**
     * Returns the extended block ID for a location in a chunk, merged from a byte array and a NibbleArray to form a
     * full 12-bit block ID.
     */
    public int getExtBlockID(int par1, int par2, int par3)
    {
        int i = blockLSBArray[par2 << 8 | par3 << 4 | par1] & 0xff;

        if (blockMSBArray != null)
        {
            return blockMSBArray.get(par1, par2, par3) << 8 | i;
        }
        else
        {
            return i;
        }
    }

    /**
     * Sets the extended block ID for a location in a chunk, splitting bits 11..8 into a NibbleArray and bits 7..0 into
     * a byte array. Also performs reference counting to determine whether or not to broadly cull this Chunk from the
     * random-update tick list.
     */
    public void setExtBlockID(int par1, int par2, int par3, int par4)
    {
        int i = blockLSBArray[par2 << 8 | par3 << 4 | par1] & 0xff;

        if (blockMSBArray != null)
        {
            i = blockMSBArray.get(par1, par2, par3) << 8 | i;
        }

        if (i == 0 && par4 != 0)
        {
            blockRefCount++;

            if (Block.blocksList[par4] != null && Block.blocksList[par4].getTickRandomly())
            {
                tickRefCount++;
            }
        }
        else if (i != 0 && par4 == 0)
        {
            blockRefCount--;

            if (Block.blocksList[i] != null && Block.blocksList[i].getTickRandomly())
            {
                tickRefCount--;
            }
        }
        else if (Block.blocksList[i] != null && Block.blocksList[i].getTickRandomly() && (Block.blocksList[par4] == null || !Block.blocksList[par4].getTickRandomly()))
        {
            tickRefCount--;
        }
        else if ((Block.blocksList[i] == null || !Block.blocksList[i].getTickRandomly()) && Block.blocksList[par4] != null && Block.blocksList[par4].getTickRandomly())
        {
            tickRefCount++;
        }

        blockLSBArray[par2 << 8 | par3 << 4 | par1] = (byte)(par4 & 0xff);

        if (par4 > 255)
        {
            if (blockMSBArray == null)
            {
                blockMSBArray = new NibbleArray(blockLSBArray.length, 4);
            }

            blockMSBArray.set(par1, par2, par3, (par4 & 0xf00) >> 8);
        }
        else if (blockMSBArray != null)
        {
            blockMSBArray.set(par1, par2, par3, 0);
        }
    }

    /**
     * Returns the metadata associated with the block at the given coordinates in this ExtendedBlockStorage.
     */
    public int getExtBlockMetadata(int par1, int par2, int par3)
    {
        return blockMetadataArray.get(par1, par2, par3);
    }

    /**
     * Sets the metadata of the Block at the given coordinates in this ExtendedBlockStorage to the given metadata.
     */
    public void setExtBlockMetadata(int par1, int par2, int par3, int par4)
    {
        blockMetadataArray.set(par1, par2, par3, par4);
    }

    /**
     * Returns whether or not this block storage's Chunk is fully empty, based on its internal reference count.
     */
    public boolean getIsEmpty()
    {
        return blockRefCount == 0;
    }

    /**
     * Returns whether or not this block storage's Chunk will require random ticking, used to avoid looping through
     * random block ticks when there are no blocks that would randomly tick.
     */
    public boolean getNeedsRandomTick()
    {
        return tickRefCount > 0;
    }

    /**
     * Returns the Y location of this ExtendedBlockStorage.
     */
    public int getYLocation()
    {
        return yBase;
    }

    /**
     * Sets the saved Sky-light value in the extended block storage structure.
     */
    public void setExtSkylightValue(int par1, int par2, int par3, int par4)
    {
        skylightArray.set(par1, par2, par3, par4);
    }

    /**
     * Gets the saved Sky-light value in the extended block storage structure.
     */
    public int getExtSkylightValue(int par1, int par2, int par3)
    {
        return skylightArray.get(par1, par2, par3);
    }

    /**
     * Sets the saved Block-light value in the extended block storage structure.
     */
    public void setExtBlocklightValue(int par1, int par2, int par3, int par4)
    {
        blocklightArray.set(par1, par2, par3, par4);
    }

    /**
     * Gets the saved Block-light value in the extended block storage structure.
     */
    public int getExtBlocklightValue(int par1, int par2, int par3)
    {
        return blocklightArray.get(par1, par2, par3);
    }

    public void func_48708_d()
    {
        blockRefCount = 0;
        tickRefCount = 0;

        for (int i = 0; i < 16; i++)
        {
            for (int j = 0; j < 16; j++)
            {
                for (int k = 0; k < 16; k++)
                {
                    int l = getExtBlockID(i, j, k);

                    if (l <= 0)
                    {
                        continue;
                    }

                    if (Block.blocksList[l] == null)
                    {
                        blockLSBArray[j << 8 | k << 4 | i] = 0;

                        if (blockMSBArray != null)
                        {
                            blockMSBArray.set(i, j, k, 0);
                        }

                        continue;
                    }

                    blockRefCount++;

                    if (Block.blocksList[l].getTickRandomly())
                    {
                        tickRefCount++;
                    }
                }
            }
        }
    }

    public void func_48711_e()
    {
    }

    public int func_48700_f()
    {
        return blockRefCount;
    }

    public byte[] func_48692_g()
    {
        return blockLSBArray;
    }

    public void func_48715_h()
    {
        blockMSBArray = null;
    }

    /**
     * Returns the block ID MSB (bits 11..8) array for this storage array's Chunk.
     */
    public NibbleArray getBlockMSBArray()
    {
        return blockMSBArray;
    }

    public NibbleArray func_48697_j()
    {
        return blockMetadataArray;
    }

    /**
     * Returns the NibbleArray instance containing Block-light data.
     */
    public NibbleArray getBlocklightArray()
    {
        return blocklightArray;
    }

    /**
     * Returns the NibbleArray instance containing Sky-light data.
     */
    public NibbleArray getSkylightArray()
    {
        return skylightArray;
    }

    /**
     * Sets the array of block ID least significant bits for this ExtendedBlockStorage.
     */
    public void setBlockLSBArray(byte par1ArrayOfByte[])
    {
        blockLSBArray = par1ArrayOfByte;
    }

    /**
     * Sets the array of blockID most significant bits (blockMSBArray) for this ExtendedBlockStorage.
     */
    public void setBlockMSBArray(NibbleArray par1NibbleArray)
    {
        blockMSBArray = par1NibbleArray;
    }

    /**
     * Sets the NibbleArray of block metadata (blockMetadataArray) for this ExtendedBlockStorage.
     */
    public void setBlockMetadataArray(NibbleArray par1NibbleArray)
    {
        blockMetadataArray = par1NibbleArray;
    }

    /**
     * Sets the NibbleArray instance used for Block-light values in this particular storage block.
     */
    public void setBlocklightArray(NibbleArray par1NibbleArray)
    {
        blocklightArray = par1NibbleArray;
    }

    /**
     * Sets the NibbleArray instance used for Sky-light values in this particular storage block.
     */
    public void setSkylightArray(NibbleArray par1NibbleArray)
    {
        skylightArray = par1NibbleArray;
    }

    /**
     * Called by a Chunk to initialize the MSB array if getBlockMSBArray returns null. Returns the newly-created
     * NibbleArray instance.
     */
    public NibbleArray createBlockMSBArray()
    {
        blockMSBArray = new NibbleArray(blockLSBArray.length, 4);
        return blockMSBArray;
    }
}
