package com.example.androidmessenger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.androidmessenger.databinding.FragmentQuizBinding;
import com.example.androidmessenger.modelClass.HomeModel;
import com.example.androidmessenger.modelClass.QuizModel;
import com.example.androidmessenger.modelClass.SubModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
/**
 * Фрагмент, где подгружаются вопросы и ответы для тестирования
 * Реализована опция сохранения результатов
 *
 * @author Иван Минаев
 * @version 2.0
 */
//Задание класса с необходимыми полями для вкладки тестов
public class QuizFragment extends Fragment {
    
    /**
     * Общее количество вопросов.
     */
    int AllQuestion;
    /**
     * Размер списка вопросов.
     */
    String listSize;
     /**
     * Привязка к макету фрагмента.
     */
    FragmentQuizBinding binding;
     /**
     * Список моделей вопросов.
     */
    ArrayList<QuizModel> list = new ArrayList<>();
    /**
     * Счетчики правильных ответов для каждого шифра.
     */
    public int cezar = 0;
    public int atbash = 0;
    public int vigener = 0;
    public int vigener1 = 0;
    public int mod1_less1_correct = 0;
    public int mod1_less1_incorrect = 0;
    public int mod1_less2_correct = 0;
    public int mod1_less2_incorrect = 0;
    public int mod1_less3_correct = 0;
    public int mod1_less3_incorrect = 0;

    public int afin = 0;
    public int afin1 = 0;
    public int atbash1 = 0;
    public int gamma = 0;
    public int gamma1 = 0;
    public int rsa = 0;
    public int rsa1 = 0;
    /**
     * Текущая позиция вопроса в списке.
     */
    private int position = 0;
    /**
     * Счетчик правильных ответов.
     */
    public int right = 0;
     /**
     * Счетчик неправильных ответов.
     */
    public int wrong = 0;
    public int cezar1 = 0;
    String s1,s2,s3,s4,s5,s6,s7,s8,s9;
    
    /**
     * Правильный ответ на вопрос (значение null по умолчанию).
     */
    private static String answer =null;
     /**
     * Номер позиции вопроса.
     */
    String positionNo;
    /**
     * Модель текущего вопроса.
     */
    QuizModel quizModel;

    String category, title;
    private DatabaseReference reference;
    private ProgressBar progressBar;
    private int currentPosition = 0;
    private int totalQuestions = 10; // Общее количество вопросов


    

