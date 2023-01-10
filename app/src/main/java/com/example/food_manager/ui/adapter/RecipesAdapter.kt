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
import com.example.food_manager.ui.recipe.RecipeEditForm

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
        viewHolder.yieldView.text = "Rende: ${recipes[position].yield} porções."
        viewHolder.descriptionView.text = recipes[position].description
        Glide.with(viewHolder.recipeImageView.context).
            load(Uri.parse(recipes[position].imgUri)).into(viewHolder.recipeImageView)

        viewHolder.itemView.setOnClickListener{view->
            val i = Intent(view.context,  RecipeEditForm::class.java)


            val scope = MainScope()
            scope.launch {
                withContext(Dispatchers.IO) {
                    val  item = recipesDAO.findFullRecipeById(recipes[position].id)
                    i.putExtra("recipe", item)
                    println(item)
//                    val nameEdit = view.findViewById<TextView>(R.id.edit_recipe_name_edit)
//                    val descriptionEdit = view.findViewById<TextView>(R.id.edit_recipe_description_edit)
//                    val yieldEdit = view.findViewById<TextView>(R.id.edit_recipe_yield_field)
//                    nameEdit.text = item.recipe.name
//                    descriptionEdit.text = item.recipe.description
//                    yieldEdit.text = item.recipe.yield.toString()

                    recipes.clear()
                    view.context.startActivity(i)
                }
            }
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, recipes.size)
        }

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
