package com.Application.Tree.interfaces;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface JsonParser {
    /**
     * converts tree to Json for editor view
     * @return JsonObject
     */
   public ObjectNode toJsonEditor();

   /**
    * converts tree to Json for tree view
    * @return JsonObject
    */
   public ObjectNode toJsonTree();
}