package com.yxc.yuaiagent.rag;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.ai.rag.Query;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * ClassName: MyMultiQueryExpanderTest
 * Package: com.yxc.yuaiagent.rag
 * Description:
 *
 * @Author fishstar
 * @Create 2025/5/15 19:49
 * @Version 1.0
 */
@SpringBootTest
class MyMultiQueryExpanderTest {

    @Resource
    MyMultiQueryExpander myMultiQueryExpander;

    @Test
    void expand() {
        List<Query> queryList = myMultiQueryExpander.expand("谁是马云啊啊啊啊,哈哈哈");

        assertNotNull(queryList);
    }
}