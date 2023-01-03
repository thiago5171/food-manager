package com.example.food_manager.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.food_manager.domain.Expense

interface ExpenseDAO {
    @Insert
    fun save(expense: Expense)

    @Query("select * from expense")
    fun findAll(): List<Expense>

    @Delete
    fun deleteOne(expense: Expense)
}