    /**
     * Конструктор без параметров для создания фрагмента.
     *
     */
    public QuizFragment() {
    }
    /**
     * Конструктор фрагмента.
     *
     * @param category  Категория теста.
     * @param title Заголовок.
     */
    public QuizFragment(String category, String title) {
        this.category = category;
        this.title = title;

    }
    @Override
     /**
     *
     * @param inflater - объект LayoutInflater, который можно использовать для расширения
     * любых представлений во фрагменте,
     * Контейнер @param, если значение не равно null, является родительским представлением, к которому должен быть привязан пользовательский интерфейс фрагмента
     *Фрагмент не должен добавлять само представление,
     *но это может быть использовано для генерации параметров компоновки представления.
     * @param savedInstanceState Если значение не равно null, этот фрагмент создается заново
     * из предыдущего сохраненного состояния, как указано здесь.
     *
     * Создает в БД раздел для каждого теста, где хранит информацию об ответах
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQuizBinding.inflate(getLayoutInflater());
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        // Получаем ссылку на прогресс-бар из макета
        progressBar = binding.progressBar;

        // Устанавливаем начальное значение прогресс-бара
        progressBar.setProgress(0);

        // Устанавливаем значение максимального прогресса
        progressBar.setMax(totalQuestions);
        reference = FirebaseDatabase.getInstance().getReference().child("Result");
        switch (title) {
            case "Тест по шифру Цезаря":
                reference.child("Cryptography").child("Lesson 1: The Caesar Cipher").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Ошибка в получении данных", task.getException());
                        } else {
                            s1 = String.valueOf(task.getResult().getValue());
                            String[] s2 = s1.split(",");
                            cezar = Integer.parseInt(String.valueOf(s2[0].charAt(s2[0].length() - 1)));
                            cezar1 = AllQuestion - cezar;


                            // Проверка значений cezar и cezar1 выполняется после присвоения
                            if (cezar != 0 && cezar1 != 0) {
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ResultFragment(cezar, cezar1, AllQuestion, cezar, atbash, vigener, afin, gamma, rsa, category, title)).commit();
                                    DatabaseReference resultsRef = db.child("Result/" + "Cryptography/" + "Lesson 1: The Caesar Cipher");
                                    TestResult testResult1 = new TestResult(cezar, cezar1);
                                    String CaesarcategoryKey = userId;
                                    resultsRef.child(CaesarcategoryKey).setValue(testResult1);
                                }
                            }
                        }
                });
                break;
            case "Тест по шифру Атбаш":
                reference.child("Cryptography").child("Lesson 2: The Atbash Cipher").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        } else {
                            s2 = String.valueOf(task.getResult().getValue());
                            String[] s3 = s2.split(",");
                            atbash = Integer.parseInt(String.valueOf(s3[0].charAt(s3[0].length() - 1)));
                            atbash1 = AllQuestion - atbash;
                        }
                        if (atbash != 0 && atbash1 != 0){
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ResultFragment(atbash, atbash1, AllQuestion, cezar, atbash, vigener, afin, gamma, rsa, category, title)).commit();
                            DatabaseReference resultsRef1 = db.child("Result/" + "Cryptography/" + "Lesson 2: The Atbash Cipher");
                            String userId1 = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            // Создайте объект TestResult (предполагается, что у вас есть класс TestResult)
                            TestResult testResult2 = new TestResult(atbash, AllQuestion - atbash);
                            // Задайте уникальный ключ для вашей категории (например, "caesarCategory")
                            String AtbashcategoryKey = userId1;
                            // Обновите значения для этого ключа
                            resultsRef1.child(AtbashcategoryKey).setValue(testResult2);
                        }
                    }
                });
                break;
            case "Тест по шифру Виженера":
                reference.child("Cryptography").child("Lesson 3: The Vigener Cipher").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        } else {
                            s3 = String.valueOf(task.getResult().getValue());
                            String[] s4 = s3.split(",");
                            vigener = Integer.parseInt(String.valueOf(s4[0].charAt(s4[0].length() - 1)));
                            vigener1 = AllQuestion - vigener;
                        }
                        if (vigener != 0 && vigener1 != 0){
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ResultFragment(vigener, vigener1, AllQuestion, cezar, atbash, vigener, afin, gamma, rsa, category, title)).commit();
                            DatabaseReference resultsRef2 = db.child("Result/" + "Cryptography/" + "Lesson 3: The Vigener Cipher");
                            String userId2 = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            // Создайте объект TestResult (предполагается, что у вас есть класс TestResult)
                            TestResult testResult3 = new TestResult(vigener, AllQuestion - vigener);
                            // Задайте уникальный ключ для вашей категории (например, "caesarCategory")
                            String VigenerСategoryKey = userId2;
                            // Обновите значения для этого ключа
                            resultsRef2.child(VigenerСategoryKey).setValue(testResult3);
                        }
                    }
                });
                break;
            case "Тест по Афинному шифру":
                reference.child("Cryptography").child("Lesson 4: Affine Cipher").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        } else {
                            s4 = String.valueOf(task.getResult().getValue());
                            String[] s5 = s4.split(",");
                            afin = Integer.parseInt(String.valueOf(s5[0].charAt(s5[0].length() - 1)));
                            afin1 = AllQuestion - afin;
                        }
                        if (afin != 0 && afin1 != 0){
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ResultFragment(afin, afin1, AllQuestion, cezar, atbash, vigener, afin, gamma, rsa, category, title)).commit();
                            DatabaseReference resultsRef3 = db.child("Result/" + "Cryptography/" + "Lesson 4: Affine Cipher");
                            String userId3 = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            // Создайте объект TestResult (предполагается, что у вас есть класс TestResult)
                            TestResult testResult4 = new TestResult(afin, AllQuestion - afin);
                            // Задайте уникальный ключ для вашей категории (например, "caesarCategory")
                            String AfinСategoryKey = userId3;
                            // Обновите значения для этого ключа
                            resultsRef3.child(AfinСategoryKey).setValue(testResult4);
                        }
                    }
                });
                break;
            case "Тест по шифру Гаммирования":
                reference.child("Cryptography").child("Lesson 5: The XOR Cipher").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        } else {
                            s5 = String.valueOf(task.getResult().getValue());
                            String[] s6 = s5.split(",");
                            gamma = Integer.parseInt(String.valueOf(s6[0].charAt(s6[0].length() - 1)));
                            gamma1 = AllQuestion - gamma;
                        }
                        if (gamma != 0 && gamma1 != 0){
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ResultFragment(gamma, gamma1, AllQuestion, cezar, atbash, vigener, afin, gamma, rsa, category, title)).commit();
                            DatabaseReference resultsRef4 = db.child("Result/" + "Cryptography/" + "Lesson 5: The XOR Cipher");
                            String userId4 = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            // Создайте объект TestResult (предполагается, что у вас есть класс TestResult)
                            TestResult testResult5 = new TestResult(gamma, AllQuestion - gamma);
                            // Задайте уникальный ключ для вашей категории (например, "caesarCategory")
                            String GammaСategoryKey = userId4;
                            // Обновите значения для этого ключа
                            resultsRef4.child(GammaСategoryKey).setValue(testResult5);
                        }
                    }
                });
                break;
            case "Тест по шифру RSA":
                reference.child("Cryptography").child("Lesson 6: The RSA Cipher").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        } else {
                            s6 = String.valueOf(task.getResult().getValue());
                            String[] s7 = s6.split(",");
                            rsa = Integer.parseInt(String.valueOf(s7[0].charAt(s7[0].length() - 1)));
                            rsa1 = AllQuestion - rsa;
                        }
                        if (rsa != 0 && rsa1 != 0){
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ResultFragment(rsa, rsa1, AllQuestion, cezar, atbash, vigener, afin, gamma, rsa, category, title)).commit();
                            DatabaseReference resultsRef5 = db.child("Result/" + "Cryptography/" + "Lesson 6: The RSA Cipher");
                            String userId5 = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            // Создайте объект TestResult (предполагается, что у вас есть класс TestResult)
                            TestResult testResult6 = new TestResult(rsa, AllQuestion - rsa);
                            // Задайте уникальный ключ для вашей категории (например, "caesarCategory")
                            String RsaСategoryKey = userId5;
                            // Обновите значения для этого ключа
                            resultsRef5.child(RsaСategoryKey).setValue(testResult6);
                        }
                    }
                });
                break;
            case "Тест к уроку Как устроен интернет":
                reference.child("The basics of security and anonymity on the web").child("Lesson 1: How the Internet works").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Ошибка в получении данных", task.getException());
                        } else {
                            s7 = String.valueOf(task.getResult().getValue());
                            String[] s8 = s7.split(",");
                            mod1_less1_correct = Integer.parseInt(String.valueOf(s8[0].charAt(s8[0].length() - 1)));
                            mod1_less1_incorrect = AllQuestion - mod1_less1_correct;


                            // Проверка значений cezar и cezar1 выполняется после присвоения
                            if (mod1_less1_correct != 0 && mod1_less1_incorrect != 0) {
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ResultFragment(mod1_less1_correct, mod1_less1_incorrect, AllQuestion, cezar, atbash, vigener, afin, gamma, rsa, category, title)).commit();
                                DatabaseReference resultsRef_mod1_less1 = db.child("Result/" + "The basics of security and anonymity on the web/" + "Lesson 1: How the Internet works");
                                TestResult testResult_mod1_less1 = new TestResult(mod1_less1_correct, mod1_less1_incorrect);
                                String Key_mod1_less1 = userId;
                                resultsRef_mod1_less1.child(Key_mod1_less1).setValue(testResult_mod1_less1);
                            }
                        }
                    }
                });
                break;
            case "Тест к уроку Основы анонимности в сети":
                reference.child("The basics of security and anonymity on the web").child("Lesson 2: The Basics of Online Anonymity").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Ошибка в получении данных", task.getException());
                        } else {
                            s8 = String.valueOf(task.getResult().getValue());
                            String[] s9 = s8.split(",");
                            mod1_less2_correct = Integer.parseInt(String.valueOf(s9[0].charAt(s9[0].length() - 1)));
                            mod1_less2_incorrect = AllQuestion - mod1_less2_correct;


                            // Проверка значений cezar и cezar1 выполняется после присвоения
                            if (mod1_less2_correct != 0 && mod1_less2_incorrect != 0) {
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ResultFragment(mod1_less2_correct, mod1_less2_incorrect, AllQuestion, cezar, atbash, vigener, afin, gamma, rsa, category, title)).commit();
                                DatabaseReference resultsRef_mod1_less2 = db.child("Result/" + "The basics of security and anonymity on the web/" + "Lesson 2: The Basics of Online Anonymity");
                                TestResult testResult_mod1_less2 = new TestResult(mod1_less2_correct, mod1_less2_incorrect);
                                String Key_mod1_less2 = userId;
                                resultsRef_mod1_less2.child(Key_mod1_less2).setValue(testResult_mod1_less2);
                            }
                        }
                    }
                });
                break;
            case "Тест к уроку OSINT и Социальная инженерия":
                reference.child("The basics of security and anonymity on the web").child("Lesson 3: OSINT and Social Engineering").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Ошибка в получении данных", task.getException());
                        } else {
                            s9 = String.valueOf(task.getResult().getValue());
                            String[] s10 = s9.split(",");
                            mod1_less3_correct = Integer.parseInt(String.valueOf(s10[0].charAt(s10[0].length() - 1)));
                            mod1_less3_incorrect = AllQuestion - mod1_less3_correct;


                            // Проверка значений cezar и cezar1 выполняется после присвоения
                            if (mod1_less3_correct != 0 && mod1_less3_incorrect != 0) {
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ResultFragment(mod1_less3_correct, mod1_less3_incorrect, AllQuestion, cezar, atbash, vigener, afin, gamma, rsa, category, title)).commit();
                                DatabaseReference resultsRef_mod1_less3 = db.child("Result/" + "The basics of security and anonymity on the web/" + "Lesson 3: OSINT and Social Engineering");
                                TestResult testResult_mod1_less3 = new TestResult(mod1_less3_correct, mod1_less3_incorrect);
                                String Key_mod1_less3 = userId;
                                resultsRef_mod1_less3.child(Key_mod1_less3).setValue(testResult_mod1_less3);
                            }
                        }
                    }
                });
                break;
        }
        LoadQuestion();
        EnableOption();
        ClearOption();
        binding.nextBtn.setOnClickListener(v->{
            position++;
            // Увеличиваем текущую позицию
            currentPosition++;

            // Обновляем прогресс-бар
            updateProgressBar();
            LoadQuestion();
            EnableOption();
            ClearOption();
            checkNext();
        } );
        return binding.getRoot();

    }
    private void updateProgressBar() {
        // Обновляем значение прогресс-бара
        progressBar.setProgress(currentPosition);

        // Проверяем, достигнут ли конец теста
        if (currentPosition == totalQuestions) {
            // Все вопросы пройдены, можно закрыть или перейти к другому экрану
        }
    }

    private void checkNext() {
        if (position == AllQuestion){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ResultFragment(right, wrong, AllQuestion, cezar, atbash, vigener, afin, gamma, rsa, category, title)).commit();
            list.clear();
            position = 0;

        }
    }

    private void ClearOption() {
        binding.option1Con.setBackgroundResource(R.drawable.search_bkg_4);
        binding.option2Con.setBackgroundResource(R.drawable.search_bkg_4);
        binding.option3Con.setBackgroundResource(R.drawable.search_bkg_4);
        binding.option4Con.setBackgroundResource(R.drawable.search_bkg_4);
        binding.nextBtn.setBackgroundResource(R.drawable.search_bkg_4);

    }


    private void EnableOption() {
        binding.option1Con.setEnabled(true);
        binding.option2Con.setEnabled(true);
        binding.option3Con.setEnabled(true);
        binding.option4Con.setEnabled(true);
        binding.nextBtn.setEnabled(false);
    }

    private void LoadQuestion() {
        switch (title) {
            case "Тест по шифру Цезаря":
                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    list.add(new QuizModel("1. Какой римский император дал название шифру, который основан на сдвиге алфавита?", "Наполеон", "Гай Юлий Цезарь", "Александр Македонский", "Юлий Цезарь", "Гай Юлий Цезарь"));
                    list.add(new QuizModel("2. Что представляет собой ключ шифра Цезаря?", "Количество повторений", "Число шагов сдвига алфавита", "Случайный текст", "Длина сообщения", "Число шагов сдвига алфавита"));
                    list.add(new QuizModel("3. Сколько возможных вариантов ключей шифра Цезаря из-за использования алфавита?", "10", "26", "50", "100", "26"));
                    list.add(new QuizModel("4. Какой принцип замены символов используется в шифре Цезаря?", "Шифрование", "Дешифрование", "Сдвиг алфавита", "Инвертирование", "Сдвиг алфавита"));
                    list.add(new QuizModel("5. Как изменится буква \"А\" при шифровании шифром Цезаря с ключом 3?", "\"Г\"", " \"В\"", "\"Д\"", "\"Б\"", "\"Г\""));
                    list.add(new QuizModel("6. Почему шифр Цезаря считается легким для взлома?", "Использует сложные математические операции", " Имеет слишком большое количество ключей", "Ограниченное количество вариантов ключей", "Требует специальных устройств", "Ограниченное количество вариантов ключей"));
                    list.add(new QuizModel("7. В каких областях часто используется шифр Цезаря?", "Банковское дело", "Медицина", "Обучение шифрованию", "Транспорт", "Обучение шифрованию"));
                    list.add(new QuizModel("8. Какой принцип защиты имеет шифр Цезаря?", "Сильная криптографическая защита", "Двойное шифрование", "Низкая степень защиты", "Защита с помощью биометрии", "Низкая степень защиты"));
                    list.add(new QuizModel("9. Каким будет результат шифрования слова: \"Привет\" с ключом 3 ?", "Тулезх", "Фчйлпо", "Цшймщф", "Щщйгнъ", "Тулезх"));
                    list.add(new QuizModel("10. Каким будет результат расшифровки словосочетания: \"Ъкцт Шжйвтб\" с ключом: 2 ?", "Ключ Атбаша", "Айти отделы", "Шифр Цезаря", "Шифр Энигма", "Шифр Цезаря"));
                    optionCheckUp();
                    cezar = right;
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference resultsRef = db.child("Result/" + "Cryptography/" + "Lesson 1: The Caesar Cipher");
                    // Создайте объект TestResult (предполагается, что у вас есть класс TestResult)
                    TestResult testResult1 = new TestResult(cezar, AllQuestion - cezar);
                    // Задайте уникальный ключ для вашей категории (например, "caesarCategory")
                    String CaesarcategoryKey = userId;
                    // Обновите значения для этого ключа
                    resultsRef.child(CaesarcategoryKey).setValue(testResult1);
                    break;


            case "Тест по шифру Атбаш":
                list.add(new QuizModel("1. В чем заключается принцип шифрования Атбаш?", "Замена каждой буквы на противоположную по алфавиту", "Замена каждой буквы на следующую по алфавиту", "Замена каждой буквы на случайную букву", "Замена каждой буквы на букву из другого алфавита", "Замена каждой буквы на противоположную по алфавиту"));
                list.add(new QuizModel("2. Какими преимуществами обладает шифр Атбаш?", "Простота использования", "Высокая стойкость к взлому", "Подходит для шифрования больших объемов данных", " Не требует знания ключа для расшифровки", "Простота использования"));
                list.add(new QuizModel("3. Какими недостатками обладает шифр Атбаш?", "Низкая стойкость к взлому", "Не подходит для шифрования больших объемов данных", "Требует знания ключа для расшифровки", "Невозможность автоматизированного шифрования/расшифрования", "Не подходит для шифрования больших объемов данных"));
                list.add(new QuizModel("4. В каких исторических источниках встречались примеры использования шифра Атбаш?", "В египетских иероглифах", "В античных греческих текстах", "В средневековых манускриптах", "В Библии", "В Библии"));
                list.add(new QuizModel("5. Как можно расшифровать текст, зашифрованный с помощью шифра Атбаш?", "Подбором ключа", "С помощью частотного анализа", "С помощью компьютера", "Все перечисленные методы", "Все перечисленные методы"));
                list.add(new QuizModel("6. Где в современном мире может применяться шифр Атбаш?", "В военных коммуникациях", "В дипломатической переписке", "В компьютерных сетях", "В образовательных целях", "В образовательных целях"));
                list.add(new QuizModel("7. Каким образом шифр Атбаш может быть использован в играх и головоломках?", "Для создания секретных сообщений", "Для защиты информации от посторонних глаз", "Для проверки логического мышления", "Все перечисленные методы", "Все перечисленные методы"));
                list.add(new QuizModel("8. Какую роль шифр Атбаш играет в истории криптографии?", "Является одним из первых известных методов шифрования", "Заложил основы для развития более сложных шифров", "Используется в современных криптографических системах", "Не имеет существенного значения", "Является одним из первых известных методов шифрования"));
                list.add(new QuizModel("9. Каким будет результат шифрования слова: \"Здравствуйте\" ?", "Чыояэнмэлхмъ", "Мйфйщйщйджзк", "Нйгщщйщйиора", "Ойдйщйщйфйса", "Чыояэнмэлхмъ"));
                list.add(new QuizModel("10. Каким будет результат расшифровки словосочетания: \"Жцко Ямюяж\" ?", "Ключ Шифра", "Коды Шифра", "Шаги Шифра", "Шифр Атбаш", "Шифр Атбаш"));
                optionCheckUp();
                atbash = right;
                db = FirebaseDatabase.getInstance().getReference();
                DatabaseReference resultsRef1 = db.child("Result/" + "Cryptography/" + "Lesson 2: The Atbash Cipher");
                String userId1 = FirebaseAuth.getInstance().getCurrentUser().getUid();
                // Создайте объект TestResult (предполагается, что у вас есть класс TestResult)
                TestResult testResult2 = new TestResult(atbash, AllQuestion - atbash);
                // Задайте уникальный ключ для вашей категории (например, "caesarCategory")
                String AtbashcategoryKey = userId1;
                // Обновите значения для этого ключа
                resultsRef1.child(AtbashcategoryKey).setValue(testResult2);
                break;

            case "Тест по шифру Виженера":
                list.add(new QuizModel("1. Кто на самом деле является изобретателем шифра Виженера?", "Блез де Виженер", "Джованни Баттиста Белласо", "Леон Баттиста Альберти", "Йоханнес Тритемиус", "Джованни Баттиста Белласо"));
                list.add(new QuizModel("2. Как шифр Виженера связан с шифром Цезаря?", "Шифр Виженера использует несколько шифров Цезаря с разным ключом", "Шифр Виженера - это модификация шифра Цезаря с более сложным алгоритмом", "Шифр Цезаря - это частный случай шифра Виженера с ключом длиной 1", "Шифр Виженера и шифр Цезаря не связаны между собой", "Шифр Виженера использует несколько шифров Цезаря с разным ключом"));
                list.add(new QuizModel("3. Как автоключ меняет принцип работы шифра Виженера?", "Автоключ делает шифрование более стойким к криптоанализу", " Автоключ позволяет использовать более короткие ключи", "Автоключ упрощает процесс шифрования и расшифрования", "Автоключ делает шифр Виженера похожим на шифр Цезаря", "Автоключ делает шифрование более стойким к криптоанализу"));
                list.add(new QuizModel("4. Какие существуют варианты реализации шифра Виженера?", "С использованием таблицы Виженера", "С использованием алгоритма Виженера", "С использованием программного обеспечения", "Все перечисленные варианты", "Все перечисленные варианты"));
                list.add(new QuizModel("5. Какие методы криптоанализа могут быть применены к шифру Виженера?", "Частотный анализ", "Анализ совпадений", "Криптоанализ по тексту-открытому", "Все перечисленные варианты", "Все перечисленные варианты"));
                list.add(new QuizModel("6. Как длина ключа влияет на стойкость шифра Виженера?", " Чем длиннее ключ, тем более стойкий шифр", "Длина ключа не влияет на стойкость шифра", "Чем короче ключ, тем более стойкий шифр", "Стойкость шифра не зависит от длины ключа", "Чем длиннее ключ, тем более стойкий шифр"));
                list.add(new QuizModel("7. Где и как шифр Виженера использовался в прошлом?", "В дипломатической переписке", "В военных шифрах", "В коммерческой тайне", "Во всех перечисленных областях", "Во всех перечисленных областях"));
                list.add(new QuizModel("8. Почему шифр Виженера утратил свою актуальность?", "Были разработаны более стойкие шифры", " Расшифровка шифра Виженера стала проще", "Шифр Виженера стал слишком сложным для использования", " Шифр Виженера был запрещен", "Были разработаны более стойкие шифры"));
                list.add(new QuizModel("9. Какую длину должен иметь ключ для шифра Виженера, чтобы зашифровать сообщение: \"Встретимся в 10 утра\" ?", "4", "5", "6", "7", "6"));
                list.add(new QuizModel("10. Каким будет результат расшифровки словосочетания:\"Шифр Виженера\" с ключом \"ключ\" ?", "Гфтз Мфеьшроч", "Aшсф Мапитпьр", "Фйсп Афрольим", "Усал Зафмиорп", "Гфтз Мфеьшроч"));
                optionCheckUp();
                vigener = right;
                db = FirebaseDatabase.getInstance().getReference();
                DatabaseReference resultsRef2 = db.child("Result/" + "Cryptography/" + "Lesson 3: The Vigener Cipher");
                String userId2 = FirebaseAuth.getInstance().getCurrentUser().getUid();
                // Создайте объект TestResult (предполагается, что у вас есть класс TestResult)
                TestResult testResult3 = new TestResult(vigener, AllQuestion - vigener);
                // Задайте уникальный ключ для вашей категории (например, "caesarCategory")
                String VigenerСategoryKey = userId2;
                // Обновите значения для этого ключа
                resultsRef2.child(VigenerСategoryKey).setValue(testResult3);
                break;


            case "Тест по Афинному шифру":
                list.add(new QuizModel("1. Что такое аффинный шифр?", "Вид моноалфавитного шифра подстановки ", "Вид полиалфавитного шифра подстановки ", "Вид шифра перестановки", "Вид шифра замены ", "Вид моноалфавитного шифра подстановки"));
                list.add(new QuizModel("2. Какой основной принцип работы аффинного шифра?", "Использование одного числа в качестве ключа шифра", "Использование двух чисел в качестве ключей шифра", "Использование трех чисел в качестве ключей шифра", "Использование четырех чисел в качестве ключей шифра", "Использование двух чисел в качестве ключей шифра"));
                list.add(new QuizModel("3. Какие числа используются в аффинном шифре?", "Простые числа", "Составные числа", "Взаимно простые числа", "Четные числа", "Взаимно простые числа"));
                list.add(new QuizModel("4. Какие методы используются для разгадывания аффинного шифра?", " Аналитический и переборный", "Геометрический и алгебраический", "Статистический и вероятностный", "Дедуктивный и индуктивный", "Аналитический и переборный "));
                list.add(new QuizModel("5. Что такое \"модуль\" в контексте аффинного шифрования?", "Размер ключа шифрования", "Количество символов в алфавите", "Число символов в открытом тексте", "Число символов в зашифрованном тексте", "Количество символов в алфавите"));
                list.add(new QuizModel("6. Кто является создателем аффинного шифра?", "Алан Тьюринг", "Жюль Верн", "Неизвестен", "Брюс", "Неизвестен"));
                list.add(new QuizModel("7. В каких областях, помимо криптографии, используются принципы, схожие с аффинным шифрованием?", "В линейных конгруэнтных генераторах псевдослучайных чисел", "В компьютерной графике", "В машинном обучении", "Во всех перечисленных", "В линейных конгруэнтных генераторах псевдослучайных чисел"));
                list.add(new QuizModel("8. Какова основная причина, по которой аффинный шифр не рекомендуется использовать для защиты конфиденциальной информации?", " Легкость взлома", "Ограниченный набор символов", "Медленная скорость шифрования", "Несовместимость с современными компьютерами", "Легкость взлома"));
                list.add(new QuizModel("9. Каким будет результат расшифровки зашифрованного сообщения \"Чыояэнмэлхмъ\" с ключом a=3 и b=2, если используется русский алфавит?", "Здравствуйте", "Привет", "Добрый день", "Невозможно расшифровать", "Здравствуйте"));
                list.add(new QuizModel("10. Предположим, что мы хотим зашифровать сообщение \"Встретимся в 10 утра\" с помощью аффинного шифра, используя ключ a=3 и b=2. Каким будет зашифрованный символ для буквы \"с\"?", "д", "ф", "и", "н", "ф"));
                optionCheckUp();
                afin = right;
                db = FirebaseDatabase.getInstance().getReference();
                DatabaseReference resultsRef3 = db.child("Result/" + "Cryptography/" + "Lesson 4: Affine Cipher");
                String userId3 = FirebaseAuth.getInstance().getCurrentUser().getUid();
                // Создайте объект TestResult (предполагается, что у вас есть класс TestResult)
                TestResult testResult4 = new TestResult(afin, AllQuestion - afin);
                // Задайте уникальный ключ для вашей категории (например, "caesarCategory")
                String AfinСategoryKey = userId3;
                // Обновите значения для этого ключа
                resultsRef3.child(AfinСategoryKey).setValue(testResult4);
                break;

            case "Тест по шифру Гаммирования":
                list.add(new QuizModel("1. Что такое шифр гаммирования XOR?", "Симметричный метод шифрования", "Асимметричный метод шифрования", "Метод шифрования с открытым ключом", "Метод шифрования с закрытым ключом", "Симметричный метод шифрования"));
                list.add(new QuizModel("2. Какие три ключевых свойства обладает гамма в криптографии?", "Случайность, длина, секретность", "Предсказуемость, длина, открытость", "Случайность, короткость, секретность", "Предсказуемость, короткость, открытость", "Случайность, длина, секретность"));
                list.add(new QuizModel("3. Каким должно быть минимальное соотношение между длиной гаммы и длиной открытого текста?", "Не меньше открытого текста", "Равняться открытому тексту", "Вдвое больше открытого текста", "Нет ограничений", "Не меньше открытого текста"));
                list.add(new QuizModel("4. На каком принципе основана теоретическая стойкость шифра XOR?", "Невозможность предсказать гамму", " Секретность гаммы", "Полное покрытие открытого текста гаммой", "Все перечисленные принципы", "Все перечисленные принципы"));
                list.add(new QuizModel("5. В каких устаревших протоколах шифрования Wi-Fi применялся шифр XOR?", "WPA", "WPA2", "WEP", "WPA3", "WEP"));
                list.add(new QuizModel("6. Для каких целей, помимо шифрования Wi-Fi, может использоваться шифр XOR?", "В виртуальных частных сетях (VPN)", "В военных системах связи", "В обоих вариантах", " Ни в одном из вариантов", "В обоих вариантах"));
                list.add(new QuizModel("7. Почему шифры AES и RSA считаются более стойкими, чем XOR, для защиты конфиденциальной информации?", "Они основаны на более сложных математических операциях", "Они имеют более длинные ключи шифрования", "Они менее подвержены атакам криптоанализа", "Все перечисленные причины", "Все перечисленные причины"));
                list.add(new QuizModel("8. Какие преимущества имеет шифр XOR по сравнению с шифрами, основанными на замене символов (например, шифр Цезаря)?", "Более высокая стойкость к вскрытию", "Простота реализации", "Устойчивость к ошибкам передачи", "Все перечисленные преимущества", "Все перечисленные преимущества"));
                list.add(new QuizModel("9. Каким будет результат расшифровки зашифрованного сообщения \"ФЫЩЦЩЪЭЙЖ\" с ключом \"ПРИВЕТ\", если используется русский алфавит?", "Я люблю тебя", "Приветствую", "Завтра в 10", "Невозможно расшифровать", "Приветствую"));
                list.add(new QuizModel("10. Предположим, что мы хотим зашифровать сообщение \"СЕКРЕТНОЕ СООБЩЕНИЕ\" с помощью шифра Виженера, используя ключ \"ШЛЮЗ\". Каким будет зашифрованный текст?", "ЩНЛЪЦЪЦЧЪЭЩЪЭЩЪ", "ТЩЪЪЦЪЭЩЪЭЩЪЧЩЪ", "ЪЦЪЭЩЪЩЪЭЩЪЪЦЪЫ", "Невозможно зашифровать", "ТЩЪЪЦЪЭЩЪЭЩЪЧЩЪ"));
                optionCheckUp();
                gamma = right;
                db = FirebaseDatabase.getInstance().getReference();
                DatabaseReference resultsRef4 = db.child("Result/" + "Cryptography/" + "Lesson 5: The XOR Cipher");
                String userId4 = FirebaseAuth.getInstance().getCurrentUser().getUid();
                // Создайте объект TestResult (предполагается, что у вас есть класс TestResult)
                TestResult testResult5 = new TestResult(gamma, AllQuestion - gamma);
                // Задайте уникальный ключ для вашей категории (например, "caesarCategory")
                String GammaСategoryKey = userId4;
                // Обновите значения для этого ключа
                resultsRef4.child(GammaСategoryKey).setValue(testResult5);
                break;

            case "Тест по шифру RSA":
                list.add(new QuizModel("1. Что означает асимметричность в алгоритме RSA?", "Используются два одинаковых ключа", "Используются два разных ключа", "Используется только один ключ", "Используется ключ и пароль", "Используются два разных ключа"));
                list.add(new QuizModel("2. Что такое открытый ключ в RSA?", "Секретный ключ, который хранится в секрете от всех", "Ключ, который используется для шифрования сообщений", "Ключ, который используется для расшифрования сообщений", "Ключ, который может быть предоставлен всем", "Ключ, который может быть предоставлен всем"));
                list.add(new QuizModel("3. Что такое закрытый ключ в RSA?", "Открытый ключ, который может быть предоставлен всем", "Секретный ключ, который хранится в секрете от всех", "Ключ, который используется для шифрования сообщений", "Ключ, который используется для расшифрования сообщений", "Секретный ключ, который хранится в секрете от всех"));
                list.add(new QuizModel("4. На чем основана безопасность RSA?", "На сложности факторизации больших простых чисел", "На секретности ключей", "На использовании случайных чисел", "На всех перечисленных выше факторах", "На сложности факторизации больших простых чисел"));
                list.add(new QuizModel("5. Какая проблема является эквивалентной взлому RSA?", "Проблема дискретного логарифмирования", "Проблема факторизации", "Задача о рюкзаке", "Проблема скрытого подмножества", "Проблема факторизации"));
                list.add(new QuizModel("6. Какие преимущества имеет RSA?", "Прост в реализации", "Не требует сложных математических вычислений", "Обеспечивает высокую стойкость к вскрытию", " Все перечисленные преимущества", "Все перечисленные преимущества"));
                list.add(new QuizModel("7. Какими атаками уязвим RSA?", "Атаки с использованием известного открытого текста", "Атаки с использованием адаптивного открытого текста", "Атаки временных каналов", "Все перечисленные атаки", "Все перечисленные атаки"));
                list.add(new QuizModel("8. Как можно повысить стойкость RSA к атакам?", "Использовать более длинные ключи", "Обеспечить надежное хранение секретных ключей", "Использовать криптографически стойкие генераторы случайных чисел", "Все перечисленные методы", "Все перечисленные методы"));
                list.add(new QuizModel("9. Каким будет результат расшифровки зашифрованного сообщения \"ФЫЩЦЩЪЭЙЖ\" с ключом RSA \"ПРИВЕТ\", если используется русский алфавит?", "Я люблю тебя", "Приветствую", "Завтра в 10", "Невозможно расшифровать", "Невозможно расшифровать"));
                list.add(new QuizModel("10. Предположим, что мы хотим зашифровать сообщение \"СЕКРЕТНОЕ СООБЩЕНИЕ\" с помощью шифра RSA, используя ключ \"ШЛЮЗ\". Каким будет зашифрованный текст?", "ЩНЛЪЦЪЦЧЪЭЩЪЭЩЪ", "ТЩЪЪЦЪЭЩЪЭЩЪЧЩЪ", "ЪЦЪЭЩЪЩЪЭЩЪЪЦЪЫ", "Невозможно зашифровать", "Невозможно зашифровать"));
                optionCheckUp();
                rsa = right;
                db = FirebaseDatabase.getInstance().getReference();
                DatabaseReference resultsRef5 = db.child("Result/" + "Cryptography/" + "Lesson 6: The RSA Cipher");
                String userId5 = FirebaseAuth.getInstance().getCurrentUser().getUid();
                // Создайте объект TestResult (предполагается, что у вас есть класс TestResult)
                TestResult testResult6 = new TestResult(rsa, AllQuestion - rsa);
                // Задайте уникальный ключ для вашей категории (например, "caesarCategory")
                String RsaСategoryKey = userId5;
                // Обновите значения для этого ключа
                resultsRef5.child(RsaСategoryKey).setValue(testResult6);
                break;
            case "Тест к уроку Как устроен интернет":
                list.add(new QuizModel("1. Что такое DNS-сервер?", "Устройство, которое физически соединяет компьютеры в сеть", "Программа, преобразующая доменные имена (например, google.com) в IP-адреса", "Протокол передачи данных в сети Интернет", "Вид интернет-браузера, специализирующийся на поиске информации", "Программа, преобразующая доменные имена (например, google.com) в IP-адреса"));
                list.add(new QuizModel("2. Какое максимальное число различных IP-адресов может быть в сети IPv4? ", "256", "65 536", "4 294 967 296", "Бесконечное количество", "4 294 967 296"));
                list.add(new QuizModel("3. Что такое веб-архив?", "Программа для создания резервных копий файлов на компьютере", "Систематизированное хранилище информации о всех веб-страницах в Интернете", "Протокол для безопасной передачи данных", "Вид интернет-браузера, специализирующийся на работе с архивными данными", "Систематизированное хранилище информации о всех веб-страницах в Интернете"));
                list.add(new QuizModel("4. Какая часть IP-адреса определяет сеть, а какая - конкретное устройство в этой сети?", "Первые два числа - сеть, последние два - устройство", "Первые три числа - сеть, последнее число - устройство", "Все числа определяют устройство", "Все числа определяют сеть", "Первые три числа - сеть, последнее число - устройство"));
                list.add(new QuizModel("5. Из скольких частей (октетов) состоит IP-адрес версии IPv4?", "2", "3", "4", "8", "4"));
                list.add(new QuizModel("6. Что означает аббревиатура IP?", "Internet Protocol (Интернет-протокол)", "Internet Page (Интернет-страница)", "Internet Provider (Поставщик интернет-услуг)", "Information Processing (Обработка информации)", "Internet Protocol (Интернет-протокол)"));
                list.add(new QuizModel("7. Какая основная функция DNS-сервера?", "Хранение веб-страниц", "Передача данных между компьютерами", "Преобразование доменных имен в IP-адреса", "Защита от вирусов", "Преобразование доменных имен в IP-адреса"));
                list.add(new QuizModel("8. Какое преимущество имеет протокол IPv6 перед IPv4?", "IPv6 позволяет использовать более короткие адреса", "IPv6 обеспечивает более высокую скорость передачи данных", "IPv6 позволяет подключить гораздо больше устройств к Интернету", "IPv6 используется только для мобильных устройств", "IPv6 позволяет подключить гораздо больше устройств к Интернету"));
                list.add(new QuizModel("9. Какая технология используется веб-архивом для создания копий сайтов?", "Web scraper", "Web crawler", "Web spider", "Web indexer", "Web crawler"));
                list.add(new QuizModel("10.  Что такое \"веб-паук\" (web crawler)?", "Программа, которая собирает и анализирует данные из Интернета", "Скрипт, который обходит сайты и проверяет, изменились ли они", "Поисковый робот, индексирующий веб-страницы", "Все вышеперечисленное", " Все вышеперечисленное"));
                optionCheckUp();
                mod1_less1_correct = right;
                db = FirebaseDatabase.getInstance().getReference();
                DatabaseReference resultsRef_mod1_less1 = db.child("Result/" + "The basics of security and anonymity on the web/" + "Lesson 1: How the Internet works");
                String userId_mod1_less1 = FirebaseAuth.getInstance().getCurrentUser().getUid();
                // Создайте объект TestResult (предполагается, что у вас есть класс TestResult)
                TestResult testResult_mod1_less1 = new TestResult(mod1_less1_correct, AllQuestion - mod1_less1_correct);
                // Задайте уникальный ключ для вашей категории (например, "caesarCategory")
                String Key_mod1_less1 = userId_mod1_less1;
                // Обновите значения для этого ключа
                resultsRef_mod1_less1.child(Key_mod1_less1).setValue(testResult_mod1_less1);
                break;
            case "Тест к уроку Основы анонимности в сети":
                list.add(new QuizModel("1. Что такое анонимность?", "Когда имя человека неизвестно", "Когда человек не хочет раскрывать информацию о себе", "Когда человек действует без указания своей личности", "Все вышеперечисленное", "Все вышеперечисленное"));
                list.add(new QuizModel("2. Зачем нужна анонимность в Интернете?", "Чтобы скрыть незаконную деятельность", "Чтобы защитить свою личную информацию", "Чтобы безнаказанно нарушать правила", "Чтобы избежать ответственности за свои действия", "Чтобы защитить свою личную информацию"));
                list.add(new QuizModel("3. Как можно собрать информацию о пользователе через открытые источники?", "Используя социальные сети и поиск по имени", "Взламывая базы данных и выкрадывая личную информацию", "Устанавливая малварь(Malware) на устройства", "Все вышеперечисленное", "Используя социальные сети и поиск по имени"));
                list.add(new QuizModel("4. Что такое \"пробив\" с помощью Telegram-бота?", "Инструмент поиска личных данных пользователей", "Функция в Telegram для отслеживания местоположения друзей", "Способ защиты личной информации в Telegram", "Сервис для взлома профилей в социальных сетях", "Инструмент поиска личных данных пользователей"));
                list.add(new QuizModel("5. Зачем злоумышленники собирают информацию о пользователях из открытых источников?", "Чтобы создать персонализированную рекламу", "Чтобы совершить кражу или вымогательство", "Чтобы организовать фальшивые голосования", "Все вышеперечисленное", "Все вышеперечисленное"));
                list.add(new QuizModel("6. Как можно обезопасить себя от цифрового мошенничества?", "Использовать сложные пароли и регулярно их менять", "Быть осторожным при открытии подозрительных ссылок и вложений", "Не предоставлять личную информацию на непроверенных сайтах", "Все вышеперечисленное", "Все вышеперечисленное"));
                list.add(new QuizModel("7. Что такое анонимность в сети?", "Возможность скрывать свою личность при использовании интернета", "Способ общаться с другими людьми, не раскрывая свою настоящую личность", "Технология, позволяющая шифровать данные", "Все вышеперечисленное", "Все вышеперечисленное"));
                list.add(new QuizModel("8. Зачем нужно удалять старые, ненужные аккаунты?", "Чтобы освободить место на устройстве", "Чтобы предотвратить взлом неиспользуемых аккаунтов", "Чтобы скрыть свою активность в прошлом", "Чтобы сэкономить на оплате аккаунтов", "Чтобы предотвратить взлом неиспользуемых аккаунтов"));
                list.add(new QuizModel("9. Как можно защитить банковскую карту при онлайн-покупках?", "Использовать кредитную карту вместо дебетовой", "Хранить карту в специальном защищённом кошельке", "Заводить отдельную карту для онлайн-покупок", "Все вышеперечисленное", "Заводить отдельную карту для онлайн-покупок"));
                list.add(new QuizModel("10. Какой основной принцип помогает сохранить анонимность в Интернете?", "Разделение личной и публичной информации", "Использование только подлинных данных", "Полный отказ от использования Интернета", "Размещение максимального количества личной информации", "Разделение личной и публичной информации"));
                optionCheckUp();
                mod1_less2_correct = right;
                db = FirebaseDatabase.getInstance().getReference();
                DatabaseReference resultsRef_mod1_less2 = db.child("Result/" + "The basics of security and anonymity on the web/" + "Lesson 2: The Basics of Online Anonymity");
                String userId_mod1_less2 = FirebaseAuth.getInstance().getCurrentUser().getUid();
                // Создайте объект TestResult (предполагается, что у вас есть класс TestResult)
                TestResult testResult_mod1_less2 = new TestResult(mod1_less2_correct, AllQuestion - mod1_less2_correct);
                // Задайте уникальный ключ для вашей категории (например, "caesarCategory")
                String Key_mod1_less2 = userId_mod1_less2;
                // Обновите значения для этого ключа
                resultsRef_mod1_less2.child(Key_mod1_less2).setValue(testResult_mod1_less2);
                break;

            case "Тест к уроку OSINT и Социальная инженерия":
                list.add(new QuizModel("1. Что такое OSINT?", "Сбор данных из закрытых источников", "Сбор данных из социальных сетей", "Сбор данных из открытых источников", "Сбор данных с помощью взлома", "Сбор данных из открытых источников"));
                list.add(new QuizModel("2. Является ли сбор данных методами OSINT законным?", "Да, это абсолютно легальный способ сбора информации", "Нет, это незаконный сбор персональных данных", "Зависит от того, как используются полученные данные", "Это серая зона, существует правовая неопределенность", "Да, это абсолютно легальный способ сбора информации"));
                list.add(new QuizModel("3. Что такое социальная инженерия?", "Использование методов психологического манипулирования", "Создание фишинговых сайтов для кражи данных", "Взлом систем с помощью полученной информации", "Все вышеперечисленное", "Все вышеперечисленное"));
                list.add(new QuizModel("4. Что такое фишинг?", "Создание поддельных сайтов для кражи личных данных", "Отправка вредоносных вложений по электронной почте", "Использование социальной инженерии для обмана", "Все вышеперечисленное", "Все вышеперечисленное"));
                list.add(new QuizModel("5. Какие признаки могут указывать на фишинговый сайт?", "Неправильное доменное имя", "Отсутствие SSL-сертификата", "Наличие грамматических ошибок", "Все вышеперечисленное", "Все вышеперечисленное"));
                list.add(new QuizModel("6. Почему мошенники иногда регистрируют схожие доменные имена?", "Чтобы сбить с толку пользователей", "Чтобы обойти блокировки", "Чтобы получить лицензию на домен", "Чтобы создать впечатление легитимности", "Чтобы сбить с толку пользователей"));
                list.add(new QuizModel("7. Почему наличие ошибок в тексте может говорить о фишинговом сайте?", "Крупные компании нанимают профессиональных редакторов", "Ошибки повышают доверие пользователей", "Ошибки снижают стоимость создания сайта", "Ошибки помогают скрыть реальную личность владельца", "Крупные компании нанимают профессиональных редакторов"));
                list.add(new QuizModel("8. Как защититься от фишинга?", "Открывать все вложения в письмах", "Вводить свои данные на всех сайтах, которые об этом просят", "Переходить по любым ссылкам, пришедшим на почту", "Проверять адрес сайта, наличие SSL сертификата, искать грамматические ошибки", "Проверять адрес сайта, наличие SSL сертификата, искать грамматические ошибки"));
                list.add(new QuizModel("9. Какие данные чаще всего пытаются получить злоумышленники с помощью фишинга?", "Номера телефонов, пароли, данные банковских карт", "Информация о любимых фильмах и музыке", "Данные о геолокации", "Информация о родственниках и друзьях", "Номера телефонов, пароли, данные банковских карт"));
                list.add(new QuizModel("10. Почему важно быть осторожным при использовании открытых Wi-Fi сетей?", "Потому что они могут быть медленными", "Потому что через них злоумышленники могут перехватить ваши данные", "Потому что они могут быть платными", "Потому что они могут быть недоступны", "Потому что через них злоумышленники могут перехватить ваши данные"));
                optionCheckUp();
                mod1_less3_correct = right;
                db = FirebaseDatabase.getInstance().getReference();
                DatabaseReference resultsRef_mod1_less3 = db.child("Result/" + "The basics of security and anonymity on the web/" + "Lesson 3: OSINT and Social Engineering");
                String userId_mod1_less3 = FirebaseAuth.getInstance().getCurrentUser().getUid();
                // Создайте объект TestResult (предполагается, что у вас есть класс TestResult)
                TestResult testResult_mod1_less3 = new TestResult(mod1_less3_correct, AllQuestion - mod1_less3_correct);
                // Задайте уникальный ключ для вашей категории (например, "caesarCategory")
                String Key_mod1_less3 = userId_mod1_less3;
                // Обновите значения для этого ключа
                resultsRef_mod1_less3.child(Key_mod1_less3).setValue(testResult_mod1_less3);
                break;



                    /*case "Тест к уроку Основы анонимности в сети":
                list.add(new QuizModel("1. ", "", "", "", "", ""));
                list.add(new QuizModel("2. ", "", "", "", "", ""));
                list.add(new QuizModel("3. ", "", "", "", "", ""));
                list.add(new QuizModel("4. ", "", "", "", "", ""));
                list.add(new QuizModel("5. ", "", "", "", "", ""));
                list.add(new QuizModel("6. ", "", "", "", "", ""));
                list.add(new QuizModel("7. ", "", "", "", "", ""));
                list.add(new QuizModel("8. ", "", "", "", "", ""));
                list.add(new QuizModel("9. ", "", "", "", "", ""));
                list.add(new QuizModel("10. ", "", "", "", "", ""));*/

        }

        AllQuestion = 10;
        listSize = String.valueOf(AllQuestion);
        binding.totalQ.setText("/" + listSize);



        if (position != AllQuestion){
            positionNo = String.valueOf(position + 1);
            int currentProgress = progressBar.getProgress();
            progressBar.setProgress(currentProgress + 1);
            binding.qNo.setText(positionNo);
        }
        else {
            positionNo = String.valueOf(position);
            binding.qNo.setText(positionNo);
        }

        quizModel = list.get(position);
        answer = quizModel.getCorrectsAns();

        binding.questionCon.setText(quizModel.getQuestion());
        binding.option1Con.setText(quizModel.getOp1());
        binding.option2Con.setText(quizModel.getOp2());
        binding.option3Con.setText(quizModel.getOp3());
        binding.option4Con.setText(quizModel.getOp4());

        optionCheckUp();

    }
