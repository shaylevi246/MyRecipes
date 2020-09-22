package com.example.myrecipes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ShowRecipe extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_recipe);


        TextView nameTV = findViewById(R.id.show_recipeNameTV);
        TextView descriptionTV = findViewById(R.id.show_recipe_descriptionTV);
        TextView categoryTV = findViewById(R.id.show_recipe_categoryTV);
        TextView ingredientsTV = findViewById(R.id.show_recipe_ingredientsTV);
        TextView directionsTV = findViewById(R.id.show_recipe_directionsTV);
        TextView commentsTV = findViewById(R.id.show_recipe_commentsTV);
        ImageView imageView = findViewById(R.id.show_show_pic);

        nameTV.setText(getIntent().getStringExtra("name"));
        descriptionTV.setText(getIntent().getStringExtra("description"));
        categoryTV.setText(getIntent().getStringExtra("category"));
        ingredientsTV.setText(getIntent().getStringExtra("ingredients"));
        directionsTV.setText(getIntent().getStringExtra("directions"));
        commentsTV.setText(getIntent().getStringExtra("comments"));

        imageView.setImageBitmap(BitmapFactory.decodeFile(getIntent().getStringExtra("pic")));



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back_from_show,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(ShowRecipe.this, SavedRecipes.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

}
