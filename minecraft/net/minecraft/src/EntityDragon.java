package net.minecraft.src;

import java.util.*;

public class EntityDragon extends EntityDragonBase
{
    public double targetX;
    public double targetY;
    public double targetZ;
    public double field_40162_d[][];
    public int field_40164_e;
    public EntityDragonPart dragonPartArray[];

    /** The head bounding box of a dragon */
    public EntityDragonPart dragonPartHead;

    /** The body bounding box of a dragon */
    public EntityDragonPart dragonPartBody;
    public EntityDragonPart dragonPartTail1;
    public EntityDragonPart dragonPartTail2;
    public EntityDragonPart dragonPartTail3;
    public EntityDragonPart dragonPartWing1;
    public EntityDragonPart dragonPartWing2;
    public float field_40173_aw;
    public float field_40172_ax;
    public boolean field_40163_ay;
    public boolean field_40161_az;
    private Entity target;
    public int field_40178_aA;

    /** The current endercrystal that is healing this dragon */
    public EntityEnderCrystal healingEnderCrystal;

    public EntityDragon(World par1World)
    {
        super(par1World);
        field_40162_d = new double[64][3];
        field_40164_e = -1;
        field_40173_aw = 0.0F;
        field_40172_ax = 0.0F;
        field_40163_ay = false;
        field_40161_az = false;
        field_40178_aA = 0;
        healingEnderCrystal = null;
        dragonPartArray = (new EntityDragonPart[]
                {
                    dragonPartHead = new EntityDragonPart(this, "head", 6F, 6F), dragonPartBody = new EntityDragonPart(this, "body", 8F, 8F), dragonPartTail1 = new EntityDragonPart(this, "tail", 4F, 4F), dragonPartTail2 = new EntityDragonPart(this, "tail", 4F, 4F), dragonPartTail3 = new EntityDragonPart(this, "tail", 4F, 4F), dragonPartWing1 = new EntityDragonPart(this, "wing", 4F, 4F), dragonPartWing2 = new EntityDragonPart(this, "wing", 4F, 4F)
                });
        maxHealth = 200;
        setEntityHealth(maxHealth);
        texture = "/mob/enderdragon/ender.png";
        setSize(16F, 8F);
        noClip = true;
        isImmuneToFire = true;
        targetY = 100D;
        ignoreFrustumCheck = true;
    }

    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(16, new Integer(maxHealth));
    }

    public double[] func_40160_a(int par1, float par2)
    {
        if (health <= 0)
        {
            par2 = 0.0F;
        }

        par2 = 1.0F - par2;
        int i = field_40164_e - par1 * 1 & 0x3f;
        int j = field_40164_e - par1 * 1 - 1 & 0x3f;
        double ad[] = new double[3];
        double d = field_40162_d[i][0];
        double d1;

        for (d1 = field_40162_d[j][0] - d; d1 < -180D; d1 += 360D) { }

        for (; d1 >= 180D; d1 -= 360D) { }

        ad[0] = d + d1 * (double)par2;
        d = field_40162_d[i][1];
        d1 = field_40162_d[j][1] - d;
        ad[1] = d + d1 * (double)par2;
        ad[2] = field_40162_d[i][2] + (field_40162_d[j][2] - field_40162_d[i][2]) * (double)par2;
        return ad;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        field_40173_aw = field_40172_ax;

        if (!worldObj.isRemote)
        {
            dataWatcher.updateObject(16, Integer.valueOf(health));
        }

        if (health <= 0)
        {
            float f = (rand.nextFloat() - 0.5F) * 8F;
            float f2 = (rand.nextFloat() - 0.5F) * 4F;
            float f4 = (rand.nextFloat() - 0.5F) * 8F;
            worldObj.spawnParticle("largeexplode", posX + (double)f, posY + 2D + (double)f2, posZ + (double)f4, 0.0D, 0.0D, 0.0D);
            return;
        }

        updateDragonEnderCrystal();
        float f1 = 0.2F / (MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ) * 10F + 1.0F);
        f1 *= (float)Math.pow(2D, motionY);

        if (field_40161_az)
        {
            field_40172_ax += f1 * 0.5F;
        }
        else
        {
            field_40172_ax += f1;
        }

        for (; rotationYaw >= 180F; rotationYaw -= 360F) { }

        for (; rotationYaw < -180F; rotationYaw += 360F) { }

        if (field_40164_e < 0)
        {
            for (int i = 0; i < field_40162_d.length; i++)
            {
                field_40162_d[i][0] = rotationYaw;
                field_40162_d[i][1] = posY;
            }
        }

        if (++field_40164_e == field_40162_d.length)
        {
            field_40164_e = 0;
        }

        field_40162_d[field_40164_e][0] = rotationYaw;
        field_40162_d[field_40164_e][1] = posY;

        if (worldObj.isRemote)
        {
            if (newPosRotationIncrements > 0)
            {
                double d = posX + (newPosX - posX) / (double)newPosRotationIncrements;
                double d2 = posY + (newPosY - posY) / (double)newPosRotationIncrements;
                double d4 = posZ + (newPosZ - posZ) / (double)newPosRotationIncrements;
                double d6;

                for (d6 = newRotationYaw - (double)rotationYaw; d6 < -180D; d6 += 360D) { }

                for (; d6 >= 180D; d6 -= 360D) { }

                rotationYaw += d6 / (double)newPosRotationIncrements;
                rotationPitch += (newRotationPitch - (double)rotationPitch) / (double)newPosRotationIncrements;
                newPosRotationIncrements--;
                setPosition(d, d2, d4);
                setRotation(rotationYaw, rotationPitch);
            }
        }
        else
        {
            double d1 = targetX - posX;
            double d3 = targetY - posY;
            double d5 = targetZ - posZ;
            double d7 = d1 * d1 + d3 * d3 + d5 * d5;

            if (target != null)
            {
                targetX = target.posX;
                targetZ = target.posZ;
                double d8 = targetX - posX;
                double d10 = targetZ - posZ;
                double d12 = Math.sqrt(d8 * d8 + d10 * d10);
                double d13 = (0.40000000596046448D + d12 / 80D) - 1.0D;

                if (d13 > 10D)
                {
                    d13 = 10D;
                }

                targetY = target.boundingBox.minY + d13;
            }
            else
            {
                targetX += rand.nextGaussian() * 2D;
                targetZ += rand.nextGaussian() * 2D;
            }

            if (field_40163_ay || d7 < 100D || d7 > 22500D || isCollidedHorizontally || isCollidedVertically)
            {
                func_41006_aA();
            }

            d3 /= MathHelper.sqrt_double(d1 * d1 + d5 * d5);
            float f10 = 0.6F;

            if (d3 < (double)(-f10))
            {
                d3 = -f10;
            }

            if (d3 > (double)f10)
            {
                d3 = f10;
            }

            motionY += d3 * 0.10000000149011612D;

            for (; rotationYaw < -180F; rotationYaw += 360F) { }

            for (; rotationYaw >= 180F; rotationYaw -= 360F) { }

            double d9 = 180D - (Math.atan2(d1, d5) * 180D) / Math.PI;
            double d11;

            for (d11 = d9 - (double)rotationYaw; d11 < -180D; d11 += 360D) { }

            for (; d11 >= 180D; d11 -= 360D) { }

            if (d11 > 50D)
            {
                d11 = 50D;
            }

            if (d11 < -50D)
            {
                d11 = -50D;
            }

            Vec3D vec3d = Vec3D.createVector(targetX - posX, targetY - posY, targetZ - posZ).normalize();
            Vec3D vec3d1 = Vec3D.createVector(MathHelper.sin((rotationYaw * (float)Math.PI) / 180F), motionY, -MathHelper.cos((rotationYaw * (float)Math.PI) / 180F)).normalize();
            float f18 = (float)(vec3d1.dotProduct(vec3d) + 0.5D) / 1.5F;

            if (f18 < 0.0F)
            {
                f18 = 0.0F;
            }

            randomYawVelocity *= 0.8F;
            float f19 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ) * 1.0F + 1.0F;
            double d14 = Math.sqrt(motionX * motionX + motionZ * motionZ) * 1.0D + 1.0D;

            if (d14 > 40D)
            {
                d14 = 40D;
            }

            randomYawVelocity += d11 * (0.69999998807907104D / d14 / (double)f19);
            rotationYaw += randomYawVelocity * 0.1F;
            float f20 = (float)(2D / (d14 + 1.0D));
            float f21 = 0.06F;
            moveFlying(0.0F, -1F, f21 * (f18 * f20 + (1.0F - f20)));

            if (field_40161_az)
            {
                moveEntity(motionX * 0.80000001192092896D, motionY * 0.80000001192092896D, motionZ * 0.80000001192092896D);
            }
            else
            {
                moveEntity(motionX, motionY, motionZ);
            }

            Vec3D vec3d2 = Vec3D.createVector(motionX, motionY, motionZ).normalize();
            float f22 = (float)(vec3d2.dotProduct(vec3d1) + 1.0D) / 2.0F;
            f22 = 0.8F + 0.15F * f22;
            motionX *= f22;
            motionZ *= f22;
            motionY *= 0.9100000262260437D;
        }

        renderYawOffset = rotationYaw;
        dragonPartHead.width = dragonPartHead.height = 3F;
        dragonPartTail1.width = dragonPartTail1.height = 2.0F;
        dragonPartTail2.width = dragonPartTail2.height = 2.0F;
        dragonPartTail3.width = dragonPartTail3.height = 2.0F;
        dragonPartBody.height = 3F;
        dragonPartBody.width = 5F;
        dragonPartWing1.height = 2.0F;
        dragonPartWing1.width = 4F;
        dragonPartWing2.height = 3F;
        dragonPartWing2.width = 4F;
        float f3 = (((float)(func_40160_a(5, 1.0F)[1] - func_40160_a(10, 1.0F)[1]) * 10F) / 180F) * (float)Math.PI;
        float f5 = MathHelper.cos(f3);
        float f6 = -MathHelper.sin(f3);
        float f7 = (rotationYaw * (float)Math.PI) / 180F;
        float f8 = MathHelper.sin(f7);
        float f9 = MathHelper.cos(f7);
        dragonPartBody.onUpdate();
        dragonPartBody.setLocationAndAngles(posX + (double)(f8 * 0.5F), posY, posZ - (double)(f9 * 0.5F), 0.0F, 0.0F);
        dragonPartWing1.onUpdate();
        dragonPartWing1.setLocationAndAngles(posX + (double)(f9 * 4.5F), posY + 2D, posZ + (double)(f8 * 4.5F), 0.0F, 0.0F);
        dragonPartWing2.onUpdate();
        dragonPartWing2.setLocationAndAngles(posX - (double)(f9 * 4.5F), posY + 2D, posZ - (double)(f8 * 4.5F), 0.0F, 0.0F);

        if (!worldObj.isRemote)
        {
            func_41007_az();
        }

        if (!worldObj.isRemote && maxHurtTime == 0)
        {
            collideWithEntities(worldObj.getEntitiesWithinAABBExcludingEntity(this, dragonPartWing1.boundingBox.expand(4D, 2D, 4D).offset(0.0D, -2D, 0.0D)));
            collideWithEntities(worldObj.getEntitiesWithinAABBExcludingEntity(this, dragonPartWing2.boundingBox.expand(4D, 2D, 4D).offset(0.0D, -2D, 0.0D)));
            attackEntitiesInList(worldObj.getEntitiesWithinAABBExcludingEntity(this, dragonPartHead.boundingBox.expand(1.0D, 1.0D, 1.0D)));
        }

        double ad[] = func_40160_a(5, 1.0F);
        double ad1[] = func_40160_a(0, 1.0F);
        float f11 = MathHelper.sin((rotationYaw * (float)Math.PI) / 180F - randomYawVelocity * 0.01F);
        float f12 = MathHelper.cos((rotationYaw * (float)Math.PI) / 180F - randomYawVelocity * 0.01F);
        dragonPartHead.onUpdate();
        dragonPartHead.setLocationAndAngles(posX + (double)(f11 * 5.5F * f5), posY + (ad1[1] - ad[1]) * 1.0D + (double)(f6 * 5.5F), posZ - (double)(f12 * 5.5F * f5), 0.0F, 0.0F);

        for (int j = 0; j < 3; j++)
        {
            EntityDragonPart entitydragonpart = null;

            if (j == 0)
            {
                entitydragonpart = dragonPartTail1;
            }

            if (j == 1)
            {
                entitydragonpart = dragonPartTail2;
            }

            if (j == 2)
            {
                entitydragonpart = dragonPartTail3;
            }

            double ad2[] = func_40160_a(12 + j * 2, 1.0F);
            float f13 = (rotationYaw * (float)Math.PI) / 180F + ((simplifyAngle(ad2[0] - ad[0]) * (float)Math.PI) / 180F) * 1.0F;
            float f14 = MathHelper.sin(f13);
            float f15 = MathHelper.cos(f13);
            float f16 = 1.5F;
            float f17 = (float)(j + 1) * 2.0F;
            entitydragonpart.onUpdate();
            entitydragonpart.setLocationAndAngles(posX - (double)((f8 * f16 + f14 * f17) * f5), ((posY + (ad2[1] - ad[1]) * 1.0D) - (double)((f17 + f16) * f6)) + 1.5D, posZ + (double)((f9 * f16 + f15 * f17) * f5), 0.0F, 0.0F);
        }

        if (!worldObj.isRemote)
        {
            field_40161_az = destroyBlocksInAABB(dragonPartHead.boundingBox) | destroyBlocksInAABB(dragonPartBody.boundingBox);
        }
    }

    /**
     * Updates the state of the enderdragon's current endercrystal.
     */
    private void updateDragonEnderCrystal()
    {
        if (healingEnderCrystal != null)
        {
            if (healingEnderCrystal.isDead)
            {
                if (!worldObj.isRemote)
                {
                    attackEntityFromPart(dragonPartHead, DamageSource.explosion, 10);
                }

                healingEnderCrystal = null;
            }
            else if (ticksExisted % 10 == 0 && health < maxHealth)
            {
                health++;
            }
        }

        if (rand.nextInt(10) == 0)
        {
            float f = 32F;
            List list = worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityEnderCrystal.class, boundingBox.expand(f, f, f));
            EntityEnderCrystal entityendercrystal = null;
            double d = Double.MAX_VALUE;
            Iterator iterator = list.iterator();

            do
            {
                if (!iterator.hasNext())
                {
                    break;
                }

                Entity entity = (Entity)iterator.next();
                double d1 = entity.getDistanceSqToEntity(this);

                if (d1 < d)
                {
                    d = d1;
                    entityendercrystal = (EntityEnderCrystal)entity;
                }
            }
            while (true);

            healingEnderCrystal = entityendercrystal;
        }
    }

    private void func_41007_az()
    {
    }

    /**
     * Pushes all entities inside the list away from the enderdragon.
     */
    private void collideWithEntities(List par1List)
    {
        double d = (dragonPartBody.boundingBox.minX + dragonPartBody.boundingBox.maxX) / 2D;
        double d1 = (dragonPartBody.boundingBox.minZ + dragonPartBody.boundingBox.maxZ) / 2D;
        Iterator iterator = par1List.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            Entity entity = (Entity)iterator.next();

            if (entity instanceof EntityLiving)
            {
                double d2 = entity.posX - d;
                double d3 = entity.posZ - d1;
                double d4 = d2 * d2 + d3 * d3;
                entity.addVelocity((d2 / d4) * 4D, 0.20000000298023224D, (d3 / d4) * 4D);
            }
        }
        while (true);
    }

    /**
     * Attacks all entities inside this list, dealing 5 hearts of damage.
     */
    private void attackEntitiesInList(List par1List)
    {
        for (int i = 0; i < par1List.size(); i++)
        {
            Entity entity = (Entity)par1List.get(i);

            if (entity instanceof EntityLiving)
            {
                entity.attackEntityFrom(DamageSource.causeMobDamage(this), 10);
            }
        }
    }

    private void func_41006_aA()
    {
        field_40163_ay = false;

        if (rand.nextInt(2) == 0 && worldObj.playerEntities.size() > 0)
        {
            target = (Entity)worldObj.playerEntities.get(rand.nextInt(worldObj.playerEntities.size()));
        }
        else
        {
            boolean flag = false;

            do
            {
                targetX = 0.0D;
                targetY = 70F + rand.nextFloat() * 50F;
                targetZ = 0.0D;
                targetX += rand.nextFloat() * 120F - 60F;
                targetZ += rand.nextFloat() * 120F - 60F;
                double d = posX - targetX;
                double d1 = posY - targetY;
                double d2 = posZ - targetZ;
                flag = d * d + d1 * d1 + d2 * d2 > 100D;
            }
            while (!flag);

            target = null;
        }
    }

    /**
     * Simplifies the value of a number by adding/subtracting 180 to the point that the number is between -180 and 180.
     */
    private float simplifyAngle(double par1)
    {
        for (; par1 >= 180D; par1 -= 360D) { }

        for (; par1 < -180D; par1 += 360D) { }

        return (float)par1;
    }

    /**
     * Destroys all blocks that aren't associated with 'The End' inside the given bounding box.
     */
    private boolean destroyBlocksInAABB(AxisAlignedBB par1AxisAlignedBB)
    {
        int i = MathHelper.floor_double(par1AxisAlignedBB.minX);
        int j = MathHelper.floor_double(par1AxisAlignedBB.minY);
        int k = MathHelper.floor_double(par1AxisAlignedBB.minZ);
        int l = MathHelper.floor_double(par1AxisAlignedBB.maxX);
        int i1 = MathHelper.floor_double(par1AxisAlignedBB.maxY);
        int j1 = MathHelper.floor_double(par1AxisAlignedBB.maxZ);
        boolean flag = false;
        boolean flag1 = false;

        for (int k1 = i; k1 <= l; k1++)
        {
            for (int l1 = j; l1 <= i1; l1++)
            {
                for (int i2 = k; i2 <= j1; i2++)
                {
                    int j2 = worldObj.getBlockId(k1, l1, i2);

                    if (j2 == 0)
                    {
                        continue;
                    }

                    if (j2 == Block.obsidian.blockID || j2 == Block.whiteStone.blockID || j2 == Block.bedrock.blockID)
                    {
                        flag = true;
                    }
                    else
                    {
                        flag1 = true;
                        worldObj.setBlockWithNotify(k1, l1, i2, 0);
                    }
                }
            }
        }

        if (flag1)
        {
            double d = par1AxisAlignedBB.minX + (par1AxisAlignedBB.maxX - par1AxisAlignedBB.minX) * (double)rand.nextFloat();
            double d1 = par1AxisAlignedBB.minY + (par1AxisAlignedBB.maxY - par1AxisAlignedBB.minY) * (double)rand.nextFloat();
            double d2 = par1AxisAlignedBB.minZ + (par1AxisAlignedBB.maxZ - par1AxisAlignedBB.minZ) * (double)rand.nextFloat();
            worldObj.spawnParticle("largeexplode", d, d1, d2, 0.0D, 0.0D, 0.0D);
        }

        return flag;
    }

    public boolean attackEntityFromPart(EntityDragonPart par1EntityDragonPart, DamageSource par2DamageSource, int par3)
    {
        if (par1EntityDragonPart != dragonPartHead)
        {
            par3 = par3 / 4 + 1;
        }

        float f = (rotationYaw * (float)Math.PI) / 180F;
        float f1 = MathHelper.sin(f);
        float f2 = MathHelper.cos(f);
        targetX = posX + (double)(f1 * 5F) + (double)((rand.nextFloat() - 0.5F) * 2.0F);
        targetY = posY + (double)(rand.nextFloat() * 3F) + 1.0D;
        targetZ = (posZ - (double)(f2 * 5F)) + (double)((rand.nextFloat() - 0.5F) * 2.0F);
        target = null;

        if ((par2DamageSource.getEntity() instanceof EntityPlayer) || par2DamageSource == DamageSource.explosion)
        {
            superAttackFrom(par2DamageSource, par3);
        }

        return true;
    }

    /**
     * handles entity death timer, experience orb and particle creation
     */
    protected void onDeathUpdate()
    {
        field_40178_aA++;

        if (field_40178_aA >= 180 && field_40178_aA <= 200)
        {
            float f = (rand.nextFloat() - 0.5F) * 8F;
            float f1 = (rand.nextFloat() - 0.5F) * 4F;
            float f2 = (rand.nextFloat() - 0.5F) * 8F;
            worldObj.spawnParticle("hugeexplosion", posX + (double)f, posY + 2D + (double)f1, posZ + (double)f2, 0.0D, 0.0D, 0.0D);
        }

        if (!worldObj.isRemote && field_40178_aA > 150 && field_40178_aA % 5 == 0)
        {
            for (int i = 1000; i > 0;)
            {
                int k = EntityXPOrb.getXPSplit(i);
                i -= k;
                worldObj.spawnEntityInWorld(new EntityXPOrb(worldObj, posX, posY, posZ, k));
            }
        }

        moveEntity(0.0D, 0.10000000149011612D, 0.0D);
        renderYawOffset = rotationYaw += 20F;

        if (field_40178_aA == 200)
        {
            for (int j = 10000; j > 0;)
            {
                int l = EntityXPOrb.getXPSplit(j);
                j -= l;
                worldObj.spawnEntityInWorld(new EntityXPOrb(worldObj, posX, posY, posZ, l));
            }

            createEnderPortal(MathHelper.floor_double(posX), MathHelper.floor_double(posZ));
            onEntityDeath();
            setDead();
        }
    }

    /**
     * Creates the ender portal leading back to the normal world after defeating the enderdragon.
     */
    private void createEnderPortal(int par1, int par2)
    {
        byte byte0 = 64;
        BlockEndPortal.bossDefeated = true;
        int i = 4;

        for (int j = byte0 - 1; j <= byte0 + 32; j++)
        {
            for (int k = par1 - i; k <= par1 + i; k++)
            {
                for (int l = par2 - i; l <= par2 + i; l++)
                {
                    double d = k - par1;
                    double d1 = l - par2;
                    double d2 = MathHelper.sqrt_double(d * d + d1 * d1);

                    if (d2 > (double)i - 0.5D)
                    {
                        continue;
                    }

                    if (j < byte0)
                    {
                        if (d2 <= (double)(i - 1) - 0.5D)
                        {
                            worldObj.setBlockWithNotify(k, j, l, Block.bedrock.blockID);
                        }

                        continue;
                    }

                    if (j > byte0)
                    {
                        worldObj.setBlockWithNotify(k, j, l, 0);
                        continue;
                    }

                    if (d2 > (double)(i - 1) - 0.5D)
                    {
                        worldObj.setBlockWithNotify(k, j, l, Block.bedrock.blockID);
                    }
                    else
                    {
                        worldObj.setBlockWithNotify(k, j, l, Block.endPortal.blockID);
                    }
                }
            }
        }

        worldObj.setBlockWithNotify(par1, byte0 + 0, par2, Block.bedrock.blockID);
        worldObj.setBlockWithNotify(par1, byte0 + 1, par2, Block.bedrock.blockID);
        worldObj.setBlockWithNotify(par1, byte0 + 2, par2, Block.bedrock.blockID);
        worldObj.setBlockWithNotify(par1 - 1, byte0 + 2, par2, Block.torchWood.blockID);
        worldObj.setBlockWithNotify(par1 + 1, byte0 + 2, par2, Block.torchWood.blockID);
        worldObj.setBlockWithNotify(par1, byte0 + 2, par2 - 1, Block.torchWood.blockID);
        worldObj.setBlockWithNotify(par1, byte0 + 2, par2 + 1, Block.torchWood.blockID);
        worldObj.setBlockWithNotify(par1, byte0 + 3, par2, Block.bedrock.blockID);
        worldObj.setBlockWithNotify(par1, byte0 + 4, par2, Block.dragonEgg.blockID);
        BlockEndPortal.bossDefeated = false;
    }

    /**
     * Makes the entity despawn if requirements are reached
     */
    protected void despawnEntity()
    {
    }

    /**
     * Return the Entity parts making up this Entity (currently only for dragons)
     */
    public Entity[] getParts()
    {
        return dragonPartArray;
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
        return false;
    }

    public int func_41010_ax()
    {
        return dataWatcher.getWatchableObjectInt(16);
    }
}
