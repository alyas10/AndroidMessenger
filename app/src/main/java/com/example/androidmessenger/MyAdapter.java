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
public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private List<DataClass> dataList;


    /**
     * Устанавливает новый список данных для отображения и уведомляет адаптер об изменениях.
     *
     * @param dataSearchList Новый список данных.
     */
    public void setSearchList(List<DataClass> dataSearchList){
        this.dataList = dataSearchList;
        notifyDataSetChanged();
    }
    /**
     * Конструктор адаптера.
     *
     * @param context  Контекст приложения.
     * @param dataList Список данных для отображения.
     */
    public MyAdapter(Context context, List<DataClass> dataList){
        this.context = context;
        this.dataList = dataList;
    }
    /**
     * Создает новый ViewHolder для элемента списка.
     *
     * @param parent   Родительский ViewGroup.
     * @param viewType Тип элемента списка (не используется в данном случае).
     * @return Новый объект MyViewHolder.
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item, parent, false);
        return new MyViewHolder(view);
    }
    /**
     * Связывает данные с ViewHolder для отображения элемента списка.
     *
     * @param holder   ViewHolder для элемента списка.
     * @param position Позиция элемента списка.
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Устанавливаем данные в элементы представления ViewHolder
        holder.recImage.setImageResource(dataList.get(position).getDataImage());
        holder.recTitle.setText(dataList.get(position).getDataTitle());
        holder.recDesc.setText(dataList.get(position).getDataDesc());
        holder.recLang.setText(dataList.get(position).getDataLang());
        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                // Передаем данные выбранного элемента в DetailActivity
                intent.putExtra("Image", dataList.get(holder.getAdapterPosition()).getDataImage());
                intent.putExtra("Title", dataList.get(holder.getAdapterPosition()).getDataTitle());
                intent.putExtra("Desc", dataList.get(holder.getAdapterPosition()).getDataDesc());
                context.startActivity(intent);
            }
        });
    }
    /**
     * Возвращает количество элементов в списке данных.
     *
     * @return Количество элементов в списке данных.
     */
    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
/**
 * ViewHolder для элемента списка. Содержит ссылки на элементы представления элемента списка.
 */
class MyViewHolder extends RecyclerView.ViewHolder{
    ImageView recImage;
    TextView recTitle, recDesc, recLang;
    CardView recCard;
    /**
     * Конструктор ViewHolder. Инициализирует ссылки на элементы представления.
     *
     * @param itemView Представление элемента списка.
     */
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        recImage = itemView.findViewById(R.id.recImage);
        recTitle = itemView.findViewById(R.id.recTitle);
        recDesc = itemView.findViewById(R.id.recDesc);
        recLang = itemView.findViewById(R.id.recLang);
        recCard = itemView.findViewById(R.id.recCard);
    }
}