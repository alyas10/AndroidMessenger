package com.example.androidmessenger;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
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
import com.example.androidmessenger.databinding.FragmentResultBinding;
import android.content.SharedPreferences;



public class ResultFragment extends Fragment {
FragmentResultBinding binding;
int right,wrong, AllQuestion;
PieChart pieChart;
private SharedPreferences preferences;

   String category, title;
    // Константы для работы с SharedPreferences
    private static final String PREF_NAME = "QuizResults";
    private static final String RIGHT_ANSWERS_KEY = "RightAnswers";
    private static final String WRONG_ANSWERS_KEY = "WrongAnswers";
    public ResultFragment() {


    }
    public ResultFragment(int right,int wrong, int AllQuestion, String category, String title) {
        this.AllQuestion = AllQuestion;
        this.right = right;
        this.category = category;
        this.title = title;
        this.wrong = wrong;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentResultBinding.inflate(inflater, container, false);
        preferences = getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String rightScore = String.valueOf(right);
        String wrongScore = String.valueOf(AllQuestion - right);
        String totalScore = String.valueOf(AllQuestion);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(RIGHT_ANSWERS_KEY, right);
        editor.putInt(WRONG_ANSWERS_KEY, AllQuestion - right);
        editor.apply();
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
            editor.putInt(RIGHT_ANSWERS_KEY, 0);
            editor.putInt(WRONG_ANSWERS_KEY, 0);
            editor.apply();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SubFragment(category)).addToBackStack(null).commit();
        });

        binding.replay.setOnClickListener(v -> {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            editor.putInt(RIGHT_ANSWERS_KEY, 0);
            editor.putInt(WRONG_ANSWERS_KEY, 0);
            editor.apply();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new QuizFragment(category, title)).addToBackStack(null).commit();
        });

        return binding.getRoot();
    }}

