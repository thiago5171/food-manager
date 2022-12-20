package com.example.food_manager.view.recipe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.food_manager.R
import com.example.food_manager.view.recipe.ingredient.IngredientRegisterForm
import com.example.food_manager.view.recipe.list.RecipeList

class RecipeRegisterForm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_register_form)
        val addIngredientButton = findViewById<Button>(R.id.add_ingredients_btn)
        addIngredientButton.setOnClickListener {
            val intent = Intent(this, RecipeList::class.java)
            startActivity(intent)
        }
    }
}