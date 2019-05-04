package com.example.krruiz.withfirebase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {

    private ImageView Computers, Technolgies, Mobiles, Printers;
    private ImageView Shirts, Suits, Dress, Weedings;
    private ImageView Gifts, Watches, Glasses, Foods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        Computers = (ImageView) findViewById(R.id.imageView1);
        Technolgies = (ImageView) findViewById(R.id.imageView2);
        Mobiles = (ImageView) findViewById(R.id.imageView3);
        Printers = (ImageView) findViewById(R.id.imageView4);

        Shirts = (ImageView) findViewById(R.id.imageView5);
        Suits = (ImageView) findViewById(R.id.imageView6);
        Dress = (ImageView) findViewById(R.id.imageView7);
        Weedings = (ImageView) findViewById(R.id.imageView8);

        Gifts = (ImageView) findViewById(R.id.imageView9);
        Watches = (ImageView) findViewById(R.id.imageView10);
        Glasses = (ImageView) findViewById(R.id.imageView11);
        Foods = (ImageView) findViewById(R.id.imageView12);



        Computers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Computers");
                startActivity(intent);

            }
        });

        Technolgies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Technologies");
                startActivity(intent);

            }
        });

        Mobiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Mobiles");
                startActivity(intent);

            }
        });

        Printers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Printers");
                startActivity(intent);

            }
        });

        Shirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Shirts");
                startActivity(intent);

            }
        });

        Suits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Suits");
                startActivity(intent);

            }
        });

        Dress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Dress");
                startActivity(intent);

            }
        });

        Weedings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Weedings");
                startActivity(intent);

            }
        });


        Gifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Gifts");
                startActivity(intent);

            }
        });

        Watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Watches");
                startActivity(intent);

            }
        });

        Glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Glasses");
                startActivity(intent);

            }
        });

        Foods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Foods");
                startActivity(intent);

            }
        });


    }
}
