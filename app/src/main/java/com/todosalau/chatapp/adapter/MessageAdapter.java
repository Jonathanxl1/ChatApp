package com.todosalau.chatapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.todosalau.chatapp.R;
import com.todosalau.chatapp.model.MessageModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MessageAdapter extends ArrayAdapter<MessageModel> {
    private Context mContext;
    private List<MessageModel> mMessages;

    public MessageAdapter(Context context, List<MessageModel> messages) {
        super(context,0,messages);
        mContext = context;
        mMessages = messages;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if (listItem == null) {
            // Si no hay una vista reutilizable, inflarla desde el layout XML
            listItem = LayoutInflater.from(mContext).inflate(R.layout.item_message, parent, false);
        }

        // Obtener el mensaje actual en la posición especificada
        MessageModel currentMessage = mMessages.get(position);

        // Obtener referencias a los TextView en el layout del mensaje
        TextView senderNameTextView = listItem.findViewById(R.id.senderNameTextView);
        TextView messageTextView = listItem.findViewById(R.id.messageTextView);
        TextView timestampTextView = listItem.findViewById(R.id.timestampTextView);

        senderNameTextView.setText(currentMessage.getSenderEmail());
        messageTextView.setText(currentMessage.getMessageText());

        String formattedTimestamp = formatTimestamp(currentMessage.getTimestamp());
        timestampTextView.setText(formattedTimestamp);

        return listItem;
    }

    private String formatTimestamp(Date timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        return dateFormat.format(timestamp);
    }
}
