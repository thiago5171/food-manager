package com.example.food_manager.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.food_manager.domain.recipe.RecipeWithIngredients

@Dao
interface RecipeWithIngredientsDAO {
    @Transaction
    @Insert
    fun save(recipeWithIngredients: RecipeWithIngredients)

    @Transaction
    @Query("select * from recipeIngredientCrossRef where recipeID = :id")
    fun findByID(id: Long): RecipeWithIngredients
}