package com.todosalau.chatapp.view;

public interface RegistroContract {

    interface View {
        void showToast(String message);
        void clearInputFields();
        void navigateToLogin();
        String getName();
        String getEmail();
        String getPassword();
    }
}
