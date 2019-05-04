package com.example.krruiz.withfirebase;

import android.Manifest;
import android.app.ProgressDialog;
import android.arch.core.executor.TaskExecutor;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewProductActivity extends AppCompatActivity {

    private String CategoryName, Description, Price, Name, saveCurrentDate, saveCurrenTime, downloadImageURL;
    private ImageView ImageProduct;
    private EditText ProductName, ProductDescrip, ProductPrice;
    private Button AddProduct;
    private ProgressDialog loadingBar;
    private static final int GalleryPick = 1;
    private Uri imageUri;

    private String productRandomKey;
    private StorageReference ProductImagesRef;
    private DatabaseReference ProductsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);

        CategoryName = getIntent().getExtras().get("category").toString();
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        ImageProduct = (ImageView) findViewById(R.id.image_product);
        ProductName = (EditText) findViewById(R.id.product_name);
        ProductDescrip = (EditText) findViewById(R.id.product_descrip);
        ProductPrice = (EditText) findViewById(R.id.product_price);
        AddProduct = (Button) findViewById(R.id.button2);

        loadingBar = new ProgressDialog(this);

        ImageProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String [] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                } else{
                    openGallery();

                }

            }
        });

        AddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingBar.setTitle("Saving Data");
                loadingBar.setMessage("Please wait");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                validateProductInfo();
            }
        });
    }

    private void openGallery() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

         imageUri = data.getData();

        if (requestCode == 1 && resultCode == RESULT_OK && data != null){

            Picasso.with(this).load(imageUri).into(ImageProduct);
        }else {

            Toast.makeText(AdminAddNewProductActivity.this, "There is a issue", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
        }
    }



    private void validateProductInfo() {

        Name = ProductName.getText().toString();
        Description = ProductDescrip.getText().toString();
        Price = ProductPrice.getText().toString();

        if (imageUri == null ){
            Toast.makeText(this, "Product Image is required", Toast.LENGTH_LONG).show();
        } else if (Description.equals("")){
            Toast.makeText(this, "Description must be filled", Toast.LENGTH_LONG).show();
        } else if (Name.equals("")){
            Toast.makeText(this, "Name must be filled", Toast.LENGTH_LONG).show();
        }else if (Price.equals("")){
            Toast.makeText(this, "Price must be filled", Toast.LENGTH_LONG).show();
        } else {
            StoreProductInformation();
        }

    }

    private void StoreProductInformation() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currenDate = new SimpleDateFormat("yyyy-MM-dd");
        saveCurrentDate = currenDate.format(calendar.getTime());

        SimpleDateFormat currenTime = new SimpleDateFormat("HH:mm:ss");
        saveCurrenTime = currenTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrenTime;

        // StorageReference variable to save everything
        final StorageReference filePath = ProductImagesRef.child(imageUri.getLastPathSegment() + productRandomKey + ".jpg" );
        final UploadTask uploadTask = filePath.putFile(imageUri);

        // checking if failure upload
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                Toast.makeText(AdminAddNewProductActivity.this, "Error: ", Toast.LENGTH_LONG).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(AdminAddNewProductActivity.this, "Image uploaded successfully", Toast.LENGTH_LONG).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if (!task.isSuccessful()){

                            throw task.getException();
                        }

                        downloadImageURL = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if (task.isSuccessful()){
                            downloadImageURL = task.getResult().toString();
                            System.out.println("========================================================");
                            System.out.println(downloadImageURL);
                            System.out.println("========================================================");

                            Toast.makeText(AdminAddNewProductActivity.this, "Product Image URL sucessfully ", Toast.LENGTH_LONG);

                            saveProductInfoDatabase();
                        }
                    }
                });

            }
        });
    }

    private void saveProductInfoDatabase() {

        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid", productRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrenTime);
        productMap.put("category", CategoryName);
        productMap.put("name", Name);
        productMap.put("description", Description);
        productMap.put("price", Price);
        productMap.put("image", downloadImageURL);

        String clearCaracter = productRandomKey.replace('.', ':').replace(',', ' ');

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(productRandomKey);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");

        ProductsRef.child(productRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){
                    //downloadImageURL = task.getResult().toString();
                    Intent intent = new Intent(AdminAddNewProductActivity.this, AdminCategoryActivity.class);
                    startActivity(intent);

                    Toast.makeText(AdminAddNewProductActivity.this, "Product is saved sucessfully", Toast.LENGTH_LONG).show();

                }else {
                    loadingBar.dismiss();
                    Toast.makeText(AdminAddNewProductActivity.this, "Error saving Product", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


}
