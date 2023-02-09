package com.visualization.logtransformer;

import com.visualization.logtransformer.transformers.CyverseLogTransformer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class LogTransformerApplication {

    private static ApplicationContext context;

    public static void main (String []args) throws ParseException, IOException, ExecutionException, InterruptedException {
        context = SpringApplication.run(LogTransformerApplication.class, args);
        CyverseLogTransformer cyverseLogTransformer = new CyverseLogTransformer("plugin_logs.json", context);
        cyverseLogTransformer.setLog();
    }
}
