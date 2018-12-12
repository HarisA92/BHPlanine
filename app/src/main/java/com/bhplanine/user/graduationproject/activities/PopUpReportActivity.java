package com.bhplanine.user.graduationproject.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bhplanine.user.graduationproject.R;
import com.bhplanine.user.graduationproject.models.SkiResortHolder;
import com.bhplanine.user.graduationproject.models.UploadUserReport;
import com.bhplanine.user.graduationproject.utils.FirebaseHolder;
import com.bumptech.glide.Glide;
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
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class PopUpReportActivity extends AppCompatActivity {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    public static final int REQUEST_PERMISSION = 100;
    private static final int CAMERA_REQUEST = 100, SELECT_FILE = 0;
    private EditText mEditText;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask<UploadTask.TaskSnapshot> uploadTask;
    private FirebaseUser user;
    private String spinnerSnow, spinnerSurface;
    private String imageFilePath;
    private FirebaseHolder firebaseHolder;

    public static String getPath(Context context, Uri uri) {
        String result = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                result = cursor.getString(column_index);
            }
            cursor.close();
        }
        if (result == null) {
            result = "Not found";
        }
        return result;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_report);
        onCreate();
        Button mButtonChooseImage = findViewById(R.id.AddPhoto);
        Button mButtonUpload = findViewById(R.id.Submit);
        TextView mUsername = findViewById(R.id.username);
        mEditText = findViewById(R.id.CommentBox);
        mImageView = findViewById(R.id.imageView);
        mProgressBar = findViewById(R.id.progress_bar);
        mUsername.setText(getUsername());
        firebaseHolder = new FirebaseHolder();

        final String mountainName = SkiResortHolder.getSkiResort().getMountain().getValue();

        mStorageRef = FirebaseStorage.getInstance().getReference(mountainName);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(mountainName);

        mButtonChooseImage.setOnClickListener(v -> SelectImage());

        mButtonUpload.setOnClickListener(v -> {
            if (uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(PopUpReportActivity.this, getResources().getString(R.string.upload_in_progress), Toast.LENGTH_SHORT).show();
            } else {
                finalCheck();
            }
        });

        String[] itemsForSnowSpinner = new String[]{getResources().getString(R.string.blank_space), getResources().getString(R.string.size_5cm), getResources().getString(R.string.size_10cm), getResources().getString(R.string.size_15cm), getResources().getString(R.string.size_20cm), getResources().getString(R.string.size_30cm), getResources().getString(R.string.size_40cm)};
        String[] itemsForSurfaceSpinner = new String[]{getResources().getString(R.string.blank_space), getResources().getString(R.string.machine_groomed), getResources().getString(R.string.powder), getResources().getString(R.string.wet), getResources().getString(R.string.icy), getResources().getString(R.string.hard_packed), getResources().getString(R.string.variable)};

        MaterialBetterSpinner snowSpinner = findViewById(R.id.android_material_design_spinner);
        MaterialBetterSpinner surfaceSpinner = findViewById(R.id.android_material_design_spinner1);

        ArrayAdapter<String> adapterSnow = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsForSnowSpinner);
        snowSpinner.setAdapter(adapterSnow);
        ArrayAdapter<String> adapterSurface = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsForSurfaceSpinner);
        surfaceSpinner.setAdapter(adapterSurface);

        snowSpinner.setOnItemClickListener((adapterView, view, i, l) -> spinnerSnow = adapterView.getItemAtPosition(i).toString());

        surfaceSpinner.setOnItemClickListener((adapterView, view, i, l) -> spinnerSurface = adapterView.getItemAtPosition(i).toString());
        capturePhotoPermission();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(getResources().getString(R.string.on_save_instance_state), mEditText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String stateSavedEditText = savedInstanceState.getString(getResources().getString(R.string.on_save_instance_state));
        mEditText.setText(stateSavedEditText);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST) {
            if (resultCode == RESULT_OK) {
                Glide.with(this).load(imageFilePath).into(mImageView);
                mImageUri = Uri.parse(imageFilePath);
            }
        } else if (resultCode == RESULT_OK && requestCode == SELECT_FILE) {
            mImageUri = data.getData();
            String path = getPath(this.getApplicationContext(), mImageUri);
            mImageUri = Uri.parse(path);
            Glide.with(this).load(path).into(mImageView);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getResources().getString(R.string.photo_permission), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void capturePhotoPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ignored) {
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
        final CharSequence[] items = {getResources().getString(R.string.camera), getResources().getString(R.string.gallery), getResources().getString(R.string.cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(PopUpReportActivity.this);
        builder.setTitle("Add Image");
        builder.setItems(items, (dialogInterface, i) -> {
            if (items[i].equals(getResources().getString(R.string.camera))) {
                dispatchTakePictureIntent();
            } else if (items[i].equals(getResources().getString(R.string.gallery))) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, SELECT_FILE);
            } else if (items[i].equals(getResources().getString(R.string.cancel))) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private String getUsername() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        String emailUser = null;
        if (user != null) {
            emailUser = user.getDisplayName();
        }
        return emailUser;
    }

    private String getUserEmail() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        String email = null;
        if (user != null) {
            email = user.getEmail();
        }
        return email;
    }

    private boolean checkDuplicateData(String commentBox, DataSnapshot dataSnapshot) {
        UploadUserReport upload = new UploadUserReport();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            upload.setName(Objects.requireNonNull(ds.getValue(UploadUserReport.class)).getName());
            if (upload.getName().equals(commentBox)) {
                return true;
            }
        }
        return false;
    }

    private void finalCheck() {
        firebaseHolder.getDatabaseReferenceForReport().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (checkDuplicateData(mEditText.getText().toString().toLowerCase(), dataSnapshot)) {
                    Toast.makeText(PopUpReportActivity.this, getResources().getString(R.string.similar_post), Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void uploadFile() {
        final String date = DATE_FORMAT.format(new Date());
        if (mImageUri != null) {
            Uri file = Uri.fromFile(new File(String.valueOf(mImageUri)));
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + file.getLastPathSegment());
            uploadTask = fileReference.putFile(file);
            uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw Objects.requireNonNull(task.getException());
                }
                return fileReference.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    new Handler().postDelayed(() -> mProgressBar.setProgress(0), 2000);
                    Toast.makeText(PopUpReportActivity.this, getResources().getString(R.string.upload_successful), Toast.LENGTH_LONG).show();
                    Uri downloadUri = task.getResult();
                    UploadUserReport upload = new UploadUserReport(mEditText.getText().toString().toLowerCase(),
                            Objects.requireNonNull(downloadUri).toString(),
                            getUsername(),
                            spinnerSnow,
                            spinnerSurface,
                            date, getUserEmail());
                    String uploadId = mDatabaseRef.push().getKey();
                    mDatabaseRef.child(Objects.requireNonNull(uploadId)).setValue(upload);
                    finish();
                }
            }).addOnFailureListener(e -> Toast.makeText(PopUpReportActivity.this, getResources().getString(R.string.error) + e.getMessage(), Toast.LENGTH_SHORT).show());
            uploadTask.addOnProgressListener(taskSnapshot -> {
                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                mProgressBar.setProgress((int) progress);
            });
        } else {
            Toast.makeText(this, getResources().getString(R.string.upload_image), Toast.LENGTH_SHORT).show();
        }
    }

    public void onCreate() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }
}
















