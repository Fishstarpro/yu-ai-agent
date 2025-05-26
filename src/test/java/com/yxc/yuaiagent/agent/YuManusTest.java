package com.yxc.yuaiagent.agent;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * ClassName: YuManusTest
 * Package: com.yxc.yuaiagent.agent
 *
 * @Author fishstar
 * @Create 2025/5/24 18:41
 * @Version 1.0
 * Description:
 */
@SpringBootTest
class YuManusTest {

    @Resource
    private YuManus yuManus;

    @Test
    void run() {
        String userPrompt = """  
                我的另一半居住在上海静安区，请帮我找到 5 公里内合适的约会地点，  
                并结合一些网络图片，制定一份简单的约会计划即可,，  
                并以 PDF 格式输出,,步骤不要太繁琐,快速生成""";
        String answer = yuManus.run(userPrompt);
        Assertions.assertNotNull(answer);
    }
}
