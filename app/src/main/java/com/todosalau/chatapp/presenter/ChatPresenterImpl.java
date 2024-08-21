package com.todosalau.chatapp.presenter;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.todosalau.chatapp.model.MessageModel;
import com.todosalau.chatapp.model.UserModel;
import com.todosalau.chatapp.view.ChatContract;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ChatPresenterImpl implements ChatPresenter{

    private ChatContract view;
    private UserModel user1;
    private UserModel user2;
    private String conversationId;

    public ChatPresenterImpl(ChatContract view) {
        this.view = view;
    }

    @Override
    public void loadConversations(UserModel user1, UserModel user2) {
        this.user1 = user1;
        this.user2 = user2;

        if (user1.getEmail().compareTo(user2.getEmail()) < 0) {
            conversationId = user1.getEmail() + "_" + user2.getEmail();
        } else {
            conversationId = user2.getEmail() + "_" + user1.getEmail();
        }

        FirebaseFirestore.getInstance().collection("conversations")
                .document(conversationId)
                .collection("messages")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener((queryDocumentsSnapshots,e)->{
                    if(e != null){
                        Log.e(TAG,"Error al cargar las conversaciones: ",e);
                        return;
                    }

                    List<MessageModel> conversationMessages = new ArrayList<>();
                    if(queryDocumentsSnapshots != null ){
                        for (QueryDocumentSnapshot document : queryDocumentsSnapshots){
                            MessageModel message = document.toObject(MessageModel.class);
                            conversationMessages.add(message);
                        }
                    }

                    if (view != null) {
                        view.showConversations(conversationMessages);
                    }
                });

    }

    @Override
    public void sendMessage(String messageText, UserModel user1, UserModel user2) {
        if(user1 != null && user2 != null){

            String conversationId = generateConversationId(user1.getEmail(),user2.getEmail());

            CollectionReference messagesRef = FirebaseFirestore.getInstance()
                    .collection("conversations")
                    .document(conversationId)
                    .collection("messages");

            MessageModel message = createMessageModel(user1,messageText);

            messagesRef.add(message)
                    .addOnSuccessListener(documentReference -> {

                        view.showMessageSentConfirmation();
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG,"Error sending message : ",e);
                    });

            updateUserWithMessageId(conversationId, message.getMessageId(), user1, user2);
            updateUserWithMessageId(conversationId, message.getMessageId(), user2, user1);
        }else {
            Log.e(TAG,"User1 or User2 is null");
        }
    }

    private String generateConversationId(String email1, String email2){
        return email1.compareTo(email2)  < 0 ? email1 + "_" + email2 : email2 + "_" + email1;
    }

    private MessageModel createMessageModel(UserModel user,String messageText){
        String messageId = UUID.randomUUID().toString();
        String senderId = user.getUserId();
        String senderName = user.getName();
        String senderMail = user.getEmail();
        Date timestamp = new Date();

        return new MessageModel(messageId,messageText,senderId,senderName,timestamp,senderMail);
    }

    private void updateUserWithMessageId(String conversationId,String messageId,UserModel user,UserModel anotherUser){
        FirebaseFirestore.getInstance()
                .collection("usuarios")
                .whereEqualTo("email",user.getEmail())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                        UserModel userFromFirestore = documentSnapshot.toObject(UserModel.class);
                        if(userFromFirestore != null ){
                            if(userFromFirestore.getUserId() != null){
                                updateUserData(userFromFirestore.getUserId(),conversationId,messageId);
                            }else{
                                Log.e(TAG,"userId is null for user: "+ userFromFirestore.getEmail());
                            }
                        }else{
                            Log.e(TAG,"User not found in usuarios collection");
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG,"Error getting user from usuarios colletions: "+e.getMessage());
                });
    }

    private void updateUserData(String userId,String conversationId, String messageId){
        FirebaseFirestore.getInstance().collection("usuarios").document(userId)
                .update("messagesId." + conversationId,messageId)
                .addOnSuccessListener(aVoid-> Log.d(TAG,"MessageId added to user"))
                .addOnFailureListener(e -> Log.e(TAG,"Error updating document: "+ e.getMessage()));
    }
}
