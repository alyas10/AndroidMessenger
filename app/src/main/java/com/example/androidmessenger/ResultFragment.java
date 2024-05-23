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
    int right, wrong, AllQuestion;
    PieChart pieChart;
    private SharedPreferences preferences;

    String category, title;
    // Константы для работы с SharedPreferences
    private static final String PREF_NAME = "QuizResults";
    private static final String CAESAR_RIGHT_ANSWERS_KEY = "CaesarRightAnswers";
    private static final String CAESAR_WRONG_ANSWERS_KEY = "CaesarWrongAnswers";
    private static final String VIGENERE_RIGHT_ANSWERS_KEY = "VigenereRightAnswers";
    private static final String VIGENERE_WRONG_ANSWERS_KEY = "VigenereWrongAnswers";
    private static final String ATBASH_RIGHT_ANSWERS_KEY = "AtbashRightAnswers";
    private static final String ATBASH_WRONG_ANSWERS_KEY = "AtbashWrongAnswers";

    public ResultFragment() {
    }
    public void saveResults() {
        SharedPreferences.Editor editor = preferences.edit();
        switch (category) {
            case "Цезарь":
                editor.putInt(CAESAR_RIGHT_ANSWERS_KEY, right);
                editor.putInt(CAESAR_WRONG_ANSWERS_KEY, wrong);
                break;
            case "Виженер":
                editor.putInt(VIGENERE_RIGHT_ANSWERS_KEY, right);
                editor.putInt(VIGENERE_WRONG_ANSWERS_KEY, wrong);
                break;
            case "Атбаш":
                editor.putInt(ATBASH_RIGHT_ANSWERS_KEY, right);
                editor.putInt(ATBASH_WRONG_ANSWERS_KEY, wrong);
                break;
        }
        editor.apply();
    }
    public ResultFragment(int right, int wrong, int AllQuestion, String category, String title) {
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
        switch (category) {
            case "Цезарь":
                right = preferences.getInt(CAESAR_RIGHT_ANSWERS_KEY, 0);
                wrong = preferences.getInt(CAESAR_WRONG_ANSWERS_KEY, 0);
                break;
            case "Виженер":
                right = preferences.getInt(VIGENERE_RIGHT_ANSWERS_KEY, 0);
                wrong = preferences.getInt(VIGENERE_WRONG_ANSWERS_KEY, 0);
                break;
            case "Атбаш":
                right = preferences.getInt(ATBASH_RIGHT_ANSWERS_KEY, 0);
                wrong = preferences.getInt(ATBASH_WRONG_ANSWERS_KEY, 0);
                break;
        }
        String rightScore = String.valueOf(right);
        String wrongScore = String.valueOf(AllQuestion - right);
        String totalScore = String.valueOf(AllQuestion);

        binding.correct.setText(rightScore + " Верных");
        binding.incorrect.setText(wrongScore + " Неверных");
        binding.score.setText("Верных ответов " + rightScore + " из " + totalScore);
        saveResults();
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
            SharedPreferences.Editor editor = preferences.edit();
            switch (category) {
                case "Цезарь":
                    editor.putInt(CAESAR_RIGHT_ANSWERS_KEY, 0);
                    editor.putInt(CAESAR_WRONG_ANSWERS_KEY, 0);
                    break;
                case "Виженер":
                    editor.putInt(VIGENERE_RIGHT_ANSWERS_KEY, 0);
                    editor.putInt(VIGENERE_WRONG_ANSWERS_KEY, 0);
                    break;
                case "Атбаш":
                    editor.putInt(ATBASH_RIGHT_ANSWERS_KEY, 0);
                    editor.putInt(ATBASH_WRONG_ANSWERS_KEY, 0);
                    break;
            }
            editor.apply();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SubFragment(category)).addToBackStack(null).commit();
        });

        binding.replay.setOnClickListener(v -> {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            SharedPreferences.Editor editor = preferences.edit();
            switch (category) {
                case "Цезарь":
                    editor.putInt(CAESAR_RIGHT_ANSWERS_KEY, 0);
                    editor.putInt(CAESAR_WRONG_ANSWERS_KEY, 0);
                    break;
                case "Виженер":
                    editor.putInt(VIGENERE_RIGHT_ANSWERS_KEY, 0);
                    editor.putInt(VIGENERE_WRONG_ANSWERS_KEY, 0);
                    break;
                case "Атбаш":
                    editor.putInt(ATBASH_RIGHT_ANSWERS_KEY, 0);
                    editor.putInt(ATBASH_WRONG_ANSWERS_KEY, 0);
                    break;
            }
            editor.apply();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new QuizFragment(category, title)).addToBackStack(null).commit();
        });

        return binding.getRoot();
    }
}
