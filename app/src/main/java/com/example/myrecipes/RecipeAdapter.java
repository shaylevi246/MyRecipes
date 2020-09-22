package com.example.myrecipes;

import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private MyRecipeListener listener;
    private List<Recipe> recipes;

    interface MyRecipeListener{
        void OnRecipeClicked(int position,View view);
    }
    public void setListener(MyRecipeListener listener){
        this.listener = listener;
    }

    public RecipeAdapter(List<Recipe> recipes)
    {
        this.recipes = recipes;

    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView nameTV;
        TextView categoryTV;
        ImageView imageView;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.TVrecipe_name);
            categoryTV = itemView.findViewById(R.id.TVrecipe_category);
            imageView = itemView.findViewById(R.id.small_pic);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.OnRecipeClicked(getAdapterPosition(),v);
                    }
                }
            });
        }
    }


    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_card_view, parent, false);
        RecipeViewHolder recipeViewHolder = new RecipeViewHolder(view);
        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.nameTV.setText(recipe.getName());
        holder.categoryTV.setText(recipe.getCategory());
        holder.imageView.setImageBitmap(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(recipe.getPhoto_path()),  200, 200 ));

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void filterList(ArrayList<Recipe>filteredList){
        recipes = filteredList;
        notifyDataSetChanged();
    }

}
