package com.yxc.yuaiagent.agent;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.yxc.yuaiagent.agent.model.AgentState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.model.tool.ToolExecutionResult;
import org.springframework.ai.tool.ToolCallback;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: ToolCallAgent
 * Package: com.yxc.yuaiagent.agent
 *
 * @Author fishstar
 * @Create 2025/5/22 23:06
 * @Version 1.0
 * Description:
 */
@Slf4j
public class ToolCallAgent extends ReActAgent {

    //可以调用的工具
    private ToolCallback[] availableTools;

    //保存了工具调用信息的响应
    private ChatResponse toolCallChatResponse;

    //工具调用管理者
    private ToolCallingManager toolCallingManager;

    //禁用内置的工具调用机制,自己维护工具调用的上下文
    private ChatOptions chatOptions;

    public ToolCallAgent(ToolCallback[] availableTools) {
        this.availableTools = availableTools;

        this.toolCallingManager = ToolCallingManager.builder().build();
        //阿里大模型构造ChatOptions与其他的大模型不同
        this.chatOptions = DashScopeChatOptions.builder()
                .withProxyToolCalls(true)
                .build();
    }

    @Override
    public boolean think() {
        //1.添加nextStepPrompt到上下文中
        List<Message> messages = this.getMessages();

        if (this.getNextStepPrompt() != null && !this.getNextStepPrompt().isEmpty()) {
            messages.add(new UserMessage(getNextStepPrompt()));
        }
        //2.调用大模型得到响应
        try {
            Prompt prompt = new Prompt(messages, chatOptions);

            ChatResponse chatResponse = this.getChatClient().prompt(prompt)
                    .tools(availableTools)
                    .call()
                    .chatResponse();

            this.toolCallChatResponse = chatResponse;
        } catch (Exception e) {
            log.error(getName() + "思考过程中遇到了问题: " + e.getMessage() );

            messages.add(new AssistantMessage("处理时遇到了错误: " + e.getMessage()));

            return false;
        }
        //3.如果需要调用工具，返回true
        AssistantMessage assistantMessage = toolCallChatResponse.getResult().getOutput();

        String result = assistantMessage.getText();

        log.info(this.getName() + "思考结果：" + result);

        List<AssistantMessage.ToolCall> toolCallList = assistantMessage.getToolCalls();

        if (toolCallList != null && !toolCallList.isEmpty()) {
            log.info(getName() + "选择了" + toolCallList.size() + "个工具");

            String toolCallInfo = toolCallList.stream()
                    .map(toolCall ->
                            String.format("工具名称: %s, 工具参数: %s", toolCall.name(), toolCall.arguments()))
                    .collect(Collectors.joining("\n"));

            log.info(getName() + "选择了以下工具：\n" + toolCallInfo);

            return true;
        }
        //4.如果不需要调用工具，记录助手消息到上下文中
        messages.add(assistantMessage);

        log.info(getName() + "没有选择工具");

        return false;
    }

    @Override
    public String act() {
        //1.判断是否需要调用工具
        if (this.toolCallChatResponse.hasToolCalls() == false) {
            return "没有工具调用";
        }
        //2.执行工具调用
        List<Message> messages = this.getMessages();

        Prompt prompt = new Prompt(messages, chatOptions);

        ToolExecutionResult toolExecutionResult = toolCallingManager.executeToolCalls(prompt, toolCallChatResponse);
        //3.记录工具调用结果到上下文中
        setMessages(toolExecutionResult.conversationHistory());
        //4.返回工具调用结果0
        ToolResponseMessage toolResponseMessage = (ToolResponseMessage) CollUtil.getLast(toolExecutionResult.conversationHistory());

        String result = toolResponseMessage.getResponses().stream()
                .map(toolResponse -> "工具" + toolResponse.name() + "完成了任务, 工具结果: " + toolResponse.responseData())
                .collect(Collectors.joining("\n"));

        log.info(result);
        //5.判断是否调用了终止工具
        boolean isCalledTerminateTool = toolResponseMessage.getResponses().stream()
                .anyMatch(toolResponse -> "doTerminate".equals(toolResponse.name()));

        if (isCalledTerminateTool) {
            this.setState(AgentState.FINISHED);
        }

        return result;
    }
}
