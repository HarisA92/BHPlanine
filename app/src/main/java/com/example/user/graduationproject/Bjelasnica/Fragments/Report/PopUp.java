package com.example.user.graduationproject.Bjelasnica.Fragments.Report;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user.graduationproject.Bjelasnica.Firebase.FirebaseHolder;
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

import java.io.File;
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
    private static final int CAMERA_REQUEST = 100, SELECT_FILE = 0;
    public static final int REQUEST_PERMISSION = 200;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private FirebaseUser user;
    private MaterialBetterSpinner snowSpinner, surfaceSpinner;
    private String spinnerSnow, spinnerSurface;
    private String imageFilePath;
    private FirebaseHolder firebaseHolder = new FirebaseHolder();

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

        snowSpinner = findViewById(R.id.android_material_design_spinner);
        surfaceSpinner = findViewById(R.id.android_material_design_spinner1);

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

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
        }


    }

    @Override
     protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("editTextIsSaved", mEditText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String stateSavedEditText = savedInstanceState.getString("editTextIsSaved");
        mEditText.setText(stateSavedEditText);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        imageFilePath = image.getAbsolutePath();
        return image;
    }

    private void SelectImage() {
        final CharSequence[] items = {CAMERA, GALLERY, CANCEL};
        AlertDialog.Builder builder = new AlertDialog.Builder(PopUp.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals(CAMERA)) {
                    dispatchTakePictureIntent();
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Thanks for granting Permission", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public static String getPath(Context context, Uri uri ) {
        String result = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver( ).query( uri, proj, null, null, null );
        if(cursor != null){
            if ( cursor.moveToFirst( ) ) {
                int column_index = cursor.getColumnIndexOrThrow( proj[0] );
                result = cursor.getString( column_index );
            }
            cursor.close( );
        }
        if(result == null) {
            result = "Not found";
        }
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST ) {
            if (resultCode == RESULT_OK ) {
                mImageUri = Uri.parse(imageFilePath);
                Picasso.with(getApplicationContext()).load("file:" + mImageUri).resize(IMAGE_WIDTH, IMAGE_HEIGHT).onlyScaleDown().into(mImageView);
            }
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "You cancelled the operation", Toast.LENGTH_SHORT).show();
            }
        }
        else if (resultCode == RESULT_OK && requestCode == SELECT_FILE){
            mImageUri = data.getData();
            String path = getPath(this.getApplicationContext(), mImageUri);
            mImageUri = Uri.parse(path);
            Picasso.with(getApplicationContext()).load("file:" + mImageUri).resize(IMAGE_WIDTH, IMAGE_HEIGHT).onlyScaleDown().into(mImageView);
        }
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
        firebaseHolder.getDatabaseReferenceForReport().addListenerForSingleValueEvent(new ValueEventListener() {
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
            Uri file = Uri.fromFile(new File(String.valueOf(mImageUri)));

            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + file.getLastPathSegment());
            mUploadTask = fileReference.putFile(file)
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
                            Toast.makeText(PopUp.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
















