package com.example.food_manager.ui.recipe

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.example.food_manager.R
import com.example.food_manager.data.DatabaseHelper
import com.example.food_manager.databinding.ActivityRecipeRegisterFormBinding
import com.example.food_manager.domain.recipe.Ingredient
import com.example.food_manager.domain.recipe.Recipe
import com.example.food_manager.domain.recipe.RecipeIngredientCrossRef
import com.example.food_manager.domain.recipe.RecipeWithIngredients
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

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val selectIngredients = binding.selectIngredients

        selectIngredients.setOnClickListener {
            val builder = AlertDialog.Builder(this)

            builder.setTitle(R.string.pick_ingredients)
            builder.setCancelable(false)

            val db = DatabaseHelper.getInstance(this)
            val dao = db.ingredientDAO()
            val scope = MainScope()

            scope.launch {
                val ingredients = withContext(Dispatchers.IO) {
                    dao.findAll()
                }
                db.close()

                val titlesArray = Array(ingredients.size) { _ -> ""}
                val checkedArray = BooleanArray(ingredients.size)

                for ((index, ingredient) in ingredients.withIndex()) {
                    titlesArray[index] = "${ingredient.name} " +
                            "${ingredient.quantity} ${ingredient.unitMeasurement}"
                    checkedArray[index] = false
                }

                builder.setMultiChoiceItems(titlesArray, checkedArray) {
                    _, which, isChecked ->
                    checkedArray[which] = isChecked
                    chosenIngredients.add(ingredients[which])
                }

                builder.setPositiveButton("OK") { dialog, _ ->
                    val adapter = IngredientsAdapter(chosenIngredients)
                    val ingredientsList = binding.chosenIngredients
                    ingredientsList.layoutManager = GridLayoutManager(
                        this@RecipeRegisterForm, GridLayoutManager.VERTICAL)
                    ingredientsList.adapter = adapter
                    dialog.dismiss()
                }
                builder.setNegativeButton("Cancel") {dialog, _ -> dialog.cancel()}

                val alertDialog = builder.create()
                alertDialog.show()
            }
        }

        val saveRecipeButton = binding.saveRecipeBtn

        saveRecipeButton.setOnClickListener {
            val name = binding.registerRecipeNameEdit.text.toString()
            val description = binding.registerRecipeDescriptionEdit.text.toString()
            val yield = binding.registerRecipeYieldEdit.text.toString().toInt()
            val ingredients = chosenIngredients
            var price = 0.0
            for (ingredient in ingredients) {
                price += ingredient.price
            }

            val recipe = Recipe(
                name = name,
                description = description,
                yield = yield,
                price = price
            )

            val recipeWithIngredients = RecipeWithIngredients(recipe, ingredients)

            val crossRefs = ArrayList<RecipeIngredientCrossRef>()
            for (ingredient in ingredients) {
                crossRefs.add(RecipeIngredientCrossRef(recipe.id, ingredient.id))
            }

            if (isRecipeWithIngredientsValid(recipeWithIngredients)) {
                val dao = DatabaseHelper.getInstance(this).recipeWithIngredientsDAO()

                val scope = MainScope()
                scope.launch {
                    withContext(Dispatchers.IO) {
                        dao.save(recipe)
                        for (crossRef in crossRefs) {
                            dao.saveIngredient(crossRef)
                        }
                    }
                }

                Toast.makeText(this, "salvo com sucesso", Toast.LENGTH_SHORT).show()

                finish()
            } else {
                Toast.makeText(this, "algo deu errado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isRecipeWithIngredientsValid(recipeWithIngredients: RecipeWithIngredients): Boolean {
        return recipeWithIngredients.recipe.name.isNotBlank() &&
                recipeWithIngredients.recipe.price > 0 && recipeWithIngredients.recipe.yield > 0
    }
}