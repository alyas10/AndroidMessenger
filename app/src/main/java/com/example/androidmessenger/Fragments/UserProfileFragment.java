package com.example.androidmessenger.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.androidmessenger.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import Chats.User;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileFragment extends Fragment {

    private TextView welcome, fullName, email, Dob, gender, mobile;

    private ProgressBar progressBar;
    private String fullName_txt, email_txt, Dob_txt, gender_txt, mobile_txt;

    private CircleImageView circleImageView;

    private FirebaseAuth auth;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    private CircleImageView profileImageView;
    private StorageReference storageReference;
    public UserProfileFragment() {
        super(R.layout.activity_user_profile);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("Профиль");

        welcome = view.findViewById(R.id.textView_show_welcome);
        fullName = view.findViewById(R.id.textView_show_full_name);
        email = view.findViewById(R.id.textView_show_email);
        gender = view.findViewById(R.id.textView_show_gender);
        mobile = view.findViewById(R.id.textView_show_mobile);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        email.setText(firebaseUser.getEmail());

        profileImageView = view.findViewById(R.id.imageView_profile_dp);
        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                fullName.setText(user.getUsername());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Используйте Image Cropper для выбора и обрезки изображения
                CropImage.activity()
                        .setAspectRatio(1, 1) // Установите соотношение сторон для круглого изображения
                        .setCropShape(CropImageView.CropShape.OVAL) // Установите форму обрезки как OVAL
                        .setGuidelines(CropImageView.Guidelines.ON) // Включите руководства обрезки
                        .start(getContext(), UserProfileFragment.this);
            }
        });
    }

        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                Uri imageUri = result.getUri();
                uploadImageToFirebase(imageUri);
            }
        }

    private void uploadImageToFirebase(Uri imageUri) {
        StorageReference fileRef = storageReference.child("profileImages/" + firebaseUser.getUid() + ".jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String imageUrl = uri.toString();
                        // Обновление URL изображения в базе данных
                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
                        userRef.child("ImageURL").setValue(imageUrl).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(), "Изображение профиля обновлено.", Toast.LENGTH_SHORT).show();  }
                        });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Ошибка загрузки изображения.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}