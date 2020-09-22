package com.example.myrecipes;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

public class AddingRecipe extends AppCompatActivity {
    SharedPreferences sp;
    int recipe_id = 1;
    final int CAMERA_REQUEST = 1;
    final int WRITE_PERMISSION_REQUEST = 2;
    private final int READ_PERMISSION_REQUEST = 3;
    Bitmap bitmap;
    ImageView imageview;
    EditText recipe_nameET;
    EditText descriptionET;
    TextView categoryET;
    EditText ingredientsET;
    EditText directionsET;
    EditText commentsET;
    Button camera_btn;
    Button save_btn;
    Date date;
    File file;
    boolean check_camera_press;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_recipe);

        sp = getSharedPreferences("details", MODE_PRIVATE);
        recipe_id = sp.getInt("ID", 1);

        imageview = findViewById(R.id.show_pic);
        recipe_nameET = findViewById(R.id.recipeNameET);
        descriptionET = findViewById(R.id.recipe_descriptionET);
        categoryET = findViewById(R.id.recipe_categoryET);
        ingredientsET = findViewById(R.id.recipe_ingredientsET);
        directionsET = findViewById(R.id.recipe_directionsET);
        commentsET = findViewById(R.id.recipe_commentsET);
        imageview.setImageBitmap(null);
        save_btn = findViewById(R.id.save_btn);
        check_camera_press = false;
        categoryET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddingRecipe.this);
                builder.setTitle(R.string.pick_a_category)
                        .setSingleChoiceItems(R.array.choose_category, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                categoryET.setText(getResources().getStringArray(R.array.choose_category)[which]);
                                dialog.dismiss();
                            }
                        }).show();
            }

        });
        imageview.setImageBitmap(null);
        recipe_nameET.setText("");
        descriptionET.setText("");
        ingredientsET.setText("");
        directionsET.setText("");
        commentsET.setText("");
        camera_btn = findViewById(R.id.pic_btn);
        camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New photo");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From the camera");
                date = new Date();
                file = new File(Environment.getExternalStorageDirectory(),date.toString()+".jpg");
                Uri photoURI = FileProvider.getUriForFile(v.getContext(), "com.example.myrecipes.provider", file);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, CAMERA_REQUEST);
                check_camera_press = true;

            }
        });
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recipe_nameET.getText().toString().equals("") || categoryET.getText().toString().equals("")){
                    Toast.makeText(AddingRecipe.this, R.string.category_and_name, Toast.LENGTH_LONG).show();
                    return;}
                else if(check_camera_press==false){
                    Toast.makeText(AddingRecipe.this, R.string.cameraBtn_not_pushed, Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    Recipe recipe = new Recipe(recipe_nameET.getText().toString(), descriptionET.getText().toString(), categoryET.getText().toString(), ingredientsET.getText().toString(), directionsET.getText().toString(), commentsET.getText().toString(), file.getAbsolutePath(), file,recipe_id);
                    RecipeManager savedRecipes = RecipeManager.getInstance(AddingRecipe.this);
                    savedRecipes.addRecipe(recipe);

                    imageview.setImageBitmap(null);
                    recipe_nameET.setText("");
                    descriptionET.setText("");
                    categoryET.setText("");
                    ingredientsET.setText("");
                    directionsET.setText("");
                    commentsET.setText("");
                    recipe_id ++;
                    Toast.makeText(AddingRecipe.this, R.string.save_success, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddingRecipe.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
        if(Build.VERSION.SDK_INT>= 23){
            String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION_REQUEST);
            }
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_PERMISSION_REQUEST);
            }
            if (checkCallingOrSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
            }
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == WRITE_PERMISSION_REQUEST){
            if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,R.string.permission_not_granted, Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode == CAMERA_REQUEST) && (resultCode == RESULT_OK)){
            imageview.getLayoutParams().height = ((int)(370 * Resources.getSystem().getDisplayMetrics().density));
            imageview.setImageDrawable(Drawable.createFromPath(file.getAbsolutePath()));
            bitmap = BitmapFactory.decodeFile(file.getPath());
            bitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(file.getPath()), 200, 200);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_back_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.back_to_main:
                Intent intent = new Intent(AddingRecipe.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.save: if(recipe_nameET.getText().toString().equals("") || categoryET.getText().toString().equals("")){
                Toast.makeText(AddingRecipe.this, R.string.category_and_name, Toast.LENGTH_LONG).show();
                break;}
                else if(check_camera_press==false){
                Toast.makeText(AddingRecipe.this, R.string.cameraBtn_not_pushed, Toast.LENGTH_LONG).show();
                break;
            }
                else{
                        Recipe recipe = new Recipe(recipe_nameET.getText().toString(), descriptionET.getText().toString(), categoryET.getText().toString(), ingredientsET.getText().toString(), directionsET.getText().toString(), commentsET.getText().toString(), file.getAbsolutePath(), file,recipe_id);
                        RecipeManager savedRecipes = RecipeManager.getInstance(AddingRecipe.this);
                        savedRecipes.addRecipe(recipe);

                imageview.setImageBitmap(null);
                recipe_nameET.setText("");
                descriptionET.setText("");
                categoryET.setText("");
                ingredientsET.setText("");
                directionsET.setText("");
                commentsET.setText("");
                recipe_id++;
                Toast.makeText(AddingRecipe.this, R.string.save_success, Toast.LENGTH_SHORT).show();
                intent = new Intent(AddingRecipe.this, MainActivity.class);
                startActivity(intent);
                    break;
            }
            case R.id.action_my_recipes:
                intent = new Intent(AddingRecipe.this, SavedRecipes.class);
                startActivity(intent);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("ID", recipe_id);
        editor.apply();
    }
}
