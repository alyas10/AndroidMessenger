package com.example.androidmessenger;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidmessenger.AddapterClass.SubAdapter;
import com.example.androidmessenger.databinding.FragmentHomeBinding;
import com.example.androidmessenger.databinding.FragmentSubBinding;
import com.example.androidmessenger.modelClass.HomeModel;
import com.example.androidmessenger.modelClass.QuizModel;
import com.example.androidmessenger.modelClass.SubModel;

import java.util.ArrayList;


public class SubFragment extends Fragment {
    FragmentSubBinding binding;
    ArrayList<SubModel> list = new ArrayList<>();
    SubAdapter adapter;
    String title;
    public SubFragment() {
        // Required empty public constructor
    }
    public SubFragment(String title) {
        this.title = title;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSubBinding.inflate(inflater, container, false);
        LoadData();
        return binding.getRoot();
    }

    private void LoadData() {
        binding.rcv.setLayoutManager(new LinearLayoutManager(getContext()));

        // Проверяем, есть ли уже категории в списке
        if (list.isEmpty()) {
            switch (title) {
                case "Тесты по шифрованию":
                    list.add(new SubModel("Тест по шифру Цезаря", "Тесты по шифрованию"));
                    list.add(new SubModel("Тест по шифру Атбаш", "Тесты по шифрованию"));
                    list.add(new SubModel("Тест по шифру Виженера", "Тесты по шифрованию"));
                    list.add(new SubModel("Тест по Афинному шифру", "Тесты по шифрованию"));
                    list.add(new SubModel("Тест по шифру Гаммирования", "Тесты по шифрованию"));
                    list.add(new SubModel("Тест по шифру RSA", "Тесты по шифрованию"));
                    break;
            }
        }

        adapter = new SubAdapter(getContext(), list);
        binding.rcv.setAdapter(adapter);
    }
}
