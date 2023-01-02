package com.example.food_manager.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.example.food_manager.R
import com.example.food_manager.databinding.ActivityMainBinding
import com.example.food_manager.ui.recipe.ingredient.IngredientRegisterForm
import com.example.food_manager.ui.recipe.list.RecipeList

class Main : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val recipesLayout = binding.recipes
        recipesLayout.setOnClickListener {
            val intent = Intent(this, RecipeList::class.java)
            startActivity(intent)
        }

        val ingredientsLayout = binding.ingredients
        ingredientsLayout.setOnClickListener {
            val intent = Intent(this, IngredientRegisterForm::class.java)
            startActivity(intent)
        }
    }
}