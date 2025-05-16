package com.yxc.yuaiagent.rag;

import jakarta.annotation.Resource;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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

   /* @Resource
    private VectorStore pgVectorStore;*/

    @Resource
    private MyTokenTextSplitter myTokenTextSplitter;

    @Resource
    private MyKeywordEnricher myKeywordEnricher;

    @Bean
    public VectorStore loveAppVectorStore(EmbeddingModel dashscopeEmbeddingModel) {
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(dashscopeEmbeddingModel).build();
        //文档抽取
        List<Document> documents = loveAppDocumentLoader.loadMarkdowns();
        //文档转换
//        List<Document> splitDocuments = myTokenTextSplitter.splitDocuments(documents);

        List<Document> enrichedDocuments = myKeywordEnricher.enrichDocuments(documents);
        //文档加载
        simpleVectorStore.add(enrichedDocuments);

        return simpleVectorStore;
    }
}
