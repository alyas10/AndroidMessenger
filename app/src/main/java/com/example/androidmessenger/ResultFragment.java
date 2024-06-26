package com.example.androidmessenger;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.androidmessenger.databinding.FragmentResultBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import android.content.SharedPreferences;

public class ResultFragment extends Fragment {
    FragmentResultBinding binding;
    int right, wrong, AllQuestion, cezar, atbash, vigener, afin,gamma,rsa;
    PieChart pieChart;
    private SharedPreferences preferences;

    String category, title;
    public ResultFragment(int right, int allQuestion, String category, String title) {
    }
    public ResultFragment(){
    };
    public ResultFragment(int right, int wrong, int AllQuestion, int cezar, int atbash, int vigener, int afin, int gamma,int rsa, String category, String title) {
        this.AllQuestion = AllQuestion;
        this.right = right;
        this.category = category;
        this.title = title;
        this.wrong = wrong;
        this.cezar = cezar;
        this.atbash = atbash;
        this.vigener = vigener;
        this.afin = afin;
        this.gamma = gamma;
        this.rsa = rsa;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentResultBinding.inflate(inflater, container, false);
        String rightScore = String.valueOf(right);
        String wrongScore = String.valueOf(AllQuestion - right);
        String totalScore = String.valueOf(AllQuestion);

        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

        binding.correct.setText(rightScore + " Верных");
        binding.incorrect.setText(wrongScore + " Неверных");
        binding.score.setText("Верных ответов " + rightScore + " из " + totalScore);

        pieChart = binding.pieChart;


        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(right));
        entries.add(new PieEntry(AllQuestion - right));

        PieDataSet dataSet = new PieDataSet(entries, "Результаты теста");
        dataSet.setColors(new int[]{Color.GREEN, Color.RED});
        dataSet.setDrawValues(false); // Отключаем отображение значений на диаграмме

        PieData data = new PieData(dataSet);
        pieChart.setData(data);

        // Устанавливаем цвет фона центра круговой диаграммы на черный
        pieChart.setHoleColor(Color.BLACK);

        // Убираем легенду с обозначением цвета
        pieChart.getLegend().setEnabled(false);

        pieChart.invalidate();


        binding.exploreBtn.setOnClickListener(v -> {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SubFragment(category)).addToBackStack(null).commit();
        });

        binding.replay.setOnClickListener(v -> {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new QuizFragment(category, title)).addToBackStack(null).commit();
        });

        return binding.getRoot();
    }
}
