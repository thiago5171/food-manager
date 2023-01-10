package com.example.food_manager.ui.recipe.ingredient.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.food_manager.databinding.ActivitySingleIngredientToSelectBinding

class SingleIngredientToSelect : AppCompatActivity() {
    private val binding by lazy {
        ActivitySingleIngredientToSelectBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}