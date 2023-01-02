package com.example.food_manager.ui.recipe.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.food_manager.R
import com.example.food_manager.domain.RecipeItemListModel
import com.example.food_manager.ui.adapter.RecipeListAdapter
import com.example.food_manager.ui.recipe.RecipeRegisterForm
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RecipeList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)
        val recipes = ArrayList<RecipeItemListModel>()
        val url =
            "https://assets.unileversolutions.com/recipes-v2/214590.jpg"
        recipes.add(
            RecipeItemListModel(
                id = 1,
                tittle = "Torta de frango sem queijoTorta de frango sem queijoTorta de frango sem queijo",
                subtitle = "Especial do artur corno",
                cost = 24.00,
                urlImage = url
            )
        )
        recipes.add(
            RecipeItemListModel(
                id = 2,
                tittle = "Torta de frango sem queijoTorta de frango sem queijoTorta de frango sem queijo",
                subtitle = "Especial do artur corno",
                cost = 24.00,
                urlImage = url
            )
        )
        recipes.add(
            RecipeItemListModel(
                id = 3,
                tittle = "Torta de frango sem queijoTorta de frango sem queijoTorta de frango sem queijo",
                subtitle = "Especial do artur corno",
                cost = 24.00,
                urlImage = url
            )
        )
        recipes.add(
            RecipeItemListModel(
                id = 4,
                tittle = "Torta de frango sem queijoTorta de frango sem queijoTorta de frango sem queijo",
                subtitle = "Especial do artur corno",
                cost = 24.00,
                urlImage = url
            )
        )
        recipes.add(
            RecipeItemListModel(
                id = 5,
                tittle = "Torta de frango sem queijoTorta de frango sem queijoTorta de frango sem queijo",
                subtitle = "Especial do artur corno",
                cost = 24.00,
                urlImage = url
            )
        )
        recipes.add(
            RecipeItemListModel(
                id = 6,
                tittle = "Torta de frango sem queijoTorta de frango sem queijoTorta de frango sem queijo",
                subtitle = "Especial do artur corno",
                cost = 24.00,
                urlImage = url
            )
        )

        val adapter = RecipeListAdapter(applicationContext, recipes)
        val list = findViewById<ListView>(R.id.recipeListItem)

        list.adapter = adapter
        val createButton = findViewById<FloatingActionButton>(R.id.createRecipe)
        createButton.setOnClickListener{
            val intent = Intent(this, RecipeRegisterForm::class.java)
            startActivity(intent)
        }
    }
}