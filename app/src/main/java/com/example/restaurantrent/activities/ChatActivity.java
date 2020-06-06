package com.example.restaurantrent.activities;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantrent.Message;
import com.example.restaurantrent.R;
import com.example.restaurantrent.Server;
import com.example.restaurantrent.adapters.MessageAdapter;

import java.util.ArrayList;

// activity для чата по заказу
public class ChatActivity extends AppCompatActivity {

    private ListView messageList;
    private EditText messageEditText;
    // массив сообщений по конкретному заказу(заполняется в Server)
    public static ArrayList<Message> messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messageEditText = findViewById(R.id.messageField);
        messageList = findViewById(R.id.messagesListView);

        // создаём адаптер и присваеваем его messageList
        MessageAdapter messageAdapter = new MessageAdapter(this, messages);
        messageList.setAdapter(messageAdapter);

    }

    // метод отслеживающий нажатие на кнопку отправки сообщения
    public void sendMessageButton(View view) {
        String message = messageEditText.getText().toString();
        // проверяем не пустое ли сообщение
        if (!message.isEmpty()) {
            // отправляем сообщение
            Server.sendMessage(new Message(getIntent().getLongExtra("idRent", -1), false, message));
        }
    }

}
