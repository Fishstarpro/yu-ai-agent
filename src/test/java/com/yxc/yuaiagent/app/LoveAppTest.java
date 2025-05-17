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

    @Test
    void doChatWithTools() {
        // 测试联网搜索问题的答案
        testMessage("周末想带女朋友去上海约会，帮我网上搜索几个适合情侣的小众打卡地");

        // 测试网页抓取：恋爱案例分析
        testMessage("最近和对象吵架了，看看编程导航网站（codefather.cn）的其他情侣是怎么解决矛盾的？");

        // 测试资源下载：图片下载
        testMessage("直接下载一张适合做手机壁纸的星空情侣图片为文件");

        // 测试终端操作：执行代码
        testMessage("执行 Python3 脚本来生成数据分析报告");

        // 测试文件操作：保存用户档案
        testMessage("保存我的恋爱档案为文件");

        // 测试 PDF 生成
        testMessage("生成一份‘七夕约会计划’PDF，包含餐厅预订、活动流程和礼物清单");
    }

    private void testMessage(String message) {
        String chatId = UUID.randomUUID().toString();
        String answer = loveApp.doChatWithTools(message, chatId);
        Assertions.assertNotNull(answer);
    }

}
