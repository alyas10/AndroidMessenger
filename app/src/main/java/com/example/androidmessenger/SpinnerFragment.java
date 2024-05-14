package com.example.androidmessenger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SpinnerFragment extends Fragment {
    public SpinnerFragment() {
        super(R.layout.activity_spinner);
    }
    private Spinner spinner;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        spinner = getView().findViewById(R.id.spinner);
        Button buttonGo = getView().findViewById(R.id.button_go);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.cipher_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранный объект
                String[] choose = getResources().getStringArray(R.array.cipher_array);
                Toast toast = Toast.makeText(getActivity(),
                        "Ваш выбор: " + choose[position], Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        buttonGo.setOnClickListener(v -> {
            int position = spinner.getSelectedItemPosition();
            Intent intent;
            switch (position) {
                case 0: // Атбаш
                    intent = new Intent(getActivity(), AtbashActivity.class);
                    startActivity(intent);
                    break;
                case 1: // Цезарь
                    intent = new Intent(getActivity(), CeasesActivity.class);
                    startActivity(intent);
                    break;
                case 2: // Виженер
                    intent = new Intent(getActivity(), VigenerActivity.class);
                    startActivity(intent);
                    break;
                case 3:
                    intent = new Intent(getActivity(), AffineCipherActivity.class);
                    startActivity(intent);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + position);
            }
        });
    }
}
