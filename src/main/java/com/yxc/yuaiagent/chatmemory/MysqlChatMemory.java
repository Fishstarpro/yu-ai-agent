package com.yxc.yuaiagent.chatmemory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 基于Mysql持久化对话记忆
 */
@Slf4j
@Component
public class MysqlChatMemory implements ChatMemory {
    private final JdbcTemplate jdbcTemplate;
    private ObjectMapper objectMapper;

    public MysqlChatMemory(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        // 注册自定义反序列化器
        // 创建模块
        SimpleModule module = new SimpleModule("MessageModule");

        module.addDeserializer(Message.class, new MessageDeserializer());

        this.objectMapper = new ObjectMapper().registerModule(module);

        log.info("已注册的模块: {}", objectMapper.getRegisteredModuleIds());
    }

    @Override
    public void add(String conversationId, List<Message> messages) {
        String sql = "INSERT INTO chat_messages (conversation_id, message_type, message_content) VALUES (?, ?, ?)";
        messages.forEach(message -> {
            try {
                String contentJson = objectMapper.writeValueAsString(message);
                String type = message.getMessageType().name();
                jdbcTemplate.update(sql, conversationId, type, contentJson);
            } catch (Exception e) {
                throw new RuntimeException("Failed to add message", e);
            }
        });
    }

    @Override
    public List<Message> get(String conversationId, int lastN) {
        String sql = "SELECT message_type, message_content FROM chat_messages WHERE conversation_id = ? ORDER BY id DESC LIMIT ?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, conversationId, lastN);
        return rows.stream()
                .map(row -> {
                    String typeName = (String) row.get("message_type");
                    String contentJson = (String) row.get("message_content");
                    try {
                                return objectMapper.readValue(contentJson, Message.class);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to deserialize message", e);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public void clear(String conversationId) {
        String sql = "DELETE FROM chat_messages WHERE conversation_id = ?";
        jdbcTemplate.update(sql, conversationId);
    }
}