package com.example.food_manager.ui.recipe.ingredient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.food_manager.databinding.ActivitySingleIngredientBinding

class SingleIngredient : AppCompatActivity() {
    private val binding by lazy {
        ActivitySingleIngredientBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}