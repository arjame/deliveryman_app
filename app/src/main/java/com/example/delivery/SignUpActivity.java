package com.example.delivery;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private EditText txt_Name,txt_Mail,txt_Phone,txt_Address,txt_Description,txt_Password;
    //*********Define variables to read from camera and put in ImageView
    String myUri;
    private Uri image_uri;
    private de.hdodenhof.circleimageview.CircleImageView imgProfile;
    ImageButton btnSelectPhoto;
    private Bitmap imageBitmap;
    private static final int PICK_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 3;
    // End
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //****************************** Camera
        btnSelectPhoto=findViewById(R.id.btnSelectPhoto);
        imgProfile=findViewById(R.id.imgProfile);
        btnSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();
            }
        });
        // End

        //********* Define variable for each view
        txt_Name = findViewById(R.id.edtFullName);
        txt_Password=findViewById(R.id.edtPassword);
        txt_Mail = findViewById(R.id.etMail);
        txt_Phone = findViewById(R.id.etPhone);
        txt_Description = findViewById(R.id.etDescription);
        //End of defining variables

        //***Checking validation of Name
        txt_Name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateName(txt_Name.getText().toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //********Check validation of phone
        txt_Phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validatePhone(txt_Phone.getText().toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        Button btn_Confirm = findViewById(R.id.btnConfirm);
        btn_Confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (!validateName(txt_Name.getText().toString()))
                {
                    txt_Name.requestFocus();

                }else if (!validatePhone(txt_Phone.getText().toString()))
                {
                    txt_Phone.requestFocus();
                }
                else if (!validateEmail(txt_Mail.getText().toString()))
                {
                    txt_Mail.requestFocus();
                } else {
                  return;
                }
            }
        });
        //End of click on button Confirm


    }
    //************Validate each view
    private boolean validateName(String Name) {
        int characters = Name.trim().length();
        if (characters > 20) {
            txt_Name.setError("Name is too long ( maximum is 20)");
            return false;
        } else if (characters<1){
            txt_Name.setError("Name can not be empty");
            return false;
        }
        else {
            txt_Name.setError(null);
            return true;
        }
    }

    //*************Validate Phone
    private boolean validatePhone(String Phone) {

        int characters = Phone.trim().length();
        if (characters<1) {
            txt_Phone.setError("Phone can not be empty");
            return false;
        }
        else if (characters > 15 || characters <10){
            txt_Phone.setError("Invalid phone number");
            return false;
        } else {
            String phonePatteren = "^([0-9\\+]|\\(\\d{1,3}\\))[0-9\\-\\. ]{3,15}$";
            Pattern pattern = Pattern.compile(phonePatteren);
            Matcher matcher = pattern.matcher(Phone);
            if (matcher.matches())
            {
                return true;
            }
            else {
                txt_Phone.setError("Invalid phone number");
                return false;
            }
        }
    }
    //...........
    //*******Validate Email
    private boolean validateEmail(String Email) {
        int characters = Email.trim().length();
        if (characters<1){
            return true;
        } else if (characters>25){
            txt_Mail.setError("Invalid Email address(it is too long)");
            return false;
        } else {
            String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            Pattern pattern = Pattern.compile(emailPattern);
            Matcher matcher = pattern.matcher(Email);
            if (matcher.matches())
            {
                return true;
            }
            else {
                txt_Mail.setError("Invalid Email address ");
                return false;
            }
        }
    }
    //....................
    // *****************Camera
    // *****************This part create dialog box
    private void selectImage() {
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Delete" };
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);

        builder.setTitle("Add Photo!");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    openCamera();
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    openGallery();
                }
                else if (options[item].equals("Delete")) {
                    int drawableResource = R.drawable.personal;
                    Drawable d = getResources().getDrawable(drawableResource);
                    imgProfile.setImageDrawable(d);

                    dialog.dismiss();
                }
            }
        });


        builder.show();
    }

    //..........................
    //*****Open Gallery
    public void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    //.....................

    //***********Open Camera
    public void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    //........................
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            image_uri = data.getData();
            imgProfile.setImageURI(image_uri);
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            image_uri = getImageUri(this, imageBitmap);
            imgProfile.setImageURI(image_uri);
        }
    }
    //..................
    //*****
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    //..................
    //******* We want when we rotate screen image does not change
    //We use these 2 below fuctions
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (image_uri != null) {
            outState.putString("image", image_uri.toString());
        }
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String image=savedInstanceState.getString("image",""); // Value that was saved will restore to variable
        image_uri = Uri.parse(image);
        imgProfile.setImageURI(image_uri);
    }
    //...................................





}
