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

class IngredientEditForm : AppCompatActivity() {
    private val binding by lazy {
        ActivityIngredientRegisterFormBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val title = binding.registerIngredientTitle
        title.text = getString(R.string.edit_ingredient)

        val ingredient = intent.getSerializableExtra("ingredient") as Ingredient?

        fillUpIngredientInfoOnView(ingredient)

        val saveIngredientButton = binding.saveIngredientBtn
        saveIngredientButton.setOnClickListener {
            ingredient?.id?.let { id -> editIngredient(id) }
        }

        val cancelButton = binding.cancelIngredientRegisterBtn
        cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun fillUpIngredientInfoOnView(ingredient: Ingredient?) {
        val nameEdit = binding.registerIngredientNameEdit
        val descriptionEdit = binding.registerIngredientDescriptionEdit
        val quantityEdit = binding.registerIngredientQuantityEdit
        val unitMeasurementEdit = binding.registerIngredientUnitMeasurementEdit
        val priceEdit = binding.registerIngredientPriceEdit

        nameEdit.setText(ingredient?.name)
        descriptionEdit.setText(ingredient?.description)
        quantityEdit.setText(ingredient?.quantity.toString())
        unitMeasurementEdit.setText(ingredient?.unitMeasurement)
        priceEdit.setText(ingredient?.price.toString())
    }

    private fun editIngredient(id: Long) {
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
            id = id,
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
                    dao.edit(ingredient)
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