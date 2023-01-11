package com.example.food_manager.ui.recipe.ingredient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.food_manager.R
import com.example.food_manager.data.DatabaseHelper
import com.example.food_manager.databinding.ActivityIngredientRegisterFormBinding
import com.example.food_manager.domain.Ingredient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IngredientRegisterForm : AppCompatActivity() {
    private val binding by lazy {
        ActivityIngredientRegisterFormBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val saveIngredientButton = binding.saveIngredientBtn
        saveIngredientButton.setOnClickListener {
            saveIngredient()
        }

        val cancelButton = binding.cancelIngredientRegisterBtn
        cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun saveIngredient() {
        val name = binding.registerIngredientNameEdit.text.toString()
        val description = binding.registerIngredientDescriptionEdit.text.toString()
        var quantity = 0
        val quantityText = binding.registerIngredientQuantityEdit.text.toString()
        if (quantityText.isNotBlank()) {
            quantity = quantityText.toInt()
        }
        var price = 0.0
        val priceText = binding.registerIngredientPriceEdit.text.toString().replace(',', '.')
        if (priceText.isNotBlank()) {
            price = priceText.toDouble()
        }
        val unitMeasurement = binding.registerIngredientUnitMeasurementEdit.text.toString()

        val ingredient = Ingredient(
            name = name,
            description = description,
            quantity = quantity,
            price = price,
            unitMeasurement = unitMeasurement
        )

        if (ingredientIsValid(ingredient)) {
            val dao = DatabaseHelper.getInstance(this).ingredientDAO()

            val scope = MainScope()
            scope.launch {
                withContext(Dispatchers.IO) {
                    dao.save(ingredient)
                }
            }

            Toast.makeText(this, getString(R.string.successful_operation), Toast.LENGTH_SHORT).show()

            finish()
        } else {
            Toast.makeText(this, getString(R.string.data_not_processed), Toast.LENGTH_SHORT).show()
        }
    }

    private fun ingredientIsValid(ingredient: Ingredient): Boolean {
        return ingredient.name.isNotBlank() && ingredient.quantity > 0 &&
                ingredient.price > 0 && ingredient.unitMeasurement.isNotBlank()
    }
}