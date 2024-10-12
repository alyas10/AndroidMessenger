package com.example.androidmessenger;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.androidmessenger.AddapterClass.OnboardingAdapter;
import com.example.androidmessenger.modelClass.SubModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
/**
 * Активити для отображения подробной информации о конкретном шифре.
 * @author Иван Минаев
 * @version 2.0
 */
public class DetailActivity extends AppCompatActivity {

    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnboardingIndicators;
    private AppCompatButton buttonOnboardingAction;
    private String dataTitle;
    private ArrayList<SubModel> list; // добавлено поле list
    private int position; // добавлено поле position
    int cezar,cezar1,atbash, atbash1,vigener,vigener1,afin,afin1,gamma,gamma1,rsa,rsa1;
    public int mod1_less1_correct,mod1_less1_incorrect,mod1_less2_correct,mod1_less2_incorrect,mod1_less3_correct,mod1_less3_incorrect;

    /**
     * Вызывается при запуске действия.
     * Этот метод инициализирует представление активити, извлекает данные из intent и устанавливает содержимое представлений.
     *
     * @param savedInstanceState - Если действие повторно инициализируется после предыдущего завершения,
     * то этот пакет содержит данные, которые оно в последний раз предоставляло в onSaveInstanceState().
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        layoutOnboardingIndicators = findViewById(R.id.layoutOnboardingIndicators);
        buttonOnboardingAction = findViewById(R.id.buttonOnboardingAction);

        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

        // Получаем данные, переданные из LessonsAdapter
        Intent intent = getIntent();
        String lessonTitle = intent.getStringExtra("LessonTitle");

        switch (lessonTitle) {
            case "Урок 1: Шифр Цезаря":
                setupOnboardingItems();
                ViewPager2 onboardingViewPager = findViewById(R.id.onboardingViewPager);
                onboardingViewPager.setAdapter(onboardingAdapter);

                setupOnboardingIndicators();
                setCurrentOnboardingIndicator(0);

                onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        setCurrentOnboardingIndicator(position);
                    }
                });
                buttonOnboardingAction.setOnClickListener(v -> {
                    if (onboardingViewPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()) {
                        onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem() + 1);
                    }
                    else {
                        //Обработка выбора последнего элемента
                        String category = "Тесты по шифрованию"; // Инициализация категории
                        String title = "Тест по шифру Цезаря";

                        // Проверка, что список не пустой
                        if (list != null) {
                            int position = list.size() - 1;
                            category = list.get(position).getCategory();
                            title = list.get(position).getTitle();
                        }
                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        cezar = 0;
                        cezar1 = 0;
                        DatabaseReference resultsRef = db.child("Result/" + "Cryptography/" + "Lesson 1: The Caesar Cipher");
                        // Создайте объект TestResult (предполагается, что у вас есть класс TestResult)
                        TestResult testResult1 = new TestResult(cezar, cezar1);
                        // Задайте уникальный ключ для вашей категории (например, "caesarCategory")
                        String CaesarcategoryKey = userId;
                        // Обновите значения для этого ключа
                        resultsRef.child(CaesarcategoryKey).setValue(testResult1);
                        QuizFragment quizFragment = new QuizFragment(category, title);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, quizFragment)
                                .addToBackStack(null)
                                .commit();
                        // Очистка кнопки "Пройти тест"
                        buttonOnboardingAction.setVisibility(View.GONE); // Hide the button


                    }
                });
                break;
            case "Урок 2: Шифр Атбаш":
                // Обработка шифра Атбаш
                setupOnboardingItems1();
                ViewPager2 onboardingViewPager1 = findViewById(R.id.onboardingViewPager);
                onboardingViewPager1.setAdapter(onboardingAdapter);

                setupOnboardingIndicators();
                setCurrentOnboardingIndicator(0);

                onboardingViewPager1.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        setCurrentOnboardingIndicator(position);
                    }
                });
                buttonOnboardingAction.setOnClickListener(v -> {
                    if (onboardingViewPager1.getCurrentItem() + 1 < onboardingAdapter.getItemCount()) {
                        onboardingViewPager1.setCurrentItem(onboardingViewPager1.getCurrentItem() + 1);
                    }
                    else {
                        //Обработка выбора последнего элемента
                        String category = "Тесты по шифрованию"; // Инициализация категории
                        String title = "Тест по шифру Атбаш";

                        // Проверка, что список не пустой
                        if (list != null) {
                            int position = list.size() - 1;
                            category = list.get(position).getCategory();
                            title = list.get(position).getTitle();
                        }
                        String userId1 = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        atbash= 0;
                        atbash1 = 0;
                        DatabaseReference resultsRef1 = db.child("Result/" + "Cryptography/" + "Lesson 2: The Atbash Cipher");
                        // Создайте объект TestResult (предполагается, что у вас есть класс TestResult)
                        TestResult testResult2 = new TestResult(atbash, atbash1);
                        // Задайте уникальный ключ для вашей категории (например, "caesarCategory")
                        String AtbashcategoryKey = userId1;
                        // Обновите значения для этого ключа
                        resultsRef1.child(AtbashcategoryKey).setValue(testResult2);
                        QuizFragment quizFragment = new QuizFragment(category, title);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, quizFragment)
                                .addToBackStack(null)
                                .commit();
                        // Очистка кнопки "Пройти тест"
                        buttonOnboardingAction.setVisibility(View.GONE); // Hide the button


                    }
                });
                break;
            case "Урок 3: Шифр Виженера":
                // Обработка шифра Атбаш
                setupOnboardingItems2();
                ViewPager2 onboardingViewPager2 = findViewById(R.id.onboardingViewPager);
                onboardingViewPager2.setAdapter(onboardingAdapter);

                setupOnboardingIndicators();
                setCurrentOnboardingIndicator(0);

                onboardingViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        setCurrentOnboardingIndicator(position);
                    }
                });
                buttonOnboardingAction.setOnClickListener(v -> {
                    if (onboardingViewPager2.getCurrentItem() + 1 < onboardingAdapter.getItemCount()) {
                        onboardingViewPager2.setCurrentItem(onboardingViewPager2.getCurrentItem() + 1);
                    }
                    else {
                        //Обработка выбора последнего элемента
                        String category = "Тесты по шифрованию"; // Инициализация категории
                        String title = "Тест по шифру Виженера";

                        // Проверка, что список не пустой
                        if (list != null) {
                            int position = list.size() - 1;
                            category = list.get(position).getCategory();
                            title = list.get(position).getTitle();
                        }
                        String userId2 = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        vigener= 0;
                        vigener1 = 0;
                        DatabaseReference resultsRef2 = db.child("Result/" + "Cryptography/" + "Lesson 3: The Vigener Cipher");
                        // Создайте объект TestResult (предполагается, что у вас есть класс TestResult)
                        TestResult testResult3 = new TestResult(vigener, vigener1);
                        // Задайте уникальный ключ для вашей категории (например, "caesarCategory")
                        String VigenerСategoryKey = userId2;
                        // Обновите значения для этого ключа
                        resultsRef2.child(VigenerСategoryKey).setValue(testResult3);
                        QuizFragment quizFragment = new QuizFragment(category, title);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, quizFragment)
                                .addToBackStack(null)
                                .commit();
                        // Очистка кнопки "Пройти тест"
                        buttonOnboardingAction.setVisibility(View.GONE); // Hide the button


                    }
                });
                break;
            case "Урок 4: Афинный шифр":
                // Обработка шифра Атбаш
                setupOnboardingItems3();
                ViewPager2 onboardingViewPager3 = findViewById(R.id.onboardingViewPager);
                onboardingViewPager3.setAdapter(onboardingAdapter);

                setupOnboardingIndicators();
                setCurrentOnboardingIndicator(0);

                onboardingViewPager3.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        setCurrentOnboardingIndicator(position);
                    }
                });
                buttonOnboardingAction.setOnClickListener(v -> {
                    if (onboardingViewPager3.getCurrentItem() + 1 < onboardingAdapter.getItemCount()) {
                        onboardingViewPager3.setCurrentItem(onboardingViewPager3.getCurrentItem() + 1);
                    }
                    else {
                        //Обработка выбора последнего элемента
                        String category = "Тесты по шифрованию"; // Инициализация категории
                        String title = "Тест по Афинному шифру";

                        // Проверка, что список не пустой
                        if (list != null) {
                            int position = list.size() - 1;
                            category = list.get(position).getCategory();
                            title = list.get(position).getTitle();
                        }
                        String userId3 = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        afin= 0;
                        afin1 = 0;
                        DatabaseReference resultsRef3 = db.child("Result/" + "Cryptography/" + "Lesson 4: Affine Cipher");
                        // Создайте объект TestResult (предполагается, что у вас есть класс TestResult)
                        TestResult testResult4 = new TestResult(afin, afin1);
                        // Задайте уникальный ключ для вашей категории (например, "caesarCategory")
                        String AfinСategoryKey = userId3;
                        // Обновите значения для этого ключа
                        resultsRef3.child(AfinСategoryKey).setValue(testResult4);
                        QuizFragment quizFragment = new QuizFragment(category, title);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, quizFragment)
                                .addToBackStack(null)
                                .commit();
                        // Очистка кнопки "Пройти тест"
                        buttonOnboardingAction.setVisibility(View.GONE); // Hide the button


                    }
                });
                break;
            case "Урок 5: Шифр XOR":
                // Обработка шифра Атбаш
                setupOnboardingItems4();
                ViewPager2 onboardingViewPager4 = findViewById(R.id.onboardingViewPager);
                onboardingViewPager4.setAdapter(onboardingAdapter);

                setupOnboardingIndicators();
                setCurrentOnboardingIndicator(0);

                onboardingViewPager4.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        setCurrentOnboardingIndicator(position);
                    }
                });
                buttonOnboardingAction.setOnClickListener(v -> {
                    if (onboardingViewPager4.getCurrentItem() + 1 < onboardingAdapter.getItemCount()) {
                        onboardingViewPager4.setCurrentItem(onboardingViewPager4.getCurrentItem() + 1);
                    }
                    else {
                        //Обработка выбора последнего элемента
                        String category = "Тесты по шифрованию"; // Инициализация категории
                        String title = "Тест по шифру Гаммирования";

                        // Проверка, что список не пустой
                        if (list != null) {
                            int position = list.size() - 1;
                            category = list.get(position).getCategory();
                            title = list.get(position).getTitle();
                        }
                        String userId4 = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        gamma= 0;
                        gamma1 = 0;
                        DatabaseReference resultsRef4 = db.child("Result/" + "Cryptography/" + "Lesson 5: The XOR Cipher");
                        // Создайте объект TestResult (предполагается, что у вас есть класс TestResult)
                        TestResult testResult5 = new TestResult(gamma, gamma1);
                        // Задайте уникальный ключ для вашей категории (например, "caesarCategory")
                        String GammaСategoryKey = userId4;
                        // Обновите значения для этого ключа
                        resultsRef4.child(GammaСategoryKey).setValue(testResult5);
                        QuizFragment quizFragment = new QuizFragment(category, title);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, quizFragment)
                                .addToBackStack(null)
                                .commit();
                        // Очистка кнопки "Пройти тест"
                        buttonOnboardingAction.setVisibility(View.GONE); // Hide the button


                    }
                });
                break;
            case "Урок 6: Шифр RSA":
                // Обработка шифра Атбаш
                setupOnboardingItems5();
                ViewPager2 onboardingViewPager5 = findViewById(R.id.onboardingViewPager);
                onboardingViewPager5.setAdapter(onboardingAdapter);

                setupOnboardingIndicators();
                setCurrentOnboardingIndicator(0);

                onboardingViewPager5.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        setCurrentOnboardingIndicator(position);
                    }
                });
                buttonOnboardingAction.setOnClickListener(v -> {
                    if (onboardingViewPager5.getCurrentItem() + 1 < onboardingAdapter.getItemCount()) {
                        onboardingViewPager5.setCurrentItem(onboardingViewPager5.getCurrentItem() + 1);
                    }
                    else {
                        //Обработка выбора последнего элемента
                        String category = "Тесты по шифрованию"; // Инициализация категории
                        String title = "Тест по шифру RSA";

                        // Проверка, что список не пустой
                        if (list != null) {
                            int position = list.size() - 1;
                            category = list.get(position).getCategory();
                            title = list.get(position).getTitle();
                        }
                        String userId5 = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        rsa= 0;
                        rsa1 = 0;
                        DatabaseReference resultsRef5 = db.child("Result/" + "Cryptography/" + "Lesson 6: The RSA Cipher");
                        // Создайте объект TestResult (предполагается, что у вас есть класс TestResult)
                        TestResult testResult6 = new TestResult(gamma, gamma1);
                        // Задайте уникальный ключ для вашей категории (например, "caesarCategory")
                        String RsaСategoryKey = userId5;
                        // Обновите значения для этого ключа
                        resultsRef5.child(RsaСategoryKey).setValue(testResult6);
                        QuizFragment quizFragment = new QuizFragment(category, title);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, quizFragment)
                                .addToBackStack(null)
                                .commit();
                        // Очистка кнопки "Пройти тест"
                        buttonOnboardingAction.setVisibility(View.GONE); // Hide the button


                    }
                });
                break;
            case "Урок 1: Как устроен интернет":
                setupOnboardingItems_mod1_less1();
                ViewPager2 onboardingViewPagermod1_less1 = findViewById(R.id.onboardingViewPager);
                onboardingViewPagermod1_less1.setAdapter(onboardingAdapter);

                setupOnboardingIndicators();
                setCurrentOnboardingIndicator(0);

                onboardingViewPagermod1_less1.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        setCurrentOnboardingIndicator(position);
                    }
                });
                buttonOnboardingAction.setOnClickListener(v -> {
                    if (onboardingViewPagermod1_less1.getCurrentItem() + 1 < onboardingAdapter.getItemCount()) {
                        onboardingViewPagermod1_less1.setCurrentItem(onboardingViewPagermod1_less1.getCurrentItem() + 1);
                    }
                    else {
                        //Обработка выбора последнего элемента
                        String category = "Тесты по блоку Основны безопасности и анонимности в сети"; // Инициализация категории
                        String title = "Тест к уроку Как устроен интернет";

                        // Проверка, что список не пустой
                        if (list != null) {
                            int position = list.size() - 1;
                            category = list.get(position).getCategory();
                            title = list.get(position).getTitle();
                        }
                        String userId_mod1_less1 = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        mod1_less1_correct= 0;
                        mod1_less1_incorrect = 0;
                        DatabaseReference resultsRef_mod1_less1 = db.child("Result/" + "The basics of security and anonymity on the web/" + "Lesson 1: How the Internet works");
                        // Создайте объект TestResult (предполагается, что у вас есть класс TestResult)
                        TestResult testResult_mod1_less1 = new TestResult(mod1_less1_correct, mod1_less1_incorrect);
                        // Задайте уникальный ключ для вашей категории (например, "caesarCategory")
                        String Key_mod1_less1 = userId_mod1_less1;
                        // Обновите значения для этого ключа
                        resultsRef_mod1_less1.child(Key_mod1_less1).setValue(testResult_mod1_less1);
                        QuizFragment quizFragment = new QuizFragment(category, title);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, quizFragment)
                                .addToBackStack(null)
                                .commit();
                        // Очистка кнопки "Пройти тест"
                        buttonOnboardingAction.setVisibility(View.GONE); // Hide the button
                    }
                });
                break;
            case "Урок 2: Основы анонимности в сети":
                setupOnboardingItems_mod1_less2();
                ViewPager2 onboardingViewPagermod1_less2 = findViewById(R.id.onboardingViewPager);
                onboardingViewPagermod1_less2.setAdapter(onboardingAdapter);

                setupOnboardingIndicators();
                setCurrentOnboardingIndicator(0);

                onboardingViewPagermod1_less2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        setCurrentOnboardingIndicator(position);
                    }
                });
                buttonOnboardingAction.setOnClickListener(v -> {
                    if (onboardingViewPagermod1_less2.getCurrentItem() + 1 < onboardingAdapter.getItemCount()) {
                        onboardingViewPagermod1_less2.setCurrentItem(onboardingViewPagermod1_less2.getCurrentItem() + 1);
                    }
                    else {
                        //Обработка выбора последнего элемента
                        String category = "Тесты по блоку Основны безопасности и анонимности в сети"; // Инициализация категории
                        String title = "Тест к уроку Основы анонимности в сети";

                        // Проверка, что список не пустой
                        if (list != null) {
                            int position = list.size() - 1;
                            category = list.get(position).getCategory();
                            title = list.get(position).getTitle();
                        }
                        String userId_mod1_less2 = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        mod1_less2_correct= 0;
                        mod1_less2_incorrect = 0;
                        DatabaseReference resultsRef_mod1_less2 = db.child("Result/" + "The basics of security and anonymity on the web/" + "Lesson 2: The Basics of Online Anonymity");
                        // Создайте объект TestResult (предполагается, что у вас есть класс TestResult)
                        TestResult testResult_mod1_less2 = new TestResult(mod1_less2_correct, mod1_less2_incorrect);
                        // Задайте уникальный ключ для вашей категории (например, "caesarCategory")
                        String Key_mod1_less2 = userId_mod1_less2;
                        // Обновите значения для этого ключа
                        resultsRef_mod1_less2.child(Key_mod1_less2).setValue(testResult_mod1_less2);
                        QuizFragment quizFragment = new QuizFragment(category, title);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, quizFragment)
                                .addToBackStack(null)
                                .commit();
                        // Очистка кнопки "Пройти тест"
                        buttonOnboardingAction.setVisibility(View.GONE); // Hide the button
                    }
                });
                break;
            case "Урок 3: OSINT и Социальная инженерия":
                setupOnboardingItems_mod1_less3();
                ViewPager2 onboardingViewPagermod1_less3 = findViewById(R.id.onboardingViewPager);
                onboardingViewPagermod1_less3.setAdapter(onboardingAdapter);

                setupOnboardingIndicators();
                setCurrentOnboardingIndicator(0);

                onboardingViewPagermod1_less3.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        setCurrentOnboardingIndicator(position);
                    }
                });
                buttonOnboardingAction.setOnClickListener(v -> {
                    if (onboardingViewPagermod1_less3.getCurrentItem() + 1 < onboardingAdapter.getItemCount()) {
                        onboardingViewPagermod1_less3.setCurrentItem(onboardingViewPagermod1_less3.getCurrentItem() + 1);
                    }
                    else {
                        //Обработка выбора последнего элемента
                        String category = "Тесты по блоку Основны безопасности и анонимности в сети"; // Инициализация категории
                        String title = "Тест к уроку OSINT и Социальная инженерия";

                        // Проверка, что список не пустой
                        if (list != null) {
                            int position = list.size() - 1;
                            category = list.get(position).getCategory();
                            title = list.get(position).getTitle();
                        }
                        String userId_mod1_less3 = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        mod1_less3_correct= 0;
                        mod1_less3_incorrect = 0;
                        DatabaseReference resultsRef_mod1_less3 = db.child("Result/" + "The basics of security and anonymity on the web/" + "Lesson 3: OSINT and Social Engineering");
                        // Создайте объект TestResult (предполагается, что у вас есть класс TestResult)
                        TestResult testResult_mod1_less3 = new TestResult(mod1_less3_correct, mod1_less3_incorrect);
                        // Задайте уникальный ключ для вашей категории (например, "caesarCategory")
                        String Key_mod1_less3 = userId_mod1_less3;
                        // Обновите значения для этого ключа
                        resultsRef_mod1_less3.child(Key_mod1_less3).setValue(testResult_mod1_less3);
                        QuizFragment quizFragment = new QuizFragment(category, title);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, quizFragment)
                                .addToBackStack(null)
                                .commit();
                        // Очистка кнопки "Пройти тест"
                        buttonOnboardingAction.setVisibility(View.GONE); // Hide the button
                    }
                });
                break;

        }
    }

    private void setupOnboardingItems() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        onboardingItems.add(new OnboardingItem("Шифр Цезаря", getString(R.string.cezar1)));
        onboardingItems.add(new OnboardingItem("Принцип работы", R.drawable.cezar, getString(R.string.cezar2)));
        onboardingItems.add(new OnboardingItem("Сложность", R.drawable.cezar, getString(R.string.cezar3)));
        onboardingItems.add(new OnboardingItem("Использование", R.drawable.cezar, getString(R.string.cezar4)));
        onboardingItems.add(new OnboardingItem("Алгоритм работы", R.drawable.cezar, getString(R.string.cezar6)));
        onboardingItems.add(new OnboardingItem("Алгоритм работы", R.drawable.cezar, getString(R.string.cezar7)));
        onboardingItems.add(new OnboardingItem("Алгоритм работы", R.drawable.cezar, getString(R.string.cezar8)));
        onboardingItems.add(new OnboardingItem("Алгоритм шифрования", R.drawable.cezar, getString(R.string.cezar9)));
        onboardingItems.add(new OnboardingItem("Алгоритм расшифрования", R.drawable.cezar, getString(R.string.cezar10)));
        onboardingItems.add(new OnboardingItem("Вывод", R.drawable.cezar, getString(R.string.cezar11)));

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }
    private void setupOnboardingItems1() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        onboardingItems.add(new OnboardingItem("Шифр Атбаш", R.drawable.atbash, getString(R.string.atbash1)));
        onboardingItems.add(new OnboardingItem("Принцип работы", R.drawable.atbash, getString(R.string.atbash2)));
        onboardingItems.add(new OnboardingItem("Сложность", R.drawable.atbash, getString(R.string.atbash3)));
        onboardingItems.add(new OnboardingItem("Использование", R.drawable.atbash, getString(R.string.atbash4)));
        onboardingItems.add(new OnboardingItem("Алгоритм работы", R.drawable.atbash, getString(R.string.atbash6)));
        onboardingItems.add(new OnboardingItem("Алгоритм работы", R.drawable.atbash, getString(R.string.atbash7)));
        onboardingItems.add(new OnboardingItem("Алгоритм работы", R.drawable.atbash, getString(R.string.atbash8)));
        onboardingItems.add(new OnboardingItem("Алгоритм шифрования", R.drawable.atbash, getString(R.string.atbash9)));
        onboardingItems.add(new OnboardingItem("Алгоритм расшифрования", R.drawable.atbash, getString(R.string.atbash10)));
        onboardingItems.add(new OnboardingItem("Вывод", R.drawable.atbash, getString(R.string.atbash11)));

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }

    private void setupOnboardingItems2() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        onboardingItems.add(new OnboardingItem("Шифр Виженер", R.drawable.vigener, getString(R.string.vigener1)));
        onboardingItems.add(new OnboardingItem("Принцип работы", R.drawable.vigener, getString(R.string.vigener2)));
        onboardingItems.add(new OnboardingItem("Сложность", R.drawable.vigener, getString(R.string.vigener3)));
        onboardingItems.add(new OnboardingItem("Использование", R.drawable.vigener, getString(R.string.vigener4)));
        onboardingItems.add(new OnboardingItem("Алгоритм работы", R.drawable.vigener, getString(R.string.vigener6)));
        onboardingItems.add(new OnboardingItem("Алгоритм работы", R.drawable.vigener, getString(R.string.vigener7)));
        onboardingItems.add(new OnboardingItem("Алгоритм работы", R.drawable.vigener, getString(R.string.vigener8)));
        onboardingItems.add(new OnboardingItem("Алгоритм шифрования", R.drawable.vigener, getString(R.string.vigener9)));
        onboardingItems.add(new OnboardingItem("Алгоритм расшифрования", R.drawable.vigener, getString(R.string.vigener10)));
        onboardingItems.add(new OnboardingItem("Вывод", R.drawable.vigener, getString(R.string.vigener11)));

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }

    private void setupOnboardingItems3() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        onboardingItems.add(new OnboardingItem("Афинный шифр", R.drawable.afin, getString(R.string.afin1)));
        onboardingItems.add(new OnboardingItem("Принцип работы", R.drawable.vigener, getString(R.string.vigener2)));
        onboardingItems.add(new OnboardingItem("Сложность", R.drawable.vigener, getString(R.string.vigener3)));
        onboardingItems.add(new OnboardingItem("Использование", R.drawable.vigener, getString(R.string.vigener4)));
        onboardingItems.add(new OnboardingItem("Алгоритм шифрования", R.drawable.vigener, getString(R.string.vigener6)));
        onboardingItems.add(new OnboardingItem("Алгоритм шифрования", R.drawable.vigener, getString(R.string.vigener7)));
        onboardingItems.add(new OnboardingItem("Алгоритм расшифрования", R.drawable.vigener, getString(R.string.vigener8)));
        onboardingItems.add(new OnboardingItem("Алгоритм расшифрования", R.drawable.vigener, getString(R.string.vigener9)));
        onboardingItems.add(new OnboardingItem("Криптостойкость", R.drawable.vigener, getString(R.string.vigener10)));
        onboardingItems.add(new OnboardingItem("Вывод", R.drawable.vigener, getString(R.string.vigener11)));

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }

    private void setupOnboardingItems4() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        onboardingItems.add(new OnboardingItem("Шифр XOR", R.drawable.gamma, getString(R.string.gamma1)));
        onboardingItems.add(new OnboardingItem("Гамма", R.drawable.gamma, getString(R.string.gamma2)));
        onboardingItems.add(new OnboardingItem("Принцип работы", R.drawable.gamma, getString(R.string.gamma3)));
        onboardingItems.add(new OnboardingItem("Криптосойкость", R.drawable.gamma, getString(R.string.gamma4)));
        onboardingItems.add(new OnboardingItem("Криптосойкость", R.drawable.gamma, getString(R.string.gamma5)));
        onboardingItems.add(new OnboardingItem("Использование", R.drawable.gamma, getString(R.string.gamma6)));
        onboardingItems.add(new OnboardingItem("Алгоритм шифрования", R.drawable.gamma, getString(R.string.gamma7)));
        onboardingItems.add(new OnboardingItem("Алгоритм шифрования", R.drawable.gamma, getString(R.string.gamma8)));
        onboardingItems.add(new OnboardingItem("Алгоритм расшифрования", R.drawable.gamma, getString(R.string.gamma9)));
        onboardingItems.add(new OnboardingItem("Вывод", R.drawable.gamma, getString(R.string.gamma10)));

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }

    private void setupOnboardingItems5() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        onboardingItems.add(new OnboardingItem("Шифр RSA", R.drawable.rsa1, getString(R.string.rsa1)));
        onboardingItems.add(new OnboardingItem("Принцип работы", R.drawable.rsa1, getString(R.string.rsa2)));
        onboardingItems.add(new OnboardingItem("Сложность", R.drawable.rsa1, getString(R.string.rsa3)));
        onboardingItems.add(new OnboardingItem("Криптосойкость", R.drawable.rsa1, getString(R.string.rsa4)));
        onboardingItems.add(new OnboardingItem("Криптосойкость", R.drawable.rsa1, getString(R.string.rsa5)));
        onboardingItems.add(new OnboardingItem("Использование", R.drawable.rsa1, getString(R.string.rsa6)));
        onboardingItems.add(new OnboardingItem("Алгоритм работы", R.drawable.rsa1, getString(R.string.rsa7)));
        onboardingItems.add(new OnboardingItem("Алгоритм шифрования", R.drawable.rsa1, getString(R.string.rsa8)));
        onboardingItems.add(new OnboardingItem("Алгоритм расшифрования", R.drawable.rsa1, getString(R.string.rsa9)));
        onboardingItems.add(new OnboardingItem("Вывод", R.drawable.rsa1, getString(R.string.rsa10)));

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }
    private void setupOnboardingItems_mod1_less1() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        onboardingItems.add(new OnboardingItem("Понятие Интернет",  getString(R.string.mod1_less1_1)));
        onboardingItems.add(new OnboardingItem("IP адрес", R.drawable.mod1_less1_2 , getString(R.string.mod1_less1_2)));
        onboardingItems.add(new OnboardingItem("Пример IP адреса", R.drawable.mod1_less1_3 , getString(R.string.mod1_less1_3)));
        onboardingItems.add(new OnboardingItem("Диапазон IP-адресов",  getString(R.string.mod1_less1_4)));
        onboardingItems.add(new OnboardingItem("Состав IP-адреса", R.drawable.mod1_less1_5 , getString(R.string.mod1_less1_5)));
        onboardingItems.add(new OnboardingItem("Веб Архив",  getString(R.string.mod1_less1_6)));

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }
    private void setupOnboardingItems_mod1_less2() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        onboardingItems.add(new OnboardingItem("Анонимность",  getString(R.string.mod1_less2_1)));
        onboardingItems.add(new OnboardingItem("Безопасность в сети", getString(R.string.mod1_less2_2)));
        onboardingItems.add(new OnboardingItem("Советы по анонимности", getString(R.string.mod1_less2_3)));
        onboardingItems.add(new OnboardingItem("Советы по анонимности", getString(R.string.mod1_less2_4)));

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }
    private void setupOnboardingItems_mod1_less3() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        onboardingItems.add(new OnboardingItem("OSINT",  getString(R.string.mod1_less3_1)));
        onboardingItems.add(new OnboardingItem("Социальная инженерия", getString(R.string.mod1_less3_2)));
        onboardingItems.add(new OnboardingItem("Поддельные сайты", getString(R.string.mod1_less3_3)));
        onboardingItems.add(new OnboardingItem("Поддельные сайты", getString(R.string.mod1_less3_4)));
        onboardingItems.add(new OnboardingItem("Поддельные сайты", getString(R.string.mod1_less3_5)));
        onboardingItems.add(new OnboardingItem("Поддельные сайты", getString(R.string.mod1_less3_6)));
        onboardingItems.add(new OnboardingItem("Вишинг", getString(R.string.mod1_less3_7)));
        onboardingItems.add(new OnboardingItem("Дополнительная информация", getString(R.string.mod1_less3_8)));

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }
    /*private void setupOnboardingItems_mod1_less4() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }*/
    /*private void setupOnboardingItems_mod1_less5() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }*/
    /*private void setupOnboardingItems_mod2_less1() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }*/
    /*private void setupOnboardingItems_mod2_less2() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }*/
    /*private void setupOnboardingItems_mod2_less3() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }*/
    /*private void setupOnboardingItems_mod2_less4() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }*/
    /*private void setupOnboardingItems_mod2_less5() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }*/
    /*private void setupOnboardingItems_mod3_less1() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }*/
    /*private void setupOnboardingItems_mod3_less2() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }*/
    /*private void setupOnboardingItems_mod3_less3() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }*/
    /*private void setupOnboardingItems_mod3_less4() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }*/
    /*private void setupOnboardingItems_mod3_less5() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }*/
    /*private void setupOnboardingItems_mod4_less1() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }*/
    /*private void setupOnboardingItems_mod4_less2() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }*/
    /*private void setupOnboardingItems_mod4_less3() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }*/
    /*private void setupOnboardingItems_mod4_less4() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }*/
    /*private void setupOnboardingItems_mod4_less5() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }*/
    /*private void setupOnboardingItems_mod5_less1() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }*/
    /*private void setupOnboardingItems_mod5_less2() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }*/
     /*private void setupOnboardingItems_mod5_less3() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }*/
     /*private void setupOnboardingItems_mod5_less4() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }*/
     /*private void setupOnboardingItems_mod5_less5() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));
        onboardingItems.add(new OnboardingItem("", getString(R.string.)));

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }*/


