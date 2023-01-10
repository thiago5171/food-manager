package com.example.food_manager.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id : Long = 0L,
    val name: String,
    val description: String,
    val cost: Double,
    val yield: Int,
    val imgUri: String
) : Serializable