package com.yxc.yuaiagent.rag;

import jakarta.annotation.Resource;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName: LoveAppVectorStoreConfig
 * Package: com.yxc.yuaiagent.rag
 * Description:向量存储和转换
 *
 * @Author fishstar
 * @Create 2025/5/12 22:18
 * @Version 1.0
 */

@Configuration
public class LoveAppVectorStoreConfig {

    @Resource
    private LoveAppDocumentLoader loveAppDocumentLoader;

    @Bean
    public VectorStore loveAppVectorStore(EmbeddingModel dashscopeEmbeddingModel) {
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(dashscopeEmbeddingModel).build();

        simpleVectorStore.add(loveAppDocumentLoader.loadMarkdowns());

        return simpleVectorStore;
    }
}
