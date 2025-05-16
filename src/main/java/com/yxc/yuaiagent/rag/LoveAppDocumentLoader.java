package com.yxc.yuaiagent.rag;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: LoveAppDocumentLoader
 * Package: com.yxc.yuaiagent.rag
 * Description:
 *
 * @Author fishstar
 * @Create 2025/5/12 22:07
 * @Version 1.0
 */
@Component
public class LoveAppDocumentLoader {

    private final ResourcePatternResolver resourcePatternResolver;

    public LoveAppDocumentLoader(ResourcePatternResolver resourcePatternResolver) {
        this.resourcePatternResolver = resourcePatternResolver;
    }

    public List<Document> loadMarkdowns() {
        List<Document> documents = new ArrayList<>();

        try {
            Resource[] resources = resourcePatternResolver.getResources("classpath:document/*.md");

            for (Resource resource : resources) {
                String filename = resource.getFilename();

                String status = filename.substring(filename.length() - 6, filename.length() - 4);

                MarkdownDocumentReaderConfig config = MarkdownDocumentReaderConfig.builder()
                        .withHorizontalRuleCreateDocument(true)
                        .withIncludeCodeBlock(false)
                        .withIncludeBlockquote(false)
                        .withAdditionalMetadata("filename", filename)
                        .withAdditionalMetadata("status", status)
                        .build();

                MarkdownDocumentReader reader = new MarkdownDocumentReader(resource, config);

                documents.addAll(reader.get());
            }

            return documents;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
