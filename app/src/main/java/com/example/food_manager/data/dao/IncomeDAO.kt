package com.example.food_manager.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.food_manager.domain.income.Income

@Dao
interface IncomeDAO {
    @Insert
    fun save(income: Income)

    @Query("select * from income")
    fun findAll(): List<Income>

    @Delete
    fun deleteOne(income: Income)
}