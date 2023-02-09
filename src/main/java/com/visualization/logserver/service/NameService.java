package com.visualization.logserver.service;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.visualization.logserver.entity.Log;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class NameService {
    private static final String COLLECTION_NAME = "names";

    public List<String> getNames() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        final List<String> result = new ArrayList<>();
        try {
            ApiFuture<QuerySnapshot> querySnapshot = dbFirestore.collection(COLLECTION_NAME).get();
            QuerySnapshot queryResult = querySnapshot.get();
            for (QueryDocumentSnapshot document : queryResult) {
                result.add(document.getString("name"));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String deleteAllNamePairs() {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        try {
            ApiFuture<QuerySnapshot> querySnapshot = dbFirestore.collection(COLLECTION_NAME).get();
            QuerySnapshot queryResult = querySnapshot.get();
            List<ApiFuture<WriteResult>> futures = new ArrayList<>();
            for (QueryDocumentSnapshot document : queryResult) {
                futures.add(document.getReference().delete());
            }
            ApiFutures.allAsList(futures).get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e);
        }
        return "Deleted all id-name pairs in database!";
    }
}
