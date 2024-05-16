package com.example.androidmessenger.AddapterClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidmessenger.QuizFragment;
import com.example.androidmessenger.R;
import com.example.androidmessenger.SubFragment;
import com.example.androidmessenger.modelClass.HomeModel;
import com.example.androidmessenger.modelClass.SubModel;

import java.util.ArrayList;

public class SubAdapter extends RecyclerView.Adapter<SubAdapter.ViewHolder> {

    Context context;
    ArrayList<SubModel> list;


    public SubAdapter(Context context, ArrayList<SubModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SubAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    SubModel model = list.get(position);
    holder.title.setText(model.getTitle());
    holder.itemView.setOnClickListener(v -> {
        AppCompatActivity activity = (AppCompatActivity) v.getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new QuizFragment(model.getCategory(), model.getTitle())).addToBackStack(null).commit();
    });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
        }
    }
}
