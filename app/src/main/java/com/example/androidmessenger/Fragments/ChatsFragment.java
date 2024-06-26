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

import Chats.Chatlist;
import Chats.User;
import Chats.UserAdapter;
/**
 * Фрагмент для отображения списка чатов пользователя.
 * @author Алевтина Ильина
 * @version 1.0
 */
public class ChatsFragment extends Fragment {

    // RecyclerView для отображения списка чатов
    private RecyclerView recyclerView;
    // Адаптер для управления данными списка чатов
    private UserAdapter userAdapter;
    //Список для отображения пользователей
    private List<User> mUsers;

    FirebaseUser firebaseUser;
    DatabaseReference reference;
    //Список для хранения чатов
    private List<Chatlist> userList;

    /**
     * Вызывается для создания фрагменто.
     * @param inflater объект LayoutInflater, который можно использовать для расширения любых видов во фрагменте.
     * @param container, если значение не равно null, это родительское представление, к которому должен быть подключен пользовательский интерфейс фрагмента.
     * @param savedInstanceState, если значение не равно null, этот фрагмент создается заново из предыдущего сохраненного состояния.
     * @return представление пользовательского интерфейса фрагмента или значение null.
     */
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

        reference = FirebaseDatabase.getInstance().getReference("Chatlist").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chatlist chatlist = snapshot.getValue(Chatlist.class);
                    userList.add(chatlist);
                }

                chatList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

    /**
     * Этот метод извлекает и фильтрует список пользователей из узла "Users" в базе данных Firebase
     * Затем он настраивает пользовательский адаптер для RecyclerView.
     */
    private void chatList() {
        mUsers = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    for (Chatlist chatlist : userList){
                        if (user.getId().equals(chatlist.getId())){
                            mUsers.add(user);
                        }
                    }
                }
                userAdapter = new UserAdapter(getContext(), mUsers, true);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}