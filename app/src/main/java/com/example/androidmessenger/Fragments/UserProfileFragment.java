package com.example.androidmessenger.Fragments;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.androidmessenger.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
//import com.theartofdev.edmodo.cropper.CropImage;
//import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import Chats.User;
import de.hdodenhof.circleimageview.CircleImageView;
/**
 * Фрагмент для отображения профиля пользователя и управления им.
 * @version 1.0
 * @author Алевтина Ильина
 */

public class UserProfileFragment extends Fragment {

    private TextView welcome, fullName, email, Dob, gender, mobile;
    private FirebaseAuth auth;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    private CircleImageView profileImageView;
    private StorageReference storageReference;
    private Uri imageUri;
    private static final int IMAGE_REQUEST = 1;
    private StorageTask uploadTask;


    /**
     * Вызывается для создания фрагментом экземпляра представления пользовательского интерфейса.
     *
     * @param inflater - объект LayoutInflater, который может использоваться для расширения любых представлений во фрагменте.
     * @param container, если значение не равно null, это родительское представление, к которому должен быть подключен пользовательский интерфейс фрагмента.
     * @param savedInstanceState, если значение не равно null, этот фрагмент создается заново из предыдущего сохраненного состояния.
     * @return представление пользовательского интерфейса фрагмента или значение null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_user_profile, container, false);
        return view;
    }

    /**
     * Вызывается сразу после возврата функции onCreateView(), где для фрагмента устанавливается окончательная иерархия представлений.
     *
     * @param view  - представление,возвращаемое функцией onCreateView().
     * @param savedInstanceState Если значение не равно null, этот фрагмент создается заново из предыдущего сохраненного состояния.
     */
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("Профиль");

        welcome = view.findViewById(R.id.textView_show_welcome);
        fullName = view.findViewById(R.id.textView_show_full_name);
        email = view.findViewById(R.id.textView_show_email);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        email.setText(firebaseUser.getEmail());

        profileImageView = view.findViewById(R.id.imageView_profile_dp);
        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        // Загрузка данных пользователя из Firebase database
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                fullName.setText(user.getUsername());
                welcome.setText("Добро пожаловать, " + user.getUsername());
                if (user != null && user.getImageURL() != null && !user.getImageURL().equals("default") && isAdded()){
                    Glide.with(getContext()).load(user.getImageURL()).into(profileImageView);
                } else {
                    // Здесь код для случая, когда user или user.getImageURL() равны null или imageURL равен "default"
                    profileImageView.setImageResource(R.drawable.baseline_account_circle_24);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();
            }
        });
    }

    /**
     * Открывает окно, позволяющее пользователю выбрать изображение со своего устройства.
     */
    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    /**
     * Возвращает расширение файла из заданного URI.
     *
     * @param uri - это URI файла.
     * @return  расширение файла в виде строки.
     */
    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    /**
     * Загружает выбранное изображение в Firebase storage.
     */
    private void uploadImageToFirebase() {
        if (imageUri != null) {
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri);
            // Cоздание цепочки задач, чтобы получить URL-адрес для загрузки загруженного изображения
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                //Обновление HashMap после загрузки изображения
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();

                        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("imageURL", "" + mUri);
                        reference.updateChildren(map);

                    } else {
                        Toast.makeText(getContext(), "Ошибка!", Toast.LENGTH_SHORT).show();
                    }
                }
                //Обработка ошибок
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "Изображение не выбрано!", Toast.LENGTH_SHORT).show();
        }
    }
    //Получение данных url при успешной загрузке изображения
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();

            // Запуск активности обрезки изображения
            /*CropImage.activity(imageUri)
                    .setAspectRatio(1, 1) // Установите соотношение сторон для круглого изображения
                    .setCropShape(CropImageView.CropShape.OVAL) // Установите форму обрезки как OVAL
                    .setGuidelines(CropImageView.Guidelines.ON) // Включите руководства обрезки
                    .start(getContext(), this);*/
        }

        // Обработка результата обрезки изображения
        /*if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();*/

                // Загрузка обрезанного изображения в Firebase
                uploadImageToFirebase();
            } /*else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(getContext(), "Ошибка обрезки: " + error, Toast.LENGTH_SHORT).show();*/

}
