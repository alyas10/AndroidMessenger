package com.example.androidmessenger.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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

       View view = inflater.inflate(R.layout.fragment_chat, container, false);
       recyclerView = view.findViewById(R.id.recycler_view_chats);
       recyclerView.setHasFixedSize(true);
       recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

       firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

       userList = new ArrayList<>();

       reference = FirebaseDatabase.getInstance().getReference("Chats");
       reference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               userList.clear();

               for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                   Chat chat = snapshot.getValue(Chat.class);

                   if (chat != null) {
                       if (chat.getSender() != null && chat.getSender().equals(firebaseUser.getUid())) {
                           if (chat.getReceiver() != null) {
                               userList.add(chat.getReceiver());
                           }
                       }
                       if (chat.getReceiver() != null && chat.getReceiver().equals(firebaseUser.getUid())) {
                           if (chat.getSender() != null) {
                               userList.add(chat.getSender());
                           }
                       }
                   }
               }
               readChats();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
       return  view;

    }
    private void readChats () {
        mUsers = new ArrayList<>();

        reference.getDatabase().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            mUsers.clear();

            for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                User user = snapshot.getValue(User.class);

                for(String id : userList) {
                    if(user.getId().equals(id)) {
                        if(mUsers.size() != 0) {
                            for (User user1 : mUsers) {
                                if(!user.getId().equals(user1.getId())) {
                                    mUsers.add(user);
                                }
                            }
                        } else {
                            mUsers.add(user);
                        }
                    }
                }
              }
            userAdapter = new UserAdapter(getContext(),mUsers);
            recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}