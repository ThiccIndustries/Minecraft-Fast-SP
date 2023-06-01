package argo.jdom;

import java.util.*;

class JsonObjectNodeBuilder_List extends HashMap
{
    final JsonObjectNodeBuilder nodeBuilder;

    JsonObjectNodeBuilder_List(JsonObjectNodeBuilder par1JsonObjectNodeBuilder)
    {
        nodeBuilder = par1JsonObjectNodeBuilder;
        JsonFieldBuilder jsonfieldbuilder;

        for (Iterator iterator = JsonObjectNodeBuilder.func_27236_a(nodeBuilder).iterator(); iterator.hasNext(); put(jsonfieldbuilder.func_27303_b(), jsonfieldbuilder.buildValue()))
        {
            jsonfieldbuilder = (JsonFieldBuilder)iterator.next();
        }
    }
}
