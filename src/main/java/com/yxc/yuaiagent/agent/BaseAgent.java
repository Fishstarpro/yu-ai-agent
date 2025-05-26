package com.yxc.yuaiagent.agent;


import com.yxc.yuaiagent.agent.model.AgentState;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * ClassName: BaseAgent
 * Package: com.yxc.yuaiagent.agent
 * @Author fishstar
 * @Create 2025/5/22 22:35
 * @Version 1.0
 * Description:
 *抽象基础代理类，用于管理代理状态和执行流程。
 *提供状态转换、内存管理和基于步骤的执行循环的基础功能。
 *子类必须实现step方法。
 */
@Data
@Slf4j
public abstract class BaseAgent {

    //智能体名称
    private String name;

    //提示词
    private String systemPrompt;

    private String nextStepPrompt;

    //智能体状态
    private AgentState state = AgentState.IDLE;

    //LLM
    private ChatClient chatClient;

    //对话记忆(自主维护对话上下文)
    private List<Message> messages = new ArrayList<>();

    //当前步骤
    private int currentStep = 0;

    //最大步骤
    private int maxSteps = 10;

    /**
     * 执行代理的主要方法，处理代理的状态转换和执行流程。
     * 子类必须实现step方法。
     */
    public String run(String userPrompt) {
        //1.基础校验
        if (this.state != AgentState.IDLE) {
            throw new IllegalStateException("Agent is not in IDLE state");
        }

        if (userPrompt == null || userPrompt.isEmpty()) {
            throw new IllegalArgumentException("User prompt cannot be null or empty");
        }
        //2.状态转换
        this.state = AgentState.RUNNING;
        //3.添加用户提示词到对话记忆中
        this.messages.add(new UserMessage(userPrompt));
        //4.执行step方法
        try {
            //记录每个步骤结果
            List<String> result = new ArrayList<>();

            for (int i = 0; i < maxSteps && state != AgentState.FINISHED; i++) {
                currentStep++;

                log.info("Step" + currentStep + " is excuting...");

                String stepResult = step();

                result.add("Step" + currentStep + " result: " + stepResult);
            }

            if (currentStep >= maxSteps) {
                state = AgentState.FINISHED;

                result.add("Agent reached the maximum number of" + maxSteps + " steps.");
            }

            return String.join("\n", result);
        } catch (Exception e) {
            state = AgentState.ERROR;

            log.error("Error occurred during execution: " + e.getMessage());

            return "Error occurred during execution: " + e.getMessage();
        } finally {
            //5.清理资源
            this.cleanUp();
        }
    }

    /**
     * 执行代理的主要方法，处理代理的状态转换和执行流程(流式输出)。
     * 子类必须实现step方法。
     */
    public SseEmitter runByStream(String userPrompt) {
        SseEmitter sseEmitter = new SseEmitter(300000L);
        //1.基础校验
        try {
            if (this.state != AgentState.IDLE) {
                sseEmitter.send("Agent is not in IDLE state");

                sseEmitter.complete();
            }

            if (userPrompt == null || userPrompt.isEmpty()) {
                sseEmitter.send("User prompt cannot be null or empty");

                sseEmitter.complete();
            }
        } catch (IOException e) {
            sseEmitter.completeWithError(e);
        }

        CompletableFuture.runAsync(() -> {
            //2.状态转换
            this.state = AgentState.RUNNING;
            //3.添加用户提示词到对话记忆中
            this.messages.add(new UserMessage(userPrompt));
            //4.执行step方法
            try {
                //记录每个步骤结果
                List<String> result = new ArrayList<>();

                for (int i = 0; i < maxSteps && state != AgentState.FINISHED; i++) {
                    currentStep++;

                    log.info("Step" + currentStep + " is excuting...");

                    String stepResult = step();

                    sseEmitter.send("Step" + currentStep + " result: " + stepResult);
                }

                if (currentStep >= maxSteps) {
                    state = AgentState.FINISHED;

                    sseEmitter.send("Agent reached the maximum number of" + maxSteps + " steps.");
                }

                sseEmitter.complete();
            } catch (Exception e) {
                state = AgentState.ERROR;

                log.error("Error occurred during execution: " + e.getMessage());

                try {
                    sseEmitter.send("Error occurred during execution: " + e.getMessage());

                    sseEmitter.complete();
                } catch (IOException ex) {
                    sseEmitter.completeWithError(e);
                }
            } finally {
                //5.清理资源
                this.cleanUp();
            }
        });
        // 设置超时回调
        sseEmitter.onTimeout(() -> {
            this.state = AgentState.FINISHED;
            this.cleanUp();
            log.warn("SSE connection timeout");
        });
        // 设置完成回调
        sseEmitter.onCompletion(() -> {
            if (this.state == AgentState.RUNNING) {
                this.state = AgentState.FINISHED;
            }
            this.cleanUp();
            log.info("SSE connection completed");
        });

        return sseEmitter;
    }

    /**
     * 子类必须实现的方法，用于执行代理的具体步骤。
     * @return 返回代理的执行结果
     */
    public abstract String step();

    /**
     * 清理资源的方法，子类可以根据需要重写。
     */
    protected void cleanUp() {
        //清理资源
    }
}
