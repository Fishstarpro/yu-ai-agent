package com.yxc.yuaiagent.chatmemory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.ai.chat.messages.Message;

public class TestMessageDeserialization {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Message.class, new MessageDeserializer());
        mapper.registerModule(module);

        String json = "{\"messageType\":\"ASSISTANT\",\"text\":\"测试内容\"}";
        Message message = mapper.readValue(json, Message.class);
        System.out.println("反序列化结果: " + message.getClass().getName()); // 应输出 AssistantMessage
    }
}