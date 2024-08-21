package com.todosalau.chatapp.model;

import java.util.Date;

public class MessageModel {
    private String messageId,messageText,senderId,senderName,senderEmail;
    private Date timestamp;

    public MessageModel() {
    }

    public MessageModel(String messageId, String messageText, String senderId, String senderName, Date timestamp) {
        this.messageId = messageId;
        this.messageText = messageText;
        this.senderId = senderId;
        this.senderName = senderName;
        this.timestamp = timestamp;
    }

    public MessageModel(String messageId, String messageText, String senderId, String senderName, Date timestamp, String senderEmail) {
        this.messageId = messageId;
        this.messageText = messageText;
        this.senderId = senderId;
        this.senderName = senderName;
        this.timestamp = timestamp;
        this.senderEmail = senderEmail;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }


}
