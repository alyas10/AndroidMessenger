package com.example.androidmessenger.Fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidmessenger.DataClass;
import com.example.androidmessenger.MyAdapter;
import com.example.androidmessenger.R;

import java.util.ArrayList;
import java.util.List;
/**
 * Фрагмент, который отображает карточки шифров с их описаниями и позволяет выполнять поиск.
 * @author Иван Минаев
 * @version 1.0
 */
public class TheoryFragment extends Fragment {
    RecyclerView recyclerView;
    List<DataClass> dataList;
    MyAdapter adapter;
    SearchView searchView;

    /**
     * Конструктор для фрагмента. Задает макет для фрагмента.
     */
    public TheoryFragment() {
        super(R.layout.activity_theory);
    }

    /**
     * Вызывается сразу после возврата функции onCreateView(), где для фрагмента устанавливается окончательная иерархия представлений.
     * Этот метод инициализирует представления, настраивает RecyclerView, заполняет список данных и настраивает функциональность поиска.
     *
     * @param view представления, возвращаемого функцией onCreateView().
     * @param savedInstanceState Если значение не равно null, этот фрагмент создается заново из предыдущего сохраненного состояния.
     */
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        searchView = view.findViewById(R.id.search);
        searchView.clearFocus();

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("Теория");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        dataList = new ArrayList<>();

// Создание первой карточки с 1 звездочкой
        DataClass androidData = new DataClass("Основны безопасности и анонимности в сети", R.drawable.cezar, 1);
        dataList.add(androidData);

// Создание второй карточки с 2 звездочками
        androidData = new DataClass("Киберугрозы в современном мире", R.drawable.atbash, 2);
        dataList.add(androidData);

// Создание третьей карточки с 3 звездочками
        androidData = new DataClass("Способы хранения информации", R.drawable.vigener, 3);
        dataList.add(androidData);

// Создание четвертой карточки с 4 звездочками
        androidData = new DataClass("Криптография", R.drawable.afin, 4);
        dataList.add(androidData);

// Создание пятой карточки с 5 звездочками
        androidData = new DataClass("Блок Который будет1", R.drawable.gamma, 5);
        dataList.add(androidData);

// Создание шестой карточки с 6 звездочками
        androidData = new DataClass("Блок Который будет2", R.drawable.rsa1, 6);
        dataList.add(androidData);

        adapter = new MyAdapter(getActivity(), dataList);
        recyclerView.setAdapter(adapter);

    }
    /**
     * Фильтрует список шифров на основе введенного текста для поиска.
     *
     * @param text - текст для поиска, введенный пользователем.
     */
    private void searchList(String text){
        List<DataClass> dataSearchList = new ArrayList<>();
        for (DataClass data : dataList){
            if (data.getDataTitle().toLowerCase().contains(text.toLowerCase())) {
                dataSearchList.add(data);
            }
        }
        if (dataSearchList.isEmpty()){
            Toast.makeText(getActivity(), "Не найдено", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setSearchList(dataSearchList);
        }
    }
}
