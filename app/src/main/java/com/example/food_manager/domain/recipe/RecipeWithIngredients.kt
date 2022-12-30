package com.example.food_manager.domain.recipe

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class RecipeWithIngredients(
    @Embedded val recipe: Recipe,
    @Relation(
        parentColumn = "recipeID",
        entityColumn = "ingredientID",
        associateBy = Junction(RecipeIngredientCrossRef::class)
    )
    val ingredients: ArrayList<Ingredient>
)