package argo.jdom;

import java.util.*;

public final class JsonArrayNodeBuilder implements JsonNodeBuilder
{
    private final List elementBuilders = new LinkedList();

    JsonArrayNodeBuilder()
    {
    }

    /**
     * Adds the given element to the array that will be built.
     */
    public JsonArrayNodeBuilder withElement(JsonNodeBuilder par1JsonNodeBuilder)
    {
        elementBuilders.add(par1JsonNodeBuilder);
        return this;
    }

    public JsonRootNode build()
    {
        LinkedList linkedlist = new LinkedList();
        JsonNodeBuilder jsonnodebuilder;

        for (Iterator iterator = elementBuilders.iterator(); iterator.hasNext(); linkedlist.add(jsonnodebuilder.buildNode()))
        {
            jsonnodebuilder = (JsonNodeBuilder)iterator.next();
        }

        return JsonNodeFactories.aJsonArray(linkedlist);
    }

    public JsonNode buildNode()
    {
        return build();
    }
}
