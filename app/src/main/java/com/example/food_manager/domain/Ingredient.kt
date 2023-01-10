package com.example.food_manager.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Ingredient(
    @PrimaryKey(autoGenerate = true) val id : Long = 0L,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "unitMeasurement") val unitMeasurement: String,
    @ColumnInfo(name = "price") val price: Double
) : Serializable