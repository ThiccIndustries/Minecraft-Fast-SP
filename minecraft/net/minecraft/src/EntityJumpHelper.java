package net.minecraft.src;

public class EntityJumpHelper
{
    private EntityLiving entity;
    private boolean isJumping;

    public EntityJumpHelper(EntityLiving par1EntityLiving)
    {
        isJumping = false;
        entity = par1EntityLiving;
    }

    public void setJumping()
    {
        isJumping = true;
    }

    /**
     * Called to actually make the entity jump if isJumping is true.
     */
    public void doJump()
    {
        entity.setJumping(isJumping);
        isJumping = false;
    }
}
