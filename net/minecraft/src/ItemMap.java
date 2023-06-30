package net.minecraft.src;

public class ItemMap extends ItemMapBase
{
    protected ItemMap(int par1)
    {
        super(par1);
        setMaxStackSize(1);
    }

    public static MapData getMPMapData(short par0, World par1World)
    {
        String s = (new StringBuilder()).append("map_").append(par0).toString();
        MapData mapdata = (MapData)par1World.loadItemData(net.minecraft.src.MapData.class, (new StringBuilder()).append("map_").append(par0).toString());

        if (mapdata == null)
        {
            int i = par1World.getUniqueDataId("map");
            String s1 = (new StringBuilder()).append("map_").append(i).toString();
            mapdata = new MapData(s1);
            par1World.setItemData(s1, mapdata);
        }

        return mapdata;
    }

    public MapData getMapData(ItemStack par1ItemStack, World par2World)
    {
        String s = (new StringBuilder()).append("map_").append(par1ItemStack.getItemDamage()).toString();
        MapData mapdata = (MapData)par2World.loadItemData(net.minecraft.src.MapData.class, (new StringBuilder()).append("map_").append(par1ItemStack.getItemDamage()).toString());

        if (mapdata == null)
        {
            par1ItemStack.setItemDamage(par2World.getUniqueDataId("map"));
            String s1 = (new StringBuilder()).append("map_").append(par1ItemStack.getItemDamage()).toString();
            mapdata = new MapData(s1);
            mapdata.xCenter = par2World.getWorldInfo().getSpawnX();
            mapdata.zCenter = par2World.getWorldInfo().getSpawnZ();
            mapdata.scale = 3;
            mapdata.dimension = (byte)par2World.worldProvider.worldType;
            mapdata.markDirty();
            par2World.setItemData(s1, mapdata);
        }

        return mapdata;
    }

