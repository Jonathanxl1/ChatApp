package com.todosalau.chatapp.presenter;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.todosalau.chatapp.model.UserModel;
import com.todosalau.chatapp.view.UserListContract;

import java.util.ArrayList;
import java.util.List;

public class UserListPresenterImpl implements UserListPresenter{

    private UserListContract view;
    private FirebaseFirestore db;

    public UserListPresenterImpl(UserListContract view ) {
        this.view = view;
        this.db = FirebaseFirestore.getInstance();
    }

    @Override
    public void loadUsers() {
        db.collection("usuarios")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<UserModel>  userList = new ArrayList<>();

                    for(DocumentSnapshot document: queryDocumentSnapshots.getDocuments()){
                        UserModel user =  document.toObject(UserModel.class);
                        userList.add(user);
                    }

                    view.displayUsers(userList);
                })
                .addOnFailureListener(e ->{
                   view.showError("Error al cargar los usuarios: "+ e.getMessage());
                });
    }
}
