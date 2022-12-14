package com.example.food_manager.ui.recipe.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.food_manager.databinding.ActivitySingleRecipeBinding

class SingleRecipe : AppCompatActivity() {
    private val binding by lazy {
        ActivitySingleRecipeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}