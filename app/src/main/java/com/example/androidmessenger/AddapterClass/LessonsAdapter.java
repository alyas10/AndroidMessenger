package com.example.androidmessenger.AddapterClass;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidmessenger.DetailActivity;
import com.example.androidmessenger.R;
import com.example.androidmessenger.modelClass.LessonModel;

import java.util.ArrayList;

public class LessonsAdapter extends RecyclerView.Adapter<LessonsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<LessonModel> lessonList;

    public LessonsAdapter(Context context, ArrayList<LessonModel> lessonList) {
        this.context = context;
        this.lessonList = lessonList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LessonModel lesson = lessonList.get(position);
        holder.tvLessonTitle.setText(lesson.getLessonTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Открываем DetailActivity с необходимыми данными
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("LessonTitle", lesson.getLessonTitle());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lessonList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLessonTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLessonTitle = itemView.findViewById(R.id.tvLessonTitle);
        }
    }
}