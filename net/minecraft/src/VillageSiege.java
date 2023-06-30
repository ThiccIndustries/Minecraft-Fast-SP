package net.minecraft.src;

import java.util.*;

public class VillageSiege
{
    private World field_48582_a;
    private boolean field_48580_b;
    private int field_48581_c;
    private int field_48578_d;
    private int field_48579_e;
    private Village field_48576_f;
    private int field_48577_g;
    private int field_48583_h;
    private int field_48584_i;

    public VillageSiege(World par1World)
    {
        field_48580_b = false;
        field_48581_c = -1;
        field_48582_a = par1World;
    }

    /**
     * Runs a single tick for the village siege
     */
    public void tick()
    {
        boolean flag = false;

        if (flag)
        {
            if (field_48581_c == 2)
            {
                field_48578_d = 100;
                return;
            }
        }
        else
        {
            if (field_48582_a.isDaytime())
            {
                field_48581_c = 0;
                return;
            }

            if (field_48581_c == 2)
            {
                return;
            }

            if (field_48581_c == 0)
            {
                float f = field_48582_a.getCelestialAngle(0.0F);

                if ((double)f < 0.5D || (double)f > 0.501D)
                {
                    return;
                }

                field_48581_c = field_48582_a.rand.nextInt(10) != 0 ? 2 : 1;
                field_48580_b = false;

                if (field_48581_c == 2)
                {
                    return;
                }
            }
        }

        if (!field_48580_b)
        {
            if (func_48574_b())
            {
                field_48580_b = true;
            }
            else
            {
                return;
            }
        }

        if (field_48579_e > 0)
        {
            field_48579_e--;
            return;
        }

        field_48579_e = 2;

        if (field_48578_d > 0)
        {
            func_48575_c();
            field_48578_d--;
        }
        else
        {
            field_48581_c = 2;
        }
    }

    private boolean func_48574_b()
    {
        List list = field_48582_a.playerEntities;

        for (Iterator iterator = list.iterator(); iterator.hasNext();)
        {
            EntityPlayer entityplayer = (EntityPlayer)iterator.next();
            field_48576_f = field_48582_a.villageCollectionObj.findNearestVillage((int)entityplayer.posX, (int)entityplayer.posY, (int)entityplayer.posZ, 1);

            if (field_48576_f != null && field_48576_f.getNumVillageDoors() >= 10 && field_48576_f.getTicksSinceLastDoorAdding() >= 20 && field_48576_f.getNumVillagers() >= 20)
            {
                ChunkCoordinates chunkcoordinates = field_48576_f.getCenter();
                float f = field_48576_f.getVillageRadius();
                boolean flag = false;
                int i = 0;

                do
                {
                    if (i >= 10)
                    {
                        break;
                    }

                    field_48577_g = chunkcoordinates.posX + (int)((double)(MathHelper.cos(field_48582_a.rand.nextFloat() * (float)Math.PI * 2.0F) * f) * 0.90000000000000002D);
                    field_48583_h = chunkcoordinates.posY;
                    field_48584_i = chunkcoordinates.posZ + (int)((double)(MathHelper.sin(field_48582_a.rand.nextFloat() * (float)Math.PI * 2.0F) * f) * 0.90000000000000002D);
                    flag = false;
                    Iterator iterator1 = field_48582_a.villageCollectionObj.func_48554_b().iterator();

                    do
                    {
                        if (!iterator1.hasNext())
                        {
                            break;
                        }

                        Village village = (Village)iterator1.next();

                        if (village == field_48576_f || !village.isInRange(field_48577_g, field_48583_h, field_48584_i))
                        {
                            continue;
                        }

                        flag = true;
                        break;
                    }
                    while (true);

                    if (!flag)
                    {
                        break;
                    }

                    i++;
                }
                while (true);

                if (flag)
                {
                    return false;
                }

                Vec3D vec3d = func_48572_a(field_48577_g, field_48583_h, field_48584_i);

                if (vec3d != null)
                {
                    field_48579_e = 0;
                    field_48578_d = 20;
                    return true;
                }
            }
        }

        return false;
    }

    private boolean func_48575_c()
    {
        Vec3D vec3d = func_48572_a(field_48577_g, field_48583_h, field_48584_i);

        if (vec3d == null)
        {
            return false;
        }

        EntityZombie entityzombie;

        try
        {
            entityzombie = new EntityZombie(field_48582_a);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return false;
        }

        entityzombie.setLocationAndAngles(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord, field_48582_a.rand.nextFloat() * 360F, 0.0F);
        field_48582_a.spawnEntityInWorld(entityzombie);
        ChunkCoordinates chunkcoordinates = field_48576_f.getCenter();
        entityzombie.setHomeArea(chunkcoordinates.posX, chunkcoordinates.posY, chunkcoordinates.posZ, field_48576_f.getVillageRadius());
        return true;
    }

    private Vec3D func_48572_a(int par1, int par2, int par3)
    {
        for (int i = 0; i < 10; i++)
        {
            int j = (par1 + field_48582_a.rand.nextInt(16)) - 8;
            int k = (par2 + field_48582_a.rand.nextInt(6)) - 3;
            int l = (par3 + field_48582_a.rand.nextInt(16)) - 8;

            if (field_48576_f.isInRange(j, k, l) && SpawnerAnimals.canCreatureTypeSpawnAtLocation(EnumCreatureType.monster, field_48582_a, j, k, l))
            {
                return Vec3D.createVector(j, k, l);
            }
        }

        return null;
    }
}
