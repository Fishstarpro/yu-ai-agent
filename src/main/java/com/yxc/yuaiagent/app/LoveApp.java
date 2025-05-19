package com.yxc.yuaiagent.app;

import com.yxc.yuaiagent.advisor.MyLoggerAdvisor;
import com.yxc.yuaiagent.rag.LoveAppRagCustomAdvisorFactory;
import com.yxc.yuaiagent.rag.QueryRewriter;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

/**
 * ClassName: LoveApp
 * Package: com.yxc.yuaiagent.app
 * Description:
 *
 * @Author fishstar
 * @Create 2025/5/8 19:27
 * @Version 1.0
 */
@Slf4j
@Component
public class LoveApp {

    @Resource
    private VectorStore loveAppVectorStore;

    @Resource
    private Advisor loveAppRagCloudAdvisor;

    @Resource
    private QueryRewriter QueryRewriter;

    @Resource
    private ChatMemory mysqlChatMemory;

    @Resource
    private ToolCallback[] allTools;

    private ChatClient chatClient;

    private static final String SYSTEM_PROMPT = "你是一个温暖专业的AI恋爱导师，用以下方式帮助用户：  \n" +
            "1. **先问清楚**：用一句话询问具体情境（如\"你们吵架时TA说了什么？\"）  \n" +
            "2. **给干货**：提供1-2条可操作建议（含具体话术示例）  \n" +
            "3. **情绪支持**：当检测到负面情绪时，用共情语句安抚（如\"听起来你很难过\"）  \n" +
            "4. **安全线**：遇到危险情况（如家暴）必须提醒求助  \n" +
            "\n" +
            "**示例对话：**  \n" +
            "用户：\"男友总说我不够主动怎么办？\"  \n" +
            "AI：\"他最近一次这么说是在什么情况下？（等待补充）  \n" +
            "假设他属于内向型，建议：  \n" +
            "1. 主动发条语音：\"刚才在想你说话的样子，突然笑了\"  \n" +
            "2. 约会时多问选择题：\"周末想去吃辣的还是清淡的？\"  \n" +
            "如果他说\"你终于主动了\"，可以回：\"因为和你聊天让我很放松呀~\"  \n" +
            "\n" +
            "---\n" +
            "\n" +
            "**技术要点：**  \n" +
            "- 预设基础心理学关键词（如共情/边界感/安全感）  \n" +
            "- 内置常见场景模板（表白/吵架/冷战/分手挽回）  \n" +
            "- 敏感词触发安全提醒（如\"控制\"\"威胁\"等）  \n" +
            "\n" +
            "这个版本更适合快速部署，需要再精简或补充某部分吗？";
    @Autowired
    private QueryRewriter queryRewriter;

    public LoveApp(ChatModel dashscopeChatModel) {
        //基于内存的对话存储
        ChatMemory chatMemory = new InMemoryChatMemory();
        //基于文件的对话存储
        //ChatMemory chatMemory = new FileChatMemory(System.getProperty("user.dir") + "/chatmemory");
        //基于Mysql的对话存储
        chatClient = ChatClient.builder(dashscopeChatModel)
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(chatMemory)
                )
                .build();
    }

    record LoveReport(String title, List<String> suggestions) {
    }

    public String doChat(String message, String chatId) {
        ChatResponse chatResponse = chatClient
                .prompt()
                .user(message)
                .system(SYSTEM_PROMPT)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .call()
                .chatResponse();

        String content = chatResponse.getResult().getOutput().getText();

        return content;
    }

    public LoveReport doChatWithReport(String message, String chatId) {
        LoveReport loveReport = chatClient
                .prompt()
                .user(message)
                .system(SYSTEM_PROMPT + "每次对话后都要生成恋爱结果，标题为{用户名}的恋爱报告，内容为建议列表")
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .advisors(new MyLoggerAdvisor())
                .call()
                .entity(LoveReport.class);

        return loveReport;
    }

    public String doChatWithRag(String message, String chatId) {
        //查询重写
        //String rewrite = queryRewriter.doQueryRewrite(message);

        ChatResponse chatResponse = chatClient
                .prompt()
                .user(message)
                .system(SYSTEM_PROMPT)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .advisors(new MyLoggerAdvisor())
                //.advisors(new QuestionAnswerAdvisor(loveAppVectorStore))
                .advisors(LoveAppRagCustomAdvisorFactory.createLoveAppRagCustomAdvisor(loveAppVectorStore, "单身"))
                .call()
                .chatResponse();

        String content = chatResponse.getResult().getOutput().getText();

        return content;
    }

    public String doChatWithTools(String message, String chatId) {
        ChatResponse response = chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                // 开启日志，便于观察效果
                .advisors(new MyLoggerAdvisor())
                .tools(allTools)
                .call()
                .chatResponse();
        String content = response.getResult().getOutput().getText();
        log.info("content: {}", content);
        return content;
    }

    @Resource
    private ToolCallbackProvider toolCallbackProvider;

    public String doChatWithMcp(String message, String chatId) {
        ChatResponse response = chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                // 开启日志，便于观察效果
                .advisors(new MyLoggerAdvisor())
                .tools(toolCallbackProvider)
                .call()
                .chatResponse();
        String content = response.getResult().getOutput().getText();
        log.info("content: {}", content);
        return content;
    }

}
