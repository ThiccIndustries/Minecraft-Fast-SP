package net.minecraft.src;

public interface IBlockAccess
{
    /**
     * Returns the block ID at coords x,y,z
     */
    public abstract int getBlockId(int i, int j, int k);

    /**
     * Returns the TileEntity associated with a given block in X,Y,Z coordinates, or null if no TileEntity exists
     */
    public abstract TileEntity getBlockTileEntity(int i, int j, int k);

    /**
     * 'Any Light rendered on a 1.8 Block goes through here'
     */
    public abstract int getLightBrightnessForSkyBlocks(int i, int j, int k, int l);

    public abstract float getBrightness(int i, int j, int k, int l);

    /**
     * Returns how bright the block is shown as which is the block's light value looked up in a lookup table (light
     * values aren't linear for brightness). Args: x, y, z
     */
    public abstract float getLightBrightness(int i, int j, int k);

    /**
     * Returns the block metadata at coords x,y,z
     */
    public abstract int getBlockMetadata(int i, int j, int k);

    /**
     * Returns the block's material.
     */
    public abstract Material getBlockMaterial(int i, int j, int k);

    /**
     * Returns true if the block at the specified coordinates is an opaque cube. Args: x, y, z
     */
    public abstract boolean isBlockOpaqueCube(int i, int j, int k);

    /**
     * Indicate if a material is a normal solid opaque cube.
     */
    public abstract boolean isBlockNormalCube(int i, int j, int k);

    /**
     * Returns true if the block at the specified coordinates is empty
     */
    public abstract boolean isAirBlock(int i, int j, int k);

    /**
     * Gets the biome for a given set of x/z coordinates
     */
    public abstract BiomeGenBase getBiomeGenForCoords(int i, int j);

    /**
     * Returns current world height.
     */
    public abstract int getHeight();

    public abstract boolean func_48452_a();
}
