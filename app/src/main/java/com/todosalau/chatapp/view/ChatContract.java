package com.todosalau.chatapp.view;

import com.todosalau.chatapp.model.MessageModel;

import java.util.List;

public interface ChatContract {

    void showConversations(List<MessageModel> conversations);

    void showMessageSentConfirmation();
}
