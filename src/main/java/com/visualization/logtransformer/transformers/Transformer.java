package com.visualization.logtransformer.transformers;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.visualization.logserver.entity.Content;
import com.visualization.logserver.entity.Milestone;
import com.visualization.logserver.entity.Student;
import com.visualization.logtransformer.entity.GeneralLog;
import com.visualization.logtransformer.service.FirebaseService;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Transformer implements TransformerInterface{

    private String jsonFileName;
    private ApplicationContext context;
    private Firestore firestore;

    public Transformer(String jsonFileName, ApplicationContext context) {
        setJsonFileName(jsonFileName);
        setContext(context);
        setFirestore();
    }

    public String getJsonFileName() {
        return jsonFileName;
    }

    public void setJsonFileName(String jsonFileName) {
        this.jsonFileName = jsonFileName;
    }

    public ApplicationContext getContext() {
        return context;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    public Firestore getFirestore() {
        return firestore;
    }

    public void setFirestore() {
        this.firestore = FirestoreClient.getFirestore(context.getBean(FirebaseService.class).getFirebaseApp());
    }

    public void setLog() throws IOException, ExecutionException, InterruptedException, ParseException {};

    public void addLogHelper(Student student, Milestone milestone, Content content, String source, Timestamp timestamp) throws ParseException {
        // Write data to Firestore
        GeneralLog log = new GeneralLog(student, timestamp, milestone, source, content);
        firestore.collection("logs").document().set(log);
    }

    public String getNamePairHelper(String id) throws ExecutionException, InterruptedException {
        Query query = firestore.collection("names").whereEqualTo("id", id);
        QuerySnapshot querySnapshot = query.get().get();

        if (querySnapshot.isEmpty()) {
            return null;
        }
        DocumentSnapshot document = querySnapshot.getDocuments().get(0);
        return document.getString("name");
    }

    public void setNamePairHelper(String id, String name) {
        // Create a map to represent the document
        Map<String, Object> document = new HashMap<>();
        document.put("id", id);
        document.put("name", name);

        // Add the document to the collection
        DocumentReference docRef = firestore.collection("names").document();
        ApiFuture<WriteResult> result = docRef.set(document);
    }

    public String[] getRandomNamesHelper(int num) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("https://names.drycodes.com/" + num + "?nameOptions=boy_names");
        HttpResponse response = httpClient.execute(request);
        String responseBody = EntityUtils.toString(response.getEntity()).replace("[", "").replace("]", "");
        responseBody = responseBody.substring(1, responseBody.length()-1);
        String[] names = responseBody.split("\",\"");
        return names;
    }

    public JsonObject getLogJsonHelper() {
        File file = new File("./" + getJsonFileName());
        try (FileReader reader = new FileReader(file)) {
            JsonObject obj = JsonParser.parseReader(reader).getAsJsonObject();
            return obj;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
