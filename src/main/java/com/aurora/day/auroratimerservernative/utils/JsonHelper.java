package com.aurora.day.auroratimerservernative.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonHelper {

    public static final ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectNode newEmptyNode(){
        return objectMapper.createObjectNode();
    }

    public static ArrayNode newArrayNode(Object list){
        return objectMapper.valueToTree(list);
    }
}
