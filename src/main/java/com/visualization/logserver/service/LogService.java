package com.visualization.logserver.service;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.visualization.logserver.entity.Log;
import com.visualization.logtransformer.entity.GeneralLog;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class LogService {
    private static final String COLLECTION_NAME = "logs";

//    public String saveLog(GeneralLog log) throws ExecutionException, InterruptedException {
//        Firestore dbFirestore = FirestoreClient.getFirestore();
//        ApiFuture<WriteResult> collectionApiFuture =
//                dbFirestore.collection(COLLECTION_NAME).document().set(log);
//        return collectionApiFuture.get().getUpdateTime().toString();
//    }

    public List<Log> getLogs() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        final List<Log> result = new ArrayList<>();
        try {
            ApiFuture<QuerySnapshot> querySnapshot = dbFirestore.collection(COLLECTION_NAME).orderBy("timestamp").get();
            QuerySnapshot queryResult = querySnapshot.get();
            for (QueryDocumentSnapshot document : queryResult) {
                result.add(document.toObject(Log.class));
            }
        } catch (InterruptedException | ExecutionException e) {
            // Handle error
        }
        return result;

//        Firestore dbFirestore = FirestoreClient.getFirestore();
//        Iterable<DocumentReference> documentReference = dbFirestore.collection(COLLECTION_NAME).listDocuments();
//        Iterator<DocumentReference> iterator = documentReference.iterator();
//        Log log = null;
//        List<Log> logList = new ArrayList<>();
//        while(iterator.hasNext()) {
//            DocumentReference documentReference1 = iterator.next();
//            ApiFuture<DocumentSnapshot> future = documentReference1.get();
//            DocumentSnapshot document = future.get();
//            log = document.toObject(Log.class);
//            logList.add(log);
//        }
//        return logList;
    }

    public List<Log> getByStudentName(String name) throws ExecutionException, InterruptedException {
        final List<Log> result = new ArrayList<>();
        Firestore dbFirestore = FirestoreClient.getFirestore();
        try {
            ApiFuture<QuerySnapshot> querySnapshot = dbFirestore.collection(COLLECTION_NAME).whereEqualTo("student.name", name).orderBy("timestamp").get();
            QuerySnapshot queryResult = querySnapshot.get();
            for (QueryDocumentSnapshot document : queryResult) {
                result.add(document.toObject(Log.class));
            }
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e);
        }
        return result;
    }

    public List<Log> getByStudentId(String id) throws ExecutionException, InterruptedException {
        final List<Log> result = new ArrayList<>();
        Firestore dbFirestore = FirestoreClient.getFirestore();
        try {
            ApiFuture<QuerySnapshot> querySnapshot = dbFirestore.collection(COLLECTION_NAME).whereEqualTo("student.id", id).orderBy("timestamp").get();
            QuerySnapshot queryResult = querySnapshot.get();
            for (QueryDocumentSnapshot document : queryResult) {
                result.add(document.toObject(Log.class));
            }
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e);
        }
        return result;
    }

    public List<Log> getAllMilestone() {
        final List<Log> result = new ArrayList<>();
        Firestore dbFirestore = FirestoreClient.getFirestore();
        try {
            ApiFuture<QuerySnapshot> querySnapshot = dbFirestore.collection(COLLECTION_NAME).whereEqualTo("milestone.milestone", true).orderBy("timestamp").get();
            QuerySnapshot queryResult = querySnapshot.get();
            for (QueryDocumentSnapshot document : queryResult) {
                result.add(document.toObject(Log.class));
            }
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e);
        }
        return result;
    }

    public List<Log> getAllMilestoneByName(String name) {
        final List<Log> result = new ArrayList<>();
        Firestore dbFirestore = FirestoreClient.getFirestore();
        try {
            ApiFuture<QuerySnapshot> querySnapshot = dbFirestore.collection(COLLECTION_NAME).whereEqualTo("student.name", name).whereEqualTo("milestone.milestone", true).orderBy("timestamp").get();
            QuerySnapshot queryResult = querySnapshot.get();
            for (QueryDocumentSnapshot document : queryResult) {
                result.add(document.toObject(Log.class));
            }
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e);
        }
        return result;
    }

    public String deleteAllLogs() {
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
        return "Deleted all logs in database!";
    }
}
