package com.example.androidmessenger.AddapterClass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidmessenger.OnboardingItem;
import com.example.androidmessenger.R;

import java.util.List;
/*
 * Адаптер для RecyclerView для перелистывания теории, отображающий элементы onboarding
 * @author Иван Минаев
 * @version 1.0
 */
public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder> {
    //Список элементов
    private List<OnboardingItem> onboardingItems;

    /*
     * Конструктор адаптера.
     * @param onboardingItems Список элементов onboarding.
     */
    public OnboardingAdapter(List<OnboardingItem> onboardingItems) {
        this.onboardingItems = onboardingItems;
    }

    @NonNull
    @Override
    /*
     * Создает новый ViewHolder для элемента onboarding.
     * @param parent ViewGroup, к которому будет добавлен ViewHolder.
     * @param viewType Тип элемента.
     * @return Новый OnboardingViewHolder.
     */
    public OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnboardingViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_onboarding, parent, false
                )
        );
    }

    @Override
    /*
     * Заполняет данные ViewHolder для элемента onboarding.
     * @param holder OnboardingViewHolder, который нужно заполнить данными.
     * @param position Позиция элемента в списке.
     */
    public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {
        holder.setOnboardingData(onboardingItems.get(position));
    }

    @Override
    /*
     * Возвращает количество элементов onboarding в списке.
     * @return Количество элементов.
     */
    public int getItemCount() {
        return onboardingItems.size();
    }

    /*
     * ViewHolder для элемента onboarding.
     */
    class OnboardingViewHolder extends RecyclerView.ViewHolder {
        private TextView detailDesc;
        private TextView detailTitle;
        private ImageView detailImage;

        /*
         * Конструктор ViewHolder.
         * @param itemView View, представляющая элемент onboarding.
         */
        public OnboardingViewHolder(@NonNull View itemView) {
            super(itemView);
            detailTitle = itemView.findViewById(R.id.detailTitle);
            detailImage = itemView.findViewById(R.id.detailImage);
            detailDesc = itemView.findViewById(R.id.detailDesc);
        }

        /*
         * Заполняет данные ViewHolder элементом onboarding.
         * @param onboardingItem Элемент onboarding.
         */
        void setOnboardingData(OnboardingItem onboardingItem) {
            detailTitle.setText(onboardingItem.getTitle());
            detailDesc.setText(onboardingItem.getDescription());

            // Проверяем, есть ли изображение в OnboardingItem
            if (onboardingItem.getImage() != 0) {
                detailImage.setVisibility(View.VISIBLE);
                detailImage.setImageResource(onboardingItem.getImage());
            } else {
                detailImage.setVisibility(View.GONE);
            }
        }
    }
}