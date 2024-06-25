package com.example.androidmessenger.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.androidmessenger.R;
/**
 * Фрагмент для отображения первой страницы после заставки.
 * @author Алевтина Ильина
 * @version 1.0
 */
public class WelcomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Инфлейтить разметку для этого фрагмента
        return inflater.inflate(R.layout.welcome, container, false);
    }
}
