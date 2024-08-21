package com.todosalau.chatapp.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginModel {
    private FirebaseAuth mAuth;

    public LoginModel() {
        mAuth = FirebaseAuth.getInstance();
    }

    public void loginUser(String email,String password, LoginCallback callback){
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        FirebaseUser user = mAuth.getCurrentUser();
                        callback.onSuccess(user);
                    }else{
                        callback.onFailure(task.getException());
                    }

                });
    }

    public interface LoginCallback {
        void onSuccess(FirebaseUser user);

        void onFailure(Exception e);
    }
}
