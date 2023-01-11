package com.example.food_manager.ui.recipe
import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.food_manager.R
import com.example.food_manager.data.DatabaseHelper
import com.example.food_manager.databinding.ActivityRecipeEditFormBinding
import com.example.food_manager.domain.Ingredient
import com.example.food_manager.domain.Recipe
import com.example.food_manager.domain.RecipeIngredientCrossRef
import com.example.food_manager.domain.RecipeWithIngredients
import com.google.android.material.textfield.TextInputEditText
import com.example.food_manager.ui.adapter.IngredientsToSelectAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeEditForm : AppCompatActivity() {
    private val binding by lazy {
        ActivityRecipeEditFormBinding.inflate(layoutInflater)
    }

    private var chosenIngredients = ArrayList<Ingredient>()
    private var imageUri: Uri? = null

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
        if (uri != null) {
            val pickImageButton = binding.editPickRecipeImageAction
            val newIcon = AppCompatResources.getDrawable(this,
                R.drawable.ic_baseline_check_circle_outline_24)
            pickImageButton.setCompoundDrawablesWithIntrinsicBounds(null, null,
                newIcon, null)
            pickImageButton.text = getString(R.string.image_was_selected)
            pickImageButton.setTextColor(
                AppCompatResources.getColorStateList(this,
                R.color.trendingStart))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val pickImageButton = binding.editPickRecipeImageAction

        pickImageButton.setOnClickListener {
            pickImage()
            pickImageButton.text = ""

        }

        val selectIngredients = binding.editSelectIngredients
        selectIngredients.setOnClickListener {
            selectIngredients()
        }

        val saveRecipeButton = binding.editSaveRecipeBtn
        saveRecipeButton.setOnClickListener {
            saveRecipe()
        }

        val item = intent.getSerializableExtra("recipe") as RecipeWithIngredients?
        if (item == null) {
            finish()
            return
        }
        val nameEdit = findViewById<TextInputEditText>(R.id. edit_recipe_name_edit)
        val descriptionEdit = findViewById<TextInputEditText>(R.id.edit_recipe_description_edit)
        val yieldEdit = findViewById<TextInputEditText>(R.id.register_recipe_yield_edit)

        nameEdit.setText(item.recipe.name)
        descriptionEdit.setText(item.recipe.description)
        yieldEdit.setText(item.recipe.yield.toString())

        chosenIngredients = item.ingredients as ArrayList<Ingredient>

        val adapter = IngredientsToSelectAdapter(chosenIngredients)
        val ingredientsList = binding.editChosenIngredients
        ingredientsList.layoutManager = GridLayoutManager(
            this@RecipeEditForm, GridLayoutManager.VERTICAL)
        ingredientsList.adapter = adapter
    }

    private fun pickImage() {
        val permission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.READ_EXTERNAL_STORAGE)
        if (permission == PackageManager.PERMISSION_GRANTED) {
            getContent.launch("image/*")
        } else {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                getContent.launch("image/*")
            }
        }
    }

    private fun selectIngredients() {
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
                val adapter = IngredientsToSelectAdapter(chosenIngredients)
                val ingredientsList = binding.editChosenIngredients
                ingredientsList.layoutManager = GridLayoutManager(
                    this@RecipeEditForm, GridLayoutManager.VERTICAL)
                ingredientsList.adapter = adapter
                dialog.dismiss()
            }
            builder.setNegativeButton("Cancel") {dialog, _ -> dialog.cancel()}

            val alertDialog = builder.create()
            alertDialog.show()
        }
    }

    private fun saveRecipe() {
        val name = binding.editRecipeNameEdit.text.toString()
        val description = binding.editRecipeDescriptionEdit.text.toString()
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
            cost = price,
            imgUri = imageUri.toString()
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
                    dao.editOne(recipe, crossRefs)
                }
            }

            Toast.makeText(this, getString(R.string.successful_operation), Toast.LENGTH_SHORT).show()

            finish()
        } else {
            Toast.makeText(this, getString(R.string.data_not_processed), Toast.LENGTH_SHORT).show()
        }
    }

    private fun isRecipeWithIngredientsValid(recipeWithIngredients: RecipeWithIngredients): Boolean {
        return recipeWithIngredients.recipe.name.isNotBlank() &&
                recipeWithIngredients.recipe.cost > 0 && recipeWithIngredients.recipe.yield > 0 &&
                recipeWithIngredients.recipe.imgUri != ""
    }

}