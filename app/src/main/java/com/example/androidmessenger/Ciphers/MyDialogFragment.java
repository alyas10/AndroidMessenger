package com.example.androidmessenger.Ciphers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.androidmessenger.R;

public class MyDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            String encryptedText = args.getString("inputText");
            String plainText = args.getString("outputText");
            int shift = args.getInt("shiftAmount");

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_layout, null);
            // Настройка TableLayout
            TableLayout tableLayout = dialogView.findViewById(R.id.tableLayout);
            // 1. Строка: Разбиение по буквам
            TableRow row1 = new TableRow(getActivity());
            assert plainText != null;
            for (char c : plainText.toCharArray()) {
                TextView cell = new TextView(getActivity());
                cell.setText(String.valueOf(c));
                row1.addView(cell);
            }
            tableLayout.addView(row1);

            // 2. Строка: Индексы букв
            TableRow row2 = new TableRow(getActivity());
            for (char c : plainText.toCharArray()) {
                TextView cell = new TextView(getActivity());
                cell.setText(String.valueOf((int) c)); // Преобразование символа в индекс
                row2.addView(cell);
            }
            tableLayout.addView(row2);

            // 3. Строка: Сдвиг (здесь нужно добавить логику получения сдвига)
            TableRow row3 = new TableRow(getActivity());
            for (char c : plainText.toCharArray()) {
                TextView cell = new TextView(getActivity());
                cell.setText(String.valueOf(shift));
                row3.addView(cell);
            }
            tableLayout.addView(row3);

            // 4. Строка: Новые индексы
            TableRow row4 = new TableRow(getActivity());
            for (char c : plainText.toCharArray()) {
                TextView cell = new TextView(getActivity());
                int newIndex = (int) c + shift;
                cell.setText(String.valueOf(newIndex));
                row4.addView(cell);
            }
            tableLayout.addView(row4);

            // 5. Строка: Зашифрованные буквы (из encryptedTextView)
            TableRow row5 = new TableRow(getActivity());
            for (char c : encryptedText.toCharArray()) {
                TextView cell = new TextView(getActivity());
                cell.setText(String.valueOf(c));
                row5.addView(cell);
            }
            tableLayout.addView(row5);

            // Показать диалоговое окно
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return alertDialog; // Верните созданный AlertDialog

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Заголовок вашего диалогового окна")
                    .setMessage("Сообщение в диалоговом окне")
                    .setPositiveButton("OK", (dialog, which) -> {
                        // Действие при нажатии кнопки OK
                    })
                    .setNegativeButton("Отмена", (dialog, which) -> {
                        // Действие при нажатии кнопки Отмена
                    });
            return builder.create();
        }
    }
}
