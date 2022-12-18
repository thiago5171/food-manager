package view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.example.food_manager.R
import view.recipe.RecipeRegisterForm

class Main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recipesLayout = findViewById<LinearLayout>(R.id.recipes)
        recipesLayout.setOnClickListener {
            val intent = Intent(this, RecipeRegisterForm::class.java)
            startActivity(intent)
        }
    }
}