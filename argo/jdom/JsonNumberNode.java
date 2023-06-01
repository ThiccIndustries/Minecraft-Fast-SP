package argo.jdom;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class JsonNumberNode extends JsonNode
{
    private static final Pattern PATTERN = Pattern.compile("(-?)(0|([1-9]([0-9]*)))(\\.[0-9]+)?((e|E)(\\+|-)?[0-9]+)?");
    private final String value;

    JsonNumberNode(String par1Str)
    {
        if (par1Str == null)
        {
            throw new NullPointerException("Attempt to construct a JsonNumber with a null value.");
        }

        if (!PATTERN.matcher(par1Str).matches())
        {
            throw new IllegalArgumentException((new StringBuilder()).append("Attempt to construct a JsonNumber with a String [").append(par1Str).append("] that does not match the JSON number specification.").toString());
        }
        else
        {
            value = par1Str;
            return;
        }
    }

    public JsonNodeType getType()
    {
        return JsonNodeType.NUMBER;
    }

    public String getText()
    {
        return value;
    }

    /**
     * return the fields associated with this node
     */
    public Map getFields()
    {
        throw new IllegalStateException("Attempt to get fields on a JsonNode without fields.");
    }

    public List getElements()
    {
        throw new IllegalStateException("Attempt to get elements on a JsonNode without elements.");
    }

    public boolean equals(Object par1Obj)
    {
        if (this == par1Obj)
        {
            return true;
        }

        if (par1Obj == null || getClass() != par1Obj.getClass())
        {
            return false;
        }
        else
        {
            JsonNumberNode jsonnumbernode = (JsonNumberNode)par1Obj;
            return value.equals(jsonnumbernode.value);
        }
    }

    public int hashCode()
    {
        return value.hashCode();
    }

    public String toString()
    {
        return (new StringBuilder()).append("JsonNumberNode value:[").append(value).append("]").toString();
    }
}
