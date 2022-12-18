package view.recipe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.food_manager.R
import view.recipe.ingredient.IngredientRegisterForm

class RecipeRegisterForm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_register_form)
        val addIngredientButton = findViewById<Button>(R.id.add_ingredients_btn)
        addIngredientButton.setOnClickListener {
            val intent = Intent(this, IngredientRegisterForm::class.java)
            startActivity(intent)
        }
    }
}