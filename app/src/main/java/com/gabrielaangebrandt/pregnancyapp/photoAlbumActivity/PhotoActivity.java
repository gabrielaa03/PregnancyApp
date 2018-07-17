package com.gabrielaangebrandt.pregnancyapp.photoAlbumActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gabrielaangebrandt.pregnancyapp.App;
import com.gabrielaangebrandt.pregnancyapp.R;
import com.gabrielaangebrandt.pregnancyapp.mainActivity.fragments.adapters.PhotoAlbumRecyclerAdapter;
import com.gabrielaangebrandt.pregnancyapp.mainActivity.view.MainActivity;
import com.gabrielaangebrandt.pregnancyapp.models.data_models.PhotoShare;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhotoActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.new_upload)
    LinearLayout newUpload;

    @BindView(R.id.ib_myphoto)
    ImageButton myPic;

    private PhotoAlbumRecyclerAdapter adapter;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_GALLERY = 2;
    private List<String> imageMaps = new ArrayList<>();
    private List<PhotoShare> items = new ArrayList<>();
    private Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            myPic.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        toolbar.setTitle(getResources().getString(R.string.photo_album));
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PhotoActivity.this, MainActivity.class));
            }
        });
        getData();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        adapter = new PhotoAlbumRecyclerAdapter(items);
        recyclerView.setAdapter(adapter);
        newUpload.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                myPic.setImageBitmap(imageBitmap);
            } else {
                Uri uri = data.getData();
                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                myPic.setImageBitmap(imageBitmap);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                myPic.setEnabled(true);
            }
        }
    }

    @OnClick(R.id.ib_myphoto)
    public void addPhoto() {
        final String[] list = new String[]{"Take photo", "Open gallery"};
        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(this);
        alertdialogbuilder.setItems(list, new DialogInterface.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(DialogInterface dialog, int position) {
                String selectedOption = Arrays.asList(list).get(position);

                switch (selectedOption) {
                    case "Take photo":
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        }
                        break;
                    case "Open gallery":
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI).setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(pickPhoto, REQUEST_GALLERY);
                        break;
                }
            }
        });

        AlertDialog dialog = alertdialogbuilder.create();
        dialog.show();
    }

    @OnClick(R.id.btn_share)
    public void savePicToDB() {
        FirebaseUser user = App.getFirebaseAuth().getCurrentUser();
        if (user != null && imageBitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
            imageMaps.add(imageEncoded);
            App.getFirebaseDb().getReference("images").child(user.getDisplayName()).removeValue();
            App.getFirebaseDb().getReference("images").child(user.getDisplayName()).setValue(imageMaps);

            Toast.makeText(this, R.string.imageSaved, Toast.LENGTH_SHORT).show();
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.choosePhoto, Toast.LENGTH_SHORT).show();
        }
    }

    public void getData() {
        final FirebaseUser user = App.getFirebaseAuth().getCurrentUser();
        App.getFirebaseDb().getReference("images").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items.clear();
                imageMaps.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        for (String string : (ArrayList<String>) dataSnapshot1.getValue()) {
                            items.add(new PhotoShare(dataSnapshot1.getKey(), string));
                            if (dataSnapshot1.getKey().equals(user.getDisplayName())) {
                                imageMaps.add(string);
                            }
                        }
                    }
                }

                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        progressBar.setVisibility(View.GONE);
    }
}
