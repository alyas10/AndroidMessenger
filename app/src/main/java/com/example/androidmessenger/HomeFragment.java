package com.example.androidmessenger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidmessenger.AddapterClass.HomeAdapter;
import com.example.androidmessenger.databinding.FragmentHomeBinding;
import com.example.androidmessenger.modelClass.HomeModel;

import java.util.ArrayList;

/**
 * Фрагмент для вкладки с тестированием.
 * @author Иван Минаев
 * @version 1.0
 */
public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    ArrayList<HomeModel> list = new ArrayList<>();
    HomeAdapter adapter;

    /**
     * Конструктор без параметров.
     */
    public HomeFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("Проверка знаний");
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        LoadData();
        return binding.getRoot();
    }

    /**
     * Загружает данные в RecyclerView.
     * В данном случае добавляет один элемент "Тесты по шифрованию".
     */
    private void LoadData() {
        binding.rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        list.clear();
        list.add(new HomeModel("Тесты по блоку Основны безопасности и анонимности в сети"));
        list.add(new HomeModel("Тесты по блоку Киберугрозы в современном мире"));
        list.add(new HomeModel("Тесты по блоку Способы хранения информации"));
        list.add(new HomeModel("Тесты по блоку Криптография"));
        list.add(new HomeModel("Тесты по блоку Который будет1"));
        list.add(new HomeModel("Тесты по блоку Который будет2"));
        adapter = new HomeAdapter(getContext(), list);
        binding.rcv.setAdapter(adapter);

    }
}
