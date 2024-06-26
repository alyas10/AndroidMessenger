package Chats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidmessenger.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Адаптер для отображения сообщений в RecyclerView.
 *
 * @author Алевтина Ильина
 * @version 1.0
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    /**
     * Константы для типов сообщений (левое и правое)
     */
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private Context mContext;
    private List <Chat> mChats;
    private String imageUrl;
    FirebaseUser firebaseUser;

    /**
     * Конструктор адаптера.
     *
     * @param mContext Контекст приложения.
     * @param mChats   Список чатов.
     * @param imageUrl URL изображения профиля.
     */
    public MessageAdapter(Context mContext, List<Chat> mChats, String imageUrl) {
        this.mContext = mContext;
        this.mChats = mChats;
        this.imageUrl = imageUrl;
    }
    /**
     * ViewHolder для отображения элементов списка.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }
    /**
     * Привязывает данные к элементу списка (ViewHolder) в RecyclerView.
     *
     * @param holder   ViewHolder, к которому привязываются данные.
     * @param position Позиция элемента в списке.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      Chat chat = mChats.get(position);
        // Устанавливаем текст сообщения
      holder.show_message.setText(chat.getMessage());
      if (imageUrl == null){
          // Устанавливаем изображение профиля
          holder.profile_image.setImageResource(R.drawable.baseline_account_circle_24);
      }else {
          Glide.with(mContext).load(imageUrl).into(holder.profile_image);
      }
      if(position == mChats.size()-1) {
          if(chat.isIsseen()){
              // Устанавливаем иконку прочтения сообщения
              holder.img_seen.setImageResource(R.drawable.baseline_beenhere_24);
          } else {
              holder.img_seen.setImageResource(R.drawable.baseline_check_24);
          }
      } else {
          holder.img_seen.setVisibility(View.GONE);
      }
    }
    /**
     * Возвращает количество элементов в списке чатов.
     *
     * @return Количество элементов в списке.
     */
    @Override
    public int getItemCount() {
        return mChats.size();
    }

    /**
     * ViewHolder для отображения элементов списка.
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView show_message;
        public CircleImageView profile_image;
        public ImageView img_seen;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
            img_seen = itemView.findViewById(R.id.img_seen);
        }
    }
    /**
     * Возвращает тип элемента списка (правый или левый) на основе позиции.
     *
     * @param position Позиция элемента в списке.
     * @return Тип элемента (MSG_TYPE_RIGHT или MSG_TYPE_LEFT).
     */
    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChats.get(position).getSender().equals(firebaseUser.getUid())){
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}
