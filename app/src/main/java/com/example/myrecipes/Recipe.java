package com.example.myrecipes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class Recipe implements Serializable {
    private String name ,description,category, ingredients, directions, comments;
    private String photo_path;
    private File file;
    private int id;

    public Recipe(String name, String description, String category, String ingredients, String directions, String comments,String photo, File file, int id) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.ingredients = ingredients;
        this.directions = directions;
        this.comments = comments;
        this.photo_path = photo;
        this.file = file;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
    public String getPhoto_path()
    {
        return photo_path;
    }

    public void setPhoto_path(String photo)
    {
        this.photo_path = photo;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }


    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", directions='" + directions + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}
