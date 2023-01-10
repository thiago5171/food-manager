package com.example.food_manager.ui.recipe.ingredient.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.food_manager.data.DatabaseHelper
import com.example.food_manager.databinding.ActivityIngredientListBinding
import com.example.food_manager.domain.recipe.Ingredient
import com.example.food_manager.ui.adapter.IngredientsAdapter
import com.example.food_manager.ui.recipe.ingredient.IngredientRegisterForm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IngredientList : AppCompatActivity() {
    private val binding by lazy {
        ActivityIngredientListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val dao = DatabaseHelper.getInstance(this).ingredientDAO()
        val scope = MainScope()

        val list = binding.ingredientsList
        scope.launch {
            val ingredients = withContext(Dispatchers.IO) {
                dao.findAll()
            }

            val ingredientsList = ArrayList<Ingredient>()
            ingredientsList.addAll(ingredients)
            val adapter = IngredientsAdapter(ingredientsList, dao)

            list.layoutManager = GridLayoutManager(
                this@IngredientList, GridLayoutManager.VERTICAL)
            list.adapter = adapter
        }

        val addIngredientButton = binding.createIngredient
        addIngredientButton.setOnClickListener {
            val intent = Intent(this, IngredientRegisterForm::class.java)
            startActivity(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        val dao = DatabaseHelper.getInstance(this).ingredientDAO()
        val scope = MainScope()

        val list = binding.ingredientsList
        scope.launch {
            val ingredients = withContext(Dispatchers.IO) {
                dao.findAll()
            }

            val ingredientsList = ArrayList<Ingredient>()
            ingredientsList.addAll(ingredients)
            val adapter = IngredientsAdapter(ingredientsList, dao)

            list.layoutManager = GridLayoutManager(
                this@IngredientList, GridLayoutManager.VERTICAL)
            list.adapter = adapter
        }
    }
}