package com.example.androidmessenger.AddapterClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidmessenger.R;
import com.example.androidmessenger.SubFragment;
import com.example.androidmessenger.modelClass.HomeModel;

import java.util.ArrayList;
/**
 * Адаптер для отображения элементов списка для вкладки Тестирование
 * @author Иван Минаев
 * @version 1.0.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    Context context;
    ArrayList<HomeModel> list;

    /**
     * Конструктор адаптера.
     *
     * @param context Контекст приложения.
     * @param list    Массив данных для отображения.
     */
    public HomeAdapter(Context context, ArrayList<HomeModel> list) {
        this.context = context;
        this.list = list;
    }

    /**
     * Создает новый ViewHolder и связывает его с макетом элемента списка.
     *
     * @param parent   Родительская ViewGroup.
     * @param viewType Тип элемента списка (не используется в данном случае).
     * @return Созданный ViewHolder.
     */
    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_item,parent,false);
        return new ViewHolder(view);
    }

    /**
     * Привязывает данные к ViewHolder и устанавливает обработчик нажатия на элемент списка.
     *
     * @param holder   ViewHolder, к которому привязываются данные.
     * @param position Позиция элемента в списке.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    HomeModel model = list.get(position);
    holder.title.setText(model.getTitle());
    holder.itemView.setOnClickListener(v -> {
        AppCompatActivity activity = (AppCompatActivity) v.getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SubFragment(model.getTitle())).addToBackStack(null).commit();
    });

    }

    /**
     * Возвращает общее количество элементов в списке.
     *
     * @return возвращает количество элементов.
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * ViewHolder для отображения элементов списка.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
        }
    }
}
