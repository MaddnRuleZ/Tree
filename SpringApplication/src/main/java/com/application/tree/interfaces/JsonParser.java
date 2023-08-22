package com.application.tree.interfaces;

import com.application.exceptions.ProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface JsonParser {
    /**
     * converts tree structure to Json for editor view
     * @return JsonObject
     */
    ObjectNode toJsonEditor() throws NullPointerException, ProcessingException;

   /**
    * converts tree structure to Json for tree view
    *
    * @return JsonObject
    */
   JsonNode toJsonTree() throws NullPointerException, ProcessingException;
}