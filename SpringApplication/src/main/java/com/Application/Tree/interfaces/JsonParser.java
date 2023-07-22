package com.Application.Tree.interfaces;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface JsonParser {
    /**
     * converts tree structure to Json for editor view
     * @return JsonObject
     */
    ObjectNode toJsonEditor() throws NullPointerException;

   /**
    * converts tree structure to Json for tree view
    * @return JsonObject
    */
   ArrayNode toJsonTree() throws NullPointerException;
}