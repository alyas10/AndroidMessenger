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

/**
 * Фрагмент для отображения списка подкатегорий, относящихся к выбранной категории.
 * @author Иван Минаев
 * @version 2.0
 */
public class SubFragment extends Fragment {
    FragmentSubBinding binding;
    ArrayList<SubModel> list = new ArrayList<>();
    SubAdapter adapter;
    String title;
    /**
     * Обязательный пустой конструктор фрагмента.
     */
    public SubFragment() {
    }
    /**
     * Конструктор фрагмента, принимающий название категории.
     *
     * @param title Название категории, подкатегории которой будут отображаться.
     */
    public SubFragment(String title) {
        this.title = title;
    }
  /**
     * Создает и настраивает представление фрагмента.
     *
     * @param inflater Инфлейтер для создания представления из XML-файла макета.
     * @param container Родительский контейнер представления, к которому будет прикреплено представление фрагмента.
     * @param savedInstanceState Сохраненное состояние фрагмента (если есть).
     * @return Сформированное представление фрагмента.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSubBinding.inflate(inflater, container, false);
        LoadData();
        return binding.getRoot();
    }
/**
     * Загружает данные в RecyclerView в зависимости от выбранной категории тестов.
     */
    private void LoadData() {
        binding.rcv.setLayoutManager(new LinearLayoutManager(getContext()));

        // Проверяем, есть ли уже категории в списке
        if (list.isEmpty()) {
             // Заполнение списка элементов в зависимости от заголовка (категории)
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
       //Создание адаптера
        adapter = new SubAdapter(getContext(), list);
        binding.rcv.setAdapter(adapter);
    }
}
