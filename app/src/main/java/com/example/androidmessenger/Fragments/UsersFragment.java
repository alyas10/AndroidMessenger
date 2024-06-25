package com.example.androidmessenger.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Chats.User;
import Chats.UserAdapter;
/**
 * Фрагмент для отображения списка пользователей.
 * Позволяет осуществлять поиск пользователей по имени пользователя.
 - Этот фрагмент отвечает за отображение списка пользователей в RecyclerView.
 - - Он использует "UserAdapter" для управления данными в RecyclerView.
 - Это позволяет выполнять поиск пользователей по имени пользователя с помощью
 "TextWatcher" в редактируемом тексте "search_users".
 * @author Алевтина Ильина
 * @version 1.0
 */
public class UsersFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    private List<User> mUsers;
    EditText search_users;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        // Заголовок для  ActionBar
        actionBar.setTitle("Пользователи");

        View view = inflater.inflate(R.layout.fragment_users, container, false);
        // Инициализация RecyclerView
        recyclerView = view.findViewById(R.id.recycle_view_users);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mUsers = new ArrayList<>();
        // Создание и задание  UserAdapter
        userAdapter = new UserAdapter(getContext(),mUsers,false);
        recyclerView.setAdapter(userAdapter);

        // Чтение пользователей из Firebase Database
        readUsers();
        search_users = view.findViewById(R.id.search_users);
        search_users.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchUsers(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }
    /**
     * Поиск пользователей на основе заданного поискового запроса.
     - Запрашивает в базе данных Firebase пользователей, чьи имена начинаются с поискового запроса.
     - Очищает существующий список пользователей и добавляет соответствующих пользователей в список "mUsers".
     - Обновляет адаптер RecyclerView для отображения нового списка пользователей.
     * @param s - поисковый запрос.
     */
    private void searchUsers(String s) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("search")
                .startAt(s)
                .endAt(s+"\uf00ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    assert user != null;
                    assert firebaseUser != null;
                    if(!user.getId().equals(firebaseUser.getUid())){
                        mUsers.add(user);
                    }
                }
                userAdapter =  new UserAdapter(getContext(),mUsers,false);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * Чтение всех пользователей из Firebase Database.
     - - Считывает всех пользователей из базы данных Firebase и добавляет их в список "mUsers".
     - Обновляет адаптер RecyclerView для отображения нового списка пользователей.
     */
    private void readUsers() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (search_users.getText().toString().equals("")) {
                    mUsers.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User user = snapshot.getValue(User.class);

                        assert user != null;
                        assert firebaseUser != null;
                        if (!user.getId().equals(firebaseUser.getUid())) {
                            mUsers.add(user);
                        }
                    }

                    userAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}