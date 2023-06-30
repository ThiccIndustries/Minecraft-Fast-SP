package net.minecraft.src;

import java.io.PrintStream;
import java.util.List;
import java.util.Random;

public class Item
{
    /** The RNG used by the Item subclasses. */
    protected static Random itemRand = new Random();
    public static Item itemsList[] = new Item[32000];
    public static Item shovelSteel;
    public static Item pickaxeSteel;
    public static Item axeSteel;
    public static Item flintAndSteel = (new ItemFlintAndSteel(3)).setIconCoord(5, 0).setItemName("flintAndSteel");
    public static Item appleRed = (new ItemFood(4, 4, 0.3F, false)).setIconCoord(10, 0).setItemName("apple");
    public static Item bow = (new ItemBow(5)).setIconCoord(5, 1).setItemName("bow");
    public static Item arrow = (new Item(6)).setIconCoord(5, 2).setItemName("arrow");
    public static Item coal = (new ItemCoal(7)).setIconCoord(7, 0).setItemName("coal");
    public static Item diamond = (new Item(8)).setIconCoord(7, 3).setItemName("emerald");
    public static Item ingotIron = (new Item(9)).setIconCoord(7, 1).setItemName("ingotIron");
    public static Item ingotGold = (new Item(10)).setIconCoord(7, 2).setItemName("ingotGold");
    public static Item swordSteel;
    public static Item swordWood;
    public static Item shovelWood;
    public static Item pickaxeWood;
    public static Item axeWood;
    public static Item swordStone;
    public static Item shovelStone;
    public static Item pickaxeStone;
    public static Item axeStone;
    public static Item swordDiamond;
    public static Item shovelDiamond;
    public static Item pickaxeDiamond;
    public static Item axeDiamond;
    public static Item stick = (new Item(24)).setIconCoord(5, 3).setFull3D().setItemName("stick");
    public static Item bowlEmpty = (new Item(25)).setIconCoord(7, 4).setItemName("bowl");
    public static Item bowlSoup = (new ItemSoup(26, 8)).setIconCoord(8, 4).setItemName("mushroomStew");
    public static Item swordGold;
    public static Item shovelGold;
    public static Item pickaxeGold;
    public static Item axeGold;
    public static Item silk = (new Item(31)).setIconCoord(8, 0).setItemName("string");
    public static Item feather = (new Item(32)).setIconCoord(8, 1).setItemName("feather");
    public static Item gunpowder;
    public static Item hoeWood;
    public static Item hoeStone;
    public static Item hoeSteel;
    public static Item hoeDiamond;
    public static Item hoeGold;
    public static Item seeds;
    public static Item wheat = (new Item(40)).setIconCoord(9, 1).setItemName("wheat");
    public static Item bread = (new ItemFood(41, 5, 0.6F, false)).setIconCoord(9, 2).setItemName("bread");
    public static Item helmetLeather;
    public static Item plateLeather;
    public static Item legsLeather;
    public static Item bootsLeather;
    public static Item helmetChain;
    public static Item plateChain;
    public static Item legsChain;
    public static Item bootsChain;
    public static Item helmetSteel;
    public static Item plateSteel;
    public static Item legsSteel;
    public static Item bootsSteel;
    public static Item helmetDiamond;
    public static Item plateDiamond;
    public static Item legsDiamond;
    public static Item bootsDiamond;
    public static Item helmetGold;
    public static Item plateGold;
    public static Item legsGold;
    public static Item bootsGold;
    public static Item flint = (new Item(62)).setIconCoord(6, 0).setItemName("flint");
    public static Item porkRaw = (new ItemFood(63, 3, 0.3F, true)).setIconCoord(7, 5).setItemName("porkchopRaw");
    public static Item porkCooked = (new ItemFood(64, 8, 0.8F, true)).setIconCoord(8, 5).setItemName("porkchopCooked");
    public static Item painting = (new ItemPainting(65)).setIconCoord(10, 1).setItemName("painting");
    public static Item appleGold;
    public static Item sign = (new ItemSign(67)).setIconCoord(10, 2).setItemName("sign");
    public static Item doorWood;
    public static Item bucketEmpty;
    public static Item bucketWater;
    public static Item bucketLava;
    public static Item minecartEmpty = (new ItemMinecart(72, 0)).setIconCoord(7, 8).setItemName("minecart");
    public static Item saddle = (new ItemSaddle(73)).setIconCoord(8, 6).setItemName("saddle");
    public static Item doorSteel;
    public static Item redstone;
    public static Item snowball = (new ItemSnowball(76)).setIconCoord(14, 0).setItemName("snowball");
    public static Item boat = (new ItemBoat(77)).setIconCoord(8, 8).setItemName("boat");
    public static Item leather = (new Item(78)).setIconCoord(7, 6).setItemName("leather");
    public static Item bucketMilk;
    public static Item brick = (new Item(80)).setIconCoord(6, 1).setItemName("brick");
    public static Item clay = (new Item(81)).setIconCoord(9, 3).setItemName("clay");
    public static Item reed;
    public static Item paper = (new Item(83)).setIconCoord(10, 3).setItemName("paper");
    public static Item book = (new Item(84)).setIconCoord(11, 3).setItemName("book");
    public static Item slimeBall = (new Item(85)).setIconCoord(14, 1).setItemName("slimeball");
    public static Item minecartCrate = (new ItemMinecart(86, 1)).setIconCoord(7, 9).setItemName("minecartChest");
    public static Item minecartPowered = (new ItemMinecart(87, 2)).setIconCoord(7, 10).setItemName("minecartFurnace");
    public static Item egg = (new ItemEgg(88)).setIconCoord(12, 0).setItemName("egg");
    public static Item compass = (new Item(89)).setIconCoord(6, 3).setItemName("compass");
    public static Item fishingRod = (new ItemFishingRod(90)).setIconCoord(5, 4).setItemName("fishingRod");
    public static Item pocketSundial = (new Item(91)).setIconCoord(6, 4).setItemName("clock");
    public static Item lightStoneDust;
    public static Item fishRaw = (new ItemFood(93, 2, 0.3F, false)).setIconCoord(9, 5).setItemName("fishRaw");
    public static Item fishCooked = (new ItemFood(94, 5, 0.6F, false)).setIconCoord(10, 5).setItemName("fishCooked");
    public static Item dyePowder = (new ItemDye(95)).setIconCoord(14, 4).setItemName("dyePowder");
    public static Item bone = (new Item(96)).setIconCoord(12, 1).setItemName("bone").setFull3D();
    public static Item sugar;
    public static Item cake;
    public static Item bed = (new ItemBed(99)).setMaxStackSize(1).setIconCoord(13, 2).setItemName("bed");
    public static Item redstoneRepeater;
    public static Item cookie = (new ItemFood(101, 1, 0.1F, false)).setIconCoord(12, 5).setItemName("cookie");
    public static ItemMap map = (ItemMap)(new ItemMap(102)).setIconCoord(12, 3).setItemName("map");

