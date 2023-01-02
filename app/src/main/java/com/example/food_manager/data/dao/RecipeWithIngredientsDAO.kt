package com.example.food_manager.data.dao

import androidx.room.*
import com.example.food_manager.domain.recipe.Recipe
import com.example.food_manager.domain.recipe.RecipeIngredientCrossRef

@Dao
interface RecipeWithIngredientsDAO {
    @Insert
    fun save(recipe: Recipe)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveIngredient(join: RecipeIngredientCrossRef)
}