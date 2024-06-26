package com.example.androidmessenger;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.androidmessenger.databinding.FragmentResultBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * Фрагмент для отображения результатов теста с круговой диаграммой.
 *
 * @author Иван Минаев
 * @version 1.0
 */
public class ResultFragment extends Fragment {
    FragmentResultBinding binding;
    int right, wrong, AllQuestion, cezar, atbash, vigener, afin,gamma,rsa;
    // Круговая диаграмма для отображения результатов
    PieChart pieChart;
    private SharedPreferences preferences;

    String category, title;
    /**
     * Конструктор без параметров (необходимо для Firebase).
     */
    public ResultFragment(){
    };
    /**
     * Конструктор для создания фрагмента с результатами теста.
     *
     * @param right       Количество правильных ответов.
     * @param wrong       Количество неправильных ответов.
     * @param AllQuestion Общее количество вопросов.
     * @param cezar      Количество верных ответов по шифру Цезаря.
     * @param atbash      Количество верных ответов по шифру Атбаш.
     * @param vigener     Количество верных ответов по шифру Виженера.
     * @param afin       Количество верных ответов по Афинному шифру.
     * @param gamma      Количество верных ответов по шифру Гаммирования.
     * @param rsa         Количество верных ответов по шифру RSA.
     * @param category   Название категории теста.
     * @param title       Название теста.
     */
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


    /**
     * Создает и настраивает представление фрагмента.
     *
     * @param inflater           Инфлейтер для создания представления из XML-файла макета.
     * @param container          Родительский контейнер представления, к которому будет прикреплено представление фрагмента.
     * @param savedInstanceState Сохраненное состояние фрагмента (если есть).
     * @return Сформированное представление фрагмента.
     */
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

        // Обработчик нажатия на кнопку с возвращением
        binding.exploreBtn.setOnClickListener(v -> {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SubFragment(category)).addToBackStack(null).commit();
        });

        // Обработчик нажатия на кнопку "Пройти тест заново"
        binding.replay.setOnClickListener(v -> {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new QuizFragment(category, title)).addToBackStack(null).commit();
        });

        return binding.getRoot();
    }
}
