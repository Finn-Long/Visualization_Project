package com.visualization.logtransformer.transformers;

import com.google.cloud.firestore.Firestore;
import com.google.gson.JsonObject;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

public class BashLogTransformer extends Transformer{

    public BashLogTransformer(String jsonFileName, ApplicationContext context) {
        super(jsonFileName, context);
    }

    public void setLog() throws IOException {
        JsonObject jsonRaw = getLogJsonHelper();
        System.out.println(jsonRaw.toString());
    }
}
