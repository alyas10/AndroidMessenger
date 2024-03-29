package com.example.androidmessenger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
public class TheoryFragment extends Fragment {
    RecyclerView recyclerView;
    List<DataClass> dataList;
    MyAdapter adapter;
    DataClass androidData;
    SearchView searchView;

    public TheoryFragment() {
        super(R.layout.activity_theory);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        searchView = view.findViewById(R.id.search);
        searchView.clearFocus();
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
    androidData = new DataClass("Шифр Цезаря", R.string.cezar, "Уровень 1", R.drawable.ciphercezhar);
    dataList.add(androidData);
    androidData = new DataClass("Шифр Атбаш", R.string.atbash, "Уровень 1", R.drawable.cipher_atbash);
    dataList.add(androidData);
    androidData = new DataClass("Шифр Виженера", R.string.vigener, "Уровень 1", R.drawable.cipher_2);
    dataList.add(androidData);
    adapter = new MyAdapter(getActivity(), dataList);
    recyclerView.setAdapter(adapter);

    }
    private void searchList(String text){
        List<DataClass> dataSearchList = new ArrayList<>();
        for (DataClass data : dataList){
            if (data.getDataTitle().toLowerCase().contains(text.toLowerCase())) {
                dataSearchList.add(data);
            }
        }
        if (dataSearchList.isEmpty()){
            Toast.makeText(getActivity(), "Not Found", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setSearchList(dataSearchList);
        }
    }
}
