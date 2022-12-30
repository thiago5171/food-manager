package com.example.food_manager.ui.recipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.food_manager.R
import com.example.food_manager.data.DatabaseHelper
import com.example.food_manager.databinding.ActivityRecipeRegisterFormBinding
import com.example.food_manager.domain.recipe.Ingredient
import com.example.food_manager.ui.adapter.IngredientsAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeRegisterForm : AppCompatActivity() {
    private val binding by lazy {
        ActivityRecipeRegisterFormBinding.inflate(layoutInflater)
    }

    private var chosenIngredients = ArrayList<Ingredient>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val selectIngredients = binding.selectIngredients
        selectIngredients.setOnClickListener {
            val builder = AlertDialog.Builder(this)

            builder.setTitle(R.string.pick_ingredients)
            builder.setCancelable(false)
            builder.setPositiveButton("OK") { _, _ -> finish() }
            builder.setNegativeButton("Cancel") {_, _ -> finish()}

            val db = DatabaseHelper.getInstance(this)
            val dao = db.ingredientDAO()
            val scope = MainScope()

            scope.launch {
                val ingredients = withContext(Dispatchers.IO) {
                    dao.findAll()
                }
                db.close()
                val ingredientsAdapter = IngredientsAdapter(this@RecipeRegisterForm, ingredients)
                builder.setAdapter(ingredientsAdapter) { _, which ->
                    chosenIngredients.add(ingredients[which])
                }
            }
            val alertDialog = builder.create()
            alertDialog.show()
        }
    }
}