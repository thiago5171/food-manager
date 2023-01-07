package com.example.food_manager.ui.recipe.ingredient.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.food_manager.databinding.ActivityIngredientListBinding
import com.example.food_manager.ui.recipe.ingredient.IngredientRegisterForm

class IngredientList : AppCompatActivity() {
    private val binding by lazy {
        ActivityIngredientListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val addIngredientButton = binding.createIngredient
        addIngredientButton.setOnClickListener {
            val intent = Intent(this, IngredientRegisterForm::class.java)
            startActivity(intent)
        }
    }
}