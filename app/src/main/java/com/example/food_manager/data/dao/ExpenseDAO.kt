package com.example.food_manager.data.dao

import androidx.room.*
import com.example.food_manager.domain.Expense.Expense

@Dao
interface ExpenseDAO {
    @Insert
    fun save(expense: Expense)

    @Query("select * from expense")
    fun findAll(): List<Expense>

    @Delete
    fun deleteOne(expense: Expense)
}