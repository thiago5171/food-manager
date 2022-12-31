package com.example.food_manager.ui.recipe

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.food_manager.R
import com.example.food_manager.data.DatabaseHelper
import com.example.food_manager.databinding.ActivityRecipeRegisterFormBinding
import com.example.food_manager.domain.recipe.Ingredient
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
            builder.setPositiveButton("OK") { dialog, _ ->
                selectIngredients.setBackgroundColor(R.color.primaryDark)
                for (ingredient in chosenIngredients) {
                    selectIngredients.append("${ingredient.name} " +
                            "${ingredient.quantity} ${ingredient.unitMeasurement}")
                }
                dialog.dismiss()
            }
            builder.setNegativeButton("Cancel") {dialog, _ -> dialog.cancel()}

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

                val alertDialog = builder.create()
                alertDialog.show()
            }
        }
    }
}