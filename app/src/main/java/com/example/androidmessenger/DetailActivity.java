package com.example.androidmessenger;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.androidmessenger.AddapterClass.OnboardingAdapter;
import com.example.androidmessenger.modelClass.OnboardingItem;
import com.example.androidmessenger.modelClass.SubModel;
import com.google.android.material.button.MaterialButton;
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
    int cezar,cezar1;

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

        layoutOnboardingIndicators = findViewById(R.id.layoutOnboardingIndicators);
        buttonOnboardingAction = findViewById(R.id.buttonOnboardingAction);

        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

        // Получаем данные, переданные из Intent
        Intent intent = getIntent();
        dataTitle = intent.getStringExtra("Title");
        switch (dataTitle) {
            case "Шифр Цезаря":
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
                        DatabaseReference resultsRef = db.child("Result/" + "CezarCategory");
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
            case "Шифр Атбаш":
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

                    }
                });
                break;
            default:
                // Обрабатываем остальные случаи
                break;
        }
    }

    private void setupOnboardingItems() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        OnboardingItem itemCaesar = new OnboardingItem();
        itemCaesar.setTitle("Шифр Цезаря");
        itemCaesar.setImage(R.drawable.cezar);
        itemCaesar.setDescription(getString(R.string.cezar1));

        OnboardingItem itemCaesar1 = new OnboardingItem();
        itemCaesar1.setTitle("Принцип работы");
        itemCaesar1.setImage(R.drawable.cezar);
        itemCaesar1.setDescription(getString(R.string.cezar2));

        OnboardingItem itemCaesar2 = new OnboardingItem();
        itemCaesar2.setTitle("Сложность");
        itemCaesar2.setImage(R.drawable.cezar);
        itemCaesar2.setDescription(getString(R.string.cezar3));

        OnboardingItem itemCaesar3 = new OnboardingItem();
        itemCaesar3.setTitle("Использование");
        itemCaesar3.setImage(R.drawable.cezar);
        itemCaesar3.setDescription(getString(R.string.cezar4));

        OnboardingItem itemCaesar4 = new OnboardingItem();
        itemCaesar4.setTitle(" Алгоритм работы");
        itemCaesar4.setImage(R.drawable.cezar);
        itemCaesar4.setDescription(getString(R.string.cezar6));

        OnboardingItem itemCaesar5 = new OnboardingItem();
        itemCaesar5.setTitle(" Алгоритм работы");
        itemCaesar5.setImage(R.drawable.cezar);
        itemCaesar5.setDescription(getString(R.string.cezar7));

        OnboardingItem itemCaesar6 = new OnboardingItem();
        itemCaesar6.setTitle(" Алгоритм работы");
        itemCaesar6.setImage(R.drawable.cezar);
        itemCaesar6.setDescription(getString(R.string.cezar8));

        OnboardingItem itemCaesar7 = new OnboardingItem();
        itemCaesar7.setTitle(" Пример зашифрования алгоритма");
        itemCaesar7.setImage(R.drawable.cezar);
        itemCaesar7.setDescription(getString(R.string.cezar9));

        OnboardingItem itemCaesar8 = new OnboardingItem();
        itemCaesar8.setTitle(" Пример расшифрования алгоритма");
        itemCaesar8.setImage(R.drawable.cezar);
        itemCaesar8.setDescription(getString(R.string.cezar10));

        OnboardingItem itemCaesar9 = new OnboardingItem();
        itemCaesar9.setTitle(" Вывод");
        itemCaesar9.setImage(R.drawable.cezar);
        itemCaesar9.setDescription(getString(R.string.cezar11));

        onboardingItems.add(itemCaesar);
        onboardingItems.add(itemCaesar1);
        onboardingItems.add(itemCaesar2);
        onboardingItems.add(itemCaesar3);
        onboardingItems.add(itemCaesar4);
        onboardingItems.add(itemCaesar5);
        onboardingItems.add(itemCaesar6);
        onboardingItems.add(itemCaesar7);
        onboardingItems.add(itemCaesar8);
        onboardingItems.add(itemCaesar9);


    
        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }
   //Загрузка нужных элементов
    private void setupOnboardingItems1() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        OnboardingItem itemAtbash = new OnboardingItem();
        itemAtbash.setTitle("Шифр Атбаш");
        itemAtbash.setImage(R.drawable.atbash);
        itemAtbash.setDescription("ADOASFSAOFSAOFNSAOFSNFSAFSAFLASNFASAC");

        OnboardingItem itemPlayfair = new OnboardingItem();
        itemPlayfair.setTitle("Шифр Плейфера");
        itemPlayfair.setImage(R.drawable.atbash);
        itemPlayfair.setDescription("ADOASFSAOFSAOFNSAOFSNFSAFSAFLASNFASAC");

        OnboardingItem itemHill = new OnboardingItem();
        itemHill.setTitle("Шифр Хилла");
        itemHill.setImage(R.drawable.atbash);
        itemHill.setDescription("ADOASFSAOFSAOFNSAOFSNFSAFSAFLASNFASAC");

        onboardingItems.add(itemAtbash);
        onboardingItems.add(itemPlayfair);
        onboardingItems.add(itemHill);

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }
    /*
 * Настраивает индикаторы onboarding.
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