    /**
     * Item introduced on 1.7 version, is a shear to cut leaves (you can keep the block) or get wool from sheeps.
     */
    public static ItemShears shears = (ItemShears)(new ItemShears(103)).setIconCoord(13, 5).setItemName("shears");
    public static Item melon = (new ItemFood(104, 2, 0.3F, false)).setIconCoord(13, 6).setItemName("melon");
    public static Item pumpkinSeeds;
    public static Item melonSeeds;
    public static Item beefRaw = (new ItemFood(107, 3, 0.3F, true)).setIconCoord(9, 6).setItemName("beefRaw");
    public static Item beefCooked = (new ItemFood(108, 8, 0.8F, true)).setIconCoord(10, 6).setItemName("beefCooked");
    public static Item chickenRaw;
    public static Item chickenCooked = (new ItemFood(110, 6, 0.6F, true)).setIconCoord(10, 7).setItemName("chickenCooked");
    public static Item rottenFlesh;
    public static Item enderPearl = (new ItemEnderPearl(112)).setIconCoord(11, 6).setItemName("enderPearl");
    public static Item blazeRod = (new Item(113)).setIconCoord(12, 6).setItemName("blazeRod");
    public static Item ghastTear;
    public static Item goldNugget = (new Item(115)).setIconCoord(12, 7).setItemName("goldNugget");
    public static Item netherStalkSeeds;
    public static ItemPotion potion = (ItemPotion)(new ItemPotion(117)).setIconCoord(13, 8).setItemName("potion");
    public static Item glassBottle = (new ItemGlassBottle(118)).setIconCoord(12, 8).setItemName("glassBottle");
    public static Item spiderEye;
    public static Item fermentedSpiderEye;
    public static Item blazePowder;
    public static Item magmaCream;
    public static Item brewingStand;
    public static Item cauldron;
    public static Item eyeOfEnder = (new ItemEnderEye(125)).setIconCoord(11, 9).setItemName("eyeOfEnder");
    public static Item speckledMelon;
    public static Item monsterPlacer = (new ItemMonsterPlacer(127)).setIconCoord(9, 9).setItemName("monsterPlacer");

