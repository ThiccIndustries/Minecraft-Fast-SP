package argo.jdom;

import java.util.LinkedList;
import java.util.List;

public final class JsonNodeDoesNotMatchChainedJsonNodeSelectorException extends JsonNodeDoesNotMatchJsonNodeSelectorException
{
    final Functor failedNode;
    final List failPath;

    static JsonNodeDoesNotMatchJsonNodeSelectorException func_27322_a(Functor par0Functor)
    {
        return new JsonNodeDoesNotMatchChainedJsonNodeSelectorException(par0Functor, new LinkedList());
    }

    static JsonNodeDoesNotMatchJsonNodeSelectorException func_27323_a(JsonNodeDoesNotMatchChainedJsonNodeSelectorException par0JsonNodeDoesNotMatchChainedJsonNodeSelectorException, JsonNodeSelector par1JsonNodeSelector)
    {
        LinkedList linkedlist = new LinkedList(par0JsonNodeDoesNotMatchChainedJsonNodeSelectorException.failPath);
        linkedlist.add(par1JsonNodeSelector);
        return new JsonNodeDoesNotMatchChainedJsonNodeSelectorException(par0JsonNodeDoesNotMatchChainedJsonNodeSelectorException.failedNode, linkedlist);
    }

    static JsonNodeDoesNotMatchJsonNodeSelectorException func_27321_b(JsonNodeDoesNotMatchChainedJsonNodeSelectorException par0JsonNodeDoesNotMatchChainedJsonNodeSelectorException, JsonNodeSelector par1JsonNodeSelector)
    {
        LinkedList linkedlist = new LinkedList();
        linkedlist.add(par1JsonNodeSelector);
        return new JsonNodeDoesNotMatchChainedJsonNodeSelectorException(par0JsonNodeDoesNotMatchChainedJsonNodeSelectorException.failedNode, linkedlist);
    }

    private JsonNodeDoesNotMatchChainedJsonNodeSelectorException(Functor par1Functor, List par2List)
    {
        super((new StringBuilder()).append("Failed to match any JSON node at [").append(getShortFormFailPath(par2List)).append("]").toString());
        failedNode = par1Functor;
        failPath = par2List;
    }

    static String getShortFormFailPath(List par0List)
    {
        StringBuilder stringbuilder = new StringBuilder();

        for (int i = par0List.size() - 1; i >= 0; i--)
        {
            stringbuilder.append(((JsonNodeSelector)par0List.get(i)).shortForm());

            if (i != 0)
            {
                stringbuilder.append(".");
            }
        }

        return stringbuilder.toString();
    }

    public String toString()
    {
        return (new StringBuilder()).append("JsonNodeDoesNotMatchJsonNodeSelectorException{failedNode=").append(failedNode).append(", failPath=").append(failPath).append('}').toString();
    }
}
