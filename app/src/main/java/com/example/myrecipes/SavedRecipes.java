package com.example.myrecipes;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SavedRecipes extends AppCompatActivity {
    RecipeManager manager;
    RecipeAdapter adapter;
    ArrayList<Recipe>mRecipeList = new ArrayList<>();
    ArrayList<Recipe>filteredList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_recipes);

        EditText editText = findViewById(R.id.search_bar);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });

        Toast.makeText(SavedRecipes.this, R.string.erase_option,Toast.LENGTH_LONG).show();

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        manager = RecipeManager.getInstance(this);

        adapter = new RecipeAdapter(manager.getRecipes());
        mRecipeList = manager.getRecipes();

        adapter.setListener(new RecipeAdapter.MyRecipeListener() {
            @Override
            public void OnRecipeClicked(int position, View view) {

                if(filteredList.size() != 0){
                    int check_id =filteredList.get(position).getId();
                    for(int i = 0 ; i<mRecipeList.size();i++){
                        if(check_id == mRecipeList.get(i).getId()){
                            position = i;
                        }
                    }
                }

                Intent intent = new Intent(SavedRecipes.this,ShowRecipe.class);
                intent.putExtra("name",manager.getRecipe(position).getName());
                intent.putExtra("description",manager.getRecipe(position).getDescription());
                intent.putExtra("category",manager.getRecipe(position).getCategory());
                intent.putExtra("ingredients",manager.getRecipe(position).getIngredients());
                intent.putExtra("directions",manager.getRecipe(position).getDirections());
                intent.putExtra("comments",manager.getRecipe(position).getComments());
                intent.putExtra("pic", manager.getRecipe(position).getPhoto_path());
                startActivity(intent);

            }
        });


        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {

                new AlertDialog.Builder(viewHolder.itemView.getContext()).setMessage(R.string.erase_sure)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(SavedRecipes.this, R.string.remove_success, Toast.LENGTH_LONG).show();
                                int position = viewHolder.getAdapterPosition();
                                if(filteredList.size() != 0){
                                    int check_id =filteredList.get(position).getId();
                                    for(int i = 0 ; i<mRecipeList.size();i++){
                                        if(check_id == mRecipeList.get(i).getId()){
                                            position = i;
                                        }
                                    }
                                }
                                manager.removeRecipe(position);
                                adapter.notifyItemRemoved(position);
                            }
                        }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SavedRecipes.this, R.string.not_erase, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SavedRecipes.this, SavedRecipes.class);
                        finish();
                        overridePendingTransition( 0, 0);
                        startActivity(intent);
                        overridePendingTransition( 0, 0);
                    }
                }).create().show();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
    }
    private void filter(String text){
        mRecipeList = manager.getRecipes();
        filteredList = new ArrayList<>();
        for(Recipe recipe : mRecipeList){
            if(recipe.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(recipe);
            }
        }
        adapter.filterList(filteredList);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back_from_saved_menu,menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_add_recipe) {
            Intent intent = new Intent(SavedRecipes.this, AddingRecipe.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.back_to_main) {
            Intent intent = new Intent(SavedRecipes.this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


}
