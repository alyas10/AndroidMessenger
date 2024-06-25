package Chats;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidmessenger.MessageActivity;
import com.example.androidmessenger.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Класс UserAdapter отвечает за управление списком пользователей в RecyclerView.
 * Он отображает информацию о пользователе, такую как имя пользователя, изображение профиля,
 * статус в сети и последнее сообщение.
 * @author Алевтина Ильина
 * @version 1.0
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mContext;
    private List <User> mUsers;

    private  boolean ischat;

    String theLastMessage;
    /**
     * Конструктор для класса UserAdapter.
     * @param mContext - контекст действия.
     * @param mUsers - список отображаемых пользователей.
     * @param ischat Указывает, находится ли пользователь в данный момент в чате.
     */
    public UserAdapter(Context mContext, List<User> mUsers, boolean ischat) {
        this.mContext = mContext;
        this.mUsers = mUsers;
        this.ischat = ischat;
    }

    /**
     * Вызывается, когда RecyclerView требуется новый ViewHolder данного типа для представления элемента.
     * @param parent - родительская группа ViewGroup, в которую будет добавлено новое представление.
     * @param viewType - Тип представления для нового View.
     * @return  новый ViewHolder, который содержит View данного типа.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item,parent,false);
        return new UserAdapter.ViewHolder(view);
    }

    /**
     * Вызывается RecyclerView для отображения данных в указанной позиции.
     * @param holder - ViewHolder , который должен быть обновлен, чтобы отображать содержимое элемента в заданной позиции в наборе данных.
     * @param position Определяет положение элемента в наборе данных адаптера.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = mUsers.get(position);
        holder.username.setText(user.getUsername());
        if(user.getImageURL()==null) {
            holder.profile_image.setImageResource(R.drawable.baseline_account_circle_24);
        } else {
            Glide.with(mContext).load(user.getImageURL()).into(holder.profile_image);
        }
        if(ischat) {
            lastMessage(user.getId(),holder.last_msg);
        }
        else {
            holder.last_msg.setVisibility(View.GONE);
        }
        if(ischat) {
            if (user.getStatus()!= null && user.getStatus().equals("online")) {
                holder.img_on.setVisibility(View.VISIBLE);
                holder.img_off.setVisibility(View.GONE);
            } else {
                holder.img_on.setVisibility(View.GONE);
                holder.img_off.setVisibility(View.VISIBLE);
            }
        }
            else {
            holder.img_on.setVisibility(View.GONE);
            holder.img_off.setVisibility(View.GONE);
            }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("userid",user.getId());
                mContext.startActivity(intent);
            }
        });
    }

    /**
     * * Возвращает общее количество элементов в наборе данных, хранящемся в адаптере.
     * @return  общее количество элементов в адаптере.
     */
    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    /**
     * Класс ViewHolder описывает представление элемента и данные о его месте в RecyclerView.
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public CircleImageView profile_image;
        private ImageView img_on;
        private ImageView img_off;
        private TextView  last_msg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username_item);
            profile_image = itemView.findViewById(R.id.profile_image);
            img_on = itemView.findViewById(R.id.img_on);
            img_off = itemView.findViewById(R.id.img_off);
            last_msg = itemView.findViewById(R.id.last_msg);
        }
    }

    /**
     * Получение последнего сообщения в чате
     * @param userid - Идентификатор пользователя, для которого будет получено последнее сообщение.
     * @param last_msg - Текстовое представление для отображения последнего сообщения.
     */
private  void lastMessage(String userid, TextView last_msg) {
theLastMessage = "";
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
    reference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                Chat chat = snapshot.getValue(Chat.class);
                if(chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userid) ||
                        chat.getSender().equals(firebaseUser.getUid()) && chat.getReceiver().equals(userid)  ){
                    theLastMessage = chat.getMessage();

                }
            }
         switch (theLastMessage) {
             case "":
                 last_msg.setText("");
                 break;
             default:
                 last_msg.setText(theLastMessage);
                 break;
         }
            theLastMessage ="";
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
}
}
