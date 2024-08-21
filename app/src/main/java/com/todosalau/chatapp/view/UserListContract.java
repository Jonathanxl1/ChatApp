package com.todosalau.chatapp.view;

import com.todosalau.chatapp.model.UserModel;

import java.util.List;

public interface UserListContract {
    void displayUsers(List<UserModel> users);
    void showError(String message);
}
