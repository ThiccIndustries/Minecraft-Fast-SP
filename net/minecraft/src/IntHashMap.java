package net.minecraft.src;

import java.util.HashSet;
import java.util.Set;

public class IntHashMap
{
    private transient IntHashMapEntry slots[];

    /** The number of items stored in this map */
    private transient int count;

    /** The grow threshold */
    private int threshold;

    /** The scale factor used to determine when to grow the table */
    private final float growFactor = 0.75F;

    /** A serial stamp used to mark changes */
    private volatile transient int versionStamp;

    /** The set of all the keys stored in this MCHash object */
    private Set keySet;

    public IntHashMap()
    {
        keySet = new HashSet();
        threshold = 12;
        slots = new IntHashMapEntry[16];
    }

    /**
     * Makes the passed in integer suitable for hashing by a number of shifts
     */
    private static int computeHash(int par0)
    {
        par0 ^= par0 >>> 20 ^ par0 >>> 12;
        return par0 ^ par0 >>> 7 ^ par0 >>> 4;
    }

    /**
     * Computes the index of the slot for the hash and slot count passed in.
     */
    private static int getSlotIndex(int par0, int par1)
    {
        return par0 & par1 - 1;
    }

    /**
     * Returns the object associated to a key
     */
    public Object lookup(int par1)
    {
        int i = computeHash(par1);

        for (IntHashMapEntry inthashmapentry = slots[getSlotIndex(i, slots.length)]; inthashmapentry != null; inthashmapentry = inthashmapentry.nextEntry)
        {
            if (inthashmapentry.hashEntry == par1)
            {
                return inthashmapentry.valueEntry;
            }
        }

        return null;
    }

    /**
     * Return true if an object is associated with the given key
     */
    public boolean containsItem(int par1)
    {
        return lookupEntry(par1) != null;
    }

    /**
     * Returns the key/object mapping for a given key as a MCHashEntry
     */
    final IntHashMapEntry lookupEntry(int par1)
    {
        int i = computeHash(par1);

        for (IntHashMapEntry inthashmapentry = slots[getSlotIndex(i, slots.length)]; inthashmapentry != null; inthashmapentry = inthashmapentry.nextEntry)
        {
            if (inthashmapentry.hashEntry == par1)
            {
                return inthashmapentry;
            }
        }

        return null;
    }

    /**
     * Adds a key and associated value to this map
     */
    public void addKey(int par1, Object par2Obj)
    {
        keySet.add(Integer.valueOf(par1));
        int i = computeHash(par1);
        int j = getSlotIndex(i, slots.length);

        for (IntHashMapEntry inthashmapentry = slots[j]; inthashmapentry != null; inthashmapentry = inthashmapentry.nextEntry)
        {
            if (inthashmapentry.hashEntry == par1)
            {
                inthashmapentry.valueEntry = par2Obj;
            }
        }

        versionStamp++;
        insert(i, par1, par2Obj, j);
    }

    /**
     * Increases the number of hash slots
     */
    private void grow(int par1)
    {
        IntHashMapEntry ainthashmapentry[] = slots;
        int i = ainthashmapentry.length;

        if (i == 0x40000000)
        {
            threshold = 0x7fffffff;
            return;
        }
        else
        {
            IntHashMapEntry ainthashmapentry1[] = new IntHashMapEntry[par1];
            copyTo(ainthashmapentry1);
            slots = ainthashmapentry1;
            threshold = (int)((float)par1 * growFactor);
            return;
        }
    }

    /**
     * Copies the hash slots to a new array
     */
    private void copyTo(IntHashMapEntry par1ArrayOfIntHashMapEntry[])
    {
        IntHashMapEntry ainthashmapentry[] = slots;
        int i = par1ArrayOfIntHashMapEntry.length;

        for (int j = 0; j < ainthashmapentry.length; j++)
        {
            IntHashMapEntry inthashmapentry = ainthashmapentry[j];

            if (inthashmapentry == null)
            {
                continue;
            }

            ainthashmapentry[j] = null;

            do
            {
                IntHashMapEntry inthashmapentry1 = inthashmapentry.nextEntry;
                int k = getSlotIndex(inthashmapentry.slotHash, i);
                inthashmapentry.nextEntry = par1ArrayOfIntHashMapEntry[k];
                par1ArrayOfIntHashMapEntry[k] = inthashmapentry;
                inthashmapentry = inthashmapentry1;
            }
            while (inthashmapentry != null);
        }
    }

    /**
     * Removes the specified object from the map and returns it
     */
    public Object removeObject(int par1)
    {
        keySet.remove(Integer.valueOf(par1));
        IntHashMapEntry inthashmapentry = removeEntry(par1);
        return inthashmapentry != null ? inthashmapentry.valueEntry : null;
    }

    /**
     * Removes the specified entry from the map and returns it
     */
    final IntHashMapEntry removeEntry(int par1)
    {
        int i = computeHash(par1);
        int j = getSlotIndex(i, slots.length);
        IntHashMapEntry inthashmapentry = slots[j];
        IntHashMapEntry inthashmapentry1;
        IntHashMapEntry inthashmapentry2;

        for (inthashmapentry1 = inthashmapentry; inthashmapentry1 != null; inthashmapentry1 = inthashmapentry2)
        {
            inthashmapentry2 = inthashmapentry1.nextEntry;

            if (inthashmapentry1.hashEntry == par1)
            {
                versionStamp++;
                count--;

                if (inthashmapentry == inthashmapentry1)
                {
                    slots[j] = inthashmapentry2;
                }
                else
                {
                    inthashmapentry.nextEntry = inthashmapentry2;
                }

                return inthashmapentry1;
            }

            inthashmapentry = inthashmapentry1;
        }

        return inthashmapentry1;
    }

    /**
     * Removes all entries from the map
     */
    public void clearMap()
    {
        versionStamp++;
        IntHashMapEntry ainthashmapentry[] = slots;

        for (int i = 0; i < ainthashmapentry.length; i++)
        {
            ainthashmapentry[i] = null;
        }

        count = 0;
    }

    /**
     * Adds an object to a slot
     */
    private void insert(int par1, int par2, Object par3Obj, int par4)
    {
        IntHashMapEntry inthashmapentry = slots[par4];
        slots[par4] = new IntHashMapEntry(par1, par2, par3Obj, inthashmapentry);

        if (count++ >= threshold)
        {
            grow(2 * slots.length);
        }
    }

    /**
     * Return the Set of all keys stored in this MCHash object
     */
    public Set getKeySet()
    {
        return keySet;
    }

    /**
     * Returns the hash code for a key
     */
    static int getHash(int par0)
    {
        return computeHash(par0);
    }
}
