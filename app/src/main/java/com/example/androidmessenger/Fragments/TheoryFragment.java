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
    DataClass androidData = new DataClass("Афинный шифр", R.string.afin, "Уровень 1", R.drawable.afin);
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
    androidData = new DataClass("Шифр Цезаря", R.string.cezar, "Уровень 1", R.drawable.cezar);
    dataList.add(androidData);
    androidData = new DataClass("Шифр Атбаш", R.string.atbash, "Уровень 1", R.drawable.atbash);
    dataList.add(androidData);
    androidData = new DataClass("Шифр Виженера", R.string.vigener, "Уровень 1", R.drawable.vigener);
    dataList.add(androidData);
    androidData = new DataClass("Афинный шифр", R.string.afin, "Уровень 1", R.drawable.afin);
    dataList.add(androidData);
    androidData = new DataClass("Шифр XOR", R.string.gamma, "Уровень 2", R.drawable.gamma);
    dataList.add(androidData);
    androidData = new DataClass("Шифр RSA", R.string.rsa, "Уровень 3", R.drawable.rsa1);
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
