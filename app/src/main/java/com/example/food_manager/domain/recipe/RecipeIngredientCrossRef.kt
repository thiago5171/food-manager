package com.example.food_manager.domain.recipe

import androidx.room.Entity
import java.io.Serializable

@Entity(primaryKeys = ["recipeID", "ingredientID"])
data class RecipeIngredientCrossRef(
    var recipeID: Long,
    val ingredientID: Long
): Serializable
