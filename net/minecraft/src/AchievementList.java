package net.minecraft.src;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class AchievementList
{
    /** Is the smallest column used to display a achievement on the GUI. */
    public static int minDisplayColumn;

    /** Is the smallest row used to display a achievement on the GUI. */
    public static int minDisplayRow;

    /** Is the biggest column used to display a achievement on the GUI. */
    public static int maxDisplayColumn;

    /** Is the biggest row used to display a achievement on the GUI. */
    public static int maxDisplayRow;

    /** Holds a list of all registered achievements. */
    public static List achievementList;

    /** Is the 'open inventory' achievement. */
    public static Achievement openInventory;

    /** Is the 'getting wood' achievement. */
    public static Achievement mineWood;

    /** Is the 'benchmarking' achievement. */
    public static Achievement buildWorkBench;

    /** Is the 'time to mine' achievement. */
    public static Achievement buildPickaxe;

    /** Is the 'hot topic' achievement. */
    public static Achievement buildFurnace;

    /** Is the 'acquire hardware' achievement. */
    public static Achievement acquireIron;

    /** Is the 'time to farm' achievement. */
    public static Achievement buildHoe;

    /** Is the 'bake bread' achievement. */
    public static Achievement makeBread;

    /** Is the 'the lie' achievement. */
    public static Achievement bakeCake;

    /** Is the 'getting a upgrade' achievement. */
    public static Achievement buildBetterPickaxe;

    /** Is the 'delicious fish' achievement. */
    public static Achievement cookFish;

    /** Is the 'on a rail' achievement */
    public static Achievement onARail;

    /** Is the 'time to strike' achievement. */
    public static Achievement buildSword;

    /** Is the 'monster hunter' achievement. */
    public static Achievement killEnemy;

    /** is the 'cow tipper' achievement. */
    public static Achievement killCow;

    /** Is the 'when pig fly' achievement. */
    public static Achievement flyPig;

    /** The achievement for killing a Skeleton from 50 meters aways. */
    public static Achievement snipeSkeleton;

    /** Is the 'DIAMONDS!' achievement */
    public static Achievement diamonds;

    /** Is the 'We Need to Go Deeper' achievement */
    public static Achievement portal;

    /** Is the 'Return to Sender' achievement */
    public static Achievement ghast;

    /** Is the 'Into Fire' achievement */
    public static Achievement blazeRod;

    /** Is the 'Local Brewery' achievement */
    public static Achievement potion;

    /** Is the 'The End?' achievement */
    public static Achievement theEnd;

    /** Is the 'The End.' achievement */
    public static Achievement theEnd2;

    /** Is the 'Enchanter' achievement */
    public static Achievement enchantments;
    public static Achievement overkill;

    /** Is the 'Librarian' achievement */
    public static Achievement bookcase;

    public AchievementList()
    {
    }

    /**
     * A stub functions called to make the static initializer for this class run.
     */
    public static void init()
    {
    }

    static
    {
        achievementList = new ArrayList();
        openInventory = (new Achievement(0, "openInventory", 0, 0, Item.book, null)).setIndependent().registerAchievement();
        mineWood = (new Achievement(1, "mineWood", 2, 1, Block.wood, openInventory)).registerAchievement();
        buildWorkBench = (new Achievement(2, "buildWorkBench", 4, -1, Block.workbench, mineWood)).registerAchievement();
        buildPickaxe = (new Achievement(3, "buildPickaxe", 4, 2, Item.pickaxeWood, buildWorkBench)).registerAchievement();
        buildFurnace = (new Achievement(4, "buildFurnace", 3, 4, Block.stoneOvenActive, buildPickaxe)).registerAchievement();
        acquireIron = (new Achievement(5, "acquireIron", 1, 4, Item.ingotIron, buildFurnace)).registerAchievement();
        buildHoe = (new Achievement(6, "buildHoe", 2, -3, Item.hoeWood, buildWorkBench)).registerAchievement();
        makeBread = (new Achievement(7, "makeBread", -1, -3, Item.bread, buildHoe)).registerAchievement();
        bakeCake = (new Achievement(8, "bakeCake", 0, -5, Item.cake, buildHoe)).registerAchievement();
        buildBetterPickaxe = (new Achievement(9, "buildBetterPickaxe", 6, 2, Item.pickaxeStone, buildPickaxe)).registerAchievement();
        cookFish = (new Achievement(10, "cookFish", 2, 6, Item.fishCooked, buildFurnace)).registerAchievement();
        onARail = (new Achievement(11, "onARail", 2, 3, Block.rail, acquireIron)).setSpecial().registerAchievement();
        buildSword = (new Achievement(12, "buildSword", 6, -1, Item.swordWood, buildWorkBench)).registerAchievement();
        killEnemy = (new Achievement(13, "killEnemy", 8, -1, Item.bone, buildSword)).registerAchievement();
        killCow = (new Achievement(14, "killCow", 7, -3, Item.leather, buildSword)).registerAchievement();
        flyPig = (new Achievement(15, "flyPig", 8, -4, Item.saddle, killCow)).setSpecial().registerAchievement();
        snipeSkeleton = (new Achievement(16, "snipeSkeleton", 7, 0, Item.bow, killEnemy)).setSpecial().registerAchievement();
        diamonds = (new Achievement(17, "diamonds", -1, 5, Item.diamond, acquireIron)).registerAchievement();
        portal = (new Achievement(18, "portal", -1, 7, Block.obsidian, diamonds)).registerAchievement();
        ghast = (new Achievement(19, "ghast", -4, 8, Item.ghastTear, portal)).setSpecial().registerAchievement();
        blazeRod = (new Achievement(20, "blazeRod", 0, 9, Item.blazeRod, portal)).registerAchievement();
        potion = (new Achievement(21, "potion", 2, 8, Item.potion, blazeRod)).registerAchievement();
        theEnd = (new Achievement(22, "theEnd", 3, 10, Item.eyeOfEnder, blazeRod)).setSpecial().registerAchievement();
        theEnd2 = (new Achievement(23, "theEnd2", 4, 13, Block.dragonEgg, theEnd)).setSpecial().registerAchievement();
        enchantments = (new Achievement(24, "enchantments", -4, 4, Block.enchantmentTable, diamonds)).registerAchievement();
        overkill = (new Achievement(25, "overkill", -4, 1, Item.swordDiamond, enchantments)).setSpecial().registerAchievement();
        bookcase = (new Achievement(26, "bookcase", -3, 6, Block.bookShelf, enchantments)).registerAchievement();
        System.out.println((new StringBuilder()).append(achievementList.size()).append(" achievements").toString());
    }
}
