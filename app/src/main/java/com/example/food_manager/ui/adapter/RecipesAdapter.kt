package com.example.food_manager.ui.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food_manager.R
import com.example.food_manager.data.dao.RecipeWithIngredientsDAO
import com.example.food_manager.domain.recipe.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipesAdapter (val recipes: ArrayList<Recipe>, val recipesDAO: RecipeWithIngredientsDAO) : RecyclerView.Adapter<RecipesAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView
        val costView: TextView
        val yieldView: TextView
        val descriptionView: TextView
        val recipeImageView: ImageView
        val deleteButton: ImageView

        init {
            nameView = view.findViewById(R.id.recipe_name)
            costView = view.findViewById(R.id.recipe_cost)
            yieldView = view.findViewById(R.id.recipe_yield)
            descriptionView = view.findViewById(R.id.recipe_description)
            recipeImageView = view.findViewById(R.id.recipe_image)
            deleteButton = view.findViewById(R.id.delete_button)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.activity_single_recipe, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.nameView.text = recipes[position].name
        viewHolder.costView.text = "Custa: R$${recipes[position].cost}"
        viewHolder.yieldView.text = "Rende: ${recipes[position].yield}"
        viewHolder.descriptionView.text = recipes[position].description
        Glide.with(viewHolder.recipeImageView.context).
            load(Uri.parse(recipes[position].imgUri)).into(viewHolder.recipeImageView)

        viewHolder.deleteButton.setOnClickListener {
            val scope = MainScope()
            scope.launch {
                withContext(Dispatchers.IO) {
                    recipesDAO.deleteOne(recipes[position])
                    recipes.clear()
                    recipes.addAll(recipesDAO.findAllWithNoIngredients())
                }
            }
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, recipes.size)
        }
    }

    override fun getItemCount() = recipes.size
}