//Проверка ответов
    private void optionCheckUp() {


        binding.option1Con.setOnClickListener(v ->{
            if (Objects.equals(quizModel.getOp1(),quizModel.getCorrectsAns())){
                right++;
                binding.option1Con.setBackgroundResource(R.drawable.right_bg);
            }
            else {
                ShowRightAns();
                binding.option1Con.setBackgroundResource(R.drawable.wrong_bg);
            }

            DisableOption();
            binding.nextBtn.setBackgroundResource(R.drawable.right_bg);

        } );
        binding.option2Con.setOnClickListener(v ->{
            if (Objects.equals(quizModel.getOp2(),quizModel.getCorrectsAns())){
                right++;
                binding.option2Con.setBackgroundResource(R.drawable.right_bg);
            }
            else {
                ShowRightAns();
                binding.option2Con.setBackgroundResource(R.drawable.wrong_bg);
            }

            DisableOption();
            binding.nextBtn.setBackgroundResource(R.drawable.right_bg);
        } );

        binding.option3Con.setOnClickListener(v ->{
            if (Objects.equals(quizModel.getOp3(),quizModel.getCorrectsAns())){
                right++;
                binding.option3Con.setBackgroundResource(R.drawable.right_bg);
            }
            else {
                ShowRightAns();
                binding.option3Con.setBackgroundResource(R.drawable.wrong_bg);
            }

            DisableOption();
            binding.nextBtn.setBackgroundResource(R.drawable.right_bg);
        } );

        binding.option4Con.setOnClickListener(v ->{
            if (Objects.equals(quizModel.getOp4(),quizModel.getCorrectsAns())){
                right++;
                binding.option4Con.setBackgroundResource(R.drawable.right_bg);
            }
            else {
                ShowRightAns();
                binding.option4Con.setBackgroundResource(R.drawable.wrong_bg);
            }

            DisableOption();
            binding.nextBtn.setBackgroundResource(R.drawable.right_bg);
        } );
    }

//Скрытие опции
    private void DisableOption() {
        binding.option1Con.setEnabled(false);
        binding.option2Con.setEnabled(false);
        binding.option3Con.setEnabled(false);
        binding.option4Con.setEnabled(false);
        binding.nextBtn.setEnabled(true);
    }
//Показ правильных ответов
    private void ShowRightAns() {
        if (Objects.equals(quizModel.getOp1(),quizModel.getCorrectsAns())){
            binding.option1Con.setBackgroundResource(R.drawable.right_bg);
        }else if (Objects.equals(quizModel.getOp2(),quizModel.getCorrectsAns())){
            binding.option2Con.setBackgroundResource(R.drawable.right_bg);
        }else if (Objects.equals(quizModel.getOp3(),quizModel.getCorrectsAns())){
            binding.option3Con.setBackgroundResource(R.drawable.right_bg);
        }else if (Objects.equals(quizModel.getOp4(),quizModel.getCorrectsAns())){
            binding.option4Con.setBackgroundResource(R.drawable.right_bg);
        }
    }
}
