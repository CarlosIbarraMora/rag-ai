package com.cetys.rag_ai.rag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class RagConfig {
    final Logger logger = LoggerFactory.getLogger(RagConfig.class);
    @Bean
    VectorStore vectorStore(EmbeddingModel embeddingModel) {
        return SimpleVectorStore.builder(embeddingModel).build();
    }

    @Bean
    @ConditionalOnProperty(
            name = "banco.rag.load-data-set-on-startup",
            havingValue = "true",
            matchIfMissing = true)
    ApplicationRunner createDatasetCampanias(
            //bean de vectorStore
            VectorStore vectorStore,
            //Configuración en resources con contexto de banco de sangre
            @Value("classpath:rag/campanias.md") Resource dataset){
        return args -> {
            logger.info("Loading data sets from {}",dataset);
            var documentos = new TextReader(dataset).get();
            var frag = new TokenTextSplitter()
                    .apply(documentos);

            //Agregar el MD tokenizado al vector store para el modelo rag
            vectorStore.add(frag);
        };

    }



}
