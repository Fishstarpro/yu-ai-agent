package com.yxc.yuaiagent.demo.invoke;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * ClassName: TestApiKey
 * Package: com.yxc.yuaiagent.demo.invoke
 * Description:
 *
 * @Author fishstar
 * @Create 2025/5/7 15:24
 * @Version 1.0
 */
@Component
public class TestApiKey {

    @Value("${spring.ai.dashscope.api-key}")
    public String apiKey;
}
