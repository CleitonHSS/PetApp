package br.com.ipet.model.repository;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class BaseRepository {

    private static FirebaseFirestore database;

    protected static FirebaseFirestore getDatabase() {
        if(database == null){
            database = FirebaseFirestore.getInstance();
            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                    .setTimestampsInSnapshotsEnabled(true)
                    .build();
            database.setFirestoreSettings(settings);
        }
        return database;
    }
}