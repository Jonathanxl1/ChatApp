package com.todosalau.chatapp.presenter;

import com.google.firebase.auth.FirebaseUser;
import com.todosalau.chatapp.model.RegistroModel;
import com.todosalau.chatapp.view.RegistroContract;

import java.util.HashMap;
import java.util.Map;

public class RegistroPresenter {
    private RegistroContract.View view;
    private RegistroModel model;

    public RegistroPresenter(RegistroContract.View view, RegistroModel mode) {
        this.view = view;
        this.model = mode;
    }

    public void registerUser(){
        String name = view.getName();
        String email = view.getEmail();
        String password = view.getPassword();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()){
            view.showToast("Por favor completar los datos ");
            return;
        }

        if(password.length() < 6){
            view.showToast("La contraseña no debe ser menos a 6 caracteres");
            return;
        }

        model.registerUser(email, password, new RegistroModel.RegistroCallback() {
            @Override
            public void onSuccess(Object result) {
                FirebaseUser user = (FirebaseUser) result;
                Map<String,Object> userData = new HashMap<>();
                userData.put("name",name);
                userData.put("email",email);

                model.storeUserData(user, userData, new RegistroModel.RegistroCallback() {
                    @Override
                    public void onSuccess(Object result) {
                        view.showToast("Registros exitoso");
                        view.clearInputFields();
                        view.navigateToLogin();
                    }

                    @Override
                    public void onFailure(Exception e) {
                        view.showToast("Error de registro en Firestore");
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                view.showToast("Error al registro el usuario");
            }
        });
    }
}
