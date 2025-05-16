package com.yxc.yuaiagent.app;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * ClassName: LoveAppTest
 * Package: com.yxc.yuaiagent.app
 * Description:
 *
 * @Author fishstar
 * @Create 2025/5/8 19:48
 * @Version 1.0
 */
@SpringBootTest
@Slf4j
class LoveAppTest {

    @Resource
    private LoveApp loveApp;

    @Test
    void testChat() {
        String chatId = UUID.randomUUID().toString();
        // 第一轮
        String message = "你好,我是fishstar,我想让我的另一半bb更爱我,但我不知道怎么做";

        String content = loveApp.doChat(message, chatId);

//        LoveApp.LoveReport loveReport = loveApp.doChatWithReport(message, chatId);

//        log.info("LoveReport:{}", loveReport);

        Assertions.assertNotNull(content);
  /*      // 第二轮
        message = "我想让另一半(bb)更爱我";
        answer = loveApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
        // 第三轮
        message = "我的另一半叫什么来着？刚跟你说过，帮我回忆一下";
        answer = loveApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);*/
    }

    @Test
    void testPromptTemplate() {
        // 定义带有变量的模板
        String template = "你好，{name}。今天是{day}，天气{weather}。";

        // 创建模板对象
        PromptTemplate promptTemplate = new PromptTemplate(template);

        // 准备变量映射
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", "fishstar");
        variables.put("day", "星期一");
        variables.put("weather", "晴朗");

        // 生成最终提示文本
        String prompt = promptTemplate.render(variables);

        System.out.println(prompt);
    }

    @Test
    void doChatWithRag() {
        String chatId = UUID.randomUUID().toString();
        String message = "我已经结婚了，但是婚后关系不太亲密，怎么办？";
        String answer =  loveApp.doChatWithRag(message, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithReport() {
        String message = "你好,我是fishstar,我想让我的另一半bb更爱我,但我不知道怎么做";

        LoveApp.LoveReport loveReport = loveApp.doChatWithReport(message, UUID.randomUUID().toString());

        Assertions.assertNotNull(loveReport);
    }
}
