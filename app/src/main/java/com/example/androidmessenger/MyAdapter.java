package com.example.androidmessenger;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Адаптер для RecyclerView, отображающий список элементов DataClass.
 *
 * @author Иван Минаев
 * @version 1.0
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private List<DataClass> dataList;

    public void setSearchList(List<DataClass> dataSearchList) {
        this.dataList = dataSearchList;
        notifyDataSetChanged();
    }

    public MyAdapter(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DataClass data = dataList.get(position);
        // Устанавливаем данные в элементы представления ViewHolder
        holder.recImage.setImageResource(data.getDataImage());
        holder.recTitle.setText(data.getDataTitle());

        // Устанавливаем звездочки
        for (int i = 0; i < holder.starImages.length; i++) {
            holder.starImages[i].setVisibility(i < data.getStarCount() ? View.VISIBLE : View.GONE);
        }

        holder.setOnItemClickListener(new MyViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int adapterPosition) {
                DataClass selectedItem = dataList.get(adapterPosition);
                // Открываем новую активность LessonsActivity с данными выбранного элемента
                Intent intent = new Intent(context, LessonsActivity.class);
                intent.putExtra("Title", selectedItem.getDataTitle());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView recImage;
        TextView recTitle;
        CardView recCard;
        ImageView[] starImages; // Массив для отображения звездочек
        private OnItemClickListener onItemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recImage = itemView.findViewById(R.id.recImage);
            recTitle = itemView.findViewById(R.id.recTitle);
            recCard = itemView.findViewById(R.id.recCard);

            // Инициализация массива для звездочек
            starImages = new ImageView[]{
                    itemView.findViewById(R.id.starImage1),
                    itemView.findViewById(R.id.starImage2),
                    itemView.findViewById(R.id.starImage3),
                    itemView.findViewById(R.id.starImage4),
                    itemView.findViewById(R.id.starImage5),
                    itemView.findViewById(R.id.starImage6)

            };

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(view, getAdapterPosition());
            }
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        public interface OnItemClickListener {
            void onItemClick(View view, int adapterPosition);
        }
    }
}