    /**
     * Bottle o' Enchanting. Drops between 1 and 3 experience orbs when thrown.
     */
    public static Item expBottle = (new ItemExpBottle(128)).setIconCoord(11, 10).setItemName("expBottle");

    /**
     * Fire Charge. When used in a dispenser it fires a fireball similiar to a Ghast's.
     */
    public static Item fireballCharge = (new ItemFireball(129)).setIconCoord(14, 2).setItemName("fireball");
    public static Item record13 = (new ItemRecord(2000, "13")).setIconCoord(0, 15).setItemName("record");
    public static Item recordCat = (new ItemRecord(2001, "cat")).setIconCoord(1, 15).setItemName("record");
    public static Item recordBlocks = (new ItemRecord(2002, "blocks")).setIconCoord(2, 15).setItemName("record");
    public static Item recordChirp = (new ItemRecord(2003, "chirp")).setIconCoord(3, 15).setItemName("record");
    public static Item recordFar = (new ItemRecord(2004, "far")).setIconCoord(4, 15).setItemName("record");
    public static Item recordMall = (new ItemRecord(2005, "mall")).setIconCoord(5, 15).setItemName("record");
    public static Item recordMellohi = (new ItemRecord(2006, "mellohi")).setIconCoord(6, 15).setItemName("record");
    public static Item recordStal = (new ItemRecord(2007, "stal")).setIconCoord(7, 15).setItemName("record");
    public static Item recordStrad = (new ItemRecord(2008, "strad")).setIconCoord(8, 15).setItemName("record");
    public static Item recordWard = (new ItemRecord(2009, "ward")).setIconCoord(9, 15).setItemName("record");
    public static Item record11 = (new ItemRecord(2010, "11")).setIconCoord(10, 15).setItemName("record");

    /** Item index + 256 */
    public final int shiftedIndex;

    /** Maximum size of the stack. */
    protected int maxStackSize;

    /** Maximum damage an item can handle. */
    private int maxDamage;

    /** Icon index in the icons table. */
    protected int iconIndex;

    /** If true, render the object in full 3D, like weapons and tools. */
    protected boolean bFull3D;

    /**
     * Some items (like dyes) have multiple subtypes on same item, this is field define this behavior
     */
    protected boolean hasSubtypes;
    private Item containerItem;
    private String potionEffect;

    /** full name of item from language file */
    private String itemName;

    protected Item(int par1)
    {
        maxStackSize = 64;
        maxDamage = 0;
        bFull3D = false;
        hasSubtypes = false;
        containerItem = null;
        potionEffect = null;
        shiftedIndex = 256 + par1;

        if (itemsList[256 + par1] != null)
        {
            System.out.println((new StringBuilder()).append("CONFLICT @ ").append(par1).toString());
        }

        itemsList[256 + par1] = this;
    }