    public void updateMapData(World par1World, Entity par2Entity, MapData par3MapData)
    {
        if (par1World.worldProvider.worldType != par3MapData.dimension)
        {
            return;
        }

        char c = '\200';
        char c1 = '\200';
        int i = 1 << par3MapData.scale;
        int j = par3MapData.xCenter;
        int k = par3MapData.zCenter;
        int l = MathHelper.floor_double(par2Entity.posX - (double)j) / i + c / 2;
        int i1 = MathHelper.floor_double(par2Entity.posZ - (double)k) / i + c1 / 2;
        int j1 = 128 / i;

        if (par1World.worldProvider.hasNoSky)
        {
            j1 /= 2;
        }

        par3MapData.field_28175_g++;

        for (int k1 = (l - j1) + 1; k1 < l + j1; k1++)
        {
            if ((k1 & 0xf) != (par3MapData.field_28175_g & 0xf))
            {
                continue;
            }

            int l1 = 255;
            int i2 = 0;
            double d = 0.0D;

            for (int j2 = i1 - j1 - 1; j2 < i1 + j1; j2++)
            {
                if (k1 < 0 || j2 < -1 || k1 >= c || j2 >= c1)
                {
                    continue;
                }

                int k2 = k1 - l;
                int l2 = j2 - i1;
                boolean flag = k2 * k2 + l2 * l2 > (j1 - 2) * (j1 - 2);
                int i3 = ((j / i + k1) - c / 2) * i;
                int j3 = ((k / i + j2) - c1 / 2) * i;
                int k3 = 0;
                int l3 = 0;
                int i4 = 0;
                int ai[] = new int[256];
                Chunk chunk = par1World.getChunkFromBlockCoords(i3, j3);
                int j4 = i3 & 0xf;
                int k4 = j3 & 0xf;
                int l4 = 0;
                double d1 = 0.0D;

                if (par1World.worldProvider.hasNoSky)
                {
                    int i5 = i3 + j3 * 0x389bf;
                    i5 = i5 * i5 * 0x1dd6751 + i5 * 11;

                    if ((i5 >> 20 & 1) == 0)
                    {
                        ai[Block.dirt.blockID] += 10;
                    }
                    else
                    {
                        ai[Block.stone.blockID] += 10;
                    }

                    d1 = 100D;
                }
                else
                {
                    for (int j5 = 0; j5 < i; j5++)
                    {
                        for (int l5 = 0; l5 < i; l5++)
                        {
                            int j6 = chunk.getHeightValue(j5 + j4, l5 + k4) + 1;
                            int l6 = 0;

                            if (j6 > 1)
                            {
                                boolean flag1 = false;

                                do
                                {
                                    flag1 = true;
                                    l6 = chunk.getBlockID(j5 + j4, j6 - 1, l5 + k4);

                                    if (l6 == 0)
                                    {
                                        flag1 = false;
                                    }
                                    else if (j6 > 0 && l6 > 0 && Block.blocksList[l6].blockMaterial.materialMapColor == MapColor.airColor)
                                    {
                                        flag1 = false;
                                    }

                                    if (!flag1)
                                    {
                                        j6--;
                                        l6 = chunk.getBlockID(j5 + j4, j6 - 1, l5 + k4);
                                    }
                                }
                                while (j6 > 0 && !flag1);

                                if (j6 > 0 && l6 != 0 && Block.blocksList[l6].blockMaterial.isLiquid())
                                {
                                    int i7 = j6 - 1;
                                    int k7 = 0;

                                    do
                                    {
                                        k7 = chunk.getBlockID(j5 + j4, i7--, l5 + k4);
                                        l4++;
                                    }
                                    while (i7 > 0 && k7 != 0 && Block.blocksList[k7].blockMaterial.isLiquid());
                                }
                            }

                            d1 += (double)j6 / (double)(i * i);
                            ai[l6]++;
                        }
                    }
                }

                l4 /= i * i;
                k3 /= i * i;
                l3 /= i * i;
                i4 /= i * i;
                int k5 = 0;
                int i6 = 0;

                for (int k6 = 0; k6 < 256; k6++)
                {
                    if (ai[k6] > k5)
                    {
                        i6 = k6;
                        k5 = ai[k6];
                    }
                }

                double d2 = ((d1 - d) * 4D) / (double)(i + 4) + ((double)(k1 + j2 & 1) - 0.5D) * 0.40000000000000002D;
                byte byte0 = 1;

                if (d2 > 0.59999999999999998D)
                {
                    byte0 = 2;
                }

                if (d2 < -0.59999999999999998D)
                {
                    byte0 = 0;
                }

                int j7 = 0;

                if (i6 > 0)
                {
                    MapColor mapcolor = Block.blocksList[i6].blockMaterial.materialMapColor;

                    if (mapcolor == MapColor.waterColor)
                    {
                        double d3 = (double)l4 * 0.10000000000000001D + (double)(k1 + j2 & 1) * 0.20000000000000001D;
                        byte0 = 1;

                        if (d3 < 0.5D)
                        {
                            byte0 = 2;
                        }

                        if (d3 > 0.90000000000000002D)
                        {
                            byte0 = 0;
                        }
                    }

                    j7 = mapcolor.colorIndex;
                }

                d = d1;

                if (j2 < 0 || k2 * k2 + l2 * l2 >= j1 * j1 || flag && (k1 + j2 & 1) == 0)
                {
                    continue;
                }

                byte byte1 = par3MapData.colors[k1 + j2 * c];
                byte byte2 = (byte)(j7 * 4 + byte0);

                if (byte1 == byte2)
                {
                    continue;
                }

                if (l1 > j2)
                {
                    l1 = j2;
                }

                if (i2 < j2)
                {
                    i2 = j2;
                }

                par3MapData.colors[k1 + j2 * c] = byte2;
            }

            if (l1 <= i2)
            {
                par3MapData.func_28170_a(k1, l1, i2);
            }
        }
    }

    /**
     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
     * update it's contents.
     */
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
    {
        if (par2World.isRemote)
        {
            return;
        }

        MapData mapdata = getMapData(par1ItemStack, par2World);

        if (par3Entity instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)par3Entity;
            mapdata.func_28169_a(entityplayer, par1ItemStack);
        }

        if (par5)
        {
            updateMapData(par2World, par3Entity, mapdata);
        }
    }

    /**
     * Called when item is crafted/smelted. Used only by maps so far.
     */
    public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        par1ItemStack.setItemDamage(par2World.getUniqueDataId("map"));
        String s = (new StringBuilder()).append("map_").append(par1ItemStack.getItemDamage()).toString();
        MapData mapdata = new MapData(s);
        par2World.setItemData(s, mapdata);
        mapdata.xCenter = MathHelper.floor_double(par3EntityPlayer.posX);
        mapdata.zCenter = MathHelper.floor_double(par3EntityPlayer.posZ);
        mapdata.scale = 3;
        mapdata.dimension = (byte)par2World.worldProvider.worldType;
        mapdata.markDirty();
    }
}
