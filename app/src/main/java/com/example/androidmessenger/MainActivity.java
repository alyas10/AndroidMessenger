package com.example.androidmessenger;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.androidmessenger.Ciphers.SpinnerFragment;
import com.example.androidmessenger.Fragments.ChatsFragment;
import com.example.androidmessenger.Fragments.TheoryFragment;
import com.example.androidmessenger.Fragments.UserProfileFragment;
import com.example.androidmessenger.Fragments.UsersFragment;
import com.example.androidmessenger.Fragments.WelcomeFragment;
import com.example.androidmessenger.modelClass.ScannerActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Chats.User;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Главная активность с боковым меню и менеджером по переключению между фрагментами
 *
 * @author Алевтина Ильина
 * @author Иван Минаев
 * @version 2.1
 */
public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;

    DatabaseReference reference;

    TextView username;

    CircleImageView profile_image;

    private Intent intent;

    /**
     * Создание активити
     *
     * @param savedInstanceState Если действие повторно инициализируется после того, как
     * ранее было завершено, то этот пакет содержит данные, которые были наиболее
     * недавно предоставлены в {@link #onSaveInstanceState}.  <b><i>Примечание: в противном случае значение равно нулю.</i></b>
     *
     */
    protected void onCreate(Bundle savedInstanceState) {
        // Установите ориентацию экрана в портретный режим
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_acivity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        username = findViewById(R.id.username_bar);
        profile_image = findViewById(R.id.profile_image_nav);
        //Загрузка бокового меню и его компонентов
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open,
                R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //Подгрузка начального экрана
        Fragment welcomeFragment = new WelcomeFragment();
        //Установка менеджера фрагментов
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, welcomeFragment);
        fragmentTransaction.commit();
        /**
         * Проверка входа пользователя в систему и подгрузка фото в боковое меню
         */
        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.getValue(User.class);
                            if (user != null) {
                                // Обновление NavigationView
                                NavigationView navigationView = findViewById(R.id.nav_view);
                                View headerView = navigationView.getHeaderView(0);
                                ImageView profileImageView = headerView.findViewById(R.id.profile_image_nav);
                                TextView usernameTextView = headerView.findViewById(R.id.username_bar);

                                if (user.getImageURL().equals("default")) {
                                    profileImageView.setImageResource(R.drawable.baseline_account_circle_24);
                                } else {
                                    Glide.with(getApplicationContext()).load(user.getImageURL()).into(profileImageView);
                                }
                                usernameTextView.setText(user.getUsername());
                            }
                        }

                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Обработка ошибки
                        }
                    });

                }
            }
        });
    }
    //Открытие и закрытие бокового меню
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*
 * Обрабатывает нажатие на элемент меню навигации.
 *
 * @param item Элемент меню навигации, который был нажат.
 * @return true, если обработка была успешной, иначе false.
 */
    //Переход между фрагментами
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment = null;
        Class fragmentClass = null;

        int id = item.getItemId();
        //Фрагмент профиля
        if(id == R.id.nav_profile){
            fragmentClass = UserProfileFragment.class;
        }
        //Фрагмент с теоретическим материалом
        else if (id == R.id.nav_theory) {
            fragmentClass = TheoryFragment.class;
        }
        //Фрагмент списка с выбором шифров
        else if (id == R.id.nav_code) {
            fragmentClass = SpinnerFragment.class;
        }
        else if (id == R.id.nav_vzlom) {
            //Будущая опция для считывания текста с помощью камеры
            Intent intent = new Intent(MainActivity.this, com.example.androidmessenger.modelClass.ScannerActivity.class);
            startActivity(intent);
            return true;
        }
        //Фрагмент пользователей
        else if (id == R.id.nav_users) {
            fragmentClass = UsersFragment.class;}
        //Фрагмент чатов
        else if (id == R.id.nav_chats) {
            fragmentClass = ChatsFragment.class;
        } else if (id == R.id.nav_userLogout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else if(id == R.id.nav_test) {
            fragmentClass = HomeFragment.class;
        }
        else if (id == R.id.nav_logout)
        {
            finish();
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Заменяет текущий фрагмент на новый.
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        // Отмечает выбранный элемент меню.
        item.setChecked(true);
        // Устанавливает заголовок для текущего фрагмента.
        setTitle(item.getTitle());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
