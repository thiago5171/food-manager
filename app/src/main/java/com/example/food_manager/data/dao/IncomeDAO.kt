package com.example.food_manager.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.food_manager.domain.Income

@Dao
interface IncomeDAO {
    @Insert
    fun save(income: Income)

    @Query("select * from income")
    fun findAll(): List<Income>

    @Query("select * from income where id = :id")
    fun findByID(id: Long): Income

    @Update
    fun edit(income: Income)

    @Delete
    fun deleteOne(income: Income)
}