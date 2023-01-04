package com.example.food_manager.domain.Expense

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Expense(
    @PrimaryKey(autoGenerate = true) val id : Long = 0L,
    val name: String,
    val description: String,
    val price: Double
) : Serializable