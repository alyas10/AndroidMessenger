package com.example.androidmessenger;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidmessenger.AddapterClass.LessonsAdapter;
import com.example.androidmessenger.modelClass.LessonModel;

import java.util.ArrayList;

public class LessonsActivity extends AppCompatActivity {
    private ArrayList<LessonModel> lessonList = new ArrayList<>();
    private LessonsAdapter adapter;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

        // Получаем данные, переданные из SubFragment
        Intent intent = getIntent();
        title = intent.getStringExtra("Title");

        // Инициализируем список уроков
        initLessonList();

        // Установка адаптера для RecyclerView
        RecyclerView rvLessons = findViewById(R.id.rvLessons);
        rvLessons.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LessonsAdapter(this, lessonList);
        rvLessons.setAdapter(adapter);
    }

    private void initLessonList() {
        // Заполнение списка уроков в зависимости от выбранного шифра
        switch (title) {
            case "Основы безопасности и анонимности в сети":
                lessonList.add(new LessonModel("Урок 1: Как устроен интернет"));
                lessonList.add(new LessonModel("Урок 2: Основы анонимности в сети"));
                lessonList.add(new LessonModel("Урок 3: OSINT и Социальная инженерия"));
                lessonList.add(new LessonModel("Урок 4: Основные правила кибергигиены"));
                lessonList.add(new LessonModel("Урок 5: Полезные сайты о кибербезопасности"));
                break;
            case "Киберугрозы в современном мире":
                lessonList.add(new LessonModel("Урок 1: Введение в шифр Атбаш"));
                lessonList.add(new LessonModel("Урок 2: Применение шифра Атбаш"));
                lessonList.add(new LessonModel("Урок 3: Особенности шифра Атбаш"));
                lessonList.add(new LessonModel("Урок 4: Особенности шифра Атбаш"));
                lessonList.add(new LessonModel("Урок 5: Особенности шифра Атбаш"));
                break;
            case "Способы хранения информации":
                lessonList.add(new LessonModel("Урок 1: Введение в шифр Атбаш"));
                lessonList.add(new LessonModel("Урок 2: Применение шифра Атбаш"));
                lessonList.add(new LessonModel("Урок 3: Особенности шифра Атбаш"));
                lessonList.add(new LessonModel("Урок 4: Особенности шифра Атбаш"));
                lessonList.add(new LessonModel("Урок 5: Особенности шифра Атбаш"));
                break;
            case "Криптография":
                lessonList.add(new LessonModel("Урок 1: Шифр Цезаря"));
                lessonList.add(new LessonModel("Урок 2: Шифр Атбаш"));
                lessonList.add(new LessonModel("Урок 3: Шифр Виженера"));
                lessonList.add(new LessonModel("Урок 4: Афинный шифр"));
                lessonList.add(new LessonModel("Урок 5: Шифр XOR"));
                lessonList.add(new LessonModel("Урок 6: Шифр RSA"));
                break;
            case "Блок Который будет1":
                lessonList.add(new LessonModel("Урок 1: Введение в шифр Атбаш"));
                lessonList.add(new LessonModel("Урок 2: Применение шифра Атбаш"));
                lessonList.add(new LessonModel("Урок 3: Особенности шифра Атбаш"));
                lessonList.add(new LessonModel("Урок 4: Особенности шифра Атбаш"));
                lessonList.add(new LessonModel("Урок 5: Особенности шифра Атбаш"));
                break;
            case "Блок Который будет2":
                lessonList.add(new LessonModel("Урок 1: Введение в шифр Атбаш1"));
                lessonList.add(new LessonModel("Урок 2: Применение шифра Атбаш1"));
                lessonList.add(new LessonModel("Урок 3: Особенности шифра Атбаш1"));
                lessonList.add(new LessonModel("Урок 4: Особенности шифра Атбаш1"));
                lessonList.add(new LessonModel("Урок 5: Особенности шифра Атбаш1"));
                break;
        }
    }
}