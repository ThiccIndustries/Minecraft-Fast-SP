package argo.format;

import argo.jdom.JsonNodeType;

class CompactJsonFormatter_JsonNodeType
{
    static final int enumJsonNodeTypeMappingArray[];

    static
    {
        enumJsonNodeTypeMappingArray = new int[JsonNodeType.values().length];

        try
        {
            enumJsonNodeTypeMappingArray[JsonNodeType.ARRAY.ordinal()] = 1;
        }
        catch (NoSuchFieldError nosuchfielderror) { }

        try
        {
            enumJsonNodeTypeMappingArray[JsonNodeType.OBJECT.ordinal()] = 2;
        }
        catch (NoSuchFieldError nosuchfielderror1) { }

        try
        {
            enumJsonNodeTypeMappingArray[JsonNodeType.STRING.ordinal()] = 3;
        }
        catch (NoSuchFieldError nosuchfielderror2) { }

        try
        {
            enumJsonNodeTypeMappingArray[JsonNodeType.NUMBER.ordinal()] = 4;
        }
        catch (NoSuchFieldError nosuchfielderror3) { }

        try
        {
            enumJsonNodeTypeMappingArray[JsonNodeType.FALSE.ordinal()] = 5;
        }
        catch (NoSuchFieldError nosuchfielderror4) { }

        try
        {
            enumJsonNodeTypeMappingArray[JsonNodeType.TRUE.ordinal()] = 6;
        }
        catch (NoSuchFieldError nosuchfielderror5) { }

        try
        {
            enumJsonNodeTypeMappingArray[JsonNodeType.NULL.ordinal()] = 7;
        }
        catch (NoSuchFieldError nosuchfielderror6) { }
    }
}
