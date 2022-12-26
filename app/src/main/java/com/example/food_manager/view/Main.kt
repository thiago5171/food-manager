package com.example.food_manager.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.example.food_manager.R
import com.example.food_manager.view.expense.ExpenseRegisterForm
import com.example.food_manager.view.recipe.list.RecipeList

class Main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recipesLayout = findViewById<LinearLayout>(R.id.recipes)
        recipesLayout.setOnClickListener {
            val intent = Intent(this, RecipeList::class.java)
            startActivity(intent)
        }

        val expenseLayout =findViewById<LinearLayout>(R.id.finances)
        expenseLayout.setOnClickListener {
            val intent = Intent(this, ExpenseRegisterForm::class.java)
            startActivity(intent)
        }
    }
}