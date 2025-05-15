package com.yxc.yuaiagent.demo.invoke;

import dev.langchain4j.community.model.dashscope.QwenChatModel;

/**
 * ClassName: LangChain4jAiInvoke
 * Package: com.yxc.yuaiagent.demo.invoke
 * Description:
 *
 * @Author fishstar
 * @Create 2025/5/7 22:38
 * @Version 1.0
 */
public class LangChain4jAiInvoke {

    public static void main(String[] args) {
        QwenChatModel qwenChatModel = QwenChatModel.builder()
                .apiKey(TestApiKey.apiKey)
                .modelName("qwen-plus-latest")
                .build();

        String result = qwenChatModel.chat("你好,我是fishstar");

        System.out.println(result);
    }
}
