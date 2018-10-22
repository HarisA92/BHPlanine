package com.example.user.graduationproject.Bjelasnica.Fragments.Report;

import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user.graduationproject.Bjelasnica.Main;
import com.example.user.graduationproject.Bjelasnica.Utils.SkiResortHolder;
import com.example.user.graduationproject.Bjelasnica.Utils.Upload;
import com.example.user.graduationproject.R;
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
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PopUp extends AppCompatActivity {
    public static final String CAMERA = "Camera";
    public static final String GALLERY = "Gallery";
    public static final String CANCEL = "Cancel";
    public static final int IMAGE_WIDTH = 1920;
    public static final int IMAGE_HEIGHT = 1080;
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private EditText mEditText;
    private TextView mUsername;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private Integer CAMERA_REQUEST = 1, SELECT_FILE = 0;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private  FirebaseUser user;
    private MaterialBetterSpinner snowSpinner, surfaceSpinner;
    private String spinnerSnow, spinnerSurface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_report);
        onCreate();
        mButtonChooseImage = findViewById(R.id.AddPhoto);
        mButtonUpload = findViewById(R.id.Submit);
        mUsername = findViewById(R.id.username);
        mEditText = findViewById(R.id.CommentBox);
        mImageView = findViewById(R.id.imageView);
        mProgressBar = findViewById(R.id.progress_bar);
        mUsername.setText(getUsername());

        final String mountainName = SkiResortHolder.getSkiResort().getMountain().getValue();

        mStorageRef = FirebaseStorage.getInstance().getReference(mountainName);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(mountainName);

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(PopUp.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    finalCheck();
                }
            }
        });

        String[] itemsForSnowSpinner = new String[]{" - ", "5cm", "10cm", "15cm", "20cm", "30cm", "40cm"};
        String[] itemsForSurfaceSpinner = new String[]{" - ", "Machine Groomed", "Powder", "Wet", "Icy", "Hard Packed", "Variable"};

        snowSpinner = (MaterialBetterSpinner) findViewById(R.id.android_material_design_spinner);
        surfaceSpinner = (MaterialBetterSpinner) findViewById(R.id.android_material_design_spinner1);

        ArrayAdapter<String> adapterSnow = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsForSnowSpinner);
        snowSpinner.setAdapter(adapterSnow);
        ArrayAdapter<String> adapterSurface = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsForSurfaceSpinner);
        surfaceSpinner.setAdapter(adapterSurface);

        snowSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerSnow = adapterView.getItemAtPosition(i).toString();
            }
        });

        surfaceSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerSurface = adapterView.getItemAtPosition(i).toString();
            }
        });
    }

    @Override
     protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("usernameIsSaved", mUsername.getText().toString());
        outState.putString("editTextIsSaved", mEditText.getText().toString());

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String stateSavedUsername = savedInstanceState.getString("usernameIsSaved");
        mUsername.setText(stateSavedUsername);
        String stateSavedEditText = savedInstanceState.getString("editTextIsSaved");
        mEditText.setText(stateSavedEditText);
    }

    private void SelectImage() {
        final CharSequence[] items = {CAMERA, GALLERY, CANCEL};
        AlertDialog.Builder builder = new AlertDialog.Builder(PopUp.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals(CAMERA)) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, CAMERA_REQUEST);
                    }
                } else if (items[i].equals(GALLERY)) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent, SELECT_FILE);
                } else if (items[i].equals(CANCEL)) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
                Picasso.with(this).load(mImageUri).resize(IMAGE_WIDTH, IMAGE_HEIGHT).into(mImageView);
        }
    }

    private String getFileExtension(Uri uri) {
        return MimeTypeMap
                .getSingleton()
                .getExtensionFromMimeType(getContentResolver().getType(uri));
    }

    private String getUsername(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        String emailUser = null;
        if (user != null) {
            emailUser = user.getDisplayName();
        }
        return emailUser;
    }

    private String getUserEmail(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        String email = null;
        if (user != null) {
            email = user.getEmail();
        }
        return email;
    }

    private DatabaseReference getDatabaseReference() {
        return FirebaseDatabase
                .getInstance()
                .getReference(SkiResortHolder.getSkiResort().getMountain().getValue());
    }

    private boolean checkDuplicateData(String commentBox, DataSnapshot dataSnapshot){
        Upload upload = new Upload();
        for(DataSnapshot ds: dataSnapshot.getChildren()){
            upload.setName(ds.getValue(Upload.class).getName());
            if(upload.getName().equals(commentBox)){
                return true;
            }
        }
        return false;
    }

    private void finalCheck(){
        getDatabaseReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(checkDuplicateData(mEditText.getText().toString().toLowerCase(), dataSnapshot)){
                    Toast.makeText(PopUp.this, "Someone has already posted similar post, try another one!", Toast.LENGTH_SHORT).show();
                }
                else {
                    uploadFile();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void uploadFile() {
        final String date = DATE_FORMAT.format(new Date());
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));
            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 2000);
                            Toast.makeText(PopUp.this, "Upload successful", Toast.LENGTH_LONG).show();
                            Upload upload = new Upload(mEditText.getText().toString().toLowerCase(),
                                    taskSnapshot.getDownloadUrl().toString(),
                                    getUsername(),
                                    spinnerSnow,
                                    spinnerSurface,
                                    date, getUserEmail()
                            );
                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(PopUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    public void onCreate() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}
















