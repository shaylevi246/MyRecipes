package com.example.myrecipes;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class RecipeManager {
    public static RecipeManager instance;
    private static int i = 0;
    private Context context;
    private ArrayList<Recipe> recipes = new ArrayList<>();

    static final String FILE_NAME = "SavedRecipes.dat";

    private RecipeManager(Context context) {
        this.context = context;

        try {
            FileInputStream fis = context.openFileInput(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            recipes = (ArrayList<Recipe>) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static RecipeManager getInstance(Context context) {
        if(instance == null)
        {
            instance = new RecipeManager(context);
        }
        return instance;
    }

    public Recipe getRecipe( int position)
        {
            if (position < recipes.size()) {
                return recipes.get(position);
            }
            return null;
        }

        public void addRecipe (Recipe recipe)
        {
            recipes.add(recipes.size(), recipe);
            saveRecipes();
        }

        public void removeRecipe ( int position)
        {
            if (position < recipes.size()) {
                recipes.remove(position);
            }
            saveRecipes();
        }
        private void saveRecipes ()
        {
            try {
                FileOutputStream fos = context.openFileOutput(FILE_NAME, context.MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(recipes);
                oos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public ArrayList<Recipe> getRecipes ()
        {
            return recipes;
        }

}
