package com.example.food_manager.ui.recipe.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.food_manager.data.DatabaseHelper
import com.example.food_manager.databinding.ActivityRecipeListBinding
import com.example.food_manager.domain.recipe.Recipe
import com.example.food_manager.ui.adapter.RecipesAdapter
import com.example.food_manager.ui.recipe.RecipeRegisterForm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeList : AppCompatActivity() {
    private val binding by lazy {
        ActivityRecipeListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val db = DatabaseHelper.getInstance(this)
        val dao = db.recipeWithIngredientsDAO()
        val scope = MainScope()

        val list = binding.recipesList
        scope.launch {
            val recipes = withContext(Dispatchers.IO) {
                dao.findAllWithNoIngredients()
            }

            val recipesList = ArrayList<Recipe>()
            recipesList.addAll(recipes)
            val adapter = RecipesAdapter(recipesList, dao)

            list.layoutManager = GridLayoutManager(
                this@RecipeList, GridLayoutManager.VERTICAL)
            list.adapter = adapter
        }


        val createButton = binding.createRecipe
        createButton.setOnClickListener{
            val intent = Intent(this, RecipeRegisterForm::class.java)
            startActivity(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        val db = DatabaseHelper.getInstance(this)
        val dao = db.recipeWithIngredientsDAO()
        val scope = MainScope()

        val list = binding.recipesList
        scope.launch {
            val recipes = withContext(Dispatchers.IO) {
                dao.findAllWithNoIngredients()
            }

            val recipesList = ArrayList<Recipe>()
            recipesList.addAll(recipes)
            val adapter = RecipesAdapter(recipesList, dao)

            list.layoutManager = GridLayoutManager(
                this@RecipeList, GridLayoutManager.VERTICAL)
            list.adapter = adapter
        }
    }
}