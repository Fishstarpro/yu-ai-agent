package com.yxc.yuaiagent.tools;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ClassName: FileOperationToolTest
 * Package: com.yxc.yuaiagent.tools
 * Description:
 *
 * @Author fishstar
 * @Create 2025/5/17 1:03
 * @Version 1.0
 */
public class FileOperationToolTest {

    @Test
    public void testReadFile() {
        FileOperationTool tool = new FileOperationTool();
        String fileName = "恋爱.txt";
        String result = tool.readFile(fileName);
        assertNotNull(result);
    }

    @Test
    public void testWriteFile() {
        FileOperationTool tool = new FileOperationTool();
        String fileName = "恋爱.txt";
        String content = "fishstar love bb";
        String result = tool.writeFile(fileName, content);
        assertNotNull(result);
    }
}