 /*Настраивает индикаторы onboarding.
 *
 * Этот метод создает набор индикаторов, соответствующих количеству элементов onboarding,
 * добавляет их в `layoutOnboardingIndicators` и устанавливает начальное изображение
 * для каждого индикатора.
 */
    private void setupOnboardingIndicators(){
        // Создает массив индикаторов, размер которого равен количеству элементов onboarding.
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        for (int i = 0; i < indicators.length; i++){
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboarding_indicator_active
            ));
            // Создает каждый индикатор, устанавливает его изображение и добавляет в layoutOnboardingIndicators.
            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicators.addView(indicators[i]);
        }
    }
    /*
 * Устанавливает индикатор текущего элемента onboarding.
 *
 * @param index Индекс текущего элемента onboarding.
 */
    private void setCurrentOnboardingIndicator(int index) {
        int childCount = layoutOnboardingIndicators.getChildCount();
        for (int i = 0; i < childCount; i++){
            ImageView imageView = (ImageView) layoutOnboardingIndicators.getChildAt(i);
            if (i==index){
                // Устанавливает изображение неактивного индикатора для текущего элемента.
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_inactive)
                );
            } else {
                // Устанавливает изображение активного индикатора для остальных элементов.
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_active)
                );
            }
        }
        // Изменяет текст кнопки "Далее" в зависимости от того, является ли текущий элемент последним.
        if(index == onboardingAdapter.getItemCount()-1){
            buttonOnboardingAction.setText("Пройти тест");
        }
        else {
            buttonOnboardingAction.setText("Далее");
        }
    }
}
