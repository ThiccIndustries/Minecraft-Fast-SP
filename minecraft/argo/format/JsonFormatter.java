package argo.format;

import argo.jdom.JsonRootNode;

public interface JsonFormatter
{
    public abstract String format(JsonRootNode jsonrootnode);
}
