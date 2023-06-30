package net.minecraft.src;

public class Material
{
    public static final Material air;

    /** The material used by BlockGrass */
    public static final Material grass;
    public static final Material ground;
    public static final Material wood;
    public static final Material rock;
    public static final Material iron;
    public static final Material water;
    public static final Material lava;
    public static final Material leaves;
    public static final Material plants;
    public static final Material vine;
    public static final Material sponge;
    public static final Material cloth;
    public static final Material fire;
    public static final Material sand;
    public static final Material circuits;
    public static final Material glass;
    public static final Material redstoneLight;
    public static final Material tnt;
    public static final Material unused;
    public static final Material ice;
    public static final Material snow;

    /** The material for crafted snow. */
    public static final Material craftedSnow;
    public static final Material cactus;
    public static final Material clay;

    /** pumpkin */
    public static final Material pumpkin;
    public static final Material dragonEgg;

    /** Material used for portals */
    public static final Material portal;

    /** Cake's material, see BlockCake */
    public static final Material cake;

    /** Web's material. */
    public static final Material web;

    /** Pistons' material. */
    public static final Material piston;

    /** Bool defining if the block can burn or not. */
    private boolean canBurn;

    /** Indicates if the material is a form of ground cover, e.g. Snow */
    private boolean groundCover;

    /** Indicates if the material is translucent */
    private boolean isTranslucent;

    /** The color index used to draw the blocks of this material on maps. */
    public final MapColor materialMapColor;

    /**
     * Determines if the materials is one that can be collected by the player.
     */
    private boolean canHarvest;

    /**
     * Mobility information / flag of the material. 0 = normal, 1 = can't be push but enabled piston to move over it, 2
     * = can't be pushed and stop pistons
     */
    private int mobilityFlag;

    public Material(MapColor par1MapColor)
    {
        canHarvest = true;
        materialMapColor = par1MapColor;
    }

    /**
     * Returns if blocks of these materials are liquids.
     */
    public boolean isLiquid()
    {
        return false;
    }

    public boolean isSolid()
    {
        return true;
    }

    /**
     * Will prevent grass from growing on dirt underneath and kill any grass below it if it returns true
     */
    public boolean getCanBlockGrass()
    {
        return true;
    }

    /**
     * Returns if this material is considered solid or not
     */
    public boolean blocksMovement()
    {
        return true;
    }

    /**
     * Marks the material as translucent
     */
    private Material setTranslucent()
    {
        isTranslucent = true;
        return this;
    }

    /**
     * Disables the ability to harvest this material.
     */
    protected Material setNoHarvest()
    {
        canHarvest = false;
        return this;
    }

    /**
     * Set the canBurn bool to True and return the current object.
     */
    protected Material setBurning()
    {
        canBurn = true;
        return this;
    }

    /**
     * Returns if the block can burn or not.
     */
    public boolean getCanBurn()
    {
        return canBurn;
    }

    /**
     * Sets the material as a form of ground cover, e.g. Snow
     */
    public Material setGroundCover()
    {
        groundCover = true;
        return this;
    }

    /**
     * Return whether the material is a form of ground cover, e.g. Snow
     */
    public boolean isGroundCover()
    {
        return groundCover;
    }

    /**
     * Indicates if the material is translucent
     */
    public boolean isOpaque()
    {
        if (isTranslucent)
        {
            return false;
        }
        else
        {
            return blocksMovement();
        }
    }

    /**
     * Returns true if material can be harvested by player.
     */
    public boolean isHarvestable()
    {
        return canHarvest;
    }

    /**
     * Returns the mobility information of the material, 0 = free, 1 = can't push but can move over, 2 = total
     * immobility and stop pistons
     */
    public int getMaterialMobility()
    {
        return mobilityFlag;
    }

    /**
     * This type of material can't be pushed, but pistons can move over it.
     */
    protected Material setNoPushMobility()
    {
        mobilityFlag = 1;
        return this;
    }

    /**
     * This type of material can't be pushed, and pistons are blocked to move.
     */
    protected Material setImmovableMobility()
    {
        mobilityFlag = 2;
        return this;
    }

    static
    {
        air = new MaterialTransparent(MapColor.airColor);
        grass = new Material(MapColor.grassColor);
        ground = new Material(MapColor.dirtColor);
        wood = (new Material(MapColor.woodColor)).setBurning();
        rock = (new Material(MapColor.stoneColor)).setNoHarvest();
        iron = (new Material(MapColor.ironColor)).setNoHarvest();
        water = (new MaterialLiquid(MapColor.waterColor)).setNoPushMobility();
        lava = (new MaterialLiquid(MapColor.tntColor)).setNoPushMobility();
        leaves = (new Material(MapColor.foliageColor)).setBurning().setTranslucent().setNoPushMobility();
        plants = (new MaterialLogic(MapColor.foliageColor)).setNoPushMobility();
        vine = (new MaterialLogic(MapColor.foliageColor)).setBurning().setNoPushMobility().setGroundCover();
        sponge = new Material(MapColor.clothColor);
        cloth = (new Material(MapColor.clothColor)).setBurning();
        fire = (new MaterialTransparent(MapColor.airColor)).setNoPushMobility();
        sand = new Material(MapColor.sandColor);
        circuits = (new MaterialLogic(MapColor.airColor)).setNoPushMobility();
        glass = (new Material(MapColor.airColor)).setTranslucent();
        redstoneLight = new Material(MapColor.airColor);
        tnt = (new Material(MapColor.tntColor)).setBurning().setTranslucent();
        unused = (new Material(MapColor.foliageColor)).setNoPushMobility();
        ice = (new Material(MapColor.iceColor)).setTranslucent();
        snow = (new MaterialLogic(MapColor.snowColor)).setGroundCover().setTranslucent().setNoHarvest().setNoPushMobility();
        craftedSnow = (new Material(MapColor.snowColor)).setNoHarvest();
        cactus = (new Material(MapColor.foliageColor)).setTranslucent().setNoPushMobility();
        clay = new Material(MapColor.clayColor);
        pumpkin = (new Material(MapColor.foliageColor)).setNoPushMobility();
        dragonEgg = (new Material(MapColor.foliageColor)).setNoPushMobility();
        portal = (new MaterialPortal(MapColor.airColor)).setImmovableMobility();
        cake = (new Material(MapColor.airColor)).setNoPushMobility();
        web = (new MaterialWeb(MapColor.clothColor)).setNoHarvest().setNoPushMobility();
        piston = (new Material(MapColor.stoneColor)).setImmovableMobility();
    }
}
