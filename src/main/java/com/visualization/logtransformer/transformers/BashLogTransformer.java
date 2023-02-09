package com.visualization.logtransformer.transformers;

import com.google.cloud.firestore.Firestore;
import org.springframework.context.ApplicationContext;

public class BashLogTransformer {
    private String jsonFileName;
    private ApplicationContext context;
    private Firestore firestore;
}
