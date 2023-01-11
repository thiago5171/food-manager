package com.example.food_manager.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.food_manager.domain.Ingredient

@Dao
interface IngredientDAO {
    @Insert
    fun save(ingredient: Ingredient)

    @Query("select * from ingredient")
    fun findAll() : List<Ingredient>

    @Query("select * from ingredient where id = :id")
    fun findByID(id: Long): Ingredient

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun edit(ingredient: Ingredient)

    @Delete
    fun deleteOne(ingredient: Ingredient)
}