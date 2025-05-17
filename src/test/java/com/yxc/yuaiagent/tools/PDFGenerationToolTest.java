package com.yxc.yuaiagent.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PDFGenerationToolTest {

    @Test
    public void testGeneratePDF() {
        PDFGenerationTool tool = new PDFGenerationTool();
        String fileName = "fishstar.pdf";
        String content = "fishstar";
        String result = tool.generatePDF(fileName, content);
        assertNotNull(result);
    }
}
