package com.example.androidmessenger;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidmessenger.databinding.FragmentQuizBinding;
import com.example.androidmessenger.modelClass.HomeModel;
import com.example.androidmessenger.modelClass.QuizModel;
import com.example.androidmessenger.modelClass.SubModel;

import java.util.ArrayList;
import java.util.Objects;

public class QuizFragment extends Fragment {
    int AllQuestion;
    String listSize;
    FragmentQuizBinding binding;
    ArrayList<QuizModel> list = new ArrayList<>();
    private int position = 0;
    int right = 0;
    int wrong = 0;
    private static String answer =null;
    String positionNo;
    QuizModel quizModel;
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
    public QuizFragment() {
        // Required empty public constructor
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
    public QuizFragment(String category, String title) {
        this.category = category;
        this.title = title;

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentQuizBinding.inflate(getLayoutInflater());
        preferences = getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        if (preferences.contains(CAESAR_RIGHT_ANSWERS_KEY) && preferences.contains(CAESAR_WRONG_ANSWERS_KEY)) {
            // Тест был пройден, отображаем результаты
            int right = preferences.getInt(CAESAR_RIGHT_ANSWERS_KEY, 0);
            int wrong = preferences.getInt(CAESAR_WRONG_ANSWERS_KEY, 0);

            if (right >= 0 || wrong >= 0)
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ResultFragment(right, wrong, AllQuestion, category, title)).commit();
        }
        else if (preferences.contains(VIGENERE_RIGHT_ANSWERS_KEY) && preferences.contains(VIGENERE_WRONG_ANSWERS_KEY)) {
            // Тест был пройден, отображаем результаты
            int right = preferences.getInt(VIGENERE_RIGHT_ANSWERS_KEY, 0);
            int wrong = preferences.getInt(VIGENERE_WRONG_ANSWERS_KEY, 0);

            if (right >= 0 || wrong >= 0)
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ResultFragment(right, wrong, AllQuestion, category, title)).commit();
        }
        else if (preferences.contains(ATBASH_RIGHT_ANSWERS_KEY) && preferences.contains(ATBASH_WRONG_ANSWERS_KEY)) {
            // Тест был пройден, отображаем результаты
            int right = preferences.getInt(ATBASH_RIGHT_ANSWERS_KEY, 0);
            int wrong = preferences.getInt(ATBASH_WRONG_ANSWERS_KEY, 0);

            if (right >= 0 || wrong >= 0)
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ResultFragment(right, wrong, AllQuestion, category, title)).commit();
        }
        else {
            // Тест не был пройден, начинаем тест
            LoadQuestion();
            EnableOption();
            ClearOption();
            binding.nextBtn.setOnClickListener(v -> {
                position++;
                LoadQuestion();
                EnableOption();
                ClearOption();
                checkNext();
            });
        }
        return binding.getRoot();
    }

    private void checkNext() {
        if (position == AllQuestion){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ResultFragment(right, wrong, AllQuestion, category, title)).commit();
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
                list.add(new QuizModel("1. Какой римский император дал название шифру, который основан на сдвиге алфавита?", "Наполеон", "Гай Юлий Цезарь", "Александр Македонский", "Юлий Цезарь", "Гай Юлий Цезарь"));
                list.add(new QuizModel("2. Что представляет собой ключ шифра Цезаря?", "Количество повторений", "Число шагов сдвига алфавита", "Случайный текст", "Длина сообщения", "Число шагов сдвига алфавита"));
                list.add(new QuizModel("3. Сколько возможных вариантов ключей шифра Цезаря из-за использования алфавита?", "10", "26", "50", "100", "26"));
                list.add(new QuizModel("4. Какой принцип замены символов используется в шифре Цезаря?", "Шифрование", "Дешифрование", "Сдвиг алфавита", "Инвертирование", "Сдвиг алфавита"));
                list.add(new QuizModel("5. Как изменится буква \"А\" при шифровании шифром Цезаря с ключом 3?", "\"Г\"", " \"В\"", "\"Д\"", "\"Б\"", "\"Г\""));
                list.add(new QuizModel("6. Почему шифр Цезаря считается легким для взлома?", "Использует сложные математические операции", " Имеет слишком большое количество ключей", "Ограниченное количество вариантов ключей", "Требует специальных устройств", "Ограниченное количество вариантов ключей"));
                list.add(new QuizModel("7. В каких областях часто используется шифр Цезаря?","Банковское дело", "Медицина", "Обучение шифрованию", "Транспорт", "Обучение шифрованию"));
                list.add(new QuizModel("8. Какой принцип защиты имеет шифр Цезаря?", "Сильная криптографическая защита",  "Двойное шифрование", "Низкая степень защиты", "Защита с помощью биометрии", "Низкая степень защиты"));
                list.add(new QuizModel("9. Каким будет результат шифрования слова: \"Привет\" с ключом 3 ?", "Тулезх", "Фчйлпо", "Цшймщф", "Щщйгнъ", "Тулезх"));
                list.add(new QuizModel("10. Каким будет результат расшифровки словосочетания: \"Ъкцт Шжйвтб\" с ключом: 2 ?" ,"Ключ Атбаша", "Айти отделы", "Шифр Цезаря", "Шифр Энигма", "Шифр Цезаря"));
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
                break;
        }

        AllQuestion = 10;
        listSize = String.valueOf(AllQuestion);
        binding.totalQ.setText("/" + listSize);



        if (position != AllQuestion){
            positionNo = String.valueOf(position + 1);
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
saveResults();
    }


    private void DisableOption() {
        binding.option1Con.setEnabled(false);
        binding.option2Con.setEnabled(false);
        binding.option3Con.setEnabled(false);
        binding.option4Con.setEnabled(false);
        binding.nextBtn.setEnabled(true);
    }

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