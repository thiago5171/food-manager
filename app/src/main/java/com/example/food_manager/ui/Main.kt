package com.example.food_manager.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.food_manager.databinding.ActivityMainBinding
import com.example.food_manager.ui.finances.Finances
import com.example.food_manager.ui.graphs.Graphs
import com.example.food_manager.ui.recipe.ingredient.list.IngredientList
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
            val intent = Intent(this, IngredientList::class.java)
            startActivity(intent)
        }

        val expenseLayout = binding.finances
        expenseLayout.setOnClickListener {
            val intent = Intent(this, Finances::class.java)
            startActivity(intent)
        }

        val graphsLayout = binding.graphs
        graphsLayout.setOnClickListener {
            val intent = Intent(this, Graphs::class.java)
            startActivity(intent)
        }
    }
}