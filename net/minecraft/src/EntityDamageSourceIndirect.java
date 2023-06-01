package net.minecraft.src;

public class EntityDamageSourceIndirect extends EntityDamageSource
{
    private Entity indirectEntity;

    public EntityDamageSourceIndirect(String par1Str, Entity par2Entity, Entity par3Entity)
    {
        super(par1Str, par2Entity);
        indirectEntity = par3Entity;
    }

    public Entity getSourceOfDamage()
    {
        return damageSourceEntity;
    }

    public Entity getEntity()
    {
        return indirectEntity;
    }
}
