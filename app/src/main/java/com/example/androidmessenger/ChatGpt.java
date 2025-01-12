package com.example.androidmessenger;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ChatGpt extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView welcome_txt;
    EditText message;
    ImageButton send_btn;
    List<Messages> messageList;
    messageAdapter messageAdapter;

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_gpt);
        messageList = new ArrayList<>();
       recyclerView = findViewById(R.id.recycler_view);
       welcome_txt = findViewById(R.id.welcome_text);
       message = findViewById(R.id.message_edit_text);
       send_btn = findViewById(R.id.send_button);

       messageAdapter = new messageAdapter(messageList);
       recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        /*Этот код настраивает LinearLayoutManager таким образом, что новые элементы будут добавляться в конец списка.
Когда setStackFromEnd(true) установлено, последний добавленный элемент будет отображаться вверху списка, а более ранние элементы будут расположены ниже.*/
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
       send_btn.setOnClickListener(v -> {
           //Удаление пробелов в начале и в конце строки
           String question = message.getText().toString().trim();
           addToChat(question,Messages.SENT_BY_ME);
           message.setText("");
           callAPI(question);
           welcome_txt.setVisibility(RecyclerView.GONE);
       });
    }

    void addResponse(String response) {
        messageList.remove(messageList.size()-1);
        addToChat(response,Messages.SENT_BY_BOT);
    }
    void addToChat (String message, String sentBy) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new Messages(message,sentBy));
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });
    }

    void callAPI(String question) {
        messageList.add(new Messages("Печатаю...", Messages.SENT_BY_BOT));
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model", "gpt-3.5-turbo");
            JSONArray messages = new JSONArray();
            JSONObject systemMessage = new JSONObject();
            systemMessage.put("role", "system");
            systemMessage.put("content", "You are a helpful assistant.");
            messages.put(systemMessage);
            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", question);
            messages.put(userMessage);
            jsonBody.put("messages", messages);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(jsonBody.toString(),JSON);
        Request request = new Request.Builder()
                .url("https://api.proxyapi.ru/openai/v1/chat/completions")
                .header("Authorization", "Bearer sk-6fb7Cdrd2K0a4JFOTkMJuvhOPy6oX3weeeee")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            addResponse("Ошибка при загрузке ответа" + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
             if(response.isSuccessful()) {
                 try {
                     JSONObject jsonObject = new JSONObject(response.body().string());
                     JSONArray jsonArray  = jsonObject.getJSONArray("choices");
                     String result = jsonArray.getJSONObject(0).getJSONObject("message").getString("content");
                     addResponse(result.trim());
                 } catch (JSONException e) {
                     throw new RuntimeException(e);
                 }


             }
             else {
                 addResponse("Ошибка при загрузке ответа" + response.body().toString());
             }
            }
        });
    }
}
