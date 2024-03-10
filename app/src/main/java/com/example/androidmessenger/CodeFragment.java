package com.example.androidmessenger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CodeFragment extends Fragment {
    public CodeFragment() {
        super(R.layout.activity_code);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CardView card1 = getView().findViewById(R.id.card1);
        CardView card2 = getView().findViewById(R.id.card2);
        CardView card3 = getView().findViewById(R.id.card3);
        card1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CeasesActivity.class);
                startActivity(intent);
            }

        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AtbashActivity.class);
                startActivity(intent);
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VigenerActivity.class);
                startActivity(intent);
            }
        });
    }
}