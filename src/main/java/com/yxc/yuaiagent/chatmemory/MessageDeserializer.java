package com.yxc.yuaiagent.chatmemory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import java.io.IOException;

public class MessageDeserializer extends JsonDeserializer<Message> {

    @Override
    public Message deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        String type = node.get("messageType").asText();
        switch (type) {
            case "ASSISTANT":
                String assistantContent = node.get("text").asText();
                return new AssistantMessage(assistantContent);
            case "USER":
                String userTextContent = node.get("text").asText();
                return new UserMessage(userTextContent);
            case "SYSTEM":
                String systemTextContent = node.get("text").asText();
                return new SystemMessage(systemTextContent);
            default:
                throw new IllegalArgumentException("Unknown message type: " + type);
        }
    }
}