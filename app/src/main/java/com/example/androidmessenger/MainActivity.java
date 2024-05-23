package com.example.androidmessenger;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    TextView username;

    CircleImageView profile_image;

    private Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_acivity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        username = findViewById(R.id.username_bar);
        profile_image = findViewById(R.id.profile_image_nav);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open,
                R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //Подгрузка начального экрана
        Fragment welcomeFragment = new WelcomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, welcomeFragment);
        fragmentTransaction.commit();

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

                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Обработка ошибки
                        }
                    });

                }
            }
        });
    }
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment = null;
        Class fragmentClass = null;

        int id = item.getItemId();

        if(id == R.id.nav_profile){
            fragmentClass = UserProfileFragment.class;
        }
        else if (id == R.id.nav_theory) {
            fragmentClass = TheoryFragment.class;
        }
        else if (id == R.id.nav_code) {
            fragmentClass = SpinnerFragment.class;
        } else if (id == R.id.nav_users) {
            fragmentClass = UsersFragment.class;}
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
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        item.setChecked(true);
        setTitle(item.getTitle());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}