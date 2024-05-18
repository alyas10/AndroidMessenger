package com.example.androidmessenger.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidmessenger.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Chats.Chat;
import Chats.User;
import Chats.UserAdapter;
public class ChatsFragment extends Fragment {

    private RecyclerView recyclerView;

    private UserAdapter userAdapter;
    private List<User> mUsers;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    private List <String> userList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("Сообщения");

       View view = inflater.inflate(R.layout.fragment_chat, container, false);
       recyclerView = view.findViewById(R.id.recycler_view_chats);
       recyclerView.setHasFixedSize(true);
       recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

       firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Log.d("ChatsFragment", "onCreateView: начало метода");

       userList = new ArrayList<>();

       reference = FirebaseDatabase.getInstance().getReference("Chats");
        Log.d("ChatsFragment", "FirebaseDatabase инициализирована");
        reference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               Log.d("ChatsFragment", "onDataChange: метод вызван");
               userList.clear();

               for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                   Chat chat = snapshot.getValue(Chat.class);

                   assert chat != null;
                   if (chat.getSender().equals(firebaseUser.getUid())) {
                           if (!userList.contains(chat.getReceiver())) {
                               userList.add(chat.getReceiver());
                           }
                       }
                       if (chat.getReceiver().equals(firebaseUser.getUid())) {
                           if (!userList.contains(chat.getSender())) {
                               userList.add(chat.getSender());
                           }
                       }
                   }
               readChats();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Log.d("ChatsFragment", "onCancelled: метод вызван");

           }
       });
       return  view;

    }
    private void readChats () {
        Log.d("ChatsFragment", "Метод readChats() вызван");
        mUsers = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            mUsers.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if (user != null && userList.contains(user.getId())) {
                        // Проверяем, не добавлен ли уже этот пользователь в mUsers
                        boolean isAdded = false;
                        for (User userInList : mUsers) {
                            if (userInList.getId().equals(user.getId())) {
                                isAdded = true;
                                break;
                            }
                        }
                        if (!isAdded) {
                            mUsers.add(user);
                        }
                    }
                }

            userAdapter = new UserAdapter(getContext(),mUsers,true);
            recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}