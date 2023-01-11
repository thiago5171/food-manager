package com.example.food_manager.data.dao

import androidx.room.*
import com.example.food_manager.domain.Expense

@Dao
interface ExpenseDAO {
    @Insert
    fun save(expense: Expense)

    @Query("select * from expense")
    fun findAll(): List<Expense>

    @Query("select * from expense where id = :id")
    fun findByID(id: Long): Expense

    @Update
    fun edit(expense: Expense)

    @Delete
    fun deleteOne(expense: Expense)
}