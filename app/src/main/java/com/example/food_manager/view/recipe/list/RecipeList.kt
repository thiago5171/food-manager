package com.example.food_manager.view.recipe.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import com.example.food_manager.R
import com.example.food_manager.domain.RecipeItemListModel
import com.example.food_manager.domain.adapter.RecipeListAdapter
import com.example.food_manager.view.recipe.RecipeRegisterForm
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RecipeList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)
        var recipes = ArrayList<RecipeItemListModel>()
        var url =
            "https://assets.unileversolutions.com/recipes-v2/214590.jpg"
        recipes.add(
            RecipeItemListModel(
                "Torta de frango sem queijoTorta de frango sem queijoTorta de frango sem queijo",
                "Especial do artur corno",
                24.00,
                url
            )
        )
        recipes.add(
            RecipeItemListModel(
                "Torta de frango sem queijo",
                "Especial do artur corno",
                24.00,
                url
            )
        )
        recipes.add(
            RecipeItemListModel(
                "Torta de frango sem queijo",
                "Especial do artur corno",
                24.00,
                url
            )
        )
        recipes.add(
            RecipeItemListModel(
                "Torta de frango sem queijo",
                "Especial do artur corno",
                24.00,
                url
            )
        )
        recipes.add(
            RecipeItemListModel(
                "Torta de frango sem queijo",
                "Especial do artur corno",
                24.00,
                url
            )
        )
        recipes.add(
            RecipeItemListModel(
                "Torta de frango sem queijo",
                "Especial do artur corno",
                24.00,
                url
            )
        )

        var adapter: RecipeListAdapter = RecipeListAdapter(applicationContext, recipes)
        val list = findViewById<ListView>(R.id.recipeListItem)

        list.adapter = adapter
        val createButton = findViewById<FloatingActionButton>(R.id.createRecipe)
        createButton.setOnClickListener{
            val intent = Intent(this, RecipeRegisterForm::class.java)
            startActivity(intent)
            true
        }
    }
}