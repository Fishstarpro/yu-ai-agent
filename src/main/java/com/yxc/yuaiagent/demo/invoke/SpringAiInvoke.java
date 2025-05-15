package com.yxc.yuaiagent.demo.invoke;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.CommandLineRunner;

/**
 * ClassName: SpringAiInvoke
 * Package: com.yxc.yuaiagent.demo.invoke
 * Description:
 *
 * @Author fishstar
 * @Create 2025/5/7 20:14
 * @Version 1.0
 */

public class SpringAiInvoke implements CommandLineRunner {

    @Resource
    private ChatModel dashscopeChatModel;

    @Resource
    private ChatModel ollamaChatModel;

    @Override
    public void run(String... args) throws Exception {
        AssistantMessage output = ollamaChatModel
                .call(new Prompt("你是什么模型"))
                .getResult()
                .getOutput();

        System.out.println(output.getText());
    }
}
