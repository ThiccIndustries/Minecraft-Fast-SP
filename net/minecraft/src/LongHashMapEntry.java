package net.minecraft.src;

class LongHashMapEntry
{
    /**
     * the key as a long (for playerInstances it is the x in the most significant 32 bits and then y)
     */
    final long key;

    /** the value held by the hash at the specified key */
    Object value;

    /** the next hashentry in the table */
    LongHashMapEntry nextEntry;
    final int hash;

    LongHashMapEntry(int par1, long par2, Object par4Obj, LongHashMapEntry par5LongHashMapEntry)
    {
        value = par4Obj;
        nextEntry = par5LongHashMapEntry;
        key = par2;
        hash = par1;
    }

    public final long getKey()
    {
        return key;
    }

    public final Object getValue()
    {
        return value;
    }

    public final boolean equals(Object par1Obj)
    {
        if (!(par1Obj instanceof LongHashMapEntry))
        {
            return false;
        }

        LongHashMapEntry longhashmapentry = (LongHashMapEntry)par1Obj;
        Long long1 = Long.valueOf(getKey());
        Long long2 = Long.valueOf(longhashmapentry.getKey());

        if (long1 == long2 || long1 != null && long1.equals(long2))
        {
            Object obj = getValue();
            Object obj1 = longhashmapentry.getValue();

            if (obj == obj1 || obj != null && obj.equals(obj1))
            {
                return true;
            }
        }

        return false;
    }

    public final int hashCode()
    {
        return LongHashMap.getHashCode(key);
    }

    public final String toString()
    {
        return (new StringBuilder()).append(getKey()).append("=").append(getValue()).toString();
    }
}