    /**
     * Sets the icon index for this item. Returns the item.
     */
    public Item setIconIndex(int par1)
    {
        iconIndex = par1;
        return this;
    }

    public Item setMaxStackSize(int par1)
    {
        maxStackSize = par1;
        return this;
    }

    public Item setIconCoord(int par1, int par2)
    {
        iconIndex = par1 + par2 * 16;
        return this;
    }

    /**
     * Gets an icon index based on an item's damage value
     */
    public int getIconFromDamage(int par1)
    {
        return iconIndex;
    }

    /**
     * Returns the icon index of the stack given as argument.
     */
    public final int getIconIndex(ItemStack par1ItemStack)
    {
        return getIconFromDamage(par1ItemStack.getItemDamage());
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS !
     */
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int i, int j, int k, int l)
    {
        return false;
    }

    /**
     * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
     * sword
     */
    public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
    {
        return 1.0F;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        return par1ItemStack;
    }

    public ItemStack onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        return par1ItemStack;
    }

    /**
     * Returns the maximum size of the stack for a specific item. *Isn't this more a Set than a Get?*
     */
    public int getItemStackLimit()
    {
        return maxStackSize;
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int par1)
    {
        return 0;
    }

    public boolean getHasSubtypes()
    {
        return hasSubtypes;
    }

    protected Item setHasSubtypes(boolean par1)
    {
        hasSubtypes = par1;
        return this;
    }

    /**
     * Returns the maximum damage an item can take.
     */
    public int getMaxDamage()
    {
        return maxDamage;
    }

    /**
     * set max damage of an Item
     */
    protected Item setMaxDamage(int par1)
    {
        maxDamage = par1;
        return this;
    }

    public boolean isDamageable()
    {
        return maxDamage > 0 && !hasSubtypes;
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving)
    {
        return false;
    }

    public boolean onBlockDestroyed(ItemStack par1ItemStack, int par2, int par3, int i, int j, EntityLiving entityliving)
    {
        return false;
    }

    /**
     * Returns the damage against a given entity.
     */
    public int getDamageVsEntity(Entity par1Entity)
    {
        return 1;
    }

    /**
     * Returns if the item (tool) can harvest results from the block type.
     */
    public boolean canHarvestBlock(Block par1Block)
    {
        return false;
    }

    /**
     * Called when a player right clicks a entity with a item.
     */
    public void useItemOnEntity(ItemStack itemstack, EntityLiving entityliving)
    {
    }

    /**
     * Sets bFull3D to True and return the object.
     */
    public Item setFull3D()
    {
        bFull3D = true;
        return this;
    }

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    public boolean isFull3D()
    {
        return bFull3D;
    }

    /**
     * Returns true if this item should be rotated by 180 degrees around the Y axis when being held in an entities
     * hands.
     */
    public boolean shouldRotateAroundWhenRendering()
    {
        return false;
    }

    /**
     * set name of item from language file
     */
    public Item setItemName(String par1Str)
    {
        itemName = (new StringBuilder()).append("item.").append(par1Str).toString();
        return this;
    }

    public String getLocalItemName(ItemStack par1ItemStack)
    {
        String s = getItemNameIS(par1ItemStack);

        if (s == null)
        {
            return "";
        }
        else
        {
            return StatCollector.translateToLocal(s);
        }
    }

    public String getItemName()
    {
        return itemName;
    }

    public String getItemNameIS(ItemStack par1ItemStack)
    {
        return itemName;
    }

    public Item setContainerItem(Item par1Item)
    {
        containerItem = par1Item;
        return this;
    }

    /**
     * If this returns true, after a recipe involving this item is crafted the container item will be added to the
     * player's inventory instead of remaining in the crafting grid.
     */
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack par1ItemStack)
    {
        return true;
    }

    public boolean func_46056_k()
    {
        return false;
    }

    public Item getContainerItem()
    {
        return containerItem;
    }

    /**
     * True if this Item has a container item (a.k.a. crafting result)
     */
    public boolean hasContainerItem()
    {
        return containerItem != null;
    }

    public String getStatName()
    {
        return StatCollector.translateToLocal((new StringBuilder()).append(getItemName()).append(".name").toString());
    }

    public int getColorFromDamage(int par1, int par2)
    {
        return 0xffffff;
    }

    /**
     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
     * update it's contents.
     */
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag)
    {
    }

    /**
     * Called when item is crafted/smelted. Used only by maps so far.
     */
    public void onCreated(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.none;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 0;
    }

    /**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
    public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int i)
    {
    }

    /**
     * Sets the string representing this item's effect on a potion when used as an ingredient.
     */
    protected Item setPotionEffect(String par1Str)
    {
        potionEffect = par1Str;
        return this;
    }

    /**
     * Returns a string representing what this item does to a potion.
     */
    public String getPotionEffect()
    {
        return potionEffect;
    }

    /**
     * Returns true if this item serves as a potion ingredient (its ingredient information is not null).
     */
    public boolean isPotionIngredient()
    {
        return potionEffect != null;
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack itemstack, List list)
    {
    }

    public String getItemDisplayName(ItemStack par1ItemStack)
    {
        String s = (new StringBuilder()).append("").append(StringTranslate.getInstance().translateNamedKey(getLocalItemName(par1ItemStack))).toString().trim();
        return s;
    }

    public boolean hasEffect(ItemStack par1ItemStack)
    {
        return par1ItemStack.isItemEnchanted();
    }

    /**
     * Return an item rarity from EnumRarity
     */
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        if (par1ItemStack.isItemEnchanted())
        {
            return EnumRarity.rare;
        }
        else
        {
            return EnumRarity.common;
        }
    }

    /**
     * Checks isDamagable and if it cannot be stacked
     */
    public boolean isItemTool(ItemStack par1ItemStack)
    {
        return getItemStackLimit() == 1 && isDamageable();
    }

    protected MovingObjectPosition getMovingObjectPositionFromPlayer(World par1World, EntityPlayer par2EntityPlayer, boolean par3)
    {
        float f = 1.0F;
        float f1 = par2EntityPlayer.prevRotationPitch + (par2EntityPlayer.rotationPitch - par2EntityPlayer.prevRotationPitch) * f;
        float f2 = par2EntityPlayer.prevRotationYaw + (par2EntityPlayer.rotationYaw - par2EntityPlayer.prevRotationYaw) * f;
        double d = par2EntityPlayer.prevPosX + (par2EntityPlayer.posX - par2EntityPlayer.prevPosX) * (double)f;
        double d1 = (par2EntityPlayer.prevPosY + (par2EntityPlayer.posY - par2EntityPlayer.prevPosY) * (double)f + 1.6200000000000001D) - (double)par2EntityPlayer.yOffset;
        double d2 = par2EntityPlayer.prevPosZ + (par2EntityPlayer.posZ - par2EntityPlayer.prevPosZ) * (double)f;
        Vec3D vec3d = Vec3D.createVector(d, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.01745329F - (float)Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.01745329F - (float)Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.01745329F);
        float f6 = MathHelper.sin(-f1 * 0.01745329F);
        float f7 = f4 * f5;
        float f8 = f6;
        float f9 = f3 * f5;
        double d3 = 5D;
        Vec3D vec3d1 = vec3d.addVector((double)f7 * d3, (double)f8 * d3, (double)f9 * d3);
        MovingObjectPosition movingobjectposition = par1World.rayTraceBlocks_do_do(vec3d, vec3d1, par3, !par3);
        return movingobjectposition;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
        return 0;
    }

    public boolean func_46058_c()
    {
        return false;
    }

    public int func_46057_a(int par1, int par2)
    {
        return getIconFromDamage(par1);
    }

    static
    {
        shovelSteel = (new ItemSpade(0, EnumToolMaterial.IRON)).setIconCoord(2, 5).setItemName("shovelIron");
        pickaxeSteel = (new ItemPickaxe(1, EnumToolMaterial.IRON)).setIconCoord(2, 6).setItemName("pickaxeIron");
        axeSteel = (new ItemAxe(2, EnumToolMaterial.IRON)).setIconCoord(2, 7).setItemName("hatchetIron");
        swordSteel = (new ItemSword(11, EnumToolMaterial.IRON)).setIconCoord(2, 4).setItemName("swordIron");
        swordWood = (new ItemSword(12, EnumToolMaterial.WOOD)).setIconCoord(0, 4).setItemName("swordWood");
        shovelWood = (new ItemSpade(13, EnumToolMaterial.WOOD)).setIconCoord(0, 5).setItemName("shovelWood");
        pickaxeWood = (new ItemPickaxe(14, EnumToolMaterial.WOOD)).setIconCoord(0, 6).setItemName("pickaxeWood");
        axeWood = (new ItemAxe(15, EnumToolMaterial.WOOD)).setIconCoord(0, 7).setItemName("hatchetWood");
        swordStone = (new ItemSword(16, EnumToolMaterial.STONE)).setIconCoord(1, 4).setItemName("swordStone");
        shovelStone = (new ItemSpade(17, EnumToolMaterial.STONE)).setIconCoord(1, 5).setItemName("shovelStone");
        pickaxeStone = (new ItemPickaxe(18, EnumToolMaterial.STONE)).setIconCoord(1, 6).setItemName("pickaxeStone");
        axeStone = (new ItemAxe(19, EnumToolMaterial.STONE)).setIconCoord(1, 7).setItemName("hatchetStone");
        swordDiamond = (new ItemSword(20, EnumToolMaterial.EMERALD)).setIconCoord(3, 4).setItemName("swordDiamond");
        shovelDiamond = (new ItemSpade(21, EnumToolMaterial.EMERALD)).setIconCoord(3, 5).setItemName("shovelDiamond");
        pickaxeDiamond = (new ItemPickaxe(22, EnumToolMaterial.EMERALD)).setIconCoord(3, 6).setItemName("pickaxeDiamond");
        axeDiamond = (new ItemAxe(23, EnumToolMaterial.EMERALD)).setIconCoord(3, 7).setItemName("hatchetDiamond");
        swordGold = (new ItemSword(27, EnumToolMaterial.GOLD)).setIconCoord(4, 4).setItemName("swordGold");
        shovelGold = (new ItemSpade(28, EnumToolMaterial.GOLD)).setIconCoord(4, 5).setItemName("shovelGold");
        pickaxeGold = (new ItemPickaxe(29, EnumToolMaterial.GOLD)).setIconCoord(4, 6).setItemName("pickaxeGold");
        axeGold = (new ItemAxe(30, EnumToolMaterial.GOLD)).setIconCoord(4, 7).setItemName("hatchetGold");
        gunpowder = (new Item(33)).setIconCoord(8, 2).setItemName("sulphur").setPotionEffect(PotionHelper.gunpowderEffect);
        hoeWood = (new ItemHoe(34, EnumToolMaterial.WOOD)).setIconCoord(0, 8).setItemName("hoeWood");
        hoeStone = (new ItemHoe(35, EnumToolMaterial.STONE)).setIconCoord(1, 8).setItemName("hoeStone");
        hoeSteel = (new ItemHoe(36, EnumToolMaterial.IRON)).setIconCoord(2, 8).setItemName("hoeIron");
        hoeDiamond = (new ItemHoe(37, EnumToolMaterial.EMERALD)).setIconCoord(3, 8).setItemName("hoeDiamond");
        hoeGold = (new ItemHoe(38, EnumToolMaterial.GOLD)).setIconCoord(4, 8).setItemName("hoeGold");
        seeds = (new ItemSeeds(39, Block.crops.blockID, Block.tilledField.blockID)).setIconCoord(9, 0).setItemName("seeds");
        helmetLeather = (new ItemArmor(42, EnumArmorMaterial.CLOTH, 0, 0)).setIconCoord(0, 0).setItemName("helmetCloth");
        plateLeather = (new ItemArmor(43, EnumArmorMaterial.CLOTH, 0, 1)).setIconCoord(0, 1).setItemName("chestplateCloth");
        legsLeather = (new ItemArmor(44, EnumArmorMaterial.CLOTH, 0, 2)).setIconCoord(0, 2).setItemName("leggingsCloth");
        bootsLeather = (new ItemArmor(45, EnumArmorMaterial.CLOTH, 0, 3)).setIconCoord(0, 3).setItemName("bootsCloth");
        helmetChain = (new ItemArmor(46, EnumArmorMaterial.CHAIN, 1, 0)).setIconCoord(1, 0).setItemName("helmetChain");
        plateChain = (new ItemArmor(47, EnumArmorMaterial.CHAIN, 1, 1)).setIconCoord(1, 1).setItemName("chestplateChain");
        legsChain = (new ItemArmor(48, EnumArmorMaterial.CHAIN, 1, 2)).setIconCoord(1, 2).setItemName("leggingsChain");
        bootsChain = (new ItemArmor(49, EnumArmorMaterial.CHAIN, 1, 3)).setIconCoord(1, 3).setItemName("bootsChain");
        helmetSteel = (new ItemArmor(50, EnumArmorMaterial.IRON, 2, 0)).setIconCoord(2, 0).setItemName("helmetIron");
        plateSteel = (new ItemArmor(51, EnumArmorMaterial.IRON, 2, 1)).setIconCoord(2, 1).setItemName("chestplateIron");
        legsSteel = (new ItemArmor(52, EnumArmorMaterial.IRON, 2, 2)).setIconCoord(2, 2).setItemName("leggingsIron");
        bootsSteel = (new ItemArmor(53, EnumArmorMaterial.IRON, 2, 3)).setIconCoord(2, 3).setItemName("bootsIron");
        helmetDiamond = (new ItemArmor(54, EnumArmorMaterial.DIAMOND, 3, 0)).setIconCoord(3, 0).setItemName("helmetDiamond");
        plateDiamond = (new ItemArmor(55, EnumArmorMaterial.DIAMOND, 3, 1)).setIconCoord(3, 1).setItemName("chestplateDiamond");
        legsDiamond = (new ItemArmor(56, EnumArmorMaterial.DIAMOND, 3, 2)).setIconCoord(3, 2).setItemName("leggingsDiamond");
        bootsDiamond = (new ItemArmor(57, EnumArmorMaterial.DIAMOND, 3, 3)).setIconCoord(3, 3).setItemName("bootsDiamond");
        helmetGold = (new ItemArmor(58, EnumArmorMaterial.GOLD, 4, 0)).setIconCoord(4, 0).setItemName("helmetGold");
        plateGold = (new ItemArmor(59, EnumArmorMaterial.GOLD, 4, 1)).setIconCoord(4, 1).setItemName("chestplateGold");
        legsGold = (new ItemArmor(60, EnumArmorMaterial.GOLD, 4, 2)).setIconCoord(4, 2).setItemName("leggingsGold");
        bootsGold = (new ItemArmor(61, EnumArmorMaterial.GOLD, 4, 3)).setIconCoord(4, 3).setItemName("bootsGold");
        appleGold = (new ItemAppleGold(66, 4, 1.2F, false)).setAlwaysEdible().setPotionEffect(Potion.regeneration.id, 5, 0, 1.0F).setIconCoord(11, 0).setItemName("appleGold");
        doorWood = (new ItemDoor(68, Material.wood)).setIconCoord(11, 2).setItemName("doorWood");
        bucketEmpty = (new ItemBucket(69, 0)).setIconCoord(10, 4).setItemName("bucket");
        bucketWater = (new ItemBucket(70, Block.waterMoving.blockID)).setIconCoord(11, 4).setItemName("bucketWater").setContainerItem(bucketEmpty);
        bucketLava = (new ItemBucket(71, Block.lavaMoving.blockID)).setIconCoord(12, 4).setItemName("bucketLava").setContainerItem(bucketEmpty);
        doorSteel = (new ItemDoor(74, Material.iron)).setIconCoord(12, 2).setItemName("doorIron");
        redstone = (new ItemRedstone(75)).setIconCoord(8, 3).setItemName("redstone").setPotionEffect(PotionHelper.redstoneEffect);
        bucketMilk = (new ItemBucketMilk(79)).setIconCoord(13, 4).setItemName("milk").setContainerItem(bucketEmpty);
        reed = (new ItemReed(82, Block.reed)).setIconCoord(11, 1).setItemName("reeds");
        lightStoneDust = (new Item(92)).setIconCoord(9, 4).setItemName("yellowDust").setPotionEffect(PotionHelper.glowstoneEffect);
        sugar = (new Item(97)).setIconCoord(13, 0).setItemName("sugar").setPotionEffect(PotionHelper.sugarEffect);
        cake = (new ItemReed(98, Block.cake)).setMaxStackSize(1).setIconCoord(13, 1).setItemName("cake");
        redstoneRepeater = (new ItemReed(100, Block.redstoneRepeaterIdle)).setIconCoord(6, 5).setItemName("diode");
        pumpkinSeeds = (new ItemSeeds(105, Block.pumpkinStem.blockID, Block.tilledField.blockID)).setIconCoord(13, 3).setItemName("seeds_pumpkin");
        melonSeeds = (new ItemSeeds(106, Block.melonStem.blockID, Block.tilledField.blockID)).setIconCoord(14, 3).setItemName("seeds_melon");
        chickenRaw = (new ItemFood(109, 2, 0.3F, true)).setPotionEffect(Potion.hunger.id, 30, 0, 0.3F).setIconCoord(9, 7).setItemName("chickenRaw");
        rottenFlesh = (new ItemFood(111, 4, 0.1F, true)).setPotionEffect(Potion.hunger.id, 30, 0, 0.8F).setIconCoord(11, 5).setItemName("rottenFlesh");
        ghastTear = (new Item(114)).setIconCoord(11, 7).setItemName("ghastTear").setPotionEffect(PotionHelper.ghastTearEffect);
        netherStalkSeeds = (new ItemSeeds(116, Block.netherStalk.blockID, Block.slowSand.blockID)).setIconCoord(13, 7).setItemName("netherStalkSeeds").setPotionEffect("+4");
        spiderEye = (new ItemFood(119, 2, 0.8F, false)).setPotionEffect(Potion.poison.id, 5, 0, 1.0F).setIconCoord(11, 8).setItemName("spiderEye").setPotionEffect(PotionHelper.spiderEyeEffect);
        fermentedSpiderEye = (new Item(120)).setIconCoord(10, 8).setItemName("fermentedSpiderEye").setPotionEffect(PotionHelper.fermentedSpiderEyeEffect);
        blazePowder = (new Item(121)).setIconCoord(13, 9).setItemName("blazePowder").setPotionEffect(PotionHelper.blazePowderEffect);
        magmaCream = (new Item(122)).setIconCoord(13, 10).setItemName("magmaCream").setPotionEffect(PotionHelper.magmaCreamEffect);
        brewingStand = (new ItemReed(123, Block.brewingStand)).setIconCoord(12, 10).setItemName("brewingStand");
        cauldron = (new ItemReed(124, Block.cauldron)).setIconCoord(12, 9).setItemName("cauldron");
        speckledMelon = (new Item(126)).setIconCoord(9, 8).setItemName("speckledMelon").setPotionEffect(PotionHelper.speckledMelonEffect);
        StatList.initStats();
    }
}
