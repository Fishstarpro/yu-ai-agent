package com.yxc.yuimagesearchmcp.tools;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ClassName: ImageSearchToolTest
 * Package: com.yxc.yuimagesearchmcp.tools
 * Description:
 *
 * @Author fishstar
 * @Create 2025/5/19 14:46
 * @Version 1.0
 */
@SpringBootTest
class ImageSearchToolTest {

    @Resource
    private ImageSearchTool imageSearchTool;

    @Test
    void searchImage() {
        String result = imageSearchTool.searchImage("cat");
        Assertions.assertNotNull(result);
    }
}
