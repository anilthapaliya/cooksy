package com.bca.cooksy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bca.cooksy.R;
import com.bca.cooksy.models.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipeList;
    private ItemClickListener clickListener;

    public RecipeAdapter(List<Recipe> recipeList, ItemClickListener clickListener) {
        this.recipeList = recipeList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);

        holder.tvTitle.setText(recipe.getTitle());
        holder.tvCategory.setText("Category: " + recipe.getCategory());
        holder.tvOrigin.setText("Origin: " + recipe.getOrigin());
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {

        CardView cardRoot;
        TextView tvTitle, tvCategory, tvOrigin;

        public RecipeViewHolder(@NonNull View itemView, ItemClickListener clickListener) {
            super(itemView);
            cardRoot = itemView.findViewById(R.id.cardRoot);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvOrigin = itemView.findViewById(R.id.tvOrigin);

            cardRoot.setOnClickListener(v -> {
                PopupMenu menu = new PopupMenu(v.getContext(), v);
                menu.inflate(R.menu.popup_menu);

                menu.setOnMenuItemClickListener(item -> {
                    int post = getBindingAdapterPosition();
                    if (item.getItemId() == R.id.menuEdit) {
                        clickListener.onEdit(post);
                        return true;
                    }
                    if (item.getItemId() == R.id.menuDelete) {
                        clickListener.onDelete(post);
                        return true;
                    }
                    return false;
                });
                menu.show();
            });
        }
    }

    public interface ItemClickListener {
        void onEdit(int position);
        void onDelete(int position);
    }

}

