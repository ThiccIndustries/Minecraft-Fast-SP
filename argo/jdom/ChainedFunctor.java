package argo.jdom;

final class ChainedFunctor implements Functor
{
    private final JsonNodeSelector parentJsonNodeSelector;
    private final JsonNodeSelector childJsonNodeSelector;

    ChainedFunctor(JsonNodeSelector par1JsonNodeSelector, JsonNodeSelector par2JsonNodeSelector)
    {
        parentJsonNodeSelector = par1JsonNodeSelector;
        childJsonNodeSelector = par2JsonNodeSelector;
    }

    public boolean matchesNode(Object par1Obj)
    {
        return parentJsonNodeSelector.matches(par1Obj) && childJsonNodeSelector.matches(parentJsonNodeSelector.getValue(par1Obj));
    }

    public Object applyTo(Object par1Obj)
    {
        Object obj;

        try
        {
            obj = parentJsonNodeSelector.getValue(par1Obj);
        }
        catch (JsonNodeDoesNotMatchChainedJsonNodeSelectorException jsonnodedoesnotmatchchainedjsonnodeselectorexception)
        {
            throw JsonNodeDoesNotMatchChainedJsonNodeSelectorException.func_27321_b(jsonnodedoesnotmatchchainedjsonnodeselectorexception, parentJsonNodeSelector);
        }

        Object obj1;

        try
        {
            obj1 = childJsonNodeSelector.getValue(obj);
        }
        catch (JsonNodeDoesNotMatchChainedJsonNodeSelectorException jsonnodedoesnotmatchchainedjsonnodeselectorexception1)
        {
            throw JsonNodeDoesNotMatchChainedJsonNodeSelectorException.func_27323_a(jsonnodedoesnotmatchchainedjsonnodeselectorexception1, parentJsonNodeSelector);
        }

        return obj1;
    }

    public String shortForm()
    {
        return childJsonNodeSelector.shortForm();
    }

    public String toString()
    {
        return (new StringBuilder()).append(parentJsonNodeSelector.toString()).append(", with ").append(childJsonNodeSelector.toString()).toString();
    }
}
