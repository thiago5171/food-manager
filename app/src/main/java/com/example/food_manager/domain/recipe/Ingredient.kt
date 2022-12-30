package com.example.food_manager.domain.recipe

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Ingredient(
    @PrimaryKey(autoGenerate = true) val id : Long = 0L,
    val name: String,
    val description: String,
    val quantity: Int,
    val unitMeasurement: String,
    val price: Double
) : Serializable