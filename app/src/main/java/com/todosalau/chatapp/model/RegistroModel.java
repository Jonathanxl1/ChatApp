package com.todosalau.chatapp.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class RegistroModel {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private DatabaseReference database;

    public RegistroModel() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
    }

    public void registerUser(String email,String password, RegistroCallback callback){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        FirebaseUser user  = mAuth.getCurrentUser();
                        if (user != null){
                            callback.onSuccess(user);
                        }
                    }else {
                        callback.onFailure(task.getException());
                    }
                });

    }

    public void storeUserData(FirebaseUser user, Map<String,Object> userData,RegistroCallback callback){
        db.collection("usuarios")
                .document(user.getUid())
                .set(userData)
                .addOnSuccessListener(aVoid ->{ callback.onSuccess(null); })
                .addOnFailureListener(callback::onFailure);
    }

   public void guardarUsuario(UserModel user){
        DatabaseReference usersRefs = database.child("users");
        usersRefs.child(user.getUserId()).setValue(user)
                .addOnSuccessListener(aVoid->{})
                .addOnFailureListener(e -> {});

   }

   public interface RegistroCallback {
        void onSuccess(Object result);
        void onFailure(Exception e);
   }
}
