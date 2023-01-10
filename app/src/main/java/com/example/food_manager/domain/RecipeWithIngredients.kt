package com.example.food_manager.domain

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity
data class RecipeWithIngredients(
    @Embedded var recipe: Recipe,
    @Relation(
        parentColumn = "recipeID",
        entityColumn = "ingredientID",
        associateBy = Junction(RecipeIngredientCrossRef::class)
    )
    val ingredients: List<Ingredient>
) : java.io.Serializable