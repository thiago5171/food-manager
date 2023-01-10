package com.example.food_manager.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.food_manager.domain.recipe.Ingredient

@Dao
interface IngredientDAO {
    @Insert
    fun save(ingredient: Ingredient)

    @Query("select * from ingredient")
    fun findAll() : List<Ingredient>

    @Delete
    fun deleteOne(ingredient: Ingredient)
}