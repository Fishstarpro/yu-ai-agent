package com.yxc.yuaiagent.rag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.preretrieval.query.expansion.MultiQueryExpander;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ClassName: MyMultiQueryExpander
 * Package: com.yxc.yuaiagent.rag
 * Description:
 *
 * @Author fishstar
 * @Create 2025/5/15 19:43
 * @Version 1.0
 */

/**
 * 文档过滤和检索-多查询扩展器
 */
@Component
public class MyMultiQueryExpander {

    MultiQueryExpander multiQueryExpander;

    public MyMultiQueryExpander(ChatModel dashscopeChatModel) {
        this.multiQueryExpander = MultiQueryExpander.builder()
                .chatClientBuilder(ChatClient.builder(dashscopeChatModel))
                .numberOfQueries(3)
                .build();
    }

    public List<Query> expand(String query) {
        return multiQueryExpander.expand(new Query(query));
    }
}
